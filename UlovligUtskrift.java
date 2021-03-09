// Unntaksklasse UlovligUtskrift;
// Dersom en vanlig lege forsøker å skrive ut et narkotisk Legemiddel,
// kastes unntaket UlovligUtskrift

public class UlovligUtskrift extends Exception{
  UlovligUtskrift(Lege l, Legemiddel lm){
    super("Legen " + l.hentLege() + " har ikke lov til å skrive ut " + lm.hentNavn());
    }
}
