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
import org.andersonlopez.bean.CargoEmpleado;
import org.andersonlopez.db.Conexion;
import org.andersonlopez.system.Main;

public class MenuCargosEmpleadosController implements Initializable{
    private Main escenarioPrincipal;
    private enum operaciones {AGREGAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO}
        private operaciones tipoDeOperaciones = operaciones.NINGUNO;
        private ObservableList<CargoEmpleado> ListarCargo;
    @FXML private Button btnRegresar;
    @FXML private TextField txtCodigoCargoEmpleado;
    @FXML private TextField txtnombreCargo;
    @FXML private TextField txtDescripcion;
    @FXML private TableView tblCargosEmp;
    @FXML private TableColumn colCodigoCargoEmpleado;
    @FXML private TableColumn colNombreCargo;
    @FXML private TableColumn colDes;
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
        tblCargosEmp.setItems(getCargoEmpleado());
        colCodigoCargoEmpleado.setCellValueFactory (new PropertyValueFactory<CargoEmpleado, Integer>("codigoCargoEmpleado"));
        colNombreCargo.setCellValueFactory (new PropertyValueFactory<CargoEmpleado, String>("nombreCargo"));
        colDes.setCellValueFactory (new PropertyValueFactory<CargoEmpleado, String>("descripcionCargo"));
    }
    
        public void seleccionarElemento(){
        txtCodigoCargoEmpleado.setText(String.valueOf(((CargoEmpleado)tblCargosEmp.getSelectionModel().getSelectedItem()).getClass()));
        txtnombreCargo.setText(((CargoEmpleado) tblCargosEmp.getSelectionModel().getSelectedItem()).getNombreCargo());
        txtDescripcion.setText(((CargoEmpleado) tblCargosEmp.getSelectionModel().getSelectedItem()).getDescripcionCargo());
        
    }
    
    public ObservableList<CargoEmpleado> getCargoEmpleado(){
        ArrayList<CargoEmpleado> lista = new ArrayList<>();
        try{
            PreparedStatement procediiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarCargoEmpleado()}");
            ResultSet resultado = procediiento.executeQuery();
            while(resultado.next()){
                lista.add(new CargoEmpleado (resultado.getInt("codigoCargoEmpleado"),
                                                        resultado.getString("nombreCargo"),
                                                        resultado.getString("descripcionCargo")

                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListarCargo = FXCollections.observableArrayList(lista);
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
                tipoDeOperaciones= MenuCargosEmpleadosController.operaciones.ACTUALIZAR;
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
                tipoDeOperaciones= MenuCargosEmpleadosController.operaciones.NINGUNO;
        }
    }
    
    public void guardar (){
        CargoEmpleado registro = new CargoEmpleado();
        registro.setCodigoCargoEmpleado(Integer.parseInt(txtCodigoCargoEmpleado.getText()));
        registro.setNombreCargo(txtDescripcion.getText());
        registro.setDescripcionCargo(txtDescripcion.getText());
       try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall ("{call sp_AgregarCargoEmpleado(?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
            procedimiento.execute();
            ListarCargo.add(registro);
            
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
                tipoDeOperaciones= MenuCargosEmpleadosController.operaciones.NINGUNO;
                break;
            default:
                if(tblCargosEmp.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si Elimina el registro", 
                            "Eliminar Clientes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall(" {call sp_EliminarCargoEmpleado(?)}");
                            procedimiento.setInt(1, ((CargoEmpleado) tblCargosEmp.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()); 
                            procedimiento.execute();
                            ListarCargo.remove (tblCargosEmp.getSelectionModel().getSelectedItem());
                            
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
                if (tblCargosEmp.getSelectionModel().getSelectedItem() != null) { 
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar"); 
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/andersonlopez/images/add user.png"));
                    imgReporte.setImage(new Image("/org/andersonlopez/images/Cancelar.png"));
                    activarControles();
                    txtCodigoCargoEmpleado.setEditable(false);
                    tipoDeOperaciones = MenuCargosEmpleadosController.operaciones.ACTUALIZAR;
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
                tipoDeOperaciones = MenuCargosEmpleadosController.operaciones.NINGUNO;
                CargarDatos();
            break;
        }
    }
    
    public void Actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall(" {call sp_EditarCargoEmpleado(?, ?, ?)}");
            CargoEmpleado registro = (CargoEmpleado) tblCargosEmp.getSelectionModel().getSelectedItem();
            registro.setNombreCargo(txtDescripcion.getText());
            registro.setDescripcionCargo(txtDescripcion.getText());
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
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
                tipoDeOperaciones = MenuCargosEmpleadosController.operaciones.NINGUNO;

        }
    }
    
    public void desactivarControles () {
        txtCodigoCargoEmpleado.setEditable (false);
        txtDescripcion.setEditable (false);
        txtnombreCargo.setEditable(false);
        
    }
    
    public void activarControles () {
        txtCodigoCargoEmpleado.setEditable (true);
        txtDescripcion.setEditable(true);
        txtnombreCargo.setEditable(true);
        
    }
    
    public void limpiarControles () {
        txtCodigoCargoEmpleado.clear();
        txtDescripcion.clear();
        txtnombreCargo.clear();
        
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
