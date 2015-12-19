package mayafey.grids.challenge.pbbg;

import java.util.Random;

import mayafey.grids.data.GridReader;
import mayafey.grids.data.GridView;

public class Carcass extends BattlegroundAnimal {

	public Carcass(GridReader reader, Random rand, int weight, int team) {
		super(reader, rand, team);
		this.resistance = 0;
		this.vision = 0;
		this.weight = weight;
		this.health = 1;
		this.setAlive(true);
	}

	public boolean defend(String animal)
	{
		return false;
	}

	public void tick() {}

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
		return "Carcass";
	}

	public void eat(int food) {}

}
