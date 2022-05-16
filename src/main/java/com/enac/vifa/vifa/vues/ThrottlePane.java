/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.enac.vifa.vifa.vues;

import com.enac.vifa.vifa.Modele;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import static javafx.scene.layout.GridPane.setColumnIndex;
import static javafx.scene.layout.GridPane.setRowIndex;

/**
 *
 * @author hugocourtadon
 */
public class ThrottlePane extends ControllerPane {
    private final Label poussInfo;
    private final Slider pouss;
    
    public ThrottlePane(Vue3D vue){
        super(vue);
        poussInfo = new Label("Poussée (de 0 à 1) :"+Modele.getInstance().getDx());
        setRowIndex(poussInfo,1);
        setColumnIndex(poussInfo, 1);
        pouss = new Slider(0, 1,0);
        pouss.valueProperty().bindBidirectional(Modele.getInstance().getDxProperty());
        pouss.valueProperty().addListener(((observableValue, number, t1) -> {
            genericSliderListener("Poussée: ", poussInfo, t1);
            if (!resetting){
                Modele.getInstance().descriptionService.restart();
                Modele.getInstance().getForcesMomentService.restart();
                poussInfo.setText("Poussée (de 0 à 1) :"+throtValue());
            }
        }));
        setRowIndex(pouss,1);
        setColumnIndex(pouss, 0);
        pouss.setOrientation(Orientation.VERTICAL);
        getChildren().addAll(pouss,poussInfo);
        
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7;");
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
