package units;

import javax.swing.ImageIcon;

import civilizations.Player;
//UNIT AND ITS PARAMETERS
public class Scout extends Unit {

	public Scout() {
		super.unitID = 11;
		super.hitpoints = 20;
		super.attackRating = 5;
		super.productionCost = 25;
		super.location = null;
		super.ranged = false;
		super.alive = true;
		super.fortified = false;
		super.maxMovement = 3;
		super.movesLeft = 3;
		// IGNORES TERRAIN IN CIV 5 (SO I MADE IT 3 MOVEMENT INSTEAD)
		super.maintenance = 1;
		super.isAir = false;
		super.isGround = true;
		super.isNaval = false;
		super.isSelected = false;
		super.unitName = "Scout";
		super.unitImageIcon = new ImageIcon(Unit.class.getClassLoader().getResource("units/resources/Scout.png"));
	}

	public Scout(Player player) {
		super.unitID = 11;
		super.hitpoints = 20;
		super.attackRating = 5;
		super.productionCost = 25;
		super.location = null;
		super.ranged = false;
		super.alive = true;
		super.fortified = false;
		super.maxMovement = 3;
		// IGNORES TERRAIN IN CIV 5 (SO I MADE IT 3 MOVEMENT INSTEAD)
		super.maintenance = 1;
		super.isAir = false;
		super.isGround = true;
		super.isNaval = false;
		super.isSelected = false;
		super.unitName = "Scout";
		super.unitImageIcon = new ImageIcon(Unit.class.getClassLoader().getResource("units/resources/Scout.png"));
		super.owner = player;
		owner.addUnit(this);
	}
}
