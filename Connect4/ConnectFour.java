/**
* The ConnectFour class.
*
* This class represents a Connect Four (TM)
* game, which allows two players to drop
* checkers into a grid until one achieves
* four checkers in a straight line.

*/

import java.io.*;

public class ConnectFour {

   //NOTE: Changed button and end game messages in the GUI and Listener code.

   ConnectFourGUI gui;
   
   // declare all constants here
   final int EMPTY = 0;
   final int NUMPLAYER = 2;
   final int NUMROW = 6;
   final int NUMCOL = 7;
   final int WINCOUNT = 4;
   final String GAMEFILEFOLDER = "gamefiles";

   // declare all "global" variables here
   public int[][] board; //board[0][0] is top left corner
   
   int curPlayer = 1; //player 1 or 2
   
   //Sets board to be empty by iterating through all cells
   public void resetBoard(){
      for(int i = 0; i<NUMROW; i++){
         for(int j = 0; j<NUMCOL; j++){
            board[i][j] = EMPTY;
         }
      }
      //Sets player 1 to go first
      curPlayer = 1;
      gui.setNextPlayer(curPlayer);
   }
   
   //Finds the bottom most row to put a tile, or -1 if the col is filled
   public int locateEmptySlot(int col){
      
      //Goes through the column from the top and checks until the current cell is not empty
      int bottomRow = 0;
      boolean located = false;
      for(int i = 0; i<NUMROW  && board[i][col] == EMPTY; i++){
         bottomRow = i;
         located = true;
      }
      //If no empty cell has been found, it returns -1
      if(!located)bottomRow = -1;
      return bottomRow;
   }
   
   
   //Checks if board is full
   public boolean boardFull(){
   
      //Loop through top row and checks if any cells are empty
      boolean full = true;
      for(int i = 0; i<NUMCOL && full; i++){
         if(board[0][i] == 0)full = false;
      }  
      return full;
   }
   
   //Returns how many are vertically consecutively matching to board[r][c]
   public int verticalConnect(int r, int c){
   
      //Og starting pos
      int player = board[r][c];
      
      //Counter to track number of consecutive
      int counter = 0;
      
      //Temp row variable to iterate through each row, start from the og row.
      int temprow = r;
      
      //Goes through rows above og row
      while(temprow >= 0 && board[temprow][c] == player){
         counter++;
         temprow--;
      }     
      
      //Goes through rows below og row
      temprow = r;
      while(temprow < NUMROW && board[temprow][c] == player){
         counter++;
         temprow++;
      }
      
      //Overcount since we counted og row twice
      counter--;
      return counter;
   }   
   
   //Returns how many are horizontally consecutively matching to board[r][c], same logic as vertical connect
   public int horizontalConnect(int r, int c){
      int player = board[r][c];
      int counter = 0;
      int tempcol = c;
      
      //Goes to rightmost position
      while(tempcol >= 0 && board[r][tempcol] == player){
         counter++;
         tempcol--;
      }     
      tempcol = c;
      
      //Goes to leftmost position
      while(tempcol < NUMCOL && board[r][tempcol] == player){
         counter++;
         tempcol++;
      }
      //Overcount
      counter--;
      return counter;
   }
   
   //Returns how many are diagonally (top left & btm right) consecutively matching to board[r][c]
   public int diagonalConnect1(int r, int c){
      int player = board[r][c];
      int counter = 0;
      
      //Travel to top left
      int tempcol = c;
      int temprow = r;
      while((tempcol >= 0 && temprow >= 0) && board[temprow][tempcol] == player){
         counter++;
         tempcol--;
         temprow--;
      }     
      
      //Travel to btm right
      tempcol = c;
      temprow = r;
      while((tempcol < NUMCOL && temprow < NUMROW) && board[temprow][tempcol] == player){
         counter++;
         tempcol++;
         temprow++;
      }
      //Overcount
      counter--;
      return counter;
   }  
   
