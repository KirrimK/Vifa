package com.enac.vifa.vifa;

import fr.dgac.ivy.Ivy;

import java.util.ArrayList;

import javafx.geometry.Point3D;
public class Modele {
    private ArrayList<Forme2D> listeDesFormes;
    private double mass;
    private double xCentrage;
    private double vAir;
    private double psi;
    private double theta;
    private double phi;
    private double alpha;
    private double beta;
    private double a0;
    private double trim;
    private double dl;
    private double dm;
    private double dn;
    private double dx;
    private double p;
    private double q;
    private double r;
    private Ivy radio;
    
    //CONSTRUCTOR

    public Modele() {
        this.listeDesFormes = new ArrayList<Forme2D>();
        this.mass = 0;
        this.xCentrage=0;
        this.vAir= 0;
        this.psi=0;
        this.theta=0;
        this.phi=0;
        this.alpha=0;
        this.beta=0;
        this.a0=0;
        this.trim=0;
        this.dl=0;
        this.dm=0;
        this.dn=0;
        this.dx=0;
        this.p=0;
        this.q=0;
        this.r=0;
    }

 //GETTERS AND SETTERS

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getxCentrage() {
        return xCentrage;
    }

    public void setxCentrage(double xCentrage) {
        this.xCentrage = xCentrage;
    }

    public double getvAir() {
        return vAir;
    }

    public void setvAir(double vAir) {
        this.vAir = vAir;
    }

    public double getPsi() {
        return psi;
    }

    public void setPsi(double psi) {
        this.psi = psi;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public double getPhi() {
        return phi;
    }

    public void setPhi(double phi) {
        this.phi = phi;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getA0() {
        return a0;
    }

    public void setA0(double a0) {
        this.a0 = a0;
    }

    public double getTrim() {
        return trim;
    }

    public void setTrim(double trim) {
        this.trim = trim;
    }

    public double getDl() {
        return dl;
    }

    public void setDl(double dl) {
        this.dl = dl;
    }

    public double getDm() {
        return dm;
    }

    public void setDm(double dm) {
        this.dm = dm;
    }

    public double getDn() {
        return dn;
    }

    public void setDn(double dn) {
        this.dn = dn;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

    public double getQ() {
        return q;
    }

    public void setQ(double q) {
        this.q = q;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }
    
    public void addForme (String nom){
        this.listeDesFormes.add(new Forme2D(nom));
    }

    public void addPointToForme (String nom, double x, double y, double z){
        for (Forme2D f:listeDesFormes){
            if (f.getNom().equals(nom)){
                f.addPoint(new Point3D(x, y, z));
                break;
            }
        }
    }
}
