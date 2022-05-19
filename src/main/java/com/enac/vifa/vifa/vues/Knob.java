package com.enac.vifa.vifa.vues;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Knob extends Pane{
    SimpleDoubleProperty valueProp;
    SimpleDoubleProperty lastX;
    SimpleDoubleProperty valAtLastX;
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
        valAtLastX = new SimpleDoubleProperty();
        this.setHeight(100);
        this.setWidth(100);
        Circle knob = new Circle(0,0,radius);
        knob.setTranslateX(3*this.getWidth()/4);
        knob.setTranslateY(this.getHeight()/2);
        knob.setFill(Color.BLACK);
        Circle curseur = new Circle(-0.9*radius*Math.cos(Math.PI*4/3*((value-min)/(max-min))),-0.9*radius*Math.sin(Math.PI*4/3*((value-min)/(max-min))),radius/10);
        curseur.setTranslateX(3*this.getWidth()/4);
        curseur.setTranslateY(this.getHeight()/2);
        curseur.setFill(Color.WHITE);
        getChildren().addAll(knob,curseur);
        setOnMousePressed((mouseEvent -> {
            lastX.set(mouseEvent.getX());
            valAtLastX.set(valueProp.get());
        }));
        setOnMouseDragged((mouseEvent -> {
            valueProp.set(Double.min(Double.max(valAtLastX.get()+(mouseEvent.getX()-lastX.get())/radius*(max-min),min),max));
        }));
        valueProp.addListener(((observableValue, number, t1) -> {
            if (valueProp.get()>=min && valueProp.get()<=max) {
                curseur.setCenterX(-0.9*radius*Math.cos(Math.PI*4/3*((t1.doubleValue()-min)/(max-min))));
                curseur.setCenterY(-0.9*radius*Math.sin(Math.PI*4/3*((t1.doubleValue()-min)/(max-min))));
        }}));
    }

}
