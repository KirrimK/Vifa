package com.enac.vifa.vifa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Date;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import javafx.geometry.Point3D;
import javafx.scene.shape.MeshView;

public class Modele {
    private static Modele modele;
    private ArrayList<Forme2D> listeDesFormes;
    private SimpleDoubleProperty mass;
    private SimpleDoubleProperty xCentrage;
    private SimpleDoubleProperty vAir;
    private SimpleDoubleProperty psi;
    private SimpleDoubleProperty theta;
    private SimpleDoubleProperty phi;
    private SimpleDoubleProperty alpha;
    private SimpleDoubleProperty beta;
    private SimpleDoubleProperty a0;
    private SimpleDoubleProperty trim;
    private SimpleDoubleProperty dl;
    private SimpleDoubleProperty dm;
    private SimpleDoubleProperty dn;
    private SimpleDoubleProperty dx;
    private SimpleDoubleProperty p;
    private SimpleDoubleProperty q;
    private SimpleDoubleProperty r;
    private Ivy radio;
    private boolean receivedDrawFFS = false;
    private String BUS = "127.255.255.255:2010";
    
    //      MESSAGES RECEIVED FROM IVY :

    private String INIT_FORME_2D_MSG = "^ShapeStart name=(.*)$";
    private String POINT_DE_LA_FORME = "^ShapePoint name=(.*) ptX=(.*) ptY=(.*) ptZ=(.*)$";
    private String FIN_DE_DESCRIPTION = "^Draw ffs$";

    //MESSAGES SENT THROUGH IVY :
    private String COMPUTE_DEMND = "StartComputation mass=%f xcg=%f vair=%f psi=%f theta=%f phi=%f alpha=%f betha=%f a0=%f trim=%f dl=%f dm=%f dn=%f dx=%f p=%f q=%f r=%f";
    private String DEMANDE_DESCR = "StartGettingShapes mass=%f xcg=%f vair=%f psi=%f theta=%f phi=%f alpha=%f betha=%f a0=%f trim=%f dl=%f dm=%f dn=%f";
    
    //CONSTRUCTOR

    private Modele() {
        this.listeDesFormes = new ArrayList<Forme2D>();
        this.xCentrage=new SimpleDoubleProperty(0) ;
        this.vAir=new SimpleDoubleProperty(0) ;
        this.psi=new SimpleDoubleProperty(0) ;
        this.theta=new SimpleDoubleProperty(0) ;
        this.phi=new SimpleDoubleProperty(0) ;
        this.alpha=new SimpleDoubleProperty(0) ;
        this.beta=new SimpleDoubleProperty(0) ;
        this.a0=new SimpleDoubleProperty(0) ;
        this.trim=new SimpleDoubleProperty(0) ;
        this.dl=new SimpleDoubleProperty(0) ;
        this.dm=new SimpleDoubleProperty(0) ;
        this.dn=new SimpleDoubleProperty(0) ;
        this.dx=new SimpleDoubleProperty(0) ;
        this.p=new SimpleDoubleProperty(0) ;
        this.q=new SimpleDoubleProperty(0) ;
        this.r=new SimpleDoubleProperty(0) ;
        this.mass=new SimpleDoubleProperty(0) ;
        this.radio = new Ivy("ViFA_IHM", "ViFA_IHM is ready !", null);
        try{
        this.radio.bindMsg(this.INIT_FORME_2D_MSG, new IvyMessageListener() {
            @Override
            synchronized public void receive(IvyClient client, String[] nomDansTableau) {
                addForme(nomDansTableau[0]);
            }
        });
        this.radio.bindMsg(this.POINT_DE_LA_FORME, new IvyMessageListener() {
            @Override
            public synchronized void receive(IvyClient client, String[] args) {
                String name = args[0];
                double x = Double.parseDouble(args[1]);
                double y = Double.parseDouble(args[2]);
                double z = Double.parseDouble(args[3]);
                addPointToForme(name, x, y, z);
            }
        });
        this.radio.bindMsg(this.FIN_DE_DESCRIPTION, (IvyClient client, String[] args) -> {
            receivedDrawFFS = true;
        });
        // this.radio.bindMsg("(.*)",(IvyClient client, String[] args) -> {
        //     System.out.println(args[0]);
        // });
        this.radio.start(this.BUS);
    }
        catch (IvyException e){
            System.out.println(e);
            System.exit(42);
        }
        
    }

    public static Modele getInstance (){
        if (modele==null){
            modele = new Modele();
        }
        return modele;
    }

 //GETTERS AND SETTERS

    public double getMass() {
        return mass.getValue();
    }

    public SimpleDoubleProperty getMassProperty(){
        return mass;
    }

    public void setMass(double mass) {
        this.mass.setValue(mass);
    }

    public SimpleDoubleProperty getxCentrageProperty() {
        return xCentrage;
    }

    public double getxCentrage() {
        return xCentrage.getValue();
    }

    public void setxCentrage(double xCentrage) {
        this.xCentrage.setValue(xCentrage);
    }

