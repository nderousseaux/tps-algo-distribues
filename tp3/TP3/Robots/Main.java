
import io.jbotsim.core.Topology;
import io.jbotsim.core.Link;
import io.jbotsim.core.Node;
import io.jbotsim.ui.JViewer;
import io.jbotsim.core.event.ClockListener;
import io.jbotsim.core.Point;

class IsGatherClockListener implements ClockListener {

	private Topology tp;
	public IsGatherClockListener(Topology tp) {
		this.tp = tp;
	}
	public void onClock() {
		
		int mult = 0;
		
		Point p = tp.findNodeById(0).getLocation();
		for (Node node : tp.getNodes() )
		{
			if(node.getLocation().distance(p) == 0) mult++;
		}

		System.out.print("gathered? ");
		System.out.print(mult == tp.getNodes().size());
		System.out.print(" (");
		System.out.print(mult);
		System.out.println(")");
	}
}


public class Main {

	// Start the simulation
	public static void main(String[] args){


        Topology tp = new Topology();
		tp.addClockListener(new IsGatherClockListener(tp));

		// Robots cannot communicate
		tp.disableWireless();

		// Here we remove the sensing range since the robots have unlimited visibility
		tp.setSensingRange(0);

        tp.setDefaultNodeModel(GatherRobot.class);


		// Add 20 Robots to the topology (with random positions)
		for (int i = 0; i < 20; i++)
			tp.addNode(-1,-1);

		//The clock click every 0.5 sec (so that you can see the evolution slowly)
        tp.setTimeUnit(500);

        new JViewer(tp);
		// We pause the simulation 
		// (to start it, you'll have to right click on the window and resume it)
		tp.start();
	}
}