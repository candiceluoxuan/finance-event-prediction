package finance_event_prediction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java_cup.internal_error;

import moa.cluster.Cluster;
import moa.core.AutoExpandVector;
import moa.options.FloatOption;

import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.ansj.domain.Term;
import org.candice.finance_event.utilities.UnicodeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import finance_event_prediction_word2vec.VectorCalculator;
import finance_event_prediction_word2vec.Word2VEC;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class ClusterProcessor extends TrainingFileLoader {

	Logger logger = LoggerFactory.getLogger(ClusterProcessor.class);

	private String vectorPath = "E:\\IndependentProject\\vectors.bin";
	private String outputPath = "E:\\IndependentProject\\";
	Pattern textPattern = Pattern.compile("\"text\": \"(.*?)\"");

	LUOClusterer luoClusterer;
	private float eps = 0.02f;
	private int minPoints = 1;
	private int initPoints = 10;
	private float epsMin = 0f;
	private float epsMax = 2f;

	private int dimention = 201;
	private Instances instances;

	KeyWordComputer kwc;
	private int keywordCount = 3;

	VectorCalculator vectorCalculator;

	List<Weibo> weibos = new LinkedList<>();

	public ClusterProcessor() {
		initClusterer();
		initAnalyzer();
		initVectorCalculator();
	}

	protected void initClusterer() {
		luoClusterer = new LUOClusterer();
		luoClusterer.epsilonOption = new FloatOption("epsilon", 'e',
				"Defines the epsilon neighbourhood", eps, epsMin, epsMax);
		luoClusterer.muOption.setValue(minPoints);
		// 50 samples
		// 0.07, 1 -> 25 clusters
		luoClusterer.initPointsOption.setValue(initPoints);
		luoClusterer.prepareForUse();

		ArrayList<Attribute> attributes = new ArrayList<Attribute>(dimention);
		for (int i = 1; i <= dimention; i++) {
			attributes.add(new Attribute("Dimension" + i));
		}
		instances = new Instances("vector", attributes, 0);
		instances.setClassIndex(dimention - 1);
	}

	protected void initAnalyzer() {
		kwc = new KeyWordComputer(keywordCount);
	}

	protected void initVectorCalculator() {
		try {
			vectorCalculator = new Word2VEC(vectorPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected double[] initializeVector(double[] vector) {
		if (vector != null) {
			int size = vector.length;
			double[] result = new double[size + 1];
			for (int i = 0; i < size; i++) {
				result[i] = vector[i];
			}
			return result;
		}

		return vector;
	}

	@Override
	protected void processFile(File inputFile) {

		try (BufferedReader input = new BufferedReader(new InputStreamReader(
				new FileInputStream(inputFile), "gbk"))) {

			String text;
			while ((text = input.readLine()) != null) {
				// logger.debug("{}", text);
				Matcher matcher = textPattern.matcher(text);
				while (matcher != null && matcher.find()) {
					String content = matcher.group(1);
					String content_utf8 = UnicodeHelper.toUTF8(content);
					// logger.debug("{}", content_utf8);

					// Keywords
					Weibo weibo = new Weibo(content_utf8);
					Collection<Keyword> keywords = kwc
							.computeArticleTfidf(content_utf8);
					for (Keyword keyword : keywords) {
						weibo.getKeywords().add(keyword.getName());
					}
					// logger.debug("Keywords: {}", weibo.getKeywords());
					weibos.add(weibo);

					train(weibo);
				}
			}
		} catch (IOException ioException) {
			System.err.println("File Error!");
		} finally {
			System.out.println("Mission complete!");
		}

	}

	protected void train(Weibo weibo) {
		double[] vector = initializeVector(vectorCalculator
				.getWordsVector(weibo.getKeywords()));
		if (vector != null) {
			Instance instance = new DenseInstance(1, vector);
			instance.setDataset(instances);
			luoClusterer.trainOnInstance(instance);

			weibo.setInstance(instance);
		}
	}

	@Override
	public void trainByTestNews(String path) {
		super.trainByTestNews(path);

		logger.debug("Cluster size is ", luoClusterer.getClusteringResult()
				.size());
		for (Cluster cluster : luoClusterer.getClusteringResult()
				.getClustering()) {
			logger.debug("{}", cluster.getInfo());
		}

		List<Weibo> c_weibos = matchCluster(weibos);
		for (Weibo weibo : c_weibos) {
			if (weibo.getCluster() != null) {
				logger.debug("Cluster ID is {}, Weibo is {}", weibo
						.getCluster().getId(), weibo);
			}
		}
		
		persistentCluster(c_weibos);
	}

	protected Weibo matchCluster(AutoExpandVector<Cluster> clusters, Weibo weibo) {
		if (weibo.getInstance() != null) {
			double maxProbability = 0;
			for (Cluster cluster : clusters) {
				double probability = cluster.getInclusionProbability(weibo
						.getInstance());
				if (probability > maxProbability) {
					weibo.setCluster(cluster);
					maxProbability = probability;
				}
			}
		}

		return weibo;
	}

	protected Weibo matchCluster(Weibo weibo) {
		return matchCluster(getClusters(), weibo);
	}

	protected List<Weibo> matchCluster(List<Weibo> p_weibos) {
		AutoExpandVector<Cluster> clusters = getClusters();
		for (Weibo quantizedNew : p_weibos) {
			matchCluster(clusters, quantizedNew);
		}
		return p_weibos;
	}

	protected AutoExpandVector<Cluster> getClusters() {
		return luoClusterer.copy().getClusteringResult().getClustering();
	}

	protected void persistentCluster(List<Weibo> p_weibos) {
		Map<Cluster, List<Weibo>> clusters = new HashMap<>();
		for (Weibo weibo : p_weibos) {
			if (!clusters.containsKey(weibo.getCluster())) {
				clusters.put(weibo.getCluster(), new LinkedList<Weibo>());
			}
			clusters.get(weibo.getCluster()).add(weibo);
		}

		for (Entry<Cluster, List<Weibo>> entry : clusters.entrySet()) {
			File outputFile = new File(outputPath + entry.getKey().getWeight()
					+ " - " + entry.getKey().getId() + ".txt");

			try (PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputFile,
							true), "UTF-8")))) {
				for (Weibo weibo : entry.getValue()) {
					out.println(weibo);
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
