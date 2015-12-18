package mayafey.grids.data;

public class GridReader {
	
	private final PositionGrid grid;

	public GridReader(PositionGrid grid) 
	{
		this.grid = grid;
	}
	
	public int distanceFrom(int x1, int x2, int y1, int y2)
	{
		int dy1 = y1 - y2;
		int dx1 = x1 - x2;
		dy1 = dy1 < 0 ? ~dy1 + 1 : dy1;
		dx1 = dx1 < 0 ? ~dx1 + 1 : dx1;
		int dy2 = grid.getHeight() - dy1;
		int dx2 = grid.getWidth() - dx1;
		if(dx2 < dx1)
			dx1 = dx2;
		if(dy2 < dy1)
			dy1 = dy2;
		return dx1 > dy1 ? dx1 : dy1;
	}
	
	public int getDirection(int x1, int x2, int y1, int y2)
	{
		boolean x = x1 == x2;
		boolean y = y1 == y2;
		if(x && y)
			return PositionGrid.STAY;
		if(x) {
			int d1 = y1 - y2;
			d1 = d1 < 0 ? ~d1 + 1 : d1;
			int d2 = grid.getHeight() - d1;
			if(y1 > y2 ^ d2 < d1)
				return PositionGrid.SOUTH;
			else
				return PositionGrid.NORTH;
		}
		if(y) {
			int d1 = x1 - x2;
			d1 = d1 < 0 ? ~d1 + 1 : d1;
			int d2 = grid.getHeight() - d1;
			if(x1 > x2 ^ d2 < d1)
				return PositionGrid.WEST;
			else
				return PositionGrid.EAST;
		}
		int dy1 = y1 - y2;
		int dx1 = x1 - x2;
		dy1 = dy1 < 0 ? ~dy1 + 1 : dy1;
		dx1 = dx1 < 0 ? ~dx1 + 1 : dx1;
		int dy2 = grid.getHeight() - dy1;
		int dx2 = grid.getWidth() - dx1;
		boolean south = y1 > y2 ^ dy2 < dy1;
		boolean west = x1 > x2 ^ dx2 < dx1;
		if(south)
			if(west)
				return PositionGrid.SOUTHWEST;
			else
				return PositionGrid.SOUTHEAST;
		else
			if(west)
				return PositionGrid.NORTHWEST;
			else
				return PositionGrid.NORTHEAST;
	}

}
