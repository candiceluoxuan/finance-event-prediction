package com.luoxuan.finance_event_prediction;

import junit.framework.TestCase;
import finance_event_prediction.TrainingFileLoader;

public class TestTrainingFileLoader extends TestCase {

	TrainingFileLoader trainingFileLoader = new TrainingFileLoader();
	public void testTrainByTestNews() {
		trainingFileLoader.trainByTestNews("E:\\DATA");
	}
}
