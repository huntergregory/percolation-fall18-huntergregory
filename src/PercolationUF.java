
public class PercolationUF implements IPercolate {
	private boolean[][] myGrid;
	private int myOpenCount;
	private IUnionFind myFinder;
	private int mySize;
	
	private final int VTOP;
	private final int VBOTTOM;
	
	
	public PercolationUF(int size, IUnionFind finder) {
		if (size < 1) throw new IllegalArgumentException("grid size must be greater than 0");
		mySize = size;
		VTOP = mySize * mySize;
		VBOTTOM = mySize * mySize + 1;
		myGrid = new boolean[mySize][mySize];
		myFinder = finder;
		myFinder.initialize(mySize * mySize + 2);
	}
	
	@Override
	public void open(int row, int col) {
		checkBounds(row, col);
		
		//if already open
		if (myGrid[row][col])
			return;
		
		myGrid[row][col] = true;
		
		int value = pairToInt(row,col);
		if (row == 0) 
			myFinder.union(value, VTOP);
		if (row == mySize - 1) {
			myFinder.union(value, VBOTTOM);
		}
		
		int[] deltaR = {0,0,-1,1};
		int[] deltaC = {-1,1,0,0};
		for (int k=0; k<deltaR.length; k++) {
			int neighborR = row + deltaR[k];
			int neighborC = col + deltaC[k];
			if (inBounds(neighborR, neighborC)) {// && isOpen(neighborR, neighborC) && !isFull(neighborR, neighborC)) {
				myFinder.union(value, pairToInt(neighborR, neighborC));
			}
		}
	}

	@Override
	public boolean isOpen(int row, int col) {
		checkBounds(row, col);
		return myGrid[row][col];
	}

	@Override
	public boolean isFull(int row, int col) {
		checkBounds(row, col);
		return myFinder.connected(VTOP, pairToInt(row,col));
	}

	@Override
	public boolean percolates() {
		return myFinder.connected(VTOP, VBOTTOM);
	}
	
	@Override
	public int numberOfOpenSites() {
		return myOpenCount;
	}
	
	//@throws IllegalArgumentException if inputs are out of myGrid's bounds
	private void checkBounds(int r, int c) {
		if (!inBounds(r,c))
			throw new IllegalArgumentException("Inputs must be in a " + mySize + "x" + mySize + " grid's bounds");
	}
	
	private boolean inBounds(int r, int c) {
		return r>=0 && c>=0 && r<myGrid.length && c<myGrid[0].length;
	}
		
	//map (row, col) to an integer
	private int pairToInt(int row, int col) {
		return row * mySize + col;
	}

}
