#define N 4

typedef tabcan {
  chan to[N] = [1] of {short};
};
short elu[N];
short nbFin=0;

/* utilisation de la structure tabcan permettant de définir un tableau de N
 * canaux à touble indexation : from[x].to[y] */
tabcan from[N]; 

// Election du processus ayant le plus petit numéro
// En full-mesh, trivial -> brute force (pas optimal)
proctype electeur(short numproc; chan em1, em2, em3, rec1, rec2, rec3)
{
  // On envoie notre numéro à tout les processus
  em1!numproc;
  em2!numproc;
  em3!numproc;

  // On attend la réponse de tout les processus
  short num1, num2, num3, min;
  rec1?num1;
  rec2?num2;
  rec3?num3;

  // On élit le processus ayant le plus petit numéro
  if
  :: numproc < num1 && numproc < num2 && numproc < num3 -> min = numproc
  :: num1 < numproc && num1 < num2 && num1 < num3 -> min = num1
  :: num2 < numproc && num2 < num1 && num2 < num3 -> min = num2
  :: num3 < numproc && num3 < num1 && num3 < num2 -> min = num3
  fi;

  // On enregistre le résultat
  elu[numproc-1] = min;

  // On affiche le résultat
  printf("Le processus %d a élu le processus %d\n", numproc, min);

  // On vérifie que le processus choisi est bien le plus petit
  assert(min==1);

  // On indique qu'on a fini (pour la vérification)
  nbFin++;
}

// Vérification de l'élection (que tout les processus ont bien élu le même)
// Cette vérificatio demande une vérification globale car chacun d'entre eux
// ne peut pas vérifier que tout les autres ont bien élu le même
proctype verif()
{
  // On attend que tout les processus aient fini
  do
  :: nbFin==N -> break
  od;
  assert(elu[0]==elu[1] && elu[1]==elu[2] && elu[2]==elu[3]);
}

init{
  atomic {
    run electeur(
      1,
      from[0].to[1], from[0].to[2], from[0].to[3],
      from[1].to[0], from[2].to[0], from[3].to[0]
    );
    run electeur(
      2,
      from[1].to[0], from[1].to[2], from[1].to[3],
      from[0].to[1], from[2].to[1], from[3].to[1]
    );
    run electeur(
      3,
      from[2].to[0], from[2].to[1], from[2].to[3],
      from[0].to[2], from[1].to[2], from[3].to[2]
    );
    run electeur(
      4,
      from[3].to[0], from[3].to[1], from[3].to[2],
      from[0].to[3], from[1].to[3], from[2].to[3]
    );
    run verif();
  }
  // On attend que tout les processus aient fini
}
