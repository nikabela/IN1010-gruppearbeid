abstract class Lege{  //opprettet abrakt klassen

  protected String navn;
  protected static int teller3 = 0;  //denne bruker jeg til å tilordne unik ID
  protected int legeId;

  public Lege(String navn){
    this.navn = navn;
    this.legeId = teller3 +1;  //setter unik Id til objekten når den opprettes
    teller3++;                  // neste objekten av samme klasse får en annen Id
  }

  public String hentNavn() {return navn;}  //returnerer navn

  public String toString() {return "Legens navn: " + navn + ".";}
}
