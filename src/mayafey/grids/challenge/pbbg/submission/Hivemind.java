package mayafey.grids.challenge.pbbg.submission;

import java.util.Arrays;

import mayafey.grids.challenge.pbbg.PolarBearBrain;
import mayafey.grids.data.GridView;
import mayafey.grids.data.PositionGrid;

public class Hivemind extends PolarBearBrain {
	
	private static final int[] DIRS = new int[]
		{
			PositionGrid.NORTHEAST,
			PositionGrid.NORTHWEST,
			PositionGrid.SOUTHEAST,
			PositionGrid.SOUTHWEST
		};

	private static final int SEAL = 250;
	private static final int DSEAL = 500;
	private static final int WALRUS = 400;
	private static final int DWALRUS = 800;
	private static final int BEAR = 600;
	private static final int DBEAR = 1200;
	private static final int ANIMAL = 500;
	private static final int DANIMAL = 1000;
	
	private static final int range = 11;
	private static final int[] positions = new int[GridChallenge1.BATTLESIZE];
	private static final int[] versions = new int[GridChallenge1.BATTLESIZE];
	
	private static PositionGrid memory;
	private static PositionGrid intel;
	private static HiveViewer viewer;
	private static int fs;
	private static int num = 0;
	private static boolean updated = false;
	private static boolean alloc = true;
	
	private final int[] scratch = new int[12];
	private final int[] dir = new int[9];
	private final boolean[] pos = new boolean[9];
	private final HiveView hview = new HiveView(new String[144]);
	
	private int ID;
	private int update = 0;
	private int def;
	private int times = 0;

	public boolean defendAgainst(String type)
	{
		return true;
	}

