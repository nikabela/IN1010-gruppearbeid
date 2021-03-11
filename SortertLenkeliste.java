public class SortertLenkeliste <T extends Comparable <T>> extends LenketListe <T>{

  @Override
  public void leggTil(T x){
    int index = 0;


    // itererer gjennom lista, så lenge i er mindre enn strl minus 1
    for (int i = 0; i < stoerrelse(); i++){
      // sammenligner x med data på indeks
      if (x.compareTo(hent(i)) > 0){
        index = i + 1;

      }
    } super.leggTil(index, x);
  }

  @Override
  public T fjern(){

    if (stoerrelse() == 1){
      return super.fjern();

    }

    else {
      return fjern(stoerrelse() - 1);
    }
}

@Override
   public void sett(int pos, T x) {
       throw new UnsupportedOperationException();
   }

   @Override
   public void leggTil(int pos, T x) {
       throw new UnsupportedOperationException();
   }
}
