/**********************************************************/
/*  Author:      Brendan Lilly                            */
/*  Course:      CSC421                                   */
/*  Professor:   Spiegel                                  */
/*  Assignment:  1                                        */
/*  Due Date:    TBD                                      */
/*  Start Date:  2/2/2016                                 */
/*  Filename:    MemoryCard.java                          */
/*  Purpose:     Main class for the memory card game      */
/*  Time:        Write: Way to damn long.                 */
/*               Debug: Way to damn long.                 */
/*               Test:  Way to damn long.                 */
/**********************************************************/

package com.client;

import com.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.dom.client.Style.Unit;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Event;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.user.client.ui.*;
import java.util.*;
import com.google.gwt.animation.client.Animation;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;


/**
	* This class contains all of the methods that drive the Memory Card game
	* and control the stages of the game.
	* @author Brendan Lilly
*/	
public class MemoryCard implements EntryPoint {
 
  //Creates the ArrayList to hold the players 		
   List<Player> playerList = new ArrayList<Player>();
  //Creates the ArrayList to hold the clicked cards
   List clickedCards = new ArrayList();
  //Creates the Grid that holds the toggle buttons
   Grid cardGrid = new Grid(5,6);
  //Creates the board object 
   Layout board = new Layout();

  //Creates an audio object if it is supported by the browser
   Audio background = Audio.createIfSupported();

  //Sets up counters at 0
   int numClicks = 0;
   int matchedCards = 0;
   int currentPlayer =0;

  //Sets up the boolean flags that control game state
   boolean gameStart = false;
   boolean userControl = true;
   boolean cheatToggle = true;
   boolean wildcardFound = false;
   boolean lobby = true;
   boolean host = false;
   boolean onlineGame = false;
   boolean gameOver = false;
   boolean onlineFull = false;
  private  MemoryCardServiceAsync memoryCardSvc = GWT.create(MemoryCardService.class);
  /**
   * Method that runs on load
*/
  public void onModuleLoad() {
    	
    	//Calls the newGame method and backgroundMusic methos
		newGame();  
		backgroundMusic();

		

		//Add Context menu to the RootPanel
		RootPanel.get().sinkEvents(Event.ONCONTEXTMENU);
		RootPanel.get().addHandler(
		    new ContextMenuHandler() {
		      	//When the context menu event is fired 
		      	// ie : When the user right clicks
		        public void onContextMenu(ContextMenuEvent event) {
		            //Prevents the default right click menu from appearing
		            event.preventDefault();
		            event.stopPropagation();
		            //Create the context menu and display it.
		            createContextMenu(event);
		        }
		    }, ContextMenuEvent.getType()
		);

		Window.addCloseHandler(new CloseHandler<Window>() {
		    public void onClose(CloseEvent<Window> event) {
		        if(onlineGame && !onlineFull)
		        	{
		        		//Window.alert("Server Reset1");
		        		resetServer();
			    	}
			    }
	});



  }


