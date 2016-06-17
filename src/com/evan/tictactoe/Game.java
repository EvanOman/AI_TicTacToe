package com.evan.tictactoe;

public class Game
{
	private GameBoard board;
	private GameStatus status;
	private Player[] players = new Player[2];

	public Game()
	{
		players[0] = new HumanPlayer("Human", 'o');;
		players[1] = new ComputerPlayer("HAL", 'x', "simple");
		char [] playerChars = {'o', 'x'};
		board = new GameBoard(3, playerChars);
		status = GameStatus.ONGOING;
	}

	/* Houses the com.evan.TicTacToe.Game loop, simply alternates players until com.evan.TicTacToe.Game ends */
	public void playGame()
	{
		int turnMarker = 0;
		Player currentPlayer = players[0];

		/* While the com.evan.TicTacToe.Game is still running... */
		while (status == GameStatus.ONGOING)
		{
			/* Orchestrates the com.evan.TicTacToe.Player alternation mentioned above */
			currentPlayer = players[turnMarker % 2];
			MoveOutcome outcome = MoveOutcome.UNSUCCESS;

			/* Inner feedback loop which runs until this com.evan.TicTacToe.Player successfully ends his/her/its turn */
			while(outcome != MoveOutcome.SUCCESS)
			{
				/* Gets the players movement choice */
				int chosenIndex = currentPlayer.makeMove(board);
				System.out.println(currentPlayer.playerName + " chooses " + chosenIndex);

				/* Attempts to make move */
				outcome = board.applyMarker(chosenIndex, currentPlayer.playerCharacter);

				/* Informs the user of undesirable outcomes which require further action */
				if (outcome == MoveOutcome.OUTOFRANGE)
				{
					System.out.println("Please choose an index that is in range");
				}
				else if (outcome == MoveOutcome.SPACETAKEN)
				{
					System.out.println("The space at square " + chosenIndex + " is taken.");
				}
			}
			/* Prints the board after every turn */
			board.printBoard();

			/* Updates the com.evan.TicTacToe.Game status every turn */

			updateGameStatus();
			turnMarker++;
		}

		/* Broke out of the com.evan.TicTacToe.Game loop so the com.evan.TicTacToe.Game must have ended so we need to inform the players of the outcome */
		if (status == GameStatus.CATGAME)
		{
			System.out.println("CAT GAME!");
		}
		else if (status == GameStatus.HASWINNER)
		{
			System.out.println(currentPlayer.playerName + " wins the com.evan.TicTacToe.Game!");
		}
		else
		{
			System.out.println("Game somehow ended without a winner and not in a cat com.evan.TicTacToe.Game.");
		}
	}

	/* Updates the global status of the com.evan.TicTacToe.Game */
	public void updateGameStatus()
	{
		if (isWon())
		{
			status = GameStatus.HASWINNER;
		}
		else if (isCat())
		{
			status = GameStatus.CATGAME;
		}
	}

	/* Simply says that the com.evan.TicTacToe.Game is a cat if the board is full and nobody won yet */
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
		return !board.hasWinningConfig();
	}

	private boolean isWon()
	{
		return board.hasWinningConfig();
	}

}