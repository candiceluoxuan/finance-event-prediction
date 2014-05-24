package finance_event_prediction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;

import org.ansj.app.keyword.Keyword;

import com.alibaba.fastjson.JSON;

import finance_event_prediction_utilities.UnicodeHelper;

import moa.cluster.Cluster;
import moa.options.FloatOption;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class ClusterFromPreparedData {

	private String outputPath = "E:\\IndependentProject\\";
	private String clusterPath = outputPath + "cluster.bin";

	LUOClusterer luoClusterer;
	private float eps = 0.02f;
	private int minPoints = 1;
	private int initPoints = 10;
	private float epsMin = 0f;
	private float epsMax = 2f;

	private int dimention = 201;
	private Instances instances;

	public ClusterFromPreparedData() {
		initClusterer();
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

	public void execute(String path) {
		trainByTestNews(path);

		for (Cluster cluster : luoClusterer.getClusteringResult()
				.getClustering()) {
			System.out.println(cluster.getInfo());
		}

		try (FileOutputStream f = new FileOutputStream(clusterPath)) {
			ObjectOutputStream oos = new ObjectOutputStream(f);
			oos.writeObject(luoClusterer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void trainByTestNews(String path) {
		File file = new File(path);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (files.length == 0) {
				System.out.println("文件夹是空的!");
				return;
			} else {
				for (File file2 : files) {
					if (file2.isDirectory()) {
						System.out.println("文件夹:" + file2.getAbsolutePath());
						trainByTestNews(file2.getAbsolutePath());
					} else {
						System.out.println("文件:" + file2.getAbsolutePath());
						processFile(file2);
					}
				}
			}
		} else {
			System.out.println("文件不存在!");
		}
	}

	protected void processFile(File inputFile) {

		try (BufferedReader input = new BufferedReader(new InputStreamReader(
				new FileInputStream(inputFile), "utf-8"))) {

			String text = "";
			while ((text = input.readLine()) != null) {
				// System.out.println(text);

				try {
					PersistentWeibo weibo = JSON.parseObject(text,
							PersistentWeibo.class);
					train(weibo);
				} catch (Exception e) {
					System.out.println(text);
				}

			}
		} catch (IOException ioException) {
			System.err.println("File Error!");
		} finally {
			System.out.println("Mission complete!");
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

	protected void train(PersistentWeibo weibo) {
		double[] vector = initializeVector(weibo.getVector());
		if (vector != null) {
			Instance instance = new DenseInstance(1, vector);
			instance.setDataset(instances);
			luoClusterer.trainOnInstance(instance);
		}
	}
}