  /**
	* Creates a new board and shuffles it.
	* @return The newly created and shuffled board.
*/	
	public  Layout generateCards(){
		
		boolean noCheat = false;
		
		//Create a new Layout object
		Layout board = new Layout();
		ArrayList<String> masterBoard = new ArrayList<String>();
		//Create new Card objects and add them
		// to the board object.
		if(!onlineGame)
		{	
			
			//Each card objects symbol is the path to the image file
			board.addCard(new Card("CardImages/masterkan.jpg",noCheat));
			board.addCard(new Card("CardImages/caine.jpg",noCheat));
			board.addCard(new Card("CardImages/david.jpg",noCheat));
			board.addCard(new Card("CardImages/grasshopper.jpg",noCheat));
			board.addCard(new Card("CardImages/hat.jpg",noCheat));
			board.addCard(new Card("CardImages/horse.jpg",noCheat));
			board.addCard(new Card("CardImages/kungfu.jpg",noCheat));
			board.addCard(new Card("CardImages/kungfu3.jpg",noCheat));
			board.addCard(new Card("CardImages/masterpo.jpg",noCheat));
			board.addCard(new Card("CardImages/masterpowithcandles.jpg",noCheat));
			board.addCard(new Card("CardImages/nicekungfu.jpg",noCheat));
			board.addCard(new Card("CardImages/training.jpg",noCheat));
			board.addCard(new Card("CardImages/twopeople.jpg",noCheat));
			board.addCard(new Card("CardImages/wantedposter.jpg",noCheat));
			board.addCard(new Card("CardImages/younggrasshoper.jpg",noCheat));
			
			//Set up WildCards
			board.setupWildCards();
			//Once all of the cards are added
			// shuffle the board to randomize it.
		
			board.shuffleBoard();
			return board;	
		}	
		if(host && onlineGame)
		{	
			
			//Each card objects symbol is the path to the image file
			board.addCard(new Card("CardImages/masterkan.jpg",noCheat));
			board.addCard(new Card("CardImages/caine.jpg",noCheat));
			board.addCard(new Card("CardImages/david.jpg",noCheat));
			board.addCard(new Card("CardImages/grasshopper.jpg",noCheat));
			board.addCard(new Card("CardImages/hat.jpg",noCheat));
			board.addCard(new Card("CardImages/horse.jpg",noCheat));
			board.addCard(new Card("CardImages/kungfu.jpg",noCheat));
			board.addCard(new Card("CardImages/kungfu3.jpg",noCheat));
			board.addCard(new Card("CardImages/masterpo.jpg",noCheat));
			board.addCard(new Card("CardImages/masterpowithcandles.jpg",noCheat));
			board.addCard(new Card("CardImages/nicekungfu.jpg",noCheat));
			board.addCard(new Card("CardImages/training.jpg",noCheat));
			board.addCard(new Card("CardImages/twopeople.jpg",noCheat));
			board.addCard(new Card("CardImages/wantedposter.jpg",noCheat));
			board.addCard(new Card("CardImages/younggrasshoper.jpg",noCheat));
			
			//Set up WildCards
			board.setupWildCards();
			//Once all of the cards are added
			// shuffle the board to randomize it.
		
			board.shuffleBoard();
			for(int i=0;i<30;i++)
			{

				masterBoard.add(board.getSymbolByLocation(i));
				

			}	
			putBoardOnServ(masterBoard);
			//Return the fully populated board
			return board;
		}

		return board;
	
	}
  /**
	* Creates a new grid to hold the cards
	* @param board The shuffled board
*/		
	public  void generateGrid(Layout board){


	  		 int numRows = cardGrid.getRowCount();
	     	 int numColumns = cardGrid.getColumnCount();
	     	 int cardCount = 0;
	     	
	     	 //Start of for loops to iterate through entire grid
		     for (int row = 0; row < numRows; row++) {
		        for (int col = 0; col < numColumns; col++) {

		        	final int row1= row;
		        	final int col1 = col;
		        	//Sets the widget at each cell in the grid to a 
		        	// toggle button with one side being the card back image
		        	// and the other side being the card symbol image.
		            cardGrid.setWidget(row, col, 
		            new ToggleButton(new Image("CardImages/cardback.jpg"),
		            		new Image(board.getSymbolByLocation(cardCount))
		            		,new ClickHandler(){
		            			public void onClick(ClickEvent event) {

		            				//Gets the source of the event
			            			 ToggleButton tb = 
			            			 	(ToggleButton)(event.getSource());

			            			 //If its the CPUs turn
			            			 // disallow the click and return
			            			 if(!userControl)
			            				{
			            					tb.setDown(false);
			            					return;
			            				}
			            			 //Iterate through the grid 
			            			 // until the clicked cell is found
			            			 int tempR = cardGrid.getRowCount();
			            			 int tempC = cardGrid.getColumnCount();
			            			 for (int row = 0; row < tempR; row++) {
			       						 for (int col = 0; col < tempC; col++) 
			       						 {
							        		if(cardGrid.getWidget(row,col)==tb)
							        		{
							        			//Once the cell is found
							        			//call the addCard 
							        			// and recordCard 
							        			// methods.
							        			addCard((6*row)+col);
							        			recordCard((6*row)+col);
							        			if(onlineGame)
							        			{
							        				addCardToServer((6*row)+col);
							        			}
							        		}

			        					}
			        				}
			        				flipSound();
			        				//Iterate the current number of clicks
		     						 numClicks++;
		     						 //Call the mainGameLoop();
		  							 mainGameLoop();
		  						  
	  							}

		            		}));
		            //Increase card count
		            cardCount++;

		         }


		      } 

		  //Create a new Decorator panel and add the grid to it	

		  DecoratorPanel dPanel = new DecoratorPanel();
	      dPanel.add(cardGrid);
	      // Add the panel to the root panel.
	      RootPanel.get("section").add(dPanel);
	      if(onlineGame && !userControl)
	      {
	      	mainGameLoop();
	      }
	}

/**
	* Prompts the user to start a new game.
*/	
	public  void newGame(){

	  //Create a new button
	  Button start = new Button("Start New Game!");

      // Add button to the Horizontal panel
      HorizontalPanel hPanel = new HorizontalPanel();
      hPanel.add(start);
      //Add the horizontal panel to the decorator panel
      DecoratorPanel dPanel = new DecoratorPanel();
      dPanel.add(hPanel);
      //Add the decorator panel to the root panel
      RootPanel.get("section").add(dPanel);

      //Add an click handler to the button
      start.addClickHandler(new ClickHandler() {
         
         public void onClick(ClickEvent event) {
         	//Clears the root panel
         	RootPanel.get("section").clear();
         	//Calls the displaySetup function
           	displaySetup();

	         }
	      });
     }

/**
	* Prompts the user to choose number of players.
*/	
    public  void displaySetup(){

     //Creates two new buttons
       Button one = new Button("Single Player");
       Button local = new Button("Multiplayer Local");
       Button online = new Button("Multiplayer Online");


     //Create a listbox to allow the user to chose number of players
	   final ListBox playerNum = new ListBox();
			  playerNum.addItem("2");
			  playerNum.addItem("3");
			  playerNum.addItem("4");
			  playerNum.addItem("5");
			  playerNum.addItem("6");
			  playerNum.setSelectedIndex(0);
	  
	  //Create Horizontal panel to add local button and listbox
	   HorizontalPanel hPanel = new HorizontalPanel();
	   hPanel.add(local);
	   hPanel.add(playerNum);
     //Adds the two buttons to the vertical panel and then to the 
     // decorator panel 
       VerticalPanel vPanel = new VerticalPanel();
       vPanel.setSpacing(10);
       vPanel.add(one);
       vPanel.add(hPanel);
       vPanel.add(online);
			  
		
		


       DecoratorPanel dPanel = new DecoratorPanel();
       dPanel.add(vPanel);
       RootPanel.get("section").add(dPanel);

      //Add the click handler to the first button
       one.addClickHandler(new ClickHandler(){
       		public void onClick(ClickEvent event){
       			//Clears the root panel
       			RootPanel.get("section").clear();
       			//calls createPlayers with 1 passed to it
       			createPlayers(1);
       		}
       });

      //Add the click handler to the second button 
       local.addClickHandler(new ClickHandler(){
       		public void onClick(ClickEvent event){
       			//Clears the Root panel
       			RootPanel.get("section").clear();
       			//Calls createPlayers with 2 passed to it
       			createPlayers(Integer.parseInt(playerNum.getSelectedValue()));
       		}
       });

       online.addClickHandler(new ClickHandler(){
       		public void onClick(ClickEvent event){
       			//Clears the Root panel
       			RootPanel.get("section").clear();
       			//Calls createPlayers with 2 passed to it
       			onlineGame=true;
       			createOnlineGame();

       		}

       });

    }

/**
	* Prompts the user to enter a name and type for each player.
	* @param numPlayers The number of players needed to be set up.  
*/	
    public  void createPlayers(int numPlayers){

    	//Create the grid to get player info in.
  		final Grid grid = new Grid(numPlayers+1,2);

  		
    	 final int numRows = grid.getRowCount();
     	 final int numColumns = grid.getColumnCount();
     	 //Add labels to the top of the grid
     	 grid.setWidget(0, 0, new Label("Player Name: " ));
     	 grid.setWidget(0, 1, new Label("Human or Computer"));
	    
	     //Add Text boxes and toggle buttons to the grid
	     for (int row = 1; row < numRows; row++) {
	        for (int col = 0; col < numColumns; col++) {
	        	//First col is text boxes for names
	        	if(col == 0){
	        	 grid.setWidget(row, col, new TextBox());
	        	 ((TextBox)grid.getWidget(row,col)).setText("Player"+row);
	        	}
	        	//Second col and row 1 needs to be Human only
	        	else if(col == 1 && row == 1){
	          	  grid.setWidget(row, col, new ToggleButton("Human"));
	         	}
	         	//Otherwise add a toggle button with options for human or cpu
	         	else if(col==1)
	         	{
	         	grid.setWidget(row, col, new ToggleButton("Human","Computer"));
	         	}

	         }
	      } 

	      //Create a new button 
	      Button begin = new Button("Begin");

	      //Add a click handler to this button
	      begin.addClickHandler(new ClickHandler(){
       		public void onClick(ClickEvent event){
       			//Iterate through the grid and get the names and types
       			 String name = " ", type = " ";
			     for (int row = 1; row < numRows; row++) {
			        for (int col = 0; col < numColumns; col++) {
			   			
			   			if(col == 0){
			   			name = ((TextBox)grid.getWidget(row,col)).getText();
			   			}
			   			else if(col == 1){
			   				type = grid.getText(row,col);
			   			}
			        	
			         }
			        //If the type is human create a human player
		         	if(type == "Human"){
		         		//And add the human player to the playerList
		   				playerList.add(new HumanPlayer(name,type));
		   			}
		   			else
		   			{
		   				//Else they are computer player
		   				//Add them to the same player list
		   				playerList.add(new ComputerPlayer(name,type));
		   			}
			      } 
			    //Clear the root panel  
       			RootPanel.get("section").clear();
       			//Generate the side panel for stats 
       			generateSidePanel();
       			//Generate the main game grid
       			board = generateCards();
      			generateGrid(board);
      			gridSound();
      			

       		}
       	  });

	      //Add the grid to a decorator panel and the button to another
	      DecoratorPanel dPanel = new DecoratorPanel();
	      DecoratorPanel beginP = new DecoratorPanel();
          dPanel.add(grid);
          beginP.add(begin);
          //Add them both to the root panel
	      RootPanel.get("section").add(dPanel);
	      RootPanel.get("section").add(beginP);
	      
    }



/**
	* When a card is clicked this adds it to the clickedCards array list
	* @param location The location of the card that was clicked   
*/	
    public  void addCard(int location){
    	clickedCards.add(location);

    }

/**
	* The Main Game Loop that is checked after each click to determine 
	* the current state of the game.                              
*/	
    public  void mainGameLoop(){
    		
    		 if(onlineGame && !userControl){
    				//Flip the cards up
    				//Window.alert("Get From server");
    		 	if(numClicks!=2)
    		 	{

    				getCardFromServer();
    		 	}
    

    		}

    		if(numClicks == 1){
    			//If there has been one click 
    			// and the player is human then disable the card they clicked
    			// so they cannot reselect it.
    			if(!(playerList.get(currentPlayer) instanceof ComputerPlayer)){
    			enabled(false);
    			}
    		}
    		// If there have been two clicks recorded 
    		if(numClicks == 2){
    			//numClicks=0;
    			//And the player is human , re enable the first card they 
    			//clicked.
    			if(!(playerList.get(currentPlayer) instanceof ComputerPlayer)){
    			enabled(true);
    			}
    			
    			//Check if the cards are a match
    			if(checkMatch((int)clickedCards.get(0) , 
    											(int)clickedCards.get(1)))
    			{
    				//If yes then set the cards matched for the 
    				// current player
    				playerList.get(currentPlayer).setCardsMatched();
    				//Set the pair as matched on the board
    				board.getCard((int)clickedCards.get(0)).setMatched(true);
    				board.getCard((int)clickedCards.get(1)).setMatched(true);
    				//Update the side panel with the new stats
    				updateSidePanel();

    				//If the player is a cpu then start another cpu turn
    				if(playerList.get(currentPlayer) instanceof ComputerPlayer)
    				{
    					cpuTurn();
    				}


    			}
 
    			//Clear the arraylist of clicked cards
    			clickedCards.clear();
    			
    		
    		}
    		//If all cards are matched call the gameOverState()
    		if(matchedCards==15)
    		{
    			if(!gameOver)
    			{
    				gameOver= !gameOver;
    				gameOverState();
    			}
    			
    		}
    	
    }




/**
	* Checks if a match has been found.
	* @param cardOne The location of the first card.
	* @param cardTwo The location of the second card.             
	* @return True if a correct match is made false otherwise.            
*/
	public  boolean checkMatch(int cardOne, int cardTwo)
	{


		//Window.alert("CHECKING MATCH");
		//If the symbols on the two cards passed in are equal
		if(board.getCard(cardOne).getWildCard() &&
			 board.getCard(cardTwo).getWildCard())
		{
			//Reset the number of clicks
			numClicks =0;
			//Increase the number of matches
			matchedCards++;
			//Call disableCards so that these two 
			//cannot be clicked again
			disableCards(cardOne,cardTwo);
			playWildCardSound();
			playerList.get(currentPlayer).setScore(100);
			playAnimation(0);
			//Return true
			return true;

			
		}
		
		else if(board.getSymbolByLocation(cardOne)
		   ==board.getSymbolByLocation(cardTwo))	
		{
			//Reset number of clicks
			numClicks = 0;
			matchedCards++;

			//Call disableCards so that these two 
			//cannot be clicked again
			disableCards(cardOne,cardTwo);
			//Return true
			playerList.get(currentPlayer).setScore(25);
			playMatchSound();
			return true;


		}
		else if(board.getCard(cardOne).getWildCard())
		{
			//Reset number of clicks
			numClicks = 0;
			matchedCards++;

			//Call disableCards so that these two 
			//cannot be clicked again
			disableCards(cardOne,cardTwo);
			//Return true			
			wildcardFound = true;
			playerList.get(currentPlayer).setScore(25);
			playMatchSound();
			return true;
		}
		else if(board.getCard(cardTwo).getWildCard())
		{
			//Reset number of clicks
			numClicks = 0;
			matchedCards++;

			//Call disableCards so that these two 
			//cannot be clicked again
			disableCards(cardOne,cardTwo);
			//Return true
			wildcardFound = true;
			playerList.get(currentPlayer).setScore(25);
			playMatchSound();
			return true;
		}
		else if(matchedCards == 14 && wildcardFound == true)
		{
			//Reset number of clicks
			numClicks = 0;
			matchedCards++;

			//Call disableCards so that these two 
			//cannot be clicked again
			disableCards(cardOne,cardTwo);
			playerList.get(currentPlayer).setScore(20);
			playMatchSound();
			//Return true
			return true;

		}
		else
		{
			//Flip the cards down
			resetCards(cardOne,cardTwo);
			//Return false due to no match
			// and set numClicks to 0
			noMatchSound();
			numClicks =0;

			return false;
		}
	}



