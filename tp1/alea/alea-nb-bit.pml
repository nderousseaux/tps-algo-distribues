// Génère un nombre aléatoire à n bits
#define N 2
int res;
proctype alea()
{
  int i;
  // On fait n tours de boucle
  do 
  :: (i<N) -> // On fait un tour de boucle
    if
    :: res=res*2
    :: res=res*2+1
    fi
    i++;
  :: (i>=N) -> break; // Fin de la boucle
  od
  printf("res=%d\n", res);
}
init {
  run alea();
}
