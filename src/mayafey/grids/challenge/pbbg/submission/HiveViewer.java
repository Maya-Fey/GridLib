package mayafey.grids.challenge.pbbg.submission;

import mayafey.grids.data.PositionGrid;

public class HiveViewer {

	private static final String[] types = new String[]
		{
			"", "Friendly", "Animal", "Carcass", "Seal", "Walrus", "Bear"
		};
	
	
	protected final PositionGrid grid;
	protected final PositionGrid intel;
	protected final int[] scratch;
	
	protected int intensity = 0;
	
	public HiveViewer(PositionGrid grid, PositionGrid intel) 
	{
		this.grid = grid;
		this.intel = intel;
		this.scratch = new int[grid.getWidth()];
	}

	public void view(HiveView view, int pos, int size)
	{
		view.reset();
		view.setVisionSize(size);
		int length = size * 2 + 1;
		int gwidth = grid.getWidth();
		int gheight = grid.getHeight();
		int x = pos % gwidth - size;
		int y = pos / gwidth - size;
		x = x < 0 ? x + gwidth : x >= gwidth ? x -= gwidth : x;
		y = y < 0 ? y + gheight : y >= gheight ? y -= gheight : y;
		for(int i = 0; i < length; i++) {
			scratch[i] = x;
			x++;
			x = x >= gwidth ? x -= gwidth : x;
		}
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < length; j++) {
				int obj = grid.get(scratch[j], y);
				if(obj != 0) {
					view.add(types[obj], scratch[j], y, intel.get(scratch[j], y));
				}
			}
			y++; y = y >= gheight ? y -= gheight : y;
		}
	}

}
