package mayafey.grids.data;

import java.lang.reflect.Array;

public class GridObjectManager<Type> {
	
	private final PositionGrid grid;
	private final Type[] arr;
	private final int[] pos;
	
	private int cur = 0;
	
	public GridObjectManager(PositionGrid grid, Type[] arr)
	{
		this.grid = grid;
		this.arr = arr;
		this.pos = new int[arr.length];
	}
	
	public GridObjectManager(PositionGrid grid, Type[] arr, int cur)
	{
		this.grid = grid;
		this.arr = arr;
		this.pos = new int[arr.length];
		this.cur = cur;
	}
	
	@SuppressWarnings("unchecked")
	public GridObjectManager(PositionGrid grid, Class<Type> type, int size)
	{
		this.grid = grid;
		this.arr = (Type[]) Array.newInstance(type, size);
		this.pos = new int[size];
	}
	
	public void add(Type obj, int pos)
	{
		grid.set(pos, cur);
		arr[cur] = obj;
		this.pos[cur++] = pos;
	}
	
	public void add(Type obj, int x, int y)
	{
		int pos = grid.fromXY(x, y);
		grid.set(pos, cur);
		arr[cur] = obj;
		this.pos[cur++] = pos;
	}
	
	public void set(Type obj, int pos0, int pos1)
	{
		grid.set(pos1, pos0);
		arr[pos0] = obj;
		pos[pos0] = pos1;
	}
	
	public void set(Type obj, int pos0, int x, int y)
	{
		int pos1 = grid.fromXY(x, y);
		grid.set(pos1, pos0);
		arr[pos0] = obj;
		pos[pos0] = pos1;
	}
	
	public void move(int pos0, int pos1)
	{
		grid.move(pos[pos0], pos1);
		pos[pos0] = pos1;
	}
	
	public void move(int pos0, int x, int y)
	{
		int pos1 = grid.fromXY(x, y);
		grid.move(pos[pos0], pos1);
		pos[pos0] = pos1;
	}
	
	public void moveRel(int pos0, int dir, int spaces)
	{
		pos[pos0] = grid.moveRel(pos[pos0], dir, spaces);
	}
	
	public void moveRelPos(int pos0, int x, int y)
	{
		pos[pos0] = grid.moveRelPos(pos[pos0], x, y);
	}
	
	public Type getObj(int pos)
	{
		return arr[pos];
	}
	
	public int getPos(int pos)
	{
		return this.pos[pos];
	}
	
	public void getPos(int pos, int[] xy)
	{
		grid.getXY(this.pos[pos], xy);
	}

}
