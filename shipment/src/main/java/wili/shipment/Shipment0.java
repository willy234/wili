package wili.shipment;
import java.util.List;
import java.util.Set;
/**
 * 
 * @author weila 2020年6月5日
 */
public interface Shipment0 {
	String id();

	double getQuantity();

	List<Shipment0> split(double... tonNums);

	void changeQuantity(double tonNum);

	void setQuantity(double quant);

	//Shipment0 mergeShipments(List<Shipment0> parents);
	public static Shipment0 mergeShipments(List<Shipment0> parents) {
		double sum = Comms.sum(parents);
		Shipment0 ret = new Shipment(sum, parents);
		return ret;
	}
}
