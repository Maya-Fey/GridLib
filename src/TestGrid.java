import java.util.Random;

import mayafey.grids.data.GridReader;
import mayafey.grids.data.PositionGrid;

public final class TestGrid {

	public static final void main(String[] args)
	{
		PositionGrid grid = new PositionGrid(10);
		GridReader reader = new GridReader(grid);
		for(int i = 0; i < 10; i++)
			System.out.println(PositionGrid.getDirection(reader.getDirection(0, i, 0, 9)));
	}

}
