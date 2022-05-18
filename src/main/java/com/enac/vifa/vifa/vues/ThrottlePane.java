/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.enac.vifa.vifa.vues;

import com.enac.vifa.vifa.Modele;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Priority;


/**
 *
 * @author hugocourtadon
 */
public class ThrottlePane extends ControllerPane {
    private final Label poussInfo;
    private final Slider pouss;

    protected void genericSliderListener(String labelText, Label label, Number newValue){
        String text = labelText + newValue.doubleValue();
        label.setText(text.substring(0, Math.min(5, text.length())));
    }

    public ThrottlePane(Vue3D vue){
        super(vue);
        Label poussIntitule = new Label("GAZ (%)");
        setRowIndex(poussIntitule, 0);
        poussInfo = new Label("0.5");
        setRowIndex(poussInfo,1);
        pouss = new Slider(0, 1,0);
        pouss.valueProperty().bindBidirectional(Modele.getInstance().getDxProperty());
        pouss.valueProperty().addListener(((observableValue, number, t1) -> {
            genericSliderListener("", poussInfo, t1);
            if (!resetting){
                Modele.getInstance().descriptionService.restart();
                Modele.getInstance().getForcesMomentService.restart();
            }
        }));
        setRowIndex(pouss,2);
        pouss.setOrientation(Orientation.VERTICAL);
        getChildren().addAll(poussIntitule, pouss,poussInfo);
        
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7;");
        setHalignment(pouss, HPos.CENTER);
        setHalignment(poussInfo, HPos.CENTER);
        setVgrow(pouss, Priority.ALWAYS);
        setMaxSize(50, 200);
    }
    @Override
    public void reset(){
        resetting = true;
        Modele.getInstance().setDx(0);
       
        resetting = false;
    }
    public String throtValue(){
        if (Modele.getInstance().getDx()==0d){
            return "IDLE";
        }
        if (Modele.getInstance().getDx()==1d){
            return "TOGA";
        }
        else{
          return Double.toString(Modele.getInstance().getDx());
        }
    }
}
