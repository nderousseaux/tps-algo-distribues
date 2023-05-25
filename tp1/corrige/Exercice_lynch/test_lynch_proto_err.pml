mtype = { ack, nak, err, next, accept }

proctype transfer (chan inc, out, chin, chout)
{byte o, i;
 inc?next(o);
 end0: do
 :: chin?nak(i) -> out!accept(i); end2: do
			:: chout!ack(o) -> skip;
			:: chout!err(o) -> skip;
			od
 :: chin?ack(i) -> out!accept(i); end1: inc?next(o); do
			:: chout!ack(o) -> skip;
			:: chout!err(o) -> skip;
			od
 :: chin?err(i) -> end4: do
		:: chout!nak(o) -> skip;
		:: chout!err(o) -> skip;
		od
 od
}

init
{chan AtoB = [1] of {byte, byte};
 chan BtoA = [1] of {byte, byte};
 chan Ain = [2] of {byte,byte};
 chan Bin = [2] of {byte,byte} ;
 chan Aout = [2] of {byte,byte};
 chan Bout = [2] of {byte,byte};

atomic {	
	run transfer(Ain, Aout, BtoA, AtoB);
	run transfer(Bin, Bout, AtoB, BtoA);
	Ain ! next(1); Ain ! next(2); Bin ! next(3); Bin ! next(4)
	};
	BtoA!err(0)
}
