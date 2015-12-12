package mayafey.grids.challenge.pbbg;

import mayafey.grids.data.GridView;

public abstract class PolarBearBrain {

	protected PolarBearAccessObject access;
	
	public abstract boolean defendAgainst(String type);
	public abstract int move(GridView<String> view);
	
	/**
	 * 0 = Vision
	 * 1 = Resistance
	 * 2 = Attack
	 * 3 = Digestive Efficiency
	 * 4 = Respiratory Efficiency
	 */
	public abstract void getSkills(int[] arr, int weight);
	
	void setAccess(PolarBearAccessObject access)
	{
		this.access = access;
	}
}
