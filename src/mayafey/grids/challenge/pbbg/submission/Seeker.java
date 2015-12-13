package mayafey.grids.challenge.pbbg.submission;

import java.util.Arrays;

import mayafey.grids.challenge.pbbg.PolarBearBrain;
import mayafey.grids.data.GridView;
import mayafey.grids.data.PositionGrid;

public class Seeker 
	   extends PolarBearBrain {

	private final int[] directions = new int[8];
	
	public boolean defendAgainst(String type)
	{
		return true;
	}

	public int move(GridView<String> view)
	{
		Arrays.fill(directions, -1);
		int move = PositionGrid.NORTH; //Arbitrary default direction
		for(int i = 0; i < view.getGivenSize(); i++) {
			String type = view.getObj(i);
			if(type == "Bear") {
				
			}
		}
		return move;
	}

	public void getSkills(int[] arr, int weight)
	{
		arr[PolarBearBrain.VISION] = 25;
		arr[PolarBearBrain.ATTACK] = 25;
	}
	
	private int toNumber(int direction) 
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
	
	private int fromNumber(int num)
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
