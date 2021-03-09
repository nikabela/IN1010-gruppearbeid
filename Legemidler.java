abstract class Legemidler{

  private static int globalID = 0;

  // alle instansvarabler som senere skal arves bør ha protected
  protected String navn;
  protected int id;
  protected int pris;
  protected double mg;

  public Legemidler(String navn, int id, int pris, double mg){
    id = globalID;
    globalID++;


    this.navn = navn;
    this.id = id;
    this.pris = pris;
    this.mg = mg;
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
   return navn+" "+id+" "+pris+" "+mg;
  }
}

class Vanlig extends Legemidler{

  public Vanlig (String navn, int id, int pris, double mg) {
  super(navn, id, pris, mg);
  }
}

class Narkotisk extends Legemidler{

  int hvorNarkotisk;

  public Narkotisk (String navn, int id, int pris, double mg, int narkotiskStyrke){
    super(navn, id, pris, mg);
    hvorNarkotisk = narkotiskStyrke;

    }

  public int hentNarkotiskStyrke(){
    return hvorNarkotisk;
  }
}

class Vanedannende extends Legemidler {

  int hvorVanedannende;

  public Vanedannende (String navn, int id, int pris, double mg, int hvorVanedannende){
    super(navn, id, pris, mg);
    hvorVanedannende = hvorVanedannende;
  }

  public int hentVanedannendeStyrke(){
    return hvorVanedannende;
  }
}
