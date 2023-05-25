mtype = {
  farine,
  sucre,
  oeuf,
  finPFarine,
  finPOeuf,
  finPSucre
}

chan INGREDIENT = [3] of {byte};
chan INFO = [3] of {byte};

proctype fournisseur()
{
  // On envoie les ingrédients
  INGREDIENT!farine;
  INGREDIENT!sucre;
  INGREDIENT!oeuf;

  // On attend qu'un patissier finisse
  do
  ::INFO?finPFarine -> INGREDIENT!sucre; INGREDIENT!oeuf
  ::INFO?finPSucre -> INGREDIENT!sucre; INGREDIENT!oeuf
  ::INFO?finPOeuf -> INGREDIENT!farine; INGREDIENT!sucre
  od
}

proctype patissierFarine()
{
  // On attend les ingrédients
  INGREDIENT?sucre;
  INGREDIENT?oeuf;

  printf("Patissier Farine : J'ai tout les ingrédients\n");

  // On informe le fournisseur que c'est bon
  INFO!finPFarine;
}

proctype patissierSucre()
{
  //On attend les ingrédients
  INGREDIENT?farine;
  INGREDIENT?oeuf;

  printf("Patissier Sucre : J'ai tout les ingrédients\n");

  // On informe le fournisseur que c'est bon
  INFO!finPSucre;
}

proctype patissierOeuf()
{
  //On attend les ingrédients
  INGREDIENT?farine;
  INGREDIENT?sucre;

  printf("Patissier Oeuf : J'ai tout les ingrédients\n");

  // On informe le fournisseur que c'est bon
  INFO!finPOeuf;
}

init {
  atomic {
    run fournisseur();
    run patissierOeuf();
    run patissierFarine();
    run patissierSucre();
  }
}


// Le processus est t'il bloquant avec l'ordre déterminé ?
/* Oui il l'est
Le fournisseur remplit la file                          {Farine, Sucre, Oeufs}
Le patissierSucre prend la farine                       {Sucre, Oeufs}
Le patissierFarine prend le sucre                       {Oeufs}
Le patissier sucre prend les oeufs                      {}
PatissierSurce fait un gateau
Fournisseur remplit la file des ingrédients utilisés    {Farine, Oeufs}
Cependant, PatissierSucre attend un oeuf, pas dispo
Et PatissierOeuf attend un du sucre, pas dispo
Donc interblocage
*/
