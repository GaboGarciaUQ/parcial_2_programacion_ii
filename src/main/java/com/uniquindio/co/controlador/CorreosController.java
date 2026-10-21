package com.uniquindio.co.controlador;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.uniquindio.co.modelo.servicios.ServicioCorreo.Correo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class CorreosController extends BaseController {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @FXML private TableView<Correo> tabla;
    @FXML private TextArea taDetalle;

    @Override
    protected void alIniciar() {
        ajustar(tabla);
        tabla.setPlaceholder(new javafx.scene.control.Label("Aún no se ha enviado ningún correo"));
        tabla.getColumns().add(col("Fecha", c -> c.getFecha().format(FORMATO)));
        tabla.getColumns().add(col("Para", Correo::getDestinatario));
        tabla.getColumns().add(col("Asunto", Correo::getAsunto));
        tabla.getSelectionModel().selectedItemProperty().addListener((o, a, c) -> {
            if (c != null) {
                taDetalle.setText("Para: " + c.getDestinatario()
                        + "\nAsunto: " + c.getAsunto()
                        + "\n\n" + c.getCuerpo());
            }
        });
        List<Correo> recientes = new ArrayList<>(ctx.getCorreo().getEnviados());
        Collections.reverse(recientes);   // el más reciente arriba
        tabla.setItems(FXCollections.observableArrayList(recientes));
    }
}