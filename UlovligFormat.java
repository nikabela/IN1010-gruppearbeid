/* Unntaksklasse UlovligFormat for LesInnFraFil-metoden.
Hvis det er noe gal med formatten på inputtet, saa skal dette 
unntaket kastes.*/

public class UlovligFormat extends Exception{
    UlovligFormat(String  linje) {
        super("Input-linjen" + linje + "har ugyldig format. Elementet skal ikke lagres.");
    }
}
