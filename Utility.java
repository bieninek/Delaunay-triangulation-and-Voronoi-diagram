package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Utility {

    DataManager dm;

    Utility(DataManager dm){

        this.dm = dm;

    }

    Circle findCircle(Point p1, Point p2, Point p3){
        //source:
        //https://www.geeksforgeeks.org/equation-of-circle-when-three-points-on-the-circle-are-given/
        int x12 = p1.x - p2.x;
        int x13 = p1.x - p3.x;

        int y12 = p1.y - p2.y;
        int y13 = p1.y - p3.y;

        int y31 = p3.y - p1.y;
        int y21 = p2.y - p1.y;

        int x31 = p3.x - p1.x;
        int x21 = p2.x - p1.x;

        // x1^2 - x3^2
        int sx13 = (int)(Math.pow(p1.x, 2) -
                Math.pow(p3.x, 2));

        // y1^2 - y3^2
        int sy13 = (int)(Math.pow(p1.y, 2) -
                Math.pow(p3.y, 2));

        int sx21 = (int)(Math.pow(p2.x, 2) -
                Math.pow(p1.x, 2));

        int sy21 = (int)(Math.pow(p2.y, 2) -
                Math.pow(p1.y, 2));

        int f = ((sx13) * (x12)
                + (sy13) * (x12)
                + (sx21) * (x13)
                + (sy21) * (x13))
                / (2 * ((y31) * (x12) - (y21) * (x13)));
        int g = ((sx13) * (y12)
                + (sy13) * (y12)
                + (sx21) * (y13)
                + (sy21) * (y13))
                / (2 * ((x31) * (y12) - (x21) * (y13)));

        int c = -(int)Math.pow(p1.x, 2) - (int)Math.pow(p1.y, 2) -
                2 * g * p1.x - 2 * f * p1.y;

        int h = -g;
        int k = -f;
        int sqr_of_r = h * h + k * k - c;

        double r = Math.sqrt(sqr_of_r);

        return new Circle(new Point(h, k), (int)r);

    }

    public double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    Boolean isInCircle(Circle cir, Point p){

        boolean isInside = false;

            if(distance(cir.center.x, cir.center.y, p.x, p.y) < cir.radii)
                isInside = true;

        return isInside;

    }

    float[][] voronoi(){
        //Draw draw = new Draw();
        Point[][] nearest = new Point[dm.width][dm.height];  // which point is pixel (i, j) nearest?
        float [][] colors = new float[dm.width][dm.height];
        float color;
        Utility util = new Utility(this.dm);

        for(Point p:dm.points) {
            //Point p = new Point(x, y);
            // StdOut.println("Inserting:       " + p);

            // compare each pixel (i, j) and find nearest point
            //draw.setPenColor(Color.getHSBColor((float) Math.random(), .7f, .7f));
            color = (float) Math.random();
            for (int i = 0; i < dm.width; i++) {
                for (int j = 0; j < dm.height; j++) {
                    Point q = new Point(i, j);
                    if ((nearest[i][j] == null) ||
                            (util.distance(q.x, q.y, p.x, p.y) < util.distance(nearest[i][j].x, nearest[i][j].y, q.x, q.y))) {
                        nearest[i][j] = p;
                        //draw.filledSquare(i + 0.5, j + 0.5, 0.5);
                        colors[i][j] = color;
                    }
                }
            }
        }
        dm.colors = colors;
        return colors;
    }

    void delaunay2(){

        Utility util = new Utility(this.dm);

        Point p1 = new Point(dm.width/2, 0);
        Point p2 = new Point(60, dm.height-60);
        Point p3 = new Point(dm.width-60, dm.height-60);

//        Point p1 = new Point(-dm.width, -dm.height);
//        Point p2 = new Point(0, 2*dm.height);
//        Point p3 = new Point(2*dm.width, 0);

        Triangle superTriangle = new Triangle(p1, p2, p3);

        dm.triangles.add(superTriangle);

        for(Point point:dm.points){
            Map<Integer, Edge> edgeBuffer = new HashMap<>();
            List<Triangle> trianglesToRemove = new ArrayList<>();
            for(Triangle t:dm.triangles){

                Circle c = util.findCircle(t.p1, t.p2, t.p3);

                Point p0 = c.center;
                if(p0==null)
                    break;
                double r = sqrt(pow(t.p1.x-p0.x, 2) + pow(t.p1.y - p0.y, 2));
                if(sqrt(pow(point.x - p0.x, 2) + pow(point.y - p0.y, 2))<r){ //check inside which triangle's circumcircle the point is
                    Edge e1 = new Edge(t.p1, t.p2);
                    Edge tmp = edgeBuffer.put(e1.hash(), e1);
                    edgeBuffer.remove(tmp!=null?e1.hash():null);

                    Edge e2 = new Edge(t.p2, t.p3);
                    tmp = edgeBuffer.put(e2.hash(), e2);
                    edgeBuffer.remove(tmp!=null?e2.hash():null);

                    Edge e3 = new Edge(t.p1, t.p3);
                    tmp = edgeBuffer.put(e3.hash(), e3);
                    edgeBuffer.remove(tmp!=null?e3.hash():null);

                    trianglesToRemove.add(t);

                }
            }

            dm.triangles.removeAll(trianglesToRemove);

            for(Map.Entry<Integer, Edge> edge:edgeBuffer.entrySet()){
                dm.triangles.add(new Triangle(edge.getValue().p1, edge.getValue().p2, point));
            }
        }

        List<Triangle> trianglesToRemove = new ArrayList<>();

        for(Triangle tr : dm.triangles){

            if(tr.p1 == superTriangle.p1 || tr.p2 == superTriangle.p1 || tr.p3 == superTriangle.p1)
                trianglesToRemove.add(tr);

            if(tr.p1 == superTriangle.p2 || tr.p2 == superTriangle.p2 || tr.p3 == superTriangle.p2)
                trianglesToRemove.add(tr);

            if(tr.p1 == superTriangle.p3 || tr.p2 == superTriangle.p3 || tr.p3 == superTriangle.p3)
                trianglesToRemove.add(tr);

        }

        dm.triangles.removeAll(trianglesToRemove);

    }

}
