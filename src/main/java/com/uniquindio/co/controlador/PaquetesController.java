package com.uniquindio.co.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.uniquindio.co.modelo.patrones.estructurales.composite.Actividad;
import com.uniquindio.co.modelo.patrones.estructurales.composite.PaqueteTuristico;
import com.uniquindio.co.modelo.patrones.estructurales.composite.ProductoTuristico;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class PaquetesController extends BaseController {

    @FXML private TextField tfCodigo, tfNombre, tfDescuento;
    @FXML private ListView<ProductoTuristico> lvContenidos;
    @FXML private TableView<ProductoTuristico> tabla;
    @FXML private TreeView<String> arbol;
    @FXML private Label lblResumen;

    @Override
    protected void alIniciar() {
        lvContenidos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ajustar(tabla);
        tabla.getColumns().add(col("Código", ProductoTuristico::getCodigo));
        tabla.getColumns().add(col("Nombre", ProductoTuristico::getNombre));
        tabla.getColumns().add(col("Tipo", p -> p instanceof Actividad ? "Actividad" : "Paquete"));
        tabla.getColumns().add(col("Precio p/p", p -> dinero(p.getPrecioPorPersona())));
        tabla.getColumns().add(col("Horas", p -> String.valueOf(p.getDuracionHoras())));
        tabla.getSelectionModel().selectedItemProperty().addListener((o, a, p) -> detalle(p));
        refrescar();
    }

    @FXML
    private void registrar() {
        if (hayVacios(tfCodigo, tfNombre, tfDescuento)) return;
        List<ProductoTuristico> contenidos = new ArrayList<>(lvContenidos.getSelectionModel().getSelectedItems());
        if (contenidos.isEmpty()) { error("Selecciona al menos una actividad o paquete para incluir."); return; }
        try {
            ctx.getAgencia().registrarPaqueteTuristico(tfCodigo.getText().trim(), tfNombre.getText().trim(),
                    Double.parseDouble(tfDescuento.getText().trim()), contenidos);
            tfCodigo.clear(); tfNombre.clear(); tfDescuento.clear();
            refrescar();
        } catch (NumberFormatException e) {
            error("El descuento debe ser un número entre 0 y 20.");
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
        }
    }

    /** Agrega lo seleccionado en la lista a un paquete ya existente (valida ciclos y duplicados). */
    @FXML
    private void agregarAlPaquete() {
        ProductoTuristico destino = tabla.getSelectionModel().getSelectedItem();
        if (!(destino instanceof PaqueteTuristico)) { error("Selecciona un paquete en la tabla."); return; }
        List<ProductoTuristico> nuevos = new ArrayList<>(lvContenidos.getSelectionModel().getSelectedItems());
        if (nuevos.isEmpty()) { error("Selecciona qué quieres agregar."); return; }
        try {
            for (ProductoTuristico p : nuevos) ((PaqueteTuristico) destino).agregarContenido(p);
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
        }
        refrescar();
        tabla.getSelectionModel().select(destino);
    }

    private void refrescar() {
        List<ProductoTuristico> todos = ctx.getAgencia().getListaProductos();
        tabla.setItems(FXCollections.observableArrayList(todos));
        lvContenidos.setItems(FXCollections.observableArrayList(todos));
        detalle(null);
    }

    private void detalle(ProductoTuristico p) {
        if (p == null) { arbol.setRoot(null); lblResumen.setText("Selecciona un producto para ver su detalle."); return; }
        arbol.setRoot(nodo(p));
        String acts = p.getActividades().stream().map(Actividad::getNombre).collect(Collectors.joining(", "));
        lblResumen.setText("Precio por persona: " + dinero(p.getPrecioPorPersona())
                + "   Duración: " + p.getDuracionHoras() + " h   Actividades: " + (acts.isEmpty() ? "ninguna" : acts));
    }

    private TreeItem<String> nodo(ProductoTuristico p) {
        boolean paquete = p instanceof PaqueteTuristico;
        TreeItem<String> n = new TreeItem<>(p.getNombre() + (paquete ? " (paquete)" : " - " + p.getDuracionHoras() + " h"));
        if (paquete) {
            for (ProductoTuristico h : ((PaqueteTuristico) p).getListaContenidos()) n.getChildren().add(nodo(h));
        }
        n.setExpanded(true);
        return n;
    }
}
