import java.util.Arrays;

public class gameBoard
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
	public final int boardDim;
	public final int boardSize;
	private char[] squares;
	private int[][] winningRows;
	private int[][] winningDiag;

	public gameBoard(int boardDimension)
	{
		boardDim = boardDimension;
		boardSize = boardDim * boardDim;
		squares = new char[boardSize + 1];
		Arrays.fill(squares, '_');
		winningRows = new int[boardDim][boardDim];
		winningRows = popWinningRowConfigs(winningRows);
		winningDiag = popWinningDiagConfigs();
		showMatrix(winningDiag);
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

	public boolean hasWinningConfig()
	{
		return (/*checkDiag() ||*/ checkNonDiag());
	}

	/* Loops over the rows/cols looking for a row with the same char */
	private boolean checkNonDiag()
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
			if (!containsChar(line, '_'))
			{
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

	private char[] getSquareValsAtIndices(int[] indices)
	{
		char[] outArr = new char[indices.length];
		for (int i = 0; i < indices.length; i++)
		{
			outArr[i] = squares[indices[i]];
		}
		return outArr;
	}

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

	/* Loops over the columns looking for */
	private boolean checkDiag()
	{
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
