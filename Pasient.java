
class Pasient {

   private static int globalID = 0;

   private String navn;
   private String fodselsnr;
   private int pasientId;
   private Stabel <Resept> resepter;

   public Pasient(String navn, String fodselsnr){
     // id skal settes idet objektet opprettes, ikke tas inn som argument
     pasientId = globalID;
     globalID ++;

     this.navn = navn;
     this.fodselsnr = fodselsnr;
     resepter = new Stabel<Resept>();
   }

   public String hentNavn(){
     return navn;
   }

   public String hentFodselsnr(){
     return fodselsnr;
   }

   public int hentPasientId(){
     return pasientId;
   }

   // metode som legger til en resept i stabelen
   public void leggTilReseptIStabel(Resept resept){
     resepter.leggPaa(resept);
   }

   public Stabel<Resept> hentResepter(){
     return resepter;
   }
   
   public String toString(){
     return ("Navn: " +  this.hentNavn() + " ID: " + this.hentPasientId() + " FoedselsNr: " + this.hentFodselsnr());
   }
 }
