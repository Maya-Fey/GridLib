package mayafey.grids.challenge.pbbg;

public abstract class Animal {

	protected int health;
	
	protected boolean alive = true;
	
	public final void heal(int add)
	{
		if(alive) {
			regenerate(add);
			if(health > this.getMaxHealth())
				health = this.getMaxHealth();
		}
	}
	
	public final void attack(int damage)
	{
		if(alive) {
			damage(damage);
			if(health <= 0)
				this.alive = false;
		}
	}
	
	private void regenerate(int add)
	{
		health += add;
		
	}
	
	private void damage(int damage)
	{
		health -= damage;
	}
	
	public final boolean isAlive()
	{
		return this.alive;
	}
	
	public final void setAlive(boolean alive)
	{
		this.alive = alive;
	}
	
	public abstract String getType();
	public abstract int getMaxHealth();
	
}
