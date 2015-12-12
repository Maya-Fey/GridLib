package mayafey.grids.challenge.pbbg;

public class PolarBearAccessObject {
	
	private final PolarBear bear;

	public PolarBearAccessObject(PolarBear bear) 
	{
		this.bear = bear;
	}
	
	public int[] getPos()
	{
		return bear.getPosition();
	}
	
	public int getX()
	{
		return bear.getX();
	}
	
	public int getY()
	{
		return bear.getY();
	}
	
	public int getHealth()
	{
		return this.getHealth();
	}
	
	public int getMaxHealth()
	{
		return this.getMaxHealth();
	}
	
	public int getWeight()
	{
		return bear.getWeight();
	}
	
	public int getVision()
	{
		return bear.getVision();
	}
	
	public int getResistance()
	{
		return bear.getResistance();
	}
	
	public int getAttack()
	{
		return bear.getAttack();
	}
	
	public int getDigestiveEfficiency()
	{
		return bear.getDigestiveEfficiency();
	}
	
	public int getRespiratoryEfficiency()
	{
		return bear.getRespiratoryEfficiency();
	}

	public int distanceTo(int x, int y)
	{
		return bear.distanceFrom(x, y);
	}
	
	public int directionTo(int x, int y)
	{
		return bear.directionTo(x, y);
	}
}
