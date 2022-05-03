package com.enac.vifa.vifa;

import earcut4j.Earcut;
import java.util.ArrayList;
import javafx.geometry.Point2D;

import javafx.geometry.Point3D;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class Forme2D {
    private String nom;
    private ArrayList<Point3D> contour;
    private PhongMaterial color;

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
 
    public PhongMaterial getColor() {
        return color;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setColor(PhongMaterial color) {
        this.color = color;
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
        // lists of vertices are sorted clockwise, we want them to be counterclockwise
        //Collections.reverse(this.contour);
        // determine the plane that the shape is in
        int sides=this.contour.size();
        if (sides<3) {throw new IndexOutOfBoundsException("Must be at least 3 sides");}
        Point3D a = this.contour.get(0);
        Point3D b = this.contour.get(1);

        // define texture coordinates
        ArrayList textures = new ArrayList<Point2D>();
        double[] earcut = new double[3*sides];
        for (int i=0;i<sides;i++) {
            Point3D p = this.contour.get(i);
            textures.add(new Point2D(a.distance(p)*Math.cos(a.angle(b,p)),a.distance(p)*Math.sin(a.angle(b,p))));
            earcut[3*i]=p.getX();
            earcut[3*i+1]=p.getY();
            earcut[3*i+1]=p.getZ();
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
            Point3D p = this.contour.get(i);
            Point2D p2 = (Point2D) textures.get(i);
            m.getPoints().addAll((float)p.getX(),(float)p.getY(),(float)p.getZ());
            m.getTexCoords().addAll((float)p2.getX(),(float)p2.getY());
        }
        for (int i:Earcut.earcut(earcut,null,3)) {
            m.getFaces().addAll(i,i);
        }
        MeshView mv = new MeshView(m);
        mv.setCullFace(CullFace.NONE);
        mv.setMaterial(this.color);
        return mv;
    }
}