import java.util.*;
import java.io.*;

public class Hovedprogram {
  public static void main(String[] args) throws UlovligFormat, UlovligUtskrift, IOException  {
    Legesystem legesystem = new Legesystem();
    legesystem.lesInnFraFil("inndata.txt");

    startMeny(); // kaller paa start meny (se nederst)

    Scanner valg = new Scanner(System.in);
    int in = Integer.parseInt(valg.nextLine().trim()); 
    // funker bedre enn nextInt(), fordi kombinasjon av den med nextLine() skaper problemer

    // selve kommandoloekken; her skal programmet hoppe til riktig deloppgave i E
    while(in != 0){
      if(in == 1){
        System.out.println("\n\nEn fullstendig oversikt over pasienter, leger, legemidler og resepter:");
        legesystem.hentOversikt();
        gaaTilbake(valg);
        in = Integer.parseInt(valg.nextLine().trim());
        continue;
      }else if (in == 2){
        leggTilElementMeny();
        int inp = Integer.parseInt(valg.nextLine().trim());
        while (inp != 0) {
          if(inp == 1) {legesystem.leggTilPasient(valg);}
          else if(inp == 2) {legesystem.leggTilLege(valg);}
          else if(inp == 3) {legesystem.leggTilLegemiddel(valg);}
          else if(inp == 4) {legesystem.leggTilResept(valg);}
          else System.out.println("Ugyldig input!");
          leggTilElementMeny();
          inp = Integer.parseInt(valg.nextLine().trim());
        }
        if (inp == 0) {startMeny(); in = Integer.parseInt(valg.nextLine().trim()); continue;}
      }else if(in == 3){
        legesystem.brukResept(valg);
        gaaTilbake(valg);
        in = Integer.parseInt(valg.nextLine().trim());
        continue;
      }else if(in == 4){
        System.out.println("\n\nStatistikk:\n");
        legesystem.hentStatistikk();
        gaaTilbake(valg);
        in = Integer.parseInt(valg.nextLine().trim());
        continue;
      }else if(in == 5){
        try {
          legesystem.skrivTilFil();
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Noe gikk galt");
          }
        gaaTilbake(valg);
        in = Integer.parseInt(valg.nextLine().trim());
        continue;
      }else{
        System.out.println("Ugyldig input!");
      }
      break;
    }
    valg.close();
    System.out.println("\nAvslutter programmet. Ha det!");
  }


  static void startMeny() {
    System.out.println("\nStart meny. Du har foelgende valg:" + "\n" +
    "  1: Skrive ut en fullstendig oversikt over pasienter, leger, legemidler og resepter." + "\n" +  // peker til deloppgave E3
    "  2: Opprette og legge til nye elementer i systemet." +"\n" +                                   // peker til deloppgave E4
    "  3: Bruke en gitt resept fra listen til en pasient."+ "\n" +                                  // peker til deloppgave E5
    "  4: Skrive ut forskjellige former for statistikk." + "\n" +                                  // peker til deloppgave E6
    "  5: Skrive alle data til fil." + "\n" +                                                     // peker til deloppgave E7
    "  0: Avslutte programmet.");
    System.out.print("Tast inn kommandoen: ");
  }

  static void leggTilElementMeny() {
    System.out.println("\nDu faar naa fem valg." + "\n" +
    "  For aa legge til en pasient, tast 1." + "\n" +       // peker til leggTilPasient()
    "  For aa legge til en lege, tast 2." +"\n" +          // peker til leggTilLege()
    "  For aa legge til en legemiddel, tast 3."+ "\n" +   // peker til leggTilLegemiddel()
    "  For aa legge til et resept, tast 4." + "\n" +     // peker til LeggTilResept
    "  For aa gaa tilbake til start meny, tast 0.");
    System.out.print("Tast inn kommandoen: ");
  }

  // tar brukeren tilbake til startmeny
  static void gaaTilbake(Scanner valg) {
    System.out.print("Tast inn 0 for aa gaa tilbake til start meny: ");
    int inp = Integer.parseInt(valg.nextLine().trim());
    while (inp != 0) {
      System.out.println("Ugyldig input!");
      inp = Integer.parseInt(valg.nextLine().trim());
    }
    startMeny();
  }
}
