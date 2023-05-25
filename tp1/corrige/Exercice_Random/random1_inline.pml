bit a
/* Test with a chan length of 0. Explain the result */
chan q = [1] of { bit }

inline random() { 
 bit r;
 if
 :: r=0
 :: r=1
 fi;
 q!r
}

init { random(); q?a; printf("Rand : %d\n", a) } 

/* To test in command line : spin -n123 random1_inline.pml */
