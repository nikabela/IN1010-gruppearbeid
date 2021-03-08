class Integrasjonstest{

  public static void main(String[] args){

    Vanedannende vanedannende = new Vanedannende("Vann", 545, 16.75, 4); //oppretter objekter i klassene for testing
    Narkotisk narkotisk = new Narkotisk("Morfin", 670, 25.00, 6);

    Spesialist lege1 = new Spesialist("Petter Nordmann", "FL2365");
    Spesialist lege2 = new Spesialist("Kari Pedersen", "VR4531");

    Blaaresept resept1 = new Blaaresept(narkotisk, lege1, 241287, 6);
    Militaarresepter resept2 = new Militaarresepter(narkotisk, lege2, 341298, 10);
    Presepter resept3 = new Presepter(vanedannende, lege1, 141187, 7);

    System.out.println(vanedannende); //forventet resultat: navn: Vann, pris 545kr, virkestoff 16.75mg, styrke 4. LegemiddelID 1.
    System.out.println();
    System.out.println(narkotisk);  //forventet resultat: navn: Morfin, pris 670kr, virkestoff 25.00mg, styrke 6. LegemiddelID 2.
    System.out.println();

    System.out.println(vanedannende.settNyPris());
    System.out.println();
    System.out.println(vanedannende); //forventet resultat: bare prisen endrer seg til den brukeren oppga.
    System.out.println();

    System.out.println(resept1);  //forventet: Id: 1. Navn: Morfin. Legens navn: Petter Nordmann. Pasientens Id: 241287. Reit: 6.
    System.out.println("Reseptens farge: " + resept1.farge(resept1) + ". Pris etter rabatt: " + resept1.prisAABetale() + "kr."); //forventet: blaa og 150kr.
    System.out.println();
    System.out.println(resept2); //Id: 2. Navn: Morfin. Legens navn: Kari Pedersen. Pasientens Id: 341298. Reit: 10.
    System.out.println("Reseptens farge: " + resept2.farge(resept2) + ". Pris etter rabatt: " + resept2.prisAABetale() + "kr."); //forventet: hvit og 0kr.
    System.out.println();
    System.out.println(resept3); //forventet: Id: 3. Navn: Vann. Lege: Petter Nordmann. Pas. Id: 141187. Reit: 3.
    System.out.println("Reseptens farge: " + resept3.farge(resept3) + ". Pris etter rabatt: " + resept3.prisAABetale() + "kr."); //forventet: hvit og 492 kr.
    System.out.println();
    System.out.println(resept3.hentLeggemiddel()); //tester metoden. Den var ikke inn i min toString. Forventer info om "vanedannende" legemiddler: navn: Vann, pris 545kr, virkestoff 16.75mg, styrke 4. LegemiddelID 1.
    System.out.println();


    resept1.bruk(); //bruker reseptene
    resept1.bruk();
    resept2.bruk();
    resept3.bruk();


    System.out.println(resept1); //forventer at reiten blir 4 i stedet for 6, fordi brukte det 2 ganger.
    System.out.println();
    System.out.println(resept2); //forventer at reiten er 9 i stedet for 10
    System.out.println();
    System.out.println(resept3); //forventer at reiten er 2 istedet for 3.
    System.out.println();

    System.out.println(lege1); //Forventer navn Petter Nordmann og kontorollid: FL2365.
    System.out.println();
    System.out.println(lege2); //Forventer navn KAri Pedersen og kont.Id: VR4531.
    System.out.println();


  }
}
