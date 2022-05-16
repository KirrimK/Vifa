package com.enac.vifa.vifa;

import java.util.ArrayList;

import com.enac.vifa.vifa.vues.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.*;
import javafx.scene.control.ComboBox;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class Main extends Application {
    public Parent createContent(Scene mainScene){
        // Box
        Box centerBox = new Box(0.1, 0.1, 0.1);

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
        form.setTranslateY(10);
        form.setTranslateX(150);

        CameraInfoPane camInfo = new CameraInfoPane(vue);
        camInfo.setTranslateX(10);
        camInfo.setTranslateY(10);

        GouverneControllerPane gouvCtl = new GouverneControllerPane(vue);
        gouvCtl.setTranslateX(700);
        gouvCtl.setTranslateY(480);

        ArrayList<ControllerPane> stonks = new ArrayList<>();
        stonks.add(repb);
        stonks.add(pqrb);
        stonks.add(gouvCtl);
        stonks.add(form);

        ResetButton resetb = new ResetButton(stonks);
        resetb.setTranslateX(420);
        resetb.setTranslateY(650);

        ObservableList<Mode> modeList = FXCollections.observableArrayList(Mode.AERO,Mode.AVION,Mode.ATTITUDE);
        ComboBox<Mode> modeSelect = new ComboBox<Mode>(modeList);
        modeSelect.setValue(Configuration.getInstance().getMode());
        modeSelect.setTranslateX(10);
        modeSelect.setTranslateY(480);
        modeSelect.setOnAction(e->vue.setMode(modeSelect.getValue()));

        group.getChildren().add(camInfo);
        group.getChildren().add(repb);
        group.getChildren().add(pqrb);
        group.getChildren().add(resetb);
        group.getChildren().add(gouvCtl);

        group.getChildren().add(form);

        group.getChildren().add(modeSelect);

        Modele modele = Modele.getInstance();
        modele.setVue(vue);

        modele.descriptionService.start();

        modele.getForcesMomentService.start();
        return group;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //primaryStage.setResizable(false);
        Scene scene = new Scene(new Group(), 900, 700);
        scene.setRoot(createContent(scene));
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest((windowEvent -> {
            System.exit(0);
        }));
    }

    public static void main(String[] args) {
        Configuration conf = Configuration.getInstance();
        System.out.println(conf.getLaunchMessage());
        launch();
    }
}