    //Returns how many are diagonally (top right & btm left) consecutively matching to board[r][c]
   public int diagonalConnect2(int r, int c){
      int player = board[r][c];
      int counter = 0;
      
      //Top right
      int tempcol = c;
      int temprow = r;
      while((tempcol < NUMCOL && temprow >= 0) && board[temprow][tempcol] == player){
         counter++;
         tempcol++;
         temprow--;
      }     
      
      //Btm left
      tempcol = c;
      temprow = r;
      while((tempcol >= 0 && temprow < NUMROW) && board[temprow][tempcol] == player){
         counter++;
         tempcol--;
         temprow++;
      }
      
      //overcount
      counter--;
      return counter;
   }
   
   //Save board to a file
   public boolean saveToFile(String filename){
   
      try{
         BufferedWriter out = new BufferedWriter (new FileWriter(GAMEFILEFOLDER + "/" + filename));
          
          //Saves whoever the current player is
         out.write(curPlayer+"");
         out.newLine();
          
          //Saves board as a 2d grid type
         for(int i = 0; i< NUMROW; i++){
            for(int j = 0; j<NUMCOL; j++){
               out.write(board[i][j] +" ");
            }
            out.newLine();
         }
         out.close();
          
      } catch (IOException iox) {
         return false;
      }
   
      return true;
   }
   
   //Take board from a file
   public boolean loadFromFile(String name){
      try{
         BufferedReader in = new BufferedReader(new FileReader(GAMEFILEFOLDER + "/" +name));   
            
            //Read current player
         curPlayer = Integer.parseInt(in.readLine());
            
            //Read board in row by row
         for(int i = 0; i<NUMROW; i++){
            
               //Read current row
            String input = in.readLine();
               //Store it in temp array by spaces
            String[] temp = input.split(" ");
            for(int j = 0; j<NUMCOL; j++){
                  //Stores each cell of jth col of the ith row
               board[i][j] = Integer.parseInt(temp[j]);
            }
         }
         in.close();
         //Sets the next player to be whatever we read in earlier
         gui.setNextPlayer(curPlayer);
         return true;
      
      }catch (IOException iox) {
         return false;
      }
   
   }
   
   //Method runs when game starts
   public void start () {
   
      //Initializes board, and sets current player to be the first player
      board = new int [NUMROW][NUMCOL];
      resetBoard();
      curPlayer = 1;
      gui.setNextPlayer(curPlayer);
   }

   //Updates the visual board based on the 2d array
   public void updateGameBoard () {
      for (int r = 0; r < NUMROW; r++) {
         for (int c = 0; c < NUMCOL; c++) {
            if(board[r][c] != EMPTY) gui.setPiece(r, c, board[r][c]);
         }
      }
   
   }
   
  
   public ConnectFour(ConnectFourGUI gui) {
      this.gui = gui;
      start();
   }
       
   public void play (int column) {  
   
   
      //Check if column is full, valid move if returns != -1           
      int row = locateEmptySlot(column);
      if(row != -1){
          
         gui.setPiece(row, column, curPlayer);
         board[row][column] = curPlayer;
            //Finds length of segments created by new piece
         int vlength = verticalConnect(row, column);
         int hlength = horizontalConnect(row, column);
         int dlength1 = diagonalConnect1(row, column);
         int dlength2 = diagonalConnect2(row, column);
            //Won if any of the lengths make "connect 4"
         if(vlength >= WINCOUNT || hlength >= WINCOUNT || dlength1 >= WINCOUNT || dlength2 >= WINCOUNT){
               
            gui.showWinnerMessage(curPlayer);
            resetBoard();
         
            gui.resetGameBoard();
         }  
            //Check if board is full, if it is, then that means a tie, since no player won
         else if(boardFull()){
            gui.showTieGameMessage();
            resetBoard();
            gui.resetGameBoard();
         }
            //If no tie, game carries on and current player changes
         else{
            if(curPlayer == 1)curPlayer = 2;
            else curPlayer = 1;
            gui.setNextPlayer(curPlayer);
         
         } 
      }
   
   }
}