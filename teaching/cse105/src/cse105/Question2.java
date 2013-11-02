package cse105;

public class Question2 {

	public static void main(String[] args) {
		int amount = 0;
		for(int total = 0; total<30000; amount++) {
			total = (int)(5000 + amount * getCommissionRate(amount));
		}
		System.out.println("The minimun sales that you have to gengrate in order to make $30000 is: " + amount);
	}

	private static float getCommissionRate(int amount) {
		if(amount<=5000 && amount>0)
			return 0.08f;
		if(amount>5000 && amount<=10000)
			return 0.1f;
		if(amount>10000)
			return 0.12f;
		return 0.0f;
	}

}
