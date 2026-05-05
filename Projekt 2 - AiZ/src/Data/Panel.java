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

    private JComboBox<String> comboAlg;
    private JTextField txtV;
    private JTextField txtE;
    private JTextField txtStart;

    private JCheckBox chkSkierowany;

    private JButton btnStart;
    private JButton btnLoad;
    private JButton btnTestV;
    private JButton btnTestE;

    private JTable table;

    private PanelGrafu panelGrafu;
    private Wykres wykres;

    private Graf currentGraf;

    public Panel() {

        setTitle("Projekt 2 AiZ - Najkrótsze ścieżki w grafie");
        setSize(1300, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initTop();
        initCenter();
        initEvents();

        setVisible(true);

        EksportWynikow.zapiszNaglowek();
    }

    private void initTop() {

        JPanel top = new JPanel();

        comboAlg = new JComboBox<>(new String[]{
                "Dijkstra",
                "BellmanFord",
                "FloydWarshall",
                "Wszystkie"
        });

        txtV = new JTextField("10",5);
        txtE = new JTextField("20",5);
        txtStart = new JTextField("0",5);

        chkSkierowany = new JCheckBox("Skierowany");

        btnStart = new JButton("Start");
        btnLoad = new JButton("Wczytaj plik");
        btnTestV = new JButton("Test V");
        btnTestE = new JButton("Test E");

        top.add(new JLabel("Algorytm:"));
        top.add(comboAlg);

        top.add(new JLabel("V:"));
        top.add(txtV);

        top.add(new JLabel("E:"));
        top.add(txtE);

        top.add(new JLabel("Start:"));
        top.add(txtStart);

        top.add(chkSkierowany);

        top.add(btnStart);
        top.add(btnLoad);
        top.add(btnTestV);
        top.add(btnTestE);

        add(top, BorderLayout.NORTH);
    }

    private void initCenter() {

        panelGrafu = new PanelGrafu();
        table = new JTable();
        wykres = new Wykres();

        JSplitPane right =
                new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                        new JScrollPane(table),
                        wykres);

        right.setDividerLocation(320);

        JSplitPane main =
                new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                        panelGrafu,
                        right);

        main.setDividerLocation(650);

        add(main, BorderLayout.CENTER);
    }

    private void initEvents() {

        btnStart.addActionListener(e -> runMain());

        btnLoad.addActionListener(e -> loadFile());

        btnTestV.addActionListener(e -> testV());

        btnTestE.addActionListener(e -> testE());
    }

    private void runMain() {

        try {

            int v = Integer.parseInt(txtV.getText());
            int e = Integer.parseInt(txtE.getText());
            int start = Integer.parseInt(txtStart.getText());

            boolean skierowany = chkSkierowany.isSelected();

            currentGraf = GenerowanieGrafu.generuj(v, e, skierowany);

            panelGrafu.setGraf(currentGraf);

            runAlgorithm(currentGraf, start);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Błąd danych!");
        }
    }

    private void runAlgorithm(Graf graf, int start) {

        String alg = (String) comboAlg.getSelectedItem();

        if (alg.equals("Dijkstra")) {

            Rezultat r = Dijkstra.start(graf, start);

            EksportWynikow.zapiszWynik("Dijkstra", r.getCzas(), graf.getV(), txtE.getText().isEmpty() ? 0 : Integer.parseInt(txtE.getText()));

            showTable(r);

            Map<String,Long> map = new LinkedHashMap<>();
            map.put("Dijkstra", r.getCzas());

            wykres.setData(map);

        } else if (alg.equals("BellmanFord")) {

            Rezultat r = BellmanFord.start(graf, start);

            EksportWynikow.zapiszWynik("BellmanFord", r.getCzas(), graf.getV(), txtE.getText().isEmpty() ? 0 : Integer.parseInt(txtE.getText()));

            showTable(r);

            Map<String,Long> map = new LinkedHashMap<>();
            map.put("BellmanFord", r.getCzas());

            wykres.setData(map);

        } else if (alg.equals("FloydWarshall")) {

            Rezultat r = FloydWarshall.start(graf, start);

            EksportWynikow.zapiszWynik("FloydWarshall", r.getCzas(), graf.getV(), txtE.getText().isEmpty() ? 0 : Integer.parseInt(txtE.getText()));

            showTable(r);

            Map<String,Long> map = new LinkedHashMap<>();
            map.put("FloydWarshall", r.getCzas());

            wykres.setData(map);

        } else {

            Rezultat d = Dijkstra.start(graf, start);
            Rezultat b = BellmanFord.start(graf, start);
            Rezultat f = FloydWarshall.start(graf, start);

            EksportWynikow.zapiszWynik("Dijkstra", d.getCzas(), graf.getV(), Integer.parseInt(txtE.getText()));
            EksportWynikow.zapiszWynik("Bellman", b.getCzas(), graf.getV(), Integer.parseInt(txtE.getText()));
            EksportWynikow.zapiszWynik("Floyd", f.getCzas(), graf.getV(), Integer.parseInt(txtE.getText()));

            showTable(d);

            Map<String,Long> map = new LinkedHashMap<>();
            map.put("Dijkstra", d.getCzas());
            map.put("Bellman", b.getCzas());
            map.put("Floyd", f.getCzas());

            wykres.setData(map);
        }
    }

    private void showTable(Rezultat r) {

        DefaultTableModel model =
                new DefaultTableModel(
                        new String[]{"Wierzchołek","Odległość"},
                        0
                );

        for(int i=0;i<r.getDist().length;i++) {

            model.addRow(new Object[]{
                    i,
                    r.getDist()[i] == Integer.MAX_VALUE ? "INF" : r.getDist()[i]
            });
        }

        table.setModel(model);

        int end = r.getDist().length - 1;

        java.util.List<Integer> path =
                buildPath(r.getParent(), end);

        panelGrafu.setSciezka(path);

        JOptionPane.showMessageDialog(this,
                "Najkrótsza ścieżka do " + end + ":\n" +
                        path.toString() +
                        "\nKoszt = " + r.getDist()[end]);
    }

    private void loadFile() {

        JFileChooser chooser = new JFileChooser();

        int x = chooser.showOpenDialog(this);

        if(x == JFileChooser.APPROVE_OPTION) {

            try {

                currentGraf =
                        Plik.wczytaj(
                                chooser.getSelectedFile().getAbsolutePath(),
                                chkSkierowany.isSelected()
                        );

                panelGrafu.setGraf(currentGraf);

                runAlgorithm(currentGraf,
                        Integer.parseInt(txtStart.getText()));

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,"Błąd pliku!");
            }
        }
    }

    private void testV() {

        EksportWynikow.zresetuj();

        int[] arr = {10,50,100,200,500};

        Map<String,Long> wykresData = new LinkedHashMap<>();

        String alg = (String) comboAlg.getSelectedItem();

        for(int v : arr) {

            Graf g = GenerowanieGrafu.generuj(v, v*2, false);

            if(alg.equals("Wszystkie")) {

                Rezultat d = Dijkstra.start(g,0);
                Rezultat b = BellmanFord.start(g,0);
                Rezultat f = FloydWarshall.start(g,0);

                EksportWynikow.zapiszWynik("Dijkstra", d.getCzas(), v, v*2);
                EksportWynikow.zapiszWynik("BellmanFord", b.getCzas(), v, v*2);
                EksportWynikow.zapiszWynik("Floyd", f.getCzas(), v, v*2);

                wykresData.put("Dijkstra V=" + v, d.getCzas());
                wykresData.put("Bellman V=" + v, b.getCzas());
                wykresData.put("Floyd V=" + v, f.getCzas());

            } else {

                Rezultat r = runSelectedAlgorithm(g, 0);

                EksportWynikow.zapiszWynik(alg, r.getCzas(), v, v*2);

                wykresData.put(alg + " V=" + v, r.getCzas());
            }
        }

        wykres.setData(wykresData);
    }

    private void testE() {

        EksportWynikow.zresetuj();

        int[] arr = {20,50,100,300,500};

        Map<String,Long> wykresData = new LinkedHashMap<>();

        String alg = (String) comboAlg.getSelectedItem();

        for(int e : arr) {

            Graf g = GenerowanieGrafu.generuj(100, e, false);

            if(alg.equals("Wszystkie")) {

                Rezultat d = Dijkstra.start(g,0);
                Rezultat b = BellmanFord.start(g,0);
                Rezultat f = FloydWarshall.start(g,0);

                EksportWynikow.zapiszWynik("Dijkstra", d.getCzas(), 100, e);
                EksportWynikow.zapiszWynik("BellmanFord", b.getCzas(), 100, e);
                EksportWynikow.zapiszWynik("Floyd", f.getCzas(), 100, e);

                wykresData.put("Dijkstra E=" + e, d.getCzas());
                wykresData.put("Bellman E=" + e, b.getCzas());
                wykresData.put("Floyd E=" + e, f.getCzas());

            } else {

                Rezultat r = runSelectedAlgorithm(g, 0);

                EksportWynikow.zapiszWynik(alg, r.getCzas(), 100, e);

                wykresData.put(alg + " E=" + e, r.getCzas());
            }
        }

        wykres.setData(wykresData);
    }

    private java.util.List<Integer> buildPath(int[] parent, int end){

        java.util.List<Integer> path = new java.util.ArrayList<>();

        while(end != -1){
            path.add(0,end);
            end = parent[end];
        }

        return path;
    }

    private Rezultat runSelectedAlgorithm(Graf g, int start) {

        String alg = (String) comboAlg.getSelectedItem();

        switch (alg) {
            case "Dijkstra":
                return Dijkstra.start(g, start);

            case "BellmanFord":
                return BellmanFord.start(g, start);

            case "FloydWarshall":
                return FloydWarshall.start(g, start);
        }

        return null;
    }



}
