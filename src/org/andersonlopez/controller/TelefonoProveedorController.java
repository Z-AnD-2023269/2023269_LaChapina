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
import org.andersonlopez.bean.TelefonoProveedor;
import org.andersonlopez.db.Conexion;
import org.andersonlopez.system.Main;


public class TelefonoProveedorController implements Initializable{
        private Main escenarioPrincipal;


    private enum operaciones {
        AGREGAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }

    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<TelefonoProveedor> listarTelefonosProveedores;

    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtCodigoTelefonoProveedor;
    @FXML
    private TextField txtNumeroPrincipal;
    @FXML
    private TextField txtNumeroSecundario;
    @FXML
    private TextField txtObservaciones;
    @FXML
    private TextField txtCodigoProveedor;
    @FXML
    private TableView tblTelefonosProveedores;
    @FXML
    private TableColumn colCodigoTelefonoProveedor;
    @FXML
    private TableColumn colNumeroPrincipal;
    @FXML
    private TableColumn colNumeroSecundario;
    @FXML
    private TableColumn colObservaciones;
    @FXML
    private TableColumn colCodigoProveedor;
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
        CargarDatos();
    }
    
    public void CargarDatos() {
        try {
            ObservableList<TelefonoProveedor> telefonosProveedores = getTelefonosProveedores();
            tblTelefonosProveedores.setItems(telefonosProveedores);
            colCodigoTelefonoProveedor.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, Integer>("codigoTelefonoProveedor"));
            colNumeroPrincipal.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, String>("numeroPrincipal"));
            colNumeroSecundario.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, String>("numeroSecundario"));
            colObservaciones.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, String>("observaciones"));
            colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, Integer>("codigoProveedor"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void seleccionarElemento() {
        TelefonoProveedor telefonoProveedorSeleccionado = (TelefonoProveedor) tblTelefonosProveedores.getSelectionModel().getSelectedItem();
        if (telefonoProveedorSeleccionado != null) {
            txtCodigoTelefonoProveedor.setText(String.valueOf(telefonoProveedorSeleccionado.getCodigoTelefonoProveedor()));
            txtNumeroPrincipal.setText(telefonoProveedorSeleccionado.getNumeroPrincipal());
            txtNumeroSecundario.setText(telefonoProveedorSeleccionado.getNumeroSecundario());
            txtObservaciones.setText(telefonoProveedorSeleccionado.getObservaciones());
            txtCodigoProveedor.setText(String.valueOf(telefonoProveedorSeleccionado.getCodigoProveedor()));
        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún teléfono de proveedor");
        }
    }

    public ObservableList<TelefonoProveedor> getTelefonosProveedores() {
        ArrayList<TelefonoProveedor> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarTelefonosProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TelefonoProveedor(
                        resultado.getInt("codigoTelefonoProveedor"),
                        resultado.getString("numeroPrincipal"),
                        resultado.getString("numeroSecundario"),
                        resultado.getString("observaciones"),
                        resultado.getInt("codigoProveedor")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarTelefonosProveedores = FXCollections.observableArrayList(lista);
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
        TelefonoProveedor registro = new TelefonoProveedor();
        registro.setCodigoTelefonoProveedor(Integer.parseInt(txtCodigoTelefonoProveedor.getText()));
        registro.setNumeroPrincipal(txtNumeroPrincipal.getText());
        registro.setNumeroSecundario(txtNumeroSecundario.getText());
        registro.setObservaciones(txtObservaciones.getText());
        registro.setCodigoProveedor(Integer.parseInt(txtCodigoProveedor.getText()));
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarTelefonoProveedor(?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoTelefonoProveedor());
            procedimiento.setString(2, registro.getNumeroPrincipal());
            procedimiento.setString(3, registro.getNumeroSecundario());
            procedimiento.setString(4, registro.getObservaciones());
            procedimiento.setInt(5, registro.getCodigoProveedor());
            procedimiento.execute();
            listarTelefonosProveedores.add(registro);

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
                if (tblTelefonosProveedores.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si Elimina el registro",
                            "Eliminar Teléfono de Proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall(" {call sp_EliminarTelefonoProveedor(?)}");
                            procedimiento.setInt(1, ((TelefonoProveedor) tblTelefonosProveedores.getSelectionModel().getSelectedItem()).getCodigoTelefonoProveedor());
                            procedimiento.execute();
                            listarTelefonosProveedores.remove(tblTelefonosProveedores.getSelectionModel().getSelectedItem());
                                    
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un Elemento");
                }
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblTelefonosProveedores.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/andersonlopez/images/add user.png"));
                    imgReporte.setImage(new Image("/org/andersonlopez/images/Cancelar.png"));
                    activarControles();
                    txtCodigoTelefonoProveedor.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un Elemento");
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
                CargarDatos();
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall(" {call sp_EditarTelefonoProveedor(?, ?, ?, ?, ?)}");
            TelefonoProveedor registro = (TelefonoProveedor) tblTelefonosProveedores.getSelectionModel().getSelectedItem();
            registro.setNumeroPrincipal(txtNumeroPrincipal.getText());
            registro.setNumeroSecundario(txtNumeroSecundario.getText());
            registro.setObservaciones(txtObservaciones.getText());
            registro.setCodigoProveedor(Integer.parseInt(txtCodigoProveedor.getText()));
            procedimiento.setInt(1, registro.getCodigoTelefonoProveedor());
            procedimiento.setString(2, registro.getNumeroPrincipal());
            procedimiento.setString(3, registro.getNumeroSecundario());
            procedimiento.setString(4, registro.getObservaciones());
            procedimiento.setInt(5, registro.getCodigoProveedor());
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
        txtCodigoTelefonoProveedor.setEditable(false);
        txtNumeroPrincipal.setEditable(false);
        txtNumeroSecundario.setEditable(false);
        txtObservaciones.setEditable(false);
        txtCodigoProveedor.setEditable(false);

    }

    public void activarControles() {
        txtCodigoTelefonoProveedor.setEditable(true);
        txtNumeroPrincipal.setEditable(true);
        txtNumeroSecundario.setEditable(true);
        txtObservaciones.setEditable(true);
        txtCodigoProveedor.setEditable(true);

    }

    public void limpiarControles() {
        txtCodigoTelefonoProveedor.clear();
        txtNumeroPrincipal.clear();
        txtNumeroSecundario.clear();
        txtObservaciones.clear();
        txtCodigoProveedor.clear();

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
