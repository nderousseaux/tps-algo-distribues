#define n 10
int numbers[n] = {1,8,0,124,13,2,3,4,5,6};
int max;
int i;
proctype boucle()
{
  do
  :: (i<=n) ->
    if
    :: (numbers[i] > max) -> max = numbers[i];
    :: else -> skip;
    fi;
    i++;
  :: (i>n) ->
    goto done;
	od;
  done:
    printf("max = %d\n", max);
}

init {
  run boucle();
}
