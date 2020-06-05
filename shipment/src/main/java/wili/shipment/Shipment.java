package wili.shipment;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * 
 * @author weila 2020年6月5日
 */
public class Shipment implements Shipment0 {
	public String id;

	double quant;

	Shipment0 parent, son;

	/**
	 * as merge result, new node will has more than one parent. 
	 */
	List<Shipment0> parents;

	List<Shipment0> sons;
	public String toString() {
		if (sons == null)
			return id;
		StringBuilder sb = new StringBuilder();
		sb.append(id + ":" + quant + "ton[");
		for (int i = 0; i < sons.size(); i++) {
			if (i > 0)
				sb.append(", ");
			Shipment0 men = sons.get(i);
			sb.append(men.id() + ":" + men.getQuantity());
		}
		sb.append(']');
		return sb.toString();
	}

	public Shipment(String id, double tonNum, Shipment0 dad) {
		if (id.isEmpty()) {
			this.id = String.valueOf(++GenID);
		} else {
			this.id = id;
		}
		this.quant = tonNum;
		this.parent = dad;
	}

	public Shipment(double tonNum, List<Shipment0> dads) {
		this.quant = tonNum;
		this.parents = dads;
		for (Shipment0 dad : dads) {
			Shipment dad2 = (Shipment) dad;
			dad2.clearSons();
			dad2.setSon(this);
		}
	}

	private void addParent(Shipment0 dad) {
		if (this.parent == null) {
			parents = new ArrayList<>();
		} else {
			if (parents.contains(dad))
				return;
		}
		parents.add(dad);
	}

	public List<Shipment0> split(double... tonNums) {
		Double sum = Comms.sum(tonNums);
		if (!sum.equals(quant)) {
			throw new IllegalArgumentException("sum quantity not equals.");
		}
		{
			this.sons = new ArrayList<>(tonNums.length);
			for (int i = 0; i < tonNums.length; i++) {
				Shipment ship = new Shipment("", tonNums[i], this);
				sons.add(ship);
			}
			return sons;
		}
	}

	void clearSons() {
		if (sons != null) {
			sons.clear();
		}
	}

	void setSon(Shipment son) {
		this.son = son;
		son.addParent(this);
	}

	@Override
	public void changeQuantity(double newQuant) {
		double percent = newQuant / quant;
		this.quant = newQuant;
		Set<Shipment0> skipSet = new HashSet<>();
		if (sons != null && sons.size() > 0) {
			for (Shipment0 son : sons) {
				((Shipment) son).changeQuantity00(true, percent, skipSet);
			}
			return;
		}
		this.changeQuantity00(false, percent, skipSet);
	}

	/**
	 * 
	 * @param toSons true: to sons(this.split result) | false: to son which has many parent except me.
	 * @param percent
	 * @param skipSet
	 */
	void changeQuantity00(boolean toSons, double percent, Set<Shipment0> skipSet) {
		if (skipSet.contains(this))
			return;
		skipSet.add(this);
		if (toSons) {
			double newQuant = percent * quant;
			this.quant = newQuant;
			if (sons != null && sons.size() > 0) {
				for (Shipment0 son : sons) {
					((Shipment) son).changeQuantity00(toSons, percent, skipSet);
				}
				return;
			}
		} else {
			if (son != null) {
				Double newSum = Comms.sum(((Shipment) son).parents);
				double percent2 = son.getQuantity() / newSum;
				Shipment son2 = (Shipment) son;
				son2.setQuantity(newSum);
				son2.changeQuantity00(true, percent2, skipSet);
				son2.changeQuantity00(false, percent2, skipSet);
			}
		}
	}

	@Override
	public String id() {
		return id;
	}

	@Override
	public double getQuantity() {
		return quant;
	}

	@Override
	public void setQuantity(double quant) {
		this.quant = quant;
	}
	static long GenID;
}
