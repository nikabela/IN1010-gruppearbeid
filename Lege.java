class Lege implements Comparable<Lege>{

  String lege;
  LenketListe<Resept> utskrevedeResepter;

  public Lege(String navn) {
    lege = navn;
    utskrevedeResepter = new LenketListe<Resept>();
  }

  public void kastUlovligUtskrift(Lege l, Legemidler lm) throws UlovligUtskrift {
    if (!(l instanceof Spesialist) && (lm instanceof Narkotisk)) {
        throw new UlovligUtskrift(l, lm);
    }
  }

  public LenketListe<Resept> hentUtskrevedeResepter() {
    return utskrevedeResepter;
  }

  @Override
  public int compareTo(Lege l) {
    return lege.compareTo(l.hentLege());
  }

  public String hentLege() {
    return lege;
  }

  public void skrivUtLegesNavn() {
    System.out.println("Legens navn: " + hentLege());
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
}
