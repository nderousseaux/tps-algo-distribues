TP1 D'Algorithmique Distribuée avec jBotSim
=============================================

(Certaines parties sont copiées des tutoriels d'Arnaud Casteigts, créateur de jBotSim).
# Exercice 1 - Hello world

Pour vérifier que tout fonctionne bien, allez dans le dossier `HelloWorld` et exécutez `./start.sh`.

Normalement la complation devrait fonctionner et une fenêtre s'ouvrir. Dans cette fenêtre, vous pouvez clic gauche pour ajouter des noeuds, clic droit pour les supprimer, et déplacer des noeuds. Des liens apparaissent automatiquement en fonction de la distance entre les noeuds.

Le code correspondant à cet exemple est
```
    Topology tp = new Topology();
    new JViewer(tp);
    tp.start();
```
On a créé une topologie, un visualisateur (la fenêtre), et on a démarrer la topologie. La classe main contiendra toujours une fonction main avec un code similaire qui permet de mettre en place la topologie et de la demarrer (`tp.start()` sera toujours placer à la fin du `main` ou bien inexistant, dans ce dernier cas, la simulation démarre depuis la fenêtre en faisant `clic droit > start`).


# Exercice 2 - Broadcast

Dans le dossier 2 se trouve une classe `Main` avec deux instructions en plus. La première est `tp.setDefaultNodeModel(BroadcastNode.class);` qui signifie que les noeuds utilisés sont de type `BroadcastNode` définie dans le fichier `BroadcastNode.java`.

La classe `BroadcastNode` hérite de `Node` et surcharge certaine fonction pour réagir à certain événements:
* `onStart` : cette fonction est appelée lorsque la simulation démarre (lors de l'appel `tp.start()` ou en faisant `clic droit > start`). Dans notre exemple, le noeud prend la couleur verte pour signifier qu'il n'a pas reçu le message.
* `onSelection` : cette fonction est appelée quand l'utilisateur `Ctrl+clic` sur un noeud. Dans notre exemple, cela représente le fait d'initier le broadcast depuis ce noeud, il prend donc la couleur rouge et commence la diffusion en envoyant un message à tous ses voisins.
* `onMessage(Message message)` :  cette fonction est appelée quand le noeud reçoit un message de ces voisins. si un voisin exécute `sendAll(new Message(42))` alors la fonction `onMessage(Message message)` va être exécutée chez ses voisins et la valeurs `42` peut être récupérée en convertissant la valeur reçue : `42 == (Integer) message.getContent()`. Dans notre exemple, si le noeud n'a pas encore reçu de message (il est encore vert), alors il devient rouge et re-transemet le message à tous ses voisins.

Ainsi,  le message va se diffuser de la source à tous les noeuds. L'algorithme s'exécute de manière synchrone: à chaque ronde un noeud reçois les messages envoyez par ces voisins la ronde précédente. S'il décide d'envoyer un message, il sera reçu la ronde suivante. Dans le visualisateur, chaque ronde dure $`1000ms`$ grâce à l'instruction `tp.setTimeUnit(1000);`.

__Question 1.__ Etant donné un graphe $`G`$, combien de rondes prendra la diffusion dans le pire des cas ?
    La diffusion prendra au plus n-1

__Question 2.__ Combien de messages seront échangés en fonction du nombre d’arêtes (noté $`m`$) ? Pour quelle topologie ce nombre est-il maximum ? Si l’on sait qu’on est dans une telle topologie, peut-on faire mieux ? Si oui comment et avec combien de messages ?
   Graphe complet. Si on le sait, on peut faire mieux : il suffit qu'un seul des élément diffuse 

## Exercice 3 - Construction d’arbres couvrants

Dans le dossier SpanningTree, la classe `TreeNodeQ1` est une copie de la classe `BroadcastNode` vu précédemment. Modifiez cette classe pour répondre à la première question et nous vous conseillons de créer une nouvelle version de ce fichier pour chaque question (pensez dans ce cas à modifier le `main` pour qu'il utilise la classe correspondante).

__Question 1.__ Adaptez le code pour construire un arbre couvrant, tel que lorsqu'un noeud est informé par un noeud voisin, il choisit ce voisin comme parent. On pourra utiliser un champ parent de type Node pour mémoriser le noeud parent. (voir la documentation de [`Message`](https://jbotsim.io/javadoc/1.2.0/io/jbotsim/core/Message.html) pour récupérer l''envoyeur d'un message).
_Visualisation:_ Une fois le noeud parent connu, vous pouvez utiliser la méthode `getCommonLinkWith()` pour obtenir le lien qui vous relie à lui, et la méthode `setWidth()` sur ce dernier pour mettre l'arête en gras.

__Question 2.__ Faites maintenant en sorte que les noeuds apprennent la liste de leurs enfants dans l'arbre. Vous pouvez utiliser un champ children de type [`ArrayList`](https://itsallbinary.com/javadoc-examples/java/docs/api/examples/index.html?java/util/java.util.ArrayList.html). Vous pouvez utiliser des messages de différents types en utilisant des flags. 
```java
//envoie:
sendAll(new Message("message de controle", "TREE"));

//reception
message.getFlag().equals("TREE"); // est vrai
```
Il est aussi possible d'envoyer un message à un seul noeud voisin plutôt qu'à tous en utilisant la bonne méthode (voir la documentation de `Node` dont le lien est sur le Readme à la racine des TPs).

__Question 3.__
Faites maintenant en sorte que les noeuds détectent le fait qu'ils n'auront pas d'enfants dans l'arbre (en les colorant d'une couleur différente par exemple). Vous pouvez utiliser `getTime()` pour connaître le numéro de ronde actuelle. Vous pouvez surcharger `onClock()` pour exécuter un code spécifique à chaque ronde (la méthode sera appelée juste après la réception des messages envoyés à la ronde précédente).

__Question 4.__ Faites en sorte que la racine détecte lorsque la construction de l'arbre est terminée (en le colorant d'une couleur différente).

__Question 5.__ Faites en sorte que la racine apprendre le nombre de noeuds total dans le réseau. Notez que le contenu des messages peut être n'importe quel type d'objet.

__Question 6.__ La racine doit ensuite informer chaque noeud du nombre de noeuds total.


## Exercice 4 - Election de Leader dans un anneau orienté.

__Question 1.__
Implémentez dans le fichier `LeaderElection/ElectionNode.java` l'algorithme de Chang et Roberts afin que quand un noeud est sélectionner (`Ctrl+Clic` sur un noeud), il initie une élection et en temps fini un noeud devient leader (le colorer en rouge). L'identifiant d'un noeud est stocké dans la variable `myRandomId`.
Vous pouvez utiliser les flags des messages afin que le leader préviennent les autres noeuds qu'il est l'élu. Quand un noeud apprend l'identifiant de l'élu il doit devenir orange.

__Question 2.__ 
Combien de messages sont envoyés dans le pire des cas ? Dans le meilleur des cas ?
