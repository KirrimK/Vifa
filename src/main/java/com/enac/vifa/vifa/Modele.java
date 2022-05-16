package com.enac.vifa.vifa;

import java.util.ArrayList;
import java.util.Date;

import com.enac.vifa.vifa.formes.Forme2D;
import com.enac.vifa.vifa.formes.Forme3D;
import com.enac.vifa.vifa.formes.Moment3D;
import com.enac.vifa.vifa.formes.Vecteur3D;

import com.enac.vifa.vifa.vues.Vue3D;
import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.shape.MeshView;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Translate;
import org.fxyz3d.shapes.primitives.TriangulatedMesh;

public class Modele {
    private int TEMPS_MIN_ENTRE_DEUX_REFRESHS = 50;//ms
    private static Modele modele;
    private ArrayList<Forme2D> listeDesFormes;
    private ArrayList<Vecteur3D> listeDesForces;
    private Moment3D momentTotal;
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
    private Object verrouDescr = new Object();
    private boolean receivedDrawFFS = false;
    private Object verrouForce = new Object();
    private boolean receivedLift = false;
    private boolean displayedForcesMoment = false;
    private boolean displayedForme2D = false;
    private Object verrouTemporelForce = new Object();
    private long tempsDerniereDemandeForce;
    private Object verrouTemporelDescr =new Object();
    private long tempsDerniereDemandeDescr;
    public CommunicationService descriptionService;
    public CommunicationService getForcesMomentService;
    private String BUS = (System.getProperty("os.name").equals("Mac OS X")) ? "224.255.255.255:2010" : "127.255.255.255:2010"; //127.255.255.255:2010

    //      MESSAGES RECEIVED FROM IVY :

    private String INIT_FORME_2D_MSG = "^ShapeStart name=(.*)$";
    private String POINT_DE_LA_FORME = "^ShapePoint name=(.*) ptX=(.*) ptY=(.*) ptZ=(.*)$";
    private String FIN_DE_DESCRIPTION = "^Draw ffs$";
    private String FORCE = "Force name=(.*) applicationX=(.*) applicationY=(.*) applicationZ=(.*) normeX=(.*) normeY=(.*) normeZ=(.*) color=(.*)";
    private String MOMENT = "Moment name=(.*) normeX=(.*) normeY=(.*) normeZ=(.*)";

    //MESSAGES SENT THROUGH IVY :
    private String COMPUTE_DEMND = "StartComputation mass=%f xcg=%f vair=%f psi=%f theta=%f phi=%f alpha=%f betha=%f a0=%f trim=%f dl=%f dm=%f dn=%f dx=%f p=%f q=%f r=%f";
    private String DEMANDE_DESCR = "StartGettingShapes mass=%f xcg=%f vair=%f psi=%f theta=%f phi=%f alpha=%f betha=%f a0=%f trim=%f dl=%f dm=%f dn=%f";

    private static double VECTOR_SCALING = 10000;
    private  ArrayList<Forme3D> listeDesFormes3D;

    private Vue3D vue;

    public boolean isDisplayedForcesMoment() {
        return displayedForcesMoment;
    }

    public void setDisplayedForcesMoment(boolean displayedForcesMoment) {
        this.displayedForcesMoment = displayedForcesMoment;
    }

    public boolean isDisplayedForme2D() {
        return displayedForme2D;
    }

    public void setDisplayedForme2D(boolean displayedForme2D) {
        this.displayedForme2D = displayedForme2D;
    }

