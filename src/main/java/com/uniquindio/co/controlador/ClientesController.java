package com.uniquindio.co.controlador;

import com.uniquindio.co.modelo.entidades.Cliente;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ClientesController extends BaseController {

    @FXML private TextField tfId, tfNombre, tfTelefono, tfCorreo, tfPais;
    @FXML private ComboBox<String> cbIdioma;
    @FXML private TableView<Cliente> tabla;

    @Override
    protected void alIniciar() {
        cbIdioma.setItems(FXCollections.observableArrayList(IDIOMAS));
        ajustar(tabla);
        tabla.getColumns().add(col("Documento", Cliente::getId));
        tabla.getColumns().add(col("Nombre", Cliente::getNombre));
        tabla.getColumns().add(col("Teléfono", Cliente::getTelefono));
        tabla.getColumns().add(col("Correo", Cliente::getCorreo));
        tabla.getColumns().add(col("País", Cliente::getPais));
        tabla.getColumns().add(col("Idioma", Cliente::getIdioma));
        tabla.getSelectionModel().selectedItemProperty().addListener((o, a, c) -> {
            if (c != null) {
                tfId.setText(c.getId()); tfNombre.setText(c.getNombre());
                tfTelefono.setText(c.getTelefono()); tfCorreo.setText(c.getCorreo());
                tfPais.setText(c.getPais()); cbIdioma.setValue(c.getIdioma());
            }
        });
        refrescar();
    }

    @FXML
    private void registrar() {
        if (hayVacios(tfId, tfNombre, tfTelefono, tfCorreo, tfPais) || cbIdioma.getValue() == null) {
            if (cbIdioma.getValue() == null) error("Selecciona el idioma preferido.");
            return;
        }
        try {
            ctx.getAgencia().registrarCliente(tfId.getText().trim(), tfNombre.getText().trim(),
                    tfTelefono.getText().trim(), tfCorreo.getText().trim(),
                    tfPais.getText().trim(), cbIdioma.getValue());
            refrescar();
            limpiar();
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
        }
    }

    @FXML
    private void actualizar() {
        Cliente c = tabla.getSelectionModel().getSelectedItem();
        if (c == null) { error("Selecciona un cliente de la tabla."); return; }
        if (hayVacios(tfNombre, tfTelefono, tfCorreo, tfPais) || cbIdioma.getValue() == null) return;
        try {
            c.setCorreo(tfCorreo.getText());
            c.setNombre(tfNombre.getText().trim());
            c.setTelefono(tfTelefono.getText().trim());
            c.setPais(tfPais.getText().trim());
            c.setIdioma(cbIdioma.getValue());
            tabla.refresh();
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
        }
    }

    @FXML
    private void limpiar() {
        tfId.clear(); tfNombre.clear(); tfTelefono.clear(); tfCorreo.clear(); tfPais.clear();
        cbIdioma.setValue(null);
        tabla.getSelectionModel().clearSelection();
    }

    private void refrescar() {
        tabla.setItems(FXCollections.observableArrayList(ctx.getAgencia().getListaClientes()));
    }
}
