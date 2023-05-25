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
  int nbFin = 0;
  do
  ::INFO?finPFarine -> INGREDIENT!sucre; INGREDIENT!oeuf; nbFin++
  ::INFO?finPSucre -> INGREDIENT!sucre; INGREDIENT!oeuf; nbFin++
  ::INFO?finPOeuf -> INGREDIENT!farine; INGREDIENT!sucre; nbFin++
  ::if
    ::nbFin == 3 -> break
    ::else -> skip
    fi 
  od
}

proctype patissierFarine()
{
  // On attend les ingrédients
  byte s, o;
  // Tant que l'on a pas reçu les ingrédients, on boucle
  do
  ::INGREDIENT?sucre -> s = 1
  ::INGREDIENT?oeuf -> o = 1
  ::if
    ::s == 1 && o == 1 -> break
    ::else -> skip
    fi
  od

  printf("Patissier Farine : J'ai tout les ingrédients\n");

  // On informe le fournisseur que c'est bon
  INFO!finPFarine;
}

proctype patissierSucre()
{
  // On attend les ingrédients
  byte f, o;
  // Tant que l'on a pas reçu les ingrédients, on boucle
  do
  ::INGREDIENT?farine -> f = 1
  ::INGREDIENT?oeuf -> o = 1
  ::if
    ::f == 1 && o == 1 -> break
    ::else -> skip
    fi
  od

  printf("Patissier Sucre : J'ai tout les ingrédients\n");

  // On informe le fournisseur que c'est bon
  INFO!finPSucre;
}

proctype patissierOeuf()
{
  // On attend les ingrédients
  byte s, f;
  // Tant que l'on a pas reçu les ingrédients, on boucle
  do
  ::INGREDIENT?farine -> f = 1
  ::INGREDIENT?sucre-> s = 1
  ::if
    ::f == 1 && s == 1 -> break
    ::else -> skip
    fi
  od

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
// PAs blocage
