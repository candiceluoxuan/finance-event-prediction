package com.luoxuan.finance_event_prediction.utilities;

import org.candice.finance_event.utilities.UnicodeHelper;

import junit.framework.TestCase;

public class TestUnicodeHelper extends TestCase {

	public void testdecodeUnicode() {
		assertEquals("【女职工超50岁",
				UnicodeHelper.toUTF8("\u3010\u5973\u804c\u5de5\u8d8550\u5c81"));
		
		System.out.println(UnicodeHelper.toUTF8("#\u7b7e\u5355\u5206\u4eab#\u3010\u54c8\u54c8\u53c8\u7b7e\u5355\u4e86"));
	}

}
