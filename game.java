public class game
{
	private gameBoard board;
	private gameStatus status;
	private player[] players = new player[2];

	public game()
	{
		players[0] = new humanPlayer("Human", 'o');;
		players[1] = new computerPlayer("HAL", 'x', "simple");
		char [] playerChars = {'o', 'x'};
		board = new gameBoard(3, playerChars);
		status = gameStatus.ONGOING;
	}

	/* Houses the game loop, simply alternates players until game ends */
	public void playGame()
	{
		int turnMarker = 0;
		player currentPlayer = players[0];

		/* While the game is still running... */
		while (status == gameStatus.ONGOING)
		{
			/* Orchestrates the player alternation mentioned above */
			currentPlayer = players[turnMarker % 2];
			moveOutcome outcome = moveOutcome.UNSUCCESS;

			/* Inner feedback loop which runs until this player successfully ends his/her/its turn */
			while(outcome != moveOutcome.SUCCESS)
			{
				/* Gets the players movement choice */
				int chosenIndex = currentPlayer.makeMove(board);
				System.out.println(currentPlayer.playerName + " chooses " + chosenIndex);

				/* Attempts to make move */
				outcome = board.applyMarker(chosenIndex, currentPlayer.playerCharacter);

				/* Informs the user of undesirable outcomes which require further action */
				if (outcome == moveOutcome.OUTOFRANGE)
				{
					System.out.println("Please choose an index that is in range");
				}
				else if (outcome == moveOutcome.SPACETAKEN)
				{
					System.out.println("The space at square " + chosenIndex + " is taken.");
				}
			}
			/* Prints the board after every turn */
			board.printBoard();

			/* Updates the game status every turn */
			updateGameStatus();
			turnMarker++;
		}

		/* Broke out of the game loop so the game must have ended so we need to inform the players of the outcome */
		if (status == gameStatus.CATGAME)
		{
			System.out.println("CAT GAME!");
		}
		else if (status == gameStatus.HASWINNER)
		{
			System.out.println(currentPlayer.playerName + " wins the game!");
		}
		else
		{
			System.out.println("Game somehow ended without a winner and not in a cat game.");
		}
	}

	/* Updates the global status of the game */
	public void updateGameStatus()
	{
		if (isWon())
		{
			status = gameStatus.HASWINNER;
		}
		else if (isCat())
		{
			status = gameStatus.CATGAME;
		}
	}

	/* Simply says that the game is a cat if the board is full and nobody won yet */
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
		return board.hasWinningConfig();
	}

	private boolean isWon()
	{
		return board.hasWinningConfig();
	}

}