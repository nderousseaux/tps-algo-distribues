import io.jbotsim.core.Color;
import io.jbotsim.core.Node;

public class PPElection extends PPNode {
    @Override
    public void onStart() {
        setColor(Color.blue);
    }
    @Override
    public void onSelection() {
        // Laisser vide
    }
    @Override
    public void interactWith(Node responder) {
        // Completez
    }
}