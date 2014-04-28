package finance_event_prediction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ansj.domain.Term;
import org.candice.finance_event.utilities.UnicodeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProcessTrainingFile extends TrainingFileLoader {

	Logger logger = LoggerFactory.getLogger(ProcessTrainingFile.class);
	
	private String outputPath = "E:\\IndependentProject\\text.txt";
	Pattern textPattern = Pattern.compile("\"text\": \"(.*?)\"");
	
	@Override
	protected void processFile(File inputFile){
		
		File outputFile = new File(outputPath);

		try (BufferedReader input = new BufferedReader(new InputStreamReader(
				new FileInputStream(inputFile), "gbk"));
				PrintWriter out = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(
								new FileOutputStream(outputFile), "UTF-8")))) {

			String text;
			while ((text = input.readLine()) != null) {
				//logger.debug("{}", text);
				Matcher matcher = textPattern.matcher(text);
				while (matcher != null && matcher.find()) {
					String content = matcher.group(1);
//					content = content.replaceAll("\\\\", "%");					
					String content_utf8 = UnicodeHelper.toUTF8(content);
//					logger.debug("{}", content_utf8);
					
//					split(content_utf8);
					for (Term term : split(content_utf8)) {
						String word = term.getName();
						out.print(word);
						out.print(" ");
					}
					out.println();

				}
			}
		} catch (IOException ioException) {
			System.err.println("File Error!");
		} finally {
			System.out.println("Mission complete!");
		}
		
	}
	
	protected List<Term> split(String words) {
		return null;
	}
}
