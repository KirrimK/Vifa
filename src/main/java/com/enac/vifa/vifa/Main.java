package com.enac.vifa.vifa;

import java.util.ArrayList;

import com.enac.vifa.vifa.formes.Vecteur3D;
import com.enac.vifa.vifa.vues.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.ComboBox;
import javafx.scene.shape.*;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class Main extends Application {
    public Parent createContent(Scene mainScene){
        // Box
        Box centerBox = new Box(1, 1, 1);

        Group group = new Group();
        Vue3D vue = new Vue3D(mainScene, new Group());
        group.getChildren().add(vue);
        vue.getRepereTerrestre().getChildren().add(centerBox);

        RepereControllerPane repb = new RepereControllerPane(vue);
        repb.setTranslateY(150);
        repb.setTranslateX(10);

        PQRPane pqrb = new PQRPane(vue);
        pqrb.setTranslateY(350);
        pqrb.setTranslateX(10);
        
        FormPane form = new FormPane(vue);
        form.setTranslateY(550);
        form.setTranslateX(10);

        CameraInfoPane camInfo = new CameraInfoPane(vue);
        camInfo.setTranslateX(10);
        camInfo.setTranslateY(10);

        GouverneControllerPane gouvCtl = new GouverneControllerPane(vue);
        gouvCtl.setTranslateX(250);
        gouvCtl.setTranslateY(10);


       

        ArrayList<ControllerPane> stonks = new ArrayList<>();
        stonks.add(repb);
        stonks.add(pqrb);
        stonks.add(gouvCtl);
        stonks.add(form);

        ResetButton resetb = new ResetButton(stonks);
        resetb.setTranslateX(150);
        resetb.setTranslateY(10);

        ObservableList<Mode> modeList = FXCollections.observableArrayList(Mode.AERO,Mode.AVION,Mode.ATTITUDE);
        ComboBox modeSelect = new ComboBox(modeList);
        modeSelect.setValue(Mode.ATTITUDE);
        modeSelect.setTranslateX(10);
        modeSelect.setTranslateY(480);
        modeSelect.setOnAction(e->vue.setMode((Mode)modeSelect.getValue()));

        group.getChildren().add(camInfo);
        group.getChildren().add(repb);
        group.getChildren().add(pqrb);
        group.getChildren().add(resetb);
        group.getChildren().add(gouvCtl);

        group.getChildren().add(form);

        group.getChildren().add(modeSelect);


        Modele modele = Modele.getInstance();
        modele.setVue(vue);
        modele.descriptionService.setOnFailed(e -> {
            System.out.println(modele.descriptionService.getException());
            modele.descriptionService.getException().printStackTrace();
            System.out.println("ThreadDescr a rencontré une erreur");
        });
        modele.descriptionService.setOnSucceeded(e -> {
            System.out.println("ThreadPrincipal a bien reçu la descr.");
            if (modele.isDisplayedForme2D()){
                ArrayList<MeshView> ytreza = modele.DrawFFS();
                if (ytreza.size() > 0){
                    vue.getGroupe2D().getChildren().clear();
                    vue.getGroupe2D().getChildren().addAll(ytreza);
                }
            } else {
                modele.setDisplayedForme2D(true);
                vue.getGroupe2D().getChildren().addAll(modele.DrawFFS());
                vue.getGroupeAvion().getChildren().addAll(modele.DrawFus());
                vue.getGroupeAvion().getChildren().addAll(modele.DrawNac());
            }

           });

        modele.descriptionService.start();

        modele.getForcesMomentService.setOnSucceeded((e) -> {
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
                            azerty.setOrigineMagnitude(new Point3D(0, 0, 0), azerty.getMagnitude());
                        }
                        azerty.refreshView();
                    }
                    modele.getMomentTotal().refreshView();
                }
            }
        });
        modele.getForcesMomentService.start();
        return group;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //primaryStage.setResizable(false);
        Configuration conf = Configuration.getInstance();
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
