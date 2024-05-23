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
import org.andersonlopez.bean.Factura;
import org.andersonlopez.db.Conexion;
import org.andersonlopez.system.Main;

public class FacturaController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Factura> listarFacturas;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtNumeroFactura;
    @FXML
    private TextField txtEstado;
    @FXML
    private TextField txtTotalFactura;
    @FXML
    private TextField txtFechaFactura;
    @FXML
    private TextField txtCodigoCliente;
    @FXML
    private TextField txtCodigoEmpleado;
    @FXML
    private TableView tblFacturas;
    @FXML
    private TableColumn colNumeroFactura;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TableColumn colTotalFactura;
    @FXML
    private TableColumn colFechaFactura;
    @FXML
    private TableColumn colCodigoCliente;
    @FXML
    private TableColumn colCodigoEmpleado;
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
            ObservableList<Factura> facturas = getFacturas();
            tblFacturas.setItems(facturas);
            colNumeroFactura.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("numeroFactura"));
            colEstado.setCellValueFactory(new PropertyValueFactory<Factura, String>("estado"));
            colTotalFactura.setCellValueFactory(new PropertyValueFactory<Factura, Double>("totalFactura"));
            colFechaFactura.setCellValueFactory(new PropertyValueFactory<Factura, String>("fechaFactura"));
            colCodigoCliente.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("codigoCliente"));
            colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("codigoEmpleado"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void seleccionarElemento() {
        Factura facturaSeleccionada = (Factura) tblFacturas.getSelectionModel().getSelectedItem();
        if (facturaSeleccionada != null) {
            txtNumeroFactura.setText(String.valueOf(facturaSeleccionada.getNumeroFactura()));
            txtEstado.setText(facturaSeleccionada.getEstado());
            txtTotalFactura.setText(String.valueOf(facturaSeleccionada.getTotalFactura()));
            txtFechaFactura.setText(facturaSeleccionada.getFechaFactura());
            txtCodigoCliente.setText(String.valueOf(facturaSeleccionada.getCodigoCliente()));
            txtCodigoEmpleado.setText(String.valueOf(facturaSeleccionada.getCodigoEmpleado()));
        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna factura");
        }
    }

    public ObservableList<Factura> getFacturas() {
        ArrayList<Factura> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarFacturas()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Factura(
                        resultado.getInt("numeroFactura"),
                        resultado.getString("estado"),
                        resultado.getDouble("totalFactura"),
                        resultado.getString("fechaFactura"),
                        resultado.getInt("codigoCliente"),
                        resultado.getInt("codigoEmpleado")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarFacturas = FXCollections.observableArrayList(lista);
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
        Factura registro = new Factura();
        registro.setNumeroFactura(Integer.parseInt(txtNumeroFactura.getText()));
        registro.setEstado(txtEstado.getText());
        registro.setTotalFactura(Double.parseDouble(txtTotalFactura.getText()));
        registro.setFechaFactura(txtFechaFactura.getText());
        registro.setCodigoCliente(Integer.parseInt(txtCodigoCliente.getText()));
        registro.setCodigoEmpleado(Integer.parseInt(txtCodigoEmpleado.getText()));
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarFactura(?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setDouble(3, registro.getTotalFactura());
            procedimiento.setString(4, registro.getFechaFactura());
            procedimiento.setInt(5, registro.getCodigoCliente());
            procedimiento.setInt(6, registro.getCodigoEmpleado());
            procedimiento.execute();
            listarFacturas.add(registro);

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
                if (tblFacturas.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si Elimina el registro",
                            "Eliminar Factura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarFactura(?)}");
                            procedimiento.setInt(1, ((Factura) tblFacturas.getSelectionModel().getSelectedItem()).getNumeroFactura());
                            procedimiento.execute();
                            listarFacturas.remove(tblFacturas.getSelectionModel().getSelectedItem());
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
                if (tblFacturas.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/andersonlopez/images/edit user.png"));
                    imgReporte.setImage(new Image("/org/andersonlopez/images/Cancelar.png"));
                    activarControles();
                    txtNumeroFactura.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EditarFactura(?, ?, ?, ?, ?, ?)}");
            Factura registro = (Factura) tblFacturas.getSelectionModel().getSelectedItem();
            registro.setEstado(txtEstado.getText());
            registro.setTotalFactura(Double.parseDouble(txtTotalFactura.getText()));
            registro.setFechaFactura(txtFechaFactura.getText());
            registro.setCodigoCliente(Integer.parseInt(txtCodigoCliente.getText()));
            registro.setCodigoEmpleado(Integer.parseInt(txtCodigoEmpleado.getText()));
            procedimiento.setInt(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setDouble(3, registro.getTotalFactura());
            procedimiento.setString(4, registro.getFechaFactura());
            procedimiento.setInt(5, registro.getCodigoCliente());
            procedimiento.setInt(6, registro.getCodigoEmpleado());
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
        txtNumeroFactura.setEditable(false);
        txtEstado.setEditable(false);
        txtTotalFactura.setEditable(false);
        txtFechaFactura.setEditable(false);
        txtCodigoCliente.setEditable(false);
        txtCodigoEmpleado.setEditable(false);

    }

    public void activarControles() {
        txtNumeroFactura.setEditable(true);
        txtEstado.setEditable(true);
        txtTotalFactura.setEditable(true);
        txtFechaFactura.setEditable(true);
        txtCodigoCliente.setEditable(true);
        txtCodigoEmpleado.setEditable(true);

    }

    public void limpiarControles() {
        txtNumeroFactura.clear();
        txtEstado.clear();
        txtTotalFactura.clear();
        txtFechaFactura.clear();
        txtCodigoCliente.clear();
        txtCodigoEmpleado.clear();

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
