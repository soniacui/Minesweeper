import java.io.*;
import java.util.*;
import java.text.*;
import java.lang.*;

/*
Minesweeper

Author: Sonia Cui
Date: July 23, 2018

Input: The user enters coordinates via keyboard (e.g. 'E5'), or 'X' if they wish to mark a spot as a mine.
Output: Upon receiving a coordinate, the program produces an updated board that reveals surrounding safe spaces, or an 'X' in the desired position. If the user enters a coordinate
	that was a mine, it displays 'Game over'.

*/

public class Minesweeper
{
    public static void initializeUpperBoard (String[] [] upperBoard)  //initialize upperboard
    {
	for (int j = 0 ; j < 10 ; j++) //y
	{
	    for (int i = 0 ; i < 10 ; i++) //x
	    {
		upperBoard [i] [j] = (" ");
	    }
	}
    }

    public static void initializeUnderBoard (String[] [] underBoard)  //assign mines
    {

	Random rand = new Random ();
	int count = 0;
	int a = 0;
	int b = 0;

	//initialize whole board to 0 (safe)
	for (int j = 0 ; j < 10 ; j++) //y
	{
	    for (int i = 0 ; i < 10 ; i++) //x
	    {
		underBoard [i] [j] = "0";
		// System.out.print (underBoard [i] [j]);
	    }
	    // System.out.println ();
	}

	//assign mines: 0 = safe, 1 = mine
	while (count < 10)
	{
	    a = rand.nextInt (10);
	    b = rand.nextInt (10);
	    if (!underBoard [a] [b].equals ("1")) //only assign if space is not already a mine
	    {
		underBoard [a] [b] = "1";
		count++;
	    }
	}
	// //print board for testing
	// for (int j = 0 ; j < 10 ; j++) //y
	// {
	//     for (int i = 0 ; i < 10 ; i++) //x
	//     {
	//         // underBoard [i] [j] = "0";
	//         System.out.print (underBoard [i] [j]);
	//     }
	//     System.out.println ();
	// }

    }

    public static void displayBoard (String[] [] upperBoard)  //(display upper board)
    {
	//creating the board
	System.out.println ();
	System.out.print ("\t   A ");
	System.out.print ("B ");
	System.out.print ("C ");
	System.out.print ("D ");
	System.out.print ("E ");
	System.out.print ("F ");
	System.out.print ("G ");
	System.out.print ("H ");
	System.out.print ("I ");
	System.out.print ("J ");
	System.out.println ();

	for (int j = 0 ; j < 10 ; j++) //y
	{
	    System.out.println ("\t  ---------------------");
	    if (j == 10) //special case for alignment bc 10 is two digits
		System.out.print ("" + (j));
	    else
		System.out.print ("\t " + (j));

	    for (int i = 0 ; i < 10 ; i++) //x
	    {

		System.out.print ("|" + upperBoard [i] [j]);

	    }
	    System.out.println ();
	}
    }


    public static boolean didTheyWin (String[] [] upperBoard, boolean win)
    {
	int count = 0;

	for (int j = 0 ; j < 10 ; j++)
	{
	    for (int i = 0 ; i < 10 ; i++)
	    {
		if (upperBoard [i] [j].equals (" "))
		{
		    count++;
		}
	    }
	}
	if (count == 10)
	{
	    win = true;
	    System.out.println ("///WINNER WINNER CHICKEN DINNER!!! CONGRATULATIONS C: GOOD JOB FELLER!!!!///");
	}
	return win;

    }

    // public static void markBomb (int x, int y, String[] [] upperBoard)
    // {
    //     upperBoard [x] [y] = "X";
    // }


    public static int checkSurroundings (int x, int y, String[] [] underBoard, String[] [] upperBoard)
    {
	int count = 0;
	boolean checkUpper = true;
	boolean checkRight = true;
	boolean checkBelow = true;
	boolean checkLeft = true;

	// System.out.println ("x:" + x);
	// System.out.println ("y:" + y);

	// if (y == 0)
	//     checkUpper = false;
	// if (x == 9)
	//     checkRight = false;
	// if (y == 9)
	//     checkBelow = false;
	// if (x == 0)
	//     checkLeft = false;


	if (y > 0)
	{
	    if (underBoard [x] [y - 1].equals ("1")) //up
		count++;
	}


	if (y > 0 && x < 9)
	{
	    if (underBoard [x + 1] [y - 1].equals ("1"))  //diagonal up to the right
		count++;
	}


	if (x < 9)
	{
	    if (underBoard [x + 1] [y].equals ("1"))  //right
		count++;
	}


	if (y < 9 && x < 9)
	{
	    if (underBoard [x + 1] [y + 1].equals ("1")) //diagonal down to the right
		count++;
	}


	if (y < 9)
	{
	    if (underBoard [x] [y + 1].equals ("1")) //down
		count++;
	}


	if (y < 9 && x > 0)
	{
	    if (underBoard [x - 1] [y + 1].equals ("1")) //diagonal down to the left
		count++;
	}


	if (x > 0)
	{
	    if (underBoard [x - 1] [y].equals ("1")) //left
		count++;
	}


	if (y > 0 && x > 0)
	{
	    if (underBoard [x - 1] [y - 1].equals ("1")) //diagonal up to the left
		count++;
	}


	// System.out.println (count);

	return count;
    }


