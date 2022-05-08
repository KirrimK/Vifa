package com.enac.vifa.vifa;

import com.enac.vifa.vifa.formes.Vecteur3D;
import com.enac.vifa.vifa.vues.CameraInfoPane;
import com.enac.vifa.vifa.vues.GouverneControllerPane;
import com.enac.vifa.vifa.vues.RepereControllerPane;
import com.enac.vifa.vifa.vues.Vue3D;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class Main extends Application {
    public Parent createContent(Scene mainScene){
        // Box
        Box centerBox = new Box(1, 1, 1);

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
        modele.descriptionService.setOnFailed(e -> System.out.println("ThreadDescr a rencontré une erreur"));
        modele.descriptionService.setOnSucceeded(e -> {
            System.out.println("ThreadPrincipal a bien reçu la descr.");
            if (modele.isDisplayedForme2D()){
                modele.DrawFFS();
            } else {
                modele.setDisplayedForme2D(true);
                avion.getChildren().addAll(modele.DrawFFS());
                avion.getChildren().addAll(modele.DrawFus());
            }

           });
        modele.descriptionService.start();

        modele.getForcesMomentService.setOnSucceeded((e) -> {
            System.out.println("ThreadPrincipal a bien reçu les forces et le moment.");
            if (!modele.isDisplayedForcesMoment()){
                synchronized (modele.getListeDesForces()){
                    for(Vecteur3D azerty: modele.getListeDesForces()){
                        vue.getRepereAvion().getChildren().add(azerty);
                    }
                    modele.setDisplayedForcesMoment(true);
                /*if (!modele.isDisplayedMomentGeneral()){
                    modele.setDisplayedMomentGeneral(true);
                    vue.getRepereAvion().getChildren().add(modele.getMomentTotal());
                    System.out.println("moment total: " + modele.getMomentTotal().getMx() + " " + modele.getMomentTotal().getMy() + " " +modele.getMomentTotal().getMz());
                }*/
                }
            }
        });
        modele.getForcesMomentService.start();
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
