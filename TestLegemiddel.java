public class TestLegemiddel{  //testprogram til Legemiddel klassen og subklassene til Legemiddel
  public static void main(String[] args){

//opprettet tre objekter, en i alle subklasser:

    Vanlig vanlig = new Vanlig("Paracet", 200, 15.75);
    Narkotisk narkotisk = new Narkotisk("Morfin", 120, 25.00, 6);
    Vanedannende vanedannende = new Vanedannende("Vann", 65, 16.75, 4);

    System.out.println();
    System.out.println(vanlig); //forventet resultat: navn: Paracet, pris 200kr, virkestoff 15.75.
    System.out.println();
    System.out.println(narkotisk);  //forventet resultat: navn: Morfin, pris 120kr, virkestoff 25.00, styrke 6mg.
    System.out.println();
    System.out.println(vanedannende); //forventet resultat: navn: Vann, pris 65kr, virkestoff 16.75, styrke 4mg.
    System.out.println();
    System.out.println(vanedannende.settNyPris());
    System.out.println();
    System.out.println(vanedannende); //forventet resultat: navn: Vann Pris: det som brukeren oppgir. virkestoff 16.75, styrke 4mg.

  }
}

//Dette er min tidligere program før jeg laget toString()
/*    System.out.println("Medisinens Id: " + vanlig.hentId());
    System.out.println("Medisinens navn: " + vanlig.hentNavn());
    System.out.println("Medisinens pris: " + vanlig.hentPris() + "kr.");
    System.out.println("Virkestoff i medisinen: " + vanlig.hentVirkestoff() + "mg.");
    System.out.println("Medisinens ny pris: " + vanlig.settNyPris() + "kr.");
    System.out.println();

    System.out.println("Medisinens Id: " + narkotisk.hentId());
    System.out.println("Medisinens navn: " + narkotisk.hentNavn());
    System.out.println("Medisinens pris: " + narkotisk.hentPris() + "kr.");
    System.out.println("Virkestoff i medisinen: " + narkotisk.hentVirkestoff() + "mg.");
    System.out.println("Medisinens ny pris: " + narkotisk.settNyPris() + "kr.");
    System.out.println("Medisinens styrke: " + narkotisk.hentNarkotiskStyrke());
    System.out.println();


    System.out.println("Medisinens Id: " + vanedannende.hentId());
    System.out.println("Medisinens navn: " + vanedannende.hentNavn());
    System.out.println("Medisinens pris: " + vanedannende.hentPris() + "kr.");
    System.out.println("Virkestoff i medisinen: " + vanedannende.hentVirkestoff() + "mg.");
    System.out.println("Medisinens ny pris: " + vanedannende.settNyPris() + "kr.");
    System.out.println("Medisinens styrke: " + vanedannende.hentVanedannendeStyrke()); */
