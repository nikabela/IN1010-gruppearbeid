class Lege{

  String lege;

  public Lege(String navn){
    lege = navn;
  }

  public String hentLege(){
    return lege;
  }
}

class Spesialist extends Lege implements Godkjenningsfritak{

  int kontrollId;

  public Spesialist(String navn, int kontroll){
  super(navn);
  kontrollId = kontroll;
  }

  public int hentKontrollId(){
    return kontrollId;
  }
}
