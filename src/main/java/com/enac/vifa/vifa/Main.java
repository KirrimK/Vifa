package com.enac.vifa.vifa;

import java.util.List;

import com.enac.vifa.vifa.vues.CameraInfoPane;
import com.enac.vifa.vifa.vues.RepereControllerPane;
import com.enac.vifa.vifa.vues.Vue3D;
import earcut4j.Earcut;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import com.enac.vifa.vifa.Forme3D;

public class Main extends Application {
    public Parent createContent(Scene mainScene){
        // Box
        Box testBox = new Box(5, 0, 5);
        testBox.setMaterial(new PhongMaterial(Color.RED));

        Cylinder bruh = new Cylinder(5, 0, 6);
        bruh.setMaterial(new PhongMaterial(Color.BLUE));
        bruh.setTranslateY(5);

        float smallSize = 40.0f;
        float bigSize = 60.0f;
        float high = 30.0f;
        float depth = 10.0f;

        TriangleMesh m = new TriangleMesh();
        float s = ((float)smallSize) ;
        float b = ((float)bigSize);
        float h = ((float)high);
        float d = ((float)depth);

        //create Points
        m.getPoints().addAll(
                -s/2 , -h/2 ,  d/2,	// A = 0
                s/2 , -h/2 ,  d/2,	// B = 1
                -b/2 ,  h/2 ,  d/2,	// C = 2
                b/2 ,  h/2 ,  d/2,	// D = 3
                -s/2 , -h/2 , -d/2,	// E = 4
                s/2 , -h/2 , -d/2,	// F = 5
                -b/2 ,  h/2 , -d/2,	// G = 6
                b/2 ,  h/2 , -d/2	// H = 7
        );

        m.getTexCoords().addAll(0,0);

        m.getFaces().addAll(
                0 , 0 , 1 , 0 , 3 , 0 ,		// A-B-D
                0 , 0 , 3 , 0 , 2 , 0 , 	// A-D-C
                0 , 0 , 2 , 0 , 6 , 0 ,		// A-C-G
                0 , 0 , 6 , 0 , 4 , 0 , 	// A-G-E
                0 , 0 , 4 , 0 , 1 , 0 ,		// A-E-B
                1 , 0 , 4 , 0 , 5 , 0 , 	// B-E-F
                1 , 0 , 5 , 0 , 7 , 0 ,		// B-F-H
                1 , 0 , 7 , 0 , 3 , 0 ,		// B-H-D
                3 , 0 , 7 , 0 , 6 , 0 ,		// D-H-G
                3 , 0 , 6 , 0 , 2 , 0 ,		// D-G-C
                6 , 0 , 7 , 0 , 5 , 0 ,		// G-H-F
                6 , 0 , 5 , 0 , 4 , 0		// G-F-E
        );

        Forme2D vtp = new Forme2D("vtp");
        vtp.addPoint(new Point3D(30.92461340000062f,0.0f,4.0f));
        vtp.addPoint(new Point3D(37.55f,0.0f,4.0f));
        vtp.addPoint(new Point3D(37.58215536893538f,0.0f,4.335410196624968f));
        vtp.addPoint(new Point3D(35.33863383151059f,0.0f,4.335410196624968f));
        vtp.addPoint(new Point3D(37.381497520207795f,0.0f,10.70820393249937f));
        vtp.addPoint(new Point3D(35.87422206870794f,0.0f,10.70820393249937f));
        MeshView mvtp = vtp.Draw2D();
        mvtp.setCullFace(CullFace.NONE);
        mvtp.setMaterial(new PhongMaterial(Color.GREY));
        Forme2D ruder = new Forme2D("ruder");
        ruder.addPoint(new Point3D(35.33863383151059f,0.0f,4.335410196624968f));
        ruder.addPoint(new Point3D(37.58215536893538f,0.0f,4.335410196624968f));
        ruder.addPoint(new Point3D(38.193107378707715f,0.0f,10.70820393249937f));
        ruder.addPoint(new Point3D(37.381497520207795f,0.0f,10.70820393249937f));
        MeshView mruder = ruder.Draw2D();
        mruder.setCullFace(CullFace.NONE);
        mruder.setMaterial(new PhongMaterial(Color.ORANGE));
        Forme2D htpr = new Forme2D("htpr");
        htpr.addPoint(new Point3D(31.812246894507528f,0.0f,3.2f));
        htpr.addPoint(new Point3D(37.55f,0.0f,3.2f));
        htpr.addPoint(new Point3D(37.8849962632144f,1.5491933384829668f,3.33553685472714f));
        htpr.addPoint(new Point3D(36.13785044259194f,1.5491933384829668f,3.33553685472714f));
        htpr.addPoint(new Point3D(38.52210656064916f,7.745966692414834f,3.8776842736356962f));
        htpr.addPoint(new Point3D(37.21676772914963f,7.745966692414834f,3.8776842736356962f));
        MeshView mhtpr = htpr.Draw2D();
        mhtpr.setCullFace(CullFace.NONE);
        mhtpr.setMaterial(new PhongMaterial(Color.GREY));
        Forme2D htpl = new Forme2D("htpl");
        htpl.addPoint(new Point3D(31.812246894507528f,0.0f,3.2f));
        htpl.addPoint(new Point3D(37.55f,0.0f,3.2f));
        htpl.addPoint(new Point3D(37.8849962632144f,-1.5491933384829668f,3.33553685472714f));
        htpl.addPoint(new Point3D(36.13785044259194f,-1.5491933384829668f,3.33553685472714f));
        htpl.addPoint(new Point3D(38.52210656064916f,-7.745966692414834f,3.8776842736356962f));
        htpl.addPoint(new Point3D(37.21676772914963f,-7.745966692414834f,3.8776842736356962f));
        MeshView mhtpl = htpl.Draw2D();
        mhtpl.setCullFace(CullFace.NONE);
        mhtpl.setMaterial(new PhongMaterial(Color.GREY));
        Forme2D elevatorr = new Forme2D("elevatorr");
        elevatorr.addPoint(new Point3D(36.13785044259194f,1.5491933384829668f,3.33553685472714f));
        elevatorr.addPoint(new Point3D(37.8849962632144f,1.5491933384829668f,3.33553685472714f));
        elevatorr.addPoint(new Point3D(39.22498131607199f,7.745966692414834f,3.8776842736356962f));
        elevatorr.addPoint(new Point3D(38.52210656064916f,7.745966692414834f,3.8776842736356962f));
        MeshView melevatorr = elevatorr.Draw2D();
        melevatorr.setCullFace(CullFace.NONE);
        melevatorr.setMaterial(new PhongMaterial(Color.ORANGE));
        Forme2D elevatorl = new Forme2D("elevatorl");
        elevatorl.addPoint(new Point3D(36.13785044259194f,-1.5491933384829668f,3.33553685472714f));
        elevatorl.addPoint(new Point3D(37.8849962632144f,-1.5491933384829668f,3.33553685472714f));
        elevatorl.addPoint(new Point3D(39.22498131607199f,-7.745966692414834f,3.8776842736356962f));
        elevatorl.addPoint(new Point3D(38.52210656064916f,-7.745966692414834f,3.8776842736356962f));
        MeshView melevatorl = elevatorl.Draw2D();
        melevatorl.setCullFace(CullFace.NONE);
        melevatorl.setMaterial(new PhongMaterial(Color.ORANGE));
        Forme2D wingr = new Forme2D("wingr");
        wingr.addPoint(new Point3D(12.372383077410786f,0.0f,0.6f));
        wingr.addPoint(new Point3D(20.924742818608365f,0.0f,0.6f));
        wingr.addPoint(new Point3D(23.46826764257895f,12.16038650701531f,1.6638959634574488f));
        wingr.addPoint(new Point3D(21.934188114001635f,12.16038650701531f,1.6638959634574488f));
        wingr.addPoint(new Point3D(23.781620977761218f,18.708286933869708f,2.154924869668579f));
        wingr.addPoint(new Point3D(22.699767997109873f,18.708286933869708f,2.2367630207037674f));
        MeshView mwingr = wingr.Draw2D(); 
        mwingr.setCullFace(CullFace.NONE);
        mwingr.setMaterial(new PhongMaterial(Color.GREY));
        Forme2D wingl = new Forme2D("wingl");
        wingl.addPoint(new Point3D(12.372383077410786f,0.0f,0.6f));
        wingl.addPoint(new Point3D(20.924742818608365f,0.0f,0.6f));
        wingl.addPoint(new Point3D(23.46826764257895f,-12.16038650701531f,1.6638959634574488f));
        wingl.addPoint(new Point3D(21.934188114001635f,-12.16038650701531f,1.6638959634574488f));
        wingl.addPoint(new Point3D(23.781620977761218f,-18.708286933869708f,2.154924869668579f));
        wingl.addPoint(new Point3D(22.699767997109873f,-18.708286933869708f,2.2367630207037674f));
        MeshView mwingl = wingl.Draw2D(); 
        mwingl.setCullFace(CullFace.NONE);
        mwingl.setMaterial(new PhongMaterial(Color.GREY));
        Forme2D aileronr = new Forme2D("aileronr");
        aileronr.addPoint(new Point3D(21.934188114001635f,12.16038650701531f,1.6638959634574488f));
        aileronr.addPoint(new Point3D(23.46826764257895f,12.16038650701531f,1.6638959634574488f));
        aileronr.addPoint(new Point3D(24.642202176719223f,18.708286933869708f,2.154924869668579f));
        aileronr.addPoint(new Point3D(23.781620977761218f,18.708286933869708f,2.154924869668579f));
        MeshView maileronr = aileronr.Draw2D();
        maileronr.setCullFace(CullFace.NONE);
        maileronr.setMaterial(new PhongMaterial(Color.ORANGE));
        Forme2D aileronl = new Forme2D("aileronl");
        aileronl.addPoint(new Point3D(21.934188114001635f,-12.16038650701531f,1.6638959634574488f));
        aileronl.addPoint(new Point3D(23.46826764257895f,-12.16038650701531f,1.6638959634574488f));
        aileronl.addPoint(new Point3D(24.642202176719223f,-18.708286933869708f,2.154924869668579f));
        aileronl.addPoint(new Point3D(23.781620977761218f,-18.708286933869708f,2.154924869668579f));
        MeshView maileronl = aileronl.Draw2D();
        maileronl.setCullFace(CullFace.NONE);
        maileronl.setMaterial(new PhongMaterial(Color.ORANGE));
        /*Cylinder fuselage = new Cylinder(25,1000);
        fuselage.setRotate(90);
        fuselage.setMaterial(new PhongMaterial(Color.GREY));*/
        
       
       
        
       
        
        


        Group group = new Group();
        Vue3D vue = new Vue3D(mainScene, new Group());
        group.getChildren().add(vue);
        vue.getRepereTerrestre().getChildren().add(testBox);

        Group avion = new Group(mvtp,mruder,mhtpr,mhtpl,melevatorr,melevatorl,mwingr,mwingl,maileronr,maileronl);
        avion.getTransforms().addAll(new Rotate(90, Rotate.X_AXIS), new Rotate(180, Rotate.Z_AXIS));
        vue.getRepereAvion().getChildren().add(avion);


        vue.getRepereAvion().getChildren().add(bruh);
        //vue.getRepereAvion().getChildren().add(fuselage);

        //vue.rotateRepereAvion(10, 10, 10);
        //vue.rotateRepereAero(10, 10);

        RepereControllerPane repb = new RepereControllerPane(vue);
        repb.setTranslateY(150);
        repb.setTranslateX(10);

        CameraInfoPane camInfo = new CameraInfoPane(vue);
        camInfo.setTranslateX(10);
        camInfo.setTranslateY(10);

        group.getChildren().add(camInfo);
        group.getChildren().add(repb);

        Modele modele = Modele.getInstance();
        Thread test_th = new Thread(modele::getDescription);
        test_th.start();
        return group;
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        //primaryStage.setResizable(false);
        Scene scene = new Scene(new Group(), 800, 800);
        scene.setRoot(createContent(scene));
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest((windowEvent -> {
            System.exit(0);
        }));
    }

    public static void main(String[] args) {
        launch();
    }
}
