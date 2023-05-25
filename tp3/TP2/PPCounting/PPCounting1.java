import io.jbotsim.core.Color;
import io.jbotsim.core.Node;
// Dans cette version, le noeud selectionné à un rôle différent des autres. 
// Il est le compteur et son rôle est de compter les noeuds avec lesquels il interagit.
// Les autres sont initialement dans l'état non compté, puis à mesure que le compteur interagit avec eux, 
// ils passent dans l'état compté.
// On peut représenter les trois états (i.e. compteur, compté, et non compté) par trois couleurs, 
// et le décompte par une variable entière que seul le compteur utilise.
// Ce protocole est très limité, et c'est normal ! On ne vous demande pas de l'améliorer.
// → Astuce de visualisation: il est possible de redéfinir la méthode toString() 
// pour qu'elle renvoie le décompte lorsqu'elle est appelée sur le noeud compteur (et une chaine vide sur les autres noeuds).
// Cette méthode sera utilisée automatiquement par JBotSim pour remplir l'étiquette des noeuds.

public class PPCounting1 extends PPNode {
    @Override
    public void onStart() {
        setColor(Color.red);
        // Complétez
    }
    @Override
    public void onSelection() {
        // On change l'état du noeud sélectionné il devient compteur
        setColor(Color.green);
    }
    @Override
    public void interactWith(Node responder) {
        if (getColor() == Color.green && responder.getColor() == Color.red) {
            // On change l'état du noeud avec lequel on interagit il devient compté
            responder.setColor(Color.blue);
            // On incrémente le décompte
            nbNodes++;
        }
    }

    @Override
    public String toString() {
        if (getColor() == Color.green) {
            return Integer.toString(nbNodes);
        } else {
            return "";
        }
    }
}
