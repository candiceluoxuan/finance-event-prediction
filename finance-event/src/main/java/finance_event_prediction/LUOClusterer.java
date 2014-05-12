package finance_event_prediction;

import moa.clusterers.denstream.WithDBSCAN;

public class LUOClusterer extends WithDBSCAN {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2295264541998796791L;

	@Override
	protected double distance(double[] pointA, double[] pointB) {
		double distance = 0.0;
		for (int i = 0; i < pointA.length; i++) {
			distance += pointA[i] * pointB[i];
		}
//		System.out.println(1 - distance);
		return 1 - distance;
	}

}
