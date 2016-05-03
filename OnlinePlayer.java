/**********************************************************/
/*  Author:      Brendan Lilly                            */
/*  Course:      CSC421                                   */
/*  Professor:   Spiegel                                  */
/*  Assignment:  2                                        */
/*  Due Date:    3/22/2016                                */
/*  Start Date:  3/12/2016                                */
/*  Filename:    OnlinePlayer.java                        */
/*  Purpose:     OnlinePlayer class for the memory card game/
/*  Time:        Write: 5 Minutes                         */
/*               Debug: 0 Minutes                         */
/*               Test: 	5 Minutes                         */
/**********************************************************/


package com.client;

/**
	* This class contains all of the methods to interact with the 
	*  online player in the Memory Card game.   
	* @author Brendan Lilly
*/	
public class OnlinePlayer extends Player
{
	
/**                               
	* Default constructor for OnlinePlayer class   
	* @param name The player's name.                                  
*/	
	public OnlinePlayer(String name, String type){
	    //Assigns the String passed in to the playerName
	  super(name , type);
	}


}