	/**
	* Sets the cards passed to disabled      
	* @param cardOne The first card in the match.
	* @param cardTwo The second card in the match.     
*/	
	public  void disableCards(int cardOne, int cardTwo)
	{
		//Gets the X and Y values based on the index in the array list
		// 6 is the width of the board
		// x is equal to index/6
		// y is equal to index%6

		final int cardOneX = (cardOne/6);
		final int cardOneY = (cardOne%6);
		final int cardTwoX = (cardTwo/6);
		final int cardTwoY = (cardTwo%6);
		
		
			  
		if(onlineGame)
		{
			//Start a timer 
		Timer timer = new Timer(){

		      @Override
		      public void run() {
		      	//When the time runs turn the cards face down

	        	((ToggleButton)cardGrid.getWidget(cardOneX,cardOneY)).setEnabled(false);
				((ToggleButton)cardGrid.getWidget(cardTwoX,cardTwoY)).setEnabled(false);
				resetCardsOnServ();
				mainGameLoop();
			
		      }
   		 };
		//Call the time on a 1.5 second delay.
		timer.schedule(1500);
			
		}
		else
		{
			//Gets the Widget at the x and y in the board 
		//Casts it as a Toggle button and sets it to disabled.
		((ToggleButton)cardGrid.getWidget(cardOneX,cardOneY)).setEnabled(false);
		((ToggleButton)cardGrid.getWidget(cardTwoX,cardTwoY)).setEnabled(false);
		}


		//Goes through all current players
		for(int i = 0; i < playerList.size();i++)
		{
			//If any of them are Computer Players
			if(playerList.get(i) instanceof ComputerPlayer)
			{
				//Remove the matched cards from their memory
				((ComputerPlayer)playerList.get(i)).removeFromMem(cardOne
																,cardTwo);
			}
		}
		
	}


