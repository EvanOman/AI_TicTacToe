import java.util.Arrays;
import java.util.Scanner;

public class tictactoe
{
	public static void main(String[] args)
	{
		game tictactoeGame = new game();
		tictactoeGame.playGame();
	}
}

class game
{
	private gameBoard board;
	private gameStatus status;
	private player[] players = new player[2];

	public game()
	{
		humanPlayer human = new humanPlayer("Human", 'o');
		computerPlayer computer = new computerPlayer("HAL", 'x');
		players[0] = human;
		players[1] = computer;
		board = new gameBoard();
		status = gameStatus.ONGOING;
	}

	public void playGame()
	{
		int turnMarker = 0;
		player currentPlayer = players[0];
		while (status == gameStatus.ONGOING)
		{
			currentPlayer = players[turnMarker % 2];
			moveOutcome outcome = moveOutcome.UNSUCCESS;
			while(outcome != moveOutcome.SUCCESS)
			{
				int chosenIndex = currentPlayer.makeMove(board);
				System.out.println(currentPlayer.playerName + " chooses " + chosenIndex);
				outcome = board.applyMarker(chosenIndex, currentPlayer.playerCharacter);
			}
			board.printBoard();
			updateGameStatus();
			turnMarker++;
		}

		if (status == gameStatus.CATGAME)
		{
			System.out.println("CAT GAME!");
		}
		else if (status == gameStatus.HASWINNER)
		{
			System.out.println(currentPlayer.playerName + "wins the game!");
		}
		else
		{
			System.out.println("Game somehow ended without a winner and not in a cat game.");
		}
	}

	public void updateGameStatus()
	{
		if (isCat())
		{
			status = gameStatus.CATGAME;
		}
		else if (isWon())
		{
			status = gameStatus.HASWINNER;
		}
	}

	private boolean isCat()
	{
		char[] squares = board.getSquares();
		for (int i = 1; i < squares.length; i++)
		{
			if (squares[i] == '_')
			{
				return false;
			}
		}
		return true;
	}

	private boolean isWon()
	{
		return false;
	}

}

class player
{
	public String playerName;
	public char playerCharacter;

	public player(String name, char character)
	{
		playerName = name;
		playerCharacter = character;
	}

	public int makeMove(gameBoard board)
	{
		char[] squares = board.getSquares();

		for (int i = 1; i < squares.length; i++)
		{
			if (squares[i] == '_')
			{
				return i;
			}
		}
		/* Default failure case, shouldn't ever happen */
		return -1;
	}
}

class humanPlayer extends player
{
	private Scanner keyboard = new Scanner(System.in);
	public humanPlayer(String name, char character)
	{
		super(name, character);
	}

	/* Prompts the user for a move */
	public int makeMove(gameBoard board)
	{
		System.out.print("Please choose a square: ");
		return keyboard.nextInt();
	}
}

class computerPlayer extends player
{
	public computerPlayer(String name, char character)
	{
		super(name, character);
	}

	/* Reads the game board and follows the prescribed strategy */
	//public int makeMove(gameBoard board)
	//{
	//	char[] squares = board.getSquares();
//
	//	for (int i = 0; i < squares.length; i++)
	//	{
	//		if (squares[i] == '_')
	//		{
	//			return i;
	//		}
	//	}
	//	/* Default failure case, shouldn't ever happen */
	//	return -1;
	//}
}

class gameBoard
{
	/*
		1) You want to draw the board. Have a DrawBoard class (or function). 
		2) You have players. Have a Player class. 
		3) Players take turns. Have a GamePlay class. 
		4) You need to keep track which moves have been made. Have a Game class. 
		5) You need to decide if somebody won. Have a GameEvaluate class.
	*/

	/* 
		Internal representation of the game board where indices 1-9 represent the spaces on the board
	*/
	public final int boardSize;
	private char[] squares;
	private int[][] winningRows = new int[3][3];

	//{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};


	public gameBoard()
	{
		boardSize = 9;
		squares = new char[boardSize + 1];
		Arrays.fill(squares, '_');
		winningRows = populateWinningConfigs(winningRows);
	}

	/* Returns a list of open squares */
	public char[] getSquares()
	{
		return squares;
	}

	public void printBoard()
	{
		System.out.print("\n\n\n");
		for (int i = 1; i <= boardSize; i++)
		{
			System.out.print(" " + squares[i] + " ");

			if (i % 3 != 0)
			{
				System.out.print("|");
			}
			else if (i % 9 != 0)
			{
				System.out.print("\n-----------\n");
			}
		}
		System.out.print("\n\n\n\n");
	}

	/* Attempts to make a move on the board */
	public moveOutcome applyMarker(int position, char marker)
	{
		if (position < 1 || position > 9)
		{
			System.out.println("Please choose a position between 1 and 9.");
			return moveOutcome.OUTOFRANGE;
		}
		else if (squares[position] != '_')
		{
			System.out.println("The square " + position + " is taken.");
			return moveOutcome.SPACETAKEN;
		}
		else
		{
			squares[position] = marker;
			return moveOutcome.SUCCESS;
		}
	}

	private int[][] populateWinningConfigs(int[][] matrix)
	{
		int value = 1;
		for (int row = 0; row < matrix.length; row++)
		{
			for (int col = 0; col < matrix[row].length; col++)
			{
				matrix[row][col] = value++;
			}
		}
		return matrix;
	}

	private int[][] transpose(int[][] matrix)
	{
		for (int i = 0; i < matrix.length; i++)
		{
			for (int j = 0; j < i; j++)
			{
				int temp = matrix[i][j];
				matrix[i][j] = matrix[j][i];
				matrix[j][i] = temp;
			}
		}
		return matrix;
	}

	public void showMatrix(int[][] matrix)
	{
		for (int i = 0; i < matrix.length; i++)
		{
			for (int j = 0; j < matrix[i].length; j++)
			{
				System.out.print(matrix[i][j] + "\t");
			}
			System.out.println();
		}
	}


}
