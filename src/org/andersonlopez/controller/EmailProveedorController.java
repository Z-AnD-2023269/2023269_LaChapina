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
import org.andersonlopez.bean.EmailProveedor;
import org.andersonlopez.db.Conexion;
import org.andersonlopez.system.Main;

public class EmailProveedorController implements Initializable{

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }

    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<EmailProveedor> listarEmailsProveedores;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtCodigoEmailProveedor;
    @FXML
    private TextField txtEmailProveedor;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtCodigoProveedor;
    @FXML
    private TableView tblEmailsProveedores;
    @FXML
    private TableColumn colCodigoEmailProveedor;
    @FXML
    private TableColumn colEmailProveedor;
    @FXML
    private TableColumn colDescripcion;
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
            ObservableList<EmailProveedor> emailsProveedores = getEmailsProveedores();
            tblEmailsProveedores.setItems(emailsProveedores);
            colCodigoEmailProveedor.setCellValueFactory(new PropertyValueFactory<EmailProveedor, Integer>("codigoEmailProveedor"));
            colEmailProveedor.setCellValueFactory(new PropertyValueFactory<EmailProveedor, String>("emailProveedor"));
            colDescripcion.setCellValueFactory(new PropertyValueFactory<EmailProveedor, String>("descripcion"));
            colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<EmailProveedor, Integer>("codigoProveedor"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void seleccionarElemento() {
        EmailProveedor emailProveedorSeleccionado = (EmailProveedor) tblEmailsProveedores.getSelectionModel().getSelectedItem();
        if (emailProveedorSeleccionado != null) {
            txtCodigoEmailProveedor.setText(String.valueOf(emailProveedorSeleccionado.getCodigoEmailProveedor()));
            txtEmailProveedor.setText(emailProveedorSeleccionado.getEmailProveedor());
            txtDescripcion.setText(emailProveedorSeleccionado.getDescripcion());
            txtCodigoProveedor.setText(String.valueOf(emailProveedorSeleccionado.getCodigoProveedor()));
        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún email de proveedor");
        }
    }

    public ObservableList<EmailProveedor> getEmailsProveedores() {
        ArrayList<EmailProveedor> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarEmailsProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new EmailProveedor(
                        resultado.getInt("codigoEmailProveedor"),
                        resultado.getString("emailProveedor"),
                        resultado.getString("descripcion"),
                        resultado.getInt("codigoProveedor")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarEmailsProveedores = FXCollections.observableArrayList(lista);
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
        EmailProveedor registro = new EmailProveedor();
        registro.setCodigoEmailProveedor(Integer.parseInt(txtCodigoEmailProveedor.getText()));
        registro.setEmailProveedor(txtEmailProveedor.getText());
        registro.setDescripcion(txtDescripcion.getText());
        registro.setCodigoProveedor(Integer.parseInt(txtCodigoProveedor.getText()));
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarEmailProveedor(?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoEmailProveedor());
            procedimiento.setString(2, registro.getEmailProveedor());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setInt(4, registro.getCodigoProveedor());
            procedimiento.execute();
            listarEmailsProveedores.add(registro);

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
                if (tblEmailsProveedores.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si Elimina el registro",
                            "Eliminar Email de Proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall(" {call sp_EliminarEmailProveedor(?)}");
                            procedimiento.setInt(1, ((EmailProveedor) tblEmailsProveedores.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor());
                            procedimiento.execute();
                            listarEmailsProveedores.remove(tblEmailsProveedores.getSelectionModel().getSelectedItem());

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
                if (tblEmailsProveedores.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/andersonlopez/images/add user.png"));
                    imgReporte.setImage(new Image("/org/andersonlopez/images/Cancelar.png"));
                    activarControles();
                    txtCodigoEmailProveedor.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall(" {call sp_EditarEmailProveedor(?, ?, ?, ?)}");
            EmailProveedor registro = (EmailProveedor) tblEmailsProveedores.getSelectionModel().getSelectedItem();
            registro.setEmailProveedor(txtEmailProveedor.getText());
            registro.setDescripcion(txtDescripcion.getText());
            registro.setCodigoProveedor(Integer.parseInt(txtCodigoProveedor.getText()));
            procedimiento.setInt(1, registro.getCodigoEmailProveedor());
            procedimiento.setString(2, registro.getEmailProveedor());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setInt(4, registro.getCodigoProveedor());
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
        txtCodigoEmailProveedor.setEditable(false);
        txtEmailProveedor.setEditable(false);
        txtDescripcion.setEditable(false);
        txtCodigoProveedor.setEditable(false);

    }

    public void activarControles() {
        txtCodigoEmailProveedor.setEditable(true);
        txtEmailProveedor.setEditable(true);
        txtDescripcion.setEditable(true);
        txtCodigoProveedor.setEditable(true);

    }

    public void limpiarControles() {
        txtCodigoEmailProveedor.clear();
        txtEmailProveedor.clear();
        txtDescripcion.clear();
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