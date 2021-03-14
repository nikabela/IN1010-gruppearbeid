import java.util.*;
import java.io.*;

class Legesystem {
    private LenketListe<Pasient> pasientListe = new LenketListe<Pasient>();
    private LenketListe<Legemidler> legemiddelListe = new LenketListe<Legemidler>();
    private SortertLenkeliste<Lege> legerListe = new SortertLenkeliste<Lege>();
    private LenketListe<Resept> reseptListe = new LenketListe<Resept>();


    public void lesInnFraFil(String filnavn) throws UlovligFormat, UlovligUtskrift {
        Scanner fil = null;

        try { //haandterer FileNotFoundException
            fil = new Scanner(new File(filnavn));
        } catch (FileNotFoundException e) {
            System.out.println("Kan ikke lese filen " + filnavn + ". Avslutter programmet.");
            System.exit(1);
        }

        //sjekker at filen er ikke tom
        if (!fil.hasNextLine()) {System.out.println("Filen er tom!"); System.exit(1);}

        //leser inn pasient-objekter
        String currentHeader = "Pasienter";
        if (fil.nextLine().startsWith("# Pasienter")) {
            String linje = fil.nextLine();
            while (!linje.startsWith("# Legemidler")) {
                String[] biter = linje.split(",");
                if (biter.length != 2) throw new UlovligFormat(linje);

                Pasient nyPasient = new Pasient(biter[0], biter[1]);
                pasientListe.leggTil(nyPasient);
                linje = fil.nextLine();
            } currentHeader = "Legemidler"; //bytte header
        } else {System.out.println("Filformatten for pasienter er ikke riktig."); System.exit(1);}

        //leser inn legemiddel-objekter
        if (currentHeader == "Legemidler") {
            String linje = fil.nextLine();
            while (!linje.startsWith("# Leger")) {
                String[] biter = linje.split(",");
                if (biter.length != 4 && biter.length != 5) throw new UlovligFormat(linje);
                String navn = biter[0];
                //for komplekse navn i myeInndata.txt
                if (navn.contains("/")) navn.replace("/", ",");

                String type = biter[1].trim();
                double virkestoff = Double.parseDouble(biter[3]);
                int pris = 0;

                try { //sjekker om prisen er int
                    Float tallFloat = Float.parseFloat(biter[2]);
                    pris = Math.round(tallFloat);
                } catch (NumberFormatException e) {
                    System.out.println("Prisen " + biter[2] + " er ikke en gyldig pris. Elementet skal ikke lagres.");
                    System.exit(1);
                }

                //om legemiddel er av typen 'vanlig'
                if (type.equals("vanlig")) {
                    Vanlig nyLegemiddel = new Vanlig(navn, pris, virkestoff);
                    legemiddelListe.leggTil(nyLegemiddel);
                }

                //om legemiddel er av typen 'narkotisk'
                if (type.equals("narkotisk")) {
                    int styrke = Integer.parseInt(biter[4]);
                    Narkotisk nyLegemiddel = new Narkotisk(navn, pris, virkestoff, styrke);
                    legemiddelListe.leggTil(nyLegemiddel);
                }

                //om legemiddel er av typen 'vanedannende'
                if (type.equals("vanedannende")) {
                    int styrke = Integer.parseInt(biter[4]);
                    Vanedannende nyLegemiddel = new Vanedannende(navn, pris, virkestoff, styrke);
                    legemiddelListe.leggTil(nyLegemiddel);
                }
                linje = fil.nextLine(); //oppdaterer linje
            } currentHeader = "Leger"; //bytte header
        } else {System.out.println("Filformatten for legemidler er ikke riktig."); System.exit(1);}

        //leser inn lege-objekter
        if (currentHeader == "Leger") {
            String linje = fil.nextLine();
            while (!linje.startsWith("# Resepter")) {
                Lege nyLege;
                String[] biter = linje.split(",");
                if (biter.length != 2) throw new UlovligFormat(linje);

                String navn = biter[0];
                int kontroll = Integer.parseInt(biter[1]);

                if (kontroll == 0) {nyLege = new Lege(navn);}
                else {nyLege = new Spesialist(navn, kontroll);}

                legerListe.leggTil(nyLege);

                linje = fil.nextLine(); //oppdaterer linje
            } currentHeader = "Resepter"; //bytte header
        } else {System.out.println("Filformatten for leger er ikke riktig."); System.exit(1);}

        //leser inn resept-objekter
        if (currentHeader == "Resepter") {
            while (fil.hasNextLine()) {
                Lege utskrevendeLege = null;
                Pasient naavaerendePasient = null;
                Legemidler naavaerLegemid = null;
                Resept nyResept = null;

                String linje = fil.nextLine();
                String[] biter = linje.split(",");
                if (biter.length != 4 && biter.length != 5) throw new UlovligFormat(linje);

                int legemiddelNummer = Integer.parseInt(biter[0]);
                String legeNavn = biter[1].trim();
                int pasientID = Integer.parseInt(biter[2]);
                String type = biter[3].trim();

                //henter ut legemiddel
                for(Legemidler legemiddel: legemiddelListe) {
                    if (legemiddel.hentId() == legemiddelNummer) naavaerLegemid = legemiddel;
                }
                //henter ut lege
                for(Lege lege: legerListe) {
                    if (lege.hentLege().equals(legeNavn)) utskrevendeLege = lege;
                }
                //henter ut pasient
                for(Pasient pasient: pasientListe) {
                    if (pasient.hentPasientId() == pasientID) naavaerendePasient = pasient;
                }

                       //behandler p-resepter
                if (type.equals("p")) {
                    nyResept = utskrevendeLege.skrivPResept(naavaerLegemid, naavaerendePasient);
                    reseptListe.leggTil(nyResept);
                }
                else { //behandler andre typer resepter
                    if (type.equals("hvit")) {
                        int reit = Integer.parseInt(biter[4]);
                        nyResept = utskrevendeLege.skrivHvitResept(naavaerLegemid, naavaerendePasient, reit);
                        reseptListe.leggTil(nyResept);
                    } else if (type.equals("blaa")) {
                        int reit = Integer.parseInt(biter[4]);
                        nyResept = utskrevendeLege.skrivBlaaResept(naavaerLegemid, naavaerendePasient, reit);
                        reseptListe.leggTil(nyResept);
                    } else if (type.equals("militaer")) {
                        int reit = Integer.parseInt(biter[4]);
                        nyResept = utskrevendeLege.skrivMilitaerResept(naavaerLegemid, naavaerendePasient, reit);
                        reseptListe.leggTil(nyResept);
                    }
                }
            }
        } else {System.out.println("Filformatten for pasienter er ikke riktig."); System.exit(1);}
    }



