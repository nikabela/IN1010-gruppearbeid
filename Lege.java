class Lege implements Comparable<Lege>{

  String navn;
  LenketListe<Resept> utskrevedeResepter;

  public Lege(String navn) {
    this.navn = navn;
    utskrevedeResepter = new LenketListe<Resept>();
  }

  public void kastUlovligUtskrift(Lege l, Legemidler lm) throws UlovligUtskrift {
    if (!(l instanceof Spesialist) && (lm instanceof Narkotisk)) {
        throw new UlovligUtskrift(l, lm);
    }
  }

  public HvitResept skrivHvitResept(Legemidler legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
    kastUlovligUtskrift(this, legemiddel);
    HvitResept nyUtskrevenResept = new HvitResept(legemiddel, this, pasient, reit);
    utskrevedeResepter.leggTil(nyUtskrevenResept);
    return nyUtskrevenResept;
  }

  public Militaer skrivMilitaerResept(Legemidler legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
    kastUlovligUtskrift(this, legemiddel);
    Militaer nyUtskrevenResept = new Militaer(legemiddel, this, pasient, reit);
    utskrevedeResepter.leggTil(nyUtskrevenResept);
    return nyUtskrevenResept;
  }

  public PResept skrivPResept(Legemidler legemiddel, Pasient pasient) throws UlovligUtskrift {
    kastUlovligUtskrift(this, legemiddel);
    PResept nyUtskrevenResept = new PResept(legemiddel, this, pasient);
    utskrevedeResepter.leggTil(nyUtskrevenResept);
    return nyUtskrevenResept;
  }

  public BlaaResept skrivBlaaResept(Legemidler legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {
    kastUlovligUtskrift(this, legemiddel);
    BlaaResept nyUtskrevenResept = new BlaaResept(legemiddel, this, pasient, reit);
    utskrevedeResepter.leggTil(nyUtskrevenResept);
    return nyUtskrevenResept;
  }

  public LenketListe<Resept> hentUtskrevedeResepter() {
    return utskrevedeResepter;
  }

  @Override
  public int compareTo(Lege l) {
    return navn.compareTo(l.hentLege());
  }

  public String hentLege() {
    return navn;
  }

  public String toString() {
    return(hentLege());
  }

}


class Spesialist extends Lege implements Godkjenningsfritak {

  int kontrollId;

  public Spesialist(String navn, int kontroll) {
    super(navn);
    kontrollId = kontroll;
  }

  public int hentKontrollId() {
    return kontrollId;
  }

  @Override
  public String toString() {
    return(hentLege() + " (kontroll-ID: " + hentKontrollId() + ")");
  }
}
