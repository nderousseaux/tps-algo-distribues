import io.jbotsim.core.Message;
import io.jbotsim.core.Color;
import io.jbotsim.core.Node;
import java.util.ArrayList;

/*
Faites maintenant en sorte que les noeuds détectent le fait qu'ils n'auront pas
d'enfants dans l'arbre (en les colorant d'une couleur différente par exemple).
Vous pouvez utiliser `getTime()` pour connaître le numéro de ronde actuelle.
Vous pouvez surcharger `onClock()` pour exécuter un code spécifique à chaque
ronde (la méthode sera appelée juste après la réception des messages envoyés à
la ronde précédente).
*/

public class TreeNodeQ3 extends Node {

    // On utilise un champ parent de type Node pour mémoriser le noeud parent
    private Node parent;
    private ArrayList<Node> children;
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
    }
    
    @Override
    public void onClock() {
        if (time_red == -1)
            return;

        // Si le noeud n'a pas reçu de message depuis 2 tours
        if (getTime() - time_red > 2 && children.size() == 0){
            // On met la couleur en bleu
            setColor(Color.blue);
        }
    }
}
