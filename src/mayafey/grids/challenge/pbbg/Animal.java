package mayafey.grids.challenge.pbbg;

public abstract class Animal {

	protected int health;
	
	protected boolean alive = true;
	
	public final void regenerate(int add)
	{
		if(alive) {
			health += add;
			if(health > this.getMaxHealth())
				health = this.getMaxHealth();
		}
	}
	
	public final void damage(int damage)
	{
		if(alive) {
			health -= damage;
			if(health <= 0)
				this.alive = false;
		}
	}
	
	public final boolean isAlive()
	{
		return this.alive;
	}
	
	public void setAlive(boolean alive)
	{
		this.alive = alive;
	}
	
	public int getHealth()
	{
		return this.health;
	}
	
	public abstract char getTypeChar();
	public abstract String getType();
	public abstract int getMaxHealth();
	
}
