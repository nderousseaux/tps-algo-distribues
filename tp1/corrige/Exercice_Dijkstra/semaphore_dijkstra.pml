#define p 0
#define v 1
chan sema = [0] of {bit};

proctype dijkstra()
{
 	do
	:: sema!p ->sema?v
 	od
}

proctype user()
{
 do
 :: sema?p;
    skip; /* critical section */
    sema!v; 
    skip;  /* non critical section */
 od;
 
}

init{
	atomic { run dijkstra();
              	 run user(); run user();run user() } }
