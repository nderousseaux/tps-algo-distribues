
proctype random(chan c) { 
 byte r;
 if
 :: r=1
 :: r=2
 :: r=3
 :: r=4
 :: r=5
 fi;
 c!r
}

init { 	byte a;
	chan q = [0] of { byte }
	run random(q); q?a; printf("Rand : %d\n", a) } 
