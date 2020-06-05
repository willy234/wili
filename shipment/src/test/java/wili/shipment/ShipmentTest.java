package wili.shipment;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import junit.framework.Assert;
public class ShipmentTest {
	static class SplitItem {
		public SplitItem(String id, double quantity, double... nn) {
			super();
			this.id = id;
			this.quantity = quantity;
			this.splitResult = nn;
		}
		String id;

		double quantity;

		double[] splitResult;
	}
	@Test
	public void merge_test() {
		SplitItem[] test = { new SplitItem("s1", 100, 20, 30, 40, 10) };
		for (int i = 0; i < test.length; i++) {
			SplitItem item = test[i];
			Shipment ment = new Shipment(item.id, item.quantity, null);
			List<Shipment0> lis = ment.split(item.splitResult);
			if (!Comms.sum(lis).equals(ment.quant)) {
				Assert.assertTrue(false);
			}
			{//test merge begin...
				Shipment0 men1 = lis.get(1), men2 = lis.get(2);
				List<Shipment0> lis2 = Arrays.asList(men1, men2);
				Shipment0 ret = Shipment0.mergeShipments(lis2);
				Double expect = Comms.sum(lis2);
				Assert.assertEquals(expect, ret.getQuantity());
			}
		}
	}

	@Test
	public void split_test() {
		SplitItem[] test = { new SplitItem("s1", 100, 20, 30, 50), new SplitItem("s2", 100, 20, 30, 50) };
		for (int i = 0; i < test.length; i++) {
			SplitItem item = test[i];
			Shipment ment = new Shipment(item.id, item.quantity, null);
			List<Shipment0> lis = ment.split(item.splitResult);
			Double expect = Comms.sum(lis);
			Assert.assertEquals(expect, ment.getQuantity());
		}
	}

	@Test
	public void changeQuantity_test() {
		SplitItem[] test = { new SplitItem("s1", 100, 20, 30, 50) };
		for (int i = 0; i < test.length; i++) {
			SplitItem item = test[i];
			Shipment ment = new Shipment(item.id, item.quantity, null);
			List<Shipment0> lis = ment.split(item.splitResult);
			Double expect = Comms.sum(lis);
			Assert.assertEquals(expect, ment.getQuantity());
			{//test chang quantity begin...
				double newQuant = ment.getQuantity() * 2;
				ment.changeQuantity(newQuant);
				Double expect2 = Comms.sum(lis);
				Assert.assertEquals(expect2, ment.getQuantity());
			}
		}
	}

	@Test
	public void complete_test() {
		//todo...
	}
}
