proctype alea()
{
  if
  :: (1==1) -> printf("1\n");
  :: (0==0) -> printf("0\n");
  fi;

}
init {
  run alea();
}
