class Spesialist extends Lege implements Godkjenningsfritak{  //denne er en subklasse til Lege som også bruker interface Godkjenningsfritak

  protected String kontrollId;

  public Spesialist(String navn, String kontrollId){ //klassens konstruktør
    super(navn);
    this.kontrollId = kontrollId;
  }

  public String hentKontrollId(){return kontrollId;}  //overrides metoden i interface og returnerer kontrollid

  public String toString() {return "Legens navn: " + navn + ". Kontroll Id: " + kontrollId + ".";}

}
