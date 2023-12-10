package com.company;

public class Edge {
    public Edge(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public boolean equals(Object e){
        return (p1.equals(((Edge)e).p1) && p2.equals(((Edge)e).p2)) || (p1.equals(((Edge)e).p2) && p2.equals(((Edge)e).p1));
    }

    public Integer hash(){
        return (int)(p1.x+p1.y+p2.x+p2.y);
    }

    Point p1;
    Point p2;
}