    private Modele() {
        this.tempsDerniereDemandeDescr = -1000;
        this.tempsDerniereDemandeForce = -1000;
        this.listeDesFormes = new ArrayList<Forme2D>();
        this.listeDesFormes3D= new ArrayList<Forme3D>();
        this.listeDesForces = new ArrayList<Vecteur3D>();
        this.momentTotal = new Moment3D(new Point3D(0, 0, 0), 30, 30, 30, 0, 0, 0, "mx_total", "my_total", "mz_total", 
                                        Configuration.getInstance().getCouleurMoment());
        this.xCentrage=new SimpleDoubleProperty(0.2555) ;
        this.vAir=new SimpleDoubleProperty(150) ;
        this.psi=new SimpleDoubleProperty(0) ;
        this.theta=new SimpleDoubleProperty(0) ;
        this.phi=new SimpleDoubleProperty(0) ;
        this.alpha=new SimpleDoubleProperty(0) ;
        this.beta=new SimpleDoubleProperty(0) ;
        this.a0=new SimpleDoubleProperty(3.031) ;
        this.trim=new SimpleDoubleProperty(0) ;
        this.dl=new SimpleDoubleProperty(0) ;
        this.dm=new SimpleDoubleProperty(0) ;
        this.dn=new SimpleDoubleProperty(0) ;
        this.dx=new SimpleDoubleProperty(0.5) ;
        this.p=new SimpleDoubleProperty(0) ;
        this.q=new SimpleDoubleProperty(0) ;
        this.r=new SimpleDoubleProperty(0) ;
        this.mass=new SimpleDoubleProperty(70000) ;
        this.radio = new Ivy("ViFA_IHM", "ViFA_IHM is ready !", null);
        try{
            this.radio.bindMsg(this.FORCE,(sender, strings) -> {
                String nom = strings[0];
                Point3D debut = new Point3D (Double.parseDouble(strings[1]),
                                             Double.parseDouble(strings[3]),
                                             Double.parseDouble(strings[2]));
                Point3D norme = new Point3D (Double.parseDouble(strings[4])/VECTOR_SCALING,
                                             Double.parseDouble(strings[6])/VECTOR_SCALING,
                                             Double.parseDouble(strings[5])/VECTOR_SCALING);
                Color color;
                switch (strings[7]){
                    case "yellow":
                        color = Color.YELLOW;
                        break;
                    case "red":
                        color = Color.RED;
                        break;
                    case "violet":
                        color = Color.DARKVIOLET;
                        break;
                    case "grey":
                        color = Color.WHITESMOKE;
                        break;
                    case "brown":
                        color = Color.BROWN;
                        break;
                    case "blue":
                        color = Color.BLUE;
                        break;
                    default:
                        color = Color.GREEN;
                };
                Vecteur3D force = new Vecteur3D(nom, debut, norme, color);

                if (nom.equals("LiftTotal")){
                    synchronized (verrouForce){
                        receivedLift = true;
                    }
                } else if (nom.equals("mg")){
                    //momentTotal.changeCenter(debut);
                    force.setOrigineMagnitude(force.getOrigine(), new Point3D(0, force.getMagnitude().magnitude(), 0));
                }
                updateForce(force);
            });
            this.radio.bindMsg(this.MOMENT, (sender, strings) -> {
                /*Point3D moment = new Point3D (Double.parseDouble(strings[1]),
                                                Double.parseDouble(strings[2]),
                                                Double.parseDouble(strings[3]));*/
                //setMomentTotal(moment)
                momentTotal.update(Double.parseDouble(strings[1]),
                        Double.parseDouble(strings[2]),
                        Double.parseDouble(strings[3]));
            });
            this.radio.bindMsg(this.INIT_FORME_2D_MSG, (client, nomDansTableau) -> {addForme(nomDansTableau[0]);
                                                                                    addForme3D(nomDansTableau[0]);
            });
            this.radio.bindMsg(this.POINT_DE_LA_FORME, (client, args) -> {
                String name = args[0];
                double x = Double.parseDouble(args[1]);
                double y = Double.parseDouble(args[2]);
                double z = Double.parseDouble(args[3]);
                addPointToForme(name, x, y, z);
            });
            this.radio.bindMsg(this.FIN_DE_DESCRIPTION, (IvyClient client, String[] args) -> {
                synchronized (verrouDescr){
                    receivedDrawFFS = true;
                }
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
        try {
            this.descriptionService = new CommunicationService(this.getClass().getMethod("getDescription"));
            this.getForcesMomentService = new CommunicationService (this.getClass().getMethod("getForcesAndMoment"));
        }
        catch (NoSuchMethodException e){
            System.out.println(e);
            System.out.println("Le daiveulopeur à ancaur fé une fôte d'aurtaugraff");
            System.exit (0);
        }

        descriptionService.setOnFailed(e -> {System.out.println("ThreadDescr a rencontré une erreur :\n");
        descriptionService.getException().printStackTrace();});

        descriptionService.setOnSucceeded(e -> {
            ArrayList<MeshView> items = modele.DrawFFS();
            if (items.size() > 0){
                vue.getGroupe2D().getChildren().clear();
                vue.getGroupe2D().getChildren().addAll(items);
                System.out.println("Formes surfaciques mises à jour avec succès.");
            }
            if (!modele.isDisplayedForme2D()){
                try {
                    vue.getGroupeAvion().getChildren().addAll(modele.DrawFus());
                    vue.getGroupeAvion().getChildren().addAll(modele.DrawNac());
                    modele.setDisplayedForme2D(true);
                    System.out.println("Formes volumiques affichées avec succès.");
                } catch (Exception e2){
                    System.out.println(e2);
                    System.out.println("Erreur lors de l'affichage des formes volumiques.");
                }
            }
        });

        getForcesMomentService.setOnSucceeded((e) -> {
            System.out.println("ThreadPrincipal a bien reçu les forces et le moment.");
            if (!modele.isDisplayedForcesMoment()) {
                synchronized (modele.getListeDesForces()) {
                    for (Vecteur3D azerty : modele.getListeDesForces()) {
                        if (azerty.getNom().equals("mg")) {
                            Point3D debut = azerty.getOrigine();
                            vue.getRepereTerrestre().getChildren().add(azerty);
                            vue.getGroupeAvion().getTransforms().set(0, new Translate(debut.getX(), 0, debut.getZ()));
                            vue.getGroupeForces().getTransforms().set(0, new Translate(-debut.getX(), 0, debut.getZ()));
                            azerty.setOrigineMagnitude(new Point3D(0, 0, 0), azerty.getMagnitude());
                        } else {
                            vue.getGroupeForces().getChildren().add(azerty);
                        }
                        azerty.refreshView();
                    }
                    vue.getRepereAvion().getChildren().add(modele.getMomentTotal());
                    modele.getMomentTotal().refreshView();
                    modele.setDisplayedForcesMoment(true);
                }
            }
            else {
                synchronized (modele.getListeDesForces()) {
                    for (Vecteur3D azerty : modele.getListeDesForces()) {
                        if (azerty.getNom().equals("mg")){
                            System.out.println(azerty);
                            Point3D debut = azerty.getOrigine();
                            System.out.println(debut);
                            if (debut.getX() != 0.0) {
                                vue.getGroupeAvion().getTransforms().set(0, new Translate(debut.getX(), 0, debut.getZ()));
                                vue.getGroupeForces().getTransforms().set(0, new Translate(-debut.getX(), 0, debut.getZ()));
                            }
                            azerty.setOrigineMagnitude(new Point3D(0, 0, 0), azerty.getMagnitude());
                        }
                        azerty.refreshView();
                    }
                    modele.getMomentTotal().refreshView();
                }
            }
        });
    }

    public static Modele getInstance (){
        if (modele==null){
            modele = new Modele();
        }
        return modele;
    }

 //GETTERS AND SETTERS


    public Vue3D getVue() {
        return vue;
    }

    public void setVue(Vue3D vue) {
        this.vue = vue;
    }

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
        boolean t =false;
        if(!(nom.equals("fuselage")|| nom.equals("naceller")||nom.equals("nacellel"))){
            for (Forme2D f:listeDesFormes){
                if (nom.equals(f.getNom())){
                    t=true;
                    f.setContour(new ArrayList<Point3D>());
                }
            }
            if (!t){
                listeDesFormes.add(new Forme2D(nom));
            }
        }
    }
    
    public void addForme3D (String nom){
        if (nom.equals("fuselage")|| nom.equals("naceller")||nom.equals("nacellel")){
        
            this.listeDesFormes3D.add(new Forme3D(nom));
        }
    }

    public void addPointToForme (String nom, double x, double y, double z){
        for (Forme2D f:listeDesFormes){
            if (f.getNom().equals(nom)){
                f.addPoint(new Point3D(x, y, z));
                break;
            }
        }
        for (Forme3D f:listeDesFormes3D){
            if (f.getNom().equals(nom)){
                f.addPoint(new org.fxyz3d.geometry.Point3D(x, z, y));
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
    
     public Forme3D getForme3D (String nom){
        for (Forme3D f:listeDesFormes3D){
            if (f.getNom().equals(nom)){
                return f;
            }
        };
        addForme3D(nom);
        return (getForme3D(nom));
    }

    public void getDescription() throws IvyException{
        long temps = (new Date()).getTime();
        long tps;
        synchronized (verrouTemporelDescr){
            tps = tempsDerniereDemandeDescr;
        }
        if (temps - tps > TEMPS_MIN_ENTRE_DEUX_REFRESHS){
            synchronized (verrouTemporelDescr){
                this.tempsDerniereDemandeDescr = temps;
            }
            try {
                Thread.sleep(50);
            } 
            catch (InterruptedException e1) {}
            this.receivedDrawFFS = false;
            System.out.println("Description en attente...");
            this.listeDesFormes.clear();
            this.listeDesFormes3D.clear();
            try {
                String msg=String.format(this.DEMANDE_DESCR,
                        mass.getValue(),
                        xCentrage.getValue(),
                        vAir.getValue(),
                        Math.toRadians(psi.getValue()),
                        Math.toRadians(theta.getValue()),
                        Math.toRadians(phi.getValue()),
                        Math.toRadians(alpha.getValue()),
                        Math.toRadians(beta.getValue()),
                        Math.toRadians(a0.getValue()),
                        trim.getValue(),
                        Math.toRadians(dl.getValue()),
                        Math.toRadians(dm.getValue()),
                        Math.toRadians(dn.getValue())).replace(',','.');
                this.radio.sendMsg(msg);
            }
            catch (IvyException e){
                e.printStackTrace();
                System.out.println(e);
            }
            boolean fin = false;
            while ((! fin) &((new Date()).getTime()-temps < 4000) ){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {}
                synchronized (verrouDescr){
                    fin = receivedDrawFFS;
                }
            }
            if (! this.receivedDrawFFS){//on a attendu 2secs, et on n'a pas la description
                IvyException e = new IvyException("Time out de l'attente de description");
                throw e;
                //System.out.println(e);
                //getDescription();
            }
            else{
                System.out.println("Description received");
            }
        }
        else{
            System.out.println("Mais, pas si vite...");
        }
    }

    public void getForcesAndMoment (){
        
        
        long temps = (new Date()).getTime();
        long tps;
        synchronized(verrouTemporelForce){
            tps = tempsDerniereDemandeForce;
        }
        if (temps - tps > TEMPS_MIN_ENTRE_DEUX_REFRESHS){
            System.out.println("Waiting for Forces and Moments");
            synchronized (verrouTemporelForce){
                this.tempsDerniereDemandeForce = temps;
            }
            try {
                this.receivedLift=false;
                try {
                    Thread.sleep(50);
                } 
                catch (InterruptedException e1) {}
                String msg=String.format(this.COMPUTE_DEMND,
                        mass.getValue(),
                        xCentrage.getValue(),
                        vAir.getValue(),
                        Math.toRadians(psi.getValue()),
                        Math.toRadians(theta.getValue()),
                        Math.toRadians(phi.getValue()),
                        Math.toRadians(alpha.getValue()),
                        Math.toRadians(beta.getValue()),
                        Math.toRadians(a0.getValue()),
                        trim.getValue(),
                        Math.toRadians(dl.getValue()),
                        Math.toRadians(dm.getValue()),
                        Math.toRadians(dn.getValue()),
                        dx.getValue(),
                        Math.toRadians(p.getValue()),
                        Math.toRadians(q.getValue()),
                        Math.toRadians(q.getValue())).replace(',','.');
                this.radio.sendMsg(msg);
            }
            catch (IvyException e){
                System.out.println(e);
            }
            boolean fin = false;
            while ((! fin) & ((new Date()).getTime()-temps < 4000)){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                }
                synchronized (verrouForce){
                    fin=receivedLift;
                }
            }
            if (! this.receivedLift){//on a attendu 4 secs, et on n'a pas les résulatats
                IvyException e = new IvyException("Time out de l'attente des forces et moments");
                System.out.println(e);
                //getForcesAndMoment();
            }
            else{
                System.out.println("Forces and moment received");
            }
        }
        else{
            System.out.println("Mais, pas si vite...");
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
    
     public ArrayList<Forme3D> getListeDesFormes3D(){
        return listeDesFormes3D;
    }

    public ArrayList<Vecteur3D> getListeDesForces() {
        return listeDesForces;
    }

    public void updateForce(Vecteur3D force) {
        String nom = force.getNom();
        boolean trouvee = false;
        for (Vecteur3D f:listeDesForces){
            if (f.getNom().equals(nom)){
                f.setOrigineMagnitude(force.getOrigine(), force.getMagnitude());
                f.setCouleur(force.getCouleur());
                trouvee = true;
                break;
            }
        }
        if (! trouvee){
            listeDesForces.add(force);
        }
    }

    public Moment3D getMomentTotal() {
        return momentTotal;
    }

    public void setMomentTotal(Moment3D momentTotal) {
        this.momentTotal = momentTotal;
    }

    public ArrayList<MeshView> DrawFFS() {
        ArrayList<MeshView> meshList = new ArrayList<MeshView>();
        for (Forme2D f :this.listeDesFormes) {
            f.setMesh();
            if (f.getNom().equals("htpr") || f.getNom().equals("htpl") || f.getNom().equals("wingr") || f.getNom().equals("wingl")) {
                f.setMaterial(new PhongMaterial(Configuration.getInstance().getCouleurAiles()));
            } 
            if (f.getNom().equals("vtp")) {
                f.setMaterial(new PhongMaterial(Configuration.getInstance().getCouleurFuselage()));
            } 
            if (f.getNom().equals("ruder") || f.getNom().equals("elevatorr") || f.getNom().equals("elevatorl") || f.getNom().equals("aileronr") || f.getNom().equals("aileronl")) {
                f.setMaterial(new PhongMaterial(Configuration.getInstance().getCouleurSurfaceControle()));
            }       
            meshList.add(f);
        }
        return meshList;
    }
    public ArrayList<TriangulatedMesh> DrawFus(){
        ArrayList<TriangulatedMesh> TrianglulatedMeshList = new ArrayList<TriangulatedMesh>();
        for (Forme3D f :this.listeDesFormes3D) {
            if (f.getNom().equals("fuselage")){
                TriangulatedMesh newMesh = f.setTriangulatedMesh();
                TrianglulatedMeshList.add(newMesh);
            }
        }
        return TrianglulatedMeshList;
    }
    public ArrayList<Cylinder> DrawNac(){
        ArrayList<Cylinder> CylinderList = new ArrayList<Cylinder>();
        for (Forme3D f :this.listeDesFormes3D) {
            if (!(f.getNom().equals("fuselage"))){
                Cylinder nac = f.setNacelles();
                CylinderList.add(nac);
            }
        }
        return CylinderList;
        
    }
      
    
}
