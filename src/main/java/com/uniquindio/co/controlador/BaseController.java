package com.uniquindio.co.controlador;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.Function;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public abstract class BaseController {

    protected static final String[] IDIOMAS = {"Español", "Inglés", "Francés", "Alemán", "Portugués"};
    protected Contexto ctx;

    public void init(Contexto ctx) {
        this.ctx = ctx;
        alIniciar();
    }

    protected abstract void alIniciar();

    protected <T> TableColumn<T, String> col(String titulo, Function<T, String> f) {
        TableColumn<T, String> c = new TableColumn<>(titulo);
        c.setCellValueFactory(d -> new SimpleStringProperty(f.apply(d.getValue())));
        return c;
    }

    protected <T> void ajustar(TableView<T> tabla) {
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla.setPlaceholder(new javafx.scene.control.Label("Aún no hay registros"));
    }

    protected static <T> void mostrarComo(ComboBox<T> cb, Function<T, String> f) {
        cb.setConverter(new StringConverter<T>() {
            @Override public String toString(T t) { return t == null ? "" : f.apply(t); }
            @Override public T fromString(String s) { return null; }
        });
    }

    protected static String dinero(double v) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        nf.setMaximumFractionDigits(0);
        return nf.format(v);
    }

    protected boolean hayVacios(TextField... campos) {
        for (TextField c : campos) {
            if (c.getText() == null || c.getText().trim().isEmpty()) {
                error("Completa todos los campos obligatorios.");
                return true;
            }
        }
        return false;
    }

    protected void error(String mensaje) {
        mostrar(Alert.AlertType.ERROR, "No se pudo completar", mensaje);
    }

    protected void info(String mensaje) {
        mostrar(Alert.AlertType.INFORMATION, "Listo", mensaje);
    }

    private void mostrar(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.showAndWait();
    }
}
