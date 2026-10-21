package com.uniquindio.co.controlador;

import java.util.ArrayList;
import java.util.List;

import com.uniquindio.co.modelo.entidades.Guia;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class GuiasController extends BaseController {

    @FXML private TextField tfId, tfNombre, tfTelefono, tfTarifa;
    @FXML private ListView<String> lvIdiomas;
    @FXML private TableView<Guia> tabla;

    @Override
    protected void alIniciar() {
        lvIdiomas.setItems(FXCollections.observableArrayList(IDIOMAS));
        lvIdiomas.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ajustar(tabla);
        tabla.getColumns().add(col("Identificación", Guia::getId));
        tabla.getColumns().add(col("Nombre", Guia::getNombre));
        tabla.getColumns().add(col("Idiomas", g -> String.join(", ", g.getIdiomas())));
        tabla.getColumns().add(col("Teléfono", Guia::getTelefono));
        tabla.getColumns().add(col("Tarifa por día", g -> dinero(g.getTarifadia())));
        tabla.getSelectionModel().selectedItemProperty().addListener((o, a, g) -> {
            if (g != null) {
                tfId.setText(g.getId()); tfNombre.setText(g.getNombre());
                tfTelefono.setText(g.getTelefono());
                tfTarifa.setText(String.valueOf((long) g.getTarifadia()));
                lvIdiomas.getSelectionModel().clearSelection();
                for (String i : g.getIdiomas()) lvIdiomas.getSelectionModel().select(i);
            }
        });
        refrescar();
    }

    @FXML
    private void registrar() {
        if (hayVacios(tfId, tfNombre, tfTelefono, tfTarifa)) return;
        try {
            List<String> idiomas = new ArrayList<>(lvIdiomas.getSelectionModel().getSelectedItems());
            ctx.getAgencia().registrarGuia(tfId.getText().trim(), tfNombre.getText().trim(), idiomas,
                    tfTelefono.getText().trim(), Double.parseDouble(tfTarifa.getText().trim()));
            refrescar();
            limpiar();
        } catch (NumberFormatException e) {
            error("La tarifa debe ser un número.");
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
        }
    }

    @FXML
    private void actualizar() {
        Guia g = tabla.getSelectionModel().getSelectedItem();
        if (g == null) { error("Selecciona un guía de la tabla."); return; }
        if (hayVacios(tfNombre, tfTelefono, tfTarifa)) return;
        try {
            g.setTarifadia(Double.parseDouble(tfTarifa.getText().trim()));
            g.setNombre(tfNombre.getText().trim());
            g.setTelefono(tfTelefono.getText().trim());
            for (String i : lvIdiomas.getSelectionModel().getSelectedItems()) g.agregarIdioma(i);
            tabla.refresh();
        } catch (NumberFormatException e) {
            error("La tarifa debe ser un número.");
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
        }
    }

    @FXML
    private void limpiar() {
        tfId.clear(); tfNombre.clear(); tfTelefono.clear(); tfTarifa.clear();
        lvIdiomas.getSelectionModel().clearSelection();
        tabla.getSelectionModel().clearSelection();
    }

    private void refrescar() {
        tabla.setItems(FXCollections.observableArrayList(ctx.getAgencia().getListaGuias()));
    }
}
