public class LenketListe <T> implements Liste<T>{

  private Node forste = null;
  private Node siste = null;

  class Node {
    T data;
    Node neste = null;
    Node forrige = null;


    Node(T data){
      this.data = data;
    }

    public T hentData(){
      return data;
    }
  }

  @Override
  public String toString(){
    Node denne = forste;
    String utskrift = " ";
    while (denne != null){
      utskrift += denne.data + " -> ";
      denne = denne.neste;
    }
    return utskrift;
  }

  // for å finne størrelsen på lista itererer jeg gjennom lista og teller alle nodene
  @Override
  public int stoerrelse(){

    Node denne = forste;
    int teller = 0;

    while (denne != null){
      teller ++;
      denne = denne.neste;
    }
    return teller;

  }

  public void leggTil(int pos, T x){

    Node nyNode = new Node(x);
    Node denne = forste;
    Node sist = siste;

    // hvis ugyldig indeks
    if(pos < 0 || pos > stoerrelse()){

      throw new UgyldigListeIndeks(pos);
    }

  // om lista er tom
    else if (forste == null){


        forste = siste = nyNode;
        forste.forrige = null;
        siste.neste = null;

    }

    else if (pos == 0){
      // System.out.println("wtf");

      forste.forrige = nyNode;
      nyNode.neste = forste;
      nyNode.forrige = null;
      forste = nyNode;



    }  else if (pos == stoerrelse()){

      siste.neste = nyNode;
      nyNode.forrige = siste;
      siste = nyNode;
      siste.neste = null;

    } else {

      for (int i= 0; i < pos && denne.neste != null; i++){
        denne = denne.neste;
      }

      nyNode.neste = denne;
      denne.forrige.neste = nyNode;
      nyNode.forrige = denne.forrige;
      denne.forrige = nyNode;
    }
  }

  // denne metoden legger til et element på slutten av lista

  public void leggTil(T x){

    Node nyNode = new Node (x);

    if (forste == null){
      forste = siste = nyNode;
      forste.forrige = null;
      siste.neste = null;
    } else {
      siste.neste = nyNode;
      nyNode.forrige = siste;
      siste = nyNode;
      siste.neste = null;
    }
  }

  public void sett(int pos, T x){


    Node denne = forste;
    Node sist = siste;

    if(pos <0 || pos > stoerrelse()-1 ){
      throw new UgyldigListeIndeks(pos);
    }

    if (pos == 0){

      denne.data = x;

    }
    else if (pos == stoerrelse()){

      System.out.println(sist);
      sist.data = x;


    }

    else{
      for (int i= 0; i < pos && denne.neste != null; i++){
      denne = denne.neste;
    }
    denne.data = x;

  }
}

  public T hent(int pos){

    Node denne = forste;
    Node sist = siste;

    if(pos <0 || pos > stoerrelse() -1 ){
      throw new UgyldigListeIndeks(pos);
    }

    // hvis liste er tom
    if (denne == null){

      throw new UgyldigListeIndeks(pos);
    }
    else if (pos == 0){
      return denne.data;
    }
    else if (pos == stoerrelse() -1){
      return sist.data;
    }

    else {
      for (int i = 0; denne != null && i < pos; i++ ){
        denne = denne.neste;
      } return denne.data;
    }
  }
  // // denne metoden fjerner og returnerer elementet på starten av lista
  public T fjern(){

    Node denne = forste;
    // om lista er tom gis en feilmelding
    if (denne == null){
      throw new UgyldigListeIndeks(-1);
    }
    if (forste == siste){
      siste = null;

    }
     else {

      forste.neste.forrige = null;
    }
    forste = forste.neste;
    denne.neste = null;
    return denne.data;
  }

  // fjerner node på gitt indeks
  public T fjern(int pos){
    Node denne = forste;
    Node tmp = siste;
    Node fjern;


    if (pos < 0 || pos > stoerrelse() -1){
      throw new UgyldigListeIndeks(pos);
    }

    else if (pos == stoerrelse() -1  && denne.neste != null){

      fjern = siste;

      siste = siste.forrige;
      siste.neste = null;

      return fjern.data;


    } else  if (pos == 0 ) {


      return fjern();

    }
     else {
      for (int i = 0; denne != null && i < pos; i++ ){
        denne = denne.neste;
      }
      denne.forrige.neste = denne.neste;

      denne.neste.forrige = denne.forrige;

    } return denne.data;
    }
  }
