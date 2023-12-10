package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class Window {

    int width, height;

    DataManager dm;

    JFrame mainWindow;

    JPanel mainPan; //main panel

    JPanel midPan;
    JPanel rightPan;
    JCanvasPanel canvas;
    JSlider alphaVoronoi;
    JSlider alphaDelaunay;

    Window(DataManager dm){

        this.dm = dm;

        this.width = dm.width;
        this.height = dm.height;

        this.mainWindow = new JFrame();
        this.mainWindow.setSize(new Dimension(dm.width, dm.height));
        this.mainWindow.setLocationRelativeTo(null);


        this.mainPan = new JPanel();
        this.mainPan.setLayout(new BorderLayout());

        this.midPan = new JPanel();
        this.rightPan = new JPanel();
        this.canvas = new JCanvasPanel(this.dm);

        this.alphaVoronoi = new JSlider(JSlider.VERTICAL, 0, 255, 170);
        this.alphaDelaunay = new JSlider(JSlider.VERTICAL, 0, 255, 255);
        this.alphaDelaunay.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                dm.setAlphaDelaunay(alphaDelaunay.getValue());
                canvas.update();
            }
        });

        this.alphaVoronoi.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                dm.setAlphaVoronoi(alphaVoronoi.getValue());
                canvas.update();
            }
        });


        this.mainWindow.add(BorderLayout.EAST, this.alphaVoronoi);
        this.mainWindow.add(BorderLayout.WEST, this.alphaDelaunay);

        this.mainWindow.add(BorderLayout.CENTER, this.canvas);
        this.canvas.update();

        this.mainWindow.setVisible(true);
        this.mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }


}
