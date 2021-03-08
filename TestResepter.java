class TestResepter{  //testprogram til Resept og Lege klassene

  public static void main(String[] args){

    Vanlig vanlig = new Vanlig("Paracet", 200, 15.75);                        //obrettet et par legemiddel objekter som kan sendes inn som argument
    Narkotisk narkotisk = new Narkotisk("Morfin", 530, 25.00, 6);
    Vanedannende vanedannende = new Vanedannende("Cataflam", 450, 16.75, 4);

    Spesialist lege1 = new Spesialist("Petter Nordmann", "FL2365");  //opprettet to Lege objekter som kan sendes inn som argument til reseptene
    Spesialist lege2 = new Spesialist("Kari Pedersen", "VR4531");

    Blaaresept resept1 = new Blaaresept(vanlig, lege1, 241287, 6);                    //opprettet 3 objekter for de subklassene i Resept (og i Hviteresepter)
    Militaarresepter resept2 = new Militaarresepter(narkotisk, lege2, 341298, 10);
    Presepter resept3 = new Presepter(vanedannende, lege1, 141187, 7);


    System.out.println(resept1); //forventet utskrift: Reseptens Id: 1. Legemiddelens navn: Paracet. Legens navn: Petter Nordmann. Pasientens Id: 241287. Reit: 6.
    System.out.println("Reseptens farge: " + resept1.farge(resept1) + ". Pris etter rabatt: " + resept1.prisAABetale() + "kr."); //forventet: Reseptens farge: blaa. Pris etter rabatt: 50kr.
    System.out.println();

    System.out.println(resept2); //forventet: Reseptens Id: 2. Legemiddelens navn: Morfin. Legens navn: Kari Pedersen. Pasientens Id: 341298. Reit: 10.
    System.out.println("Reseptens farge: " + resept2.farge(resept2) + ". Pris etter rabatt: " + resept2.prisAABetale() + "kr."); //forventet: Reseptens farge: hvit. Pris etter rabatt: 0kr. 0 fordi den er militaaresept.
    System.out.println();

    System.out.println(resept3); //forventet: eseptens Id: 3. Legemiddelens navn: Cataflam. Legens navn: Petter Nordmann. Pasientens Id: 141187. Reit: 3. (fordi det settes fast til 3!)
    System.out.println("Reseptens farge: " + resept3.farge(resept3) + ". Pris etter rabatt: " + resept3.prisAABetale() + "kr."); //forventet: RReseptens farge: hvit. Pris etter rabatt: 342kr.

    resept2.bruk(); //bruker en av de reseptene to ganger
    resept2.bruk();

    System.out.println(resept2); //forventer at reiten har gått fra 10 til 8.
    System.out.println();

    System.out.println(resept3.hentLeggemiddel()); //tester metoden. Den var ikke inn i min toString. Forventer info om "vanedannende" legemiddler: navn: Cataflam, pris 450kr, virkestoff 16.75mg, styrke 4. ID 3.
    System.out.println();
  }
}
