class Hviteresepter extends Resept{  //opprettet subklassen til Resept klassen

  public Hviteresepter(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId, int reit){
    super(legemiddel, utskrivendeLege, pasientId, reit);  //arvet konstruktøren fra Resept superklassen
  }

  @Override
  public int prisAABetale(){return pris;}  // overrides abstrakt metoden i Resept.java. Det blir en ny override i Militaarresepter og Presepter, så her jeg bare returnerer prisen.

  @Override
  public String farge(Resept resept){     //overrides abstrakt metoden i Resept.java.
    if (resept instanceof Blaaresept){
      return "blaa";                    //metoden sjekker  hvilken subklassen til Resept tilhører objekten og returnerer en string
    }
    else{
      return "hvit";
    }
  }
  public String toString(){ //jeg har ikke de abstrakt metodene i dette, bare de egenskapene fra konstruktøren
    return "Reseptens Id: " + reseptId + ". Legemiddelens navn: " + legemiddel.hentNavn() + ". Legens navn: " + utskrivendeLege.hentNavn() + ". Pasientens Id: " + pasientId + ". Reit: " + reit + ".";
  }
}
//Tidligere jeg hadde subklassene til Hviteresepter her
/*
class Militaarresepter extends Hviteresepter{

  public Militaarresepter(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId, int reit){
    super(legemiddel, utskrivendeLege, pasientId, reit);
  }

  public int prisAABetale(){
    int prisfortilbud = legemiddel.hentPris();
    prisfortilbud = 0;
    return prisfortilbud;
  }
}
*/
/*
class Presepter extends Hviteresepter{

  protected int reit = 3;

  public Presepter(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId, int reit){
    super(legemiddel, utskrivendeLege, pasientId, reit);
  }

  public int prisAABetale(){
    int prisfortilbud = legemiddel.hentPris();
    if (prisfortilbud>108){
      prisfortilbud -= 108;
      return prisfortilbud;
    }
    else{
      prisfortilbud = 0;
      return prisfortilbud;
    }
  }
}
*/
