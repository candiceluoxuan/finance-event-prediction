package finance_event_prediction;

import java.util.List;

import moa.cluster.Cluster;
import weka.core.Instance;

public class ClusteredWeibo extends PersistentWeibo {

	private Instance instance;
	private Cluster cluster;

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

	public ClusteredWeibo() {
		super();
	}

	public ClusteredWeibo(String content, List<String> keywords, double[] vector) {
		super(content, keywords, vector);
	}

	@Override
	public String toString() {
		StringBuilder sBuilder = new StringBuilder();
		for (String keyword : getKeywords()) {
			sBuilder.append(keyword);
			sBuilder.append(", ");
		}
		sBuilder.append(getContent());

		return sBuilder.toString();
	}
}
