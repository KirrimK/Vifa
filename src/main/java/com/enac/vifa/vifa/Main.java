package com.enac.vifa.vifa;

import com.enac.vifa.vifa.formes.Vecteur3D;
import com.enac.vifa.vifa.vues.CameraInfoPane;
import com.enac.vifa.vifa.vues.GouverneControllerPane;
import com.enac.vifa.vifa.vues.RepereControllerPane;
import com.enac.vifa.vifa.vues.Vue3D;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

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
        float s = smallSize ;
        float b = bigSize;
        float h = high;
        float d = depth;

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

        Group group = new Group();
        Vue3D vue = new Vue3D(mainScene, new Group());
        group.getChildren().add(vue);
        vue.getRepereTerrestre().getChildren().add(testBox);
        // avion.setTranslateX(25);

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

        GouverneControllerPane gouvCtl = new GouverneControllerPane();
        gouvCtl.setTranslateX(250);
        gouvCtl.setTranslateY(10);

        Button resetb = new Button("RESET");
        resetb.setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7;");
        resetb.setOnAction((actionEvent) -> {
            repb.reset();
            gouvCtl.reset();
        });
        resetb.setTranslateX(150);
        resetb.setTranslateY(10);

        group.getChildren().add(camInfo);
        group.getChildren().add(repb);
        group.getChildren().add(resetb);
        group.getChildren().add(gouvCtl);

        Modele modele = Modele.getInstance();
        Group avion = new Group(new AmbientLight(Color.WHITESMOKE));
        avion.getTransforms().addAll(new Rotate(90, Rotate.X_AXIS), new Rotate(180, Rotate.Z_AXIS));
        vue.getRepereAvion().getChildren().add(avion);
        Task<Integer> descrTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                modele.getDescription();
                return 1;
            }
        };
        descrTask.setOnFailed(e -> System.out.println("ThreadDescr a rencontré une erreur"));
        descrTask.setOnSucceeded(e -> {
            System.out.println("ThreadPrincipal a bien reçu la descr.");
            avion.getChildren().addAll(modele.DrawFFS());
           });
        Thread test_th = new Thread(descrTask);
        test_th.start();



        
        Task<Integer> computeTask = new Task<Integer>() {
            @Override
            protected Integer call(){
                synchronized(modele.getListeDesForces()) {
                    modele.getForcesAndMoment();
                }
                return 1;
            }
        };

        computeTask.setOnSucceeded((e) -> {
            System.out.println("ThreadPrincipal a bien reçu les forces et le moment.");
            synchronized (modele.getListeDesForces()){
                for(Vecteur3D azerty: modele.getListeDesForces()){
                    vue.getRepereAvion().getChildren().add(azerty);
                }
            }
        });
        Thread test_th2 = new Thread(computeTask);
        test_th2.start();
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
