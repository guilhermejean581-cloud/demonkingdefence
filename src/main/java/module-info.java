module DemonKingDefence {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens demonkingdefence to javafx.fxml;
    exports demonkingdefence;

    opens controller to javafx.fxml;
    exports controller;
}