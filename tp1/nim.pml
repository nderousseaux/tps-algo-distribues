// Modèlise le jeu de nim
#define N 10
int nbAlumettes = N;
int nbRetire=0;
chan atob = [0] of {int};
chan btoa = [0] of {int};

proctype joueur(chan in, out; int num)
{
  do
  :: (nbAlumettes > 0) -> // Il reste allumettes
    //On attend notre tour
    in?0;
    if
    :: (nbAlumettes <= 0) -> // Il n'y a plus d'allumettes
      printf("Plus d'allumettes: %d à perdu\n", num);
      break;
    :: else -> // Il reste des allumettes
      printf("Joueur %d joue\n", num);
      // On choisi le nombre d'allumettes à enlever
      if
      :: (nbAlumettes >= 3) -> // On peut enlever 3 allumettes
        nbAlumettes = nbAlumettes - 3;
        nbRetire=nbRetire+3;
      :: (nbAlumettes >= 2) -> // On peut enlever 2 allumettes
        nbAlumettes = nbAlumettes - 2;
        nbRetire=nbRetire+2;
      :: (nbAlumettes >= 1) -> // On peut enlever 1 allumette
        nbAlumettes = nbAlumettes - 1;
        nbRetire=nbRetire+1;
      fi

      printf("Il reste %d allumettes\n", nbAlumettes);
    fi

    // On envoie un message à l'autre joueur pour lui dire de jouer
    out!0;

  :: else -> // Il n'y a plus d'allumettes
    printf("%d à gagné\n", num);
    break;
  od
  assert(nbAlumettes == 0);
}

proctype verif() {
  assert(nbAlumettes + nbRetire < N);
}

init { 
  printf("Il reste %d allumettes\n", nbAlumettes)
	run joueur(btoa, atob, 0);
	run joueur(atob, btoa, 1);
  run verif();
  btoa!0;
}
