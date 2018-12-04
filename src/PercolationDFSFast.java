
public class PercolationDFSFast extends PercolationDFS {
	PercolationDFSFast(int size) {
		super(size);
	}
	
	
	@Override public void updateOnOpen(int row, int col) {
		if (isFull(row, col))
			return;
		if (row == 0)
			dfs(row, col);
		else {
			//fill cell with dfs if a neighbor is full
			int[] deltaR = {0,0,-1,1};
			int[] deltaC = {-1,1,0,0};
			for (int k=0; k<deltaR.length; k++) {
				int newR = row + deltaR[k];
				int newC = col + deltaC[k];
				if(inBounds(newR, newC) && isFull(newR, newC)) {
					dfs(row, col);
					break;
				}
			}
		}
	}
	
}
