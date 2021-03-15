class Stabel <T>  extends LenketListe <T>{

  // legger til element på slutten av lista
  public void leggPaa(T x){
    leggTil(x);
  }

  // Tar av elementet på slutten av lista
  public T taAv(){
   return fjern(stoerrelse() -1);
  }
}
