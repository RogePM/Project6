package edu.guilford;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

public class CalPane extends GridPane {

    private TextField billField;
    private TextField tipField;
    private TextField totalField;
    private Button calculateButton;
    private ImageView imageview;
    private Calculate calculate;

    private Label billLabel;
    private Label tipLabel;
    private Label totalLabel;

    // create constructor for the calPane
    public CalPane(Calculate calculate) throws NumberFormatException {
        super();
        this.calculate = calculate;

        setAlignment(Pos.CENTER);

        // set the ColumnConstraints to fill the available space and keep the nodes
        // centered
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setFillWidth(true);
        col1.setHgrow(Priority.ALWAYS);

        // instantiate text fields
        billField = new TextField();
        tipField = new TextField();
        totalField = new TextField();
        calculateButton = new Button("Calculate");

        // add labels to the pane
        billLabel = new Label("Bill Amount:");
        add(billLabel, 1, 0);

        tipLabel = new Label("Tip Percent:");
        add(tipLabel, 1, 2);

        totalLabel = new Label("Total Amount:");
        add(totalLabel, 1, 3);

        // add textfields to the pane
        billField = new TextField();
        billField.setPromptText("Enter bill amount");
        add(billField, 2, 0);

        tipField = new TextField();
        tipField.setPromptText("Enter tip percentage");
        add(tipField, 2, 2);

        totalField = new TextField();
        totalField.setPromptText("total amount");
        add(totalField, 2, 3);
        totalField.editableProperty().setValue(false);

        // calculate button
        calculateButton = new Button("Calculate");
        add(calculateButton, 2, 4);

        // add event handler to the button
        calculateButton.setOnAction(event -> {
            try {
                calculateTip();
                billLabel.setText("Bill Amount:");
                tipLabel.setText("Tip Percent:");
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // display error in bill field
                billLabel.setText("Error: Enter a numeric value");
                tipLabel.setText("Error: Enter a numeric value");
                // tipLabel.setTextFill(Color.RED);

            }
        });

        // set font of labels bigger
        billLabel.setStyle("-fx-font-size: 20px;");
        tipLabel.setStyle("-fx-font-size: 20px;");
        totalField.setStyle("-fx-font-size: 20px;");
        // set font of textfields bigger
        billField.setStyle("-fx-font-size: 20px;");
        tipField.setStyle("-fx-font-size: 20px;");
        totalLabel.setStyle("-fx-font-size: 20px;");
        totalField.setStyle("-fx-font-size: 20px;");
        // set font of button bigger
        calculateButton.setStyle("-fx-font-size: 20px;");

        // event handler for tip percentage input
        tipField.textProperty().addListener((observable, oldValue, newValue) -> {
            double tipPercentage = Double.parseDouble(newValue);
            if (tipPercentage < 20) {
                tipLabel.setTextFill(Color.RED);
                tipLabel.setText("Tip Percent:\nTip should be at least 20%)");
            } else if (tipPercentage > 20) {
                tipLabel.setTextFill(Color.GREEN);
                tipLabel.setText("Tip Percent:" + (calculate.getTip()));
            } else
                tipLabel.setTextFill(Color.BLACK);
        });

    }

    // method that calculates the tip
    private void calculateTip() throws NumberFormatException {
            Label error = new Label("Enter a postive numeric value");
        try {
            double billAmount = Double.parseDouble(billField.getText()); // negative number exception
            if (billAmount < 0) {
                throw new NumberFormatException();
            }
            double tipPercentage = Double.parseDouble(tipField.getText());
            if (tipPercentage < 0) {
                throw new NumberFormatException();
            } else if (tipPercentage > 0 && tipPercentage > 0) {
                tipLabel.setTextFill(Color.BLACK);
                totalLabel.setTextFill(Color.BLACK);
                billLabel.setTextFill(Color.BLACK);
                error.setVisible(false);
            }
            // calculate the tip and total
            double tipAmount = billAmount * tipPercentage / 100;
            double totalAmount = billAmount + tipAmount;

            // display the total
            calculate.setTotal(totalAmount);// set total in calculate object

            // totalField.setText(String.format("$%.2f", totalAmount));
            totalField.setText(String.format("$%.2f", totalAmount));

            /// Image element
            imageview = new ImageView("https://itxdesign.com/wp-content/uploads/2016/05/6072965_s-300x251.jpg");
            // display the imageview url
            System.out.println(imageview.getImage().getUrl());
            this.add(imageview, 2, 5);
            imageview.setFitHeight(400);
            imageview.setFitWidth(400);
            imageview.setPreserveRatio(true);

        } catch (NumberFormatException e) {
          e.printStackTrace();
            // display error in bill field

            billLabel.setTextFill(Color.RED); // this works but not the next line
            tipLabel.setTextFill(Color.RED);
            totalLabel.setTextFill(Color.RED);

            error.setVisible(true);
            error.setTextFill(Color.RED);
            add(error, 1, 4);
            error.setStyle("-fx-font-size: 20px;");
        }

    }
}
