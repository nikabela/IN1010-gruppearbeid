import java.util.*;
import java.io.*;

public class Hovedprogram {
    public static void main(String[] args) throws UlovligFormat, UlovligUtskrift {
        Legesystem legesystem = new Legesystem();
        legesystem.lesInnFraFil("inndata.txt");

        System.out.println("Du har foelgende valg:");
        System.out.println("1: Du kan skrive ut en fullstendig oversikt over pasienter, leger, legemidler og resepter  \n");  //peker til deloppgave E3
        System.out.println("2: Du kan opprette og legge til nye elementer i systemet \n"); //peker til deloppgave E4
        System.out.println("3: Du kan bruke en gitt resept fra listen til en pasient   \n"); //peker til deloppgave E5
        System.out.println("4: Du kan skrive ut forskjellige former for statistikk  \n"); //peker til deloppgave E6
        System.out.println("5: Skrive alle data til fil \n"); //peker til deloppgave E7
        System.out.println("0: programmet avslutter. \n ");
        System.out.println("Skriv inn tall til valget ditt: ");

        Scanner valg = new Scanner(System.in);
        int in = valg.nextInt();

        while(in != 0){             //her skal programmet håpe til riktig deloppgave i E
          if(in == 1){
          }else if (in ==2){
          }else if(in == 3){
          }else if(in == 4){
          }else if(in == 5){
          }else{
            System.out.println("ugyldig innput : ");
          }
          break;
        }
    }
}
