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
    
  //  @FXML MenuItem btnMenuClientes;
    @FXML MenuItem btnClientes;
    @FXML MenuItem btnProgramador;
    @FXML MenuItem btnProveedores;
    @FXML MenuItem btnCargos;
    @FXML MenuItem btnTipoPr;
    @FXML MenuItem btnCompras;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }    
    
    @FXML
    public void clicClientes (ActionEvent event){
       if (event.getSource() == btnClientes){
          escenarioPrincipal.menuClientes();
        }
    }
    
    @FXML
    public void clicProgramador (ActionEvent event){
       if (event.getSource() == btnProgramador){
          escenarioPrincipal.menuProgramador();
        }
    }
    
        @FXML
    public void clicProveedores (ActionEvent event){
       if (event.getSource() == btnProveedores){
          escenarioPrincipal.menuProveedores();
        }
    }
    
     @FXML
    public void clicTipoProductos (ActionEvent event){
       if (event.getSource() == btnTipoPr){
          escenarioPrincipal.menuTipoProducto();
        }
    }
    
     @FXML
    public void clicCargos (ActionEvent event){
       if (event.getSource() == btnCargos){
          escenarioPrincipal.menuCargos();
        }
    }
    
     @FXML
    public void clicCompras(ActionEvent event){
       if (event.getSource() == btnCompras){
          escenarioPrincipal.menuCompras();
        }
    }

   /* @FXML
    public void handleButtonAction (ActionEvent event){
        if (event.getSource() == btnMenuClientes){
        escenarioPrincipal.menuClientesView();
        }
    }*/
   
}
