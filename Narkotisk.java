public class Narkotisk extends Legemiddel{  //subklass til Legemiddel

  protected int styrke;

  public Narkotisk(String navn, int pris, double virkestoff, int styrke){
    super(navn, pris, virkestoff);  //arver superklassens konstruktør
    this.styrke = styrke;           //konstruktøren utviddes med styrke
  }

  public int hentNarkotiskStyrke() {return styrke;}  //returnerer styrke som var ikke med i Legemiddel.java

  public String toString(){return "Medisinens Id: " + legemiddelId + ". Medisinens navn: " + navn + ". Medisinens pris: " + pris + "kr. Virkestoff i medisinen: " + virkestoff + "mg. Medisinens styrke: " + styrke + ".";}


}
