abstract class Resept{

  protected static int teller2 = 0;  //bruker den til å tilordne individuell Id.
  protected int reseptId;
  protected Legemiddel legemiddel; //referanse til legemiddel objekt
  protected Lege utskrivendeLege; //referanse til lege objekt
  protected int pasientId; //pasienten som eier resepten
  protected int reit;
  protected int pris;

  public Resept(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId, int reit) {
    this.legemiddel = legemiddel;
    this.utskrivendeLege = utskrivendeLege;
    this.pasientId = pasientId;
    this.reit = reit;
    this.reseptId = teller2 + 1;  //tilordner individuell Id til resepten
    teller2++;  //neste objekten i klassen får en annen Id, så øker jeg telleren.
  }

  public int hentId() {return reseptId;}   //returnerer info om Resept objekten

  public Legemiddel hentLeggemiddel(){return legemiddel;}

  public String hentLege() {return utskrivendeLege.hentNavn();}  //henter legens navn

//  public String hentLege() {return ((Spesialist)utskrivendeLege).hentKontrollId();} jeg trodde med hentLege jeg må hente kontrollId...

  public int hentPasientId() {return pasientId;}

  public int hentReit() {return reit;}

  public boolean bruk(){
    if (reit <= 0){
      return false;
    }
    else{
      reit--;
      return true;
    }
  }

  public abstract String farge(Resept resept);

  public abstract int prisAABetale();

  public String toString(){
    return "Reseptens Id: " + reseptId + ". Legemiddelens navn: " + legemiddel.hentNavn() + ". Legens navn: " + utskrivendeLege.hentNavn() + ". Pasientens Id: " + pasientId + ". Reit: " + reit + ".";
  }
}

/*  tidligere forsøk for å sjekke gyldigheten.

  public String gyldig(Resept resept){
    if (resept.bruk() == false){
      return "Resepten er ikke gyldig!";
    }
    else{
      return "Resepten er gyldig.";}
  }
*/
