#define true 1
#define false 0
#define Aturn false
#define Bturn true
/* Utilisation de mémoire partagee */
bool x=0, y=0, t=Aturn;
byte nbSC = 0;

proctype A()
{ 
	/* Comparer les vérifications pour x=true et x=false*/ 
	x = true;
	t = Bturn;
	(y == false || t == Aturn);
	/* critical section */
	nbSC++;
	printf(" A is in critical section \n");
	nbSC--;
	x = false
}
proctype B()
{ 
	y = true;
	t = Aturn;
	(x == false || t == Bturn);
	/* critical section */
	nbSC++;
	printf(" B is in critical section \n");
	nbSC--;
	y = false
}

proctype verif() { assert(nbSC==0 || nbSC==1) } 

init { 
	atomic {
	 run A(); 
	 run B();
	 run verif()
	}
}