	public int move(GridView<String> view)
	{
		Arrays.fill(pos, true);
		Arrays.fill(dir, 0);
		updated = false;
		boolean seal = access.getHealth() > 14;
		boolean walrus = access.getHealth() > 300;
		boolean bear = access.getHealth() > 350;
		
		int sx = access.getX() - view.getVisionSize();
		int sy = access.getY() - view.getVisionSize();
		sx = sx < 0 ? sx + access.gridSize() : sx;
		sy = sy < 0 ? sy + access.gridSize() : sy;
		int len = view.getVisionSize() * 2 + 1;
		for(int i = 0; i < len; i++) {
			scratch[i] = sx;
			sx++; sx = sx >= access.gridSize() ? sx -= access.gridSize() : sx;
		}
		for(int i = 0; i < len; i++) {
			for(int j = 0; j < len; j++) {
				int pos = intel.fromXY(scratch[j], sy);
				intel.set(pos, update);
				if(memory.get(pos) != 1)
					memory.set(pos, 0);
			}
			sy++; sy = sy >= access.gridSize() ? sy -= access.gridSize() : sy;
		}
		for(int i = 0; i < view.getGivenSize(); i++) {
			String type = view.getObj(i);
			int val = 0;
			switch(type)
			{
				case "Animal":
					val = 2;
					break;
				case "Carcass":
					val = 3;
					break;
				case "Seal":
					val = 4;
					break;
				case "Walrus":
					val = 5;
					break;
				case "Bear":
					val = 6;
					break;
			}
			memory.set(view.getX(i), view.getY(i), val);
		}
		
		viewer.view(hview, access.getPos(), range);
		boolean easy = false;
		boolean medium = false;
		for(int i = 0; i < hview.getGivenSize(); i++) {
			String type = hview.getObj(i);
			switch(type)
			{
				case "Carcass":
				case "Seal":
					easy = true;
					break;
				case "Walrus":
				case "Animal":
					medium = true;
			}
			if(easy)
				break;
		}
		if(!easy && access.getHealth() > (access.getMaxHealth() / 2)) 
			if(!medium) 
				bear = true;
			else
				walrus = true;
		for(int i = 0; i < hview.getGivenSize(); i++) {
			String type = hview.getObj(i);
			int x = hview.getX(i);
			int y = hview.getY(i);
			int dist = access.distanceTo(x, y);
			if(dist == 0)
				continue;
			if(dist == 1) {
				switch(type)
				{
					case "Friendly":
						int spy = PositionGrid.numberFromDirection(access.directionTo(x, y));
						pos[spy] = false;
						dir[spy] -= 2400;
						break;
					case "Bear":
						if(bear)
							dir[PositionGrid.numberFromDirection(access.directionTo(x, y))] += BEAR;
						else {
							int d = access.directionTo(x, y);
							int ew = PositionGrid.getEW(d);
							int ns = PositionGrid.getNS(d);
							boolean ge = ew != 0;
							boolean gn = ns != 0;
							if(ge && gn) {
								int n = PositionGrid.numberFromDirection(d);
								pos[n] = false;
								dir[n] -= DBEAR;
								n = PositionGrid.numberFromDirection(ew);
								pos[n] = false;
								dir[n] -= BEAR;
								n = PositionGrid.numberFromDirection(ns);
								pos[n] = false;
								dir[n] -= BEAR;
							} else if(gn) {
								pos[3] = false;
								pos[7] = false;
								dir[3] -= BEAR;
								dir[7] -= BEAR;
								int n = PositionGrid.numberFromDirection(ns);
								pos[n] = false;
								dir[n] -= DBEAR;
								n = PositionGrid.numberFromDirection(PositionGrid.setEast(ns));
								pos[n] = false;
								dir[n] -= BEAR;
								n = PositionGrid.numberFromDirection(PositionGrid.setWest(ns));
								pos[n] = false;
								dir[n] -= BEAR;
							} else {
								pos[1] = false;
								pos[5] = false;
								dir[1] -= BEAR;
								dir[5] -= BEAR;
								int n = PositionGrid.numberFromDirection(ew);
								pos[n] = false;
								dir[n] -= DBEAR;
								n = PositionGrid.numberFromDirection(PositionGrid.setEast(ew));
								pos[n] = false;
								dir[n] -= BEAR;
								n = PositionGrid.numberFromDirection(PositionGrid.setWest(ew));
								pos[n] = false;
								dir[n] -= BEAR;
							}
						}
						break;
					case "Walrus":
						if(walrus)
							dir[PositionGrid.numberFromDirection(access.directionTo(x, y))] += WALRUS;
						else {
							int d = access.directionTo(x, y);
							int ew = PositionGrid.getEW(d);
							int ns = PositionGrid.getNS(d);
							boolean ge = ew != 0;
							boolean gn = ns != 0;
							if(ge && gn) {
								int n = PositionGrid.numberFromDirection(d);
								pos[n] = false;
								dir[n] -= DWALRUS;
								n = PositionGrid.numberFromDirection(ew);
								pos[n] = false;
								dir[n] -= WALRUS;
								n = PositionGrid.numberFromDirection(ns);
								pos[n] = false;
								dir[n] -= WALRUS;
							} else if(gn) {
								pos[3] = false;
								pos[7] = false;
								dir[3] -= WALRUS;
								dir[7] -= WALRUS;
								int n = PositionGrid.numberFromDirection(ns);
								pos[n] = false;
								dir[n] -= DWALRUS;
								n = PositionGrid.numberFromDirection(PositionGrid.setEast(ns));
								pos[n] = false;
								dir[n] -= WALRUS;
								n = PositionGrid.numberFromDirection(PositionGrid.setWest(ns));
								pos[n] = false;
								dir[n] -= WALRUS;
							} else {
								pos[1] = false;
								pos[5] = false;
								dir[1] -= WALRUS;
								dir[5] -= WALRUS;
								int n = PositionGrid.numberFromDirection(ew);
								pos[n] = false;
								dir[n] -= DWALRUS;
								n = PositionGrid.numberFromDirection(PositionGrid.setEast(ew));
								pos[n] = false;
								dir[n] -= WALRUS;
								n = PositionGrid.numberFromDirection(PositionGrid.setWest(ew));
								pos[n] = false;
								dir[n] -= WALRUS;
							}
						}
						break;
					case "Seal":
						if(seal)
							dir[PositionGrid.numberFromDirection(access.directionTo(x, y))] += SEAL;
						else {
							int d = access.directionTo(x, y);
							int ew = PositionGrid.getEW(d);
							int ns = PositionGrid.getNS(d);
							boolean ge = ew != 0;
							boolean gn = ns != 0;
							if(ge && gn) {
								int n = PositionGrid.numberFromDirection(d);
								pos[n] = false;
								dir[n] -= DSEAL;
								n = PositionGrid.numberFromDirection(ew);
								pos[n] = false;
								dir[n] -= SEAL;
								n = PositionGrid.numberFromDirection(ns);
								pos[n] = false;
								dir[n] -= SEAL;
							} else if(gn) {
								pos[3] = false;
								pos[7] = false;
								dir[3] -= SEAL;
								dir[7] -= SEAL;
								int n = PositionGrid.numberFromDirection(ns);
								pos[n] = false;
								dir[n] -= DSEAL;
								n = PositionGrid.numberFromDirection(PositionGrid.setEast(ns));
								pos[n] = false;
								dir[n] -= SEAL;
								n = PositionGrid.numberFromDirection(PositionGrid.setWest(ns));
								pos[n] = false;
								dir[n] -= SEAL;
							} else {
								pos[1] = false;
								pos[5] = false;
								dir[1] -= SEAL;
								dir[5] -= SEAL;
								int n = PositionGrid.numberFromDirection(ew);
								pos[n] = false;
								dir[n] -= DSEAL;
								n = PositionGrid.numberFromDirection(PositionGrid.setEast(ew));
								pos[n] = false;
								dir[n] -= SEAL;
								n = PositionGrid.numberFromDirection(PositionGrid.setWest(ew));
								pos[n] = false;
								dir[n] -= SEAL;
							}
						}
						break;
					case "Animal":
						if(walrus)
							dir[PositionGrid.numberFromDirection(access.directionTo(x, y))] += ANIMAL;
						else {
							int d = access.directionTo(x, y);
							int ew = PositionGrid.getEW(d);
							int ns = PositionGrid.getNS(d);
							boolean ge = ew != 0;
							boolean gn = ns != 0;
							if(ge && gn) {
								int n = PositionGrid.numberFromDirection(d);
								pos[n] = false;
								dir[n] -= DANIMAL;
								n = PositionGrid.numberFromDirection(ew);
								pos[n] = false;
								dir[n] -= ANIMAL;
								n = PositionGrid.numberFromDirection(ns);
								pos[n] = false;
								dir[n] -= ANIMAL;
							} else if(gn) {
								pos[3] = false;
								pos[7] = false;
								dir[3] -= ANIMAL;
								dir[7] -= ANIMAL;
								int n = PositionGrid.numberFromDirection(ns);
								pos[n] = false;
								dir[n] -= DANIMAL;
								n = PositionGrid.numberFromDirection(PositionGrid.setEast(ns));
								pos[n] = false;
								dir[n] -= ANIMAL;
								n = PositionGrid.numberFromDirection(PositionGrid.setWest(ns));
								pos[n] = false;
								dir[n] -= ANIMAL;
							} else {
								pos[1] = false;
								pos[5] = false;
								dir[1] -= ANIMAL;
								dir[5] -= ANIMAL;
								int n = PositionGrid.numberFromDirection(ew);
								pos[n] = false;
								dir[n] -= DANIMAL;
								n = PositionGrid.numberFromDirection(PositionGrid.setEast(ew));
								pos[n] = false;
								dir[n] -= ANIMAL;
								n = PositionGrid.numberFromDirection(PositionGrid.setWest(ew));
								pos[n] = false;
								dir[n] -= ANIMAL;
							}
						}
						break;
					case "Carcass":
						dir[PositionGrid.numberFromDirection(access.directionTo(x, y))] += ANIMAL;
						break;
						
				}
			} else if(dist <= view.getGivenSize()) {
				switch(type) {
					case "Friendly":
						break;
					case "Bear":
						int n = PositionGrid.numberFromDirection(access.directionTo(x, y));
						if(bear)
							dir[n] += BEAR / dist;
						else
							dir[n] -= BEAR / dist;
						break;
					case "Walrus":
						n = PositionGrid.numberFromDirection(access.directionTo(x, y));
						if(walrus)
							dir[n] += WALRUS / dist;
						else
							dir[n] -= WALRUS / dist;
						break;
					case "Seal":
						n = PositionGrid.numberFromDirection(access.directionTo(x, y));
						if(seal)
							dir[n] += SEAL / dist;
						else
							dir[n] -= SEAL / dist;
						break;
					case "Animal":
						n = PositionGrid.numberFromDirection(access.directionTo(x, y));
						if(walrus)
							dir[n] += ANIMAL / dist;
						else
							dir[n] -= ANIMAL / dist;
						break;
					case "Carcass":
						n = PositionGrid.numberFromDirection(access.directionTo(x, y));
						dir[n] += ANIMAL / dist;
						break;
				}
			} else {
				int r = hview.getReliability(i);
				switch(type) {
					case "Friendly":
						break;
					case "Bear":
						int n = PositionGrid.numberFromDirection(access.directionTo(x, y));
						if(bear)
							dir[n] += BEAR / (dist + r);
						else
							dir[n] -= BEAR / (dist + r);
						break;
					case "Walrus":
						n = PositionGrid.numberFromDirection(access.directionTo(x, y));
						if(walrus)
							dir[n] += WALRUS / (dist + r);
						else
							dir[n] -= WALRUS / (dist + r);
						break;
					case "Seal":
						n = PositionGrid.numberFromDirection(access.directionTo(x, y));
						if(seal)
							dir[n] += SEAL / (dist + r);
						else
							dir[n] -= SEAL / (dist + r);
						break;
					case "Animal":
						n = PositionGrid.numberFromDirection(access.directionTo(x, y));
						if(walrus)
							dir[n] += ANIMAL / (dist + r);
						else
							dir[n] -= ANIMAL / (dist + r);
						break;
					case "Carcass":
						n = PositionGrid.numberFromDirection(access.directionTo(x, y));
						dir[n] += ANIMAL / (dist + r);
						break;
				}
			}
		}
		
		int move = 0;
		int best = 0;
		for(int i = 0; i < 9; i++)
			if(pos[i]) {
				if(dir[i] > best) {
					best = dir[i];
					move = i;
				}
			}
		
		if(move == 0) {
			best = Integer.MIN_VALUE;
			for(int i = 0; i < 9; i++)
				if(dir[i] > best) {
					best = dir[i];
					move = i;
				}
		}
		
		if(times-- == 0) {
			def = PositionGrid.numberFromDirection(DIRS[access.getRandom().nextInt(3)]);
			times = 10 + access.getRandom().nextInt(100);
		}
		
		if(move == 0)
			move = def;
		
		
		positions[ID] = memory.moveRel(positions[ID], PositionGrid.directionFromNumber(move), 1);
		return PositionGrid.directionFromNumber(move);
	}
	
	public void getSkills(int[] arr, int weight)
	{
		arr[0] = 30;
		arr[4] = 20;
	}

	public void tick()
	{
		if(!updated) {
			for(int i : positions)
				if(i > 0)
					memory.set(i, 0);				
			updated |= true;
		}
		versions[ID] = ++update;
		positions[ID] = access.getPos();
		memory.set(access.getPos(), 1);
		intel.set(access.getPos(), update);
	}
	
	protected void init()
	{
		ID = num++;
		if(alloc)
		{
			fs = access.gridSize() * access.gridSize();
			memory = new PositionGrid(access.gridSize(), true);
			intel = new PositionGrid(access.gridSize(), true);
			viewer = new HiveViewer(memory, intel);
			alloc &= false;
		}
	}
	
	@SuppressWarnings("unused")
	private int getAge(int pos)
	{
		return update - intel.get(pos);
	}

}
