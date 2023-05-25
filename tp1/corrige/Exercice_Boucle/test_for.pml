/* faire l'équivalent du for :
   somme des n premiers entiers */

proctype iteration(short n)
{
	short i=1, somme=0;

	do
	:: (i<=n) -> somme=somme+i; i=i+1
	:: else -> break
	od;

	printf("somme : %d\n", somme)

}

init { run iteration(5) }
