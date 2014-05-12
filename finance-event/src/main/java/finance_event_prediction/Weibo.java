package finance_event_prediction;

import java.util.LinkedList;
import java.util.List;

import moa.cluster.Cluster;

import weka.core.Instance;

public class Weibo {

	private String content;
	private Instance instance;
	private Cluster cluster;
	private List<String> keywords = new LinkedList<>();

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Instance getInstance() {
		return instance;
	}

	public void setInstance(Instance instance) {
		this.instance = instance;
	}

	public Cluster getCluster() {
		return cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public Weibo() {

	}

	public Weibo(String wContent) {
		content = wContent;
	}

	public Weibo(String wContent, List<String> wKeywords) {
		content = wContent;
		keywords = wKeywords;
	}
	
	@Override
	public String toString(){
		StringBuilder sBuilder = new StringBuilder();
		for (String keyword : keywords) {
			sBuilder.append(keyword);
			sBuilder.append(", ");
		}
		sBuilder.append(content);
		
		return sBuilder.toString();
	}
}
