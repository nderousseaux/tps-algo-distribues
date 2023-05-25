TP2 D'Algorithmique Distribuée avec jBotSim
=============================================

(Certaines parties sont copiées des tutoriels d'Arnaud Casteigts, créateur de jBotSim).

# Exercice 1 - Protocoles de population

Dans le modèle des protocoles de population, des agents (représentés par des noeuds) sont interconnectés par des liens de communications. De manière artbiraire et dans un ordre quelconque les agents vont intéragir 2 à 2. Chaque agent a un état interne et lorsqu'une interaction a lieu, l'initiateur de l'intéraction connait son état et celui de l'autre agent et peut modifier son état et celui de l'autre agent. Ce modèle très simple permet de créer des algorithmes distribués qui sont par nature asynchrone. 

L'environnement d'exécution consiste en un ordonnanceur et un algorithme. L'ordonnanceur (aussi appelé adversaire ou démon) est responsable de choisir quelle paire de noeud interagit à chaque étape. L'algorithme, de son côté, réalise l'interaction en modifiant l'état des deux noeuds choisis. C'est aussi lui qui détermine l'état initial des noeuds, ce dernier pouvant être uniforme (tout les noeuds commencent dans le même état) ou non-uniforme (un ou plusieurs noeuds démarrent dans un état différent, appelé état distingué).

Implémenter un algorithme revient donc à écrire les fonctions `onStart();` qui permet de définir l'état initial des noeuds, `onSelection()` qui permet, si nécessaire, de définir un état particulier pour un agent, et  `interactWith(Node responder)` qui permet de modifier l'état des agents lorsqu'ils intéragissent. Ces 3 fonctions définissent un noeud de type `PPNode` (voir le fichier `PPNode.java`).

L'ordonnanceur est implémenté dans `PPScheduler`. Dans notre version, l'ordonnanceur choisi les noeuds qui intéragissent de manière aléatoire, mais on pourrai imaginer un _adversaire_ plus intélligent qui choisit les interactions de manière arbitraire (mais pas forcément aléatoire).

Dans la fonction `main` (dans `Main.java`) on voit que l'on créer une topologie fixe, qu'on choisi notre _Scheduleur_ et qu'on exécute l'algorithme donné. 

Dans ce premier exemple implémenté dans `PPNodeAlgo1.java` les noeuds commence avec l'état bleu, le noeud sélectionné commence avec l'état rouge, et quand deux noeuds intéragissent, si l'un est rouge et l'autre et bleu, alors le bleu devient rouge.

__Question 1.__ Executez l'algorithme et observez ce qu'il se passe. Que fait cet algorithme ? _(on peut sélectionner un noeud en cliquant dessus avec la molette ou via `Ctrl+clic`)_

# Exercice 2 - Election de leader

Le but est d'implémenter un protocol de population pour élir un leader parmis les agents. Tous les agents commencent initialement avec l'état `bleu` (qui signifie leader) et au bout d'un certains temps il ne doit rester qu'un seul agent bleu (il restera ainsi un unique leader). Les autres agents doivent être rouge. 


__Question 2.__ Complétez la fonction `interactWith` dans le fichier `PPElection.java` pour implémenter un algorithme d'élection de leader. Exécutez votre algorithme dans un graphe d'interaction complet (déjà implémenté dans la fonction `main`). 
Êtes-vous sûr que l'algorithme est correct et fonction dans tous les cas ?

__Question 3.__ Si le graphe n'est pas complet (mais est connexe) l'algorithme a-t-il des chances de réussir (même petites) ? Vous pouvez tester votre nouvelle algorithme en remplaçant l'appelle `Main.complet(tp, 10);` par `Main.load(tp, "topo1.plain");` dans la fonction `main`.

Si oui, le succès est-il garanti pour autant dans n'importe quel graphe connexe ? Maintenant, vous comprenez sûrement pourquoi l'ordonnanceur est parfois appelé adversaire.

__Question 4.__ En supposant que les noeuds ont des identifiants aléatoires (comme dans le `TP1/LeaderElection`) que l'on peut utiliser pendant l'intéraction, comment améliorer l'algorithme en une version 2 pour qu'il fonctionne dans tous les graphes connexes (avec un ordonnanceur aléatoire).  (en cas de blocage **après reflexion**, vous pouvez demander un indice à l'enseignant).

__Question 5.__ Supposons que le graphe d'interaction est complet et qu'on continue à utiliser un ordonnanceur aléatoire. Soit $`E[T]`$ l'espérance du temps qu'il faut jusqu'à ce qu'il n'y ait plus qu'un seul compteur. Quel protocole entre la version 1 et la version 2 a l'espérance la plus petite ? Autrement dit, lequel converge le plus vite en moyenne ?

Plus difficile: Quelle est cette espérance (en fonction du nombre $`n`$ de noeuds) pour la version 1 ? encore plus difficile: pour la version 2 ?. (vous pouvez construire un graphe complet et lancer des simulations pour avoir une idée de la réponse).



# Exercice 3 - Comptage

Vous devez implémenter un algorithme pour que les agents comptent combien ils sont dans le réseau. Plusieurs version seront implémentez. Vous pourrez ajouter des variables entières en attribut de vos classes afin de stocker certaines valeurs, et lors d'une interaction, les agents peuvent accéder à ces variables.

__Question 6.__ Version 1: Dans un fichier `PPCounting1.java`. Dans cette version, le noeud selectionné à un rôle différent des autres. Il est le __compteur__ et son rôle est de compter les noeuds avec lesquels il interagit. Les autres sont initialement dans l'état __non compté__, puis à mesure que le compteur interagit avec eux, ils passent dans l'état __compté__. Il est essentiel d'avoir ces deux états, sinon le compteur risque de compter plusieurs fois le même noeud. 

On peut représenter les trois états (i.e. compteur, compté, et non compté) par trois couleurs, et le décompte par une variable entière que seul le compteur utilise. Ce protocole est très limité, et c'est normal ! On ne vous demande pas de l'améliorer.

→ Astuce de visualisation: il est possible de redéfinir la méthode toString() pour qu'elle renvoie le décompte lorsqu'elle est appelée sur le noeud compteur (et une chaine vide sur les autres noeuds). Cette méthode sera utilisée automatiquement par JBotSim pour remplir l'étiquette des noeuds (visible lorsque la souris passe sur un noeud).

__Question 7.__ Version 2 : Définissons maintenant un second protocole de comptage. Il ne sera pas encore parfait, mais sera mieux que le précédent pour (au moins) deux aspects : 
1) il a une initialisation uniforme, c.à.d. qu'il n'a pas besoin de noeud distingué (tout le monde a le même état initialement), et 
2) il est susceptible de réussir dans plus de graphes. Son principe est le suivant : initialement chaque noeud est un compteur dont le décompte est égal à 1 (il s'est compté lui-même). Puis, lorsque deux compteurs non-nuls interagissent, ils s'affrontent. Le gagnant (disons le plus grand compteur) reste compteur et l'autre n'est plus compteur. De plus, le perdant se fait voler son décompte par le gagnant, qui cumule maintenant les deux décomptes.

Implémenter ce protocole dans un fichier `PPCounting2.java`

Expliquez pourquoi on peut dire que l'exécution a réussi s'il ne reste qu'un seul compteur à la fin. 

__Question 8.__ Comment améliorer l'algorithme en une version 3 pour qu'il fonctionne dans tous les graphes connexes (avec un ordonnanceur aléatoire).

