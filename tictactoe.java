import java.util.Arrays;

public class tictactoe
{
	public static void main(String[] args)
	{
		gameBoard board = new gameBoard();
		board.printBoard();
	}
}

enum moveOutcome {
	SUCCESS, OUTOFRANGE, SPACETAKEN
}

class game
{
	private humanPlayer human;
	private computerPlayer computer;
	private gameBoard board;
	private moveOutcome outcome;
	private int numMoves = 0;
	private boolean gameOver;

	public game()
	{
		human = new humanPlayer("Jeff", 'o');
		computer = new computerPlayer("HAL", 'x');
		board = new gameBoard();
	}

	public void playGame()
	{
		//while (!)
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

		for (int i = 0; i < squares.length; i++)
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
	public humanPlayer(String name, char character)
	{
		super(name, character);
	}

	/* Prompts the user for a move */
	public int makeMove(gameBoard board)
	{
		char[] squares = board.getSquares();

		for (int i = 0; i < squares.length; i++)
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

class computerPlayer extends player
{
	public computerPlayer(String name, char character)
	{
		super(name, character);
	}

	/* Reads the game board and follows the prescribed strategy */
	public int makeMove(gameBoard board)
	{
		char[] squares = board.getSquares();

		for (int i = 0; i < squares.length; i++)
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
	private moveOutcome outcome;
	


	public gameBoard()
	{
		boardSize = 9;
		squares = new char[boardSize + 1];
		Arrays.fill(squares, '_');
	}

	/* Returns a list of open squares */
	public char[] getSquares()
	{
		return squares;
	}

	public void printBoard()
	{
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
		System.out.println();
	}

	/* Attempts to make a move on the board */
	public moveOutcome applyMarker(int position, char marker)
	{
		if (position < 1 || position > 9)
		{
			return outcome.OUTOFRANGE;
		}
		else if (squares[position] != '_')
		{
			return outcome.SPACETAKEN;
		}
		else
		{
			squares[position] = marker;
			return outcome.SUCCESS;
		}
	}


}
