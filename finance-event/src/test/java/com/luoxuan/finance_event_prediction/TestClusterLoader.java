package com.luoxuan.finance_event_prediction;

import static org.junit.Assert.*;

import moa.cluster.Cluster;
import moa.cluster.Clustering;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import finance_event_prediction.ClusterLoader;
import finance_event_prediction.ClusterProcessor;
import finance_event_prediction.LUOClusterer;

public class TestClusterLoader {

	Logger logger = LoggerFactory.getLogger(TestClusterLoader.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testLoadCluster() {
		LUOClusterer clustering = ClusterLoader
				.loadCluster("E:\\IndependentProject\\cluster.bin");
		for (Cluster cluster : clustering.getClusteringResult().getClustering()) {
			logger.debug("{}", cluster.getInfo());
		}
	}

}
