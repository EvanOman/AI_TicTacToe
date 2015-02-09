import java.util.Arrays;

public class gameBoard
{
	/* 
		Internal representation of the game board where indices 1-9 represent the spaces on the board
	*/
	public final int boardDim;
	public final int boardSize;
	public final char BLANKSPACE = '_';
	private char[] squares;
	private char[] playerChars;
	private int[][] winningRows;
	private int[][] winningDiag;

	/* Constructor, sets everything up and get the board ready for use */
	public gameBoard(int boardDimension, char[] playerCharacters)
	{
		boardDim = boardDimension;
		boardSize = boardDim * boardDim;
		squares = new char[boardSize + 1];
		Arrays.fill(squares, BLANKSPACE);
		playerChars = playerCharacters;
		winningRows = new int[boardDim][boardDim];
		winningRows = popWinningRowConfigs(winningRows);
		winningDiag = popWinningDiagConfigs();
	}

	/* Getter for the squares structure */
	public char[] getSquares()
	{
		return squares;
	}

	/* Prints the board to the console */
	public void printBoard()
	{
		System.out.print("\n\n\n");
		for (int i = 1; i <= boardSize; i++)
		{
			System.out.print(" " + squares[i] + " ");

			if (i % boardDim != 0)
			{
				System.out.print("|");
			}
			else if (i % boardSize != 0)
			{
				System.out.print("\n");
				int boardWidth = 4*boardDim - 1;
				for (int j = 0; j < boardWidth; j++)
				{
					System.out.print("-");
				}
				System.out.print("\n");
			}
		}
		System.out.print("\n\n\n\n");
	}

	/* Attempts to make a move on the board */
	public moveOutcome applyMarker(int position, char marker)
	{
		if (position < 1 || position > boardSize)
		{
			System.out.println("Please choose a position between 1 and 9.");
			return moveOutcome.OUTOFRANGE;
		}
		else if (squares[position] != BLANKSPACE)
		{
			return moveOutcome.SPACETAKEN;
		}
		else
		{
			squares[position] = marker;
			return moveOutcome.SUCCESS;
		}
	}

	/* Clears the given space on the board */
	private moveOutcome clearSpace(int position)
	{
		if (position < 1 || position > boardSize)
		{
			System.out.println("Please choose a position between 1 and 9.");
			return moveOutcome.OUTOFRANGE;
		}
		else
		{
			squares[position] = BLANKSPACE;
			return moveOutcome.SUCCESS;
		}
	}

	/* Checks if the current board has any winning combinations */
	public boolean hasWinningConfig()
	{
		return (checkLines(winningRows) || checkLines(transpose(winningRows)) || checkLines(winningDiag));
	}

	/* Checks the given lines for a winning configuration, returns true if one exists, false otherwise */
	private boolean checkLines(int[][] winningLines)
	{
		/*
			How this works is I have matrices called winningLines whose rows define
			an n-tuple of indices where if the game board contains the same, non-blank
			symbol in each index, then that symbol has a winning configuration
		*/
		for (int row = 0; row < winningLines.length; row++)
		{
			/* Gets the chars along the current winning configuration */
			char[] line = getSquareValsAtIndices(winningLines[row]);

			/* If this row contains any blanks, it cannot be a winning row */
			if (!containsChar(line, BLANKSPACE))
			{
				/* Now we check to see if the line contains all of the same char */
				boolean flag = true;
				int first = line[0];
				for(int i = 1; i < line.length && flag; i++)
				{
					if (line[i] != first)
					{
						flag = false;
					}
				}
				if (flag)
				{
					return true;
				}
			}
		}
		return false;
	}

	/* Returns an array containing chars found at the given indices */
	private char[] getSquareValsAtIndices(int[] indices)
	{
		char[] outArr = new char[indices.length];
		for (int i = 0; i < indices.length; i++)
		{
			outArr[i] = squares[indices[i]];
		}
		return outArr;
	}

	/* Simple linear search method for a given char */
	private boolean containsChar(char[] array, char key)
	{
		for (int i = 0; i < array.length; i++)
		{
			if (array[i] == key)
			{
				return true;
			}
		}
		return false;
	}

	/* Creates a matrix whose rows are n-tuples defining a winning board config*/
	private int[][] popWinningRowConfigs(int[][] matrix)
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

