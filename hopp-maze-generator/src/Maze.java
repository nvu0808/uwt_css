/*
 * TCSS 342 - Maze Generator.
 */
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;
import java.util.Stack;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The Class will implement a Maze Generator by:
 * representing the graph G(n,m);
 * using a  randomized method to create a spanning tree of G(n,m)
 * displaying the final maze to console with solution.
 * @author Hop pham
 * @version May 2018
 */
public class Maze extends JPanel {
    private static final long serialVersionUID = -5563440505550236562L;
    /** Default bound. */
    private static final int BOUND = 50;
    /** Cell Padding. */
    private static final int CELL_PADDING = 20;
    
    /** Character represents the wall. */    
    private static final char WALL_CHAR = 'X';
    /** Character represents the visited. */
    private static final char VISITED_CHAR = 'V';
    /** Character represents the path. */
    private static final char SOLUTION_CHAR = '*';
    /** The number of edge. */
    private static final int DIRECTION = 4;
    /** Representing the direction wall. */
    private static final int TOP = 0;
    /** Representing the direction wall. */
    private static final int RIGHT = 1;
    /** Representing the direction wall. */
    private static final int BOTTOM = 2;
    /** Representing the direction wall. */
    private static final int LEFT = 3;
    /** The random. */
    private static final Random RD = new Random();
    
    /** Store the original Graph. */
    private final Cell[][] myMaze;
    /** Store the Graph with double size to print to console. */
    private final char[][] myDisplayGraph;
    /** Store the solution with double size to print to console. . */
    private final char[][] myGraphSolution;
    
    /** The column of the maze. */
    private final int myCols;
    /** The row of the maze. */
    private final int myRows;
    /** Start postion. */
    private Cell myStart;
    /** Destination. */
    private Cell myDes;
    /** Debug flag. */
    private final boolean myDebug;


    /**
     * Construct a 2d maze of size n by m and the debug flag.
     * Will show the steps of creation when the debug is true.
     * @param theWidth the width
     * @param theDepth the height
     * @param theDebug the debug
     */
    public Maze(final int theWidth, final int theDepth, final boolean theDebug) {
        myDebug = theDebug;
        myCols = theWidth;
        myRows = theDepth;
        myMaze = new Cell[theDepth][theWidth];
        myDisplayGraph = new char[2 * theDepth + 1][2 * theWidth + 1];
        myGraphSolution = new char[2 * theDepth + 1][2 * theWidth + 1];
        initializeMaze();
        generate();
        getSolution();
    }
    
    /** 
     * Generate a maze with full wall.
     * Create a start and a destination.
     */
    private void initializeMaze() {
        for (int i = 0; i < myRows; i++) {
            for (int j = 0; j < myCols; j++) {
                myMaze[i][j] = new Cell(i, j); 
            }
        }  
        myStart = getStart();
        myDes = getDestination();
    }
    
    /** Helper method to pick a random right cell to be the destination. 
     * @return the destination.
     * */
    private Cell getDestination() {        
        final Cell end = myMaze[RD.nextInt(myRows)][myCols - 1];
        end.setRight(false);
        return end;
    }
    
    /**
     * Helper method to pick a random left cell to be the start postion.
     * @return the start cell.
     */
    private Cell getStart() {
        final Cell start = myMaze[RD.nextInt(myRows)][0];
        start.setLeft(false);
        start.setVisited(true);
        return start;
    }

    /**
     * Helper method to solve the Maze.
     */
    private void getSolution() {        
        final Stack<Cell> path = new Stack<Cell>();
        path.push(myStart);
        myStart.setVisited(true);
        Cell currentCell = myStart;
        Cell lastCell = myStart;
        boolean popFlag = false;
        do {
            // not visited around the current cell.
            final MyHashTable.Entry<Cell, Integer>[] rdCells 
                                = generateRDCell(currentCell).entrySet();
            boolean flag = false; // signal that the last cell can reach the current
            for (final MyHashTable.Entry<Cell, Integer> entry : rdCells) {                
                flag = !(lastCell.isBottom() && lastCell.isLeft()
                                             && lastCell.isRight() && lastCell.isTop());
                if (flag) {
                    if (popFlag) {
                        path.push(lastCell);
                        popFlag = false;
                    }
                    currentCell = entry.getKey();
                    lastCell = currentCell;
                    currentCell.setVisited(true);
                    path.push(currentCell);

                    updatePath(path, currentCell);
                    break;
                }
            }
            // No path from the last cell.
            if (!flag) {
                popFlag = true;
                currentCell = path.pop();
                lastCell = currentCell;
            }
        } while (!path.isEmpty());
    }
    
