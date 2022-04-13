package com.enac.vifa.vifa;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import org.fxyz3d.geometry.Point3D;
import org.fxyz3d.shapes.composites.PolyLine3D;
import org.fxyz3d.shapes.polygon.PolygonMesh;
import org.fxyz3d.shapes.primitives.SpringMesh;
import org.fxyz3d.shapes.primitives.Surface3DMesh;
import org.fxyz3d.shapes.primitives.TexturedMesh;
import org.fxyz3d.shapes.primitives.TriangulatedMesh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import static java.lang.Double.max;

//import com.enac.vifa.vifa.Vue3D;

public class Main extends Application {
    public Parent createContent(Scene mainScene) throws Exception {
        // Box
        Box testBox = new Box(5, 0, 5);
        testBox.setMaterial(new PhongMaterial(Color.RED));
        //testBox.setDrawMode(DrawMode.LINE);

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

        /*ArrayList<Point3D> bruh_ = new ArrayList<Point3D>();
        bruh_.add(new Point3D(0, 0, 0));
        bruh_.add(new Point3D(0, 20, 0));
        bruh_.add(new Point3D(0, 0, 20));
        bruh_.add(new Point3D(0, 0, 0));

        TexturedMesh spring = new TriangulatedMesh(bruh_);
        spring.setCullFace(CullFace.NONE);
        //spring.setTextureModeVertices3D(1530, p -> p.f);
        spring.setDrawMode(DrawMode.FILL);*/

        // Create and position camera
        SimpleIntegerProperty xprop = new SimpleIntegerProperty();
        xprop.set(0);
        SimpleIntegerProperty yprop = new SimpleIntegerProperty();
        yprop.set(0);
        SimpleDoubleProperty zoomprop = new SimpleDoubleProperty();
        zoomprop.set(15);
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setFarClip(300.0f);
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
        root.getChildren().add(mm);
        //root.getChildren().add(spring);

        // Use a SubScene
        SubScene subScene = new SubScene(root, 400, 400, true, SceneAntialiasing.BALANCED);
        subScene.heightProperty().bind(mainScene.heightProperty());
        subScene.widthProperty().bind((mainScene.widthProperty()));
        subScene.setFill(Color.ALICEBLUE);
        subScene.setCamera(camera);

        float MIN_VALUE = 25;
        subScene.setOnScroll((event) -> {
            zoomprop.set(max(event.getDeltaY()/2 + zoomprop.get(), MIN_VALUE));
        });

        Group group = new Group();
        group.getChildren().add(subScene);
        // create camera control tool (test)
        HBox hbox = new HBox();
        Slider xsl = new Slider(-180, 180, 1);
        xprop.bind(xsl.valueProperty());
        Slider ysl = new Slider(-180, 180, 1);
        yprop.bind(ysl.valueProperty());
        xsl.setValue(0);
        ysl.setValue(0);
        zoomprop.set(50);
        hbox.getChildren().add(xsl);
        hbox.getChildren().add(ysl);
        group.getChildren().add(hbox);
        return group;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //primaryStage.setResizable(false);
        Scene scene = new Scene(new Group(), 800, 800);
        scene.setRoot(createContent(scene));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
