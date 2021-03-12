import java.util.*;

class LeggTilElement{

  public static void main(String [] args){

    Scanner brukerInput = new Scanner(System.in);
    int in = brukerInput.nextInt();

    // public void meny(){

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
    }
        // opprettElement(brukerInput);

    }

    // public void leggTilPasient(){
    //
    // }
  // }
}
