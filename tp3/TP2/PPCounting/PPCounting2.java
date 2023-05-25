import io.jbotsim.core.Color;
import io.jbotsim.core.Node;


// Version 2 : Définissons maintenant un second protocole de comptage. Il ne sera
// pas encore parfait, mais sera mieux que le précédent pour (au moins) deux
// aspects :
//
// il a une initialisation uniforme, c.à.d. qu'il n'a pas besoin de noeud
// distingué (tout le monde a le même état initialement), et il est susceptible de
// réussir dans plus de graphes. Son principe est le suivant : initialement chaque
// noeud est un compteur dont le décompte est égal à 1 (il s'est compté lui-même).
// Puis, lorsque deux compteurs non-nuls interagissent, ils s'affrontent. Le
// gagnant (disons le plus grand compteur) reste compteur et l'autre n'est plus
// compteur. De plus, le perdant se fait voler son décompte par le gagnant, qui
// cumule maintenant les deux décomptes.
//
// Implémenter ce protocole dans un fichier PPCounting2.java

public class PPCounting2 extends PPNode {
    @Override
    public void onStart() {
        // Au début, tout le monde est compteur
        setColor(Color.green);
    }

    @Override
    public void onSelection() {
    }

    @Override
    public void interactWith(Node responder) {
        // Si deux compteurs se rencontrent
        if (getColor() == Color.green && responder.getColor() == Color.green) {
            // On garde le plus grand id
            PPCounting2 greater = ((PPCounting2)responder).getID() > getID() ? (PPCounting2)responder : this;
            PPCounting2 smaller = ((PPCounting2)responder).getID() > getID() ? this : (PPCounting2)responder;

            // Le plus grand prend le compte du plus petit
            greater.nbNodes += smaller.nbNodes;

            // Le plus petit devient non-compteur
            smaller.setColor(Color.red);
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
