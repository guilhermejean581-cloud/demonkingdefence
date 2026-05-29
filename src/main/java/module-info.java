module com.mycompany.demonkingdefence {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.demonkingdefence to javafx.fxml;
    exports demonkingdefence;
}
