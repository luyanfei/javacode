package cse105;

public class Question3 {

	public static void main(String[] args) {
		for(int num=2,count=0; count<100; num++) {
			if(isPrime(num)) {
				count++;
				System.out.print(num + "\t");
				if(count % 10 == 0)
					System.out.println();
			}
		}
	}

	private static boolean isPrime(int num) {
		for (int i = 2; i <= (int)Math.sqrt(num); i++) {
			if(num % i == 0)
				return false;
		}
		return true;
	}

}
