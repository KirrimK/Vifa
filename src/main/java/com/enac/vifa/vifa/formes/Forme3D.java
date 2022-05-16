package com.enac.vifa.vifa.formes;


import java.util.ArrayList;
import java.util.List;

import com.enac.vifa.vifa.Configuration;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Translate;
import org.fxyz3d.shapes.primitives.TriangulatedMesh;
import org.fxyz3d.geometry.Point3D;

public class Forme3D {
    private String nom;
    private final List<Point3D> contour;

    public Forme3D(String nom) {
        this.nom = nom;
        this.contour=new ArrayList<>();
    }

    
    public String getNom() {
        return nom;
    }

    public List<Point3D> getContour() {
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
        return "Forme3D ["+nom + "]:\n{"+res+"}";
    }
    public void addPoint (Point3D p){
        this.contour.add(p);
    }

    public TriangulatedMesh setTriangulatedMesh() {
        // lists of vertices are sorted clockwise, we want them to be counterclockwise
        List<Point3D> contourN = new ArrayList<>();
        contourN.add(this.contour.get(0));
        int sides=this.contour.size();
        for (int i=sides-1;i>0;i--) {
            contourN.add(this.contour.get(i));
        }
        
            
        
        // determine the plane that the shape is in
        if (sides<3) {throw new IndexOutOfBoundsException("Must be at least 3 sides");}
        
        TriangulatedMesh m;
        m = new TriangulatedMesh(contourN, 2);
        m.setCullFace(CullFace.NONE);
        m.setLevel(0);
        m.setDiffuseColor(Configuration.getInstance().getCouleurFuselage());
        m.setRotationAxis(new javafx.geometry.Point3D(1, 0, 0));
        m.setRotate(90);
        m.setTranslateY(-2);
        m.setTranslateZ(1);
        return m;   
    }
    
    public Cylinder setNacelles(){
        
        List<Point3D> contourN = new ArrayList<>();
        contourN.add(this.contour.get(0));
        int sides=this.contour.size();
        for (int i=sides-1;i>0;i--) {
            contourN.add(this.contour.get(i));
        }
        int n = contourN.size();
        
        Point3D p = contourN.get(0);
        javafx.geometry.Point3D origine = new javafx.geometry.Point3D(p.getX(), p.getY(), p.getZ());
        double xmin = contourN.get(0).x;
        double xmax = contourN.get(n-2).x;
        double radius = Math.abs(xmax-xmin)/2;
        
        double ymax = contourN.get(0).y;
        double ymin = contourN.get(7).y;
          
        double height = Math.abs(ymax-ymin);
        
        Cylinder nacelle = new Cylinder(radius/1.5, height*4);
        nacelle.setMaterial(new PhongMaterial(Configuration.getInstance().getCouleurFuselage()));
        nacelle.setRotationAxis(new javafx.geometry.Point3D(0, 0, 1));
        nacelle.setRotate(90);
       
            
        nacelle.getTransforms().add(new Translate(origine.getZ(), -origine.getX()-nacelle.getHeight()/2, +origine.getY()-nacelle.getRadius()));
        return nacelle;
        
        
        
    }
}



          
       



