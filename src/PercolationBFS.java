import java.util.LinkedList;
import java.util.Queue;

public class PercolationBFS extends PercolationDFSFast {
	Queue<Integer> myQueue;
	int mySize;
	
	PercolationBFS(int size) {
		super(size);
		mySize = size;
		myQueue = new LinkedList<>();
	}
	
	@Override
	public void dfs(int row, int col) {
		// out of bounds?
		if (! inBounds(row,col)) return;
				
		// full or open, don't process
		if (isFull(row, col) || !isOpen(row, col))
			return;
				
		myGrid[row][col] = FULL;
		myQueue.add(pairToInt(row, col));
		
		int[] deltaR = {0,0,-1,1};
		int[] deltaC = {-1,1,0,0};
		
		while (myQueue.size() > 0) {
			int[] coordinates = intToPair(myQueue.remove());
			
			for (int k=0; k<deltaR.length; k++) {
				int newRow = coordinates[0] + deltaR[k];
				int newCol = coordinates[1] + deltaC[k];
				if (inBounds(newRow, newCol) && isOpen(newRow, newCol) && !isFull(newRow, newCol)) {
					myGrid[newRow][newCol] = FULL;
					myQueue.add(pairToInt(newRow,newCol));	
				}
			}
		}
	}
	
	//recover (row,col) from integer 
	private int[] intToPair(int value) {
		return new int[] {value / mySize, value % mySize};
	}
	
	//map (row, col) to an integer
	private int pairToInt(int row, int col) {
		return row * mySize + col;
	}
}
