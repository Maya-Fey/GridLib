package mayafey.grids.challenge.pbbg;

import java.util.Random;

import mayafey.grids.data.GridReader;
import mayafey.grids.data.GridView;

public class PolarBear extends BattlegroundAnimal {
	
	protected int digeff = 0,
	 			  respeff = 0,
	 			  attack = 0,
	 			  eaten = 0;
	
	private final PolarBearBrain brain;

	public PolarBear(GridReader reader, Random rand, PolarBearBrain brain, int[] skills) {
		super(reader, rand);
		this.weight = 200 + rand.nextInt(801);
		this.brain = brain;
		brain.getSkills(skills, weight);
		int total = 0;
		boolean pos = true;
		for(int i : skills)
			if(i >= 0)
				total += i;
			else {
				pos = false;
				break;
			}
		if(pos && total < 50) {
			this.vision = skills[0];
			this.resistance = skills[1];
			this.attack = skills[2];
			this.digeff = skills[3];
			this.respeff = skills[4];
		}
			
	}

	public void tick()
	{
		this.heal(5);
		if(eaten > 0) {
			this.heal(random.nextInt(6));
			eaten--;
		}
		int sub = 10 + random.nextInt(11);
		sub *= 100 - respeff;
		sub /= 100;
		this.weight -= sub;
		brain.tick();
	}

	public int attack()
	{
		return attack * 5 + (weight / 4);
	}
	
	public boolean defend(String animal)
	{
		return brain.defendAgainst(animal);
	}

	public int getMove(GridView<String> view)
	{
		return brain.move(view);
	}
	
	public String getType()
	{
		return "Bear";
	}
	
	public void eat(int food)
	{
		eaten += random.nextInt(respeff);
		food *= random.nextInt(51) + digeff;
		food /= 100;
		this.weight += food;
	}
	
	public int getAttack()
	{
		return this.attack;
	}
	
	public int getDigestiveEfficiency()
	{
		return this.digeff;
	}
	
	public int getRespiratoryEfficiency()
	{
		return this.respeff;
	}

}
