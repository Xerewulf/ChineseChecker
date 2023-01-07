
import java.util.ArrayList;
import java.util.Scanner;

// Algorithms explained: minimax and alpha-beta pruning
// https://www.youtube.com/watch?v=l-hh51ncgDI
public class TicTacToeTest {

    public static void main(String[] args) {

        int boardSize = 8;
        CheckerState board = new CheckerState(boardSize);
        //AdversialSearch adversialSearch = new Minimax();
        //AdversialSearch adversialSearch = new AlphaBetaPruning();

       for (int y=0; y<boardSize; y++) {
			for (int x=0; x<boardSize; x++) {
				System.out.print((y * boardSize + x) + " ");
			}
			System.out.println();
		}
       
       System.out.println(board);

       

        int playerIndex = 1;
        Scanner in = new Scanner(System.in);
        Solution solution;
        do {
            playerIndex++;
            

            // Player 2 : Human
            System.out.println("Human move \n----------");
            System.out.println("Which stone do you want to pick\n----------");
            boolean isValid_set = true;
            boolean isValid_pick;
            
            do {
                int pickIndex = in.nextInt();
                board.index_X(pickIndex);
                int xP = pickIndex % boardSize;
                int yP = pickIndex / boardSize;
                ArrayList<Integer> move =board.possible_moves( xP,yP);
                System.out.println("You can move these places:"+move);

        

                isValid_pick=board.to_null(xP, yP, Player.Two);
                while(isValid_pick!=true){
                 System.out.println("plese pick one of your stone which is O !!!\n----------");
                 pickIndex = in.nextInt();
                board.index_X(pickIndex);
                 xP = pickIndex % boardSize;
                yP = pickIndex / boardSize;
               
                isValid_pick=board.to_null(xP, yP, Player.Two);
                }
                System.out.println("Which place you want to move\n----------");
                int moveIndex = in.nextInt();
                board.index_Y(moveIndex);
                
                int x = moveIndex % boardSize;
                int y = moveIndex / boardSize;
                if(!move.contains(moveIndex)) {
                	
                	System.out.println("You cannot move this position!\n----------");
                	isValid_set= false;
                	
                }
                else {
                	isValid_set = board.set(x, y, Player.Two);
                }
                
                
                 
                while(isValid_set!=true && !move.contains(moveIndex) ){
                	
                System.out.println("plese enter a valid place( you cannot go back step or diagnolly forword!!!!)\n----------");
                moveIndex = in.nextInt();
                board.index_Y(moveIndex);
                 x = moveIndex % boardSize;
                 y = moveIndex / boardSize;
                 if(!move.contains(moveIndex)) {
                 	
                 	System.out.println("You cannot move this position!\n----------");
                 	isValid_set= false;
                 	
                 }
                 else {
                	isValid_set = board.set(x, y, Player.Two); 
                 }
                
                }
                
            } while (!isValid_set);

            System.out.println(board);
            // if (board.wins(Player.Two)) {
            //     System.out.println("Human player wins");
            //     break;
            // }
            // if (board.isTie()) {
            //     System.out.println("Tie");
            //     break;
            // }

        } while (true);

        // in.close();
    }

}
