#define p 0
#define v 1

chan sema = [0] of { bit };

proctype dijkstra() {
	byte count=1;

	do
	:: (count == 1) ->
		sema!p; count=0
	:: (count == 0) -> 
		sema?v; count=1
	od
}

proctype user() {
	do
	:: sema?p;
	/* critical section */
	sema!v;
	/* non-critical section */
	od
}

init {
	run dijkstra();
	run user();
	run user();
	run user()
}