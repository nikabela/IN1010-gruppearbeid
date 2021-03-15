import java.util.*;
import java.io.*;

class Legesystem {
    private LenketListe<Pasient> pasientListe = new LenketListe<Pasient>();
    private LenketListe<Legemidler> legemiddelListe = new LenketListe<Legemidler>();
    private SortertLenkeliste<Lege> legerListe = new SortertLenkeliste<Lege>();
    private LenketListe<Resept> reseptListe = new LenketListe<Resept>();

    // Deloppgave E1
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

                Pasient nyPasient = new Pasient(biter[0].trim(), biter[1].trim());
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
                double virkestoff = Double.parseDouble(biter[3].trim());
                int pris = 0;

                try { //sjekker om prisen er int
                    Float tallFloat = Float.parseFloat(biter[2].trim());
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
                    int styrke = Integer.parseInt(biter[4].trim());
                    Narkotisk nyLegemiddel = new Narkotisk(navn, pris, virkestoff, styrke);
                    legemiddelListe.leggTil(nyLegemiddel);
                }

                //om legemiddel er av typen 'vanedannende'
                if (type.equals("vanedannende")) {
                    int styrke = Integer.parseInt(biter[4].trim());
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

                String navn = biter[0].trim();
                int kontroll = Integer.parseInt(biter[1].trim());

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
                Spesialist spesLege = null;
                Pasient naavaerendePasient = null;
                Legemidler naavaerLegemid = null;
                Resept nyResept = null;

                String linje = fil.nextLine();
                String[] biter = linje.split(",");
                if (biter.length != 4 && biter.length != 5) throw new UlovligFormat(linje);

                int legemiddelNummer = Integer.parseInt(biter[0].trim());
                String legeNavn = biter[1].trim();
                int pasientID = Integer.parseInt(biter[2].trim());
                String type = biter[3].trim();

                //henter ut legemiddel
                for(Legemidler legemiddel: legemiddelListe) {
                    if (legemiddel.hentId() == legemiddelNummer) naavaerLegemid = legemiddel;
                }
                if (naavaerLegemid == null) {
                    System.out.println("Legemiddelet med id " + legemiddelNummer + " er ikke i systemet.");
                    System.exit(1);
                }
                //henter ut lege
                for(Lege lege: legerListe) {
                    if (lege.hentLege().equals(legeNavn)) {
                        utskrevendeLege = lege;
                        if (utskrevendeLege instanceof Spesialist) {spesLege = (Spesialist) utskrevendeLege;}
                        //else {Lege spesLege = utskrevendeLege;}
                    }
                }
                if (utskrevendeLege == null) {
                    System.out.println("Legen " + utskrevendeLege + " er ikke i systemet.");
                    System.exit(1);
                }
                //henter ut pasient
                for(Pasient pasient: pasientListe) {
                    if (pasient.hentPasientId() == pasientID) naavaerendePasient = pasient;
                }
                if (naavaerendePasient == null) {
                    System.out.println("Legemiddelet med id " + naavaerendePasient + " er ikke i systemet.");
                    System.exit(1);
                }

                    //behandler p-resepter
                if (type.equals("p")) {
                    nyResept = utskrevendeLege.skrivPResept(naavaerLegemid, naavaerendePasient);
                } else { //behandler andre typer resepter
                    int reit = Integer.parseInt(biter[4].trim());
                    if (type.equals("hvit")) {
                        if (utskrevendeLege instanceof Spesialist) {
                            nyResept = spesLege.skrivHvitResept(naavaerLegemid, naavaerendePasient, reit);
                        } else {
                            nyResept = utskrevendeLege.skrivHvitResept(naavaerLegemid, naavaerendePasient, reit);
                        }
                    } else if (type.equals("blaa")) {
                        if (utskrevendeLege instanceof Spesialist) {
                            nyResept = spesLege.skrivBlaaResept(naavaerLegemid, naavaerendePasient, reit);
                        } else {
                            nyResept = utskrevendeLege.skrivBlaaResept(naavaerLegemid, naavaerendePasient, reit);
                        }
                    } else if (type.equals("militaer")) {
                        if (utskrevendeLege instanceof Spesialist) {
                            nyResept = spesLege.skrivMilitaerResept(naavaerLegemid, naavaerendePasient, reit);
                        } else {
                            nyResept = utskrevendeLege.skrivMilitaerResept(naavaerLegemid, naavaerendePasient, reit);
                        }
                    } else {System.out.println("Resepttypen " + type + "finns ikke i systemet. Avslutter."); System.exit(1);}
                } reseptListe.leggTil(nyResept);
                naavaerendePasient.leggTilReseptIStabel(nyResept);
            }
        } else {System.out.println("Filformatten for pasienter er ikke riktig."); System.exit(1);}
    }


    // Deloppgave E3
    public void hentOversikt() {

        System.out.println("\nPasienter:");

        for (Pasient pasient : pasientListe) {
            System.out.println("    "+ pasient.toString());
        }

        System.out.println("\nLegemiddler:");

        for (Legemidler legemiddel : legemiddelListe) {
            System.out.println(legemiddel.toString() + "\n");
        }

        System.out.println("Leger:");

        for (Lege lege : legerListe) {
            System.out.println("    " + lege);
        }

        System.out.println("\nResepter:");

        for (Resept resept : reseptListe) {
            System.out.println(resept.toString() + "\n");
        }

    }


    // Deloppgave E4
    public void leggTilPasient(Scanner data){

      System.out.print("\nSkriv inn pasientens navn: ");
      String navn = data.nextLine().trim();
      System.out.print("Skriv inn pasientens personummer: ");
      String personNr = data.nextLine().trim();

      //sjekker om personNr har noeyaktig 11 siffer
      while (!personNr.matches("(^[0-9]{11})")) {
        System.out.println("Ugyldig input! Personnummer maa vaere integer og ha 11 siffer.");
        System.out.print("Skriv inn pasientens personummer: ");
        personNr = data.nextLine().trim();
      }

      for (Pasient pasient: pasientListe){
        if (pasient.hentNavn().trim().equals(navn) && pasient.hentFodselsnr().trim().equals(personNr)){
          System.out.println("Personen finnes allerede i systemet!");
          return;
        }
      }
      Pasient nyPasient = new Pasient(navn, personNr);
      pasientListe.leggTil(nyPasient);
      System.out.println("\nNy pasient er lagt til.");
    }

    public void leggTilLege(Scanner data) {
        System.out.print("\nSkriv inn navn til lege (kun siste navn, uten 'Dr.'): ");
        String navn = data.nextLine().trim();
        navn = "Dr. " + navn;
        System.out.print("Skriv inn kontroll-ID (0 hvis vanlig lege): ");
        String kontroll = data.nextLine().trim();

        //sjekker om kontrollID er int
        kontroll = erInt(data, kontroll, "Skriv inn kontroll-ID (0 hvis vanlig lege): ");

        int kontrollid = Integer.parseInt(kontroll);


        for (Lege lege: legerListe) { //sjekker om navnet og kontrollID finns i systemet
            if (lege.hentLege().trim().equals(navn)) {
                System.out.println("\nNavnet paa legen er allerede i systemet!");
                return;
            }
            if (kontrollid == 0) continue;
            else {
                if(lege instanceof Spesialist) {
                    Spesialist spes = (Spesialist) lege;
                    if (spes.hentKontrollId() == kontrollid) {
                        System.out.println("\nKontroll-ID er allerede i systemet!");
                        return;
                    }
                }
            }
        }

        Lege nyLege;
        if (kontrollid == 0) {nyLege = new Lege(navn);}
        else {nyLege = new Spesialist(navn, kontrollid);}
        legerListe.leggTil(nyLege);
        System.out.println("\nNy lege er lagt til.");
    }

    public void leggTilLegemiddel(Scanner data) {
        System.out.print("\nSkriv inn navn paa legemiddel: ");
        String navn = data.nextLine().trim();                     // data

        System.out.print("Skriv inn type paa legemiddel: ");
        String type = data.nextLine().trim();                         // data

        //sjekker om type er gyldig
        while (!type.matches("(V|v)anlig|(N|n)arkotisk|(V|v)anedannende")) {
            System.out.println("Ugyldig input! Tast inn eksisterende type.");
            System.out.print("Skriv inn type paa legemiddel: ");
            type = data.nextLine().trim();
        }

        System.out.print("Skriv inn pris paa legemiddel: ");
        String prisString = data.nextLine().trim();

        //sjekker om prisen er gyldig
        prisString = erInt(data, prisString, "Skriv inn pris paa legemiddel: ");

        int pris = Integer.parseInt(prisString);                   // data

        System.out.print("Skriv inn virkestoff til legemiddel: ");
        String mg = data.nextLine().trim();

        //sjekker om virkestoff er gyldig
        mg = erDouble(data, mg, "Skriv inn virkestoff til legemiddel: ");

        double stoff = Double.parseDouble(mg);                    // data

        //data.nextLine(); // MAA IKKE FJERNES - tom input linje knyttet til rar Scanner oppfoersel

        int styrke = 0;
        if (type.matches("narkotisk|vanedannende")) {
            System.out.print("Skriv inn styrke til legemiddel: ");
            String strk = data.nextLine().trim();
            strk = erInt(data, strk, "Skriv inn styrke til legemiddel: ");
            styrke = Integer.parseInt(strk);                      // data

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

        System.out.print("\nSkriv inn navn paa utskrivende lege (kun siste navn, uten 'Dr.'): ");
        String legeNavn = "Dr. " + data.nextLine().trim();

        if (!finnesLege(legeNavn)){
          System.out.println("\nLege er ikke registrert.");
          return;
        }

        System.out.print("Skriv legemiddel-id: ");
        String lgmdlID = data.nextLine().trim();

        lgmdlID = erInt(data, lgmdlID, "Skriv legemiddel-id: ");
        int legemiddelId = Integer.parseInt(lgmdlID);

        if(!finnesLegemiddel(legemiddelId)){
          System.out.println("\nheiii");
          System.out.println("Legemiddelet finnes ikke.");
          return;
        }

        System.out.print("Skriv pasientID: ");
        String pasID = data.nextLine().trim();
        pasID = erInt(data, pasID, "Skriv pasientID: ");
        int pasientId = Integer.parseInt(pasID);

        if (!finnesPasient(pasientId)){
          System.out.println("\nfuuuu");
          System.out.println("Pasienten er ikke registrert.");
          return;
        }

        System.out.print("Hvilken type skal resepten være (p-resept, militaer eller blaa): ");
        String reseptType = data.nextLine().trim();
        while (!reseptType.matches("p-resept|militaer|blaa")) {
            System.out.println("Ugyldig input! Tast inn eksisterende type.");
            System.out.print("Hvilken type skal resepten være (p-resept, militaer eller blaa): ");
            reseptType = data.nextLine().trim();
        }

        int reit = 3;
        if (!reseptType.equals("p-resept")) {
            System.out.print("Hvor mange reit skal resepten ha: ");
            String rt = data.nextLine().trim();
            rt = erInt(data, rt, "Hvor mange reit skal resepten ha: ");
            reit = Integer.parseInt(rt);
        }

        Resept resept = null;

        Legemidler legemiddel = null;
        for (Legemidler l: legemiddelListe){
          if (l.hentId() == legemiddelId){
            legemiddel = l;
          }
        }

        Pasient pasient = null;
        for (Pasient p : pasientListe){
          if (p.hentPasientId() == pasientId){
            pasient = p;
          }
        }

        Lege lege = null;
        for (Lege l : legerListe) {
            if (l.hentLege().equals(legeNavn)) lege = l;
        }

        if((legemiddel instanceof Narkotisk) && !(lege instanceof Spesialist) ){
          System.out.println("\nwtf");
          System.out.println("Denne legen er ikke autorisert til aa skrive ut legemiddelet.");
          return;
        }

        if (reseptType.equals("blaa")){
          resept = new BlaaResept(legemiddel, lege, pasient, reit);
        } else if(reseptType.equals("militaer")){
          resept = new Militaer(legemiddel, lege, pasient, reit);
        } else if(reseptType.equals("p-resept")){
          resept = new PResept(legemiddel, lege, pasient);
        } else {
            System.out.println("Resepttypen finns ikke i systemet. Avslutter.");
        }

        pasient.leggTilReseptIStabel(resept);
        reseptListe.leggTil(resept);

        //dette skulle vaere den siste linjen i hele metoden
        System.out.println("\nNy resept er lagt til.");
    }


     // Det var bestemt for aa bruke egne sjekk-metoder i stedet for hasNextInt() og hasNextDouble() i Scanner,
    // fordi next(), nextInt() og nextDouble() kombinerer ikke godt med nextLine.
    private String erInt(Scanner data, String input, String outputLinje) {
        while (!input.matches("([0-9]+)")) {
            System.out.println("Ugyldig input! Tast inn integer.");
            System.out.print(outputLinje);
            input = data.nextLine().trim();
        }
        return input;
    }

    private String erDouble(Scanner data, String input, String outputLinje) {
        while (!input.matches("([0-9]+\\.[0-9]+)|([0-9]+)")) {
            System.out.println("Ugyldig input! Tast inn double.");
            System.out.print(outputLinje);
            input = data.nextLine().trim();
        }
        return input;
    }


    private boolean finnesLegemiddel(int id) {
        for (Legemidler legemiddel : legemiddelListe) {
            if (legemiddel.hentId() == id)
                return true;
        }
        return false;
    }

    private boolean finnesPasient(int id) {
        for (Pasient pasient : pasientListe) {
            if (pasient.hentPasientId() == id)
                return true;
        }
        return false;
    }

    private boolean finnesLege(String legeNavn) {
        for (Lege lege : legerListe) {
            if (lege.hentLege().equals(legeNavn))
                return true;
        }
        return false;
    }

    // Deloppgave E5
  public void brukResept(Scanner inputHeltall) {
     System.out.println();
     System.out.println("Hvilken pasient vil du se resepter for?");
     // for-løkke som printer ut alle pasienter med info
     for (Pasient pasient : pasientListe) {
       System.out.println("    "+ pasient.toString());
     }
     // ta input (integer) for indeks som er pasientens id;
     System.out.println();
     System.out.print("Vennligst oppgi pasientens id for å velge pasient (et gyldig heltall fra listen): ");
     String pasID = inputHeltall.nextLine().trim();

     // tester at inputen er faktisk int (se erInt oppe)
     pasID = erInt(inputHeltall, pasID, "Vennligst oppgi pasientens id for å velge pasient (et gyldig heltall fra listen): ");
     int pasientID = Integer.parseInt(pasID);
     System.out.println();
     System.out.println("Valgt pasient: ");

     // presenterer på nytt denne pasientens info
     Pasient pasient = pasientListe.hent(pasientID);
     System.out.println("    Navn: " + pasient.hentNavn() + " " + "FoedselsNr: " + pasient.hentFodselsnr());
     System.out.println();
     System.out.println("Hvilken resept vil du bruke?");

     // presenter resepter på pasienten
     for (Resept resept : pasient.hentResepter()) {
       System.out.println("    " + resept.hentReseptId() + ":" + " " + resept.hentLegemiddel().hentNavn() + " (" + resept.hentReit() + " reit)");

     }

     // ta input (integer) for indeks for å velge legemiddel
     System.out.println();
     System.out.print("Oppgi et gyldig heltall som vist fra reseptlisten: ");
     String resID = inputHeltall.nextLine().trim();

     // tester at inputen er faktisk int (se erInt oppe)
     resID = erInt(inputHeltall, resID, "Oppgi et gyldig heltall som vist fra pasientlisten: ");
     int reseptID = Integer.parseInt(resID);
     System.out.println("Valgt resept med resept ID: " + reseptID);

     // henter valgt resept
     boolean reseptIDFinnes = false;
     for (Resept resept : pasient.hentResepter()) {
         int reseptIDForBruk = resept.hentReseptId(); // finner reseptID
         // Dersom oppgitt id er lik gyldig, bruk resept
         if (reseptIDForBruk == reseptID) {
             reseptIDFinnes = true;
             // Forsøker å bruke resept
             if (resept.bruk()) {
               System.out.println("Brukte resept paa " + resept.hentLegemiddel().hentNavn() + "." + " " + "Antall gjenvaerende reit: " + resept.hentReit());
               System.out.println("\n");
             } else {
               System.out.println("Kunne ikke bruke resept paa " + resept.hentLegemiddel().hentNavn() + " " + "(ingen gjenvaerende reit)." );
               System.out.println("\n");
             }
         }
      }
      // Gir melding til bruker dersom reseptID ikke eksisterer
       if (reseptIDFinnes == false) {
        System.out.println("ReseptID (heltallet) du oppga er ikke gyldig!");
        System.out.println();
        return;
      }
   }

    // Deloppgave E6
    public void hentStatistikk() {

        int antallNarkotiske = 0;
        int antallVanedannende = 0;

        System.out.println("Generell oversikt:\n  Antall resepter: " + reseptListe.stoerrelse());

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

        System.out.println("  Antall narkotiske resepter: " + antallNarkotiske);
        System.out.println("  Antall vanedannende resepter: " + antallVanedannende);

        // System.out.println("Statistikk om mulig misbruk av narkotika.");
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


          if (antallLegeNark > 0) {
            System.out.println("  " + legeMedNark.hentLege() + " har skrevet ut " + antallLegeNark
            + " narkotiske legemidler.");
          }
        }

        System.out.println("\nPasienter med gyldig resept paa narkotiske legemidler: ");

        for (int k = 0; k < pasientListe.stoerrelse(); k++) {

          Pasient pasMedNark = pasientListe.hent(k);
          int antallPasMedNark = 0;
          Liste<Resept> tempPasListe = pasMedNark.hentResepter();

          for (int l = 0; l < tempPasListe.stoerrelse(); l++) {
            if (tempPasListe.hent(l).hentLegemiddel() instanceof Narkotisk) {
              antallPasMedNark++;
              //pasMedNark = tempPasListe.hent(l).hentPasient(); // ??
            }
          }

          if (antallPasMedNark > 0) {
            System.out.println("  Pasient " + pasMedNark.hentNavn() + " har faatt utskrevet "
            + antallPasMedNark + " narkotiske resepter.");
          }
        }

        System.out.println("\n");

    }


    // Deloppgave E7
    public void skrivTilFil() throws IOException{
        System.out.println("\n\nSkriver info til fil...");

        File file = new File("file.txt");
        PrintWriter printWriter = new PrintWriter(file);

        int i = 0;
        printWriter.println("# Pasienter (navn,fnr)");
        while (i < pasientListe.stoerrelse()) {
            Pasient pasient = pasientListe.hent(i);
            printWriter.println(pasient.hentNavn() + "," + pasient.hentFodselsnr());
            i++;
            printWriter.flush();

        }

        int j = 0;

        printWriter.println("# Legemidler (navn,type,pris,virkestoff,[styrke])");
        while (j < legemiddelListe.stoerrelse()) {
            Legemidler legemiddel = legemiddelListe.hent(j);
            if (legemiddel instanceof Vanlig) {
                printWriter.println(legemiddel.hentNavn() + "," + "vanlig" + "," + legemiddel.hentPris() + "," + legemiddel.hentVirkestoff());
            } else if (legemiddel instanceof Narkotisk) { 						//maatte kaste legemiddel til subklasse for aa faa tilgang til metodene der
                printWriter.println(legemiddel.hentNavn() + "," + "narkotisk" + "," + legemiddel.hentPris() + ","
                + legemiddel.hentVirkestoff() + "," + ((Narkotisk)legemiddel).hentNarkotiskStyrke());
            } else if (legemiddel instanceof Vanedannende) { 					//maatte kaste legemiddel til subklasse for aa faa tilgang til metodene der
                printWriter.println(legemiddel.hentNavn() + "," + "vanedannende" + "," + legemiddel.hentPris() + ","
                + legemiddel.hentVirkestoff() + "," + ((Vanedannende)legemiddel).hentVanedannendeStyrke());
            }
          j++;
          printWriter.flush();
        }

        int k = 0;
        printWriter.println("# Leger (navn, [kontroll- id])");
        while (k < legerListe.stoerrelse()){
          Lege lege = legerListe.hent(k);
          if (lege instanceof Spesialist){
            printWriter.println(lege.hentLege() + "," + ((Spesialist)lege).hentKontrollId());
          }else {
            printWriter.println(lege.hentLege());
          }

          k++;
          printWriter.flush();
        }

        int l = 0;
        printWriter.println("# Resepter (type, legemiddel, resept-id, utskrivende lege, reit, pasient)");
        while (l < reseptListe.stoerrelse()){

          Resept resept = reseptListe.hent(l);
          if (resept instanceof HvitResept){
            if (resept instanceof PResept){
              printWriter.println( "Prevensjon" + "\n" + resept);
            } else if (resept instanceof Militaer){
              printWriter.println("Militaer" + "\n" + resept);
            }
          }
          if (resept instanceof BlaaResept){
            printWriter.println(resept.farge() + "\n" + resept);
          }
          l++;
          printWriter.flush();
        }
        System.out.println("Har skrevet data til fil.\n\n");
        printWriter.close();
    }
}
