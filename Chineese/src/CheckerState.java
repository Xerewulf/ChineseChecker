
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

enum CellState {
    X, O,
};

public class CheckerState extends BoardState {

    final int boardSize;
    private final CellState boardState[][];
    int to_null_x = 1000;
    int to_null_y = 1000;
    int picked;
    int target;
    //StringBuilder sb = new StringBuilder();

    CheckerState(int boardSize) {
        boardSize = Math.max(boardSize, 3);
        this.boardSize = boardSize;

        boardState = new CellState[boardSize][boardSize];

    }

    CheckerState(CheckerState state) {
        this.boardSize = state.boardSize;

        boardState = new CellState[boardSize][boardSize];

        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {
                this.boardState[y][x] = state.boardState[y][x];
            }
        }
    }

    @Override
    public BoardState clone() throws CloneNotSupportedException {
        return new CheckerState(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof CheckerState) {
            CheckerState state = (CheckerState) obj;
            for (int y = 0; y < boardSize; y++) {
                for (int x = 0; x < boardSize; x++) {
                    if (this.boardState[y][x] != state.boardState[y][x]) {
                        return false;
                    }
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        long code = 0;

        for (int i = 0; i < boardSize * boardSize; i++) {
            int x = i % boardSize;
            int y = i / boardSize;
            code = (code << 2) | (boardState[y][x].ordinal() + 1);
        }

        return new Long(code).hashCode();
    }

    boolean set(int x, int y, Player player) {
      

        if (/*important*/player == player.Two &&boardState[y][x] == null && picked > target &&((picked-target)!=boardSize+1/*must be 9*/  || ((picked-target)==1 || ((picked-target)==boardSize)/*must be 8*/))) { //     picked-target)!=9   for not allowing the stone to move diagonally         

            boardState[y][x] = (player == Player.One ? CellState.X : CellState.O);

            return true;
        } else {

            return false;
        }
    }

   boolean to_null(int x, int y, Player player) {

        to_null_x = x;
        to_null_y = y;
        if (/*important*/player == player.Two && boardState[y][x] != null && boardState[y][x]!=CellState.X/*&& boardState[y][x] != CellState.X && boardState[y][x] != CellState.O*/) {

            boardState[y][x] = null;
            return true;
        }else {

            return false;
        }
    }

    
        public List<CheckerState> succesor(int yP, int xP, List<CheckerState> children)
        {//this is a recursive method for the jumping over stones

            if(boardState[yP][xP-1]!= null && boardState[yP][xP-2] == null)
            {// left jump
                         CheckerState childState = new CheckerState(this);  // We had a problem over here when our first jump is complete,
                        childState.to_null(xP, yP, Player.Two);             // second recorsion our board place an O to the moved stone index. Only doing it in the first jumpin not other jumps
                        childState.boardState[yP][xP-2] = CellState.O;
                        children.add(childState);
                
                        
                        
                        succesor(yP,xP-2,children);
            }

            if(boardState[yP-1][xP] != null && boardState[yP-2][xP] == null)
            {// up jump
                
                
                        CheckerState childState = new CheckerState(this);// We had a problem over here when our first jump is complete,
                        childState.to_null(xP, yP, Player.Two);         // second recorsion our board place an O to the moved stone index. Only doing it in the first jumpin not other jumps
                        childState.boardState[yP-2][xP] = CellState.O;
                        children.add(childState);
                        succesor(yP-2,xP,children);
            }


            return children;
        }






    public ArrayList<Integer> possible_jumps(int yP, int xP, ArrayList<Integer> jumps)
    {// this is adding moveble index nummber to the arraylist for playing easy
        
        
        if(boardState[yP][xP-1]!= null && boardState[yP][xP-2] == null)
        {// left jump
                    
                   
            jumps.add((boardSize)*(yP+1)-(boardSize-(xP-2)));
                    
                    
            possible_jumps(yP,xP-2,jumps);
        }

        if(boardState[yP-1][xP] != null && boardState[yP-2][xP] == null)
        {// up jump
            
            jumps.add(((boardSize)*(yP-1))-(boardSize-xP));
        
            possible_jumps(yP-2,xP,jumps);
        }


        
        
        return jumps;
    }




  
   public ArrayList<Integer> possible_moves( int xP, int yP ) {
	   
       List<CheckerState> children = new ArrayList<CheckerState>();

	   ArrayList<Integer> moves = new ArrayList<Integer>();
	   int possibleMove=0;
       
       moves.addAll(possible_jumps(yP,xP,moves));
	

       if(boardState[yP][xP-1] == null)
       {// left jump
        possibleMove =  (boardSize)*(yP+1)-(boardSize-(xP-1));         
                  
        CheckerState childState = new CheckerState(this);
        //childState.boardState[yP][xP] = null;
        childState.to_null(xP, yP, Player.Two);
        childState.boardState[yP][xP-1] = CellState.O;
        children.add(childState);

        moves.add(possibleMove); 
       }

       if(boardState[yP-1][xP] == null)
       {// up jump
        possibleMove = (boardSize)*(yP)-(boardSize-xP);
           
        CheckerState childState = new CheckerState(this);
        //childState.boardState[yP][xP] = null;
        childState.to_null(xP, yP, Player.Two);
        childState.boardState[yP-1][xP] = CellState.O;
        children.add(childState);

        moves.add(possibleMove); 
       }




       System.out.println("\n");
       System.out.println(succesor(yP, xP, children).toString());
      
     

       



	  
       return moves;
        
	 }
    public void index_X(int x){
    this.picked=x;
    
    }
    public void index_Y(int y){
    this.target=y;
    
    }

    @Override
    public boolean wins(Player player) {
        /* Double utility = getUtility(player);
        return (utility != null && getUtility(player) > 0.0);*/
        int counter = 0;
        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {

                if (x < 3 && y < 3) {
                    if (boardState[y][x] == CellState.O) {
                        counter++;
                    }
                }
            }
        }

        if (counter < 9) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public boolean loses(Player player) {
        Double utility = getUtility(player);
        return (utility != null && getUtility(player) < 0.0);
    }

    @Override
    public boolean isTie() {
        Double utility1 = getUtility(Player.One);
        Double utility2 = getUtility(Player.Two);
        return (utility1 != null && utility2 != null && (utility1 == 0 || utility2 == 0));
    }


    public BoardState newUtility(List<CheckerState> states)
    {
        CheckerState bestState = new CheckerState(boardSize);
        int max= Integer.MAX_VALUE;
        CheckerState resultState = new CheckerState(boardSize);

        for(int count=0;count< states.size();count++){
            int score=Integer.MAX_VALUE;
            bestState = states.get(count);

            for(int i=0;i<boardSize;i++)
            {
                for(int j=0;j<boardSize;j++)
                {
                    if(bestState.boardState[i][j] == CellState.O)
                    {
                        score = score -(i+j) ;
                    }

                }
            }

            if(score<max)
            {
                resultState = bestState;
            }


        }

        return resultState;
    }





    @Override
    public Double getUtility(Player player /* list of state will be there*/) {
       

        CellState playerState = (player == Player.One ? CellState.X : CellState.O);
        CellState opponentState = (player == Player.One ? CellState.O : CellState.X);

        int playerCompleteCount = 0;
        int opponentCompleteCount = 0;
        for (int i = 0; i < boardSize; i++) {
            // vertical
            if (boardState[0][i] == playerState && isComplete(i, 0, 0, 1)) {
                playerCompleteCount++;
            }
            if (boardState[0][i] == opponentState && isComplete(i, 0, 0, 1)) {
                opponentCompleteCount++;
            }

            // horizontal
            if (boardState[i][0] == playerState && isComplete(0, i, 1, 0)) {
                playerCompleteCount++;
            }
            if (boardState[i][0] == opponentState && isComplete(0, i, 1, 0)) {
                opponentCompleteCount++;
            }
        }

        // diagonal
        

        if (playerCompleteCount > 0) {
            return (double) playerCompleteCount;
        } else {
            return -1.0 * opponentCompleteCount;
        }
    }

    boolean isComplete(int x, int y, int deltax, int deltay) {
        for (int i = 1; i < boardSize; i++) {
            if (boardState[y][x] != boardState[y + i * deltay][x + i * deltax]) {
                return false;
            }
        }

        return true;
    }

    @Override
    public List<BoardState> getChildList(Player player) {
        List<BoardState> children = new ArrayList<BoardState>();

        CellState playerState = (player == Player.One ? CellState.X : CellState.O);

        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {
                if (boardState[y][x] == null) {
                    CheckerState childState = new CheckerState(this);
                    childState.boardState[y][x] = playerState;
                    children.add(childState);
                }
            }
        }

        return children;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        int colmn = 0, row = 0;
        if (boardSize == 8) {
            colmn = 3;
            row = 4;
        } else if (boardSize == 6) {
            colmn = 2;
            row = 3;
        }  else if (boardSize == 7) {
            colmn = 3;
            row = 3;
        }else {
            colmn = 2;
            row = 2;
        }
        if (to_null_x == 1000 && to_null_y == 1000) {
            for (int y = 0; y < boardSize; y++) {
                for (int x = 0; x < boardSize; x++) {

                    if (x < colmn && y < colmn) {

                        boardState[y][x] = CellState.X;
                    } else if (x > row && y > row) {

                        boardState[y][x] = CellState.O;

                    }
                }
            }

        } else {

            boardState[to_null_y][to_null_x] = null;

        }
        if (colmn == 2 && row == 2) {
            boardState[1][1] = null;
            boardState[3][3] = null;

        }
        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {
                if (boardState[y][x] == null /*&& to_null_x==0 && to_null_y==0*/) {

                    if (boardState[y][x] == null) {
                        sb.append("." + " ");
                    }
                } else {

                    sb.append(boardState[y][x].toString() + " ");

                }

            }
            sb.append('\n');
        }

        return sb.toString();
        }
    //     if (to_null_x == 1000 && to_null_y == 1000) {
    //         for (int y = 0; y < boardSize; y++) {
    //             for (int x = 0; x < boardSize; x++) {

    //                 if (x < 3 && y < 3) {
    //                     boardState[y][x] = CellState.X;
    //                 } else if (x > 4 && y > 4) {
    //                     boardState[y][x] = CellState.O;

    //                 }
    //             }
    //         }

    //     } else {

    //         boardState[to_null_y][to_null_x] = null;

    //     }

    //     for (int y = 0; y < boardSize; y++) {
    //         for (int x = 0; x < boardSize; x++) {
    //             if (boardState[y][x] == null /*&& to_null_x==0 && to_null_y==0*/) {

    //                 if (boardState[y][x] == null) {
    //                     sb.append("." + " ");
    //                 }
    //             } else {

    //                 sb.append(boardState[y][x].toString() + " ");

    //             }

    //         }
    //         sb.append('\n');
    //     }

    //     return sb.toString();
    // }

}
