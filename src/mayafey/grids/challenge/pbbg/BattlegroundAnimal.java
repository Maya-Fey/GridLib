package mayafey.grids.challenge.pbbg;

import java.util.Random;

public abstract class BattlegroundAnimal extends Animal {
	
	protected int resistance;
	protected int vision;
	protected int weight;
	
	protected Random random;
	
	public BattlegroundAnimal(Random rand)
	{
		this.random = rand;
	}
	
	public void damage(int damage)
	{
		damage *= 100;
		damage /= 50 + random.nextInt(151);
		damage *= 100 - resistance;
		damage /= 100;
		this.health -= damage;
	}
	
	public int getWeight()
	{
		return this.weight;
	}
	
	public int getResistance()
	{
		return this.resistance;
	}
	
	public final int getVision()
	{
		return this.vision;
	}
	
	public Random getRandom()
	{
		return this.random;
	}

}
