
import io.jbotsim.core.Topology;
import io.jbotsim.core.Link;
import io.jbotsim.core.Node;
import io.jbotsim.ui.JViewer;
import io.jbotsim.core.event.ClockListener;
import io.jbotsim.core.Point;
import io.jbotsim.core.Color;


public class Main {

	// Start the simulation
	public static void main(String[] args){


        Topology tp = new Topology();

		// Robots cannot communicate
		tp.disableWireless();

		// Here we remove the sensing range since the robots have unlimited visibility
		tp.setSensingRange(0);

		Node shark = new Node();
		tp.addNode(-1,-1,shark);
		shark.setColor(Color.red);

        tp.setDefaultNodeModel(FlockRobot.class);


		// Add 20 Robots to the topology (with random positions)
		for (int i = 0; i < 20; i++)
			tp.addNode(-1,-1);

		//The clock click every 0.5 sec (so that you can see the evolution slowly)
        tp.setTimeUnit(20);

        JViewer jv = new JViewer(tp);
    	jv.getJTopology().addBackgroundPainter(new AreaBackground());

		// We pause the simulation 
		// (to start it, you'll have to right click on the window and resume it)
		//tp.start();
	}
}