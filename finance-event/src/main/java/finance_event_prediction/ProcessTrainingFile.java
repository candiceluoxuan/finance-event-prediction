package finance_event_prediction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.candice.finance_event.utilities.UnicodeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProcessTrainingFile extends TrainingFileLoader {

	Logger logger = LoggerFactory.getLogger(ProcessTrainingFile.class);
	
	Pattern textPattern = Pattern.compile("\"text\": \"(.*?)\"");
	
	@Override
	protected void processFile(File inputFile){
		try (BufferedReader input = new BufferedReader(new InputStreamReader(
				new FileInputStream(inputFile), "gbk"))) {

			String text;
			while ((text = input.readLine()) != null) {
				//logger.debug("{}", text);
				Matcher matcher = textPattern.matcher(text);
				if (matcher != null && matcher.find()) {
					String content = matcher.group(1);
//					content = content.replaceAll("\\\\", "%");					
					String content_utf8 = UnicodeHelper.toUTF8(content);
					logger.debug("{}", content_utf8);
				}
			}
		} catch (IOException ioException) {
			System.err.println("File Error!");
		} finally {
			System.out.println("Mission complete!");
		}
		
	}
	
}
