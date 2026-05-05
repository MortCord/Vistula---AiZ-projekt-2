package Data;

import java.io.FileWriter;
import java.io.IOException;

public class EksportWynikow {

    private static final String nazwaPliku = "wyniki.txt";

    public static void zapiszNaglowek(){
        try(FileWriter writer = new FileWriter(nazwaPliku)){
            writer.write(String.format("%-15s %-15s %-15s %-15s\n", "Alg", "Czas(ns)", "Wierzcholki", "Krawedzie"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void zapiszWynik(String alg, long czas, int w, int k){
        try(FileWriter writer = new FileWriter(nazwaPliku, true)){
            writer.write(String.format("%-15s %-15d %-15d %-15d\n", alg, czas, w, k));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void zresetuj(){
        zapiszNaglowek();
    }

}
