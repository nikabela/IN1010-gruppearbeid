import java.util.*;
import java.io.*;

public class Hovedprogram {
    public static void main(String[] args) throws UlovligFormat, UlovligUtskrift {
      Legesystem legesystem = new Legesystem();
      legesystem.lesInnFraFil("inndata.txt");

      startMeny(); // kaller paa start meny (se nederst)

      Scanner valg = new Scanner(System.in);
      int in = valg.nextInt();

      // selve kommandoloekken
      while(in != 0){ // her skal programmet hoppe til riktig deloppgave i E
        if(in == 1){
          System.out.println("\n\nEn fullstendig oversikt over pasienter, leger, legemidler og resepter:");
          legesystem.hentOversikt();

          // "gaa-tilbake"-blokken; kan bare kopieres til andre deler av denne if-sjekken naar disse er skrevet ferdig
          System.out.println("Skriv inn 0 for aa gaa tilbake til start meny: ");
          int inp = valg.nextInt();
          while (inp != 0) {
            System.out.println("Ugyldig input!");
            inp = valg.nextInt();
          }
          if (inp == 0) {startMeny(); in = valg.nextInt(); continue;}
        }else if (in == 2){
          leggTilElementMeny();
          int inp = valg.nextInt();
          while (inp != 0) {
            if(inp == 1) {legesystem.leggTilPasient(); System.out.println("Ny pasient er legget til.");}
            else if(inp == 2) {legesystem.leggTilLege(valg); System.out.println("Ny lege er legget til.");}
            else if(inp == 3) {legesystem.leggTilResept(); System.out.println("Ny resept er legget til.");}
            else if(inp == 4) {legesystem.leggTilLegemiddel(); System.out.println("Ny legemiddel er legget til.");}
            else System.out.println("Ugyldig input!");
            leggTilElementMeny();
            inp = valg.nextInt();
          }
          if (inp == 0) {startMeny(); in = valg.nextInt(); continue;
          }
        }else if(in == 3){
        }else if(in == 4){
        }else if(in == 5){
        }else{
          System.out.println("Ugyldig input!");
        }
        break;
      }
      valg.close();
      System.out.println("\nAvslutter programmet. Ha det!");
    }


    static void startMeny() {
      System.out.println("Start meny. Du har foelgende valg:" + "\n" +
      "  1: Du kan skrive ut en fullstendig oversikt over pasienter, leger, legemidler og resepter." + "\n" +   //peker til deloppgave E3
      "  2: Du kan opprette og legge til nye elementer i systemet." +"\n" +   //peker til deloppgave E4
      "  3: Du kan bruke en gitt resept fra listen til en pasient."+ "\n" +   //peker til deloppgave E5
      "  4: Du kan skrive ut forskjellige former for statistikk." + "\n" +    //peker til deloppgave E6
      "  5: Skrive alle data til fil." + "\n" +     //peker til deloppgave E7
      "  0: Programmet avslutter.");
    }

    static void leggTilElementMeny(){
      System.out.println("\nDu faar naa fire valg." + "\n" +
      "  For aa legge til en pasient, tast '1'" + "\n" +
      "  For aa legge til en lege, tast '2'" +"\n" +
      "  For aa legge til en resept, tast '3'"+ "\n" +
      "  For aa legge til et legemiddel, tast '4'" + "\n" +
      "  For aa gaa tilbake til start meny, tast 0.");
    }


}
