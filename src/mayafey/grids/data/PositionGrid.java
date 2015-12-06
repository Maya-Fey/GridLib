package mayafey.grids.data;

public class PositionGrid {
	
	public static final int NORTH     = 012;
	public static final int NORTHEAST = 017;
	public static final int EAST      = 005;
	public static final int SOUTHEAST = 007;
	public static final int SOUTH     = 002;
	public static final int SOUTHWEST = 003;
	public static final int WEST      = 001;
	public static final int NORTHWEST = 013;
	
	private final int width;
	private final int height;
	
	private final int[] grid;
	
	private boolean wrap = false;

	public PositionGrid(int size) 
	{
		this.width = size;
		this.height = size;
		this.grid = new int[size * size];
	}
	
	public PositionGrid(int width, int height)
	{
		this.width = width;
		this.height = height;
		this.grid = new int[width * height];
	}
	
	public int get(int pos)
	{
		return grid[pos];
	}
	
	/**
	 * Where y is the vertical axis. 
	 */
	public int get(int x, int y)
	{
		return grid[y * width + x];
	}
	
	public void set(int pos, int val)
	{
		grid[pos] = val;
	}
	
	public void set(int x, int y, int val)
	{
		grid[y * width + x] = val;
	}
	
	public void move(int oldp, int newp)
	{
		grid[newp] = grid[oldp];
		grid[oldp] = 0;
	}
	
	public void move(int x1, int y1, int x2, int y2)
	{
		int old = y1 * width + x1;
		grid[y2 * width + x2] = grid[old];
		grid[old] = 0;
	}
	
	public int moveRel(int pos, int dir, int space)
	{
		int x = pos % width;
		int y = pos / width;
		boolean cx = (dir & 1) == 1; dir >>>= 1;
		boolean cy = (dir & 1) == 1; dir >>>= 1;
		if(wrap) {
			if(cx) {
				boolean neg = (dir & 1) == 0;
				x = neg ? x - space : (x + space) % width;
				if(x < 0)
					x += width;
			} dir >>>= 1;
			if(cy) {
				boolean neg = (dir & 1) == 0;
				y = neg ? y - space : (y + space) % height;
				if(y < 0)
					y += height;
			}
		} else {
			if(cx) {
				boolean neg = (dir & 1) == 0;
				x = neg ? x - space : x + space;
				if(x < 0 || x >= width) {
					grid[pos] = 0;
					return -1;
				}
			} dir >>>= 1;
			if(cy) {
				boolean neg = (dir & 1) == 0;
				y = neg ? y - space : y + space;
				if(y < 0 || y >= width) {
					grid[pos] = 0;
					return -1;
				}
			}
		}
		int npos = y * width + x;
		grid[npos] = grid[pos];
		grid[pos] = 0;
		return npos;
	}
	
	public int moveRel(int x, int y, int dir, int space)
	{
		int pos = y * width + x;
		boolean cx = (dir & 1) == 1; dir >>>= 1;
		boolean cy = (dir & 1) == 1; dir >>>= 1;
		if(wrap) {
			if(cx) {
				boolean neg = (dir & 1) == 0;
				x = neg ? x - space : (x + space) % width;
				if(x < 0)
					x += width;
			} dir >>>= 1;
			if(cy) {
				boolean neg = (dir & 1) == 0;
				y = neg ? y - space : (y + space) % height;
				if(y < 0)
					y += height;
			}
		} else {
			if(cx) {
				boolean neg = (dir & 1) == 0;
				x = neg ? x - space : x + space;
				if(x < 0 || x >= width) {
					grid[pos] = 0;
					return -1;
				}
			} dir >>>= 1;
			if(cy) {
				boolean neg = (dir & 1) == 0;
				y = neg ? y - space : y + space;
				if(y < 0 || y >= width) {
					grid[pos] = 0;
					return -1;
				}
			}
		}
		int npos = y * width + x;
		grid[npos] = grid[pos];
		grid[pos] = 0;
		return npos;
	}
	
	public int moveRelPos(int pos, int nx, int ny)
	{
		int x = pos % width;
		int y = pos / width;
		if(wrap) {
			x = nx < 0 ? x + nx : (x + nx) % width;
			y = ny < 0 ? y + ny : (y + ny) % height;
			if(x < 0)
				x += width;
			if(y < 0)
				y += height;
		} else {
			x += nx;
			y += ny;
			if(x < 0 || x >= width || y < 0 || y >= height) {
				grid[pos] = 0;
				return -1;
			}
		}
		int npos = y * width + x;
		grid[npos] = grid[pos];
		grid[pos] = 0;
		return npos;
	}
	
	public int moveRelPos(int x, int y, int nx, int ny)
	{
		int pos = y * width + x;
		if(wrap) {
			x = nx < 0 ? x + nx : (x + nx) % width;
			y = ny < 0 ? y + ny : (y + ny) % height;
			if(x < 0)
				x += width;
			if(y < 0)
				y += height;
		} else {
			x += nx;
			y += ny;
			if(x < 0 || x >= width || y < 0 || y >= height) {
				grid[pos] = 0;
				return -1;
			}
		}
		int npos = y * width + x;
		grid[npos] = grid[pos];
		grid[pos] = 0;
		return npos;
	}
	
	public boolean getWrapped()
	{
		return this.wrap;
	}
	
	public void setWrapped(boolean wrap)
	{
		this.wrap = wrap;
	}

}
