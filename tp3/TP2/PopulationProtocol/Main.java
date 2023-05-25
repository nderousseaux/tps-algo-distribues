
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

    public static void main(String[] args) {
        Topology tp = new Topology();
        tp.setDefaultNodeModel(PPNodeAlgo1.class);
        tp.setTimeUnit(100);
        Main.load(tp, "topo1.plain");
        new PPScheduler(tp);
        new JViewer(tp);
        tp.start();
    }
}