    public static void main (String[] args) throws IOException
    {
	BufferedReader stdin = new BufferedReader (new InputStreamReader (System.in));

	String input;
	String[] parts = new String [2];
	String[] [] underBoard = new String [10] [10]; //array that holds the position of mines
	String[] [] upperBoard = new String [10] [10]; //board displayed to the user
	int x = 0;
	int y = 0;
	int count = 0;
	boolean dead = false;
	boolean win = false;
	int tempUp = 0;
	int tempDown = 0;
	int tempRight = 0;
	int tempLeft = 0;


	//instructions
	System.out.println ("Welcome to Minesweeper!");
	System.out.println ("Enter a coordinate and it will reveal the number of mines adjacent to it.");
	System.out.println ("There are 10 mines in total. Identify all the safe spaces to win!");


	initializeUnderBoard (underBoard); //assign mines
	initializeUpperBoard (upperBoard); //set all spaces to blank
	displayBoard (upperBoard); //display board


	while (dead == false && win == false)
	{
	    //input
	    System.out.print ("Enter a coordinate (e.g. E5) or enter 'X' to mark a bomb: ");
	    input = stdin.readLine ();
	     if (input.equals ("X"))
	     {
		 System.out.println ("Enter the coordinate you wish to mark (e.g. C4): ");
		 input = stdin.readLine ();
	     }
	    parts = input.split ("");
	    String xi = parts [1];
	    String yi = parts [2];

	    // System.out.println (xi);
	    // System.out.println (yi);

	    if (xi.equals ("A"))
		x = 0;
	    else if (xi.equals ("B"))
		x = 1;
	    else if (xi.equals ("C"))
		x = 2;
	    else if (xi.equals ("D"))
		x = 3;
	    else if (xi.equals ("E"))
		x = 4;
	    else if (xi.equals ("F"))
		x = 5;
	    else if (xi.equals ("G"))
		x = 6;
	    else if (xi.equals ("H"))
		x = 7;
	    else if (xi.equals ("I"))
		x = 8;
	    else if (xi.equals ("J"))
		x = 9;
	    y = (Integer.parseInt (yi));


	    // System.out.println (x);
	    // System.out.println (y);

	    //if coord was a mine
	    if (underBoard [x] [y].equals ("1"))
	    {
		System.out.println ("Kaboom! Game over."); //fail message
		upperBoard [x] [y] = "M";
		dead = true;
	    }
	    //if coord was not a mine
	    else if (underBoard [x] [y].equals ("0"))
	    {
		count = checkSurroundings (x, y, underBoard, upperBoard);

		if (count != 0)
		{
		    upperBoard [x] [y] = Integer.toString (count);
		}

		else if (count == 0)
		{
		    upperBoard [x] [y] = "0";

		    tempUp = y;
		    while (count == 0 && tempUp > 0) //finding the upper bound
		    {
			tempUp = tempUp - 1;
			count = checkSurroundings (x, tempUp, underBoard, upperBoard);
			upperBoard [x] [tempUp] = Integer.toString (count);

		    }
		    // System.out.println ("tempUp: " + tempUp);

		    tempDown = y;
		    count = 0; //reset count
		    while (count == 0 && tempDown < 9)
		    {
			tempDown = tempDown + 1;
			count = checkSurroundings (x, tempDown, underBoard, upperBoard);
			upperBoard [x] [tempDown] = Integer.toString (count);
		    }
		    // System.out.println ("tempDown: " + tempDown);

		    tempRight = x;
		    tempLeft = x;
		    for (int i = tempDown ; i > tempUp ; i--) //moving up
		    {
			count = 0; //reset count
			while (count == 0 && tempRight < 9) //check right
			{
			    tempRight = tempRight + 1;
			    count = checkSurroundings (tempRight, i, underBoard, upperBoard);
			    upperBoard [tempRight] [i] = Integer.toString (count);
			    // System.out.println ("Right loop: x: " + tempRight + " y: " + i);
			    // System.out.println ("count inside Right loop:" + count);

			}
			tempRight = x; //reset tempRight

			count = 0; // reset count
			while (count == 0 && tempLeft > 0) //check left
			{
			    tempLeft = tempLeft - 1;
			    count = checkSurroundings (tempLeft, i, underBoard, upperBoard);
			    upperBoard [tempLeft] [i] = Integer.toString (count);
			    // System.out.println ("Left loop: x: " + tempLeft + " y: " + i);
			    // System.out.println ("count inside Left loop:" + count);

			}
			tempLeft = x; //reset tempLeft
		    }

		    // test (x, y, underBoard, upperBoard, count);
		    // displayBoard (upperBoard);
		}
	    }
	    displayBoard (upperBoard);
	    didTheyWin (upperBoard, win);
	}
    }
}
