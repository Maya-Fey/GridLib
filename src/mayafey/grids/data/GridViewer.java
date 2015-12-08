package mayafey.grids.data;

public abstract class GridViewer<Type, Viewer> {
	
	protected final PositionGrid grid;
	protected final GridObjectManager<Viewer> manager;
	
	public GridViewer(PositionGrid grid, GridObjectManager<Viewer> manager)
	{
		this.grid = grid;
		this.manager = manager;
	}
	
	public abstract void view(GridView<Type> view, int obj);

}
