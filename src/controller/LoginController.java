package controller;

import application.Main;
import exception.EmptyFieldException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
	
	private Main main;
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	@FXML
	void initialize() {
		identificationInput.setFocusTraversable(false);
		passwordInput.setFocusTraversable(false);
	}
	
	@FXML
	Button loginButton;
	
	@FXML
	TextField identificationInput;
	
	@FXML
	PasswordField passwordInput;
	
	@FXML
	void login() {
		try {
			if (identificationInput.getText().equals("")) {
				throw new EmptyFieldException("Debe ingresar su identificación");
			}
			
			if (passwordInput.getText().equals("")) {
				throw new EmptyFieldException("Debe ingresar su contraseña");
			}
			
			if (main.authenticateAdmin(identificationInput.getText(), passwordInput.getText())) {
				main.openRegistry();
			} else {
				passwordInput.setText(null);;
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error de autenticación");
				alert.setHeaderText(null);
				alert.setContentText("Usuario o contraseña incorrecta");
				alert.showAndWait();		
			}
		} catch(EmptyFieldException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Campo vacío");
			alert.setHeaderText(null);
			alert.setContentText(e.getMessage());
			alert.showAndWait();	
		}
	}
}
