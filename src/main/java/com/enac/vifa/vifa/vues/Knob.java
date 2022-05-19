package com.enac.vifa.vifa.vues;

import com.enac.vifa.vifa.Configuration;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Knob extends Pane{
    SimpleDoubleProperty valueProp;
    SimpleDoubleProperty lastX;
    SimpleDoubleProperty lastY;
    SimpleDoubleProperty valAtLastPress;
    public double getValueProp() {
        return valueProp.get();
    }

    public SimpleDoubleProperty valueProperty() {
        return valueProp;
    }

    public void setAilProp(double ailProp) {
        this.valueProp.set(ailProp);
    }
    public Knob(double min, double max, double value, double radius){
        valueProp = new SimpleDoubleProperty(value);
        lastX = new SimpleDoubleProperty();
        lastY = new SimpleDoubleProperty();
        valAtLastPress = new SimpleDoubleProperty();
        this.setHeight(radius*2);
        this.setWidth(radius*2);
        Circle knob = new Circle(0,0,radius);
        knob.setTranslateX(3*this.getWidth()/4);
        knob.setTranslateY(this.getHeight()/2);
        knob.setFill(Color.BLACK);
        Circle curseur = new Circle(-0.9*radius*Math.cos(Math.PI*179/90*((value-min)/(max-min))),-0.9*radius*Math.sin(Math.PI*179/90*((value-min)/(max-min))),radius/10);
        curseur.setTranslateX(3*this.getWidth()/4);
        curseur.setTranslateY(this.getHeight()/2);
        curseur.setFill(Color.WHITE);
        getChildren().addAll(knob,curseur);
        setOnMousePressed((mouseEvent -> {
            lastX.set(mouseEvent.getX());
            lastY.set(mouseEvent.getY());
            valAtLastPress.set(valueProp.get());
        }));
        double sensibilite = Configuration.getInstance().getReducSensibilite();
        setOnMouseDragged((mouseEvent -> {
            valueProp.set(Double.min(Double.max(valAtLastPress.get()+(mouseEvent.getX()+mouseEvent.getY()-lastX.get()-lastY.get())/radius*(max-min)/sensibilite,min),max));
        }));
        valueProp.addListener(((observableValue, number, t1) -> {
            if (valueProp.get()>=min && valueProp.get()<=max) {
                curseur.setCenterX(-0.9*radius*Math.cos(Math.PI*179/90*((t1.doubleValue()-min)/(max-min))));
                curseur.setCenterY(-0.9*radius*Math.sin(Math.PI*179/90*((t1.doubleValue()-min)/(max-min))));
        }}));
    }

}
