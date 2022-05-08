package com.enac.vifa.vifa;

import com.enac.vifa.vifa.formes.Vecteur3D;
import com.enac.vifa.vifa.vues.CameraInfoPane;
import com.enac.vifa.vifa.vues.GouverneControllerPane;
import com.enac.vifa.vifa.vues.RepereControllerPane;
import com.enac.vifa.vifa.vues.Vue3D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
//import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import org.fxyz3d.geometry.Point3D;
import org.fxyz3d.shapes.primitives.TriangulatedMesh;

public class Main extends Application {
    public Parent createContent(Scene mainScene){
        // Box
        Box centerBox = new Box(1, 1, 1);
        
        List<Point3D> points = new ArrayList<>(Arrays.asList(
            new Point3D(0,   0, 0),
            new Point3D(0, 100, 20), new Point3D(60, 100, 30),
            new Point3D(60, 60, 20), new Point3D(40, 60,  50),
            new Point3D(40,  0, 40), new Point3D( 0,  0,  0)));
        TriangulatedMesh customShape = new TriangulatedMesh(points, 50);
        customShape.setLevel(0);
        customShape.setCullFace(CullFace.NONE);
        
        
        
        Group group = new Group();
        Vue3D vue = new Vue3D(mainScene, new Group());
        group.getChildren().add(vue);
        vue.getRepereTerrestre().getChildren().add(centerBox);
        // avion.setTranslateX(25);

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
            avion.getChildren().addAll(modele.DrawFus());
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
