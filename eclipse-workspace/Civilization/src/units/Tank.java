package units;

public class Tank extends Unit {

	public Tank() {
		super.hitpoints = 180;
		super.attackRating = 48;
		super.productionCost = 425;
		super.location  = null;
		super.ranged = false;
		super.alive = true;
		super.fortified = false;
		super.maxMovement = 2;
	}
}