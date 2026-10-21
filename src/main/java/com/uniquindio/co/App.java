package com.uniquindio.co;

import java.io.IOException;

import com.uniquindio.co.controlador.Contexto;
import com.uniquindio.co.controlador.MainController;
import com.uniquindio.co.modelo.entidades.Agencia;
import com.uniquindio.co.modelo.patrones.estructurales.adapter.PagoAndinoAdapter;
import com.uniquindio.co.modelo.patrones.estructurales.adapter.externo.PagoAndinoAPI;
import com.uniquindio.co.modelo.patrones.estructurales.facade.ConfirmarReservaFacade;
import com.uniquindio.co.modelo.servicios.GestorCupos;
import com.uniquindio.co.modelo.servicios.ServicioCorreo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Agencia agencia = new Agencia("RutaVerde Quindío", "900.123.456-7", "RNT 123456",
                "Armenia, Quindío", "3001234567", "info@rutaverde.co", "www.rutaverde.co");

        GestorCupos gestorCupos = new GestorCupos(agencia);
        PagoAndinoAdapter pasarela = new PagoAndinoAdapter(new PagoAndinoAPI());
        ServicioCorreo correo = new ServicioCorreo();

        ConfirmarReservaFacade facade = new ConfirmarReservaFacade(agencia, gestorCupos, pasarela, correo);
        Contexto ctx = new Contexto(agencia, facade, correo, pasarela);

        FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/uniquindio/co/vista/main.fxml"));
        Parent root = loader.load();
        MainController main = loader.getController();
        main.init(ctx);

        Scene scene = new Scene(root, 1180, 740);
        scene.getStylesheets().add(App.class.getResource("/com/uniquindio/co/css/estilos.css").toExternalForm());
        stage.setTitle("RutaVerde Quindío");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