    public void hentOversikt() {

        System.out.println("\nPasienter:");

        for (Pasient pasient : pasientListe) {
            System.out.println("    "+ pasient.toString());
        }

        System.out.println("\n\nLegemiddler:");

        for (Legemidler legemiddel : legemiddelListe) {
            System.out.println(legemiddel.toString() + "\n");
        }

        System.out.println("\nLeger:");

        for (Lege lege : legerListe) {
            System.out.println("    " + lege);
        }

        System.out.println("\n\nResepter:");

        for (Resept resept : reseptListe) {
            System.out.println(resept.toString() + "\n");
        }

    }

    public void leggTilPasient(Scanner data){
    // System.out.println("hohho");
    //
    // int inp == input;
    // if (inp.equals(0)){
    //   for ()
    //
    // 

    //dette skulle vaere den siste linjen i hele metoden
    System.out.println("\nNy pasient er lagt til.");
    }

    public void leggTilLege(Scanner data) {
        System.out.print("Skriv inn navn til lege (kun siste navn, uten 'Dr.'): ");
        String navn = data.next().trim();
        navn = "Dr. " + navn;
        System.out.print("Skriv inn kontroll-ID (0 hvis vanlig lege): ");
        int kontrollid = data.nextInt();

        for (Lege lege: legerListe) { //sjekker om navnet og kontrollID finns i systemet
            if (lege.hentLege().trim().equals(navn)) {
                System.out.println("Navnet paa legen er allerede i systemet!");
                return;
            }
            if (kontrollid == 0) continue;
            else {
                if(lege instanceof Spesialist) {
                    Spesialist spes = (Spesialist) lege;
                    if (spes.hentKontrollId() == kontrollid) {
                        System.out.println("kontroll-ID er allerede i systemet!");
                        return;
                    }
                }
            }
        }

        Lege nyLege;
        if (kontrollid == 0) {nyLege = new Lege(navn);}
        else {nyLege = new Spesialist(navn, kontrollid);}
        legerListe.leggTil(nyLege);
        System.out.println("Ny lege er lagt til.");
    }

