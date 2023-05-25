#define p 0
#define v 1
chan sema = [0] of {bit};
int nbCrit;

proctype djikstra()
{
  do
	::sema!p->
    sema?v;
	od
}

proctype user()
{
	do
	::sema?p->
	   skip;
     nbCrit++;
     nbCrit--;
	   sema!v;
	   skip;
	od
}

proctype monitor()
{
	assert(nbCrit <= 1);
}

init {
	atomic {
		run djikstra ();
		run user();
		run user();
		run user();
		run monitor();
	}
}
