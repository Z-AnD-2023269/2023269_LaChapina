package org.andersonlopez.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.andersonlopez.controller.MenuCargosEmpleadosController;
import org.andersonlopez.controller.MenuClientesController;
import org.andersonlopez.controller.MenuComprasController;
import org.andersonlopez.controller.MenuPrincipalController;
import org.andersonlopez.controller.MenuProgramadorController;
import org.andersonlopez.controller.MenuProveedoresController;
import org.andersonlopez.controller.MenuTipoProductosController;

public class Main extends Application {

    private Stage escenarioPrincipal;
    private Scene escena;
    private final String URLVIEW = "/org/andersonlopez/view/";

    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("La Chapina");
        escenarioPrincipal.getIcons().add(new Image(Main.class.getResourceAsStream("/org/andersonlopez/images/Logo.png")));
        menuPrincipal();
        escenarioPrincipal.show();
    }

    public Initializable cambiarEscena(String fxmlName, int width, int height) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream file = Main.class.getResourceAsStream(URLVIEW + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(URLVIEW + fxmlName));

        escena = new Scene((AnchorPane) loader.load(file), width, height);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();

        return (Initializable) loader.getController();
    }

    public void menuPrincipal() {
        try {
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 1193, 791);
            menuPrincipalView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuClientes() {
        try {
            MenuClientesController menuClienteView = (MenuClientesController) cambiarEscena("MenuClienteView.fxml", 1187, 790);
            menuClienteView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuProgramador() {
        try {
            MenuProgramadorController menuProgramadorView = (MenuProgramadorController) cambiarEscena("MenuProveedoresView.fxml", 1187, 790);
            menuProgramadorView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuProveedores() {
        try {
            MenuProveedoresController menuProveedoresView = (MenuProveedoresController) cambiarEscena("MenuProveedoresView.fxml", 1187, 790);
            menuProveedoresView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuCargos() {
        try {
            MenuCargosEmpleadosController menuCargosView = (MenuCargosEmpleadosController) cambiarEscena("MenuCargosEmpleadoView.fxml", 1187, 790);
            menuCargosView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuCompras() {
        try {
            MenuComprasController menuComprasView = (MenuComprasController) cambiarEscena("MenuComprasView.fxml", 1187, 790);
            menuComprasView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuTipoProducto() {
        try {
            MenuTipoProductosController menuTipoProductoView = (MenuTipoProductosController) cambiarEscena("MenuTipoProductoView.fxml", 1187, 790);
            menuTipoProductoView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
