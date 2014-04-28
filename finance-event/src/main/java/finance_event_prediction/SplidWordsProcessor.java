package finance_event_prediction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.BaseAnalysis;
import org.ansj.util.FilterModifWord;

public class SplidWordsProcessor extends ProcessTrainingFile {

	public SplidWordsProcessor(){
		super();
		initializeStopWord();
	}
	
	private void initializeStopWord() {
		try (BufferedReader input = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File("library/HIT_stopword.dic")),
				"gbk"))) {
			String text;
			while ((text = input.readLine()) != null) {
				FilterModifWord.insertStopWord(text);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	@Override
	protected List<Term> split(String words) {
		List<Term> parse = BaseAnalysis.parse(words);
//	    System.out.println(parse);
		return FilterModifWord.modifResult(parse);
	}
}
