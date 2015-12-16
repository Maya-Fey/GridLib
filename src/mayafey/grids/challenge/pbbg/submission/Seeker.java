package mayafey.grids.challenge.pbbg.submission;

import java.util.Arrays;

import mayafey.grids.challenge.pbbg.PolarBearBrain;
import mayafey.grids.data.GridView;
import mayafey.grids.data.PositionGrid;

public class Seeker 
	   extends PolarBearBrain {

	private final int[] directions = new int[8];
	private int def;
	private int num = 0;
	private static int kek = 0;
	
	public void init()
	{
		num = kek++;
		def = fromNumber(this.access.getRandom().nextInt(8));
	}
	
	public void tick() {
		if(num == 0)
		System.out.println(num + " has " + access.getWeight() + "kg in weight and has " + access.getHealth() + "/" + access.getMaxHealth() + " HP.");
	}
	
	public boolean defendAgainst(String type)
	{
		return true;
	}

	public int move(GridView<String> view)
	{
		Arrays.fill(directions, -1);
		int move = def; //Arbitrary default direction
		for(int i = 0; i < view.getGivenSize(); i++) {
			String type = view.getObj(i);
			int x = view.getX(i),
				y = view.getY(i);
			int dir = toNumber(this.access.directionTo(x, y));
			if(dir == -1)
				continue;
			switch(type)
			{
				case "Bear":
					directions[dir] = - 1;
					break;
				case "Walrus":
					int dist0 = this.access.distanceTo(x, y) + 1;
					if(directions[dir] != -1 && directions[dir] > dist0)
						directions[dir] = dist0;
					break;
				case "Seal":
				case "Carcass":
					int dist1 = this.access.distanceTo(x, y);
					if(directions[dir] != -1 && directions[dir] > dist1)
						directions[dir] = dist1;
					break;
				case "Animal":
					int dist2 = this.access.distanceTo(x, y) + 2;
					if(directions[dir] != -1 && directions[dir] > dist2)
						directions[dir] = dist2;
					break;
			}
		}
		int best = 9001;
		for(int i = 0; i < 8; i++)
			if(directions[i] > 0 || directions[i] < best) {
				move = fromNumber(i);
			}
		return move;
	}

	public void getSkills(int[] arr, int weight)
	{
		arr[PolarBearBrain.VISION] = 25;
		arr[PolarBearBrain.ATTACK] = 25;
	}
	
	private static int toNumber(int direction) 
	{
		switch(direction) 
		{
			case PositionGrid.NORTH:
				return 0;
			case PositionGrid.NORTHEAST:
				return 1;
			case PositionGrid.EAST:
				return 2;
			case PositionGrid.SOUTHEAST:
				return 3;
			case PositionGrid.SOUTH:
				return 4;
			case PositionGrid.SOUTHWEST:
				return 5;
			case PositionGrid.WEST:
				return 6;
			case PositionGrid.NORTHWEST:
				return 7;
			default:
				return -1;
		}
	}
	
	private static int fromNumber(int num)
	{
		switch(num)
		{
			case 0:
				return PositionGrid.NORTH;
			case 1:
				return PositionGrid.NORTHEAST;
			case 2:
				return PositionGrid.EAST;
			case 3:
				return PositionGrid.SOUTHEAST;
			case 4:
				return PositionGrid.SOUTH;
			case 5:
				return PositionGrid.SOUTHWEST;
			case 6:
				return PositionGrid.WEST;
			case 7:
				return PositionGrid.NORTHWEST;
			default:
				return -1;
		}
	}

}
