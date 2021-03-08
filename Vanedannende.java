public class Vanedannende extends Legemiddel{  //subklasse til Legemiddel klass

  protected int styrke;

  public Vanedannende(String navn, int pris, double virkestoff, int styrke){
    super(navn, pris, virkestoff);  //arvet konstruktøren fra superklassen
    this.styrke = styrke;           //utviddes med legemiddelens styrke
  }

  public int hentVanedannendeStyrke() {return styrke;}

  public String toString(){return "Medisinens Id: " + legemiddelId + ". Medisinens navn: " + navn + ". Medisinens pris: " + pris + "kr. Virkestoff i medisinen: " + virkestoff + "mg. Medisinens styrke: " + styrke + ".";}

}
