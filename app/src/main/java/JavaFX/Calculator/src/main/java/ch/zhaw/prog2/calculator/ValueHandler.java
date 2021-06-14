package JavaFX.Calculator.src.main.java.ch.zhaw.prog2.calculator;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Handles the values from the input form. Offers the {@link resultBound} StringProperty to listen from
 * a GUI (bind to a field in the GUI or add listener)
 * @author bles
 * @version 1.1
 */
public class ValueHandler {

	private double initialAmount;
	private double returnInPercent;
	private double annualCost;
	private int numberOfYears;
	private boolean valuesOk = false;
	// Solution with bound properties
	private StringProperty resultBound = new SimpleStringProperty();

	public ValueHandler() {		
	}

	/**
	 * Check the input values are valid (can be improved)
	 * If not ok, fill error message to the results and return false
	 * If ok, set the date fields and return true
	 */
	private String checkValues(String initialAmount, String returnInPercent, String annualCost, String numberOfYears) {
		StringBuilder sb = new StringBuilder();
		valuesOk = true;
		if ("".equals(initialAmount) || Double.parseDouble(initialAmount)<=0) {
			sb.append("Please specify a positive  initial amount!\n");
			valuesOk = false;
		} else {
			this.initialAmount = Double.parseDouble(initialAmount);
		}
		if ("".equals(returnInPercent)) {
			sb.append("Please specify the annual return rate in %!\n");
			valuesOk = false;
		} else {
			this.returnInPercent = Double.parseDouble(returnInPercent)/100;
		}
		if ("".equals(annualCost) || Double.parseDouble(annualCost)<0) {
			sb.append("Please specify the annual cost!\n");
			valuesOk = false;
		} else {
			this.annualCost = Double.parseDouble(annualCost);
		}
		if ("".equals(numberOfYears) ||
				Double.parseDouble(numberOfYears) < 1 ||
				Double.parseDouble(numberOfYears) > 99 ||
				Math.round(Double.parseDouble(numberOfYears))!=Double.parseDouble(numberOfYears)) {

			sb.append("Please enter a time period in years!");
			valuesOk = false;
		} else {
			this.numberOfYears = Integer.parseInt(numberOfYears);
		}
		return sb.toString();
	}

	/**
	 * Calculates the result
	 * @return the result as a String
	 */
	private String calculateResult() {
		StringBuilder resultSB = new StringBuilder();
		double val = initialAmount;
		for(int i = 1; i <= numberOfYears; i++) {
			resultSB.append("After ");
			resultSB.append(i).append(" year(s): ");
			val = val * (1 + returnInPercent) - annualCost;
			resultSB.append(Math.round(val)).append("\n");
		}
		return resultSB.toString();
	}
	
	/**
	 * Result String can be "", if no calculation or check is done
	 * @return	String with the result of the value checking or the calculation
	 */
	public String getResultBound() {
		return resultBound.get();
	}
	
	/**
	 * Set the result to the string in the parameter
	 * @param infoText
	 */
	public void setResultBound(String infoText) {
		resultBound.set(infoText);
	}

	/**
	 * If the values checked by {@link checkValues} are ok, the return is true
	 * @return	true, if ok
	 */
	public boolean areValuesOk() {
		return valuesOk;
	}
	
	// Solution with bound properties
	/**
	 * The property to bind
	 * @return
	 */
	public StringProperty resultBoundProperty() {
		return resultBound;
	}
	/**
	 * Checks and calculates the result. All values as String (from the Text-Fields)
	 * If the check fails, an error message is set to the bound result
	 * @param initialAmount
	 * @param returnInPercent
	 * @param annualCost
	 * @param numberOfYears
	 */
	public void checkAndCalculateResult(String initialAmount, String returnInPercent, String annualCost, String numberOfYears) {
		setResultBound(checkValues(initialAmount, returnInPercent, annualCost, numberOfYears));
		if(valuesOk) {
			setResultBound(calculateResult());
		}
	}
}
