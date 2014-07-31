package com.adamheins.calculator;


import com.adamheins.expression.ExpressionEvaluator;
import com.adamheins.expression.ExpressionException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * GUI for a calculator that evaluates string of math.
 * 
 * @author Adam Heins
 *
 */
public class Calculator extends Application {

	/** Field where mathematical expressions are entered. */
	TextField entryField;
	
	/** Field where the result of the expression is printed. */
	TextField resultField;
	
	/** Button to evaluate the entered expression. */
	Button evaluateButton;
	

	/**
	 * Start the application.
	 */
	public void start(Stage stage) throws Exception {

		// Set up the field where expressions are entered.
		entryField = new TextField();
		entryField.setPromptText("Math goes here.");
		entryField.setStyle("-fx-prompt-text-fill:darkgray;");
		entryField.setOnKeyPressed(new EventHandler<KeyEvent>(){

			public void handle(KeyEvent e) {
				if (e.getCode().equals(KeyCode.ENTER))
					evaluateExpression();			
			}			
		});
		
		// Set up the field where the result is displayed.
		resultField = new TextField();
		resultField.setEditable(false);	
		resultField.setPrefWidth(300);
		
		// Set up the button to evaluate the entered expression.
		evaluateButton = new Button("=");
		evaluateButton.setOnAction(new EventHandler<ActionEvent>(){

			public void handle(ActionEvent e) {
				evaluateExpression();
			}			
		});
		
		// Set up the box containing the result field and the evaluate button.
		HBox resultBox = new HBox();
		resultBox.setSpacing(10);
		resultBox.getChildren().add(resultField);
		resultBox.getChildren().add(evaluateButton);
		HBox.setHgrow(resultField, Priority.ALWAYS);
		
		// Set up the root pane of the application.
		VBox root = new VBox();
		root.setSpacing(10);
		root.setPadding(new Insets(12, 12, 0, 12));
		root.getChildren().add(entryField);
		root.getChildren().add(resultBox);
		
		Scene scene = new Scene(root);
		
		// Set up the stage.
		stage.setTitle("Calculator");
		stage.setResizable(false);
		stage.setScene(scene);
		
		stage.show();
	}
	
	
	/**
	 * Evaluate the mathematical expression in the entry field.
	 */
	private void evaluateExpression() {
		try {
			resultField.setText(ExpressionEvaluator.evaluate(entryField.getText()));
		} catch (ExpressionException ex) {
			resultField.setText(ex.getMessage());
		}
	}
	
	
	/**
	 * Main method.
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
