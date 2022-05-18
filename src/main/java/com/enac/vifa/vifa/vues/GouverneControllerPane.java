package com.enac.vifa.vifa.vues;

import com.enac.vifa.vifa.Modele;

import javafx.beans.binding.Bindings;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Priority;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;


/**
 * Panneau permettant d'actionner les gouvernes de l'avion.
 */
public class GouverneControllerPane extends ControllerPane {

    private final Label profInfo;
    private final Slider prof;
    private final TextField profs;
    private final Label dirInfo;
    private final Slider dir;
    private final TextField dirs;
    private final Label ailInfo;
    private final Slider ailerons;
    private final TextField ails;


    protected void genericSliderListener(String labelText, Label label, Number newValue){
        String text = labelText + newValue.doubleValue();
        label.setText(text.substring(0, Math.min(22, text.length())));
    }
    public GouverneControllerPane(Vue3D vue){
        super(vue);
        profInfo = new Label("Profondeur (deg):");
        setRowIndex(profInfo, 2);
        setColumnIndex(profInfo, 1);
        profs = new TextField(Double.toString(Modele.getInstance().getDm()));
        profs.setMaxSize(50, 50);
        setRowIndex(profs,2);
        setColumnIndex(profs,2);
        NumberStringConverter scProf = new NumberStringConverter();


        prof = new Slider(-90, 90, 0);
        prof.valueProperty().bindBidirectional(Modele.getInstance().getDmProperty());
        prof.valueProperty().addListener(((observableValue, number, t1) -> {
            //genericSliderListener("Profondeur (deg): ", profInfo, t1);
            if (!resetting){
                //profs.setText(Double.toString(prof.getValue()));
                Modele.getInstance().descriptionService.restart();
                Modele.getInstance().getForcesMomentService.restart();
            }
        }));
        setRowIndex(prof, 0);
        setColumnIndex(prof, 0);
        //Binding bidirectionnel Slider-Textfield
        Bindings.bindBidirectional(profs.textProperty(), prof.valueProperty(), scProf);

        dirInfo = new Label("Direction (deg):");
        setRowIndex(dirInfo, 3);
        setColumnIndex(dirInfo, 1);
        dirs = new TextField(Double.toString(Modele.getInstance().getDn()));
        dirs.setMaxSize(50, 50);
        setRowIndex(dirs,3);
        setColumnIndex(dirs,2);
        NumberStringConverter scDir = new NumberStringConverter();

        dir = new Slider(-90, 90, 0);
        dir.valueProperty().bindBidirectional(Modele.getInstance().getDnProperty());
        dir.valueProperty().addListener(((observableValue, number, t1) -> {
            //genericSliderListener("Direction (deg): ", dirInfo, t1);
            if (!resetting){
                Modele.getInstance().descriptionService.restart();
                Modele.getInstance().getForcesMomentService.restart();
            }
        }));
        setRowIndex(dir, 1);
        setColumnIndex(dir, 1);
        //Binding bidirectionnel Textfield-Slider
        Bindings.bindBidirectional(dirs.textProperty(), dir.valueProperty(), scDir);

        //Contrôle des ailerons
        ailInfo = new Label("Ailerons (deg):");
        setRowIndex(ailInfo, 4);
        setColumnIndex(ailInfo, 1);
        ails = new TextField(Double.toString(Modele.getInstance().getDm()));
        ails.setMaxSize(50, 50);
        setRowIndex(ails,4);
        setColumnIndex(ails,2);
        NumberStringConverter scAil = new NumberStringConverter();

        ailerons = new Slider(-90, 90, 0);
        ailerons.valueProperty().bindBidirectional(Modele.getInstance().getDlProperty());
        ailerons.valueProperty().addListener(((observableValue, number, t1) -> {
            //genericSliderListener("Ailerons (deg): ", ailInfo, t1);
            if (!resetting){
                Modele.getInstance().descriptionService.restart();
                Modele.getInstance().getForcesMomentService.restart();
            }
        }));
        setRowIndex(ailerons, 0);
        setColumnIndex(ailerons, 2);
        //Binding bidirectionnel slider-Textfield
        Bindings.bindBidirectional(ails.textProperty(), ailerons.valueProperty(), scAil);

        prof.setOrientation(Orientation.VERTICAL);
        ailerons.setOrientation(Orientation.VERTICAL);
        getChildren().addAll(prof, dir, ailerons, profInfo, dirInfo, ailInfo,profs,dirs,ails);
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7;");

        for (Node node: getChildren()){
            setHgrow(node, Priority.ALWAYS);
            setVgrow(node, Priority.ALWAYS);
        }
    }

    public void reset(){
        resetting = true;
        Modele.getInstance().setDl(0);
        Modele.getInstance().setDm(0);
        Modele.getInstance().setDn(0);
        resetting = false;
    }
}
