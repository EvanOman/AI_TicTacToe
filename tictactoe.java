import java.util.Arrays;

public class tictactoe
{
	public static void main(String[] args)
	{
		gameBoard board = new gameBoard();
		board.printBoard();
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
	public char[] squares;

	public gameBoard()
	{
		boardSize = 9;
		squares = new char[boardSize + 1];
		Arrays.fill(squares, ' ');
		squares[1] = 'o';
		squares[5] = 'o';
		squares[9] = 'o';
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
				System.out.print("\n---------\n");
			}
		}
	}
}

//class employee extends person
//{
//	private int EmployeeNumber;
//	public employee(String surname, int age, boolean ismale, int employeenumber)
//	{
//		super(surname, age, ismale);
//		EmployeeNumber = employeenumber;
//	}
//
//	@Override public String toString()
//	{
//		return Surname + " is a " + Age + " year old employee with Employee Number: " + EmployeeNumber;
//	}
//}