package mayafey.grids.data;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;

public class GridView<Type> {
	
	private final Type[] arr;
	
	private final int[] x,
						y;
	
	private int size = 0;
	private int givenSize = 0;
	
	public GridView(Type[] arr)
	{
		this.arr = arr;
		this.x = new int[arr.length];
		this.y = new int[arr.length];
	}
	
	@SuppressWarnings("unchecked")
	public GridView(Class<Type> type, int size)
	{
		this.arr = (Type[]) Array.newInstance(type, size);
		this.x = new int[size];
		this.y = new int[size];
	}
	
	public int size()
	{
		return this.size;
	}
	
	public int getGivenSize()
	{
		return this.givenSize;
	}
	
	public void setGivenSize(int size)
	{
		this.givenSize = size;
	}
	
	/**
	 * xy[0] = X
	 * xy[1] = Y
	 */
	public void getPos(int pos, int[] xy)
	{
		xy[0] = x[pos];
		xy[1] = y[pos];
	}
	
	public int getX(int pos)
	{
		return x[pos];
	}
	
	public int getY(int pos)
	{
		return y[pos];
	}
	
	public Type getObj(int pos)
	{
		return arr[pos];
	}
	
	public GridPosition<Type> getPosition(int pos)
	{
		return new GridPosition<Type>(arr[pos], x[pos], y[pos]);
	}
	public void reset()
	{
		Arrays.fill(arr, 0, size, null);
		Arrays.fill(x, 0, size, 0);
		Arrays.fill(y, 0, size, 0);
		size = 0;
	}
	
	public void add(Type obj, int x, int y)
	{
		   arr[size  ] = obj;
		this.x[size  ] = x;
		this.y[size++] = y;
	}
	
	public Iterator<GridPosition<Type>> iterator()
	{
		return new GridViewIterator<Type>(this);
	}
	
	private static final class GridViewIterator<Type> 
						 implements Iterator<GridPosition<Type>> {
		
		private final GridView<Type> view;
		
		private int pos = 0;
		
		private GridViewIterator(GridView<Type> view)
		{
			this.view = view;
		}
		
		public boolean hasNext()
		{
			return pos < view.getGivenSize();
		}

		public GridPosition<Type> next()
		{
			return view.getPosition(pos++);
		}
		
	}
	
	public static final class GridPosition<Type> {
		
		private final Type obj;
		
		private final int x, y;
		
		public GridPosition(Type obj, int x, int y)
		{
			this.obj = obj;
			this.x = x;
			this.y = y;
		}
		
		public int getX()
		{
			return x;
		}
		
		public int getY()
		{
			return y;
		}
		
		public Type getObj()
		{
			return obj;
		}
		
		/**
		 * xy[0] = X
		 * xy[1] = Y
		 */
		public void getPos(int[] xy)
		{
			xy[0] = x;
			xy[1] = y;
		}
		
	}

}
