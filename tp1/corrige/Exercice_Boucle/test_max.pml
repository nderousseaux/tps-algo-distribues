/* faire l'équivalent du for :
   chercher l'element maximum dans un tableau */
#define N 5
short tab[N];

/* Pour simplifier, maximum d'un tableau d'entiers positifs */
proctype maxtab(short n)
{
 short i=0,max=-1;
 do
 ::(i<n) ->
	if
	:: ( tab[i] > max ) -> max=tab[i]
	/* Comparer avec l'execution sans la branche ::else */
  	:: else -> skip
	fi;
	i=i+1
 ::else -> break
 od;

printf("max : %d\n", max)
}

init {
 atomic{    tab[0]= 1;
	 tab[1]= 3 ;
	 tab[2]= 6 ;
	 tab[3]= 2;
	 tab[4]= 5 ;
	 run maxtab(5)}}
