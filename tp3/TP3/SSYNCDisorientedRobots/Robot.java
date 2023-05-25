import io.jbotsim.core.Node;

import io.jbotsim.core.Point;
import java.util.ArrayList;
import java.util.Random;


public abstract class Robot extends Node{

	ArrayList<Point> locations;
	Point target;
	Point myLocation;
	double angle;

	public abstract Point compute(Point myLocation, ArrayList<Point> robots);

	/** rotate a point around the origin */
	public Point rotatePoint(Point p, double angle) {

		while(angle < 0) angle += Math.PI * 2;
		while(angle >= Math.PI * 2) angle -= Math.PI * 2;

		angle /= 2 * Math.PI;


		double cos_a = 1;//Math.cos(angle);
		double sin_a = 0;//Math.sin(angle);
		if(angle > 1.0/8 && angle <= 3.0/8) {
			cos_a = 0;
			sin_a = 1;
		} else
		if(angle > 3.0/8 && angle < 5.0/8) {
			cos_a = -1;
			sin_a = 0;
		}else
		if(angle >= 5.0/8 && angle < 7.0/8) {
			cos_a = 0;
			sin_a = -1;
		}

		double xnew = p.getX() * cos_a - p.getY() * sin_a;
		double ynew = p.getX() * sin_a + p.getY() * cos_a;

		return new Point(xnew, ynew);
	}

	@Override
	public void onPreClock() {   
		locations = new ArrayList<Point>();
		this.angle = Math.random()*Math.PI*2;
		
		for (Node node : getTopology().getNodes() )
		{
			Point p = node.getLocation();
			locations.add(rotatePoint(p, angle));
		}
		myLocation = rotatePoint(getLocation(), angle);

	}

	@Override
	public void onClock(){   
		target = rotatePoint(compute(myLocation, locations), -angle);
	}

	@Override
	public void onPostClock(){ 
		if(Math.random() < 2.0/3) return; // 2 chances sur 3 de ne pas s'activer

		int minDistance = 20;
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