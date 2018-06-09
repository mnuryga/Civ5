package map;

import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import civilizations.Player;
import units.Unit;

public abstract class Tile {
	protected final static int MAP_SIZE = 50;
	protected final static int TEXTURE_SIZE = 50;
	protected final static int GENERATION_ITERATIONS = 30;
	protected final static int GENERATION_SIZE_MULTIPLIER = 5;
	protected final static int SAND_GENERATION_ITERATIONS = 6;
	protected final static int SAND_GENERATION_SIZE_MULTIPLIER = 2;
	protected final static int FOREST_GENERATION_ITERATIONS = 7;
	protected final static int FOREST_GENERATION_SIZE_MULTIPLIER = 2;
	protected int terrainID; // 0 - water, 1 - grassTile, 2 - sandTile, 3 - MountainTile, 4 - Forest
	protected int movesRequired;
	protected double productionBase;
	protected double productionPotential;
	protected double foodBase;
	protected double foodPotential;
	protected double goldBase;
	protected double goldPotential;
	protected boolean crossable;
	protected boolean occupied;
	protected boolean improved;
	protected Player owner;
	protected int[] $location = new int[2];
	protected ImageIcon tileImageIcon;
	protected JLabel tileLabel;
	protected boolean isCity = false;
	protected Unit unitOnTile;
	protected static Tile[][] $map = new Tile[MAP_SIZE][MAP_SIZE];

