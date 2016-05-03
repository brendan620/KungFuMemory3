/**********************************************************/
/*  Author:      Brendan Lilly                            */
/*  Course:      CSC421                                   */
/*  Professor:   Spiegel                                  */
/*  Assignment:  3                                        */
/*  Due Date:    TBD                                      */
/*  Start Date:  2/2/2016                                 */
/*  Filename:    MemoryCardServiceAsync.java              */
/*  Purpose:     Async information                        */
/*  Time:        Write: 20 minutes                        */
/*               Debug: 10 minutes                        */
/*               Test:  30 minutes                        */
/**********************************************************/

package com.client;
import java.util.*;

/**
	* This class contains the async callbacks  
	* @author Brendan Lilly
*/	

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MemoryCardServiceAsync {

/**
  * Used to check connection to server.
  * @param flag The boolen to be inverted.           
  * @return The inverse of the flag passed in.          
*/    
    void getConnected(Boolean flag, AsyncCallback<Boolean> callback);


/**
  * Gets the ArrayList of players from the server.            
  * @return The ArrayList of players           
*/	    
    void getPlayers(AsyncCallback<ArrayList<String>> callback);

/**
  * Adds a player to the game
  * @param name The name of the player being added.            
  * @return The number of players currently in the game           
*/	    
    void addPlayer(String name,AsyncCallback<Integer> callback);

/**
  * Adds the board to the server
  * @param board The ArrayList that contains the strings of the cards.            
*/	    
    void addBoard(ArrayList<String> board,AsyncCallback<Void> callback);

/**
  * Gets the board from the server.           
  * @return The ArrayList of symbols for the board.          
*/	    
    void getBoard(AsyncCallback<ArrayList<String>> callback);

/**
  * Adds the first card to the server
  * @param location The location of the first card.                       
*/	    
   	void addCardOne(Integer location,AsyncCallback<Void> callback);

/**
  * Adds the second card to the server
  * @param location The location of the second card.       
*/	   	
	void addCardTwo(Integer location,AsyncCallback<Void> callback);

/**
  * Gets the first card from the server.          
  * @return The location of the first card on the board           
*/ 	
	void getCardOne(AsyncCallback<Integer> callback);

/**
  * Gets the second card from the server           
  * @return The location of the second card on the board            
*/  	
	void getCardTwo(AsyncCallback<Integer> callback);

/**
  * Resets the cards on the server.          
*/		
	void resetCards(AsyncCallback<Void> callback);

/**
  * Resets all of the variables on the server.         
*/  	
	void resetServer(AsyncCallback<Void> callback);
}