#define true 1
#define false 0
#define Aturn false
#define Bturn true

bool x, y, t;

int nbCrit;

proctype A()
{ 
	x = true;
	t = Bturn;
	(y == false || t == Aturn);
	/* critical section */
	nbCrit++;
	nbCrit--;
	x = false;
}

proctype B()
{
	y = true;
	t = Aturn;
	(x == false || t == Bturn);
	/* critical section */
	nbCrit++;
	nbCrit--;
	y = false;
}

proctype C()
{
	assert(nbCrit <= 1)
}

init { 
	run A(); 
	run B();
	run C()
}
