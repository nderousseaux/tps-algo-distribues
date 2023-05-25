
import io.jbotsim.core.Topology;
import io.jbotsim.ui.JViewer;

public class Main {

    public static void main(String[] args) {
        Topology tp = new Topology();
        tp.setDefaultNodeModel(TreeNodeQ6.class);
        tp.setTimeUnit(500); // 1 ronde = 500ms

        // On peut créer quelque noeuds placé aléatoirement, n'hesitez pas à changer cette partie comme vous le désirez (ce n'est pas très grave si le graphe n'est pas connexe)

        for(int i = 0; i < 40; i++) {
            tp.addNode(-1, -1); // -1 sera remplacer par un nombre aléatoire
        }

        new JViewer(tp);
        tp.start();
    }
}
