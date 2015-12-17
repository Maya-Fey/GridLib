package mayafey.grids.data;

public class GridReader {
	
	private final PositionGrid grid;

	public GridReader(PositionGrid grid) 
	{
		this.grid = grid;
	}
	
	public int distanceFrom(int x1, int x2, int y1, int y2)
	{
		int dx1 = x1 - x2;
		int dy1 = y1 - y2;
		dx1 = dx1 < 0 ? ~dx1 + 1 : dx1;
		dy1 = dy1 < 0 ? ~dy1 + 1 : dy1;
		int dx2 = (x1 + grid.getWidth()) - x2;
		int dy2 = (y1 + grid.getHeight()) - y2;
		dx2 = dx2 < 0 ? ~dx2 + 1 : dx2;
		dy2 = dy2 < 0 ? ~dy2 + 1 : dy2;
		System.out.println("(X: " + x1 + ", Y: " + y1 + ")");
		System.out.println(dx1 + " vs " + dx2 + " || " + dy1 + " vs " + dy2);
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
			int d2 = (y1 + grid.getHeight()) - y2;
			d1 = d1 < 0 ? ~d1 + 1 : d1;
			d2 = d2 < 0 ? ~d2 + 1 : d2;
			if(d2 < d1)
				y1 += grid.getHeight();
			if((y1 - y2) < 0)
				return PositionGrid.NORTH;
			else
				return PositionGrid.SOUTH;
		}
		if(y) {
			int d1 = x1 - x2;
			int d2 = (x1 + grid.getHeight()) - x2;
			d1 = d1 < 0 ? ~d1 + 1 : d1;
			d2 = d2 < 0 ? ~d2 + 1 : d2;
			if(d2 < d1)
				x1 += grid.getWidth();
			if((x1 - x2) < 0)
				return PositionGrid.EAST;
			else
				return PositionGrid.WEST;
		}
		int dy1 = y1 - y2;
		int dx1 = x1 - x2;
		int dy2 = (y1 + grid.getHeight()) - y2;
		int dx2 = (x1 + grid.getWidth()) - x2;
		dy1 = dy1 < 0 ? ~dy1 + 1 : dy1;
		dx1 = dx1 < 0 ? ~dx1 + 1 : dx1;
		dy2 = dy2 < 0 ? ~dy2 + 1 : dy2;
		dx2 = dx2 < 0 ? ~dx2 + 1 : dx2;
		if(dy2 < dy1)
			y1 += grid.getHeight();
		if(dx2 < dx1)
			x1 += grid.getWidth();
		boolean north = (y1 - y2) < 0;
		boolean east = (x1 - x2) < 0;
		if(north)
			if(east)
				return PositionGrid.NORTHEAST;
			else
				return PositionGrid.NORTHWEST;
		else
			if(east)
				return PositionGrid.SOUTHEAST;
			else
				return PositionGrid.SOUTHWEST;
	}

}
