
import com.enac.vifa.vifa.Modele;
import com.enac.vifa.vifa.vues.ControllerPane;
import com.enac.vifa.vifa.vues.Vue3D;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import static javafx.scene.layout.GridPane.setColumnIndex;
import static javafx.scene.layout.GridPane.setRowIndex;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hugocourtadon
 */
public class Gas extends ControllerPane{
    
    private final Label poussInfo;
    private final Slider pouss;
    
    public Gas(Vue3D vue){
        super(vue);
        poussInfo = new Label("Poussée (de 0 à 1) : 0.0");
        setRowIndex(poussInfo,1);
        setColumnIndex(poussInfo, 1);
        pouss = new Slider(0, 1,0);
        pouss.valueProperty().bindBidirectional(Modele.getInstance().getDxProperty());
        pouss.valueProperty().addListener(((observableValue, number, t1) -> {
            genericSliderListener("Poussée: ", poussInfo, t1);
            if (!resetting){
                Modele.getInstance().descriptionService.restart();
                Modele.getInstance().getForcesMomentService.restart();
            }
        }));
        setRowIndex(pouss,1);
        setColumnIndex(pouss, 0);
        getChildren().addAll(pouss,poussInfo);
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7;");
    }
}
