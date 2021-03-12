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
      while(in != 0){             //her skal programmet hoppe til riktig deloppgave i E
        if(in == 1){
          System.out.println("\n\nEn fullstendig oversikt over pasienter, leger, legemidler og resepter:");
          legesystem.hentOversikt();

          // "gaa-tilbake"-blokken; skal bare kopieres til andre deler av denne if-sjekken naar disse er skrevet ferdig
          System.out.println("Skriv inn 9 for aa gaa tilbake til start meny: ");
          int inp = valg.nextInt();
          while (inp != 9) {
            System.out.println("Ugyldig input!");
            inp = valg.nextInt();
          }
          if (inp == 9) {startMeny(); in = valg.nextInt();}
        }
        else if (in ==2){
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
      System.out.println("Du har foelgende valg:");
      System.out.println("  1: Du kan skrive ut en fullstendig oversikt over pasienter, leger, legemidler og resepter");  //peker til deloppgave E3
      System.out.println("  2: Du kan opprette og legge til nye elementer i systemet"); //peker til deloppgave E4
      System.out.println("  3: Du kan bruke en gitt resept fra listen til en pasient"); //peker til deloppgave E5
      System.out.println("  4: Du kan skrive ut forskjellige former for statistikk"); //peker til deloppgave E6
      System.out.println("  5: Skrive alle data til fil"); //peker til deloppgave E7
      System.out.println("  0: Programmet avslutter.");
      System.out.println("Skriv inn tall til valget ditt: ");
    }

    static void leggTilElementMeny(){
      System.out.println("Du faar naa fire valg." + "\n" +
      "For aa legge til en pasient, tast '1'" + "\n" +
      "For aa legge til en lege, tast '2'" +"\n" +
      "For aa legge til en resept, tast '3'"+ "\n" +
      "For aa legge til et legemiddel, tast '4'" + "\n" +
      "For aa avslutte, tast 0.");

      while (in != 0){

        switch(in) {

          case 1:

            leggTilPasient();
            break;

        }

        switch (in){
          case 2:
           
        }
      }
    }


}
