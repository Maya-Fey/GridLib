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
	 * <ul>
	 * <li>0 = Vision</li>
	 * <li>1 = Resistance</li>
	 * <li>2 = Attack</li>
	 * <li>3 = Digestive Efficiency</li>
	 * <li>4 = Respiratory Efficiency</li>
	 * </ul><br>
	 * 
	 * <b>Vision</b> provides you the tactical information each round. This comes
	 * as a list of animals in the radius of your animal. Increased vision increases
	 * the radius and increases the probability of determining the animal type. Default
	 * radius is 0 - 2 squares in all directions and default probability is 0% of 
	 * determining animal type.<br>
	 * <b>Resistance</b> decreases damage dealt from enemy animals by -1%, and increases 
	 * defensive damage dealt to them by on average 0.5 points.<br>
	 * <b>Attack</b> increases the amount of damage you do on top of the weight damage.
	 * An increase in attack provides a flat increase in damage plus a +1% modifier on
	 * the weight damage.<br>
	 * <b>Digestive Efficiency</b> increases the amount of weight gained by eating food.
	 * Each additional point gives 1% of the weight from food. Default efficiency is 
	 * 20% - 50%<br>
	 * <b>Respiratory Efficiency</b> decreases the amount of weight lost to attrition
	 * each tick of the game and increases regeneration. Each point reduces attrition by
	 * -1% and every five points increases regeneration by 1 point per round.
	 */
	public abstract void getSkills(int[] arr, int weight);
	public abstract void tick();
	
	final void setAccess(PolarBearAccessObject access)
	{
		this.access = access;
	}
	
	protected final PolarBearAccessObject getAccess()
	{
		return this.access;
	}
}
