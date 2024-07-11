package com.Raj.Validator.Validator;

import java.io.IOException;
import java.sql.SQLException;

import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class JavaFx extends Application {
	
	 @Override
	    public void start(Stage primaryStage) {
	        primaryStage.setTitle("File Uploader and Validator");

	        ComboBox<String> serverDropdown = new ComboBox<>();
	        serverDropdown.getItems().addAll("CFG", "DEV", "SIT", "PROD");
	        serverDropdown.setPromptText("Select Server");

	        Button uploadButton = new Button("Upload File and validate");
	        uploadButton.setOnAction(event -> {
				try {
					openFileChooser(primaryStage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});

	        VBox vbox = new VBox(serverDropdown, uploadButton);
	        vbox.setSpacing(10);

	        Scene scene = new Scene(vbox, 300, 200);
	        primaryStage.setScene(scene);
	        primaryStage.show();
	    }

	    private void openFileChooser(Stage primaryStage) throws IOException, SQLException {
	        FileChooser fileChooser = new FileChooser();
	        fileChooser.getExtensionFilters().add(
	                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx")
	        );
	        java.io.File selectedFile = fileChooser.showOpenDialog(primaryStage);

	        if (selectedFile != null) {
	            String filePath = selectedFile.getAbsolutePath();
	            ReadDataFromExcel.UploadFileAndValidate(filePath);
	            System.out.println("FilePath "+ filePath);
	            // Call the validation method
	            validateFile(filePath);
	        }
	    }

	    private void validateFile(String filePath) {
	        // Your validation logic here
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("File Validation");
	        alert.setHeaderText(null);

	        if (filePath.endsWith(".xlsx")) {
	            alert.setContentText("File is valid: " + filePath);
	        } else {
	            alert.setContentText("Invalid file type. Please select an Excel file.");
	        }

	        alert.showAndWait();
	    }


	 
	public static void main(String[] args) {
		launch();

	}

}
