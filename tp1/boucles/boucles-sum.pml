#define n 2
int sum;
int i;

proctype boucle()
{
  do
  :: (i<=n) ->
    sum = sum + i;
    i++;
  :: (i>n) ->
    goto done;
	od
  done:
    printf("sum = %d\n", sum);
}

init {
  run boucle();
}
