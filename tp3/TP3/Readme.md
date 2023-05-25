TP3 d'Algorithmique distribuée
===============================

## Exercice 1 - Robots autonomes

Un robot est une entité autonome qui s'éxécute en effectuant des cycles `Voir-Calculer-Se déplacer`. D'abord il regarde son environement et obtient ainsi la position des autres robots, puis il calcul une destination, et se déplace vers cette destination.

On considère un ensemble de robots qui exécute le même algorithme. Implémenter un algorithme revient à écire la fonction `compute` qui prend l'ensemble des positions des autres robots et retourne la destination. Le but de l'algorithme est de faire résoudre une tâche aux robots.

Dans le dossier `Robots` se trouve la classe abstraite `Robots` qui implémente la phase d'observation et de déplacement des robots, et on voit que la fonction `compute` est abstraite. La classe `GatherRobot` implémente un algorithme pour les robots. 

__Question 1.__ Exécutez la simulation et expliquez quelle tâche est résolue par les robots. Décrivez rapidement quel algorithme a été implémenté pour résoudre cette tâche.



## Exercice 2 - Robots autonomes désorientés

Les robots sont indépendants et donc peuvent avoir leur propre système de coordonnées différent des autres. Vous pouvez voir dans le dossier `DisorientedRobots`, dans la classe `Robot`, que la position des robots obtenue dans la phase d'observation a subit une rotation aléatoire avant l'appel de la fonction `compute` (un robot sais ou il se trouve dans cette liste, grâce au premier argument de la fonction).
La classe `GatherRobot` implémente le même algorithme que précédement.

__Question 2.__ Executez la simulation et décrivez ce qu'il se passe.

__Question 3.__ Implementez un nouvel algorithme qui fonctionne avec ces robots désorientés. (Vous pouvez voir dans la sortie standard du terminal quand la tâche est résolue).


## Exercice 3 - Robots autonomes désorientés non-synchrone

Les robots étant indépendants, ils ne s'exécutent par forcément en même temps. Vous pouvez voir dans le dossier `SSYNCDisorientedRobots`, dans la classe `Robot`, qu'un robot a une probabilité $`1/3`$ de s'exécuter à chaque ronde. On dit que les robots sont semi-synchrone car un sous-ensemble des robots va s'exécuter à chaque ronde.

La classe `GatherRobot` implémente le même algorithme que dans l'exercice 1, vous pouvez déjà remplacer l'algorithme par votre version trouvée dans l'exercice 2.

__Question 4.__ Executez la simulation et décrivez ce qu'il se passe.

__Question 5.__ Implementez un nouvel algorithme qui fonctionne avec ces robots non-synchrone et désorientés. (Vous pouvez proposer une solution randomisée car une version déterministe est assez difficile à trouver).

## Exercice 4 - Eparpillement de robots

Dans cet exercice, la tâche à résoudre est l'éparpillement: on démarre d'une configuration où tous les robots ont la même positions et on souhaite que les robots aient des positions différentes. On suppose pour commencer que les robots sont synchrone et non-désorientés. Par contre on suppose que l'ensemble des positions vues par les robots ne contient pas de doublons (sauf sur la position occupé par le robot), ainsi les robots ne connaissent pas le nombre total de robots.

__Question 6.__  Existe-t-il un algorithme déterministe pour ce problème ?

__Question 7.__ Implémentez la fonction `compute` dans le fichier `ScatterRobot.java` pour résoudre le problème.

__Question 8.__ Etudiez en combien de rondes votre algorithme résout le problème en moyenne, en fonction du nombre de robots. Peut-on trouver un algorithme plus rapide ?

__Question 9.__ Votre algorithme fonctionnerait-il avec des robots désorientés ? et non-synchrones ?

## Exercice 5 - Déplacement des robots en nuée

Le déplacement des robots en nuée peut sembler difficile mais il peut être obtenu approximativement, en s'insipirant de la nature, en définissant quelques règles simples.

On va supposer que les robots vont toujours se déplacer à la même vitesse, et on s'interesse donc uniquement à la direction de leur déplacement. Dans le dossier `Flocking` se trouve la classe `FlockRobot` qui définit les robots qui nous intéressent. 
La direction est stockée par la variable `force` qui représente un vecteur de direction. Dans la fonction `compute`, ce vecteur peut être modifié en fonction de l'environement (observez qu'ici la fonctione `compute` reçoit comme argument la liste de tous les autres robots, et pas seulement leur position). Dans tous les cas, la fonction `compute` retourne un vecteur de longueur 3 (qui représente la vitesse constante des robots), grâce à l'appel de la fonction `vectWithLength` qui normalise le vecteur et lui donne la longueur voulue.

Tout d'abord, on donne une direction aléatoire aux robots dans la fonction `onStart()` et vous pouvez voir en exécutant la simulation que les robots se déplacer avec une vitesse constante et en ligne droite selon leur vecteur. Le noeud rouge n'est pas un robot, mais servira juste d'obstacle que les autres robots devront éviter (vous pouvez le déplacer pendant la simulation).

__Question 10.__ Modifiez la fonction compute (sans changer les deux dernière lignes) afin que le robot s'éloigne des robots qui sont trop proche. Cela s'effectue en ajoutant au vecteur `force`, un vecteur opposé au vecteur diriger les voisins trop proches (vous pouvez utiliser `subP` pour obtenir le vecteur entre 2 points et `addP` pour ajouter ce vecteur au vecteur `force`) Vous pouvez jouer avec l'amplitude du vecteur de telle sorte que, plus un voisin est proche, plus la force appliquée sera importante.

__Question 11.__ Ajoutez le fait qu'un robot à envie d'avoir le même vecteur force que les autres robots dans un rayon donné. Utilisez `if(r instanceof FlockRobot)` pour savoir si `r` est un robot et non un noeud obstacle, ainsi vous pourrer récupérer son vecteur grâce à `((FlockRobot)r).force`.

__Question 12.__ Vous pouvez aussi ajouter le fait qu'un robot est attiré par les autres robots qui sont assez éloigné. Vous pouvez implémenter d'autres choses comme il vous plaira.
