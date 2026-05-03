package Data;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Wykres extends JPanel {

    private Map<String,Long> data;

    public void setData(Map<String,Long> data) {
        this.data = data;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if(data == null || data.isEmpty())
            return;

        int w = getWidth();
        int h = getHeight();

        int m = 40;

        g.drawLine(m,h-m,w-m,h-m);
        g.drawLine(m,m,m,h-m);

        long max = 1;

        for(long x : data.values())
            max = Math.max(max,x);

        int i = 0;
        int step = (w-2*m)/data.size();

        for(String key : data.keySet()) {

            long val = data.get(key);

            int bh = (int)((double)val/max*(h-2*m));

            int x = m + i*step + 20;
            int y = h-m-bh;

            g.setColor(Color.BLUE);
            g.fillRect(x,y,40,bh);

            g.setColor(Color.BLACK);
            g.drawString(key,x,h-m+20);
            g.drawString(val+" ns",x,y-5);

            i++;
        }
    }
}