	/**
	* Sets the cards passed to face down      
	* @param card1 The first card 
	* @param card2 The second card  
*/	
	public  void resetCards(int card1, int card2){

		final int cardOne = card1;
		final int cardTwo = card2;

	
		//Start a timer 
		Timer timer = new Timer(){

		      @Override
		      public void run() {
		      	//When the time runs turn the cards face down

	        	((ToggleButton)cardGrid.getWidget((cardOne/6),
	        								(cardOne%6))).setValue(false);
				((ToggleButton)cardGrid.getWidget((cardTwo/6),
											(cardTwo%6))).setValue(false);
					if(onlineGame){
						resetCardsOnServ();
					}
				
    				//The rounds played gets increased for the current
    				// player
    				playerList.get(currentPlayer).setRoundsPlayed();
    				

    				if(playerList.size()!=1){
    					//Change to the next player
    					changePlayer();
    				}

    				//Update the side panel with the new stats
    				updateSidePanel();
			
		      }
   		 };
		//Call the time on a 1.5 second delay.
		timer.schedule(1500);
	
	}

	/**
	* Generates the panel on the left hand side.    
*/	
	public  void generateSidePanel()
	{

		//Creates a new tab panel and grid
		 TabPanel tPanel = new TabPanel();
		 Grid infoGrid = new Grid(playerList.size()+1,5);
		 infoGrid.setBorderWidth(3);
		 infoGrid.setCellSpacing(2);

	 	 final int numRows = infoGrid.getRowCount();
     	 final int numColumns = infoGrid.getColumnCount();

     	 //Set the labels on the top row of the info grid
     	 infoGrid.setWidget(0, 0, new Label("Name" ));
     	 infoGrid.setWidget(0, 1, new Label("Type"));
     	 infoGrid.setWidget(0,2,new Label("Card Matches"));
     	 infoGrid.setWidget(0,3,new Label("Rounds Played"));
     	 infoGrid.setWidget(0,4,new Label("Score"));
	     
	     //Iterate through all of the cells in the info grid
	     for (int row = 1; row < numRows; row++) {
	        for (int col = 0; col < numColumns; col++) {
	        	
	        	//If it is col 0
	        	if(col == 0){
	        		//If the current player is equal to the row of the label
	        		// being added , set the style to currentToken
	        		// This adds a background
	        		if((row-1)==currentPlayer){
		        	 	infoGrid.setWidget(row, col,
		        	 			 new Label(playerList.get(row-1).getName()));
		        	 	((Label)infoGrid.getWidget(row,
		        	 					col)).setStyleName("currentToken");
	        		}
	        		else
	        		{
	        			//Else just print the name
	        			infoGrid.setWidget(row, col, 
	        					new Label(playerList.get(row-1).getName()));
	        		}
	        	}
	        	//Set the label with the players type
	        	if(col == 1)
	        	{
	        	 infoGrid.setWidget(row,col,
	        	 			new Label(playerList.get(row-1).getType()));
	        	}
	        	//Sets the label with the players cards matched
	        	if(col ==2){
	        		infoGrid.setWidget(row,col,
	        			new Label(Integer.toString(
	        			playerList.get(row-1).getCardsMatched())));
	        	}
	        	//Sets the label with the players rounds played
	        	if(col == 3){
	        		infoGrid.setWidget(row,col,
	        			new Label(Integer.toString(
	        				playerList.get(row-1).getRoundsPlayed())));
	        	}
				
				if(col == 4)
				{
					infoGrid.setWidget(row,col, new Label(Integer.toString(playerList.get(row-1).getScore())));
				}	       

	         }
	      //End of the outer for loop
	      } 

	  //Add a drop down menu to control the volume of the background music
	  final ListBox lbox = new ListBox();
	  lbox.addItem("Muted");
	  lbox.addItem("25%");
	  lbox.addItem("50%");
	  lbox.addItem("75%");
	  lbox.addItem("100%");
	  lbox.setSelectedIndex(2);
	  
	 
	  lbox.addChangeHandler(new ChangeHandler(){

	  	public void onChange(ChangeEvent event){
	  		setBackgroundVolume(lbox.getSelectedValue());
	  	}

	  });



	  //Creates an image to toggle cheats
	  final Image cheater = new Image("CardImages/cheatImage.jpg");
	  
	  //Addss a mouseDownHandler
	  cheater.addMouseDownHandler(new MouseDownHandler() {
         
         public void onMouseDown(MouseDownEvent event) {
         	//When the mouse is pressed down call the enabledCheats() method
         	enableCheats();
         	cheater.setUrl("CardImages/cheatImageEnabled.jpg");

         }
      });  

      //Addss a mouseuphandler
	  cheater.addMouseUpHandler(new MouseUpHandler() {
         
         public void onMouseUp(MouseUpEvent event) {
         	//When mouse click is released call the enabledCheats() method
         	enableCheats();
         	cheater.setUrl("CardImages/cheatImage.jpg");

         }
      });    



	  //Create a decorator panel and add the infoGrid
	  DecoratorPanel decoratorPanel = new DecoratorPanel();
      decoratorPanel.add(infoGrid);
      
      //Create Vertical panel for the second tab
      VerticalPanel vPanel = new VerticalPanel();

      //Create Horizontal panel and add the volume control / cheat
      HorizontalPanel hPanel = new HorizontalPanel();
      hPanel.add(new Label("Volume Control: "));
      hPanel.add(lbox);
      
      vPanel.add(hPanel);
      vPanel.add(cheater);
      
      // Configure the tab panel
      tPanel.add(decoratorPanel, "Game Info");
      tPanel.add(vPanel, "Game Settings");
      tPanel.setStyleName("side");
      tPanel.setWidth("100");
      tPanel.selectTab(0);
      // Add the widgets to the root panel
      RootPanel.get("nav").add(tPanel);

	}

