#define Place byte	// assume at most 255 tokens per place                             
			// file ex_4.pml 

Place p1=1, p2, p3          // place p1 has an initial token 
Place p4=1, p5, p6          // place p4 has an initial token

#define inp1(x) x>0 -> x-- 
#define inp2(x,y) x>0 && y>0 -> x--; y-- 
#define out1(x) x++ 
#define out2(x,y) x++; y++ 

# define petri net with priority to avoid deadlock
init {   
	do     
	:: inp2(p2,p4) -> out1(p3) // t2   
	:: inp2(p1,p5) -> out1(p6)// t5 
	:: else ->
		if
		:: inp1(p3)    -> out2(p1,p4) // t3   
		:: inp1(p4)    -> out1(p5) // t4   
	  	:: inp1(p1)    -> out1(p2) // t1 
		:: inp1(p6)    -> out2(p4,p1) // t6
		fi
	od 
}