    /**
     * Helper method to update the solution path.
     * @param thePath the path
     * @param theCurrent the current
     */
    private void updatePath(final Stack<Cell> thePath, final Cell theCurrent) {
        if (theCurrent.equals(myDes)) {
            final int len = thePath.size();
            for (int i = 0; i < len; i++) {
                thePath.elementAt(i).setPath(true);
            }
            theCurrent.setPath(true);
        }
    }

    /**
     * Helper method to generate the Maze.
     */
    private void generate() {
        final Stack<Cell> pathStack = new Stack<>();
        pathStack.push(myStart);
        
        Cell currentCell = myStart;
        Cell lastCell;
        while (!pathStack.isEmpty()) {
            final MyHashTable<Cell, Integer> random = generateRDCell(currentCell);
            currentCell.setVisited(true);
            if (random.size() <= 0) {
                currentCell = pathStack.pop();
            } else {
                lastCell = currentCell;
                int direction = -1;
                for (final MyHashTable.Entry<Cell, Integer> en : random.entrySet()) {
                    currentCell = en.getKey();
                    direction = en.getValue();
                    break;
                }
                setWall(currentCell, lastCell, direction);                
                pathStack.push(currentCell);
            }
            if (myDebug) {
                showMaze(VISITED_CHAR, myDisplayGraph);
                // debug the maze
                debug();
            } else {
                showMaze(VISITED_CHAR, myDisplayGraph);
            }
        }
        reset();
    }
    /** 
     * Helper method to reset the path. 
     */
    private void reset() {
        for (int i = 0; i < myRows; i++) {
            for (int j = 0; j < myCols; j++) {
                myMaze[i][j].setVisited(false);
            }
        }
    }

    /**
     * Helper method to set the wall for certain cell.
     * @param theCurrentCell the current.
     * @param theLastCell the last
     * @param theDirection the edge
     */
    private void setWall(final Cell theCurrentCell, final Cell theLastCell,
                                                     final int theDirection) {
        switch (theDirection) {
            case TOP:
                theLastCell.setTop(false);
                theCurrentCell.setBottom(false);
                break;
            case RIGHT:
                theLastCell.setRight(false);
                theCurrentCell.setLeft(false);
                break;
            case BOTTOM:
                theLastCell.setBottom(false);
                theCurrentCell.setTop(false);
                break;
            case LEFT:
                theLastCell.setLeft(false);
                theCurrentCell.setRight(false);
                break;
            default:
                break;
        }
    }
    /**
     * Random the Cell wall.
     * @param theCell the Cell
     * @return the random wall cell.
     */
    private MyHashTable<Cell, Integer> generateRDCell(final Cell theCell) {

        final MyHashTable<Cell, Integer> positions 
                                = new MyHashTable<Cell, Integer>(myCols * myRows * 3);
        final int row = theCell.getPosition()[0];
        final int col = theCell.getPosition()[1]; 
        final int[] cell = createCell();
        for (int i = 0; i < DIRECTION; i++) {
            int newRow = row;
            int newCol = col;
            switch (cell[i]) {
                case TOP:
                    newRow = row - 1;
                    break;
                case RIGHT:
                    newCol = col + 1;
                    break;
                case BOTTOM:
                    newRow = row + 1;
                    break;
                case LEFT:
                    newCol = col - 1;
                    break;
                default:
                    break;
            }
            if (checkPossible(newRow, newCol)) {
                positions.put(myMaze[newRow][newCol], cell[i]);
            }
        }
        return positions;
    }
    
    
    
    /**
     * Helper method to check moving possible.
     * @param theRow the row
     * @param theCol the col
     * @return true false
     */
    private boolean checkPossible(final int theRow, final int theCol) {
        return theRow > -1 && theRow < myRows
                        && theCol > -1 && theCol < myCols
                        && !myMaze[theRow][theCol].isVisited();
    }    
    
    /**
     * Helper method to create a valid spanning tree throw each cell.
     * @return The array containt 4 direction.
     */
    private int[] createCell() {
        final int[] temp = {-1, -1, -1, -1};
        
        int direction = 0;
        while (direction < DIRECTION) {
            boolean validAdd = true;
            final int next = RD.nextInt(DIRECTION);
            for (int i = 0; i < direction; i++) {
                if (temp[i] == next) {
                    validAdd = false;
                    break;
                }
            }
            if (validAdd) {
                temp[direction++] = next;
            }
        }
        return temp;
    }
    
