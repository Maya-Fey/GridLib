package mayafey.grids.challenge.pbbg.submission;

import java.util.Arrays;

import mayafey.grids.data.GridView;

public class HiveView extends GridView<String> {
	
	public int current;
	
	private final int[] reliability;

	public HiveView(String[] arr) 
	{
		super(arr);
		reliability = new int[arr.length];
	}
	
	public void setCurrent(int cur)
	{
		this.current = cur;
	}
	
	public void reset()
	{
		Arrays.fill(reliability, 0);
		super.reset();
	}
	
	public int getReliability(int i)
	{
		return reliability[i];
	}
	
	public void add(String s, int x, int y, int r)
	{
		reliability[givenSize] = r;
		super.add(s, x, y);
	}

}
