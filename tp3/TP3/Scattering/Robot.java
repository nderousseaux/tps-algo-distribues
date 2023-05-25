import io.jbotsim.core.Node;

import io.jbotsim.core.Point;
import java.util.ArrayList;
import java.util.Random;


public abstract class Robot extends Node{

	ArrayList<Point> locations;
	Point target;

	static double EPS = 0.000001;

	public abstract Point compute(Point myLocation, ArrayList<Point> robots);

	@Override
	public void onPreClock() {   
		locations = new ArrayList<Point>();
		for (Node node : getTopology().getNodes() )
		{
			if(node.equals(this)) continue;
			
			// ici on n'ajoute qu'une seul fois chaque point
			// pour que le robot ne voit pas combien de robots
			// sont localisé sur un point donné
			// On peut commenter cette ligne pour donné accès au robot
			// à la multiplicité
			if(locations.contains(node.getLocation())) continue;

			locations.add(node.getLocation());
		}
		// le seul doublon possible est sur la position courante
		// du robot, pour le robot puisse savoir s'il est tout seul ou non
		locations.add(this.getLocation());
	}

	@Override
	public void onClock(){   
		target = compute(getLocation(), locations);
	}

	@Override
	public void onPostClock(){ 
		int minDistance = 400;
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