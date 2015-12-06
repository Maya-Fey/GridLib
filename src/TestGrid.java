import mayafey.grids.data.PositionGrid;

public final class TestGrid {

	public static final void main(String[] args)
	{
		PositionGrid grid = new PositionGrid(4);
		grid.setWrapped(true);
		grid.set(1, 1, 1);
		grid.moveRel(1, 1, PositionGrid.NORTHEAST, 3);
		System.out.println(grid.get(0, 0));
	}

}
