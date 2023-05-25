byte state = 2 ;

proctype A() { (state == 1) -> state = 3 }

proctype B() { state = state - 1 }

init { atomic{ run A(); run B() } }
