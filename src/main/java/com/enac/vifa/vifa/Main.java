package com.enac.vifa.vifa;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.*;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class Main extends Application {
    public Parent createContent() throws Exception {

        // Box
        Box testBox = new Box(5, 5, 5);
        testBox.setMaterial(new PhongMaterial(Color.RED));
        //testBox.setDrawMode(DrawMode.LINE);

        // Create and position camera
        SimpleIntegerProperty xprop = new SimpleIntegerProperty();
        xprop.set(0);
        SimpleIntegerProperty yprop = new SimpleIntegerProperty();
        yprop.set(0);
        SimpleIntegerProperty zoomprop = new SimpleIntegerProperty();
        zoomprop.set(15);
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.getTransforms().setAll (
                new Rotate(yprop.get(), Rotate.Y_AXIS),
                new Rotate(xprop.get(), Rotate.X_AXIS),
                new Translate(0, 0, -zoomprop.get()));
        xprop.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                camera.getTransforms().set(1, new Rotate(xprop.get(), Rotate.X_AXIS));
            }
        });

        yprop.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                camera.getTransforms().set(0, new Rotate(yprop.get(), Rotate.Y_AXIS));
            }
        });

        zoomprop.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                camera.getTransforms().set(2, new Translate(0, 0, -zoomprop.get()));
            }
        });

        // Build the Scene Graph
        Group root = new Group();
        root.getChildren().add(camera);
        root.getChildren().add(testBox);

        // Use a SubScene
        SubScene subScene = new SubScene(root, 400, 400);
        subScene.setFill(Color.ALICEBLUE);
        subScene.setCamera(camera);
        Group group = new Group();
        group.getChildren().add(subScene);
        // create camera control tool (test)
        HBox hbox = new HBox();
        Slider xsl = new Slider(0, 360, 1);
        xprop.bindBidirectional(xsl.valueProperty());
        Slider ysl = new Slider(0, 360, 1);
        yprop.bindBidirectional(ysl.valueProperty());
        Slider zoomsl = new Slider(1, 100, 1);
        zoomprop.bindBidirectional(zoomsl.valueProperty());
        hbox.getChildren().add(xsl);
        hbox.getChildren().add(ysl);
        hbox.getChildren().add(zoomsl);
        group.getChildren().add(hbox);
        return group;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //primaryStage.setResizable(false);
        Scene scene = new Scene(createContent());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}