package org.andersonlopez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.andersonlopez.bean.DetalleCompra;
import org.andersonlopez.db.Conexion;
import org.andersonlopez.system.Main;

public class DetalleCompraController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<DetalleCompra> listarDetalleCompra;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtCodigoDetalleCompra;
    @FXML
    private TextField txtCostoUnitario;
    @FXML
    private TextField txtCantidad;
    @FXML
    private TextField txtCodigoProductoDC;
    @FXML
    private TextField txtNumeroDocumentoDC;
    @FXML
    private TableView tblDetalleCompra;
    @FXML
    private TableColumn colCodigoDetalleCompra;
    @FXML
    private TableColumn colCostoUnitario;
    @FXML
    private TableColumn colCantidad;
    @FXML
    private TableColumn colCodigoProductoDC;
    @FXML
    private TableColumn colNumeroDocumentoDC;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReporte;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgReporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public void cargarDatos() {
        try {
            ObservableList<DetalleCompra> detalleCompras = getDetalleCompras();
            tblDetalleCompra.setItems(detalleCompras);
            colCodigoDetalleCompra.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("codigoDetalleCompra"));
            colCostoUnitario.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Double>("costoUnitario"));
            colCantidad.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("cantidad"));
            colCodigoProductoDC.setCellValueFactory(new PropertyValueFactory<DetalleCompra, String>("codigoProducto"));
            colNumeroDocumentoDC.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("numeroDocumento"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void seleccionarElemento() {
        DetalleCompra detalleCompraSeleccionado = (DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem();
        if (detalleCompraSeleccionado != null) {
            txtCodigoDetalleCompra.setText(String.valueOf(detalleCompraSeleccionado.getCodigoDetalleCompra()));
            txtCostoUnitario.setText(String.valueOf(detalleCompraSeleccionado.getCostoUnitario()));
            txtCantidad.setText(String.valueOf(detalleCompraSeleccionado.getCantidad()));
            txtCodigoProductoDC.setText(detalleCompraSeleccionado.getCodigoProducto());
            txtNumeroDocumentoDC.setText(String.valueOf(detalleCompraSeleccionado.getNumeroDocumento()));
        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún detalle de compra");
        }
    }

    public ObservableList<DetalleCompra> getDetalleCompras() {
        ArrayList<DetalleCompra> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarDetallesCompra()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new DetalleCompra(
                        resultado.getInt("codigoDetalleCompra"),
                        resultado.getDouble("costoUnitario"),
                        resultado.getInt("cantidad"),
                        resultado.getString("codigoProducto"),
                        resultado.getInt("numeroDocumento")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarDetalleCompra = FXCollections.observableArrayList(lista);
    }

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/andersonlopez/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/andersonlopez/images/Cancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/andersonlopez/images/add user.png"));
                imgEliminar.setImage(new Image("/org/andersonlopez/images/delete user.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
        }
    }

    public void guardar() {
        DetalleCompra registro = new DetalleCompra();
        registro.setCodigoDetalleCompra(Integer.parseInt(txtCodigoDetalleCompra.getText()));
        registro.setCostoUnitario(Double.parseDouble(txtCostoUnitario.getText()));
        registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
        registro.setCodigoProducto(txtCodigoProductoDC.getText());
        registro.setNumeroDocumento(Integer.parseInt(txtNumeroDocumentoDC.getText()));
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarDetalleCompra(?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoDetalleCompra());
            procedimiento.setDouble(2, registro.getCostoUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setString(4, registro.getCodigoProducto());
            procedimiento.setInt(5, registro.getNumeroDocumento());
            procedimiento.execute();
            listarDetalleCompra.add(registro);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/andersonlopez/images/add user.png"));
                imgEliminar.setImage(new Image("/org/andersonlopez/images/delete user.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblDetalleCompra.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si Elimina el registro",
                            "Eliminar Detalle de Compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarDetalleCompra(?)}");
                            procedimiento.setInt(1, ((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra());
                            procedimiento.execute();
                            listarDetalleCompra.remove(tblDetalleCompra.getSelectionModel().getSelectedItem());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblDetalleCompra.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/andersonlopez/images/edit user.png"));
                    imgReporte.setImage(new Image("/org/andersonlopez/images/Cancelar.png"));
                    activarControles();
                    txtCodigoDetalleCompra.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/andersonlopez/images/edit user.png"));
                imgReporte.setImage(new Image("/org/andersonlopez/images/usereport.png"));
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EditarDetalleCompra(?, ?, ?, ?, ?)}");
            DetalleCompra registro = (DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem();
            registro.setCostoUnitario(Double.parseDouble(txtCostoUnitario.getText()));
            registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
            registro.setCodigoProducto(txtCodigoProductoDC.getText());
            registro.setNumeroDocumento(Integer.parseInt(txtNumeroDocumentoDC.getText()));
            procedimiento.setInt(1, registro.getCodigoDetalleCompra());
            procedimiento.setDouble(2, registro.getCostoUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setString(4, registro.getCodigoProducto());
            procedimiento.setInt(5, registro.getNumeroDocumento());
            procedimiento.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reporte() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/andersonlopez/images/edit user.png"));
                imgReporte.setImage(new Image("/org/andersonlopez/images/usereport.png"));
                tipoDeOperaciones = operaciones.NINGUNO;

        }
    }

    public void desactivarControles() {
        txtCodigoDetalleCompra.setEditable(false);
        txtCostoUnitario.setEditable(false);
        txtCantidad.setEditable(false);
        txtCodigoProductoDC.setEditable(false);
        txtNumeroDocumentoDC.setEditable(false);

    }

    public void activarControles() {
        txtCodigoDetalleCompra.setEditable(true);
        txtCostoUnitario.setEditable(true);
        txtCantidad.setEditable(true);
        txtCodigoProductoDC.setEditable(true);
        txtNumeroDocumentoDC.setEditable(true);

    }

    public void limpiarControles() {
        txtCodigoDetalleCompra.clear();
        txtCostoUnitario.clear();
        txtCantidad.clear();
        txtCodigoProductoDC.clear();
        txtNumeroDocumentoDC.clear();

    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void clicRegresar(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipal();
        }
    }
}
