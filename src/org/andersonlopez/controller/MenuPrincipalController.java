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
    @FXML
    private MenuItem btnProductos;
    @FXML
    private MenuItem btnDetCompra;
    @FXML
    private MenuItem btnEmpleados;
    @FXML
    private MenuItem btnFactura;
    @FXML
    private MenuItem btnDetFactura;
    @FXML
    private MenuItem btnTelProveedores;
    @FXML
    private MenuItem btnEmailProveedores;

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
    
    @FXML
    public void clicProductos(ActionEvent event) {
        if (event.getSource() == btnProductos) {
            escenarioPrincipal.menuProducto();
        }
    }

    @FXML
    public void clicDetCompra(ActionEvent event) {
        if (event.getSource() == btnDetCompra) {
            escenarioPrincipal.menuDetCompra();
        }
    }

    @FXML
    public void clicEmpleados(ActionEvent event) {
        if (event.getSource() == btnEmpleados) {
            escenarioPrincipal.menuEmpleados();
        }
    }

    @FXML
    public void clicFactura(ActionEvent event) {
        if (event.getSource() == btnFactura) {
            escenarioPrincipal.menuFactura();
        }
    }

    @FXML
    public void clicDetFactura(ActionEvent event) {
        if (event.getSource() == btnDetFactura) {
            escenarioPrincipal.menuDetFactura();
        }
    }

    @FXML
    public void clicTelProveedores(ActionEvent event) {
        if (event.getSource() == btnTelProveedores) {
            escenarioPrincipal.menuTelProveedores();
        }
    }

    @FXML
    public void clicEmailProveedores(ActionEvent event) {
        if (event.getSource() == btnEmailProveedores) {
            escenarioPrincipal.menuEmailProveedores();
        }
    }

}
