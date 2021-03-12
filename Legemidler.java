abstract class Legemidler{

  private static int globalID = 0;

  // alle instansvarabler som senere skal arves bør ha protected
  protected String navn;
  protected int id;
  protected int pris;
  protected double mg;

  public Legemidler(String navn, int pris, double mg){
    this.navn = navn;
    this.id = globalID;
    this.pris = pris;
    this.mg = mg;
    globalID++;
  }

  public String hentNavn(){
    return navn;
  }

  public int hentId(){
    return id;
  }

  public int hentPris(){
    return pris;
  }

  public double hentVirkestoff(){
    return mg;
  }

  public int settNyPris(int nyPris){

    if (nyPris < 0){
      nyPris = 0;
    } else { pris = nyPris;
  }
    return pris;
}
  @Override
  public String toString(){
    return "    Navn: " + navn + "\n    ID nummer: " + id + "\n    Pris: " + 
    pris + " kr" + "\n    Virkestoff: " + mg + " mg";
  }
}

class Vanlig extends Legemidler{

  public Vanlig (String navn, int pris, double mg) {
  super(navn, pris, mg);
  }

  @Override
  public String toString() {
      return super.toString() + "\n    Type: vanlig legemiddel";
  }
}

class Narkotisk extends Legemidler{

  int hvorNarkotisk;

  public Narkotisk (String navn, int pris, double mg, int narkotiskStyrke){
    super(navn, pris, mg);
    hvorNarkotisk = narkotiskStyrke;

    }

  public int hentNarkotiskStyrke(){
    return hvorNarkotisk;
  }

  @Override
  public String toString() {
      return super.toString() + "\n    Type: narkotisk legemiddel" + "\n    Styrke: " + hvorNarkotisk + " mg";
  }
}

class Vanedannende extends Legemidler {

  int hvorVanedannende;

  public Vanedannende (String navn, int pris, double mg, int vanedannendeStyrke){
    super(navn, pris, mg);
    hvorVanedannende = vanedannendeStyrke;
  }

  public int hentVanedannendeStyrke(){
    return hvorVanedannende;
  }

  @Override
  public String toString() {
      return super.toString() + "\n    Type: vanedannende legemiddel" + "\n    Styrke: " + hvorVanedannende + " mg";
  }
}
