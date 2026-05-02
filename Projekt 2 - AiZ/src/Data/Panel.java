package Data;

import Algorytmy.BellmanFord;
import Algorytmy.Dijkstra;
import Algorytmy.FloydWarshall;
import Grafy.Graf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

public class Panel extends JFrame {

    private JComboBox<String> panelAlgrotymu;
    private JTextField poleWierzcholkow;
    private JTextField poleKrawedzi;
    private JTextField poleStartu;
    private JTable tablicaRezultatow;
    private PanelGrafu panelGrafu;
    private Wykres wykres;
    JButton btnWierzcholki = new JButton("Czas vs V");
    JButton btnKrawedzie = new JButton("Czas vs E");

    private int[] vSizes = {10, 50, 100, 200, 500};
    private int[] eSizes = {20, 100, 200, 400, 800};

    public Panel(){
        setTitle("Najkrótsze ścieżki w grafie");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        stworzGornaPanel();
        stworzCentralnaPanel();

        setVisible(true);

    }

    private void stworzGornaPanel(){

        JPanel panel = new JPanel();

        panelAlgrotymu = new JComboBox<>(new String[]{"Dijkstra", "BellmanFord", "FloydWarshall", "Wszystkie algorytmy"});

        poleWierzcholkow = new JTextField("10", 5);
        poleKrawedzi = new JTextField("20", 5);
        poleStartu = new JTextField("0", 5);

        JButton startBtn = new JButton("Start");

        startBtn.addActionListener(e -> uruchomAlgorytm());
        btnWierzcholki.addActionListener(e -> testWierzcholki());
        btnKrawedzie.addActionListener(e -> testKrawedzie());

        panel.add(new JLabel("Algorytm:"));
        panel.add(panelAlgrotymu);

        panel.add(new JLabel("Wierzchołki:"));
        panel.add(poleWierzcholkow);

        panel.add(new JLabel("Krawędzie:"));
        panel.add(poleKrawedzi);

        panel.add(startBtn);

        panel.add(btnWierzcholki);
        panel.add(btnKrawedzie);

        add(panel, BorderLayout.NORTH);

    }

    private void stworzCentralnaPanel(){

        panelGrafu = new PanelGrafu();
        tablicaRezultatow = new JTable();
        wykres = new Wykres();

        JSplitPane prawyPodzial = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(tablicaRezultatow), wykres);

        JSplitPane glownyPodzial = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelGrafu, prawyPodzial);

        add(glownyPodzial, BorderLayout.CENTER);

    }

    private void uruchomAlgorytm(){

        int wierzcholki = Integer.parseInt(poleWierzcholkow.getText());
        int krawedzie = Integer.parseInt(poleKrawedzi.getText());
        int start = Integer.parseInt(poleStartu.getText());

        Graf graf = GenerowanieGrafu.generujGraf(wierzcholki, krawedzie);

        Rezultat rezultat = null;

        String wybrany = (String) panelAlgrotymu.getSelectedItem();

        if(wybrany.equals("Dijkstra")){
            rezultat = Dijkstra.start(graf, start);

            Map<String, Long> map = new LinkedHashMap<>();
            map.put("Dijkstra", rezultat.getCzas());

            wykres.setData(map);
            wykres.revalidate();
            wykres.repaint();

            panelGrafu.setGraf(graf);

            pokazRezultaty(rezultat);
        }else if(wybrany.equals("BellmanFord")){
            rezultat = BellmanFord.start(graf, start);

            Map<String, Long> map = new LinkedHashMap<>();
            map.put("BellmanFord", rezultat.getCzas());

            wykres.setData(map);
            wykres.revalidate();
            wykres.repaint();

            panelGrafu.setGraf(graf);

            pokazRezultaty(rezultat);
        }else if(wybrany.equals("FloydWarshall")){
            rezultat = FloydWarshall.start(graf, start);

            Map<String, Long> map = new LinkedHashMap<>();
            map.put("FloydWarshall", rezultat.getCzas());

            wykres.setData(map);
            wykres.revalidate();
            wykres.repaint();

            panelGrafu.setGraf(graf);

            pokazRezultaty(rezultat);
        }else if(wybrany.equals("Wszystkie algorytmy")){
            uruchomWszystkieAlgorytmy(graf, start);
            panelGrafu.setGraf(graf);
            return;
        }

    }

    private void pokazRezultaty(Rezultat rezultat){

        String[] kolumny = {"Wierzchołek", "Odległość"};

        List<Integer> path = getSciezke(rezultat.getRodzic(), 5);

        System.out.println("Najkrótsza ścieżka: " + path);

        List<Integer> sciezka = getSciezke(rezultat.getRodzic(), 5);

        panelGrafu.setSciezka(sciezka);

        DefaultTableModel model = new DefaultTableModel(kolumny, 0);

        for(int i = 0; i < rezultat.getOdleglosci().length; i++){

            model.addRow(new Object[]{i, rezultat.getOdleglosci()[i]});

        }

        tablicaRezultatow.setModel(model);

        JOptionPane.showMessageDialog(this, "Czas działania: " + rezultat.getCzas() + " ns");

    }

    private void uruchomWszystkieAlgorytmy(Graf graf, int start){

        Rezultat dijkstra = Dijkstra.start(graf, start);
        Rezultat bellman = BellmanFord.start(graf, start);
        Rezultat floyd = FloydWarshall.start(graf, start);

        Map<String, Long> map = new LinkedHashMap<>();

        map.put("Dijkstra", dijkstra.getCzas());
        map.put("Bellman", bellman.getCzas());
        map.put("Floyd", floyd.getCzas());

        wykres.setData(map);

        pokazRezultaty(dijkstra);

    }

    public static List<Integer> getSciezke(int[] rodzic, int koniec) {

        List<Integer> sciezka = new ArrayList<>();

        for (int v = koniec; v != -1; v = rodzic[v]) {
            sciezka.add(v);
        }

        Collections.reverse(sciezka);

        return sciezka;
    }

    private void testWierzcholki() {

        long[] times = new long[vSizes.length];

        for (int i = 0; i < vSizes.length; i++) {

            Graf graf = GenerowanieGrafu.generujGraf(vSizes[i], vSizes[i] * 2);

            Rezultat r = Dijkstra.start(graf, 0);

            times[i] = r.getCzas();
        }

        wykres.setData(vSizes, times, "Czas vs liczba wierzchołków");
    }

    private void testKrawedzie() {

        long[] times = new long[eSizes.length];

        for (int i = 0; i < eSizes.length; i++) {

            Graf graf = GenerowanieGrafu.generujGraf(100, eSizes[i]);

            Rezultat r = Dijkstra.start(graf, 0);

            times[i] = r.getCzas();
        }

        wykres.setData(eSizes, times, "Czas vs liczba krawędzi");
    }



}