	public static void generateMap() {
		Random rand = new Random();
		/*
		 * for (int i = 0; i < $map.length; i++) { for (int j = 0; j < $map[i].length;
		 * j++) { //$map[i][j] = new WaterTile(); int temp = rand.nextInt(5); if(temp ==
		 * 0) $map[i][j] = new WaterTile(); if(temp == 1) $map[i][j] = new GrassTile();
		 * if(temp == 2) $map[i][j] = new SandTile(); if(temp == 3) $map[i][j] = new
		 * MountainTile(); if(temp == 4) $map[i][j] = new ForestTile(); int[]
		 * $tempLocation = {i, j}; $map[i][j].set$location($tempLocation); } }
		 */
		int[][] $genArray = new int[MAP_SIZE][MAP_SIZE];
		for (int iterations = 0; iterations < GENERATION_ITERATIONS; iterations++) {
			int tempX = rand.nextInt(Tile.getMAP_SIZE() - 8) + 5;
			int tempY = rand.nextInt(Tile.getMAP_SIZE() - 8) + 5;
			int genType = rand.nextInt(6);
			if (genType == 0) {
				$genArray[tempX][tempY] = GENERATION_SIZE_MULTIPLIER;
				$genArray[tempX - 1][tempY - 1] = GENERATION_SIZE_MULTIPLIER;
				$genArray[tempX - 2][tempY - 2] = GENERATION_SIZE_MULTIPLIER;
			}
			if (genType == 1) {
				$genArray[tempX][tempY] = GENERATION_SIZE_MULTIPLIER;
				$genArray[tempX + 1][tempY - 1] = GENERATION_SIZE_MULTIPLIER;
				$genArray[tempX + 2][tempY - 2] = GENERATION_SIZE_MULTIPLIER;
			}
			if (genType == 2) {
				$genArray[tempX][tempY] = GENERATION_SIZE_MULTIPLIER;
				$genArray[tempX - 1][tempY - 1] = GENERATION_SIZE_MULTIPLIER;
				$genArray[tempX - 1][tempY + 1] = GENERATION_SIZE_MULTIPLIER;
			}
			if (genType == 3) {
				$genArray[tempX][tempY] = GENERATION_SIZE_MULTIPLIER;
				$genArray[tempX + 1][tempY - 1] = GENERATION_SIZE_MULTIPLIER;
				$genArray[tempX + 1][tempY + 1] = GENERATION_SIZE_MULTIPLIER;
			}
			if (genType == 4) {
				$genArray[tempX][tempY] = GENERATION_SIZE_MULTIPLIER;
				$genArray[tempX - 1][tempY - 1] = GENERATION_SIZE_MULTIPLIER;
				$genArray[tempX - 2][tempY - 2] = GENERATION_SIZE_MULTIPLIER;
				$genArray[tempX - 3][tempY - 3] = GENERATION_SIZE_MULTIPLIER;
			}
			if (genType == 5) {
				$genArray[tempX][tempY] = GENERATION_SIZE_MULTIPLIER;
				$genArray[tempX + 1][tempY - 1] = GENERATION_SIZE_MULTIPLIER;
				$genArray[tempX + 2][tempY - 2] = GENERATION_SIZE_MULTIPLIER;
				$genArray[tempX + 3][tempY - 3] = GENERATION_SIZE_MULTIPLIER;
			}
		}

		for (int iterations = GENERATION_SIZE_MULTIPLIER; iterations > 0; iterations--) {
			for (int i = 1; i < $map.length - 1; i++)
				for (int j = 1; j < $map.length - 1; j++) {
					if ($genArray[i][j] == iterations) {
						if ($genArray[i - 1][j - 1] < iterations)
							$genArray[i - 1][j - 1] = iterations - 1;
						if ($genArray[i - 1][j] < iterations)
							$genArray[i - 1][j] = iterations - 1;
						if ($genArray[i - 1][j + 1] < iterations)
							$genArray[i - 1][j + 1] = iterations - 1;
						if ($genArray[i][j - 1] < iterations)
							$genArray[i][j - 1] = iterations - 1;
						if ($genArray[i][j + 1] < iterations)
							$genArray[i][j + 1] = iterations - 1;
						if ($genArray[i + 1][j - 1] < iterations)
							$genArray[i + 1][j - 1] = iterations - 1;
						if ($genArray[i + 1][j] < iterations)
							$genArray[i + 1][j] = iterations - 1;
						if ($genArray[i + 1][j + 1] < iterations)
							$genArray[i + 1][j + 1] = iterations - 1;
					}
				}
		}

		for (int i = 0; i < $map.length; i++)
			for (int j = 0; j < $map.length; j++) {
				if ($genArray[i][j] == 0)
					$map[i][j] = new WaterTile();
				else if ($genArray[i][j] == GENERATION_SIZE_MULTIPLIER)
					$map[i][j] = new MountainTile();
				/*
				 * else if($genArray[i][j] == GENERATION_SIZE_MULTIPLIER - 1) $map[i][j] = new
				 * ForestTile();
				 */
				else
					$map[i][j] = new GrassTile();
				int[] $tempLocation = { i, j };
				$map[i][j].set$location($tempLocation);
			}

		if (GENERATION_SIZE_MULTIPLIER - SAND_GENERATION_SIZE_MULTIPLIER > 0) {
			for (int i = 0; i < $map.length; i++)
				for (int j = 0; j < $map.length; j++)
					$genArray[i][j] = 0;

			for (int iterations = 0; iterations < SAND_GENERATION_ITERATIONS; iterations++) {
				int tempX = rand.nextInt(Tile.getMAP_SIZE() - 8) + 5;
				int tempY = rand.nextInt(Tile.getMAP_SIZE() - 8) + 5;
				int genType = rand.nextInt(6);
				if (genType == 0) {
					$genArray[tempX][tempY] = SAND_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX - 1][tempY - 1] = SAND_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX - 2][tempY - 2] = SAND_GENERATION_SIZE_MULTIPLIER;
				}
				if (genType == 1) {
					$genArray[tempX][tempY] = SAND_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX + 1][tempY - 1] = SAND_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX + 2][tempY - 2] = SAND_GENERATION_SIZE_MULTIPLIER;
				}
				if (genType == 2) {
					$genArray[tempX][tempY] = SAND_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX - 1][tempY - 1] = SAND_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX - 1][tempY + 1] = SAND_GENERATION_SIZE_MULTIPLIER;
				}
				if (genType == 3) {
					$genArray[tempX][tempY] = SAND_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX + 1][tempY - 1] = SAND_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX + 1][tempY + 1] = SAND_GENERATION_SIZE_MULTIPLIER;
				}
				if (genType == 4) {
					$genArray[tempX][tempY] = SAND_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX - 1][tempY - 1] = SAND_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX - 2][tempY - 2] = SAND_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX - 3][tempY - 3] = SAND_GENERATION_SIZE_MULTIPLIER;
				}
				if (genType == 5) {
					$genArray[tempX][tempY] = SAND_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX + 1][tempY - 1] = SAND_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX + 2][tempY - 2] = SAND_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX + 3][tempY - 3] = SAND_GENERATION_SIZE_MULTIPLIER;
				}
			}

			for (int iterations = SAND_GENERATION_SIZE_MULTIPLIER; iterations > 0; iterations--) {
				for (int i = 1; i < $map.length - 1; i++)
					for (int j = 1; j < $map.length - 1; j++) {
						if ($genArray[i][j] == iterations) {
							if ($genArray[i - 1][j - 1] < iterations)
								$genArray[i - 1][j - 1] = iterations - 1;
							if ($genArray[i - 1][j] < iterations)
								$genArray[i - 1][j] = iterations - 1;
							if ($genArray[i - 1][j + 1] < iterations)
								$genArray[i - 1][j + 1] = iterations - 1;
							if ($genArray[i][j - 1] < iterations)
								$genArray[i][j - 1] = iterations - 1;
							if ($genArray[i][j + 1] < iterations)
								$genArray[i][j + 1] = iterations - 1;
							if ($genArray[i + 1][j - 1] < iterations)
								$genArray[i + 1][j - 1] = iterations - 1;
							if ($genArray[i + 1][j] < iterations)
								$genArray[i + 1][j] = iterations - 1;
							if ($genArray[i + 1][j + 1] < iterations)
								$genArray[i + 1][j + 1] = iterations - 1;
						}
					}
			}

			for (int i = 0; i < $map.length; i++)
				for (int j = 0; j < $map.length; j++) {
					if ($genArray[i][j] > 0 && $map[i][j].getTerrainID() != 0)
						$map[i][j] = new SandTile();
					int[] $tempLocation = { i, j };
					$map[i][j].set$location($tempLocation);
				}
		}
		for (int i = 0; i < $map.length; i++) {
			for (int j = 0; j < $map.length; j++)
				if ($map[i][j].getTerrainID() > 0)
					if (rand.nextInt(100) + 1 > 95 && $map[i][j].getTerrainID() == 1)
						$map[i][j] = new ForestTile();
		}

		if (GENERATION_SIZE_MULTIPLIER - FOREST_GENERATION_SIZE_MULTIPLIER > 0) {
			for (int i = 0; i < $map.length; i++)
				for (int j = 0; j < $map.length; j++)
					$genArray[i][j] = 0;

			for (int iterations = 0; iterations < FOREST_GENERATION_ITERATIONS; iterations++) {
				int tempX = rand.nextInt(Tile.getMAP_SIZE() - 8) + 5;
				int tempY = rand.nextInt(Tile.getMAP_SIZE() - 8) + 5;
				int genType = rand.nextInt(6);
				if (genType == 0) {
					$genArray[tempX][tempY] = FOREST_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX - 1][tempY - 1] = FOREST_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX - 2][tempY - 2] = FOREST_GENERATION_SIZE_MULTIPLIER;
				}
				if (genType == 1) {
					$genArray[tempX][tempY] = FOREST_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX + 1][tempY - 1] = FOREST_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX + 2][tempY - 2] = FOREST_GENERATION_SIZE_MULTIPLIER;
				}
				if (genType == 2) {
					$genArray[tempX][tempY] = FOREST_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX - 1][tempY - 1] = FOREST_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX - 1][tempY + 1] = FOREST_GENERATION_SIZE_MULTIPLIER;
				}
				if (genType == 3) {
					$genArray[tempX][tempY] = FOREST_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX + 1][tempY - 1] = FOREST_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX + 1][tempY + 1] = FOREST_GENERATION_SIZE_MULTIPLIER;
				}
				if (genType == 4) {
					$genArray[tempX][tempY] = FOREST_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX - 1][tempY - 1] = FOREST_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX - 2][tempY - 2] = FOREST_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX - 3][tempY - 3] = FOREST_GENERATION_SIZE_MULTIPLIER;
				}
				if (genType == 5) {
					$genArray[tempX][tempY] = FOREST_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX + 1][tempY - 1] = FOREST_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX + 2][tempY - 2] = FOREST_GENERATION_SIZE_MULTIPLIER;
					$genArray[tempX + 3][tempY - 3] = FOREST_GENERATION_SIZE_MULTIPLIER;
				}
			}

			for (int iterations = FOREST_GENERATION_SIZE_MULTIPLIER; iterations > 0; iterations--) {
				for (int i = 1; i < $map.length - 1; i++)
					for (int j = 1; j < $map.length - 1; j++) {
						if ($genArray[i][j] == iterations) {
							if ($genArray[i - 1][j - 1] < iterations)
								$genArray[i - 1][j - 1] = iterations - 1;
							if ($genArray[i - 1][j] < iterations)
								$genArray[i - 1][j] = iterations - 1;
							if ($genArray[i - 1][j + 1] < iterations)
								$genArray[i - 1][j + 1] = iterations - 1;
							if ($genArray[i][j - 1] < iterations)
								$genArray[i][j - 1] = iterations - 1;
							if ($genArray[i][j + 1] < iterations)
								$genArray[i][j + 1] = iterations - 1;
							if ($genArray[i + 1][j - 1] < iterations)
								$genArray[i + 1][j - 1] = iterations - 1;
							if ($genArray[i + 1][j] < iterations)
								$genArray[i + 1][j] = iterations - 1;
							if ($genArray[i + 1][j + 1] < iterations)
								$genArray[i + 1][j + 1] = iterations - 1;
						}
					}
			}

			for (int i = 0; i < $map.length; i++)
				for (int j = 0; j < $map.length; j++) {
					if ($genArray[i][j] > 0 && $map[i][j].getTerrainID() != 0 && $map[i][j].getTerrainID() != 2)
						if (rand.nextInt(100) + 1 > 30)
							$map[i][j] = new ForestTile();
					int[] $tempLocation = { i, j };
					$map[i][j].set$location($tempLocation);
				}
		}
	}

	// GETTERS AND SETTERS

	public int getTerrainID() {
		return terrainID;
	}

	public static int getTEXTURE_SIZE() {
		return TEXTURE_SIZE;
	}

	public static int getMAP_SIZE() {
		return MAP_SIZE;
	}

	public void setTerrainID(int terrainID) {
		this.terrainID = terrainID;
	}

	public int getMovesRequired() {
		return movesRequired;
	}

	public void setMovesRequired(int movesRequired) {
		this.movesRequired = movesRequired;
	}

	public double getProductionBase() {
		return productionBase;
	}

	public void setProductionBase(double productionBase) {
		this.productionBase = productionBase;
	}

	public double getProductionPotential() {
		return productionPotential;
	}

	public void setProductionPotential(double productionPotential) {
		this.productionPotential = productionPotential;
	}

	public double getFoodBase() {
		return foodBase;
	}

	public void setFoodBase(double foodBase) {
		this.foodBase = foodBase;
	}

	public double getFoodPotential() {
		return foodPotential;
	}

	public void setFoodPotential(double foodPotential) {
		this.foodPotential = foodPotential;
	}

	public double getGoldBase() {
		return goldBase;
	}

	public void setGoldBase(double goldBase) {
		this.goldBase = goldBase;
	}

	public double getGoldPotential() {
		return goldPotential;
	}

	public void setGoldPotential(double goldPotential) {
		this.goldPotential = goldPotential;
	}

	public boolean isCrossable() {
		return crossable;
	}

	public void setCrossable(boolean crossable) {
		this.crossable = crossable;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public boolean isImproved() {
		return improved;
	}

	public void setImproved(boolean improved) {
		this.improved = improved;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public int[] get$location() {
		return $location;
	}

	public JLabel getTileLabel() {
		return tileLabel;
	}

	public ImageIcon getTileImageIcon() {
		return tileImageIcon;
	}

	public boolean isCity() {
		return isCity;
	}

	public void setIsCity(boolean isCity) {
		this.isCity = isCity;
	}

	public void setTileImageIcon(ImageIcon tileImageIcon) {
		this.tileImageIcon = tileImageIcon;
	}

	public void setTileLabel(JLabel tileLabel) {
		this.tileLabel = tileLabel;
	}

	public void set$location(int[] $location) {
		this.$location = $location;
	}

	public static Tile[][] get$map() {
		return $map;
	}

	public void set$map(Tile[][] $map) {
		Tile.$map = $map;
	}

	public static int getMapSize() {
		return MAP_SIZE;
	}

	public static int getTextureSize() {
		return TEXTURE_SIZE;
	}

	public static int getGenerationIterations() {
		return GENERATION_ITERATIONS;
	}

	public static int getGenerationSizeMultiplier() {
		return GENERATION_SIZE_MULTIPLIER;
	}

	public static int getSandGenerationIterations() {
		return SAND_GENERATION_ITERATIONS;
	}

	public static int getSandGenerationSizeMultiplier() {
		return SAND_GENERATION_SIZE_MULTIPLIER;
	}

	public static int getForestGenerationIterations() {
		return FOREST_GENERATION_ITERATIONS;
	}

	public static int getForestGenerationSizeMultiplier() {
		return FOREST_GENERATION_SIZE_MULTIPLIER;
	}

	public Unit getUnitOnTile() {
		return unitOnTile;
	}

	public void setUnitOnTile(Unit unitOnTile) {
		this.unitOnTile = unitOnTile;
	}

	public void setCity(boolean isCity) {
		this.isCity = isCity;
	}
	// public TIle getTileFromID(int ID){
	// if(ID = 0
	// return GrassTile;
	// }
}
