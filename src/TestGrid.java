import mayafey.grids.data.PositionGrid;


public final class TestGrid {

	public static final void main(String[] args)
	{
		System.out.println(PositionGrid.getDirection(PositionGrid.setEast(PositionGrid.WEST)));
	}

}
