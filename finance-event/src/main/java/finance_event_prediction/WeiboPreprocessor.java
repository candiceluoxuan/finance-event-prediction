package finance_event_prediction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import finance_event_prediction_utilities.UnicodeHelper;
import finance_event_prediction_word2vec.VectorCalculator;
import finance_event_prediction_word2vec.Word2VEC;

public class WeiboPreprocessor extends TrainingFileLoader {

	Logger logger = LoggerFactory.getLogger(WeiboPreprocessor.class);

	private String vectorPath = "E:\\IndependentProject\\vectors.bin";
	private String outputFolder = "E:\\IndependentProject\\";
	Pattern textPattern = Pattern.compile("\"text\": \"(.*?)\"");
	Pattern idPattern = Pattern.compile("^(.*?)\t(.*?)\t");

	KeyWordComputer kwc;
	private int keywordCount = 3;

	VectorCalculator vectorCalculator;

	public WeiboPreprocessor() {
		initAnalyzer();
		initVectorCalculator();
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

	@Override
	protected void processFile(File inputFile) {
//		logger.debug("file name is {}", inputFile.getName());

		File outputFile = new File(outputFolder + inputFile.getName());

		try (BufferedReader input = new BufferedReader(new InputStreamReader(
				new FileInputStream(inputFile), "gbk"));
				PrintWriter out = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(outputFile,
								true), "UTF-8")))) {

			String text;
			while ((text = input.readLine()) != null) {
				PersistentWeibo weibo = new PersistentWeibo();

				Matcher idMatcher = idPattern.matcher(text);
				if (idMatcher.find()) {
					weibo.setUid(idMatcher.group(1));
					weibo.setId(idMatcher.group(2));
					// logger.debug("uid is {}", idMatcher.group(1));
					// logger.debug("id is {}", idMatcher.group(2));
				}

				Matcher textMatcher = textPattern.matcher(text);
				while (textMatcher != null && textMatcher.find()) {
					String content = textMatcher.group(1);
					String content_utf8 = UnicodeHelper.toUTF8(content);
					// logger.debug("{}", content_utf8);

					// content
					weibo.setContent(content_utf8);
					// Keywords
					Collection<Keyword> keywords = kwc
							.computeArticleTfidf(content_utf8);
					for (Keyword keyword : keywords) {
						weibo.getKeywords().add(keyword.getName());
					}

					// vector
					weibo.setVector(vectorCalculator.getWordsVector(weibo
							.getKeywords()));
					
					out.println(JSON.toJSONString(weibo));

				}
			}
		} catch (IOException ioException) {
			System.err.println("File Error!");
		} finally {
			System.out.println("Mission complete!");
		}

	}
}
