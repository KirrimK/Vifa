package com.enac.vifa.vifa;

import java.util.Date;
import javafx.beans.property.SimpleListProperty;
import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import javafx.geometry.Point3D;

public class Modele {
    private SimpleListProperty<Forme2D> listeDesFormes;
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
    private boolean receivedDrawFFS = false;
    private String BUS = "127.255.255.255:2010";
    
    //      MESSAGES RECEIVED FROM IVY :

    private String INIT_FORME_2D_MSG = "^ShapeStart name=(.*)$";
    private String POINT_DE_LA_FORME = "^ShapePoint name=(.*) ptX=(.*) ptY=(.*) ptZ=(.*)$";
    private String FIN_DE_DESCRIPTION = "^Draw ffs$";

    //MESSAGES SENT THROUGH IVY :

    private String DEMANDE_DESCR = "StartGettingShapes mass=%f xcg=%f vair=%f psi=%f theta=%f phi=%f alpha=%f betha=%f a0=%f trim=%f dl=%f dm=%f dn=%f";
    
    //CONSTRUCTOR

    public Modele() {
        this.listeDesFormes = new SimpleListProperty<Forme2D>();
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
        this.radio = new Ivy("ViFA_IHM", "ViFA_IHM is ready !", null);
        try{
        this.radio.bindMsg(this.INIT_FORME_2D_MSG, (IvyClient client, String[] nomDansTableau) -> {
            addForme(nomDansTableau[0]);
        });
        this.radio.bindMsg(this.POINT_DE_LA_FORME, (IvyClient client, String[] args) -> {
            String name = args[0];
            double x = Double.parseDouble(args[1]);
            double y = Double.parseDouble(args[2]);
            double z = Double.parseDouble(args[3]);
            addPointToForme(name, x, y, z);
        });
        this.radio.bindMsg(this.FIN_DE_DESCRIPTION, (IvyClient client, String[] args) -> {
            receivedDrawFFS = true;
        });
        this.radio.start(this.BUS);
    }
        catch (IvyException e){
            e.printStackTrace();
            System.out.println(e);
            System.exit(42);
        }
        
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

    public Forme2D getForme (String nom){
        for (Forme2D f:listeDesFormes){
            if (f.getNom().equals(nom)){
                return f;
            }
        };
        addForme(nom);
        return (getForme(nom));
    }

    public void getDescription(){
        this.receivedDrawFFS = false;
        System.out.println("Description en attente...");
        long temps = (new Date()).getTime();
        try {
            String msg=String.format(this.DEMANDE_DESCR,mass, xCentrage, vAir, psi, theta, phi, alpha, beta, a0, trim, dl, dm, dn ).replace(',','.');
            this.radio.sendMsg(msg);
        }
        catch (IvyException e){
            e.printStackTrace();
            System.out.println(e);
        }
        while ((! this.receivedDrawFFS)&((new Date()).getTime()-temps < 10000) ){
            //On attends la fin de la description ou 10 secs
        }
        if (! this.receivedDrawFFS){//on a attendu 10secs, et on n'a pas la description
            IvyException e = new IvyException("Time out de l'attente de description");
            e.printStackTrace();
            System.out.println(e);
            System.exit(420);
        }
        else {
            System.out.println("Description reçue");
        }
    }
    public String toString (){
        String res="Modele [\n";
        for (Forme2D f :listeDesFormes){
            res += f.toString()+"\n";
        }
        return res+"\n\t\t]";
    }

    public SimpleListProperty<Forme2D> getListeDesFormes(){
        return listeDesFormes;
    }
}
