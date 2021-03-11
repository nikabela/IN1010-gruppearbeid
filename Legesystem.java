import java.util.Scanner;
import java.io.File;

class Legesystem {
    private LenketListe<Pasient> pasientListe = new LenketListe<Pasient>();
    private LenketListe<Legemidler> legemiddelListe = new LenketListe<Legemidler>();
    private LenketListe<Lege> legerListe = new LenketListe<Lege>();
    private LenketListe<Resept> reseptListe = new LenketListe<Resept>();


    public void lesInnFraFil(String filnavn) throws UlovligFormat, UlovligUtskrift {
        Scanner fil = null;

        try { //haandterer FileNotFoundException
            fil = new Scanner(new File(filnavn));
        } catch (Exception e) {
            System.out.println("Kan ikke lese filen " + filnavn + ". Avslutter programmet.");
            System.exit(1);
        }

        //sjekker at filen er ikke tom
        if (!fil.hasNextLine()) {System.out.println("Filen er tom!"); System.exit(1);}

        //leser inn pasient-objekter
        if (fil.nextLine() == "# Pasienter (navn, fnr)") {
            while (fil.nextLine() != "# Legemidler (navn,type,pris,virkestoff,[styrke])") {
                String linje = fil.nextLine();
                String[] biter = linje.split(",");
                if (biter.length != 2) throw new UlovligFormat(linje);
                
                Pasient nyPasient = new Pasient(biter[0], biter[1]);
                pasientListe.leggTil(nyPasient);
            }
        } else {System.out.println("Filformatten for pasienter er ikke riktig."); System.exit(1);}

        //leser inn legemiddel-objekter
        if (fil.nextLine() == "# Legemidler (navn,type,pris,virkestoff,[styrke])") {
            while (fil.nextLine() != "# Leger (navn,kontrollid / 0 hvis vanlig lege)") {
                String linje = fil.nextLine();
                String[] biter = linje.split(",");
                if (biter.length != 4 || biter.length != 5) throw new UlovligFormat(linje);                
                String navn = biter[0];
                String type = biter[1];
                int pris = Integer.parseInt(biter[2]);
                double virkestoff = Double.parseDouble(biter[3]);

                //om legemiddel er av typen 'vanlig'
                if (type == "vanlig") {
                    Vanlig nyLegemiddel = new Vanlig(navn, pris, virkestoff);
                    legemiddelListe.leggTil(nyLegemiddel);
                }

                //om legemiddel er av typen 'narkotisk'
                if (type == "narkotisk") {
                    int styrke = Integer.parseInt(biter[4]);
                    Narkotisk nyLegemiddel = new Narkotisk(navn, pris, virkestoff, styrke);
                    legemiddelListe.leggTil(nyLegemiddel);
                }

                //om legemiddel er av typen 'vanedannende'
                if (type == "vanedannende") {
                    int styrke = Integer.parseInt(biter[4]);
                    Vanedannende nyLegemiddel = new Vanedannende(navn, pris, virkestoff, styrke);
                    legemiddelListe.leggTil(nyLegemiddel);
                }
            }
        } else {System.out.println("Filformatten for legemidler er ikke riktig."); System.exit(1);}

        //leser inn lege-objekter
        if (fil.nextLine() == "# Leger (navn,kontrollid / 0 hvis vanlig lege)") {
            while (fil.nextLine() != "# Resepter (legemiddelNummer,legeNavn,pasientID,type,[reit])") {
                Lege nyLege;
                String linje = fil.nextLine();
                String[] biter = linje.split(",");
                if (biter.length != 2) throw new UlovligFormat(linje);

                String navn = biter[0];
                int kontroll = Integer.parseInt(biter[1]);

                if (kontroll == 0) {nyLege = new Lege(navn);}
                else {nyLege = new Spesialist(navn, kontroll);}
                
                legerListe.leggTil(nyLege);
            }
        } else {System.out.println("Filformatten for leger er ikke riktig."); System.exit(1);}

        //leser inn resept-objekter
        if (fil.nextLine() == "# Resepter (legemiddelNummer,legeNavn,pasientID,type,[reit])") {
            while (fil.hasNextLine()) {
                Lege utskrevendeLege = null;
                Pasient naavaerendePasient = null;
                Legemidler naavaerLegemid = null;
                Resept nyResept = null;

                String linje = fil.nextLine();
                String[] biter = linje.split(",");
                if (biter.length != 4 || biter.length != 5) throw new UlovligFormat(linje);  

                int legemiddelNummer = Integer.parseInt(biter[0]);
                String legeNavn = biter[1];
                int pasientID = Integer.parseInt(biter[2]);
                String type = biter[3];

                //henter ut legemiddel
                for(Legemidler legemiddel: legemiddelListe) {
                    if (legemiddel.hentId() == legemiddelNummer) naavaerLegemid = legemiddel;
                }
                //henter ut lege
                for(Lege lege: legerListe) {
                    if (lege.hentLege() == legeNavn) utskrevendeLege = lege;
                }
                //henter ut pasient
                for(Pasient pasient: pasientListe) {
                    if (pasient.hentPasientId() == pasientID) naavaerendePasient = pasient;
                }

                //behandler p-resepter
                if (biter.length == 4 && type == "p") {nyResept = utskrevendeLege.skrivPResept(naavaerLegemid, naavaerendePasient);}
                else {
                    int reit = Integer.parseInt(biter[4]);
                    if (type == "hvit") {
                        nyResept = utskrevendeLege.skrivHvitResept(naavaerLegemid, naavaerendePasient, reit);
                    } else if (type == "blaa") {
                        nyResept = utskrevendeLege.skrivBlaaResept(naavaerLegemid, naavaerendePasient, reit);
                    } else if (type == "militaer") {
                        nyResept = utskrevendeLege.skrivMilitaerResept(naavaerLegemid, naavaerendePasient, reit);
                    }
                }

                reseptListe.leggTil(nyResept);
            }
        } else {System.out.println("Filformatten for pasienter er ikke riktig."); System.exit(1);}
    }

    
    
    
    // dette her er helt samme metode som oppover, bare proever forskjellige maater aa lese inn input
    // spoiler: resultatet er det samme
    public void lesInnFraFil1(String filnavn) throws UlovligFormat {
        Scanner fil = null;

        try { //haandterer FileNotFoundException
            fil = new Scanner(new File(filnavn));
        } catch (Exception e) {
            System.out.println("Kan ikke lese filen " + filnavn + ". Avslutter programmet.");
            System.exit(1);
        }

        //sjekker at filen er ikke tom
        if (!fil.hasNextLine()) {System.out.println("Filen er tom!"); System.exit(1);}

        //leser inn pasient-objekter
        while(fil.hasNextLine()) {
            if (fil.next() == "#") {
                while (fil.nextLine() != "# Legemidler (navn,type,pris,virkestoff,[styrke])") {
                    String linje = fil.nextLine();
                    String[] biter = linje.split(",");
                    if (biter.length != 2) throw new UlovligFormat(linje);
                    Pasient nyPasient = new Pasient(biter[0], biter[1]);
                    pasientListe.leggTil(nyPasient);
                }
            } else {System.out.println("Filformatten for pasienter er ikke riktig."); System.exit(1);}
        }
    }
    public void hentOversikt() {

      System.out.println("Pasienter: \n");

      for (Pasient pasient : pasientListe) {
        System.out.println(pasient.toString());
      }

      System.out.println("Legemiddler: \n");

      for (Legemidler legemiddel : legemiddelListe) {
        System.out.println(legemiddel.toString());
      }

      System.out.println("Leger: \n");

      for (Lege lege : legerListe) {
        System.out.println(lege.toString());
      }

      System.out.println("Resepter: \n");

      for (Resept resept : reseptListe) {
        System.out.println(resept.toString());
      }

    }

}
