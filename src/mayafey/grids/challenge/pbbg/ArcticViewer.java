package mayafey.grids.challenge.pbbg;

import java.util.Random;

import mayafey.grids.data.GridObjectManager;
import mayafey.grids.data.GridView;
import mayafey.grids.data.GridViewer;
import mayafey.grids.data.PositionGrid;

public class ArcticViewer extends GridViewer<String, BattlegroundAnimal> {

	protected final Random rand;
	protected final int[] scratch = new int[grid.getWidth()];
	
	protected int intensity = 0;
	
	public ArcticViewer(PositionGrid grid, GridObjectManager<BattlegroundAnimal> manager, Random rand) 
	{
		super(grid, manager);
		this.rand = rand;
	}
	
	public void updateStorm()
	{
		this.intensity = rand.nextInt(101);
	}

	public void view(GridView<String> view, int obj)
	{
		view.reset();
		BattlegroundAnimal viewer = manager.getObj(obj);
		int pos = manager.getPos(obj);
		int skill = viewer.getVision();
		skill += rand.nextInt(15) - 7;
		int size = skill / 7;
		int length = size * 2 + 1;
		int gwidth = grid.getWidth();
		int gheight = grid.getHeight();
		int ID = ((skill + rand.nextInt(skill)) * (100 - intensity)) / 100;
		int x = pos & gwidth - size;
		int y = pos / gwidth - size;
		x = x < 0 ? x + gwidth : x > gwidth ? x -= gwidth : x;
		y = y < 0 ? y + gheight : y > gheight ? y -= gheight : y;
		for(int i = 0; i < length; i++) {
			scratch[i] = x;
			x++;
			x = x > gwidth ? x -= gwidth : x;
		}
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < length; j++) {
				obj = grid.get(scratch[j], y);
				if(obj != 0) {
					BattlegroundAnimal animal = manager.getObj(pos);
					if((ID + rand.nextInt(11)) > 10 + rand.nextInt(26))
						view.add(animal.getType(), scratch[j], y);
					else
						view.add("Animal", scratch[j], y);
				}
			}
			y++; y = y > gheight ? y -= gheight : y;
		}
	}

}
