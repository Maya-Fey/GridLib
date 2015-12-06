import mayafey.grids.data.GridObjectManager;
import mayafey.grids.data.PositionGrid;

public final class TestGrid {

	public static final void main(String[] args)
	{
		PositionGrid grid = new PositionGrid(4);
		GridObjectManager<String> manager = new GridObjectManager<String>(grid, String.class, 10);
		grid.setWrapped(true);
		manager.add("Donuts", 0);
		manager.moveRel(0, PositionGrid.NORTH, 1);
		System.out.println(manager.getPos(0));
	}

}
