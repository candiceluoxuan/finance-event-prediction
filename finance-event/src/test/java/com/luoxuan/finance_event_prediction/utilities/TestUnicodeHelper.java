package com.luoxuan.finance_event_prediction.utilities;

import org.candice.finance_event.utilities.UnicodeHelper;

import junit.framework.TestCase;

public class TestUnicodeHelper extends TestCase {

	public void testdecodeUnicode() {
		
		//System.out.println(UnicodeHelper.toUTF8("#\u7b7e\u5355\u5206\u4eab#\u3010\u54c8\u54c8\u53c8\u7b7e\u5355\u4e86"));
		System.out.println(UnicodeHelper.toUTF8_bak("\u53cc\u6c47\u6536\u8d2d\u53f2\u5bc6\u65af\u83f2\u5c14\u5fb7\uff08SFD\uff09\u878d\u8d44\u5b89\u6392\uff0c\u6536\u8d2d\u4ef7\u6b3e\u542b\u627f\u503a\u517171\u4ebf\u7f8e\u5143\uff1a\u4e2d\u56fd\u94f6\u884c\u7275\u5934\u7ec4\u7ec740\u4ebf\u7f8e"));
	}

}
