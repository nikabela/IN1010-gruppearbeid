import java.util.*;
import java.io.*;

class Legesystem {
    private LenketListe<Pasient> pasientListe = new LenketListe<Pasient>();
    private LenketListe<Legemidler> legemiddelListe = new LenketListe<Legemidler>();
    private LenketListe<Lege> legerListe = new LenketListe<Lege>();
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

        Lege[] leger = new Lege[legerListe.stoerrelse()];
        for (int i =0; i < leger.length; i++) {
            leger[i] = legerListe.hent(i);
        }
        Arrays.sort(leger);
        for (int i =0; i < leger.length; i++) {
            System.out.println("    " + leger[i]);
        }


        System.out.println("\n\nResepter:");

        for (Resept resept : reseptListe) {
            System.out.println(resept.toString() + "\n");
        }

    }

    public void leggTilPasient(){
    // System.out.println("hohho");
    //
    // int inp == input;
    // if (inp.equals(0)){
    //   for ()
    //
    // 
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
        String navn = data.next().trim();
    }

    public void leggTilResept() {
        //code
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
    }
}