	/**
	* Clears the nav section of the root panel
	* and generates a new side panel with
	* updated information.      
*/	
	public  void updateSidePanel()
	{
		RootPanel.get("nav").clear();
		generateSidePanel();
	}


	/**
	* Changes the current player to the next player    
*/	
	public  void changePlayer()
	{
		currentPlayer++;
		if(onlineGame)
		{
			checkGameStatus();
		}
		//If the current player is greater than number of players
		if(currentPlayer > playerList.size()-1){
			//Set it back to the start
			currentPlayer =0;
		}

		//If the current player is a computer
		if(playerList.get(currentPlayer) instanceof ComputerPlayer){
			//Start a computer turn
			cpuTurn();
		}
		
		else
		{
			userControl = !userControl;
			//Window.alert("User control is now:" + userControl);
			if(!userControl){
				mainGameLoop();
			}
			
		}

	}


	/**
	* Sets the cards passed to face down      
	* @param flag True to enable the card , false to disable it  
*/	
	public  void enabled(boolean flag){
		//Gets the toggle button at the position saved in the 
		// 0 index of the clickedCards array and call setEnabled on it.
		((ToggleButton)cardGrid.getWidget(((int)clickedCards.get(0)/6),
			((int)clickedCards.get(0)%6))).setEnabled(flag);
	}


	/**
	* Starts the Game Over state at the end of the game     
*/	
	public  void gameOverState()
	{
		int highScore = 0;
		String winner = "Spiegel";
		//Loops through all players and finds the one with the most matches
		for(int i=0;i < playerList.size();i++)
		{
			if(highScore < (playerList.get(i).getScore()/playerList.get(i).getRoundsPlayed()))
			{
				highScore = (playerList.get(i).getScore()/playerList.get(i).getRoundsPlayed());
				winner = playerList.get(i).getName();
			}
		}

		if(highScore<=100)
		{
			playAnimation(1);
		}
		else if(highScore<=200)
		{
			playAnimation(2);
		}
		else
		{
			playAnimation(3);
		}
		

		final int score = highScore;
		final String name = winner;

				//Create new timer
		Timer timer1 = new Timer(){

			      @Override
			      public void run() {
			      		Window.alert("The winner of the game is "+name+" with the total score of "+score+"!");
			      		Window.Location.reload();
			      }
	    };
		
		//Schedule the timer for 2 seconds
		timer1.schedule(10000);	





	}


	/**
	* Enables the cheat to show the facedown cards    
*/	
	public  void enableCheats()
	{

		 int numRows = cardGrid.getRowCount();
     	 int numColumns = cardGrid.getColumnCount();
     	 if(cheatToggle == true){
     	 	//If the toggle is on 
		     for (int row = 0; row < numRows; row++) {
		        for (int col = 0; col < numColumns; col++) {
		        	if(!board.getCard((row*6)+col).getMatched()) {
		        	//Set the widget to true
		        	((ToggleButton)cardGrid.getWidget(row,col)).setValue(true);
		        	
		        	}
		        }
				}
			cheatToggle = !cheatToggle;
		}
		else
		{
			//If the cheat toggle is off
			for (int row = 0; row < numRows; row++) {
	        for (int col = 0; col < numColumns; col++) {
	        	if(!board.getCard((row*6)+col).getMatched()) {
	        	//Set the widget to false
	        	((ToggleButton)cardGrid.getWidget(row,col)).setValue(false);
	        	
	        	}
	        	}
			}
			cheatToggle = !cheatToggle;

		}
		

	}

	/**
	* Controls what the cpu does during their turn    
*/	
	public  void cpuTurn(){

		//Sets user control to false
		userControl = false;

		//Checks if the game is over 
		if(matchedCards == 15){
			//Return without looking for more cards
			return;
		}

		//Get the first and second cards to flip
		final int cardOne = getChoiceOne();
		final int cardTwo = getChoiceTwo(cardOne);

		//Record both of these cards in memory	
		recordCard(cardOne);
		recordCard(cardTwo);

		//Create new timer
		Timer timer1 = new Timer(){

			      @Override
			      public void run() {
			      		//Add the cards to the cardList
			      		addCard(cardOne);
	    				addCard(cardTwo);
	    				//Increase number of clicks to two to simulate 
	    				// the computer clicking twice
			      		numClicks = 2;
			      		flipSound();
			      		//Flip the cards up
			        	((ToggleButton)cardGrid.getWidget((cardOne/6)
			        			,(cardOne%6))).setValue(true);
						((ToggleButton)cardGrid.getWidget((cardTwo/6)
								,(cardTwo%6))).setValue(true);
						//Call the main game loop
						mainGameLoop();



			      }
	    };
		
		//Schedule the timer for 2 seconds
		timer1.schedule(2000);		

	}

	/**
	* Gets the first card choice for the cpu
	* @return The index of the first card choice   
*/	
	public  int getChoiceOne()
	{
		//Assign pair the value returned from pairFound
		int pair = ((ComputerPlayer)playerList.get(currentPlayer)).pairFound();
	   
	   //Checks that there are more than 1 pairs left
	   if(matchedCards != 14)
	   {
	   		//Checks that the value of pair is valid
			if(pair != -1 && board.getCard(pair).getMatched() != true){
				//Returns the valid index
				return pair;
			}
			else{
				//Returns a random index that is not matched
				Random rand = new Random();
				int randomNum = rand.nextInt((29 - 0) + 1) + 0;
				while(board.getCard(randomNum).getMatched()){
					randomNum = rand.nextInt((29 - 0) + 1) + 0;
				}
				return randomNum;
			}
		}
		//If there is only one pair left
		else
		{
			int i = 0;
			//Search the board for the unmatched card
				while(board.getCard(i).getMatched() == true)
				{
					i++;
				}
			return i;
			
		}

	}

