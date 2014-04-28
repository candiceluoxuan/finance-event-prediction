package org.candice.finance_event.utilities;


import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UnicodeHelper {

	/*public static String toUTF8(String unicode) {
		try {
			// Convert from Unicode to UTF-8
			byte[] utf8 = unicode.getBytes("UTF-8");
			// Convert from UTF-8 to Unicode
			String utf8String = new String(utf8, "UTF-8");
			return utf8String;
		} catch (UnsupportedEncodingException e) {
		}
		return "error";
	}*/
	
	
	public static String toUTF8(String unicode) {  
		 
	    final StringBuffer buffer = new StringBuffer();  
	    Pattern p = Pattern.compile("\\\\u([\\S]{4})([^\\\\]*)");
	    Matcher match=p.matcher(unicode);
	    while(match.find())
	    {
	    	char letter = (char) Integer.parseInt(match.group(1), 16);
	    	buffer.append(new Character(letter).toString());
	    	buffer.append(match.group(2));
	    }

	    return buffer.toString();  
	} 

}