package com.enac.vifa.vifa.vues;


import javafx.scene.control.CheckBox;

/**
 * Panneau de contrôle permettant de choisir ce que l'on souhaite afficher.
 */
public class SelectionAffichagePanneau extends ControllerPane{

    private CheckBox caseAngles;
    private CheckBox caseForces;
    private CheckBox caseMoment;
    private CheckBox caseNoms;
    private CheckBox caseReperes;

    public SelectionAffichagePanneau(Vue3D vue) {
        super(vue);
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7; -fx-background-radius: 5px;");

        this.caseAngles = new CheckBox("Angles");
        caseAngles.setOnAction(event -> vue.setVisibleGroupeAngle(caseAngles.isSelected()));
        caseAngles.setSelected(true);
        this.add(caseAngles,0,0);

        this.caseForces = new CheckBox("Forces");
        caseForces.setOnAction(event -> vue.setVisibleGroupeForce(caseForces.isSelected()));
        caseForces.setSelected(true);
        this.add(caseForces,0,1);

        this.caseMoment = new CheckBox("Moment");
        caseMoment.setOnAction(event -> vue.setVisibleGroupeMoment(caseMoment.isSelected()));
        caseMoment.setSelected(true);
        this.add(caseMoment,0,2);

        this.caseNoms = new CheckBox("Noms des flèches");
        caseNoms.setOnAction(event -> vue.setVisibleNoms(caseNoms.isSelected()));
        caseNoms.setSelected(false);
        this.add (caseNoms,0,3);

        this.caseReperes = new CheckBox("Repères");
        caseReperes.setOnAction(event -> vue.setVisibleReperes(caseReperes.isSelected()));
        caseReperes.setSelected(true);
        this.add(caseReperes,0,4);
    }
    
}
