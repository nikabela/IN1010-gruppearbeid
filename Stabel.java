class Stabel <T>  extends LenketListe <T>{

  private Node forste = null;
  private Node siste = null;

  // legger til element på slutten av lista
  public void leggPaa(T x){
    leggTil(x);
  }

  // Tar av elementet på slutten av lista
  public T taAv(){

   return fjern(stoerrelse() -1);

}

}