    /**
     * This method will initialize the Maze base on the character.
     * @param theChar the character represent the path.
     * @param theMaze the path
     */
    private void showMaze(final char theChar, final char[][] theMaze) {
        final int height = theMaze.length;
        final int width = theMaze[0].length;
        //The top
        showTop(theMaze);        
        for (int i = 1; i < height; i += 2) {
            for (int j = 0; j < width; j++) {
                if (j % 2 == 0) { // init odd row and even column of
                    if (j < width - 1) {
                        if (myMaze[i / 2][j / 2].isLeft()) {
                            theMaze[i][j] = WALL_CHAR;
                        } else {
                            theMaze[i][j] = ' ';
                        }
                    } else { // init odd row and last column
                        if (myMaze[i / 2][j / 2 - 1].isRight()) {
                            theMaze[i][j] = WALL_CHAR;
                        } else {
                            theMaze[i][j] = ' ';
                        }
                    }
                } else {
                    if (theChar == SOLUTION_CHAR) {
                        if (myMaze[i / 2][j / 2].isPath()) {
                            theMaze[i][j] = theChar;
                        } else {
                            theMaze[i][j] = ' ';
                        }
                    } else {
                        if (myMaze[i / 2][j / 2].isVisited()) {
                            theMaze[i][j] = theChar;
                        } else {
                            theMaze[i][j] = ' ';
                        }
                    }
                }    
            }
        }

    }

    /**
     * Helper method to add the top wall.
     * @param theMaze the maze
     */
    private void showTop(final char[][] theMaze) {
        for (int j = 0; j < theMaze[0].length; j++) {
            if ((j % 2 == 0) || myMaze[0][j / 2].isTop()) {
                theMaze[0][j] = WALL_CHAR;
            } else {
                theMaze[0][j] = ' ';
            }
        }
        for (int i = 2; i < theMaze.length; i += 2) {
            for (int j = 0; j < theMaze[0].length; j++) {
                if (j % 2 == 0) { // init even row and even column
                    theMaze[i][j] = WALL_CHAR;
                } else {
                    if (myMaze[i / 2 - 1][j / 2].isBottom()) {
                        theMaze[i][j] = WALL_CHAR;
                    } else {
                        theMaze[i][j] = ' ';
                    }
                }
            }
        }
    }
    /**
     * Helper method to print the Maze for debug.
     */
    private void debug() {
        System.out.println("\n The maze:");
        show(myDisplayGraph);
    }

    /**
     * This method will display the Maze with solution.
     */
    public void display() {
        show(myDisplayGraph);
        System.out.println("\n The solution:");
        showMaze(SOLUTION_CHAR, myGraphSolution);
        show(myGraphSolution);
    }
    
    /**
     * Helper method to show to Maze.
     * @param theGraph the 2d array.
     */
    private void show(final char[][] theGraph) {
        for (int i = 0; i < theGraph.length; i++) {
            for (int j = 0; j < theGraph[0].length; j++) {
                System.out.print(theGraph[i][j]);
            }
            System.out.println();
        }
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public final void run() {
        final JFrame jf = new JFrame("TCSS 343 - Maze");
        jf.setBounds(BOUND, BOUND, CELL_PADDING, CELL_PADDING);
        this.setPreferredSize(new Dimension(myMaze[0].length * CELL_PADDING + BOUND,
                                          myMaze.length * CELL_PADDING + BOUND));
        this.repaint();
        jf.add(this);
        jf.pack();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);

    }

