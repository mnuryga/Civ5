package map;
//ABSTRACT CLASS FOR TILE -- CONTAINS GETTERS AND SETTERS, METHODS, AND MAKES IT EASIER TO CREATE THE SUBCLASS TILES
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import civilizations.Player;
import units.Unit;

public abstract class Tile {
	// MAP GENERATION CONSTANTS
	protected final static int MAP_SIZE = 20;
	protected final static int TEXTURE_SIZE = 50;
	protected final static int GENERATION_ITERATIONS = 7;
	protected final static int GENERATION_SIZE_MULTIPLIER = 5;
	protected final static int SAND_GENERATION_ITERATIONS = 2;
	protected final static int SAND_GENERATION_SIZE_MULTIPLIER = 2;
	protected final static int FOREST_GENERATION_ITERATIONS = 2;
	protected final static int FOREST_GENERATION_SIZE_MULTIPLIER = 2;
	protected int terrainID; // 0 - water, 1 - grassTile, 2 - sandTile, 3 - MountainTile, 4 - Forest
	protected int movesRequired;
	protected double productionBase;
	protected double productionPotential;
	protected double foodBase;
	protected double foodPotential;
	protected double goldBase;
	protected double goldPotential;
	protected double happinessBase;
	protected double happinessPotential;
	protected double scienceBase;
	protected double sciencePotential;
	protected boolean crossable;
	protected boolean defaultCrossable;
	protected boolean occupied;
	protected boolean improved;
	protected boolean tileVisible = false;
	protected Player owner;
	protected int[] $location = new int[2];
	protected ImageIcon tileImageIcon;
	protected JLabel tileLabel;
	protected boolean isCity = false;
	protected Unit unitOnTile;
	protected static Tile[][] $map = new Tile[MAP_SIZE][MAP_SIZE];
	
	//GENERATES THE MAP AS A SERIES OF INTEGERS REFERRING TO THE TERRAIN IDS
	public static void generateMap() {
		Random rand = new Random();
		int[][] $genArray = new int[MAP_SIZE][MAP_SIZE]; // Makes a temp int array to store information for map gen
		for (int iterations = 0; iterations < GENERATION_ITERATIONS; iterations++) {
			// Base generation for grass and mountain
			int tempX = rand.nextInt(Tile.getMAP_SIZE() - 8) + 5;
			int tempY = rand.nextInt(Tile.getMAP_SIZE() - 8) + 5;
			int genType = rand.nextInt(6);
			// There are six different foundations for mountains
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

		// Creates grass tiles around mountain tiles with radius
		// GENERATION_SIZE_MULTIPLIER
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

		// Reads the temp int array and makes the $map array with new tiles
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

		// Generates sand with the same template as the mountains
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

		// Turns 10% of grass tiles into forest tiles
		for (int i = 0; i < $map.length; i++) {
			for (int j = 0; j < $map.length; j++)
				if ($map[i][j].getTerrainID() > 0)
					if (rand.nextInt(100) + 1 > 95 && $map[i][j].getTerrainID() == 1)
						$map[i][j] = new ForestTile();
		}

		// Generates forest tiles the same way as mountain and sand
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

	public boolean isTileVisible() {
		return this.tileVisible;
	}

	public void setTileVisible(boolean statement) {
		this.tileVisible = statement;
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

	public boolean isDefaultCrossable() {
		return defaultCrossable;
	}

	public void setDefaultCrossable(boolean defaultCrossable) {
		this.defaultCrossable = defaultCrossable;
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

	public double getHappinessBase() {
		return happinessBase;
	}

	public void setHappinessBase(double happinessBase) {
		this.happinessBase = happinessBase;
	}

	public double getHappinessPotential() {
		return happinessPotential;
	}

	public void setHappinessPotential(double happinessPotential) {
		this.happinessPotential = happinessPotential;
	}

	public double getScienceBase() {
		return scienceBase;
	}

	public void setScienceBase(double scienceBase) {
		this.scienceBase = scienceBase;
	}

	public double getSciencePotential() {
		return sciencePotential;
	}

	public void setSciencePotential(double sciencePotential) {
		this.sciencePotential = sciencePotential;
	}
}
