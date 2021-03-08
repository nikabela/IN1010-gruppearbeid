class Blaaresept extends Resept{  //opprettet subklassen

//  protected double rabatt = 0.75;   //tidligere forsøk for prisAAbetale metoden


  public Blaaresept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId, int reit){
    super(legemiddel, utskrivendeLege, pasientId, reit);  //arvet konstruktøren fra Resept klassen
  }

//  public int prisEtterRabatt(){return Math.round(pris*rabatt);}  //tidligere forsøk for prisAAbetale metoden

  @Override   //overrides abstrakt metoden i Resept.java
  public int prisAABetale() {
    return (Math.round(legemiddel.hentPris()/100*25));
    //en legemiddel objekt ble sendt inn som argument, man kan få takk i prisen til legemiddelen. Betaler 25%.
  }

  @Override
  public String farge(Resept resept){ //overrides abstrakt metoden i Resept.java
    if (resept instanceof Blaaresept){ //metoden sjekker hvilken subklassen til Resept klassen tilhører objekten og returnerer en string
      return "blaa";
    }
    else{
      return "hvit";
    }
  }
  public String toString(){ //jeg har ikke de abstrakt metodene i dette, bare de egenskapene fra konstruktøren
    return "Reseptens Id: " + reseptId + ". Legemiddelens navn: " + legemiddel.hentNavn() + ". Legens navn: " + utskrivendeLege.hentNavn() + ". Pasientens Id: " + pasientId + ". Reit: " + reit + ".";
  }
}
