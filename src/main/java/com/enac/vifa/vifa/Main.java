package com.enac.vifa.vifa;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.*;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import org.fxyz3d.geometry.Point3D;
import org.fxyz3d.shapes.composites.PolyLine3D;

import java.util.ArrayList;
import java.util.Arrays;

//import com.enac.vifa.vifa.Vue3D;

public class Main extends Application {
    public Parent createContent() throws Exception {
        // Box
        Box testBox = new Box(5, 5, 5);
        testBox.setMaterial(new PhongMaterial(Color.RED));
        //testBox.setDrawMode(DrawMode.LINE);

        ArrayList<Point3D> bruh_ = new ArrayList<Point3D>();
        bruh_.add(new Point3D(10, 10, 10));
        bruh_.add(new Point3D(20, 20, 20));
        bruh_.add(new Point3D(30, 30, 30));
        PolyLine3D bruh = new PolyLine3D(bruh_, 20, Color.GREEN);


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
        root.getChildren().add(bruh);

        // Use a SubScene
        SubScene subScene = new SubScene(root, 400, 400);
        subScene.setFill(Color.ALICEBLUE);
        subScene.setCamera(camera);
        Group group = new Group();
        group.getChildren().add(subScene);
        // create camera control tool (test)
        HBox hbox = new HBox();
        Slider xsl = new Slider(0, 360, 1);
        xprop.bind(xsl.valueProperty());
        //xprop.bindBidirectional(xsl.valueProperty());
        Slider ysl = new Slider(0, 360, 1);
        yprop.bind(ysl.valueProperty());
        //yprop.bindBidirectional(ysl.valueProperty());
        Slider zoomsl = new Slider(1, 100, 1);
        zoomprop.bind(zoomsl.valueProperty());
        zoomsl.setValue(50);
        //zoomprop.bindBidirectional(zoomsl.valueProperty());
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
