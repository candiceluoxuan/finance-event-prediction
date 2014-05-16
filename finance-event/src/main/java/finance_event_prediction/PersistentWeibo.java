package finance_event_prediction;

import java.util.LinkedList;
import java.util.List;

public class PersistentWeibo {

	private String id;
	private String uid;
	private String content;
	private List<String> keywords = new LinkedList<>();
	private double[] vector;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public double[] getVector() {
		return vector;
	}

	public void setVector(double[] vector) {
		this.vector = vector;
	}

	public PersistentWeibo() {

	}

	public PersistentWeibo(String content, List<String> keywords,
			double[] vector) {
		this.content = content;
		this.keywords = keywords;
		this.vector = vector;
	}

}
