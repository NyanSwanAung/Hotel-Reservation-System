
package com.mainview;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane; 
import javafx.stage.Stage;

	public class LoginController implements Initializable { 
		
	public LoginModel loginModel = new LoginModel();
	
	@FXML
	private Label errorID;
	

	@FXML
	private Label errorPassword;
	
	@FXML
	private TextField txtUsername;
	
	@FXML
	private TextField txtPassword;
	
    @FXML
    private Button buttonSignIn;

    public static String username1;
    
    
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buttonSignIn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				try {
					if (loginModel.isLogin(txtUsername.getText(), txtPassword.getText())) {
						
						Parent home_page_parent = FXMLLoader.load(getClass().getResource("Reserve.fxml") );
						Scene home_page_scene = new Scene (home_page_parent);
						Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
						app_stage.setX(180);
						app_stage.setY(135);
						app_stage.setScene(home_page_scene);
						app_stage.show();
						ReserveController.Reservedata.clear();
						username1 = txtUsername.getText();
						

					} else {
						Alert alert = new Alert(AlertType.WARNING);
					    alert.setHeaderText(null);
					    alert.setContentText("Your ID and Password do not match");
						alert.showAndWait();
						errorID.setText("(Check your ID and Password)");
						errorPassword.setText("(Check your ID and Password)");
						}
				} catch (SQLException e1) {
					errorID.setText("(Incorrect ID and Password)");
					errorPassword.setText("(Incorrect ID and Password)");
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
	}
	
	
	public void Login (ActionEvent event) { 
		try {
			 txtPassword.setOnKeyPressed(e -> {
				 if(e.getCode() == KeyCode.ENTER) {
					 
						try {
							if (loginModel.isLogin(txtUsername.getText(), txtPassword.getText())) {
								Parent home_page_parent = FXMLLoader.load(getClass().getResource("Rooms.fxml") );
								Scene home_page_scene = new Scene (home_page_parent);
								Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
								app_stage.setScene(home_page_scene);
								app_stage.show();
								app_stage.setX(180);
								app_stage.setY(135);
								username1 = txtUsername.getText();
								ReserveController.Reservedata.clear();

							} else {
							   	Alert alert = new Alert(AlertType.WARNING);
							    alert.setHeaderText(null);
							    alert.setContentText("Your ID and Password do not match");
								alert.showAndWait();
								errorID.setText("(Check your ID and Password)");
								errorPassword.setText("(Check your ID and Password)");
								}
						} catch (SQLException e1) {
							errorID.setText("(Incorrect ID and Password)");
							errorPassword.setText("(Incorrect ID and Password)");
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				 }
			 });
				
			} finally {
		}
	}
	
	
	
}

