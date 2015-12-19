package mayafey.grids.challenge.pbbg.submission;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Random;

import mayafey.grids.challenge.pbbg.ArcticViewer;
import mayafey.grids.challenge.pbbg.BattlegroundAnimal;
import mayafey.grids.challenge.pbbg.Carcass;
import mayafey.grids.challenge.pbbg.PolarBear;
import mayafey.grids.challenge.pbbg.PolarBearBrain;
import mayafey.grids.challenge.pbbg.Seal;
import mayafey.grids.challenge.pbbg.Walrus;
import mayafey.grids.data.GridObjectManager;
import mayafey.grids.data.GridReader;
import mayafey.grids.data.GridView;
import mayafey.grids.data.PositionGrid;

public class GridChallenge1 {
	
	private static final Class<?>[] competitors = new Class<?>[]
		{
			Seeker.class
		};
	
	private static final Class<?>[] args = new Class[0];

	@SuppressWarnings("unchecked")
	public static final void main(String[] z) throws Exception
	{
		/*
		 * Note to the casting god: I repent
		 */
		Constructor<PolarBearBrain>[] cons = (Constructor<PolarBearBrain>[]) new Constructor<?>[competitors.length];
		for(int i = 0; i < competitors.length; i++)
		{
			Class<?> clas = competitors[i];
			if(!PolarBearBrain.class.isAssignableFrom(clas))
				throw new java.lang.ExceptionInInitializerError("Contestant " + clas.getName() + " did not extend PolarBearBrain");
			Constructor<?> con;
			try {
				con = clas.getConstructor(args);
			} catch (NoSuchMethodException e) {
				System.err.println("Error attempting to load contestant " + clas.getName() + " main class:");
				throw new java.io.IOException("Contestant " + clas.getName()  + " main class does not have a valid constructor signature.");
			} 
			cons[i] = (Constructor<PolarBearBrain>) con;
		}
		/*
		 * 100 Bears, 100 Walrus, 100 Seals per competitor, 1 Animal per 10 squares:
		 */
		int size = ((int) Math.sqrt(competitors.length * 150)) + 1;
		int players = 15 * competitors.length;
		PositionGrid grid = new PositionGrid(size, true);
		GridObjectManager<BattlegroundAnimal> manager = new GridObjectManager<BattlegroundAnimal>(grid, new BattlegroundAnimal[players + 1]);
		GridReader reader = new GridReader(grid);
		Random rand = new Random(42);
		int pos = 0;
		int[] skills = new int[5];
		for(Constructor<PolarBearBrain> con : cons) 
		{
			for(int i = 0; i < 5; i++) 
			{
				manager.add(new Seal(reader, rand), (pos += 10) - rand.nextInt(10));
				manager.add(new Walrus(reader, rand), (pos += 10) - rand.nextInt(10));
				manager.add(new PolarBear(reader, rand, con.newInstance(), skills), (pos += 10) - rand.nextInt(10));
				Arrays.fill(skills, 0);
			}
		}
		GridView<String> view = new GridView<String>(new String[128]);
		ArcticViewer viewer = new ArcticViewer(grid, manager, rand);
		int c = 0;
		while(true)
		{
			if(c++ > 12)
				break;
			//*
			for(int i = 0; i < grid.getHeight(); i++) {
				for(int j = 0; j < grid.getWidth(); j++) {
					int ref = grid.get(j, i);
					if(ref == 0)
						System.out.print("[ ]");
					else
						System.out.print("[" + manager.getObj(ref).getType().toCharArray()[0] + "]");
				}
				System.out.println();
			}
			System.out.println();
			//*/
			boolean on = false;
			int j = 0;
			for(int i = 1; i <= players; i++) {
				BattlegroundAnimal obj = manager.getObj(i);
				if(obj.isAlive()) {
					int[] xy = manager.getObj(i).update();
					grid.getXY(manager.getPos(i), xy);
					j++;
					if(obj.getType() == "Bear")
						on |= true;
				} 
			}
			if(!on)
				break;
			//System.out.println(j);
			viewer.updateStorm();
			for(int i = 1; i <= players; i++)
			{
				BattlegroundAnimal player = manager.getObj(i);
				if(player.isAlive()) {
					viewer.view(view, i);
					int npos = grid.getMoveRel(manager.getPos(i), player.getMove(view), 1);
					int to = grid.get(npos);
					if(to == i)
						continue;
					if(to != 0) {
						BattlegroundAnimal opponent = manager.getObj(to);
						boolean fp = player.defend(opponent.getType());
						boolean fo = opponent.defend(player.getType());
						boolean battle = fp || fo;
						if(battle) {
							int sp = player.getHealth();
							int so = opponent.getHealth();
							while(player.isAlive() && opponent.isAlive()) {
								if(fp) {
									opponent.attack(player.getAttack());
									player.damage(opponent.getResistDamage());
								}
								if(fo) {
									player.attack(opponent.getAttack());
									opponent.damage(player.getResistDamage());
								}
							}
							if(player.isAlive()) {
								System.out.println("A " + opponent.getType() + " was killed by a " + player.getType() + ", losing " + (sp - player.getHealth()) + " HP");
								player.eat(opponent.getWeight());							
								manager.move(i, npos);		
							} else if(opponent.isAlive()) {
								System.out.println("A " + player.getType() + " was killed by a " + opponent.getType() + ", losing " + (so - opponent.getHealth()) + " HP");	
								opponent.eat(player.getWeight());
								manager.remove(i);
							} else {
								System.out.println("A " + opponent.getType() + " and a " + player.getType() + " both died in a fight.");
								Carcass carcass = new Carcass(reader, rand, player.getWeight() + opponent.getWeight());;
								manager.replace(carcass, to);
								manager.remove(i);
								player.setAlive(false);
							}	
						} else {
							System.out.println("A " + opponent.getType() + " and a " + player.getType() + " collided and died.");
							Carcass carcass = new Carcass(reader, rand, player.getWeight() + opponent.getWeight());;
							manager.replace(carcass, to);
							manager.remove(i);
							player.setAlive(false);
						}	
					} else 
						manager.move(i, npos);	
				}
			}
		}
	}

}
