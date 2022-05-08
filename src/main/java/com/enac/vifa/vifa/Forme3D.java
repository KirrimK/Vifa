package com.enac.vifa.vifa;

import earcut4j.Earcut;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.geometry.Point2D;


import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
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

    public MeshView Draw3D() {
        // lists of vertices are sorted clockwise, we want them to be counterclockwise
        if (this.getNom().equals("fuselage")){
            contour.add(this.contour.get(0));
            int sides=this.contour.size();
            for (int i=sides-1;i>0;i--) {
                contour.add(this.contour.get(i));
            }
        
        // determine the plane that the shape is in
        if (sides<3) {throw new IndexOutOfBoundsException("Must be at least 3 sides");}
        }
        
        TriangulatedMesh m;
        m = new TriangulatedMesh(this.getContour(), 20);
        m.setCullFace(CullFace.NONE);
        m.setColors(200);
        return m;
    }
}



          
       



