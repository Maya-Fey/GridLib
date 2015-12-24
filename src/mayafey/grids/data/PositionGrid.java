package mayafey.grids.data;

import java.util.Arrays;

public class PositionGrid {
	
	private static final String[] names = new String[] {
		"Stay",      //000
		"West",      //001
		"South",     //002
		"Southwest", //003
		"",          //004
		"East",      //005
		"",          //006
		"Southeast", //007
		"",          //010
		"",          //011
		"North",     //012
		"Northwest", //013
		"",          //014
		"",          //015
		"",          //016
		"Northeast"  //017
	};
	
	public static final int STAY      =  000;
	public static final int NORTH     =  012;
	public static final int NORTHEAST =  017;
	public static final int EAST      =  005;
	public static final int SOUTHEAST =  007;
	public static final int SOUTH     =  002;
	public static final int SOUTHWEST =  003;
	public static final int WEST      =  001;
	public static final int NORTHWEST =  013;
	
	private static final int INORTH   = ~012;
	private static final int IEAST    = ~005;
	
	private static final int[] directions = new int[] 
	{
		STAY, NORTH, NORTHEAST, EAST, SOUTHEAST,
		SOUTH, SOUTHWEST, WEST, NORTHWEST
	};
	
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
	
	public PositionGrid(int size, boolean b) 
	{
		this.width = size;
		this.height = size;
		this.grid = new int[size * size];
		this.wrap = b;
	}
	
	public void fill(int filler)
	{
		Arrays.fill(grid, filler);
	}

	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return this.height;
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
		if(dir == 0)
			return pos;
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
		if(dir == 0)
			return  y * width + x;
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
	
	public int getMoveRel(int pos, int dir, int space)
	{
		if(dir == 0)
			return pos;
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
				if(x < 0 || x >= width)
					return -1;
			} dir >>>= 1;
			if(cy) {
				boolean neg = (dir & 1) == 0;
				y = neg ? y - space : y + space;
				if(y < 0 || y >= width) 
					return -1;
			}
		}
		return y * width + x;
	}
	
	public int getMoveRel(int x, int y, int dir, int space)
	{
		if(dir == 0)
			return  y * width + x;
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
				if(x < 0 || x >= width)
					return -1;
			} dir >>>= 1;
			if(cy) {
				boolean neg = (dir & 1) == 0;
				y = neg ? y - space : y + space;
				if(y < 0 || y >= width)
					return -1;
			}
		}
		return y * width + x;
	}
	
	public String printPos(int pos)
	{
		return "(X: " + (pos % width) + ", Y:" + (pos / width) + ")";
	}
	
	public static String printPos(int x, int y)
	{
		return "(X: " + x + ", Y:" + y + ")";
	}
	
	/**
	 * xy[0] = X
	 * xy[1] = Y
	 */
	public void getXY(int pos, int[] xy)
	{
		xy[0] = pos % width;
		xy[1] = pos / width;
	}
	
	public int fromXY(int x, int y)
	{
		return y * width + x;
	}
	
	public boolean getWrapped()
	{
		return this.wrap;
	}
	
	public void setWrapped(boolean wrap)
	{
		this.wrap = wrap;
	}
	
	public static String getDirection(int pos)
	{
		return names[pos];
	}
	
	public static boolean goesNorth(int dir)
	{
		return (dir & NORTH) == NORTH; 
	}
	
	public static boolean goesSouth(int dir)
	{
		return (dir & NORTH) == SOUTH; 
	}
	
	public static boolean goesEast(int dir)
	{
		return (dir & EAST) == EAST; 
	}
	
	public static boolean goesWest(int dir)
	{
		return (dir & EAST) == WEST; 
	}
	
	public static boolean goesEW(int dir)
	{
		return (dir & 1) == 1;
	}
	
	public static boolean goesNS(int dir)
	{
		return (dir & 2) == 2;
	}
	
	public static int getEW(int dir)
	{
		return dir & EAST;
	}
	
	public static int getNS(int dir)
	{
		return dir & NORTH;
	}
	
	public static int setNorth(int dir)
	{
		return dir | NORTH;
	}
	
	public static int setSouth(int dir)
	{
		return (dir & INORTH) | SOUTH;
	}
	
	public static int setEast(int dir)
	{
		return dir | EAST;
	}
	
	public static int setWest(int dir)
	{
		return (dir & IEAST) | WEST;
	}
	
	public static int reverse(int dir)
	{
		if((dir & 1) == 1)
			dir ^= 4;
		if((dir & 2) == 2)
			dir ^= 8;
		return dir;
	}
	
	public static int directionFromNumber(int num)
	{
		return directions[num];
	}
	
	/**
	 * 0 = STAY<br>
	 * 1 = NORTH<br>
	 * 2 = NORTHEAST<br>
	 * 3 = EAST<br>
	 * 4 = SOUTHEAST<br>
	 * 5 = SOUTH<br>
	 * 6 = SOUTHWEST<br>
	 * 7 = WEST<br>
	 * 8 = NORTHWEST<br>
	 */
	public static int numberFromDirection(int num)
	{
		for(int i = 0; i < directions.length; i++)
			if(directions[i] == num)
				return i;
		throw new java.lang.NullPointerException("That's no direction...");
	}

}
