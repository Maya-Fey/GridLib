package mayafey.grids.challenge.pbbg;

import java.util.Random;

import mayafey.grids.data.GridReader;
import mayafey.grids.data.GridView;

public class Carcass extends BattlegroundAnimal {

	public Carcass(GridReader reader, Random rand, int weight) {
		super(reader, rand);
		this.resistance = 0;
		this.vision = 0;
		this.weight = weight;
		this.health = 0;
		this.setAlive(false);
	}

	public boolean defend(String animal)
	{
		return false;
	}

	public void tick() {}

	public int attack()
	{
		return 0;
	}

	public int getMove(GridView<String> view)
	{
		return 0;
	}

	public String getType()
	{
		return "Carcass";
	}

}
