package software.calculator;

import java.util.ArrayList;
import java.util.Arrays;

public class LoanData {
	private double loanAmount;
	private double loanInterestRate;
	private int loanTerm;
	private double defermentInterestRate;
	private int defermentStart;
	private int defermentTerm;
	private int calcMethod;
	
	public ArrayList <MonthData> dataArray;

	public double getLoanAmount() {
		double temp = Math.round(loanAmount * 100);
		return temp / 100;
	}

	public double getLoanInterestRate() {
		double temp = Math.round(loanInterestRate * 100);
		return temp / 100;
	}

	public int getLoanTerm() {
		return loanTerm;
	}

	public double getDefermentInterestRate() {
		double temp = Math.round(defermentInterestRate * 100);
		return temp / 100;
	}

	public int getDefermentStart() {
		return defermentStart;
	}

	public int getDefermentTerm() {
		return defermentTerm;
	}

	public String getCalcMethod() {
		if (calcMethod == 1)
		{
			return "Annuity";
		}

		return "Line";
	}


	public void changeLoanAmount(String loanAmount) {
		this.loanAmount = Double.parseDouble("0" + loanAmount);
	}
	
	public void changeInterestRate(String loanInterestRate) {
		this.loanInterestRate = (Double.parseDouble("0" + loanInterestRate) / 100) / 12;
	}

	public void changeLoanTerm(String year, String month) {
		loanTerm = Integer.parseInt("0" + month) + (Integer.parseInt("0" + year) * 12);
	}
	
	public void changeDefermentInterestRate(String defermentInterestRate) {
		this.defermentInterestRate = (Double.parseDouble("0" + defermentInterestRate) / 100) / 12;
	}

	public void changeDefermentStart(String defermentStart) {
		this.defermentStart = Integer.parseInt("0" + defermentStart);
	}

	public void changeDefermentTerm(String defermentTerm) {
		this.defermentTerm = Integer.parseInt("0" + defermentTerm);
	}
	
	public void changeCalcMethod(String calcMethod) {
		if(calcMethod == "Annuity") {
			this.calcMethod = 1;
		}
		else {
			this.calcMethod = 0;
		}
	}

	public void calculateData() {
		dataArray = new ArrayList <>();

		if (calcMethod == 1) { //annuity
			double num1 = loanInterestRate * Math.pow((loanInterestRate + 1), loanTerm);
			double num2 = Math.pow((1 + loanInterestRate), loanTerm) - 1;
			double tempValue = loanAmount * (num1 / num2);
			double loanAmount = this.loanAmount;

			for(int i = 0; i < loanTerm + defermentTerm; ++i) {
				if(i + 1 == defermentStart) {
					for(int j = 0; j < defermentTerm; ++j) {
						double tempInterest = loanAmount * defermentInterestRate;
						dataArray.add(new MonthData(i+1, 0, tempInterest, loanAmount));
						++i;
					}
				}

				double tempInterest = loanAmount * loanInterestRate;
				double tempPrincipal = tempValue - tempInterest;
				loanAmount -= tempPrincipal;
				dataArray.add(new MonthData(i+1, tempPrincipal, tempInterest, loanAmount));
			}
		}
		else {	//linear
			double valPrincipal = loanAmount / loanTerm;
			double loanAmount = this.loanAmount;

			for(int i = 0; i < loanTerm + defermentTerm; ++i) {
				if(i + 1 == defermentStart) {
					for(int j = 0; j < defermentTerm; ++j) {
						double tempInterest = loanAmount * loanInterestRate;
						dataArray.add(new MonthData(i+1, 0, tempInterest, loanAmount));
						++i;
					}
				}

				double valInterest = loanAmount * (loanInterestRate / 12);
				loanAmount = loanAmount - valPrincipal;
				dataArray.add(new MonthData(i+1, valPrincipal, valInterest, loanAmount));

			}
		}
	}
	
}