    /**
     * Paint component.
     * @param theGrap paint component.
     */
    public void paint(final Graphics theGrap) {
        for (int i = 0; i < myMaze.length; i++) {
            for (int j = 0; j < myMaze[0].length; j++) {
                if (myMaze[i][j].isPath()) {
                    theGrap.fillOval(j * CELL_PADDING + CELL_PADDING, i
                            * CELL_PADDING + CELL_PADDING, CELL_PADDING - 5, CELL_PADDING - 5);
                }
                if (myMaze[i][j].isTop()) {
                    theGrap.drawLine(j * CELL_PADDING + CELL_PADDING, i
                            * CELL_PADDING + CELL_PADDING, (j + 1) * CELL_PADDING
                            + CELL_PADDING, i * CELL_PADDING + CELL_PADDING);
                }
                if (myMaze[i][j].isRight()) {
                    theGrap.drawLine((j + 1) * CELL_PADDING + CELL_PADDING, i
                            * CELL_PADDING + CELL_PADDING, (j + 1) * CELL_PADDING
                            + CELL_PADDING, (i + 1) * CELL_PADDING
                            + CELL_PADDING);
                }
                if (myMaze[i][j].isBottom()) {
                    theGrap.drawLine((j + 1) * CELL_PADDING + CELL_PADDING, (i + 1)
                            * CELL_PADDING + CELL_PADDING, j * CELL_PADDING
                            + CELL_PADDING, (i + 1) * CELL_PADDING
                            + CELL_PADDING);
                }
                if (myMaze[i][j].isLeft()) {
                    theGrap.drawLine(j * CELL_PADDING + CELL_PADDING, (i + 1)
                            * CELL_PADDING + CELL_PADDING, j * CELL_PADDING
                            + CELL_PADDING, i * CELL_PADDING + CELL_PADDING);
                }
            }
        }
    }
    
    /**
     * This class will store the Cell.
     * @author Hop Pham
     */
    public class Cell {
        /** Representing the direction wall. */
        private static final int TOP = 0;
        /** Representing the direction wall. */
        private static final int RIGHT = 1;
        /** Representing the direction wall. */
        private static final int BOTTOM = 2;
        /** Representing the direction wall. */
        private static final int LEFT = 3;
        
        /** The position. */
        private final int[] myPosition;

        /** Representing the wall around. */
        private boolean[] myWall;

        /** Signal if visited. */
        private boolean myVisited;
        /** SIgnal if the path. */
        private boolean myPath;

        /**
         * Contrust the Cell.
         * @param theI the position.
         * @param theJ the position.
         */
        public Cell(final int theI, final int theJ) {
            myWall = new boolean[] {true, true, true, true};
            myVisited = false;
            myPath = false;
            myPosition = new int[] {theI, theJ };
        }

        /**
         * Set the path.
         * @param thePath the path
         */
        public void setPath(final boolean thePath) {
            this.myPath = thePath;
        }

        /**
         * Retrun true if the path otherwise false.
         * @return true false.
         */
        public boolean isPath() {
            return myPath;
        }

        /**
         * Set the top wall.
         * @param theTopWall the top
         */
        public void setTop(final boolean theTopWall) {
            myWall[TOP] = theTopWall;
        }

        /**
         * Return true if the top is wall otherwise false.
         * @return true false
         */
        public boolean isTop() {
            return myWall[TOP];
        }

        /**
         * Set the right wall.
         * @param theRight the right
         */
        public void setRight(final boolean theRight) {
            myWall[RIGHT] = theRight;
        }

        /**
         * Return true if the right is wall otherwise false.
         * @return true false
         */
        public boolean isRight() {
            return myWall[RIGHT];
        }

        /**
         * Set the right wall.
         * @param theBottom the right
         */
        public void setBottom(final boolean theBottom) {
            myWall[BOTTOM] = theBottom;
        }

        /**
         * Return true if the bottom is wall otherwise false.
         * @return true false
         */
        public boolean isBottom() {
            return myWall[BOTTOM];
        }

        /**
         * Set the left wall.
         * @param theLeft the left
         */
        public void setLeft(final boolean theLeft) {
            myWall[LEFT] = theLeft;
        }

        /**
         * Return true if the left is wall otherwise false.
         * @return true false
         */
        public boolean isLeft() {
            return myWall[LEFT];
        }

        /**
         * Set to visited.
         * @param theVisisted true false.
         */
        public void setVisited(final boolean theVisisted) {
            myVisited = theVisisted;
        }

        /**
         * Return true if visited otherwise false.
         * @return true false
         */
        public boolean isVisited() {
            return myVisited;
        }

        /**
         * Return the position.
         * @return postion
         */
        public int[] getPosition() {
            return myPosition.clone();
        }

        /**
         * Return a string of the cell for debugging.
         * @return cell information.
         */
        public String toString() {
            return "The position: (" + myPosition[0] + "," + myPosition[1]
                    + "), wall:[" + myWall[TOP] + ", " + myWall[RIGHT] + ", "
                    + myWall[BOTTOM] + ", " + myWall[LEFT]
                    + "]";
        }
    }
}