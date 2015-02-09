import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class C {

	static final char TILE_EMPTY_SPACE = '.';
	
	static final char TILE_WALL = '#';
	
	static final char TILE_STARTING_POSITION = 'S';
	
	static final char TILE_GOAL = 'G';
	
	static final char TILE_LASER_UP = '^';
	
	static final char TILE_LASER_RIGHT = '>';
	
	static final char TILE_LASER_DOWN = 'v';
	
	static final char TILE_LASER_LEFT = '<';

	static class State {
		int m;
		int n;
		int step;
		
		State(int m, int n, int step) {
			this.m = m;
			this.n = n;
			this.step = step;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + m;
			result = prime * result + n;
			result = prime * result + (step % 4);
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			State other = (State) obj;
			if (m != other.m)
				return false;
			if (n != other.n)
				return false;
			if (step % 4 != other.step % 4)
				return false;
			return true;
		}
	}

	static boolean canWalkTo(char tile){
		return tile == TILE_EMPTY_SPACE ||
			tile == TILE_STARTING_POSITION ||
			tile == TILE_GOAL;
	}
	
	static boolean isLaser(char tile){
		return tile == TILE_LASER_UP ||
			tile == TILE_LASER_RIGHT ||
			tile == TILE_LASER_DOWN ||
			tile == TILE_LASER_LEFT;
	}
	
	static char rotateLaser(char laser){
		if (laser == TILE_LASER_UP) return TILE_LASER_RIGHT;
		if (laser == TILE_LASER_RIGHT) return TILE_LASER_DOWN;
		if (laser == TILE_LASER_DOWN) return TILE_LASER_LEFT;
		else return TILE_LASER_UP;
	}
	
	static char rotateLaser(char laser, int times){
		for (int i = 0; i < times % 4; i++){
			laser = rotateLaser(laser);
		}
		
		return laser;
	}
	
	static boolean isSafe(String[] maze, int M, int N, State state){
		int m = state.m;
		int n = state.n;
		int step = state.step;
		
		//up
		for (int dm = -1; m + dm >= 0; dm--){
			char tile = maze[m + dm].charAt(n);
			
			if (isLaser(tile)){
				char laser = rotateLaser(tile, step);
				if (laser == TILE_LASER_DOWN) return false;
			}
			
			if (!canWalkTo(tile)) break;
		}
		
		//right
		for (int dn = 1; n + dn < N; dn++){
			char tile = maze[m].charAt(n + dn);
			
			if (isLaser(tile)){
				char laser = rotateLaser(tile, step);
				if (laser == TILE_LASER_LEFT) return false;
			}
			
			if (!canWalkTo(tile)) break;
		}
		
		//down
		for (int dm = 1; m + dm < M; dm++){
			char tile = maze[m + dm].charAt(n);
		
			if (isLaser(tile)){
				char laser = rotateLaser(tile, step);
				if (laser == TILE_LASER_UP) return false;
			}
			
			if (!canWalkTo(tile)) break;
		}
		
		//left
		for (int dn = -1; n + dn >= 0; dn--){
			char tile = maze[m].charAt(n + dn);
			
			if (isLaser(tile)){
				char laser = rotateLaser(tile, step);
				if (laser == TILE_LASER_RIGHT) return false;
			}
			
			if (!canWalkTo(tile)) break;
		}
		
		return true;
	}
	
	static int solve(String[] maze, int M, int N){
		HashSet<State> visited = new HashSet<State>();
		Queue<State> queue = new LinkedList<State>();
		
		int startM = -1;
		int startN = -1;
		
		for (int i = 0; i < M; i++){
			for (int j = 0; j < N; j++){
				char c = maze[i].charAt(j);
				
				if (c == TILE_STARTING_POSITION){
					startM = i;
					startN = j;
				}
			}
		}
		
		//Breadth-first search
		State startState = new State(startM, startN, 0);
		visited.add(startState);
		queue.add(startState);
		
		int[] dm = {-1, 0, 1, 0};
		int[] dn = {0, 1, 0, -1};
		
		while (!queue.isEmpty()){
			State state = queue.remove();
			
			//move!
			for (int i = 0; i < 4; i++){
				int m = state.m + dm[i];
				int n = state.n + dn[i];
				
				//can we move to (m, n)?
				
				//check bounds
				if (m >= 0 && m < M && n >= 0 && n < N){
					//evaluating tile (m, n)...
					char tile = maze[m].charAt(n);
					
					if (canWalkTo(tile)){
						State next = new State(m, n, state.step + 1);
												
						if (isSafe(maze, M, N, next) && !visited.contains(next)){
							//goal reached in state.step + 1 moves
							if (tile == TILE_GOAL) return next.step;
							
							visited.add(next);
							queue.add(next);
						}
					}
				}
			}
		}
		
		return -1;
	}
	
	public static void main(String[] args) throws IOException {
		//File input = new File("C-test.in");
		//File output = new File("C-test.out");
		
		File input = new File("C.in");
		File output = new File("C.out");
		
		Scanner in = new Scanner(input);
		PrintWriter out = new PrintWriter(output);
		
		int T = in.nextInt();
		
		for (int t = 1; t <= T; t++){
			int M = in.nextInt();
			int N = in.nextInt();
			
			String[] maze = new String[M];
			
			for (int i = 0; i < M; i++){
				maze[i] = in.next();
			}
			
			int shortestPath = solve(maze, M, N);
			out.println(String.format("Case #%d: %s", t, shortestPath == -1? "impossible": shortestPath));
		}
		
		in.close();
		out.close();
	}
	
}
