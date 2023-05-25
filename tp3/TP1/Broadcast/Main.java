
import io.jbotsim.core.Topology;
import io.jbotsim.ui.JViewer;

public class Main {

    public static void main(String[] args) {
        Topology tp = new Topology();
        tp.setDefaultNodeModel(BroadcastNode.class);
        tp.setTimeUnit(1000); // 1 ronde = 1000ms
        new JViewer(tp);
        tp.start();
    }
}