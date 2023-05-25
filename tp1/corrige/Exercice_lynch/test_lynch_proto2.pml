mtype = { ack, nak, err, next, accept }

proctype transfer (chan inc, out, chin, chout)
{byte o, i;
 inc?next(o);
 end0: do
 :: chin?nak(i) -> out!accept(i); printf("Val: %d ", i); chout!ack(o)
 :: chin?ack(i) -> out!accept(i); printf("Val: %d ", i); end1: inc?next(o); chout!ack(o)
 :: chin?err(i) -> chout!nak(o)
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
	AtoB!err(0);
	BtoA!err(0)
}
