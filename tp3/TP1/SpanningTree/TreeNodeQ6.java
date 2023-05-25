import io.jbotsim.core.Message;
import io.jbotsim.core.Color;
import io.jbotsim.core.Node;
import java.util.ArrayList;

/*
__Question 6.__ La racine doit ensuite informer chaque noeud du nombre de
noeuds total.
*/

public class TreeNodeQ6 extends Node {

    // On utilise un champ parent de type Node pour mémoriser le noeud parent
    private Node parent;
    private ArrayList<Node> children;
    private int nbEnd = 0; // Nombre de fils ayant terminé
    private int nbNodes = 0; // Nombre de noeuds total
    private int time_red = -1;

    @Override
    public void onStart() { // Initialisation par défaut
        setColor(Color.green); // Non informé
        getLinks().forEach(l -> l.setWidth(1));
        children = new ArrayList<Node>();
    }

    @Override
    public void onSelection() { // Noeud sélectionné
        setColor(Color.red); // Informé
        sendAll(new Message("Mon message"));
    }

    @Override
    public void onMessage(Message message) {
        if (getColor() == Color.green) { // Si non-informé
            setColor(Color.red); // Devient informé
            // On choisit le voisin qui nous a envoyé le message comme parent
            parent = message.getSender();
            // On met l'arête en gras
            getCommonLinkWith(parent).setWidth(3);
            sendAll(new Message(message.getContent()));
            // On enregistre le temps de réception du message
            time_red = getTime();

            // On met au courant le parent
            send(parent, new Message("Yo", "TREE"));
        }
        else if (message.getFlag().equals("TREE")){
            // On ajoute le noeud qui nous a envoyé le message à la liste des
            // enfants
            children.add(message.getSender());
            // On met l'arête en gras
            getCommonLinkWith(message.getSender()).setWidth(3);
        }

        if (message.getFlag().equals("END")){
            int n = (int)message.getContent();
            nbNodes += n;
            // On augmente le nombre d'enfant ayant terminé 
            nbEnd++;
            if (nbEnd == children.size()){
                if (parent == null) {
                    nbNodes++;
                    setColor(Color.yellow);
                    System.out.println("Nombre de noeuds total : " + nbNodes);
                    // ON envoie un message à tous les noeuds
                    sendAll(new Message(nbNodes, "TOT"));
                }
                else {
                    // On informe le parent qu'on a terminé
                    send(parent, new Message(nbNodes+1, "END"));
                }
            }

        }
        if (message.getFlag().equals("TOT")){
            nbNodes = (int)message.getContent();
            System.out.println("Je suis le noeud " + getID() + " et je sais qu'il y a " + nbNodes + " noeuds");
            // On envoie le nombre de noeuds total à tous les enfants
            for (Node n : children){
                send(n, new Message(nbNodes, "TOT"));
            }
        }
    }
    
    @Override
    public void onClock() {
        if (time_red == -1)
            return;

        // Si le noeud n'a pas reçu de message depuis 2 tours
        if (getTime() - time_red > 2 && children.size() == 0 && getColor() != Color.blue) {
            // On met la couleur en bleu
            setColor(Color.blue);

            // Alors c'est une feuille, on envoie au parent un message
            send(parent, new Message(1, "END"));
        }
    }
}
