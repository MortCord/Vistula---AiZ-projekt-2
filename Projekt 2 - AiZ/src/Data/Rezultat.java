package Data;

public class Rezultat {

    private int[] odleglosci;
    private int[] rodzic;
    private long czas;

    public Rezultat(int[] odleglosci, int[] rodzic, long czas){
        this.odleglosci = odleglosci;
        this.rodzic = rodzic;
        this.czas = czas;
    }

    public int[] getOdleglosci(){
        return odleglosci;
    }

    public int[] getRodzic(){
        return rodzic;
    }

    public long getCzas(){
        return czas;
    }

}
