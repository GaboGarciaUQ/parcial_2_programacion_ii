module com.uniquindio.co {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.uniquindio.co to javafx.fxml;
    exports com.uniquindio.co;

    exports com.uniquindio.co.modelo.entidades;
    exports com.uniquindio.co.modelo.entidades.enums;
    exports com.uniquindio.co.modelo.patrones.creacionales.builder;
    exports com.uniquindio.co.modelo.patrones.creacionales.factory;
    exports com.uniquindio.co.modelo.patrones.estructurales.adapter;
    exports com.uniquindio.co.modelo.patrones.estructurales.composite;
    exports com.uniquindio.co.modelo.patrones.estructurales.facade;
    exports com.uniquindio.co.modelo.servicios;

    opens com.uniquindio.co.modelo.entidades;
    opens com.uniquindio.co.modelo.patrones.creacionales.builder;
    opens com.uniquindio.co.modelo.patrones.creacionales.factory;
    opens com.uniquindio.co.modelo.patrones.estructurales.adapter;
    opens com.uniquindio.co.modelo.patrones.estructurales.composite;
    opens com.uniquindio.co.modelo.patrones.estructurales.facade;
    opens com.uniquindio.co.modelo.servicios;
}