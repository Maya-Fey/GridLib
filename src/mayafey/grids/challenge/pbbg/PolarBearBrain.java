package mayafey.grids.challenge.pbbg;

import mayafey.grids.data.GridView;

public abstract class PolarBearBrain {
	
	public static final int VISION = 0;
	public static final int RESISTANCE = 1;
	public static final int ATTACK = 2;
	public static final int DIGEFF = 3;
	public static final int RESPEFF = 4;

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
	public abstract void tick();
	
	void setAccess(PolarBearAccessObject access)
	{
		this.access = access;
	}
}
