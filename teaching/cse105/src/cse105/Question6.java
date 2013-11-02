package cse105;

public class Question6 {

	private static final double MILLIONAIRE = 1000000.0;
	
	public static void main(String[] args) {
		//question (a)
		double amount=1000.0;
		int i;
		for(i=1; amount<MILLIONAIRE; i++) {
			amount += amount*0.05;
		}
		System.out.println("Question (a): After " + i + " years, you become a millionaire.");
		
		//question (b)
		amount=1000.0f;
		for(i=1; amount<MILLIONAIRE; i++) {
			amount += amount*0.05 + 1000;
		}
		System.out.println("Question (b): After " + i + " years, you become a millionaire.");
		
		//question (c)
		for(i=1; i<=100; i++) {
			if(beMillionaireIn40Years(i)) {
				System.out.println("Question (c): If the interest rate is " + i + "%, then you can be a millionaire in 40 years.");
				return;
			}
		}
	}

	private static boolean beMillionaireIn40Years(int rate) {
		double amount = 1000.0;
		for(int k=0; k<40; k++) {
			amount += amount*rate/100;
		}
		if(amount>=MILLIONAIRE)
			return true;
		return false;
	}

}
