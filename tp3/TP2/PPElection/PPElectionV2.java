import io.jbotsim.core.Color;
import io.jbotsim.core.Node;

public class PPElectionV2 extends PPNode {
    @Override
    public void onStart() {
        setColor(Color.blue);
        // Au démarrage, on est le leader
        this.leader_id = getID();
    }
    @Override
    public void onSelection() {
        // Laisser vide
    }

    @Override
    public void interactWith(Node responder) {
        // Si le responder à un plus grand id_leader que nous, on met à jour notre id_leader
        if (((PPElectionV2)responder).leader_id > leader_id) {
            this.leader_id = ((PPElectionV2)responder).leader_id;
            setColor(Color.red);
        }

    }
}
