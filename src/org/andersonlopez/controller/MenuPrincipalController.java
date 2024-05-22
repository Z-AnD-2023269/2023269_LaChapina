package org.andersonlopez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.andersonlopez.system.Main;

public class MenuPrincipalController implements Initializable {

    private Main escenarioPrincipal;

    @FXML
    private MenuItem btnClientes;
    @FXML
    private MenuItem btnProgramador;
    @FXML
    private MenuItem btnProveedores;
    @FXML
    private MenuItem btnCargos;
    @FXML
    private MenuItem btnTipoPr;
    @FXML
    private MenuItem btnCompras;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Aquí puedes inicializar elementos si es necesario
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void menuPrincipal() {
        escenarioPrincipal.menuPrincipal();
    }

    @FXML
    public void clicClientes(ActionEvent event) {
        if (event.getSource() == btnClientes) {
            escenarioPrincipal.menuClientes();
        }
    }

    @FXML
    public void clicProgramador(ActionEvent event) {
        if (event.getSource() == btnProgramador) {
            escenarioPrincipal.menuProgramador();
        }
    }

    @FXML
    public void clicProveedores(ActionEvent event) {
        if (event.getSource() == btnProveedores) {
            escenarioPrincipal.menuProveedores();
        }
    }

    @FXML
    public void clicTipoProductos(ActionEvent event) {
        if (event.getSource() == btnTipoPr) {
            escenarioPrincipal.menuTipoProducto();
        }
    }

    @FXML
    public void clicCargos(ActionEvent event) {
        if (event.getSource() == btnCargos) {
            escenarioPrincipal.menuCargos();
        }
    }

    @FXML
    public void clicCompras(ActionEvent event) {
        if (event.getSource() == btnCompras) {
            escenarioPrincipal.menuCompras();
        }
    }
}