	/* Defines two n-tuples defining the winning diagonal board configs*/
	private int[][] popWinningDiagConfigs()
	{
		int[][] matrix = new int[2][boardDim];
		/*
			There are two diagonals on every (odd) dimension board. These loops calculate the 
			indices that lie along the diagonal. The rules are:
				-Forward Diagonal: 1 + n(dim + 1) for 1 <= n <= dim - 1
				-Backward Diagonal: dim + n(dim - 2) for 1 <= n <= dim - 1
		*/
		for (int n = 0; n < boardDim; n++)
		{
			matrix[0][n] = 1 + n * (boardDim + 1);
			matrix[1][n] = boardDim + n * (boardDim - 1);
		}
		return matrix;
	}

	/* Utility function which takes the transpose of the given(square) matrix */
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

	/* Finds the space that player with marker playerChar can use to win */
	public int findWiningSpace(char playerChar)
	{
		/*
			The way this works is we simply apply this player's char to every open space
			to see if there is one move that gives this player the win. If we find one
			that is the index that we return
		*/
		for (int index = 1; index <= boardSize; index++)
		{
			if (applyMarker(index, playerChar) == moveOutcome.SUCCESS)
			{
				/* Find out if the application of that char won the game */
				boolean introducedWin = hasWinningConfig();

				/* Make sure that we undo the move before returning */
				clearSpace(index);

				/* If this is true, then the index is the winning space */
				if (introducedWin)
				{
					return index;
				}
			}
		}
		return -1;
	}

	/* Finds the number of winning spaces that are currently on the board */
	public int findNumWiningSpaces(char playerChar)
	{
		/*
			The way this works is we simply apply this player's char to every open space
			to see if there is one move that gives this player the win. If we find one
			that is the index that we return
		*/
		int numWinningSpaces = 0;
		for (int index = 1; index <= boardSize; index++)
		{
			if (applyMarker(index, playerChar) == moveOutcome.SUCCESS)
			{
				/* Find out if the application of that char won the game */
				boolean introducedWin = hasWinningConfig();

				/* Make sure that we undo the move before returning */
				clearSpace(index);

				/* If this is true, then the index is the winning space */
				if (introducedWin)
				{
					numWinningSpaces++;
				}
			}
		}
		return numWinningSpaces;
	}

	/* Finds the position of a square which blocks an opponent streak */
	public int findBlockingSpace(char playerChar)
	{
		char otherPlayerChar = findOtherPlayerChar(playerChar);
		return findWiningSpace(otherPlayerChar);
	}

	/* Finds a position which gives the player a fork */
	public int findForkingSpace(char playerChar)
	{
		/* Basically we just loop over all squares and if putting a mark in any square introduces two winning configs then we have a fork and we return the square's index */
		for (int index = 1; index <= boardSize; index++)
		{
			if (applyMarker(index, playerChar) == moveOutcome.SUCCESS)
			{
				/* Find out if the application of that char won the game */
				int numWinningSpaces = findNumWiningSpaces(playerChar);

				/* Make sure that we undo the move before returning */
				clearSpace(index);

				/* If this is true, then the index is the winning space */
				if (numWinningSpaces > 1)
				{
					return index;
				}
			}
		}
		return -1;
	}

	/* Finds the center index and returns it if open */
	public int findOpenCenterSpace()
	{
		double boardSizeDub = (double)boardSize;
		int centerIndex = (int)Math.ceil(boardSizeDub/2);
		if (squares[centerIndex] == BLANKSPACE)
		{
			return centerIndex;
		}
		else
		{
			return -1;
		}
	}

	/* Makes a list of corner spaces, returns one if open */
	public int findOpenCornerSpace()
	{
		/* We will alsways have 4 corners defined as such: */
		int[] corners = {1, boardDim, boardDim*boardDim - (boardDim - 1), boardDim*boardDim};
		for (int corner : corners)
		{
			if (squares[corner] == BLANKSPACE)
			{
				return corner;
			}
		}
		return -1;
	}

	/* Makes a list of side spaces, returns one if open */
	public int findEmptySideSpace()
	{
		/* TODO: Generalize this to more than 3x3 grid */
		int [] sides = {2, 4, 6, 8};
		for (int side : sides)
		{
			if (squares[side] == BLANKSPACE)
			{
				return side;
			}
		}
		return -1;
	}

	/* Given a player's char, this method finds the other player's char */
	private char findOtherPlayerChar(char playerChar)
	{
		/* Loops through the playerChars array and returns the one that isn't passed in */
		for (int i = 0; i < playerChars.length; i++)
		{
			if (playerChars[i] != playerChar)
			{
				return playerChars[i];
			}
		}
		/* If we cant find it, return the null char */
		return '\u0000';
	}
}
