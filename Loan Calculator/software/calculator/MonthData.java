package software.calculator;

import java.text.DecimalFormat;

public class MonthData {
	private int month;
	private double payment;
	private double principal;
	private double interest; 
	private double loanAmount;
	
	public MonthData(int month, double principal, double interest, double loanAmount) {
		this.month = month;
		this.principal = principal;
		this.interest = interest;
		this.loanAmount = loanAmount;
		this.payment = principal + interest;
	}

	public int getMonth() {
		return month;
	}

	public double getPayment() {
		double temp = Math.round(payment * 100);
		return temp / 100;
	}

	public double getPrincipal() {
		double temp = Math.round(principal * 100);
		return temp / 100;
	}
	
	public double getInterest() {
		double temp = Math.round(interest * 100);
		return temp / 100;
	}

	public double getLoanAmount() {
		double temp = Math.round(loanAmount * 100);
		return temp / 100;
	}

	@Override
	public String toString() {
		DecimalFormat fr = new DecimalFormat("0.00");
		String str = String.format("| %-5d | %-13s | %-15s | %-14s | %-19s |\n", month, fr.format(getPayment()), fr.format(principal), fr.format(interest), fr.format(loanAmount));
		return str;
	}

}
