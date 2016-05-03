/**********************************************************/
/*  Author:      Brendan Lilly                            */
/*  Course:      CSC421                                   */
/*  Professor:   Spiegel                                  */
/*  Assignment:  3                                        */
/*  Due Date:    3/22/2016                                */
/*  Start Date:  3/12/2016                                */
/*  Filename:    MemoryCardServiceImpl.java               */
/*  Purpose:     Server Side Implementation               */
/*  Time:        Write: 5 Minutes                         */
/*               Debug: 0 Minutes                         */
/*               Test:  5 Minutes                         */
/**********************************************************/

package com.server;
import java.util.*;

import com.client.MemoryCard;
import com.client.MemoryCardService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import javax.servlet.ServletOutputStream;
/**
 * The server-side implementation of the RPC service.
 * @auther Brendan Lilly
 */

@SuppressWarnings("serial")
public class MemoryCardServiceImpl extends RemoteServiceServlet implements
    MemoryCardService {
  //Creates the ArrayList to hold the players     
 
  ArrayList<String> playerList = new ArrayList<String>();
  ArrayList<String> boardSymbols = new ArrayList<String>();
  int numPlayers =0;
  int cardOne = -1;
  int cardTwo = -1;



/**
  * Used to check connection to server.
  * @param flag The boolen to be inverted.           
  * @return The inverse of the flag passed in.          
*/     
      public boolean getConnected(Boolean flag){
        return !flag;
      }



/**
  * Gets the ArrayList of players from the server.            
  * @return The ArrayList of players           
*/
      public ArrayList<String> getPlayers()
      {
         
          return playerList;
      }



/**
  * Adds a player to the game
  * @param name The name of the player being added.            
  * @return The number of players currently in the game           
*/
      public int addPlayer(String name)
      {
      
         playerList.add(name);
         numPlayers++;
         return numPlayers;         
      }



/**
  * Adds the board to the server
  * @param board The ArrayList that contains the strings of the cards.            
*/
      public void addBoard(ArrayList<String> board){
          for(int i=0;i<board.size();i++)
          {
            boardSymbols.add(board.get(i));
          }
      }



/**
  * Gets the board from the server.           
  * @return The ArrayList of symbols for the board.          
*/
       public ArrayList<String> getBoard(){
          return boardSymbols;
       }



/**
  * Adds the first card to the server
  * @param location The location of the first card.                       
*/
       public void addCardOne(Integer location){
          cardOne=location;
       }



/**
  * Adds the second card to the server
  * @param location The location of the second card.       
*/
        public void addCardTwo(Integer location){
            cardTwo=location;
        }


/**
  * Resets the cards on the server.          
*/
        public void resetCards(){
          cardOne=-1;
          cardTwo=-1;
        }


/**
  * Gets the first card from the server.          
  * @return The location of the first card on the board           
*/        
        public int getCardOne()
        {
          return cardOne;
        }



/**
  * Gets the second card from the server           
  * @return The location of the second card on the board            
*/        
         public int getCardTwo()
         {
          return cardTwo;
         }



/**
  * Resets all of the variables on the server.         
*/        
         public void resetServer()
         {
            numPlayers =0;
            cardOne = -1;
            cardTwo = -1;
            playerList.clear();
            boardSymbols.clear();
         }
}