    public void leggTilLegemiddel(Scanner data) {
        System.out.print("Skriv inn navn til legemiddel: ");
        String navn = data.nextLine().trim();                     // data

        System.out.print("Skriv inn type til legemiddel: ");
        String type = data.next().trim();                         // data

        //sjekker om type er gyldig
        while (!type.matches("vanlig|narkotisk|vanedannende")) {
            System.out.println("Ugyldig input! Tast inn eksisterende type.");
            System.out.print("Skriv inn type til legemiddel: ");
            type = data.next().trim();
        }

        System.out.print("Skriv inn pris til legemiddel: ");

        //sjekker om prisen er gyldig
        while (!data.hasNextInt()) {
            System.out.println("Ugyldig input! Tast inn integer.");
            System.out.print("Skriv inn pris til legemiddel: ");
            data.next().trim();        
        }
        int pris = data.nextInt();                                // data

        System.out.print("Skriv inn virkestoff til legemiddel: ");
        String mg = data.next().trim();

        //sjekker om virkestoff er gyldig
        while (!mg.matches("([0-9]+\\.[0-9]+)|([0-9]+)")) {
            System.out.println("Ugyldig input! Tast inn double.");
            System.out.print("Skriv inn virkestoff til legemiddel: ");
            mg = data.next().trim();        
        }
        double stoff = Double.parseDouble(mg);                    // data

        data.nextLine(); // MAA IKKE FJERNES - tom input linje knyttet til rar Scanner oppfoersel

        int styrke = 0;
        if (type.matches("narkotisk|vanedannende")) {
            System.out.print("Skriv inn styrke til legemiddel: ");
            styrke = Integer.parseInt(data.nextLine().trim());  // data

        }

        for (Legemidler legemid: legemiddelListe) { //sjekker om legemiddelen finns i systemet
            if (legemid instanceof Vanlig) {
                if (legemid.hentNavn().equals(navn) && 
                    (legemid.hentPris() == pris) && (legemid.hentVirkestoff() == stoff)) {
                    System.out.println("\nLegemiddel er allerede i systemet!");
                    return;
                }
            } else if (legemid instanceof Narkotisk) {
                Narkotisk nark = (Narkotisk) legemid;
                if (nark.hentNavn().equals(navn) && (nark.hentPris() == pris) && 
                    (nark.hentVirkestoff() == stoff) && (nark.hentNarkotiskStyrke() == styrke)) {
                    System.out.println("\nLegemiddel er allerede i systemet!");
                    return;
                }
            } else if (legemid instanceof Vanedannende) {
                Vanedannende vaned = (Vanedannende) legemid;
                if (vaned.hentNavn().equals(navn) && (vaned.hentPris() == pris) && 
                    (vaned.hentVirkestoff() == stoff) && (vaned.hentVanedannendeStyrke() == styrke)) {
                    System.out.println("\nLegemiddel er allerede i systemet!");
                    return;
                }
            }
        }

        Legemidler nyLegemiddel = null;
        
        if (type.equals("vanlig")) { //om legemiddel er av typen 'vanlig'
            nyLegemiddel = new Vanlig(navn, pris, stoff);
        } else if (type.equals("narkotisk")) { //om legemiddel er av typen 'narkotisk'
            nyLegemiddel = new Narkotisk(navn, pris, stoff, styrke);
        } else if (type.equals("vanedannende")) { //om legemiddel er av typen 'vanedannende'
            nyLegemiddel = new Vanedannende(navn, pris, stoff, styrke);
        } else {System.out.println("Kunne ikke opprette legemiddel-objekt."); return;}

        legemiddelListe.leggTil(nyLegemiddel);


        System.out.println("\nNy legemiddel er lagt til.");

    }

    public void leggTilResept(Scanner data) {
        //code

        //dette skulle vaere den siste linjen i hele metoden
        System.out.println("\nNy resept er lagt til.");
    }


