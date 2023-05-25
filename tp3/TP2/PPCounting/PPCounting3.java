import io.jbotsim.core.Color;
import io.jbotsim.core.Node;

//Comment améliorer l'algorithme en une version 3 pour qu'il fonctionne dans
//tous les graphes connexes (avec un ordonnanceur aléatoire).

public class PPCounting3 extends PPNode {
    @Override
    public void onStart() {
        // Au début, tout le monde est compteur
        setColor(Color.green);
        idLeader = getID();
    }

    @Override
    public void onSelection() {
    }

    @Override
    public void interactWith(Node responder) {
        // Si deux compteurs se rencontrent
        if (getColor() == Color.green && responder.getColor() == Color.green) {
            // On garde le plus grand id
            if (responder.getID() > idLeader) {
                idLeader = responder.getID();
            }
            // On devient pas compteur
            setColor(Color.red);
        }

    }

    @Override
    public String toString() {
        if (getColor() == Color.green) {
            return Integer.toString(nbNodes);
        } else {
            return "Pas compteur";
        }
    }
}
