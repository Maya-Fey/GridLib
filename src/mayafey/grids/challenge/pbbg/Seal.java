package mayafey.grids.challenge.pbbg;

import java.util.Random;

import mayafey.grids.data.GridReader;
import mayafey.grids.data.GridView;
import mayafey.grids.data.PositionGrid;

public class Seal extends BattlegroundAnimal {

	public Seal(GridReader reader, Random rand)
	{
		super(reader, rand);
		this.vision = 18 + rand.nextInt(19);
		this.resistance = 2;
		this.weight = 100;
	}

	public boolean defend(String animal)
	{
		return false;
	}

	public void tick() {}

	public int attack()
	{
		return 0;
	}

	public int getMove(GridView<String> view)
	{
		final int x = this.getX();
		final int y = this.getY();
		boolean  east = false,
		         west = false,
				north = false,
				south = false,
			     move = false;
		int     deast = 0,
		        dwest = 0,
			   dnorth = 0,
			   dsouth = 0;
		int i = 0;
		while(i < view.getGivenSize()) {
			String animal = view.getObj(i);
			if(animal != "Bear") {
				i++;
				continue;
			} 
			move |= true;
			int ax = view.getX(i);
			int ay = view.getY(i);
			int dist = reader.distanceFrom(x, ax, y, ay);
			int dir = reader.getDirection(x, ax, y, ay);
			if(PositionGrid.goesEast(dir)) {
				east |= true;
				if(dist < deast)
					deast = dist;
			}
			if(PositionGrid.goesWest(dir)) {
				west |= true;
				if(dist < dwest)
					dwest = dist;
			}
			if(PositionGrid.goesNorth(dir)) {
				north |= true;
				if(dist < dnorth)
					dnorth = dist;
			}
			if(PositionGrid.goesSouth(dir)) {
				south |= true;
				if(dist < dsouth)
					dsouth = dist;
			}
			i++;
		}
		int go = PositionGrid.STAY;
		if(move) {
			if(!(east && west)) {
				if(east)
					go = PositionGrid.setWest(go);
				else
					go = PositionGrid.setEast(go);
			}
			if(!(north && south)) {
				if(north)
					go = PositionGrid.setSouth(go);
				else
					go = PositionGrid.setNorth(go);
			}
			if(go == 0) {
				if(deast != dwest) 
					if(deast < dwest)
						go = PositionGrid.setWest(go);
					else
						go = PositionGrid.setEast(go);
				if(dnorth != dsouth) 
					if(dsouth < dnorth)
						go = PositionGrid.setNorth(go);
					else
						go = PositionGrid.setSouth(go);
			}
		}
		return go;
	}

	public String getType()
	{
		return "Seal";
	}

}
