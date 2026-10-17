module com.uniquindio.co {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.uniquindio.co to javafx.fxml;
    exports com.uniquindio.co;
}
