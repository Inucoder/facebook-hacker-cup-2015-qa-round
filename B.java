import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class B {

	static class Food {
		int P;
		int C;
		int F;
		
		Food(int P, int C, int F){
			this.P = P;
			this.C = C;
			this.F = F;
		}
	}
	
	static class Result {
		boolean possible = false;
	}
	
	static void solve(int i, Food[] foods, int Gp, int Gc, int Gf, int P, int C, int F, Result result){
		if (i == foods.length){
			if (P == Gp && C == Gc && F == Gf){
				result.possible = true;
			}
		}
		else {
			solve(i + 1, foods, Gp, Gc, Gf, P, C, F, result);
			solve(i + 1, foods, Gp, Gc, Gf, P + foods[i].P, C + foods[i].C, F + foods[i].F, result);
		}
	}
	
	public static void main(String[] args) throws IOException {
		//File input = new File("B-test.in");
		//File output = new File("B-test.out");
		
		File input = new File("B.in");
		File output = new File("B.out");
		
		Scanner in = new Scanner(input);
		PrintWriter out = new PrintWriter(output);
		
		int T = in.nextInt();
		
		for (int t = 1; t <= T; t++){
			int Gp = in.nextInt();
			int Gc = in.nextInt();
			int Gf = in.nextInt();
			
			int N = in.nextInt();
			
			Food[] foods = new Food[N];
			
			for (int i = 0; i < N; i++){
				int P = in.nextInt();
				int C = in.nextInt();
				int F = in.nextInt();
				
				foods[i] = new Food(P, C, F);
			}
			
			Result result = new Result();
			solve(0, foods, Gp, Gc, Gf, 0, 0, 0, result);
			
			out.println(String.format("Case #%d: %s", t, result.possible? "yes": "no"));
		}
		
		in.close();
		out.close();
	}	
	
}
