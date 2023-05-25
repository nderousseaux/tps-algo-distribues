import io.jbotsim.core.Message;
import io.jbotsim.core.Color;
import io.jbotsim.core.Node;
import java.util.ArrayList;
/*
__Question 2.__ Faites maintenant en sorte que les noeuds apprennent la liste 
de leurs enfants dans l'arbre. Vous pouvez utiliser un champ children de type
[`ArrayList`](https://itsallbinary.com/javadoc-examples/java/docs/api/examples/index.html?java/util/java.util.ArrayList.html).
Vous pouvez utiliser des messages de différents types en utilisant des flags. 

```java
//envoie:
sendAll(new Message("message de controle", "TREE"));

//reception
message.getFlag().equals("TREE"); // est vrai
```

Il est aussi possible d'envoyer un message à un seul noeud voisin plutôt qu'à
tous en utilisant la bonne méthode (voir la documentation de `Node` dont le
lien est sur le Readme à la racine des TPs).
*/

public class TreeNodeQ2 extends Node {

    // On utilise un champ parent de type Node pour mémoriser le noeud parent
    private Node parent;
    private ArrayList<Node> children;

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

            // On met au courant le parent
            send(parent, new Message("Yo", "TREE"));
        }
        else if (message.getFlag().equals("TREE")){
            // On ajoute le noeud qui nous a envoyé le message à la liste des
            // enfants
            children.add(message.getSender());
        }
    }
}
