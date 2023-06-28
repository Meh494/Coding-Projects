/**
 * ConnectFourGUI
 * Provide the GUI for the Connect Four game
 */
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ConnectFourGUI {
   private JLabel[][] slots;
   private JFrame mainFrame;
   private ImageIcon[] playerIcon;
   private JLabel nextPlayerIcon;
   private JButton saveGameButton;
   private JButton loadGameButton;
   private JButton restartGameButton;
   private JButton exitButton;
   
   private Color background = new Color(100, 100, 100);
   
   /**
    * Number of players
    */
   public final int NUMPLAYER = 2;
   /**
    * Number of rows on game board
    */
   public final int NUMROW = 6;
   /**
    * Number of columns on game board
    */
   public final int NUMCOL = 7;

   /**
    * graphic files containing the logo: "images/logo.jpg"
    */
   private final String LOGOICON = "images/logo.jpg";
   /**
    * arrays containing the graphic file represnting the player pieces: "images/player1.jpg" & "images/player1.jpg"
    */
   private final String[] ICONFILE = {"images/player1.jpg", "images/player2.jpg"};

   /**
    * dimemention of the player piece: 70 pixels
    */
   private final int PIECESIZE = 70;
   private final int PLAYPANEWIDTH = NUMCOL * PIECESIZE;
   private final int PLAYPANEHEIGHT = NUMROW * PIECESIZE;
   
   private final int INFOPANEWIDTH = 2 * PIECESIZE;
   private final int INFOPANEHEIGHT = PLAYPANEHEIGHT;
   
   private final int LOGOHEIGHT = 2 * PIECESIZE;
   private final int LOGOWIDTH = PLAYPANEWIDTH + INFOPANEWIDTH;
   
   private final int FRAMEWIDTH = (int)(LOGOWIDTH * 1.05);
   private final int FRAMEHEIGHT = (int)((LOGOHEIGHT + PLAYPANEHEIGHT) * 1.1);
   
   // Constructor:  ConnectFourGUI
   // - initialize the imageIcon array
   // - initialize the slots array
   // - create the main frame
   public ConnectFourGUI () {
      initImageIcon();
      initSlots();
      createMainFrame();
   }
    
   // initImageIcon
   // Initialize playerIcon arrays with graphic files
   private void initImageIcon() {
      playerIcon = new ImageIcon[NUMPLAYER];
      for (int i = 0; i < NUMPLAYER; i++) {
         playerIcon[i] = new ImageIcon(ICONFILE[i]);
      }
   }
   
   // initSlots
   // initialize the array of JLabels
   private void initSlots() {
      slots = new JLabel[NUMROW][NUMCOL];
      for (int i = 0; i < NUMROW; i++) {
         for (int j = 0; j < NUMCOL; j++) {
            slots [i] [j] = new JLabel ();
            slots[i][j].setPreferredSize(new Dimension(PIECESIZE, PIECESIZE));
            slots[i][j].setHorizontalAlignment (SwingConstants.CENTER);
            slots[i][j].setBorder (new LineBorder (Color.white));        
         }
      }
   }
   
   // createPlayPanel
   private JPanel createPlayPanel() {
      JPanel panel = new JPanel(); 
      panel.setPreferredSize(new Dimension(PLAYPANEWIDTH, PLAYPANEHEIGHT));
      panel.setBackground(background);
      panel.setLayout(new GridLayout(NUMROW, NUMCOL));
      for (int i = 0; i < NUMROW; i++) {
         for (int j = 0; j < NUMCOL; j++) {
            panel.add(slots[i][j]);
         }
      }
      return panel;    
   }
   
   // createInfoPanel
   private JPanel createInfoPanel() {
      JPanel panel = new JPanel();
      panel.setPreferredSize(new Dimension(INFOPANEWIDTH, INFOPANEHEIGHT));
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      panel.setBackground (background);
   
      Font headingFont = new Font ("Arial", Font.BOLD, 18);
      Font regularFont = new Font ("Arial", Font.BOLD, 16);
                
      JPanel nextPanel = new JPanel();
      nextPanel.setBackground(background);
      
      // Create the label to display "NEXT TURN" heading
      JLabel nextLabel = new JLabel ("Next Animal", JLabel.CENTER);
      nextLabel.setFont(headingFont);
      nextLabel.setAlignmentX (Component.CENTER_ALIGNMENT);
      //nextLabel.setForeground(Color.white);
      
      // Create the JLabel for the nextPlayer
      nextPlayerIcon = new JLabel();
      nextPlayerIcon.setAlignmentX(JLabel.CENTER_ALIGNMENT);
      nextPlayerIcon.setIcon(playerIcon[0]);
    
      nextPanel.add(nextLabel);
      nextPanel.add(nextPlayerIcon);
      
      // panel for the buttons
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
      buttonPanel.setBackground(background);
      
      // button for save game
      saveGameButton = new JButton("Save your game!");
      saveGameButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
      saveGameButton.setFont(regularFont);
                              
      // button for load game
      loadGameButton = new JButton("Load a past game!");
      loadGameButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
      loadGameButton.setFont(regularFont);
     
      // button for restart game
      restartGameButton = new JButton("Restart Game!");
      restartGameButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
      restartGameButton.setFont(regularFont);
   
      // button for exit
      exitButton = new JButton("Rage quit!");
      exitButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
      exitButton.setFont(regularFont);        
   
      buttonPanel.add(saveGameButton);
      buttonPanel.add(Box.createRigidArea(new Dimension(0, 25)));
      buttonPanel.add(loadGameButton);
      buttonPanel.add(Box.createRigidArea(new Dimension(0, 75)));
      buttonPanel.add(restartGameButton);
      buttonPanel.add(Box.createRigidArea(new Dimension(0, 25)));
      buttonPanel.add(exitButton);
      buttonPanel.add(Box.createRigidArea(new Dimension(0, 25)));
   
      panel.add(nextPanel);
      panel.add(buttonPanel);      
      
      return panel;
   }
   
   // createMainFrame
   private void createMainFrame() {      
      // Create the main Frame
      mainFrame = new JFrame ("Connect Four");
      JPanel panel = (JPanel)mainFrame.getContentPane();
      panel.setLayout (new BoxLayout(panel,BoxLayout.Y_AXIS));
      
      // Create the panel for the logo
      JPanel logoPane = new JPanel();
      logoPane.setPreferredSize(new Dimension (LOGOWIDTH, LOGOHEIGHT));
      JLabel logo = new JLabel();
      logo.setIcon(new ImageIcon(LOGOICON));
      logoPane.add(logo);
      
      // Create the bottom Panel which contains the play panel and info Panel
      JPanel bottomPane = new JPanel();
      bottomPane.setLayout(new BoxLayout(bottomPane,BoxLayout.X_AXIS));
      bottomPane.setPreferredSize(new Dimension(PLAYPANEWIDTH + INFOPANEWIDTH, PLAYPANEHEIGHT));
      bottomPane.add(createPlayPanel());
      bottomPane.add(createInfoPanel());
      
      // Add the logo and bottom panel to the main frame
      panel.add(logoPane);
      panel.add(bottomPane);
      
      mainFrame.setContentPane(panel);
      mainFrame.setSize(FRAMEWIDTH, FRAMEHEIGHT);
      mainFrame.setVisible(true);
   }
  
   /**
    * Returns the column number of where the given JLabel is on
    * 
    * @param  label the label whose column number to be requested
    * @return the column number
    */
   public int getColumn(JLabel label) {
      int result = -1;
      for (int i = 0; i < NUMROW && result == -1; i++) {
         for (int j = 0; j < NUMCOL && result == -1; j++) {
            if (slots[i][j] == label) {
               result = j;
            }
         }
      }
      return result;
   }
   
   // add listener to each slot on the game board
   public void addListener (ConnectFourListener listener) {
      for (int i = 0; i < NUMROW; i++) {
         for (int j = 0; j < NUMCOL; j++) {
            slots[i][j].addMouseListener(listener);
         }
      }
   }
   
   // add listener to all the buttons
   public void addListener(ButtonListener listener) {
      saveGameButton.addActionListener(listener);
      loadGameButton.addActionListener(listener);
      restartGameButton.addActionListener(listener);
      exitButton.addActionListener(listener);
   }
   
   /**
    * Display the specified player icon on the specified slot
    * 
    * @param row row of the slot
    * @param col column of the slot
    * @param player the player (number starting at 1) to be displayed
    */
   public void setPiece(int row, int col, int player) {
      slots[row][col].setIcon(playerIcon[player - 1]);
   }
   
    /**
    * Display the appropriate player icon under"Next Turn"
    * 
    * @param player the player (number starting at 1) of the next player; its corresponding icon will be displayed under "Next Turn"
    */
   public void setNextPlayer(int player) {
      nextPlayerIcon.setIcon(playerIcon[player - 1]);
   }
   
   /**
    * Reset the game board (clear all the pieces on the game board)
    * 
    */
   public void resetGameBoard() {
      for (int i = 0; i < NUMROW; i++) {
         for (int j = 0; j < NUMCOL; j++) {
            slots[i][j].setIcon(null);
         }
      }
   }

   /**
   * Display a pop up window displaying the message about a tie game
   * 
   */
   public void showTieGameMessage(){
      String[] options = {"Restart Game!", "Leave the game."};
      int selection = JOptionPane.showOptionDialog(null, "Wow, what a game. Too bad nobody won.", "Tie Game", 
                      JOptionPane.PLAIN_MESSAGE, 2, null, options, options[0]);
      if (selection == options.length - 1) {
         System.exit(0);
      } 
   }

   /**
   * Display a pop up window specifying the winner of this game
   * 
   * @param player the player number of the winner of the game
   */
   public void showWinnerMessage(int player){
      String[] options = {"Restart Game!", "Leave the game."};
      int selection = JOptionPane.showOptionDialog(null, " won this game!", "Someone won!!!!!!", 
                      JOptionPane.PLAIN_MESSAGE, 2, playerIcon[player - 1], options, options[0]);
      if (selection == options.length - 1) {
         System.exit(0);
      } 
   }
    
   public static void main (String[] args) {
      ConnectFourGUI gui = new ConnectFourGUI ();
      ConnectFour game = new ConnectFour (gui);
      ConnectFourListener listener = new ConnectFourListener (game, gui);
      ButtonListener butListener = new ButtonListener(game, gui);
   }
}