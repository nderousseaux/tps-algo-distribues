import io.jbotsim.core.Node;

import io.jbotsim.core.Point;
import java.util.ArrayList;
import java.util.Random;


public abstract class Robot extends Node{

	ArrayList<Point> locations;
	Point target;

	public abstract Point compute(Point myLocation, ArrayList<Point> robots);

	@Override
	public void onPreClock() {   
		locations = new ArrayList<Point>();
		for (Node node : getTopology().getNodes() )
		{
			locations.add(node.getLocation());
		}
	}

	@Override
	public void onClock(){   
		target = compute(getLocation(), locations);
	}

	@Override
	public void onPostClock(){ 
		int minDistance = 15;
		if(distance(target) < minDistance) {
			this.setLocation(target);
		}
		else 
		{
			setDirection(target);
			move(minDistance);
		}
	}


}