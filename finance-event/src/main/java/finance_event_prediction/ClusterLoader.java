package finance_event_prediction;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import moa.cluster.Clustering;

public class ClusterLoader {

	public static Clustering loadCluster(String path) {
		Clustering b = null;
		try (FileInputStream f = new FileInputStream(path)) {
			ObjectInputStream ois = new ObjectInputStream(f);
			b = (Clustering) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
}
