abstract class Resept{

  private static int globalID = 0;

  protected int reseptId;
  protected Legemidler legemiddel;
  protected Lege lege;
  protected Pasient pasient;
  protected int reit;

  public Resept( Legemidler legemiddel, Lege lege,  int reit, Pasient pasient){
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

  public Lege hentLege(){
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
    return "    Resept-Id: " + reseptId + "\n    Legemiddel: " + legemiddel.hentNavn() + "\n    Utskrivende lege: " + lege.hentLege() + "\n    Pasient: "+  pasient.hentNavn() + "\n    Reit: " + reit;
  }
}

class HvitResept extends Resept{
  public HvitResept ( Legemidler legemiddel, Lege lege, Pasient pasient, int reit){
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
  public Militaer(Legemidler legemiddel, Lege lege, Pasient pasient, int reit){
    super(legemiddel, lege, pasient, reit);
    legemiddel.settNyPris(0);

  }
}

class PResept extends HvitResept{

  int nyPris;

  public PResept ( Legemidler legemiddel, Lege lege, Pasient pasient){
    super( legemiddel, lege, pasient, 3);
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

  public BlaaResept (Legemidler legemiddel, Lege lege, Pasient pasient, int reit){
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
