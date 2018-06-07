package units;

public class Marine extends Unit {

	public Marine() {
		super.unitID = 13;
		super.hitpoints = 150;
		super.attackRating = 43;
		super.productionCost = 400;
		super.location = null;
		super.ranged = false;
		super.alive = true;
		super.fortified = false;
		super.maxMovement = 2;
		super.maintenance = 5;
		super.isAir = false;
		super.isGround = true;
		super.isNaval = false;
		super.isSelected = false;
		super.unitName = "Marine";
	}
}
