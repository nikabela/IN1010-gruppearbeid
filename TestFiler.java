public class TestFiler {
    public static void main(String[] args) throws Exception {
        Legesystem nyttLegesystem= new Legesystem();
        nyttLegesystem.lesInnFraFil("inndata.txt");
        nyttLegesystem.hentOversikt();
        nyttLegesystem.hentStatistikk();
    }
}
