package wili.shipment;
import java.util.List;
/**
 * 
 * @author weila 2020年6月5日
 */
public class Comms {
	public static double sum(double[] dd) {
		double d = dd[0];
		for (int i = 1; i < dd.length; i++) {
			d += dd[i];
		}
		return d;
	}

	public static Double sum(List<Shipment0> lis) {
		double ret = lis.get(0).getQuantity();
		for (int i = 1; i < lis.size(); i++) {
			ret += lis.get(i).getQuantity();
		}
		return ret;
	}
}
