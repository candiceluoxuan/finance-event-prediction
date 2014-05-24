package com.luoxuan.finance_event_prediction;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import finance_event_prediction.ClusterFromPreparedData;

public class TestClusterFromPreparedData {

	ClusterFromPreparedData clusterFromPreparedData = new ClusterFromPreparedData();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void test() {
		clusterFromPreparedData.execute("E:\\keyworddata");
	}

}
