
class Pasient <T> {

   private static int globalID = 0;

   private String navn;
   private String fodselsnr;
   private int pasientId;
   private Stabel <T> resepter;

   public Pasient(String navn, String fodselsnr){
     // id skal settes idet objektet opprettes, ikke tas inn som argument
     pasientId = globalID;
     globalID ++;

     this.navn = navn;
     this.fodselsnr = fodselsnr;
     resepter = new Stabel();
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
   public void leggTilReseptIStabel(T resept){
     resepter.leggPaa(resept);
   }

   public Stabel hentResepter(){
     return resepter;
   }
 }
