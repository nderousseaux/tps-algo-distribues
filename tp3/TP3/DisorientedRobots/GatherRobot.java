import io.jbotsim.core.Node;

import io.jbotsim.core.Point;
import java.util.ArrayList;
import java.util.Random;


public class GatherRobot extends Robot{
	
	public Point compute(Point myLocation, ArrayList<Point> locations) {   
		Point target = locations.get(0);
		for(Point r : locations)
		{
			if(r.getX() > target.getX() || (r.getX() == target.getX() && r.getY() > target.getY()))
			{
				target = r;
			}
		}
		return target;
	}

}