mtype = { create , created }

proctype tree(int n; chan t) {
	chan childL = [1] of { int };
	chan childR = [1] of { int };

	t?create ->
		if
		:: n>0 -> run tree(n-1, childL); childL!create; childL?created; /* déplacer childL?created ? Comment ne pas privilégier l'arbre gauche ? */
		              run tree(n-1, childR); childR!create; childR?created
		:: n <= 0 -> skip
		fi
	t!created
}

init {
	chan childT = [1] of { int };
	run tree(3, childT);
	childT!create ; childT?created
}
