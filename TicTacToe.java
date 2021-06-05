package com.problemstatement;

import java.util.Scanner;

public class TicTacToe {
	static final int HEAD = 1, TAIL = 0;
	static Scanner sc = new Scanner(System.in);
	static char playerLetter, computerLetter;
	static String lastPlayed;

	/* UC1 -- Creating a Board */
	public static char[] createBoard() {
		char[] board = new char[10];
		for (int i = 1; i < board.length; i++) {
			board[i] = ' ';
		}
		return board;
	}

	/* UC2 -- Taking Input */
	public static char takeInput() {
		System.out.println("Enter the letter you wish to take: ('X' / 'O'): ");
		char input = Character.toUpperCase(sc.next().charAt(0));
		if (input == 'X' || input == 'O') {
			setLetter(input);
			return input;
		} else {
			System.out.println("Invalid Character. Try Again.");
			return takeInput();
		}
	}

	/* UC2 -- Determine letter for player and computer */
	public static void setLetter(char playerLetter) {
		computerLetter = (playerLetter == 'X') ? 'O' : 'X';
		System.out.println("Player Letter: " + playerLetter + "\nComputer Letter : " + computerLetter);
	}

	/* UC3 -- Display the Board */
	public static void displayBoard(char[] board) {
		System.out.println("\n\t " + board[1] + " | " + board[2] + " | " + board[3] + "\n\t-----------");
		System.out.println("\t " + board[4] + " | " + board[5] + " | " + board[6] + "\n\t-----------");
		System.out.println("\t " + board[7] + " | " + board[8] + " | " + board[9] + "\n\t");
	}

	private static boolean isEmpty(char[] board) {
		for (char cell : board) {
			if (cell == ' ')
				return true;
		}
		return false;
	}

	/* UC4 -- Player Movement */
	/* UC5 -- Player places letter */
	public static void movePlayer(char[] board) {
		System.out.print("Enter the index you want to move to: ");
		int index = sc.nextInt();
		while (index < 1 || index > 9) {
			System.out.print("Wrong Input. Try Again : ");
			index = sc.nextInt();
		}
		if (board[index] == ' ') {
			board[index] = playerLetter;
			displayBoard(board);
		} else {
			System.out.println("Index not available. Choose another");
			movePlayer(board);
		}
		lastPlayed = "Player";
		if (checkWin(board)) { // UC12 -- Player wins
			System.out.println("Player Won The Game !! \nDo You Want to Play Another Game (Y/N) : ");
			if (Character.toUpperCase(sc.next().charAt(0)) == 'Y') 
				startGame();
			else
				System.exit(0);
		}
		if (isEmpty(board)) {
			moveComputer(board);
		} else {
			System.out.println("Game Tied. \nDo You Want to Play Another Game (Y/N) : "); 
			if (Character.toUpperCase(sc.next().charAt(0)) == 'Y') 
				startGame();
			else
				System.exit(0);
		}
		return;
	}

	/*
	 * UC8 --check if computer can win
	 */
	public static void moveComputer(char[] board) {
		int checkCompWinPos = checkIsWinning(board, computerLetter);
		int checkPlayWinPos = checkIsWinning(board, playerLetter);

		if (checkCompWinPos != 0) {
			board[checkCompWinPos] = computerLetter;
			displayBoard(board);
			System.out.println("Computer Won The Game !! \nDo You Want to Play Another Game (Y/N) : ");
			if (Character.toUpperCase(sc.next().charAt(0)) == 'Y') 
				startGame();
			else
				System.exit(0);
		} // UC8
		else if (checkPlayWinPos != 0) 
			board[checkPlayWinPos] = computerLetter;
		else {
			if (board[1] == ' ') 
				board[1] = computerLetter;
			else if (board[3] == ' ')
				board[3] = computerLetter;
			else if (board[7] == ' ')
				board[7] = computerLetter;
			else if (board[9] == ' ')
				board[9] = computerLetter;
			else if (board[5] == ' ')
				board[5] = computerLetter; 
			else if (board[2] == ' ')
				board[2] = computerLetter;
			else if (board[4] == ' ')
				board[4] = computerLetter;
			else if (board[6] == ' ')
				board[6] = computerLetter;
			else if (board[8] == ' ')
				board[8] = computerLetter;
		}
		displayBoard(board);
		lastPlayed = "Computer";
		if (checkWin(board)) { 
			System.out.println("Computer Won The Game !! \nDo You Want to Play Another Game (Y/N) : ");
			if (Character.toUpperCase(sc.next().charAt(0)) == 'Y') 
				startGame();
			else
				System.exit(0);
		}
		if (isEmpty(board)) {
			movePlayer(board);
		} else {
			System.out.println("Game Tied."); 
			System.exit(0);
		}
	}

	private static int checkIsWinning(char[] board, char letter) {
		int index = 1;
		while (index > 0 && index < 10) {
			if (board[index] == ' ') {
				board[index] = letter;
				if (checkWin(board)) {
					return index;
				} else {
					board[index] = ' ';
				}
			}
			index++;
		}
		return 0;
	}

	/* UC6 -- Randomly decide who plays first */
	public static void firstMove(char[] board) {
		int toss = (int) (Math.random() * 2 % 2);
		if (toss == HEAD) {
			System.out.println("Player Wins the Toss.");
			lastPlayed = "Player";
			movePlayer(board);
		} else {
			System.out.println("Computer Wins the Toss.");
			lastPlayed = "Computer";
			moveComputer(board);
		}
	}

	/* UC7 -- Winner, Tie or next turn */
	public static boolean outcome(char[] board) {
		if (checkWin(board))
			return true;
		else if (areMovesLeft(board)) {
			if (lastPlayed.equals("Computer"))
				movePlayer(board);
			else
				moveComputer(board);
			return false;
		} else {
			System.out.println("Game Tied.");
			return false;
		}
	}

	private static boolean checkWin(char[] board) {
		return ((board[1] == board[2] && board[2] == board[3] && board[1] != ' ') // top-row
				|| (board[4] == board[5] && board[5] == board[6] && board[4] != ' ') 
				|| (board[7] == board[8] && board[8] == board[9] && board[7] != ' ') 
				|| (board[1] == board[4] && board[4] == board[7] && board[1] != ' ') 
				|| (board[2] == board[5] && board[5] == board[8] && board[2] != ' ') 
				|| (board[3] == board[6] && board[6] == board[9] && board[3] != ' ') 
				|| (board[1] == board[5] && board[5] == board[9] && board[1] != ' ') 
				|| (board[3] == board[5] && board[5] == board[7] && board[3] != ' ')); 
	}

	private static boolean areMovesLeft(char[] board) {
		for (int pos = 1; pos < board.length; pos++) {
			if (pos == ' ')
				return true;
		}
		return false;
	}

	private static void startGame() {
		char[] board = createBoard();
		playerLetter = takeInput();
		displayBoard(board);
		firstMove(board);
	}

	public static void main(String[] args) {
		System.out.println("Welcome to the TicTacToe Game");
		startGame();
		sc.close();
	
	}

}

