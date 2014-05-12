package finance_event_prediction_word2vec;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import java_cup.version;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import finance_event_prediction.ProcessTrainingFile;

public class TestVectorCalculator {

	Logger logger = LoggerFactory.getLogger(TestVectorCalculator.class);
	
	static VectorCalculator vectorCalculator;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		vectorCalculator = new Word2VEC("E:\\IndependentProject\\vectors.bin");
	}

	@Test
	public void testDistanceDoubleArrayDoubleArray() {
//		fail("Not yet implemented");
	}

	@Test
	public void testDistanceString() {
		logger.debug("{}", vectorCalculator.distance("习近平"));
	}

	@Test
	public void testDistanceListOfString() {
		List<String> wordsList = new LinkedList<>();
		wordsList.add("银行");
		wordsList.add("习近平");
		logger.debug("{}", vectorCalculator.distance(wordsList));
	}

	@Test
	public void testGetWordVector() {
		logger.debug("{}", vectorCalculator.getWordVector("习近平"));
	}

	@Test
	public void testGetWordsVector() {
		List<String> wordsList = new LinkedList<>();
		wordsList.add("银行");
		wordsList.add("习近平");
		logger.debug("{}", vectorCalculator.getWordsVector(wordsList));
	}

}
