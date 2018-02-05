package application;

import java.util.ArrayList;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class Controller {
	@FXML
    private ChoiceBox<String> dropDownC = new ChoiceBox<>();
    @FXML
    private RadioButton pepperoniNone;

    @FXML
    private RadioButton hamSingle;

    @FXML
    private RadioButton hamDouble;

    @FXML
    private RadioButton hamTriple;

    @FXML
    private RadioButton pepperoniSingle;

    @FXML
    private RadioButton pepperoniDouble;

    @FXML
    private RadioButton pepperoniTriple;
    @FXML
    private ToggleGroup Ham;
	@FXML
	private ToggleGroup Pepperoni;
    @FXML
    private ChoiceBox<String> dropDownSize = new ChoiceBox<>();
    private ObservableList<String> sizeChoice = FXCollections.observableArrayList("Small", "Medium", "Large");
    private ObservableList<String> cheeseChoice = FXCollections.observableArrayList("Single", "Double", "Triple");

    @FXML
    private TextField numPizza;
    @FXML
    private TextField costPer;
    @FXML
    private TextField totalCost;
    @FXML
    private Button saveOrder;
    @FXML
    private TextField finalOrder;
    @FXML
    private Button saveLineItem;
    //initial Pizza Values
    private int pepperoni = 1;
    private int cheese = 1;
    private int ham = 0;
    private String size = "small";
    private int numP = 1;
    static ArrayList<LineItem> orders = new ArrayList<>();
    
 // This method sets the pepperoni value
 	private void setPep(int p){
 		pepperoni = p;
 	}

 	private void setHam(int h){
 		ham = h;
 	}
 	
 	private void setCheese(int c){
 		cheese = c;
 	}
 	
 	private void setSize(String s){
 		size = s;
 	}
 	
 	private void setNumberOfPizza(int n){
 		numP = n;
 	}
 	
 	private void calcPizza(){
		try
		{
			Pizza check = new Pizza(size,cheese,ham,pepperoni);
			totalCost.setText("$"+String.format("%.2f", (check.getCost())*numP));
			costPer.setText("$"+String.format("%.2f", (check.getCost())));
		}
		catch (IllegalPizza error) 
		{
			System.out.println(error.getMessage());
		}
	}
 	
 	private double totalCost(){
		double total = 0;
		if (orders.size() != 0)
		{
			for (LineItem order : orders) {
				total += order.getCost();
			}
		}
		return total;
	}
 	
 	

    @FXML
	void initialize(){
    	//Deal with cheese
    	dropDownC.setValue("Single");
    	dropDownC.setItems(cheeseChoice);
    	//Cheese lambda
    	dropDownC.valueProperty().addListener((observableValue, oldVal, newVal) ->{
    		if(newVal == "Single")
    			setCheese(1);
    		if(newVal == "Double")
    			setCheese(2);
    		if(newVal == "Triple")
    			setCheese(3);
    		calcPizza();
    	});
    	
    	
    	//Deal with Size
    	dropDownSize.setValue("Small");
    	dropDownSize.setItems(sizeChoice);
    	//Size Lambda
    	dropDownSize.valueProperty().addListener((observableValue, oldVal, newVal) ->{
    		setSize(newVal);
    		calcPizza();
    	});
    	
    	Pepperoni.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> 
		{
			if (((RadioButton)new_toggle).getId().equals("pepperoniTriple"))
			{
				setPep(3);
				hamTriple.setDisable(true);
				hamDouble.setDisable(true);
				hamSingle.setDisable(true);
				calcPizza();
			}
			if (((RadioButton)new_toggle).getId().equals("pepperoniDouble"))
			{
				setPep(2);
				hamTriple.setDisable(true);
				hamDouble.setDisable(true);
				hamSingle.setDisable(false);
				calcPizza();
			}
			if (((RadioButton)new_toggle).getId().equals("pepperoniSingle"))
			{
				setPep(1);
				hamTriple.setDisable(true);
				hamDouble.setDisable(false);
				hamSingle.setDisable(false);
				calcPizza();
			}
			if (((RadioButton)new_toggle).getId().equals("pepperoniNone"))
			{
				setPep(0);
				hamTriple.setDisable(false);
				hamDouble.setDisable(false);
				hamSingle.setDisable(false);
				calcPizza();
			}
		});

		Ham.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> 
		{
			if (((RadioButton)new_toggle).getId().equals("hamTriple"))
			{
				setHam(3);
				pepperoniTriple.setDisable(true);
				pepperoniDouble.setDisable(true);
				pepperoniSingle.setDisable(true);
				pepperoniNone.selectedProperty().set(true);
				setPep(0);
				calcPizza();
			}
			if (((RadioButton)new_toggle).getId().equals("hamDouble"))
			{
				setHam(2);
				pepperoniTriple.setDisable(true);
				pepperoniDouble.setDisable(true);
				pepperoniSingle.setDisable(false);
				calcPizza();
			}
			if (((RadioButton)new_toggle).getId().equals("hamSingle"))
			{
				setHam(1);
				pepperoniTriple.setDisable(true);
				pepperoniDouble.setDisable(false);
				pepperoniSingle.setDisable(false);
				calcPizza();
			}
			if (((RadioButton)new_toggle).getId().equals("hamNone"))
			{
				setHam(0);
				pepperoniTriple.setDisable(false);
				pepperoniDouble.setDisable(false);
				pepperoniSingle.setDisable(false);
				calcPizza();
			}
		});
    
    	numPizza.textProperty().addListener((observableValue, oldText, newText) ->{
			if (newText != null && !newText.isEmpty()) {
				try{
					int aVal = Integer.parseInt(newText);
					if(aVal>100 || aVal <1){
							((StringProperty)observableValue).setValue(oldText);
						}
						else{
							setNumberOfPizza(aVal);
							calcPizza();
						}
					}
					catch (NumberFormatException e){
						((StringProperty)observableValue).setValue(oldText);
					}
	
				}
		});
    
    	saveOrder.setOnAction(event -> {
			try{
				Pizza p = new Pizza(size,cheese,ham,pepperoni);
				LineItem i = new LineItem(numP,p);
				orders.add(i);
				finalOrder.setText(finalOrder.getText()+i.toString()+"\n");
				totalCost.setText("$"+String.format("%.2f", totalCost()));
			}
			catch (IllegalPizza ip) {
				System.out.println(ip.getMessage());
			}
	});
    
	
    }

}