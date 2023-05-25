import io.jbotsim.core.Color;
import io.jbotsim.core.Node;

public class PPElectionV1 extends PPNode {
    @Override
    public void onStart() {
        setColor(Color.blue);
    }
    @Override
    public void onSelection() {
        // Laisser vide
    }

    // Complétez la fonction interactWith dans le fichier PPElection.java pour implémenter un algorithme 
    // d'élection de leader.
    @Override
    public void interactWith(Node responder) {
        // Si il y a deux bleus, l'un des deux devient rouge
        if (getColor() == Color.blue && responder.getColor() == Color.blue) {
            setColor(Color.red);
        }
    }
}
