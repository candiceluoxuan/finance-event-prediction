package com.luoxuan.finance_event_prediction;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import finance_event_prediction.WeiboPreprocessor;

public class TestWeiboPreprocessor {

	WeiboPreprocessor weiboPreprocessor = new WeiboPreprocessor();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testProcessFile() {
		weiboPreprocessor.trainByTestNews("E:\\DATA");
	}

}
