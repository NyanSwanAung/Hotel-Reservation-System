package com.mainview;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


	public class Main extends Application{
	
		
	static Stage primaryStage;
	Scene MainScene;
		
	@Override
	public void start(Stage stage) {
		try {
			
			primaryStage = stage;
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Login.fxml"));
			MainScene = new Scene(root);
			primaryStage.setScene(MainScene);
			/*primaryStage.setX(350);
			primaryStage.setY(250);*/
			primaryStage.setResizable(false);
			primaryStage.setTitle("Hotel Reservation System");
			
			
			
			stage.setOnCloseRequest(event -> {
				
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Close confirmation");
				alert.setContentText("Are you sure do you want to exit?");
				
				alert.showAndWait().ifPresent(type -> {
					if(type == ButtonType.CANCEL) {
						event.consume(); 
					}
				});
			});
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
	
