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
	
	public static final int BATTLESIZE = 1000;
	
	private static final Class<?>[] competitors = new Class<?>[]
		{
			Seeker.class,
			Hivemind.class
		};
	
	private static final String[] teams = new String[] 
		{
			"Klingon Search and Destroy Team",
			"The Bugger Queen",
			"Walrus King"
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
		 * BATTLESIZE Bears, Walrus, and Seals per competitor, 1 Animal per 10 squares:
		 */
		int size = ((int) Math.sqrt(competitors.length * 30 * BATTLESIZE)) + 1;
		int players = 3 * BATTLESIZE * competitors.length;
		PositionGrid grid = new PositionGrid(size, true);
		GridObjectManager<BattlegroundAnimal> manager = new GridObjectManager<BattlegroundAnimal>(grid, new BattlegroundAnimal[players + 1]);
		GridReader reader = new GridReader(grid);
		Random rand = new Random(42);
		int pos = 0;
		long[] scores = new long[teams.length];
		int[] skills = new int[5];
		for(int i = 0; i < BATTLESIZE; i++) 
		{
			for(int j = 0; j < cons.length; j++) 
			{
				manager.add(new Seal(reader, rand, competitors.length), (pos += 10) - rand.nextInt(10));
				manager.add(new Walrus(reader, rand, competitors.length), (pos += 10) - rand.nextInt(10));
				manager.add(new PolarBear(reader, rand, cons[j].newInstance(), skills, j), (pos += 10) - rand.nextInt(10));
				Arrays.fill(skills, 0);
			}
		}
		GridView<String> view = new GridView<String>(new String[172]);
		ArcticViewer viewer = new ArcticViewer(grid, manager, rand);
		int c = 0;
		while(true)
		{
			if(c++ > 10000)
				break;
			
			boolean on = false;
			for(int i = 1; i <= players; i++) {
				BattlegroundAnimal obj = manager.getObj(i);
				if(obj.isAlive()) {
					scores[obj.getTeam()] += obj.getWeight();
					grid.getXY(manager.getPos(i), obj.update());
					obj.tick();
					if(obj.getType() == "Bear")
						on |= true;
				} 
			}
			if(!on)
				break;
			
			/*
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
			
			viewer.updateStorm();
			for(int i = 1; i <= players; i++)
			{
				BattlegroundAnimal player = manager.getObj(i);
				if(player.isAlive()) {
					viewer.view(view, i);
					int move;
					try {
						move = player.getMove(view);
					} catch(Exception e) {
						System.out.println("Oh no.");
						Carcass carcass = new Carcass(reader, rand, player.getWeight(), competitors.length);
						manager.replace(carcass, i);
						continue;
					}
					if(move == 0)
						continue;
					int npos = grid.getMoveRel(manager.getPos(i), move, 1);
					int to = grid.get(npos);
					if(to != 0) {
						BattlegroundAnimal opponent = manager.getObj(to);
						boolean fp = player.defend(opponent.getType());
						boolean fo = opponent.defend(player.getType());
						boolean battle = fp || fo;
						if(battle) {
							//int sp = player.getHealth();
							//int so = opponent.getHealth();
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
								//System.out.println("A " + opponent.getType() + " was killed by a " + player.getType() + ", losing " + (sp - player.getHealth()) + " HP");
								player.eat(opponent.getWeight());							
								manager.move(i, npos);		
							} else if(opponent.isAlive()) {
								//System.out.println("A " + player.getType() + " was killed by a " + opponent.getType() + ", losing " + (so - opponent.getHealth()) + " HP");	
								opponent.eat(player.getWeight());
								manager.remove(i);
							} else {
								//System.out.println("A " + opponent.getType() + " and a " + player.getType() + " both died in a fight.");
								Carcass carcass = new Carcass(reader, rand, player.getWeight() + opponent.getWeight(), competitors.length);
								manager.replace(carcass, to);
								manager.remove(i);
								player.setAlive(false);
							}	
						} else {
							//System.out.println("A " + opponent.getType() + " and a " + player.getType() + " collided and died.");
							Carcass carcass = new Carcass(reader, rand, player.getWeight() + opponent.getWeight(), competitors.length);;
							manager.replace(carcass, to);
							manager.remove(i);
							player.setAlive(false);
						}	
					} else 
						manager.move(i, npos);	
				}
			}
			/*System.out.println("End of round " + c + ".");
			for(int i = 0; i < teams.length; i++)
				System.out.println("The " + teams[i] + " has " + scores[i] + " points.");
			System.out.println("=============================");*/
		}
		
		for(int i = 0; i < grid.getHeight(); i++) {
			for(int j = 0; j < grid.getWidth(); j++) {
				int ref = grid.get(j, i);
				if(ref == 0)
					System.out.print("[ ]");
				else
					System.out.print("[" + manager.getObj(ref).getTypeChar() + "]");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println(c);
		int seal = 0;
		int walrus = 0;
		int carcass = 0;
		for(int i = 1; i <= players; i++) {
			BattlegroundAnimal obj = manager.getObj(i);
			if(!obj.isAlive())
				continue;
			switch(obj.getType())
			{
				case "Seal":
					seal++;
					break;
				case "Walrus":
					walrus++;
					break;
				case "Carcass":
					carcass++;
					break;
			}
		}
		System.out.println(seal + " seals survived");
		System.out.println(walrus + " walrus survived");
		System.out.println(carcass + " carcass survived");
		int max = 0;
		for(String s : teams)
			if(s.length() > max)
				max = s.length();
		char[] arr = new char[max];
		System.out.println("The game has completed! No bears remain.");
		for(int i = 0; i < teams.length; i++) {
			Arrays.fill(arr, ' ');
			System.arraycopy(teams[i].toCharArray(), 0, arr, 0, teams[i].length());
			System.out.println("The " + new String(arr) + " finished with " + scores[i] + " points.");
		}
	}

}
