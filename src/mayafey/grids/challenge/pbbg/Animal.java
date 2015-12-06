package mayafey.grids.challenge.pbbg;

public abstract class Animal {

	protected int health;
	protected int maxhealth;
	
	protected boolean alive = true;
	
	public final void heal(int add)
	{
		if(alive) {
			regenerate(add);
			if(health > maxhealth)
				health = maxhealth;
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
	
	public void regenerate(int add)
	{
		health += add;
		
	}
	
	public void damage(int damage)
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
	
}
