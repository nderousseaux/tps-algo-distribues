#define p 0
#define v 1
chan sema = [0] of {bit};

byte count=0;

proctype dijkstra()
{
 end: 	do
	:: sema!p -> progress1: sema?v
 	od
}
proctype user()
{
 end1: do
 :: sema?p;
    count= count + 1;
    skip; /* critical section */
    progress:
    count= count - 1;
    sema!v; 
    skip;  /* non critical section */
 od;
}

proctype monitor() { assert(count == 0 || count == 1) }

init{
	atomic { run dijkstra(); run monitor(); 
              	 run user(); run user();run user(); run user() } }
