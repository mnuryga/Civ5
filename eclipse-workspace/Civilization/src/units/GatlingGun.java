package units;

public class GatlingGun extends Unit {

	public GatlingGun() {
		super.hitpoints = 60;
		super.attackRating = 33;
		super.productionCost = 225;
		super.location  = null;
		super.ranged = false;
		super.alive = true;
		super.fortified = false;
		super.maxMovement = 2;
	}
}