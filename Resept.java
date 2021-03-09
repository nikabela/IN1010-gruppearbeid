abstract class Resept{

  private static int globalID = 0;

  protected int reseptId;
  protected Legemidler legemiddel;
  protected String lege;
  protected Pasient pasient;
  protected static int reit;

  public Resept( Legemidler legemiddel, String lege,  int reit, Pasient pasient){
    reseptId = globalID;
    globalID ++;

    this.legemiddel = legemiddel;
    this.lege = lege;
    this.pasient = pasient;
    this.reit = reit;
  }

  public Legemidler hentLegemiddel(){
    return legemiddel;
  }

  public String hentLege(){
    return lege;
  }

  public Pasient hentPasient(){
    return pasient;
  }

  public int hentReit(){
    return reit;
  }

  public int hentReseptId(){
    return reseptId;
  }

  public boolean bruk(){
    if (reit <= 0){
      return false;
    } else {
      reit--;
      return true;
    }
  }

  abstract public int prisAABetale();

  abstract public String farge();

  @Override
  public String toString(){
    return "Resept-Id :" + reseptId + " legemiddel: " + legemiddel + " utskrivende lege :" + lege + " pasient: "+  pasient.hentNavn() + " reit: " + reit;
  }
}

class HvitResept extends Resept{

  public HvitResept ( Legemidler legemiddel, String lege, Pasient pasient, int reit){
    super( legemiddel, lege, reit, pasient);
  }

  @Override
  public String farge(){
    return "hvit";
  }

  @Override
  public int prisAABetale(){
    return legemiddel.hentPris();
  }
}


class Militaer extends HvitResept{

  public Militaer(Legemidler legemiddel, String lege, Pasient pasient, int reit){

    super(legemiddel, lege, pasient, reit);

    legemiddel.settNyPris(0);

  }
}

class PResept extends HvitResept{

  int nyPris;

  public PResept ( Legemidler legemiddel, String lege, Pasient pasient){
    super( legemiddel, lege, pasient, 3);

    // legemiddel.settNyPris(legemiddel.hentPris() - 108);

  }

  @Override
  public int prisAABetale(){
    if (legemiddel.hentPris() - 116 < 0){
      return 0;
    }
    return legemiddel.hentPris() - 116;
  }
}

class BlaaResept extends Resept{

  int pris;
  int rabatt;

  public BlaaResept (int reseptId, Legemidler legemiddel, String lege, Pasient pasient, int reit){
    super( legemiddel, lege, reit, pasient);

    pris = legemiddel.hentPris();
    legemiddel.settNyPris(pris/100*25);
  }

  @Override
  public String farge(){
    return "blaa";
 }

 @Override
 public int prisAABetale(){
   return legemiddel.hentPris();
 }
}
