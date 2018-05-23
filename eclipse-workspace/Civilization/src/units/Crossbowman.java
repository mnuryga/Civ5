package units;

public class Crossbowman extends Unit {

	public Crossbowman() {
		super.hitpoints = 40;
		super.attackRating = 7;
		super.productionCost = 0;
		super.location  = null;
		super.ranged = false;
		super.alive = true;
		super.fortified = false;
		super.maxMovement = 2;
	}
}
