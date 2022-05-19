package com.enac.vifa.vifa.vues;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Knob extends Pane{
    SimpleDoubleProperty valueProp;
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
        this.setHeight(100);
        this.setWidth(100);
        Circle knob = new Circle(0,0,radius);
        knob.setTranslateX(3*this.getWidth()/4);
        knob.setTranslateY(this.getHeight()/2);
        knob.setFill(Color.BLACK);
        Circle curseur = new Circle(-0.9*radius*Math.cos(Math.PI*((value-min)/(max-min))),-0.9*radius*Math.sin(Math.PI*((value-min)/(max-min))),radius/10);
        curseur.setTranslateX(3*this.getWidth()/4);
        curseur.setTranslateY(this.getHeight()/2);
        curseur.setFill(Color.WHITE);
        getChildren().addAll(knob,curseur);
        setOnMouseDragged((mouseEvent -> {
            double val = (mouseEvent.getX()-3*radius/2)/radius*(max-min)/2;
            valueProp.set(val);
        }));
        valueProp.addListener(((observableValue, number, t1) -> {
            if (valueProp.get()>=min && valueProp.get()<=max) {
                curseur.setCenterX(-0.9*radius*Math.cos(Math.PI*((t1.doubleValue()-min)/(max-min))));
                curseur.setCenterY(-0.9*radius*Math.sin(Math.PI*((t1.doubleValue()-min)/(max-min))));
        }}));
    }

}
