package com.company;

import java.util.ArrayList;
import java.util.Random;

public class DataManager {

    ArrayList<Point> points;
    ArrayList<Triangle> triangles;
    float[][] colors;

    public static int alphaVoronoi;
    public static int alphaDelaunay;

    int pointSize = 5;

    int width;
    int height;

    int border = 300;


    DataManager(){

        points = new ArrayList<>();
        triangles = new ArrayList<>();
        //colors = new ArrayList<>();

        this.width = 800;
        this.height = 800;

    }

    void randomizePoints(int num){

        Random rand = new Random(System.currentTimeMillis());

        int x, y;

        for(int i = 0; i < num; i++){

            x = border + rand.nextInt(this.width-2*border);
            y = border + rand.nextInt(this.height-2*border);

            System.out.println("x: " + x +" " + y);

            this.points.add(new Point(x, y));

        }



    }

    public void setAlphaVoronoi(int alphaVoronoi) {
        this.alphaVoronoi = alphaVoronoi;
    }

    public void setAlphaDelaunay(int alphaDelaunay) {
        this.alphaDelaunay = alphaDelaunay;
    }
}
