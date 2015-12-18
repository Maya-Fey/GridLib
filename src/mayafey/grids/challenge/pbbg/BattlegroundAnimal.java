package mayafey.grids.challenge.pbbg;

import java.util.Random;

import mayafey.grids.data.GridReader;
import mayafey.grids.data.GridView;

public abstract class BattlegroundAnimal extends Animal {
	
	protected int[] pos = new int[2];
	
	protected int resistance;
	protected int vision;
	protected int weight;
	
	protected final Random random;
	protected final GridReader reader;
	
	public BattlegroundAnimal(GridReader reader, Random rand)
	{
		this.reader = reader;
		this.random = rand;
	}
	
	public abstract boolean defend(String animal);
	public abstract void tick();
	public abstract int getAttack();
	public abstract int getMove(GridView<String> view);
	public abstract void eat(int food);
	
	public void update(int x, int y)
	{
		pos[0] = x;
		pos[1] = y;
		this.tick();
	}
	
	public int[] update()
	{
		this.tick();
		return pos;
	}
	
	public void attack(int damage)
	{
		damage *= 50 + random.nextInt(101);;
		damage /= 100;
		damage *= 100 - resistance;
		damage /= 100;
		damage(damage);
	}
	
	public int getWeight()
	{
		return this.weight;
	}
	
	public int getResistance()
	{
		return this.resistance;
	}
	
	public int getVision()
	{
		return this.vision;
	}
	
	public Random getRandom()
	{
		return this.random;
	}
	
	public int[] getPosition()
	{
		return this.pos;
	}
	
	public int getX()
	{
		return pos[0];
	}
	
	public int getY()
	{
		return pos[1];
	}
	
	public int distanceFrom(int x, int y)
	{
		return reader.distanceFrom(pos[0], x, pos[1], y);
	}
	
	public int directionTo(int x, int y)
	{
		return reader.getDirection(pos[0], x, pos[1], y);
	}
	
	public int getMaxHealth()
	{
		return this.weight / 2;
	}
	
	public int getResistDamage()
	{
		return resistance != 0 ? random.nextInt(resistance + 10) : 0;
	}

	public void setAlive(boolean b)
	{
		System.out.println("This " + this.getType() + " is being set " + (b ? "Alive" : "Dead") + " with  " + this.health + "/" + this.getMaxHealth() + " HP");
		super.setAlive(b);
	}
}
