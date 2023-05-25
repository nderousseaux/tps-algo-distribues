import io.jbotsim.core.Node;

import io.jbotsim.core.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public abstract class Robot extends Node{

	ArrayList<Point> locations;
	Point target;

	static double EPS = 0.000001;

	public abstract Point compute(Point myLocation, List<Node> robots);

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
		target = compute(getLocation(), getTopology().getNodes());
	}

	@Override
	public void onPostClock(){ 
		int minDistance = 20;
		if(distance(target) < minDistance) {
			this.setLocation(target);
		}
		else 
		{
			setDirection(target);
			move(minDistance);
		}
		wrapLocation();
	}


}