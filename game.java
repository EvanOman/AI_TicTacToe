public class game
{
	private gameBoard board;
	private gameStatus status;
	private player[] players = new player[2];

	public game()
	{
		players[0] = new humanPlayer("Human", 'o');;
		players[1] = new computerPlayer("HAL", 'x');;
		board = new gameBoard(3);
		status = gameStatus.ONGOING;
	}

	public void playGame()
	{
		int turnMarker = 0;
		player currentPlayer = players[0];
		do
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
		} while (status == gameStatus.ONGOING);

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
		return board.hasWinningConfig();
	}

}