package com.enac.vifa.vifa;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class Main extends Application {
    public Parent createContent(Scene mainScene){
        // Box
        Box testBox = new Box(5, 0, 5);
        testBox.setMaterial(new PhongMaterial(Color.RED));

        Cylinder bruh = new Cylinder(5, 0, 6);
        bruh.setMaterial(new PhongMaterial(Color.BLUE));
        bruh.setTranslateY(5);

        float smallSize = 4.0f;
        float bigSize = 6.0f;
        float high = 3.0f;
        float depth = 1.0f;

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

        MeshView mm = new MeshView(m);

        Group group = new Group();
        Vue3D vue = new Vue3D(mainScene, new Group());
        group.getChildren().add(vue);
        vue.getRepereTerrestre().getChildren().add(testBox);

        vue.getRepereTerrestre().getChildren().add(mm);
        vue.getRepereAvion().getChildren().add(bruh);

        //vue.rotateRepereAvion(10, 10, 10);
        //vue.rotateRepereAero(10, 10);

        Label cml = new Label("Camera:");
        Label xrotl = new Label("X Rot: "+vue.getXrotprop());
        vue.xrotpropProperty().addListener(((observableValue, number, t1) -> {
            xrotl.setText("X Rot: "+number);
        }));
        Label yrotl = new Label("Y Rot: "+vue.getYrotprop());
        vue.yrotpropProperty().addListener(((observableValue, number, t1) -> {
            yrotl.setText("Y Rot: "+number);
        }));
        Label zooml = new Label("Zoom: "+vue.getZoomprop());
        vue.zoompropProperty().addListener(((observableValue, number, t1) -> {
            zooml.setText("Zoom: "+number);
        }));

        VBox vbox = new VBox(cml, xrotl, yrotl, zooml);
        vbox.setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7;");
        vbox.setTranslateX(10);
        vbox.setTranslateY(10);

        Label rpl = new Label("Repères:");
        Label psil = new Label("Psi: 0");
        Slider psis = new Slider(-183, 183, 0);
        psis.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotatePsi(number.intValue());
            psil.setText("Psi: "+number.intValue());
        }));
        Label phil = new Label("Phi: 0");
        Slider phis = new Slider(-183, 183, 0);
        phis.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotatePhi(number.intValue());
            phil.setText("Phi: "+number.intValue());
        }));
        Label thetal = new Label("Theta: 0");
        Slider thetas = new Slider(-183, 183, 0);
        thetas.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotateTheta(number.intValue());
            thetal.setText("Theta: "+number.intValue());
        }));

        Label alphal = new Label("Alpha: 0");
        Slider alphas = new Slider(-183, 183, 0);
        alphas.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotateAlpha(number.intValue());
            alphal.setText("Alpha: "+number.intValue());
        }));
        Label betal = new Label("Beta: 0");
        Slider betas = new Slider(-183, 183, 0);
        betas.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotateBeta(number.intValue());
            betal.setText("Beta: "+number.intValue());
        }));

        VBox repb = new VBox(rpl, psil, psis, phil, phis, thetal, thetas, alphal, alphas, betal, betas);
        repb.setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7;");
        repb.setTranslateY(100);
        repb.setTranslateX(10);

        group.getChildren().add(vbox);
        group.getChildren().add(repb);

        Modele test = new Modele();
        Thread test_th = new Thread(() -> {
            test.getDescription();
        });
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
