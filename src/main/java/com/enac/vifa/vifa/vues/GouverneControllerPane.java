package com.enac.vifa.vifa.vues;

import com.enac.vifa.vifa.Modele;

import javafx.beans.binding.Bindings;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
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

    private final Label triml;

    private final Slider trims;

    private final TextField trimt;


    protected void genericSliderListener(String labelText, Label label, Number newValue){
        String text = labelText + newValue.doubleValue();
        label.setText(text.substring(0, Math.min(22, text.length())));
    }
    public GouverneControllerPane(Vue3D vue){
        super(vue);
        profInfo = new Label("Profondeur (deg):");
        setRowIndex(profInfo, 3);
        setColumnIndex(profInfo, 1);
        profs = new TextField(Double.toString(Modele.getInstance().getDm()));
        profs.setMaxSize(50, 50);
        setRowIndex(profs,3);
        setColumnIndex(profs,2);
        setColumnSpan(profs, 2);
        NumberStringConverter scProf = new NumberStringConverter();


        prof = new Slider(-90, 90, 0);
        prof.setStyle(" -fx-color: RED;");
        prof.valueProperty().bindBidirectional(Modele.getInstance().getDmProperty());
        prof.valueProperty().addListener(((observableValue, number, t1) -> {
            //genericSliderListener("Profondeur (deg): ", profInfo, t1);
            if (!resetting){
                //profs.setText(Double.toString(prof.getValue()));
                Modele.getInstance().descriptionService.restart();
                Modele.getInstance().getForcesMomentService.restart();
            }
        }));
        setRowIndex(prof, 1);
        setColumnIndex(prof, 0);
        //Binding bidirectionnel Slider-Textfield
        Bindings.bindBidirectional(profs.textProperty(), prof.valueProperty(), scProf);

        dirInfo = new Label("Direction (deg):");
        setRowIndex(dirInfo, 4);
        setColumnIndex(dirInfo, 1);
        dirs = new TextField(Double.toString(Modele.getInstance().getDn()));
        setColumnSpan(dirs, 2);
        dirs.setMaxSize(50, 50);
        setRowIndex(dirs,4);
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
        setRowIndex(dir, 0);
        setColumnIndex(dir, 1);
        //Binding bidirectionnel Textfield-Slider
        Bindings.bindBidirectional(dirs.textProperty(), dir.valueProperty(), scDir);

        //Contrôle des ailerons
        ailInfo = new Label("Ailerons (deg):");
        ailInfo.setStyle("-fx-text-color: RED;");
        setRowIndex(ailInfo, 5);
        setColumnIndex(ailInfo, 1);
        ails = new TextField(Double.toString(Modele.getInstance().getDm()));
        setColumnSpan(ails, 2);
        ails.setMaxSize(50, 50);
        setRowIndex(ails,5);
        setColumnIndex(ails,2);
        NumberStringConverter scAil = new NumberStringConverter();

        ailerons = new Slider(-90, 90, 0);
        ailerons.setStyle(" -fx-color: RED;");
        ailerons.valueProperty().bindBidirectional(Modele.getInstance().getDlProperty());
        ailerons.valueProperty().addListener(((observableValue, number, t1) -> {
            //genericSliderListener("Ailerons (deg): ", ailInfo, t1);
            if (!resetting){
                Modele.getInstance().descriptionService.restart();
                Modele.getInstance().getForcesMomentService.restart();
            }
        }));
        setRowIndex(ailerons, 2);
        setColumnIndex(ailerons, 1);
        //Binding bidirectionnel slider-Textfield
        Bindings.bindBidirectional(ails.textProperty(), ailerons.valueProperty(), scAil);

        prof.setOrientation(Orientation.VERTICAL);

        triml = new Label("Trim (deg):");
        setRowIndex(triml, 6);
        setColumnIndex(triml, 1);
        trims = new Slider(-90, 90, 0);
        trims.setOrientation(Orientation.VERTICAL);

        Label trimindiq = new Label("Trim");
        setColumnIndex(trimindiq,3);

        setRowIndex(trims, 1);
        setColumnIndex(trims, 3);
        trims.valueProperty().bindBidirectional(Modele.getInstance().getTrimProperty());
        trims.valueProperty().addListener(((observableValue, number, t1) -> {
            if (!resetting){
                Modele.getInstance().descriptionService.restart();
                Modele.getInstance().getForcesMomentService.restart();
            }
        }));
        trimt = new TextField(Double.toString(Modele.getInstance().getTrim()));
        setColumnSpan(trimt, 2);
        trimt.setMaxSize(50, 50);
        setRowIndex(trimt, 6);
        setColumnIndex(trimt, 2);
        NumberStringConverter scTri = new NumberStringConverter();
        Bindings.bindBidirectional(trimt.textProperty(), trims.valueProperty(), scTri);

        MiniManche manche = new MiniManche();
        manche.profPropProperty().bindBidirectional(prof.valueProperty());
        manche.ailPropProperty().bindBidirectional(ailerons.valueProperty());
        setColumnIndex(manche, 1);
        setRowIndex(manche, 1);

        getChildren().addAll(prof, dir, ailerons, profInfo, dirInfo, ailInfo,profs,dirs,ails, triml, trims, trimt, trimindiq, manche);
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7; -fx-background-radius: 5px;");

        setHgap(15);
        setAlignment(Pos.CENTER);

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
        Modele.getInstance().setTrim(0);
        resetting = false;
    }
}
