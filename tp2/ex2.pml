mtype = {
  farine,
  sucre,
  oeuf,
  farinierFini,
  sucrierFini,
  oeufierFini
}

chan INGREDIENT = [3] of {byte};
chan INFO = [3] of {byte};

proctype fournisseur()
{
  // Met les trois ingrédients à disposition 
  INGREDIENT!sucre;
  INGREDIENT!farine;
  INGREDIENT!oeuf;

  // Quand il est informé qu'un gâteau a été produit, il ajoute les ingrédients
  // consommés sur la file d'attente.
  int nbFini = 0;
  do
  ::if 
    ::INFO?farinierFini -> INGREDIENT!sucre; INGREDIENT!farine;
    ::INFO?sucrierFini -> INGREDIENT!farine; INGREDIENT!oeuf;
    ::INFO?oeufierFini -> INGREDIENT!sucre; INGREDIENT!oeuf;
    fi;
  ::nbFini++;
  ::if 
    ::(nbFini >= 3) -> break;
    fi;
  od
}


// Possède de la farine à volonté
proctype patissierFarine()
{
  // Attends d'avoir du sucre
  INGREDIENT?sucre;
  // Attends d'avoir des oeufs
  INGREDIENT?oeuf;
  // Informe le fournisseur qu'un gâteau a été produit
  INFO!farinierFini;
}

// Possède du sucre à volonté
proctype patissierSucre()
{
  // Attends d'avoir de la farine
  INGREDIENT?farine;
  // Attends d'avoir des oeufs
  INGREDIENT?oeuf;
  // Informe le fournisseur qu'un gâteau a été produit
  INFO!sucrierFini;
}

// Possède des oeufs à volonté
proctype patissierOeuf()
{
  // Attends d'avoir du sucre
  INGREDIENT?sucre;
  // Attends d'avoir de la farine
  INGREDIENT?farine;
  // Informe le fournisseur qu'un gâteau a été produit
  INFO!oeufierFini;
}

init {
  atomic
  {
    run fournisseur();
    run patissierOeuf();
    run patissierFarine();
    run patissierSucre();
  }
}
