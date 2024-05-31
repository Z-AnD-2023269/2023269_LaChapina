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
import org.andersonlopez.bean.Proveedores;
import org.andersonlopez.db.Conexion;
import org.andersonlopez.system.Main;

public class MenuProveedoresController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Proveedores> listaProveedores;
    
    @FXML private TableView<Proveedores> tvPoveedores;
    @FXML private TableColumn colCodP;
    @FXML private TableColumn colNITP;
    @FXML private TableColumn colNomP;
    @FXML private TableColumn colApeP;
    @FXML private TableColumn colDireP;
    @FXML private TableColumn colRazonSP;
    @FXML private TableColumn colContactoP;
    @FXML private TableColumn colSitioWeb;
    @FXML private Button btnAgregarP;
    @FXML private Button btnEditarP;
    @FXML private Button btnEliminarP;
    @FXML private Button btnReportesP;
    @FXML private TextField txtCodigoP;
    @FXML private TextField txtNITP;
    @FXML private TextField txtNombresP;
    @FXML private TextField txtApellidosP;
    @FXML private TextField txtDireccionP;
    @FXML private TextField txtRazonSocial;
    @FXML private TextField txtContactoP;
    @FXML private TextField txtSitioWeb;
    @FXML private Button btnRegresar;
    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        desactivarControles();
    }

    public void cargarDatos() {
        tvPoveedores.setItems(getProveedores());
        colCodP.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoProveedor"));
        colNITP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("NITproveedor"));
        colNomP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nombreProveedor"));
        colApeP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("apellidoProveedor"));
        colDireP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("direccionProveedor"));
        colRazonSP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("razonSocial"));
        colContactoP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("contactoPrincipal"));
        colSitioWeb.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("paginaWeb"));
    }

    public void seleccionarElemento() {
        Proveedores proveedorSeleccionado = tvPoveedores.getSelectionModel().getSelectedItem();
        if (proveedorSeleccionado != null) {
            txtCodigoP.setText(String.valueOf(proveedorSeleccionado.getCodigoProveedor()));
            txtNITP.setText(proveedorSeleccionado.getNITproveedor());
            txtNombresP.setText(proveedorSeleccionado.getNombreProveedor());
            txtApellidosP.setText(proveedorSeleccionado.getApellidoProveedor());
            txtDireccionP.setText(proveedorSeleccionado.getDireccionProveedor());
            txtRazonSocial.setText(proveedorSeleccionado.getRazonSocial());
            txtContactoP.setText(proveedorSeleccionado.getContactoPrincipal());
            txtSitioWeb.setText(proveedorSeleccionado.getPaginaWeb());
        }
    }

    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> listaPro = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_Listarproveedor()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaPro.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("NITproveedor"),
                        resultado.getString("nombreProveedor"),
                        resultado.getString("apellidoProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FXCollections.observableList(listaPro);
    }
    
    public void Agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregarP.setText("Guardar");
                btnEliminarP.setText("Cancelar");
                btnEditarP.setDisable(true);
                btnReportesP.setDisable(true);
                imgAgregar.setImage(new Image("org/andersonlopez/images/Guardar.png"));
                imgEliminar.setImage(new Image("org/andersonlopez/images/Cancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                limpiarControles();

                desactivarControles();
                btnAgregarP.setText("Agregar");
                btnEliminarP.setText("Eliminar");
                btnEditarP.setDisable(false);
                btnReportesP.setDisable(false);
                imgAgregar.setImage (new Image("/org/andersonlopez/images/add user.png")); 
                imgEliminar.setImage(new Image("/org/andersonlopez/images/delete user.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void guardar() {
        Proveedores registro = new Proveedores();
        registro.setCodigoProveedor(Integer.parseInt(txtCodigoP.getText()));
        registro.setNITproveedor(txtNITP.getText());
        registro.setNombreProveedor(txtNombresP.getText());
        registro.setApellidoProveedor(txtApellidosP.getText());
        registro.setDireccionProveedor(txtDireccionP.getText());
        registro.setRazonSocial(txtRazonSocial.getText());
        registro.setContactoPrincipal(txtContactoP.getText());
        registro.setPaginaWeb(txtSitioWeb.getText());

        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_agregarProveedor(?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITproveedor());
            procedimiento.setString(3, registro.getNombreProveedor());
            procedimiento.setString(4, registro.getApellidoProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
            listaProveedores.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarP.setText("Agregar");
                btnEliminarP.setText("Eliminar");
                btnEditarP.setDisable(false);
                btnReportesP.setDisable(false);
                imgAgregar.setImage (new Image("/org/andersonlopez/images/add user.png")); 
                imgEliminar.setImage(new Image("/org/andersonlopez/images/delete user.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tvPoveedores.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmas la eliminacion del registro?", 
                            "Eliminar Proveedores", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_eliminarproveedor(?)}");
                            procedimiento.setInt(1, ((Proveedores) tvPoveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                            procedimiento.execute();
                            listaProveedores.remove(tvPoveedores.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente para eliminar");
                }

                break;
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tvPoveedores.getSelectionModel().getSelectedItem() != null) {
                    btnEditarP.setText("Actualizar");
                    btnReportesP.setText("Cancelar");
                    btnEliminarP.setDisable(true);
                    btnAgregarP.setDisable(true);
                    imgEditar.setImage(new Image("/org/andersonlopez/images/add user.png"));
                    imgReporte.setImage(new Image("/org/andersonlopez/images/Cancelar.png"));
                    activarControles();
                    txtCodigoP.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditarP.setText("Editar");
                btnReportesP.setText("Reportes");
                btnAgregarP.setDisable(false);
                btnEliminarP.setDisable(false);
                imgEditar.setImage (new Image("/org/andersonlopez/images/edit user.png"));
                imgReporte.setImage (new Image("/org/andersonlopez/images/usereport.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void reportes() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditarP.setText("Editar");
                btnReportesP.setText("Reportes");
                btnAgregarP.setDisable(false);
                btnEliminarP.setDisable(false);
                imgEditar.setImage(new Image("/org/andersonlopez/images/edit user.png"));
                imgReporte.setImage(new Image("/org/andersonlopez/images/usereport.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_editarProveedor(?, ?, ?, ?, ?, ?, ?, ?)}");
            Proveedores registro = (Proveedores) tvPoveedores.getSelectionModel().getSelectedItem();
            registro.setCodigoProveedor(Integer.parseInt(txtCodigoP.getText()));
            registro.setNITproveedor(txtNITP.getText());
            registro.setNombreProveedor(txtNombresP.getText());
            registro.setApellidoProveedor(txtApellidosP.getText());
            registro.setDireccionProveedor(txtDireccionP.getText());
            registro.setRazonSocial(txtRazonSocial.getText());
            registro.setContactoPrincipal(txtContactoP.getText());
            registro.setPaginaWeb(txtSitioWeb.getText());
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITproveedor());
            procedimiento.setString(3, registro.getNombreProveedor());
            procedimiento.setString(4, registro.getApellidoProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desactivarControles() {
        txtCodigoP.setEditable(false);
        txtNITP.setEditable(false);
        txtNombresP.setEditable(false);
        txtApellidosP.setEditable(false);
        txtDireccionP.setEditable(false);
        txtRazonSocial.setEditable(false);
        txtContactoP.setEditable(false);
        txtSitioWeb.setEditable(false);
    }

    public void activarControles() {
        txtCodigoP.setEditable(true);
        txtNITP.setEditable(true);
        txtNombresP.setEditable(true);
        txtApellidosP.setEditable(true);
        txtDireccionP.setEditable(true);
        txtRazonSocial.setEditable(true);
        txtContactoP.setEditable(true);
        txtSitioWeb.setEditable(true);
    }

    public void limpiarControles() {
        txtCodigoP.clear();
        txtNITP.clear();
        txtNombresP.clear();
        txtApellidosP.clear();
        txtDireccionP.clear();
        txtRazonSocial.clear();
        txtContactoP.clear();
        txtSitioWeb.clear();
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