    //Deloppgave E6:
    public void hentStatistikk() {

        int antallNarkotiske = 0;
        int antallVanedannende = 0;

        System.out.println("Reseptliste size: " + reseptListe.stoerrelse() + "\n");

        try {
            for (int i = 0; i < reseptListe.stoerrelse(); i++) {
                if (reseptListe.hent(i) != null && reseptListe.hent(i).hentLegemiddel() instanceof Narkotisk) {
                    antallNarkotiske++;
                } else if (reseptListe.hent(i) != null && reseptListe.hent(i).hentLegemiddel() instanceof Vanedannende) {
                    antallVanedannende++;
                }
            }
        } catch(NullPointerException e) {
            System.out.println("Feil i antallnarkotiske");
        }

        System.out.println("Antall Narkotiske Resepter: " + antallNarkotiske + "\n");
        System.out.println("Antall Vanedannende Resepter: " + antallVanedannende + "\n");
        
  //    System.out.println("Statistikk om mulig misbruk av narkotika.");
	System.out.println("\nLeger som har skrevet ut narkotiske legemidler: ");

        for (int i = 0; i < legerListe.stoerrelse(); i++) {

          Lege legeMedNark = legerListe.hent(i);
          int antallLegeNark = 0;
          Liste<Resept> tempLegeListe = legeMedNark.hentUtskrevedeResepter();

          for (int j = 0; j < tempLegeListe.stoerrelse(); j++) {
            if (tempLegeListe.hent(j).hentLegemiddel() instanceof Narkotisk) {
              antallLegeNark++;
            }
          }

          if (antallLegeNark > 0) {       //NARKOTISKE RESEPTEN FINNES IKKE, SÅ HER SKRIVES UT INGEN LEGER!!!
            System.out.println("Lege " + legeMedNark.hentLege() + " har skrevet ut " + antallLegeNark
            + " narkotiske legemidler");
          }
        }

        System.out.println("\nPasienter med gyldig resept paa narkotiske legemidler: ");

        for (int i = 0; i < pasientListe.stoerrelse(); i++) {
          Pasient pasMedNark = pasientListe.hent(i);
          int antallPasMedNark = 0;
          Liste<Resept> tempPasListe = pasMedNark.hentResepter();

          for (int j = 0; j < tempPasListe.stoerrelse(); j++) {
            if (tempPasListe.hent(j).hentLegemiddel() instanceof Narkotisk) {
              antallPasMedNark++;
            }
          }
          if (antallPasMedNark > 0) {        //NARKOTISKE RESEPTEN FINNES IKKE, SÅ HER SKRIVES UT INGEN PASIENTER MED RESEPTER!!!
            System.out.println("Pasient " + pasMedNark.hentNavn() + " har faatt utskrevet "
            + antallPasMedNark + " narkotiske resepter");
          }
        }
        System.out.println("\n");
    }
	
    public void skrivTilFil() {

        FileWriter fileWriter;
        PrintWriter printWriter;

        try {
            fileWriter = new FileWriter("skrivtilfil.txt");
            printWriter = new PrintWriter(fileWriter);

            int i = 0;

            printWriter.println("# Pasienter (navn,fnr)");
            while (i < pasientListe.stoerrelse()) {
                Pasient pasient = pasientListe.hent(i);
                printWriter.println(pasient.hentNavn() + "," + pasient.hentFodselsnr());
                i++;
            } //slutt av while løkken

            int j = 0;

            printWriter.println("# Legemidler (navn,type,pris,virkestoff,[styrke])");
            while (j < legemiddelListe.stoerrelse()) {
                Legemidler legemiddel = legemiddelListe.hent(j);
                if (legemiddel instanceof Vanlig) {
                printWriter.println(legemiddel.hentNavn() + "," + "vanlig" + "," + legemiddel.hentPris() + "," + legemiddel.hentVirkestoff());
                } else if (legemiddel instanceof Narkotisk) { 						//måtte kaste legemiddel til subklasse for å få tilgang til metodene der
                printWriter.println(legemiddel.hentNavn() + "," + "narkotisk" + legemiddel.hentPris() + ","
                + legemiddel.hentVirkestoff() + "," + ((Narkotisk)legemiddel).hentNarkotiskStyrke()); 
                } else if (legemiddel instanceof Vanedannende) { 					//måtte kaste legemiddel til subklasse for å få tilgang til metodene der
                printWriter.println(legemiddel.hentNavn() + "," + "vanedannende" + "," + legemiddel.hentPris() + ","
                + legemiddel.hentVirkestoff() + "," + ((Vanedannende)legemiddel).hentVanedannendeStyrke());
                }
                j++;
            } //slutt av while løkken
        } catch (IOException e) {
            System.out.println(e);
        }  //slutt av catch


    } //slutt av skrivTilFil metoden
	
} //slutt av classen Legesystem
