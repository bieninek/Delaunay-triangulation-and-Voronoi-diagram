package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here

        DataManager dm = new DataManager();
        dm.setAlphaVoronoi(170);
        dm.setAlphaDelaunay(255);
        dm.randomizePoints(10);

        Utility util = new Utility(dm);
        util.delaunay2();
        util.voronoi();

        Window wind = new Window(dm);

    }
}
