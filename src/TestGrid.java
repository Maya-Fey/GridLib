import mayafey.grids.data.GridReader;
import mayafey.grids.data.PositionGrid;


public final class TestGrid {

	public static final void main(String[] args)
	{
		PositionGrid grid = new PositionGrid(20);
		GridReader reader = new GridReader(grid);
		System.out.println(reader.distanceFrom(1, 19, 1, 19));
	}

}
