package mayafey.grids.challenge.pbbg;

import java.util.Random;

import mayafey.grids.data.GridReader;
import mayafey.grids.data.GridView;

public class Walrus extends BattlegroundAnimal {

	public Walrus(GridReader reader, Random rand) {
		super(reader, rand);
		this.vision = 0;
		this.resistance = 20 + rand.nextInt(21);
		this.weight = 400;
	}

	public boolean defend(String animal)
	{
		return true;
	}

	public void tick() 
	{
		this.regenerate(5);
	}

	public int getAttack()
	{
		return 0;
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
		this.weight += food / 2; 
	}

}
