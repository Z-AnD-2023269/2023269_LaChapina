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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javax.swing.JOptionPane;
import org.andersonlopez.bean.Productos;
import org.andersonlopez.bean.Proveedores;
import org.andersonlopez.bean.TipoProducto;
import org.andersonlopez.db.Conexion;
import org.andersonlopez.system.Main;

/**
 *
 * @author informatica
 */
public class MenuProductosController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Productos> listaProductos;
    private ObservableList<Proveedores> listaProveedores;
    private ObservableList<TipoProducto> listaTipoDeProducto;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtCodigoProd;
    @FXML
    private TextField txtDescPro;
    @FXML
    private TextField txtPrecioU;
    @FXML
    private TextField txtPrecioD;
    @FXML
    private TextField txtPrecioM;
    @FXML
    private TextField txtExistencia;
    @FXML
    private ComboBox cmbCodigoTipoP;
    @FXML
    private ComboBox cmbCodProv;
    @FXML
    private TableView tblProductos;
    @FXML
    private TableColumn colCodProd;
    @FXML
    private TableColumn colDescProd;
    @FXML
    private TableColumn colPrecioU;
    @FXML
    private TableColumn colPrecioD;
    @FXML
    private TableColumn colPrecioM;
    @FXML
    private TableColumn colExistencia;
    @FXML
    private TableColumn colCodTipoProd;
    @FXML
    private TableColumn colCodProv;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReporte;
    @FXML
    private ImageView imagenProducto;
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
        imagenProducto.setOnDragDetected(event -> {
            Dragboard db = imagenProducto.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString("imageView");
            db.setContent(content);
            event.consume();
        });

        imagenProducto.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasString() && db.getString().equals("imageView")) {
                imagenProducto.setLayoutX(event.getX());
                imagenProducto.setLayoutY(event.getY());
                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
            event.consume();
        });

        imagenProducto.setOnDragOver(event -> {
            if (event.getGestureSource() != imagenProducto
                    && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.ANY);
            }
            event.consume();
        });

        cargaDatos();
        cmbCodigoTipoP.setItems(getTipoP());
        cmbCodProv.setItems(getProveedores());
    }

    public void cargaDatos() {
        tblProductos.setItems(getProductos());
        colCodProd.setCellValueFactory(new PropertyValueFactory<Productos, String>("codigoProducto"));
        colDescProd.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcionProducto"));
        colPrecioU.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioUnitario"));
        colPrecioD.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioDocena"));
        colPrecioM.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioMayor"));
        colExistencia.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("existencia"));
        colCodTipoProd.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoTipoProducto"));
        colCodProv.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoProveedor"));

    }

    public void selecionarElementos() {
        txtCodigoProd.setText(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
        txtDescPro.setText(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getDescripcionProducto());
        txtPrecioU.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
        txtPrecioD.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getPrecioDocena()));
        txtPrecioM.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getPrecioMayor()));
        txtExistencia.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getExistencia()));
        cmbCodigoTipoP.getSelectionModel().select(buscarTipoProducto(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
    }

    public TipoProducto buscarTipoProducto(int codigoTipoProducto) {
        TipoProducto resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_buscarTipoProducto(?)}");
            procedimiento.setInt(1, codigoTipoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new TipoProducto(registro.getInt("codigoTipoProducto"),
                        registro.getString("descripcionProducto")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    public ObservableList<Productos> getProductos() {
        ArrayList<Productos> lista = new ArrayList<Productos>();
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarTipoProducto()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Productos(resultado.getString("codigoProducto"),
                        resultado.getString("descripcionProducto"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getDouble("precioDocena"),
                        resultado.getDouble("precioMayor"),
                        resultado.getInt("existencia"),
                        resultado.getInt("codigoTipoProducto"),
                        resultado.getInt("codigoProveedor")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaProductos = FXCollections.observableArrayList(lista);

    }

    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> listaPro = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaPro.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("NITProveedor"),
                        resultado.getString("nombresProveedor"),
                        resultado.getString("apellidosProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProveedores = FXCollections.observableList(listaPro);
    }

    public ObservableList<TipoProducto> getTipoP() {
        ArrayList<TipoProducto> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarTipoProducto()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TipoProducto(resultado.getInt("CodigoTipoProducto"),
                        resultado.getString("descripcionProducto")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaTipoDeProducto = FXCollections.observableList(lista);
    }

    public void agregar() {
        switch (tipoDeOperacion) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperacion = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                cargaDatos();
                break;
        }

    }

    public void guardar() {
        Productos registro = new Productos();
        registro.setCodigoProducto(txtCodigoProd.getText());
        registro.setCodigoProveedor(((Proveedores) cmbCodProv.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        registro.setCodigoTipoProducto(((TipoProducto) cmbCodigoTipoP.getSelectionModel().getSelectedItem())
                .getCodigoTipoProducto());
        registro.setDescripcionProducto(txtDescPro.getText());
        registro.setPrecioDocena(Double.parseDouble(txtPrecioD.getText()));
        registro.setPrecioUnitario(Double.parseDouble(txtPrecioU.getText()));
        registro.setPrecioMayor(Double.parseDouble(txtPrecioM.getText()));
        registro.setExistencia(Integer.parseInt(txtExistencia.getText()));
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{CALL sp_agregarProducto(?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecioUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setInt(6, registro.getExistencia());
            procedimiento.setInt(7, registro.getCodigoProveedor());
            procedimiento.setInt(8, registro.getCodigoTipoProducto());
            procedimiento.execute();

            listaProductos.add(registro);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void Eliminar() {
        switch (tipoDeOperacion) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/andersonlopez/images/add user.png"));
                imgEliminar.setImage(new Image("/org/andersonlopez/images/delete user.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default:
                if (tblProductos.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si Elimina el registro",
                            "Eliminar Producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall(" {call sp_eliminarProducto(?)}");
                            procedimiento.setString(1, ((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
                            procedimiento.execute();
                            listaProductos.remove(tblProductos.getSelectionModel().getSelectedItem());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un Elemento");
                }
        }
    }

    public void Editar() {
        switch (tipoDeOperacion) {
            case NINGUNO:
                if (tblProductos.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/andersonlopez/images/add user.png"));
                    imgReporte.setImage(new Image("/org/andersonlopez/images/Cancelar.png"));
                    activarControles();
                    txtCodigoProd.setEditable(false);
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un Elemento");
                }
                break;
            case ACTUALIZAR:
                Actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/andersonlopez/images/edit user.png"));
                imgReporte.setImage(new Image("/org/andersonlopez/images/usereport.png"));
                limpiarControles();
                tipoDeOperacion = operaciones.NINGUNO;
                cargaDatos();
                break;
        }
    }

    public void Actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall(" {call sp_actualizarProducto(?, ?, ?, ?, ?, ?, ?)}");
            Productos registro = new Productos();
            registro.setCodigoProducto(txtCodigoProd.getText());
            registro.setCodigoProveedor(((Proveedores) cmbCodProv.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            registro.setCodigoTipoProducto(((TipoProducto) cmbCodigoTipoP.getSelectionModel().getSelectedItem())
                    .getCodigoTipoProducto());
            registro.setDescripcionProducto(txtDescPro.getText());
            registro.setPrecioDocena(Double.parseDouble(txtPrecioD.getText()));
            registro.setPrecioUnitario(Double.parseDouble(txtPrecioU.getText()));
            registro.setPrecioMayor(Double.parseDouble(txtPrecioM.getText()));
            registro.setExistencia(Integer.parseInt(txtExistencia.getText()));
            procedimiento.setString(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecioUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setInt(6, registro.getExistencia());
            procedimiento.setInt(7, registro.getCodigoProveedor());
            procedimiento.setInt(8, registro.getCodigoTipoProducto());
            procedimiento.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reporte() {
        switch (tipoDeOperacion) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/andersonlopez/images/edit user.png"));
                imgReporte.setImage(new Image("/org/andersonlopez/images/usereport.png"));
                tipoDeOperacion = operaciones.NINGUNO;

        }
    }

    public void desactivarControles() {
        txtCodigoProd.setEditable(false);
        txtDescPro.setEditable(false);
        txtPrecioU.setEditable(false);
        txtPrecioD.setEditable(false);
        txtPrecioM.setEditable(false);
        txtExistencia.setEditable(false);
        cmbCodigoTipoP.setDisable(true);
        cmbCodProv.setDisable(true);

    }

    public void activarControles() {
        txtCodigoProd.setEditable(true);
        txtDescPro.setEditable(true);
        txtPrecioU.setEditable(true);
        txtPrecioD.setEditable(true);
        txtPrecioM.setEditable(true);
        txtExistencia.setEditable(true);
        cmbCodigoTipoP.setDisable(false);
        cmbCodProv.setDisable(false);

    }

    public void limpiarControles() {
        txtCodigoProd.clear();
        txtDescPro.clear();
        txtPrecioU.clear();
        txtPrecioD.clear();
        txtPrecioM.clear();
        txtExistencia.clear();
        tblProductos.getSelectionModel().getSelectedItem();
        cmbCodigoTipoP.getSelectionModel().getSelectedItem();
        cmbCodProv.getSelectionModel().getSelectedItem();

    }

    /*@FXML
    private void onDragDetected(MouseEvent event) {
        Dragboard db = tblProductos.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putString(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
        db.setContent(content);
        event.consume();
    }

    @FXML
    private void onDragOver(DragEvent event) {
        if (event.getGestureSource() != tblProductos && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    @FXML
    private void onDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    private void onDragDone(DragEvent event) {
        if (event.getTransferMode() == TransferMode.MOVE) {
        }
        event.consume();
    }*/

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
