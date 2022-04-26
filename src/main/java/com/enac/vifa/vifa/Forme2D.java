package com.enac.vifa.vifa;

import earcut4j.Earcut;
import java.util.ArrayList;
import javafx.geometry.Point2D;

import javafx.geometry.Point3D;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class Forme2D {
    private String nom;
    private ArrayList<Point3D> contour;

    public Forme2D(String nom) {
        this.nom = nom;
        this.contour=new ArrayList<Point3D>();
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<Point3D> getContour() {
        return contour;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        String res ="";
        int c=0;
        for (Point3D pt:contour){
            if (c!=0){res+=", ";}
            c++;
            res +="("+pt.getX()+", "+pt.getY()+", "+pt.getZ()+")";
        }
        return "Forme2D ["+nom + "]:\n{"+res+"}";
    }
    public void addPoint (Point3D p){
        this.contour.add(p);
    }
    public MeshView Draw2D() {
        // determine the plane that the shape is in
        int sides=this.getContour().size();
        if (sides<3) {throw new IndexOutOfBoundsException("Must be at least 3 sides");}
        Point3D a = this.getContour().get(0);
        Point3D b = this.getContour().get(1);

        // define texture coordinates
        ArrayList textures = new ArrayList<Point2D>();
        double[] earcut = new double[2*sides];
        for (int i=0;i<sides;i++) {
            Point3D p = this.getContour().get(i);
            textures.add(new Point2D(a.distance(p)*Math.cos(a.angle(b,p)),a.distance(p)*Math.sin(a.angle(b,p))));
            earcut[2*i]=a.distance(p)*Math.cos(a.angle(b,p));
            earcut[2*i+1]=a.distance(p)*Math.sin(a.angle(b,p));
        }
        double maxX=0,maxY=0,minX=0,minY=0;
        for (int i=0;i<sides;i++) {
            Point2D p = (Point2D) textures.get(i);
            if (p.getX()<minX) {
                minX=p.getX();
            }
            if (p.getY()<minY) {
                minY=p.getY();
            }
        }
        for (int i=0;i<sides;i++) {
            Point2D p = (Point2D) textures.get(i);
            textures.set(i,new Point2D(p.getX()-minX,p.getY()-minY));
        }
        for (int i=0;i<sides;i++) {
            Point2D p = (Point2D) textures.get(i);
            if (p.getX()>maxX) {
                maxX=p.getX();
            }
            if (p.getY()>maxY) {
                maxY=p.getY();
            }
        }
        for (int i=0;i<sides;i++) {
            Point2D p = (Point2D) textures.get(i);
            textures.set(i,new Point2D(p.getX()/maxX,p.getY()/maxY));
        }

        TriangleMesh m = new TriangleMesh();
        
        for (int i=0;i<sides;i++) {
            Point3D p = this.getContour().get(i);
            Point2D p2 = (Point2D) textures.get(i);
            m.getPoints().addAll((float)p.getX(),(float)p.getY(),(float)p.getZ());
            m.getTexCoords().addAll((float)p2.getX(),(float)p2.getY());
        }
        for (int i:Earcut.earcut(earcut,null,2)) {
            m.getFaces().addAll(i,i);
        }
        MeshView mv = new MeshView(m);
        return mv;
    }
}