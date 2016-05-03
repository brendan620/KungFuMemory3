/**********************************************************/
/*  Author:      Brendan Lilly                            */
/*  Course:      CSC421                                   */
/*  Professor:   Spiegel                                  */
/*  Assignment:  3                                        */
/*  Due Date:    3/22/2016                                */
/*  Start Date:  3/12/2016                                */
/*  Filename:    MemoryCardService.java                   */
/*  Purpose:                     */
/*  Time:        Write: 5 Minutes                         */
/*               Debug: 0 Minutes                         */
/*               Test: 	5 Minutes                         */
/**********************************************************/


package com.client;
import java.util.*;

/**
	* This class contains the communication methods  
	* @author Brendan Lilly
*/	

import com.google.gwt.user.client.rpc.RemoteService;	
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;	

@RemoteServiceRelativePath("MemoryWeb")
public interface MemoryCardService extends RemoteService
{
	

/**
  * Used to check connection to server.
  * @param flag The boolen to be inverted.           
  * @return The inverse of the flag passed in.          
*/    	
	boolean getConnected(Boolean flag);


/**
  * Gets the ArrayList of players from the server.            
  * @return The ArrayList of players           
*/	
	ArrayList<String> getPlayers();

/**
  * Adds a player to the game
  * @param name The name of the player being added.            
  * @return The number of players currently in the game           
*/	
	int addPlayer(String name);

/**
  * Adds the board to the server
  * @param board The ArrayList that contains the strings of the cards.            
*/	
	void addBoard(ArrayList<String> board);

/**
  * Gets the board from the server.           
  * @return The ArrayList of symbols for the board.          
*/	
	ArrayList<String> getBoard();

/**
  * Adds the first card to the server
  * @param location The location of the first card.                       
*/	
	void addCardOne(Integer location);

/**
  * Adds the second card to the server
  * @param location The location of the second card.       
*/	
	void addCardTwo(Integer location);

/**
  * Resets the cards on the server.          
*/	
	void resetCards();

/**
  * Resets all of the variables on the server.         
*/   	
	void resetServer();

/**
  * Gets the first card from the server.          
  * @return The location of the first card on the board           
*/  	
	int getCardOne();

/**
  * Gets the second card from the server           
  * @return The location of the second card on the board            
*/   	
	int getCardTwo();

}