	/**
	* Gets the second card choice for the cpu.
	* @param cardOne The first card choice.
	* @return The second card choice.    
*/	
	public  int getChoiceTwo(int cardOne){

		//Generate a new randon
		Random rand= new Random();
	    int randomNum = rand.nextInt((29 - 0) + 1) + 0;

	    //Try and get the matching card to cardOne
	    int cardTwo = ((ComputerPlayer)playerList.get(
	    		currentPlayer)).getMatchingCard(cardOne);
	    //Check that there are more than one pairs left
	   if(matchedCards != 14)
	   {
	   		//If the second card is valid
		    if(cardTwo != -1 && board.getCard(cardTwo).getMatched() 
		    				!= true && matchedCards < 15){
		    	//Return it
				return cardTwo;

			}
		    else
		    {	//Return a random index that is not already matched
				while(randomNum == cardOne 
						|| board.getCard(randomNum).getMatched()){
					 randomNum = rand.nextInt((29 - 0) + 1) + 0;
				}
				return randomNum;
			}
		}
		else
			{
				int i =0;
				//Choose the last unmatched card
					while(board.getCard(i).getMatched() == true || i == cardOne)
					{
						i++;
					}
				return i;
			}	
	}

	/**
	* Records the flipped card in the cpus memory
	* @param location The location to add.    
*/		
	public  void recordCard(int location){

		//Generate a new random object
		Random rand= new Random();
	    int randomNum; 

	    for(int i=0; i < playerList.size();i++){
	    	if(playerList.get(i) instanceof ComputerPlayer)
	    	{
	    		//Iterate through the list of computer players
	    		//select a new random number from 1-100
	    		randomNum =rand.nextInt((100 - 1) + 1) + 1;
	    		//If the number is greater than 30 
		    	if(randomNum > ((ComputerPlayer)playerList.get(i)).getDifficulty()){
		    		//Add the index and symbol to the memory
		    		((ComputerPlayer)playerList.get(i)).setPair(
		    			location , board.getSymbolByLocation(location));
		    	}
		    }
	    }
	}


/**
	* Creates a custom context menu on right click
	* @param event Used to determine the location of the right click
*/
	public  void createContextMenu(ContextMenuEvent event)
	{
		//PopupPanel to be the container of the context panel
		final PopupPanel contextPanel = new PopupPanel(true);
		//MenuBar to hold the menu in the container
		final MenuBar menu = new MenuBar(true);
		//Sub menu for the main menu
		MenuBar difficulty = new MenuBar(true);
		boolean computerPresent = false;

		//Checks if there are any computer players
	  	for(int i = 0; i < playerList.size(); i++)
		{
			if(playerList.get(i) instanceof ComputerPlayer){
				computerPresent = true;
			}
		}	



		//Command that is run when Easy is chosen as the difficulty
		  Command dif1 = new Command() {
	      public void execute() {
	      	for(int i = 0; i < playerList.size(); i++)
				{
					if(playerList.get(i) instanceof ComputerPlayer){
						((ComputerPlayer)playerList.get(i)).setDifficulty(80);
					}
				}	
	      	}
	      };

	     //Command that is run when Medium is chosen as the difficulty
      	 Command dif2 = new Command() {
	      public void execute() {
	      	for(int i = 0; i < playerList.size(); i++)
				{
					if(playerList.get(i) instanceof ComputerPlayer){
						((ComputerPlayer)playerList.get(i)).setDifficulty(55);
					}
				}	
	      	}

	      };

	      //Command that is run when Hard is chosen as the difficulty
      	  Command dif3 = new Command() {
	      public void execute() {

	      	for(int i = 0; i < playerList.size(); i++)
				{
					if(playerList.get(i) instanceof ComputerPlayer){
						((ComputerPlayer)playerList.get(i)).setDifficulty(30);
					}
				}	
	   

	      	}
	      };

	      //Command that is run when Expert is chosen 
      	  Command dif4 = new Command() {
	      public void execute() {

	      	for(int i = 0; i < playerList.size(); i++)
				{
					if(playerList.get(i) instanceof ComputerPlayer){
						((ComputerPlayer)playerList.get(i)).setDifficulty(2);
					}
				}	
	      	}
	      };

	     //Command that is run when quit is selected
	     Command quit = new Command() {
	      public void execute() {
				Window.Location.reload();
	      	}
	      };



	      //Command that is run when cancel is selected 
      	  Command emptyCmd = new Command() {
	      public void execute() {
			contextPanel.setVisible(false);
	      	}
	      };


	     //Adding the difficulty settings and their Commands to the 
	     // difficulty sub menu
		difficulty.addItem("Easy" ,dif1);
		difficulty.addItem("Medium" ,dif2);
		difficulty.addItem("Hard" ,dif3);
		difficulty.addItem("Expert" ,dif4);

		//Checks if a computer player is present
		if(computerPresent){
			//If so add the sub menu difficulty to the main menu 
			menu.addItem("Difficulty" , difficulty);
		}

		//Add the options to Quit or Cancel to the main menu
		menu.addItem("Quit" , quit);
		menu.addItem("Cancel" , emptyCmd);
		//Set the width of the menu
		menu.setWidth("60px");

		//Add the menu to the popuppanel
		contextPanel.add(menu);
		contextPanel.setWidth("60px");
		
		//Set the popuppanel to hide when the user clicks off it
		contextPanel.setAutoHideEnabled(true);

		//Set the postion based off of where the event is fired
		contextPanel.setPopupPosition(event.getNativeEvent().getClientX(),
                event.getNativeEvent().getClientY());
		//Display the panel
		contextPanel.show();
	}


/**
	* Starts the background music that plays during gameplay 
*/	
    public  void backgroundMusic(){
    	//Sets the source of the music
    	background.setSrc("Sounds/caineTheme.mp3");
    	//Play the music and set it to loop
    	background.setVolume(.15);
    	background.play();
    	background.setLoop(true);
    }

/**
	* Starts the background music that plays during gameplay 
*/	
    public  void setBackgroundVolume(String volume){
    	if(volume == "Muted")
    	{
    		background.setVolume(0);
    	}
    	else
    	{
	    	String value = volume.replace("%" , "");
	    	double volumeNum = (Integer.parseInt(value)/100.0);
	       	background.setVolume((.30 * volumeNum));
    	}
    }    


/**
	* Plays shuffling noise on grid creation   
*/	
    public  void gridSound(){
    	//Creates an audio object if it is supported by the browser
    	Audio gridShuffle = Audio.createIfSupported();
    	//Sets the source of the music
    	gridShuffle.setSrc("Sounds/cardShuffle.wav");
    	//Play the music and set it to loop
    	gridShuffle.setVolume(1.0);
    	gridShuffle.play();
    }


/** 
	* Plays a tone when the wildcards are matched.
*/
	public  void playWildCardSound()
	{
		//Creates an audio object if it is supported by the browser
    	Audio wildcard = Audio.createIfSupported();
    	//Sets the source of the music
    	wildcard.setSrc("Sounds/firecrackers.mp3");
    	//Play the music and set it to loop
    	wildcard.setVolume(1.0);
    	wildcard.play();

	} 

/** 
	* Plays a tone when a card is matched.
*/
	public  void playMatchSound()
	{
		//Creates an audio object if it is supported by the browser
    	Audio match = Audio.createIfSupported();
    	//Sets the source of the music
    	match.setSrc("Sounds/match.mp3");
    	//Play the music and set it to loop
    	match.setVolume(1.0);
    	match.play();

	}    	   

/**
	* Plays card flip sound
*/	
    public  void flipSound(){
    	//Creates an audio object if it is supported by the browser
    	Audio flipSound = Audio.createIfSupported();
    	//Sets the source of the music
    	flipSound.setSrc("Sounds/cardSlide1.wav");
    	//Play the music and set it to loop
    	flipSound.setVolume(1.0);
    	flipSound.play();
    }

/**
	* Plays noMatch sound
*/
	public  void noMatchSound()
	{
		//Creates an audio object if it is supported by the browser
    	Audio noMatch = Audio.createIfSupported();
    	//Sets the source of the music
    	noMatch.setSrc("Sounds/noMatch.mp3");
    	//Play the music and set it to loop
    	noMatch.setVolume(1.0);
    	noMatch.play();
	} 




