package leaders;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

public class Italy extends Leader {
	// mussolini
	// +2 food
	// +1 production

	public Italy() {
		leaderName = "Mussolini";
		abbrevLeaderName = "The Italians";
		super.leaderID = 1;
		color = Color.GREEN;
		cityNames = new ArrayList<String>(Arrays.asList("Rome", "Milan", "Turin", "Florence", "Naples", "Venice"));

	}

	public String getName() {
		return leaderName;
	}
}
