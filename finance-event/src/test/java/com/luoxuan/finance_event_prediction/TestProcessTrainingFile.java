package com.luoxuan.finance_event_prediction;

import junit.framework.TestCase;
import finance_event_prediction.ProcessTrainingFile;

public class TestProcessTrainingFile extends TestCase {

	ProcessTrainingFile processTrainingFile = new ProcessTrainingFile();
	public void testProcessFile() {
		processTrainingFile.trainByTestNews("E:\\DATA");
		
	}

}
