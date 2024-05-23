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
import org.andersonlopez.bean.Empleados;
import org.andersonlopez.db.Conexion;
import org.andersonlopez.system.Main;



public class EmpleadosController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Empleados> listarEmpleados;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtCodigoEmpleado;
    @FXML
    private TextField txtNombresEmpleado;
    @FXML
    private TextField txtApellidosEmpleado;
    @FXML
    private TextField txtSueldo;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtTurno;
    @FXML
    private TextField txtCodigoCargoEmpleado;
    @FXML
    private TableView tblEmpleados;
    @FXML
    private TableColumn colCodigoEmpleado;
    @FXML
    private TableColumn colNombresEmpleado;
    @FXML
    private TableColumn colApellidosEmpleado;
    @FXML
    private TableColumn colSueldo;
    @FXML
    private TableColumn colDireccion;
    @FXML
    private TableColumn colTurno;
    @FXML
    private TableColumn colCodigoCargoEmpleado;
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
            ObservableList<Empleados> empleados = getEmpleados();
            tblEmpleados.setItems(empleados);
            colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoEmpleado"));
            colNombresEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombresEmpleado"));
            colApellidosEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellidosEmpleado"));
            colSueldo.setCellValueFactory(new PropertyValueFactory<Empleados, Double>("sueldo"));
            colDireccion.setCellValueFactory(new PropertyValueFactory<Empleados, String>("direccion"));
            colTurno.setCellValueFactory(new PropertyValueFactory<Empleados, String>("turno"));
            colCodigoCargoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoCargoEmpleado"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void seleccionarElemento() {
        Empleados empleadoSeleccionado = (Empleados) tblEmpleados.getSelectionModel().getSelectedItem();
        if (empleadoSeleccionado != null) {
            txtCodigoEmpleado.setText(String.valueOf(empleadoSeleccionado.getCodigoEmpleado()));
            txtNombresEmpleado.setText(empleadoSeleccionado.getNombresEmpleado());
            txtApellidosEmpleado.setText(empleadoSeleccionado.getApellidosEmpleado());
            txtSueldo.setText(String.valueOf(empleadoSeleccionado.getSueldo()));
            txtDireccion.setText(empleadoSeleccionado.getDireccion());
            txtTurno.setText(empleadoSeleccionado.getTurno());
            txtCodigoCargoEmpleado.setText(String.valueOf(empleadoSeleccionado.getCodigoCargoEmpleado()));
        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún empleado");
        }
    }

    public ObservableList<Empleados> getEmpleados() {
        ArrayList<Empleados> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Empleados(
                        resultado.getInt("codigoEmpleado"),
                        resultado.getString("nombresEmpleado"),
                        resultado.getString("apellidosEmpleado"),
                        resultado.getDouble("sueldo"),
                        resultado.getString("direccion"),
                        resultado.getString("turno"),
                        resultado.getInt("codigoCargoEmpleado")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listarEmpleados = FXCollections.observableArrayList(lista);
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
        Empleados registro = new Empleados();
        registro.setCodigoEmpleado(Integer.parseInt(txtCodigoEmpleado.getText()));
        registro.setNombresEmpleado(txtNombresEmpleado.getText());
        registro.setApellidosEmpleado(txtApellidosEmpleado.getText());
        registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
        registro.setDireccion(txtDireccion.getText());
        registro.setTurno(txtTurno.getText());
        registro.setCodigoCargoEmpleado(Integer.parseInt(txtCodigoCargoEmpleado.getText()));
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarEmpleado(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getCodigoCargoEmpleado());
            procedimiento.execute();
            listarEmpleados.add(registro);

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
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Confirmar si desea eliminar el registro?",
                            "Eliminar Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarEmpleado(?)}");
                            procedimiento.setInt(1, ((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                            procedimiento.execute();
                            listarEmpleados.remove(tblEmpleados.getSelectionModel().getSelectedItem());

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
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/andersonlopez/images/edit user.png"));
                    imgReporte.setImage(new Image("/org/andersonlopez/images/Cancelar.png"));
                    activarControles();
                    txtCodigoEmpleado.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EditarEmpleado(?, ?, ?, ?, ?, ?, ?)}");
            Empleados registro = (Empleados) tblEmpleados.getSelectionModel().getSelectedItem();
            registro.setNombresEmpleado(txtNombresEmpleado.getText());
            registro.setApellidosEmpleado(txtApellidosEmpleado.getText());
            registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
            registro.setDireccion(txtDireccion.getText());
            registro.setTurno(txtTurno.getText());
            registro.setCodigoCargoEmpleado(Integer.parseInt(txtCodigoCargoEmpleado.getText()));
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getCodigoCargoEmpleado());
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
        txtCodigoEmpleado.setEditable(false);
        txtNombresEmpleado.setEditable(false);
        txtApellidosEmpleado.setEditable(false);
        txtSueldo.setEditable(false);
        txtDireccion.setEditable(false);
        txtTurno.setEditable(false);
        txtCodigoCargoEmpleado.setEditable(false);

    }

    public void activarControles() {
        txtCodigoEmpleado.setEditable(true);
        txtNombresEmpleado.setEditable(true);
        txtApellidosEmpleado.setEditable(true);
        txtSueldo.setEditable(true);
        txtDireccion.setEditable(true);
        txtTurno.setEditable(true);
        txtCodigoCargoEmpleado.setEditable(true);

    }

    public void limpiarControles() {
        txtCodigoEmpleado.clear();
        txtNombresEmpleado.clear();
        txtApellidosEmpleado.clear();
        txtSueldo.clear();
        txtDireccion.clear();
        txtTurno.clear();
        txtCodigoCargoEmpleado.clear();

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
