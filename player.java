import java.util.Scanner;

public class player
{
	public String playerName;
	public char playerCharacter;

	public player(String name, char character)
	{
		playerName = name;
		playerCharacter = character;
	}

	/* A default(bad) strategy that any player can play */
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

	public int makeMove(gameBoard board)
	{
		return super.makeMove(board);
	}
}