public class Hovedprogram {
    public static void main(String[] args) throws UlovligFormat, UlovligUtskrift {
        Legesystem legesystem = new Legesystem();
        legesystem.lesInnFraFil("inndata.txt");
    }
}
