package units;

public class GatlingGun extends Unit {

	public GatlingGun() {
		super.hitpoints = 55;
		super.attackRating = 7;
		super.productionCost = 0;
		super.location  = null;
		super.ranged = false;
		super.alive = true;
		super.fortified = false;
		super.maxMovement = 2;
	}
}
