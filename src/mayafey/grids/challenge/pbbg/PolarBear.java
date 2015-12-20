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
	
	public PolarBear(GridReader reader, Random rand, PolarBearBrain brain, int[] skills, int team) {
		super(reader, rand, team);
		this.weight = 200 + rand.nextInt(801);
		this.brain = brain;
		this.health = this.getMaxHealth();
		brain.setAccess(new PolarBearAccessObject(this));
		brain.getSkills(skills, weight);
		long total = 0;
		boolean pos = true;
		for(int i : skills)
			if(i >= 0)
				total += i;
			else {
				pos = false;
				break;
			}
		if(pos && total <= 50) {
			this.vision = skills[0];
			this.resistance = skills[1];
			this.attack = skills[2];
			this.digeff = skills[3];
			this.respeff = skills[4];
		} 
	}

	public void tick()
	{
		int sub = 5 + random.nextInt(7);
		if(respeff > 0) {
			sub *= 100 - (respeff + 1);
			sub /= 100;
		}
		this.weight -= sub;
		if(this.weight < 50) {
			this.setAlive(false);
			return;
		}
		this.regenerate(5 + (respeff / 2));
		if(eaten > 0) {
			this.regenerate(random.nextInt(6));
			eaten--;
		}
		brain.tick();
	}

	public int getAttack()
	{
		int wdmg = weight / 8;
		wdmg *= 100 + attack;
		wdmg /= 100;
		return attack * 2 + wdmg;
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
	
	public char getTypeChar()
	{
		return 'B';
	}
	
	public void eat(int food)
	{
		eaten += 1 + (respeff > 1 ? random.nextInt(respeff / 2) : 0);
		food *= 20 + random.nextInt(31) + digeff;
		food /= 100;
		this.regenerate(food / 20);
		this.weight += food;
	}
	
	public int getAttackSkill()
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
	
	public int getMaxHealth()
	{
		return this.weight / 2 + this.resistance;
	}
	
}
