import io.jbotsim.core.Message;
import io.jbotsim.core.Color;
import io.jbotsim.core.Node;

/*
Adaptez le code pour construire un arbre couvrant, tel que lorsqu'un noeud est
informé par un noeud voisin, il choisit ce voisin comme parent. On pourra 
utiliser un champ parent de type Node pour mémoriser le noeud parent.
(voir la documentation de 
[`Message`](https://jbotsim.io/javadoc/1.2.0/io/jbotsim/core/Message.html) pour
récupérer l''envoyeur d'un message).

_Visualisation:_ Une fois le noeud parent connu, vous pouvez utiliser la
méthode `getCommonLinkWith()` pour obtenir le lien qui vous relie à lui, et la
méthode `setWidth()` sur ce dernier pour mettre l'arête en gras.
*/

public class TreeNodeQ1 extends Node {

    // On utilise un champ parent de type Node pour mémoriser le noeud parent
    private Node parent;

    @Override
    public void onStart() { // Initialisation par défaut
        setColor(Color.green); // Non informé
        getLinks().forEach(l -> l.setWidth(1));
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
        }
    }
}
