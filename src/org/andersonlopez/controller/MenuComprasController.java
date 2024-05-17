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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.andersonlopez.bean.Compras;
import org.andersonlopez.db.Conexion;
import org.andersonlopez.system.Main;

public class MenuComprasController implements Initializable{
    private Main escenarioPrincipal;
    private enum operaciones {AGREGAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO}
        private operaciones tipoDeOperaciones = operaciones.NINGUNO;
        private ObservableList<Compras> ListarCompras;
        
    @FXML private DatePicker dpFechaDocumento;
    @FXML private Button btnRegresar;
    @FXML private TextField txtNumeroDocumento;
    @FXML private TextField txtFechaDocumento;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtTotalDocumento;
    @FXML private TableView tblCompras;
    @FXML private TableColumn colNumeroDocumento;
    @FXML private TableColumn colFechaDocumento;
    @FXML private TableColumn colDes;
    @FXML private TableColumn colTotalDocumento;
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
        tblCompras.setItems(getCompras());
        colNumeroDocumento.setCellValueFactory (new PropertyValueFactory<Compras, Integer>("numeroDocumento"));
        colFechaDocumento.setCellValueFactory (new PropertyValueFactory<Compras, Double>("fechaDocumento"));
        colDes.setCellValueFactory (new PropertyValueFactory<Compras, String>("descripcion"));
        colTotalDocumento.setCellValueFactory (new PropertyValueFactory<Compras, String>("totalDocumento"));
    }
    
        public void seleccionarElemento(){
        txtNumeroDocumento.setText(String.valueOf(((Compras)tblCompras.getSelectionModel().getSelectedItem()).getClass()));
        txtFechaDocumento.setText(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getFechaDocumento());
        txtDescripcion.setText(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getDescripcion());
        txtTotalDocumento.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getTotalDocumento()));
        
    }
    
    public ObservableList<Compras> getCompras(){
        ArrayList<Compras> lista = new ArrayList<>();
        try{
            PreparedStatement procediiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_listarCompras()}");
            ResultSet resultado = procediiento.executeQuery();
            while(resultado.next()){
                lista.add(new Compras (resultado.getInt("numeroDocumento"),
                                                        resultado.getString("fechaDocumento"),
                                                        resultado.getString("descripcion"),
                                                        resultado.getDouble("totalDocumento")

                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListarCompras = FXCollections.observableArrayList(lista);
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
                tipoDeOperaciones= MenuComprasController.operaciones.ACTUALIZAR;
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
                tipoDeOperaciones= MenuComprasController.operaciones.NINGUNO;
        }
    }
    
    public void guardar (){
        Compras registro = new Compras();
        registro.setNumeroDocumento(Integer.parseInt(txtNumeroDocumento.getText()));
        registro.setFechaDocumento(dpFechaDocumento.getValue().toString());
        registro.setDescripcion(txtDescripcion.getText());
        registro.setTotalDocumento(Double.parseDouble(txtTotalDocumento.getText()));
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall ("{call sp_editarCompra(?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getFechaDocumento());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setDouble(4, registro.getTotalDocumento());
            procedimiento.execute();
            ListarCompras.add(registro);

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
                tipoDeOperaciones= MenuComprasController.operaciones.NINGUNO;
                break;
            default:
                if(tblCompras.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si Elimina el registro", 
                            "Eliminar Clientes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall(" {call sp_eliminarCompra(?)}");
                            procedimiento.setInt(1, ((Compras) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento()); 
                            procedimiento.execute();
                            ListarCompras.remove (tblCompras.getSelectionModel().getSelectedItem());
                            
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
                if (tblCompras.getSelectionModel().getSelectedItem() != null) { 
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar"); 
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/andersonlopez/images/add user.png"));
                    imgReporte.setImage(new Image("/org/andersonlopez/images/Cancelar.png"));
                    activarControles();
                    txtNumeroDocumento.setEditable(false);
                    tipoDeOperaciones = MenuComprasController.operaciones.ACTUALIZAR;
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
                tipoDeOperaciones = MenuComprasController.operaciones.NINGUNO;
                CargarDatos();
            break;
        }
    }
    
    public void Actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall(" {call sp_EditarCompra(?, ?, ?)}");
            Compras registro = (Compras) tblCompras.getSelectionModel().getSelectedItem();
            registro.setFechaDocumento(dpFechaDocumento.getValue().toString());
            registro.setDescripcion(txtDescripcion.getText());
            registro.setTotalDocumento(Double.parseDouble(txtTotalDocumento.getText()));
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getFechaDocumento());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setDouble(4, registro.getTotalDocumento());
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
                tipoDeOperaciones = MenuComprasController.operaciones.NINGUNO;

        }
    }
    
    public void desactivarControles () {
        txtNumeroDocumento.setEditable (false);
        txtDescripcion.setEditable (false);
        dpFechaDocumento.setEditable(false);
        txtTotalDocumento.setEditable(false);
        
    }
    
    public void activarControles () {
        txtNumeroDocumento.setEditable (true);
        txtDescripcion.setEditable(true);
        dpFechaDocumento.setEditable(true);
        txtTotalDocumento.setEditable(true);
        
    }
    
    public void limpiarControles () {
        txtNumeroDocumento.clear();
        txtDescripcion.clear();
        txtTotalDocumento.clear();
        
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
