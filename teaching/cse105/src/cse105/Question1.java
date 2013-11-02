package cse105;

import java.util.Scanner;

public class Question1 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please input monthly interest rate:");
		double interestRate = scanner.nextDouble();
		System.out.println("Please input number of years:");
		int numberOfYears = scanner.nextInt();
		System.out.println("Please input load amount:");
		double loadAmount = scanner.nextDouble();
		double monthlyPayment = (interestRate*loadAmount) / (1-(1/Math.pow(1+interestRate, numberOfYears*12)));
		System.out.println("The monthly payment will be: " + monthlyPayment);
		System.out.println("The final payment will be: " + monthlyPayment*12*numberOfYears);
		scanner.close();
	}

}