    public SimpleDoubleProperty getvAirProperty() {
        return vAir;
    }

    public double getvAir() {
        return vAir.getValue();
    }

    public void setvAir(double vAir) {
        this.vAir.setValue(vAir);
    }

    public SimpleDoubleProperty getPsiProperty() {
        return psi;
    }

    public double getPsi() {
        return psi.getValue();
    }

    public void setPsi(double psi) {
        this.psi.setValue(psi);
    }

    public SimpleDoubleProperty getThetaProperty() {
        return theta;
    }

    public double getTheta() {
        return theta.getValue();
    }

    public void setTheta(double theta) {
        this.theta.setValue(theta);
    }

    public SimpleDoubleProperty getPhiProperty() {
        return phi;
    }

    public double getPhi() {
        return phi.getValue();
    }

    public void setPhi(double phi) {
        this.phi.setValue(phi);
    }

    public SimpleDoubleProperty getAlphaProperty() {
        return alpha;
    }

    public double getAlpha() {
        return alpha.getValue();
    }

    public void setAlpha(double alpha) {
        this.alpha.setValue(alpha);
    }

    public SimpleDoubleProperty getBetaProperty() {
        return beta;
    }

    public double getBeta() {
        return beta.getValue();
    }

    public void setBeta(double beta) {
        this.beta.set(beta);
    }

    public SimpleDoubleProperty getA0Property() {
        return a0;
    }

    public double getA0() {
        return a0.getValue();
    }

    public void setA0(double a0) {
        this.a0.setValue(a0);
    }

    public SimpleDoubleProperty getTrimProperty() {
        return trim;
    }

    public double getTrim() {
        return trim.getValue();
    }

    public void setTrim(double trim) {
        this.trim.setValue(trim);
    }

    public SimpleDoubleProperty getDlProperty() {
        return dl;
    }

    public double getDl() {
        return dl.getValue();
    }

    public void setDl(double dl) {
        this.dl.setValue(dl);;
    }

    public SimpleDoubleProperty getDmProperty() {
        return dm;
    }

    public double getDm() {
        return dm.getValue();
    }

    public void setDm(double dm) {
        this.dm.setValue(dm);
    }

    public SimpleDoubleProperty getDnProperty() {
        return dn;
    }

    public double getDn() {
        return dn.getValue();
    }

    public void setDn(double dn) {
        this.dn.setValue(dn);
    }

    public SimpleDoubleProperty getDxProperty() {
        return dx;
    }

    public double getDx() {
        return dx.getValue();
    }

    public void setDx(double dx) {
        this.dx.setValue(dx);
    }

    public SimpleDoubleProperty getPProperty() {
        return p;
    }

    public double getP() {
        return p.getValue();
    }

    public void setP(double p) {
        this.p.setValue(p);
    }

    public SimpleDoubleProperty getQProperty() {
        return q;
    }

    public double getQ() {
        return q.getValue();
    }

    public void setQ(double q) {
        this.q.setValue(q);
    }

    public SimpleDoubleProperty getRProperty() {
        return r;
    }

    public double getR() {
        return r.getValue();
    }

    public void setR(double r) {
        this.r.setValue(r);
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
        try {
            Thread.sleep(200);
        } catch (InterruptedException e1) {
        }
        this.receivedDrawFFS = false;
        System.out.println("Description en attente...");
        long temps = (new Date()).getTime();
        this.listeDesFormes = new ArrayList<Forme2D>();
        try {
            String msg=String.format(this.DEMANDE_DESCR,mass.getValue(), xCentrage.getValue(), 
            vAir.getValue(), psi.getValue(), theta.getValue(), phi.getValue(), alpha.getValue(), beta.getValue(), 
            a0.getValue(), trim.getValue(), dl.getValue(), dm.getValue(), dn.getValue() ).replace(',','.');
            this.radio.sendMsg(msg);
        }
        catch (IvyException e){
            e.printStackTrace();
            System.out.println(e);
        }
        while ((! this.receivedDrawFFS)&((new Date()).getTime()-temps < 15000) ){
            //On attends la fin de la description ou 10 secs
        }
        if (! this.receivedDrawFFS){//on a attendu 10secs, et on n'a pas la description
            IvyException e = new IvyException("Time out de l'attente de description");
            e.printStackTrace();
            System.out.println(e);
            getDescription();
        }
        else{
            System.out.println("Description received:\n"+this.toString());
        }
    }
    public String toString (){
        String res="Modele [\n";
        for (Forme2D f :listeDesFormes){
            res += "\t"+f.toString()+"\n";
        }
        return res+"\n\t\t]";
    }

    public ArrayList<Forme2D> getListeDesFormes(){
        return listeDesFormes;
    }

    public ArrayList<MeshView> DrawFFS() {
        ArrayList meshList = new ArrayList<MeshView>();
        for (Forme2D f :this.listeDesFormes) {
            meshList.add(f.Draw2D());
        }
        return meshList;
    }
}
