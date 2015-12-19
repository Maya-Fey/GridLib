package mayafey.grids.challenge.pbbg;

import java.util.Random;

import mayafey.grids.data.GridReader;
import mayafey.grids.data.GridView;

public class Walrus extends BattlegroundAnimal {

	public Walrus(GridReader reader, Random rand, int team) {
		super(reader, rand, team);
		this.vision = 0;
		this.resistance = 10 + rand.nextInt(41);
		this.weight = 400;
		this.health = this.getMaxHealth();
	}

	public boolean defend(String animal)
	{
		return true;
	}

	public void tick() 
	{
		this.regenerate(5);
		if(this.weight > 400)
			if(this.weight - 10 < 400)
				this.weight = 400;
			else
				this.weight -= 10;
	}

	public int getAttack()
	{
		return 10;
	}

	public int getMove(GridView<String> view)
	{
		return 0;
	}

	public String getType()
	{
		return "Walrus";
	}

	public void eat(int food)
	{
		this.weight += (food * 80) / 100; 
	}
	
	public int getMaxHealth()
	{
		return this.weight + (this.resistance * 2);
	}

}