	/**
	* Plays the appropriate animation on the screen      
	* @param numAnimation The animation number to play.  
*/	
	public void playAnimation(int numAnimation)
	{
		PopupPanel animationPanel = new PopupPanel(true);
		if(numAnimation!=3)
		{
			Image animation = new Image("Animations/animation"+numAnimation+".gif");
			//Set the popuppanel to hide when the user clicks off it
			animationPanel.setAutoHideEnabled(true);
			animationPanel.setGlassEnabled(true);
			animationPanel.add(animation);

			animationPanel.setPopupPosition(300,300);
			//Display the panel
			animationPanel.show();
			
		}
		else
		{
			Animation anim = new CustomAnimation();
	        anim.run(10000);
		}	
	}

//START OF ONLINE stuff




/**
	* Test function used to make sure connection to server is working.   
*/	
	public void onlineTest()
	{
		if(memoryCardSvc == null)
		{
			memoryCardSvc = GWT.create(MemoryCardService.class);
		}

		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>(){
			public void onFailure(Throwable caught){
				Window.alert(caught+" ");

			}
			public void onSuccess(Boolean flag){
				Window.alert(flag +" ");
			}
		};

		memoryCardSvc.getConnected(true , callback);
	}


/**
	* Adds a player to the server     
	* @param name The players name to be added to the server.    
*/	
	public  void addOnlinePlayer(String name)
	{
		if(memoryCardSvc == null)
		{
			memoryCardSvc = GWT.create(MemoryCardService.class);
		}

		AsyncCallback<Integer> callback = new AsyncCallback<Integer>(){
			public void onFailure(Throwable caught){
				Window.alert(caught+" ");

			}
			public void onSuccess(Integer num){
				//If this is the first player
				if(num==1)
				{
					//They are the host
					host=true;
					//Add their board to the server
					board = generateCards();
				}
				else
			       {
			       	//Else get the board from the server
			       	userControl=false;
			       	 getBoardOnServ();
			       	}
			}
		};

		memoryCardSvc.addPlayer(name , callback);
	}




	/**
	* Puts the already shuffled board on the server     
	* @param masterBoard The master board from the server  
*/		
	public void putBoardOnServ(ArrayList<String> masterBoard){
		if(memoryCardSvc == null)
		{
			memoryCardSvc = GWT.create(MemoryCardService.class);

		}

		AsyncCallback<Void> callback = new AsyncCallback<Void>(){

			public void onFailure(Throwable caught)
			{
				Window.alert(caught+" ");
			}
			public void onSuccess(Void Null)
			{
				
			}
		};
		memoryCardSvc.addBoard(masterBoard , callback);

	}




/**
	* Resets the card values on the server        
*/	
	public void resetCardsOnServ(){
		if(memoryCardSvc == null)
		{
			memoryCardSvc = GWT.create(MemoryCardService.class);

		}

		AsyncCallback<Void> callback = new AsyncCallback<Void>(){

			public void onFailure(Throwable caught)
			{
				Window.alert(caught+" ");
			}
			public void onSuccess(Void Null)
			{
				
			}
		};
		memoryCardSvc.resetCards(callback);

	}



/**
	* Resets all of the values on the server.         
*/	
	public void resetServer(){
		if(memoryCardSvc == null)
		{
			memoryCardSvc = GWT.create(MemoryCardService.class);

		}

		AsyncCallback<Void> callback = new AsyncCallback<Void>(){

			public void onFailure(Throwable caught)
			{
				Window.alert(caught+" ");
			}
			public void onSuccess(Void Null)
			{
				//Window.alert("Server Reset");
			}
		};
		memoryCardSvc.resetServer(callback);

	}

/**
	* Adds either the first or second card to the server     
	* @param location The location to be added to the server   
*/	
	public void addCardToServer(Integer location)
	{
		if(memoryCardSvc == null)
		{
			memoryCardSvc = GWT.create(MemoryCardService.class);

		}

		AsyncCallback<Void> callback = new AsyncCallback<Void>(){

			public void onFailure(Throwable caught)
			{
				Window.alert(caught+" ");
			}
			public void onSuccess(Void Null)
			{
				
			}
		};
		if(numClicks==0){
			memoryCardSvc.addCardOne(location , callback);
			//Window.alert("card 1 on server");
		}
		else
		{
		memoryCardSvc.addCardTwo(location , callback);
		//Window.alert("card 2 on server");
		}
	}


/**
	* Gets either the first or second card from the server.         
*/	
	public void getCardFromServer()
	{
		if(memoryCardSvc == null)
		{
			memoryCardSvc = GWT.create(MemoryCardService.class);

		}

		AsyncCallback<Integer> callback = new AsyncCallback<Integer>(){

			public void onFailure(Throwable caught)
			{
				Window.alert(caught+" ");
			}
			public void onSuccess(Integer card)
			{
			
				if(card != -1 && numClicks!= 2)
				{
			   	((ToggleButton)cardGrid.getWidget((card/6),(card%6))).setValue(true);
			   	addCard(card);
			   	flipSound();
			   	numClicks++;
			   	mainGameLoop();
			   }
			   else
			   {
			   	mainGameLoop();
			   }
			}
		};
		if(numClicks==0){
			memoryCardSvc.getCardOne(callback);
			
		}
		else
		{
		memoryCardSvc.getCardTwo(callback);
		
		}
	}


/**
	* Gets the already shuffled board from the server       
*/	
	public void getBoardOnServ(){

		if(memoryCardSvc == null)
		{
			memoryCardSvc = GWT.create(MemoryCardService.class);

		}

		AsyncCallback<ArrayList<String>> callback = new AsyncCallback<ArrayList<String>>(){

			public void onFailure(Throwable caught)
			{
				Window.alert(caught+" ");
			}
			public void onSuccess(ArrayList<String> gameBoard)
			{
				
				for(int i=0;i<gameBoard.size();i++)
				{
					if(gameBoard.get(i)=="CardImages/masterpo.jpg")
					{
						board.addCardOnline(new Card(gameBoard.get(i),true));
					}
					else
					{
					board.addCardOnline(new Card(gameBoard.get(i),false));
					}
					//Window.alert("Client:"+board.getSymbolByLocation(i));
					
				}
				
			}
		};

		memoryCardSvc.getBoard(callback);

	}





/**
	* Checks if the game is populated with players yet
*/	

