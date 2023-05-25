
import io.jbotsim.core.Topology;
import io.jbotsim.ui.JViewer;
import io.jbotsim.core.Node;
import io.jbotsim.core.Link;

public class Main {

    public static void main(String[] args) {
        Topology tp = new Topology();
        tp.setOrientation(Link.Orientation.DIRECTED);
        tp.setCommunicationRange(0);
        tp.setDefaultNodeModel(ElectionNode.class);

        int nbNodes = 6;

        for(int i = 0; i < nbNodes; i++)
        {
            tp.addNode(
                250+150*Math.cos(2*i*Math.PI/nbNodes), 
                200+150*Math.sin(2*i*Math.PI/nbNodes));
        }

        var nodes = tp.getNodes();
        for(int i = 0; i < nbNodes; i++)
        {
            tp.addLink(new Link(
                nodes.get(i),
                nodes.get((i+1) % nbNodes), 
                Link.Orientation.DIRECTED));
        }

        tp.setTimeUnit(500); // 1 ronde = 500ms
        new JViewer(tp);
        tp.start();
    }
}