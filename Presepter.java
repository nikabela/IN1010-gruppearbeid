class Presepter extends Hviteresepter{  //opprettet subklassen Presepter

//  protected int reit = 3; dette er min tidligere forsøk for å sette reit til 3.

  public Presepter(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId, int reit){  //parameterne må være samme som i superklassen
    super(legemiddel, utskrivendeLege, pasientId, 3);  //arver konstruktøren til superklassen, men reit settes fast til 3
  }
  @Override  //Overrides abstrakt metoden i Resept.java
  public int prisAABetale(){
    int prisfortilbud = legemiddel.hentPris();  //en Legemiddel obj. ble sent inn som argument. henter legemiddelens pris med metoden fra Legemiddel.java
    if (prisfortilbud>108){  //brukeren betaler 108 kroner mindre for legemiddelen, men kan ikke betale mindre enn 0
      prisfortilbud -= 108;
      return prisfortilbud;
    }
    else{
      prisfortilbud = 0;  //jeg tenkte at hvis prisen til legemiddelen er mindre enn 108, da blir det gratis pga. rabatten.
      return prisfortilbud;
    }
  }
}