	public void checkLobby()
	{
		if(memoryCardSvc == null)
		{
			memoryCardSvc = GWT.create(MemoryCardService.class);
		}

		AsyncCallback<ArrayList<String>> callback = new AsyncCallback<ArrayList<String>>(){
			public void onFailure(Throwable caught){
				Window.alert(caught+" ");

			}
			public void onSuccess(ArrayList<String> players){

				//If there are less than two players 
				//Show the waiting animation and call checkConnect again
				if(players.size() <2)
				{
					Image loading = new Image("CardImages/2.gif");
					Label wait = new Label("Waiting for Opponent!");
				      DecoratorPanel load = new DecoratorPanel();
				      DecoratorPanel message = new DecoratorPanel();
			          message.add(wait);
			          load.add(loading);
			          //Add them both to the root panel
				   	  	
				      RootPanel.get("section").add(message);
				      RootPanel.get("section").add(load);
				      checkConnect();
				}
				else
				{
					//Copy the player list from the server
					for(int i=0;i<players.size();i++)
					{
						playerList.add(new OnlinePlayer(players.get(i),"Online"));
					}
					//Create a new button 
				      Button check = new Button("Begin Online Game");

				      //Add a click handler to this button
				      check.addClickHandler(new ClickHandler(){
			       		public void onClick(ClickEvent event){
	       		
	       			 		
						    RootPanel.get("section").clear();
			       			//Generate the side panel for stats 
			       			generateSidePanel();
			       			//Generate the main game grid
			       			
			      			generateGrid(board);
			      			gridSound();
			       		}
			       	  });


				      DecoratorPanel beginP = new DecoratorPanel();
			        
			          beginP.add(check);
			          //Add them both to the root panel
				   
				      RootPanel.get("section").add(beginP);						
				}				
			}
		};

		memoryCardSvc.getPlayers(callback);
		
	}




/**
	* Checks if the other player has left the game     
*/	
public void checkGameStatus()
	{
		if(memoryCardSvc == null)
		{
			memoryCardSvc = GWT.create(MemoryCardService.class);
		}

		AsyncCallback<ArrayList<String>> callback = new AsyncCallback<ArrayList<String>>(){
			public void onFailure(Throwable caught){
				Window.alert(caught+" ");

			}
			public void onSuccess(ArrayList<String> players){

				if(players.size() == 0)
				{
					Window.alert("The other player has forfeited the game you Win!");
					Window.Location.reload();
				}

			}
			};

		memoryCardSvc.getPlayers(callback);
		
	}




/**
	* Checks wether the game lobby already has two people          
*/	
public void gameFull()
	{
		if(memoryCardSvc == null)
		{
			memoryCardSvc = GWT.create(MemoryCardService.class);
		}

		AsyncCallback<ArrayList<String>> callback = new AsyncCallback<ArrayList<String>>(){
			public void onFailure(Throwable caught){
				Window.alert(caught+" ");

			}
			public void onSuccess(ArrayList<String> players){

				if(players.size() == 2)
				{
					Window.alert("There is a game currently in progress please wait.");
					onlineFull=true;
					Window.Location.reload();
				}

			}
			};

		memoryCardSvc.getPlayers(callback);
		
	}	


/**
	* Prompts the user for their name and on submitting it checks the server
	* for other members of the game. 
*/	
    public  void createOnlineGame(){

    	//Create the grid to get player info in.
  		final Grid grid = new Grid(1,2);

  		
    	 final int numRows = grid.getRowCount();
     	 final int numColumns = grid.getColumnCount();
     	 //Add labels to the top of the grid
     	 grid.setWidget(0, 0, new Label("Player Name: " ));
     	 grid.setWidget(0, 1, new TextBox());
	    

	      //Create a new button 
	      Button join = new Button("Join Lobby");

	      //Add a click handler to this button
	      join.addClickHandler(new ClickHandler(){
       		public void onClick(ClickEvent event){
       			//Iterate through the grid and get the names and types
       			gameFull();
			    String name = ((TextBox)grid.getWidget(0,1)).getText();
			    addOnlinePlayer(name);
			    //Clear the root panel  
       			RootPanel.get("section").clear();
       			checkConnect();
       		}
       	  });

       	 //Add the grid to a decorator panel and the button to another
	      DecoratorPanel dPanel = new DecoratorPanel();
	      DecoratorPanel beginP = new DecoratorPanel();
          dPanel.add(grid);
          beginP.add(join);
          //Add them both to the root panel
	      RootPanel.get("section").add(dPanel);
	      RootPanel.get("section").add(beginP);

    }




/**
	* If the player is still waiting in the lobby 
	* wait one second and then check again for players.       
*/	
    public  void checkConnect(){

    	  
    	  if(lobby)
    	  {
    	  	Timer timer1 = new Timer(){

			      @Override
			      public void run() {
			    	  
			    	  	RootPanel.get("section").clear();
			    	  	checkLobby();			    	  
			      }
			    };
				
				//Schedule the timer for 2 seconds
				timer1.schedule(1000);		
    	  }
    


    }

}