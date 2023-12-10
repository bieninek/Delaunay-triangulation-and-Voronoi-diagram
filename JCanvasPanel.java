package com.company;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import static com.company.DataManager.alphaVoronoi;

public class JCanvasPanel extends JPanel{

    DataManager dm;

    public JCanvasPanel(DataManager dm) {
        this.dm = dm;
    }

    void update(){

        this.repaint();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.

        BufferedImage canvasImg = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D canvasPen = canvasImg.createGraphics();

        Utility util = new Utility(this.dm);

        //======================================================================
        //draw selection

        g.setColor(new Color(0, 0, 0, 255));

        if(dm.points.size() > 0)
            for(Point p : dm.points)
                g.fillRect(p.x, p.y, dm.pointSize, dm.pointSize);

        g.setColor(new Color(0, 0, 255, dm.alphaDelaunay));

        if(dm.triangles.size() > 0)
            for(Triangle t : dm.triangles){

                g.drawLine(t.p1.x, t.p1.y, t.p2.x, t.p2.y);
                g.drawLine(t.p2.x, t.p2.y, t.p3.x, t.p3.y);
                g.drawLine(t.p3.x, t.p3.y, t.p1.x, t.p1.y);

                Circle c = util.findCircle(t.p1, t.p2, t.p3);

//                g.setColor(new Color(255, 0, 0, 255));
//                g.drawOval(c.center.x-c.radii, c.center.y-c.radii, c.radii*2, c.radii*2);
//                g.setColor(new Color(0, 0, 255, 255));

            }

        //canvasPen.setColor(Color.BLACK);

        //======================================================================

        for (int i = 0; i < dm.width; i++) {
            for (int j = 0; j < dm.height; j++) {
                g.setColor(new Color((int)dm.colors[i][j]*100, (int)(dm.colors[i][j]*100)+60,
                        (int)(dm.colors[i][j]*100)+156, alphaVoronoi));
                g.fillRect(i, j, 1, 1);
            }
        }

        g.drawImage(canvasImg, 0, 0, this);

        g.dispose();

    }

    @Override
    public void repaint() {
        super.repaint(); //To change body of generated methods, choose Tools | Templates.

    }

}
