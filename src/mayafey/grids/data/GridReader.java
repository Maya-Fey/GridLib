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
		if(dx2 < dx1)
			dx1 = dx2;
		if(dy2 < dy1)
			dy1 = dy2;
		return dx1 > dy1 ? dx1 : dy1;
	}

}
