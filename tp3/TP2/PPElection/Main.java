
import io.jbotsim.core.Topology;
import io.jbotsim.core.Link;
import io.jbotsim.ui.JViewer;
import io.jbotsim.io.TopologySerializer;
import io.jbotsim.io.format.plain.PlainTopologySerializer;

public class Main {

    public static void load(Topology tp, String filename) {
        TopologySerializer serializer = new PlainTopologySerializer();
        String fileContent = tp.getFileManager().read(filename);
        serializer.importFromString(tp, fileContent);
    }

    public static void complet(Topology tp, int nbNodes) {
        for(int i = 0; i < nbNodes; i++)
        {
            tp.addNode(
                250+150*Math.cos(2*i*Math.PI/nbNodes), 
                200+150*Math.sin(2*i*Math.PI/nbNodes));
        }

        var nodes = tp.getNodes();
        for(int i = 0; i < nbNodes-1; i++)
        {
            for(int j = i+1; j < nbNodes; j++)
            {
                tp.addLink(new Link(
                    nodes.get(i),
                    nodes.get(j)));
            }
        }
    }
    public static void main(String[] args) {
        Topology tp = new Topology();
        tp.setDefaultNodeModel(PPElectionV2.class);
        tp.setTimeUnit(10);

        // Main.complet(tp, 10);
        Main.load(tp, "topo1.plain");

        new PPScheduler(tp);
        new JViewer(tp);
        tp.start();
    }
}
