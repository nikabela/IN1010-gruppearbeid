import java.util.Scanner;           //jeg importerte biblioteken så at jeg kan sette ny pris til legemidlene. det var uklart hva man skal gjøre med den metoden.
import java.io.PrintWriter;

abstract class Legemiddel {  //opprettet abstrakt klass

  public static int teller = 0; //jeg bruker en teller til å gi individuel ID til objekter i klassen
  protected int legemiddelId;
  protected String navn;
  protected int pris;
  protected double virkestoff;
  int nypris;

  public Legemiddel(String navn, int pris, double virkestoff){
    this.navn = navn;
    this.pris = pris;
    this.virkestoff = virkestoff;
    this.legemiddelId = teller + 1;   //når en legemiddel objekt lages, får den en ID
    teller++;                         //neste legemiddel objekten får en annen ID
  }

  public int hentId() {return legemiddelId;}   //returnerer relevante info ved disse metodene.
  public String hentNavn() {return navn;}
  public int hentPris(){return pris;}
  public double hentVirkestoff() {return virkestoff;}

  public int settNyPris(){                        //jeg be brukeren til å sett ny pris for legemiddelen.
    Scanner nypris = new Scanner (System.in);     //nypris skal skrives inn av brukeren
    System.out.print("Sett inn ny pris: ");
    pris = Integer.parseInt(nypris.nextLine());   //inputen blir den ny prisen som returneres. Måtte omgjøre Stringen til en integer for metoden.
    return pris;
  }

  public String toString(){
    return "Medisinens Id: " + legemiddelId + ". Medisinens navn: " + navn + ". Medisinens pris: " + pris + "kr. Virkestoff i medisinen: " + virkestoff+ "mg.";
  }

}
