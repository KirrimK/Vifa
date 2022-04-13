module com.enac.vifa.vifa {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.fxyz3d.core;


    opens com.enac.vifa.vifa to javafx.fxml;
    exports com.enac.vifa.vifa;
}