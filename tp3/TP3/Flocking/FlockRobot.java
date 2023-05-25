import io.jbotsim.core.Node;

import io.jbotsim.core.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class FlockRobot extends Robot{

	public Point force;
	
	public void onStart() {
		force = new Point(Math.random()*2 - 1, Math.random()*2-1);
	}

	/** Add two points */
	static private Point addP(Point a, Point b) {
		return new Point(a.getX()+b.getX(), a.getY()+b.getY());
	}

	/** Substract two points */
	static private Point subP(Point a, Point b) {
		return new Point(
			(b.getX()-a.getX()), 
			(b.getY()-a.getY())
			);
	}
	/** When we consider the point as a vector, this function change its length */
	static private Point vectWithLength(Point a, double length) {
		double d = (new Point(0,0)).distance(a);
		if(d == 0) {
			return new Point(Math.random()*2*length-length, Math.random()*2*length-length);
		}
		return new Point(
			a.getX()/d*length, 
			a.getY()/d*length
			);
	}

	public Point computeS(Point myLocation, ArrayList<Point> locations) {  
		// Modifier le vecteur de force ici:
		

		
		// On normalise le vecteur de force avant de retourner la destination
		force = vectWithLength(force, 3);
		return addP(myLocation, force);
	}


}