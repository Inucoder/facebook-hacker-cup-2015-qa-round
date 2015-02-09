import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class A {
	
	static int countDigits(int n){
		return n == 0? 1: (int) Math.log10(n) + 1;
	}
	
	static interface BestPicker {
		int pickBest(int a, int b);
	}
	
	static BestPicker minPicker = new BestPicker() {
		@Override
		public int pickBest(int a, int b) {
			return Math.min(a, b);
		}
	}; 
	
	static BestPicker maxPicker = new BestPicker() {
		@Override
		public int pickBest(int a, int b) {
			return Math.max(a, b);
		}
	};
	
	static int findBest(int N, BestPicker bestPicker){
		int best = N;
		int digits = countDigits(N);
		
		String nStr = Integer.toString(N);
		
		for (int i = 0; i < digits; i++){
			for (int j = i + 1; j < digits; j++){
				char[] C = nStr.toCharArray();
				
				char di = C[i];
				char dj = C[j];
				
				C[i] = dj;
				C[j] = di;
				
				String newNumberStr = new String(C);
				int newNumber = Integer.parseInt(newNumberStr);
				
				if (digits == countDigits(newNumber)){
					best = bestPicker.pickBest(best, newNumber);
				}
			}
		}
		
		return best;
	}
	
	public static void main(String[] args) throws IOException {
		//File input = new File("A-test.in");
		//File output = new File("A-test.out");
		
		File input = new File("A.in");
		File output = new File("A.out");
		
		Scanner in = new Scanner(input);
		PrintWriter out = new PrintWriter(output);
		
		int T = in.nextInt();
		
		for (int t = 1; t <= T; t++){
			int N = in.nextInt();
			
			int min = findBest(N, minPicker);
			int max = findBest(N, maxPicker);
			
			out.println(String.format("Case #%d: %d %d", t, min, max));
		}
		
		in.close();
		out.close();
	}
	
}
