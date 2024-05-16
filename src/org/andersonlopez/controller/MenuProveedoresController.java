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

public class MenuProveedoresController implements Initializable{
            private Main escenarioPrincipal;
        private enum operaciones {AGREGAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO}
        private operaciones tipoDeOperaciones = operaciones.NINGUNO;
        private ObservableList<Proveedores> listarProveedores;
    @FXML private Button btnRegresar;
    @FXML private TextField txtDireccionP;
    @FXML private TextField txtRazonSocialP;
    @FXML private TextField txtCodigoP;
    @FXML private TextField txtNitP;
    @FXML private TextField txtNombreP;
    @FXML private TextField txtApellidoP;
    @FXML private TextField txtContactoP;
    @FXML private TextField txtPaginaWebP;
    @FXML private TableView tblProveedores;
    @FXML private TableColumn colCodigoP;
    @FXML private TableColumn colNombreP;
    @FXML private TableColumn colApellidoP;
    @FXML private TableColumn colNitP;
    @FXML private TableColumn colRazonSP;
    @FXML private TableColumn colContactoP;
    @FXML private TableColumn colDireccionP;
    @FXML private TableColumn colPaginaWebP;
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CargarDatos();
    }
    
    public void CargarDatos(){
        tblProveedores.setItems(getProveedores());
        colCodigoP.setCellValueFactory (new PropertyValueFactory<Proveedores, Integer>("codigoProveedor"));
        colNitP.setCellValueFactory (new PropertyValueFactory<Proveedores, String>("NITproveedor"));
        colNombreP.setCellValueFactory (new PropertyValueFactory<Proveedores, String>("nombreProveedor"));
        colApellidoP.setCellValueFactory (new PropertyValueFactory<Proveedores, String>("apellidoProveedor"));
        colDireccionP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("direccionProveedor"));
        colRazonSP.setCellValueFactory (new PropertyValueFactory<Proveedores, String>("razonSocial")); 
        colPaginaWebP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("paginaWeb"));
        colContactoP.setCellValueFactory (new PropertyValueFactory<Proveedores, String>("contactoPrincipal"));

    }
    
        public void seleccionarElemento(){
        txtCodigoP.setText(String.valueOf(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getClass()));
        txtNitP.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getNITproveedor());
        txtNombreP.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getNombreProveedor());
        txtApellidoP.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getApellidoProveedor());
        txtDireccionP.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getDireccionProveedor());
        txtRazonSocialP.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getRazonSocial());
        txtPaginaWebP.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getPaginaWeb());
        txtContactoP.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getContactoPrincipal());
        
    }
    
    public ObservableList<Proveedores> getProveedores(){
        ArrayList<Proveedores> lista = new ArrayList<>();
        try{
            PreparedStatement procediiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarProveedores()}");
            ResultSet resultado = procediiento.executeQuery();
            while(resultado.next()){
                lista.add(new Proveedores (resultado.getInt("codigoProveedor"),
                                                        resultado.getString("NITproveedor"),
                                                        resultado.getString("nombreProveedor"),
                                                        resultado.getString("apellidoProveedor"),  
                                                        resultado.getString("direccionProveedor"),
                                                        resultado.getString("razonSocial"),
                                                        resultado.getString("paginaWeb"),
                                                        resultado.getString("contactoPrincipal")

                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarProveedores = FXCollections.observableArrayList(lista);
    }
    
    public void agregar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("org/andersonlopez/images/Guardar.png"));
                imgEliminar.setImage(new Image("org/andersonlopez/images/Cancelar.png"));
                tipoDeOperaciones= operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable (false);
                btnReporte.setDisable (false);
                imgAgregar.setImage (new Image("/org/andersonlopez/images/add user.png")); 
                imgEliminar.setImage(new Image("/org/andersonlopez/images/delete user.png"));
                tipoDeOperaciones= operaciones.NINGUNO;
        }
    }
    
    public void guardar (){
        Proveedores registro = new Proveedores();
        registro.setCodigoProveedor (Integer.parseInt(txtCodigoP.getText()));
        registro.setNITproveedor (txtNitP.getText());
        registro.setNombreProveedor (txtNombreP.getText());
        registro.setApellidoProveedor (txtApellidoP.getText());
        registro.setDireccionProveedor (txtDireccionP.getText());
        registro.setRazonSocial (txtRazonSocialP.getText());
        registro.setPaginaWeb (txtPaginaWebP.getText());
        registro.setContactoPrincipal (txtContactoP.getText());
       try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall ("{call sp_AgregarProveedores(?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITproveedor());
            procedimiento.setString (3, registro.getNombreProveedor());
            procedimiento.setString (4, registro.getApellidoProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor()); 
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString (7, registro.getPaginaWeb()); 
            procedimiento.setString (8, registro.getContactoPrincipal()); 
            procedimiento.execute();
            listarProveedores.add(registro);
            
        } catch (Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void Eliminar(){
        switch(tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable (false);
                btnReporte.setDisable (false);
                imgAgregar.setImage (new Image("/org/andersonlopez/images/add user.png")); 
                imgEliminar.setImage(new Image("/org/andersonlopez/images/delete user.png"));
                tipoDeOperaciones= operaciones.NINGUNO;
                break;
            default:
                if(tblProveedores.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si Elimina el registro", 
                            "Eliminar Proveedores", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall(" {call sp_EliminarProveedores(?)}");
                            procedimiento.setInt(1, ((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()); procedimiento.execute();
                            listarProveedores.remove (tblProveedores.getSelectionModel().getSelectedItem());
                            
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    
                }else
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un Elemento");
        }
    }
    
    public void Editar(){
        switch (tipoDeOperaciones) { 
            case NINGUNO:
                if (tblProveedores.getSelectionModel().getSelectedItem() != null) { 
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar"); 
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/andersonlopez/images/add user.png"));
                    imgReporte.setImage(new Image("/org/andersonlopez/images/Cancelar.png"));
                    activarControles();
                    txtCodigoP.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                }else 
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un Elemento");
                break;
            case ACTUALIZAR:
                Actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable (false);
                imgEditar.setImage (new Image("/org/andersonlopez/images/edit user.png"));
                imgReporte.setImage (new Image("/org/andersonlopez/images/usereport.png"));
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                CargarDatos();
            break;
        }
    }
    
    public void Actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall(" {call sp_EditarProveedores(?, ?, ?, ?, ?, ?, ?, ?)}");
            Proveedores registro = (Proveedores) tblProveedores.getSelectionModel().getSelectedItem();
            registro.setNITproveedor (txtNitP.getText());
            registro.setNombreProveedor (txtNombreP.getText());
            registro.setApellidoProveedor (txtApellidoP.getText());
            registro.setDireccionProveedor (txtDireccionP.getText());
            registro.setRazonSocial (txtRazonSocialP.getText());
            registro.setContactoPrincipal (txtContactoP.getText());
            registro.setPaginaWeb (txtPaginaWebP.getText());
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITproveedor());
            procedimiento.setString (3, registro.getNombreProveedor());
            procedimiento.setString (4, registro.getApellidoProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor()); 
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString (7, registro.getContactoPrincipal()); 
            procedimiento.setString (8, registro.getPaginaWeb()); 
            procedimiento.execute();
            
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void reporte (){
        switch(tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable (false);
                imgEditar.setImage(new Image("/org/andersonlopez/images/edit user.png"));
                imgReporte.setImage(new Image("/org/andersonlopez/images/usereport.png"));
                tipoDeOperaciones = operaciones.NINGUNO;

        }
    }
    
    public void desactivarControles () {
        txtCodigoP.setEditable (false);
        txtNombreP.setEditable (false);
        txtApellidoP.setEditable (false);
        txtDireccionP.setEditable (false);
        txtRazonSocialP.setEditable (false);
        txtNitP.setEditable (false);
        txtContactoP.setEditable (false);
         txtPaginaWebP.setEditable(false);
        
    }
    
    public void activarControles () {
        txtCodigoP.setEditable(true);
        txtNombreP.setEditable(true);
        txtApellidoP.setEditable(true); 
        txtDireccionP.setEditable (true);
        txtRazonSocialP.setEditable(true);
        txtNitP.setEditable (true);
        txtContactoP.setEditable(true);
        txtPaginaWebP.setEditable(true);

        
    }
    
    public void limpiarControles () {
        txtCodigoP.clear();
        txtNombreP.clear();
        txtApellidoP.clear();
        txtDireccionP.clear();
        txtRazonSocialP.clear();
        txtNitP.clear();
        txtContactoP.clear();
        txtPaginaWebP.clear();
        
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void clicRegresar (ActionEvent event){
        if (event.getSource() == btnRegresar){
            escenarioPrincipal.menuPrincipal();
        }
    }
    

}
