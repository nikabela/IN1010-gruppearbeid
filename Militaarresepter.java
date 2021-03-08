class Militaarresepter extends Hviteresepter{ //subklassen opprettet

  public Militaarresepter(Legemiddel legemiddel, Lege utskrivendeLege, int pasientId, int reit){
    super(legemiddel, utskrivendeLege, pasientId, reit);  //arvet konstruktøren fra superklassen
  }
  @Override  //overrides abstrakt metoden fra Resepten.java (og fra Hviteresepter klassen)
  public int prisAABetale(){
    int prisfortilbud = legemiddel.hentPris(); //en legemiddel objekt ble sendt inn som argument, man får prisen til legemiddelen med en metod fra Legemiddel klassen.
    prisfortilbud = 0; //Militaarresepter er gratis
    return prisfortilbud;  
  }
}
