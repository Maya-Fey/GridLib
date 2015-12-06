package mayafey.grids.data;

public abstract class GridViewer<Type, Viewer> {
	
	protected final PositionGrid grid;
	protected final GridObjectManager<Type> manager;
	
	public GridViewer(PositionGrid grid, GridObjectManager<Type> manager)
	{
		this.grid = grid;
		this.manager = manager;
	}
	
	public abstract void view(GridView<Type> view, Viewer viewer);

}
