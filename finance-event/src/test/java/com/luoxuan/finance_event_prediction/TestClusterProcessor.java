package com.luoxuan.finance_event_prediction;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import finance_event_prediction.ClusterProcessor;
import finance_event_prediction.LUOClusterer;

public class TestClusterProcessor {

	ClusterProcessor clusterProcessor = new ClusterProcessor();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testProcessFile() {
		clusterProcessor.trainByTestNews("E:\\DATA");
	}

	@Test
	public void testTrain() {
		
	}

}
