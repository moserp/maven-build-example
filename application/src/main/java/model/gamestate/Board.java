package model.gamestate;

import java.util.ArrayList;

public class Board {
	public static class AccessException extends RuntimeException {

		private static final long serialVersionUID = 8827142892632715315L;
		private final int column;
		private final int row;
		private Offender offender;

		public enum Offender {
			COLUMN, BOTH, ROW
		}

		public AccessException(int row, int column) {
			this.row = row;
			this.column = column;
			this.offender = Offender.ROW;
			boolean columnOffender = column < MIN_COLUMN_INDEX
					|| column > MAX_COLUMN_INDEX;
			boolean rowOffender = row < MIN_COLUMN_INDEX;
			if (columnOffender) {
				offender = Offender.COLUMN;
				if (rowOffender)
					offender = Offender.BOTH;
			}
		}

		public Offender offending() {
			return offender;
		}

		public int row() {
			return row;
		}

		public int column() {
			return column;
		}
	}

	private int[][] boardArray;
	private int tempBoardArrayForDimensionConversions[][];

	public static final int COMPUTER_PLAYER_MARK = 1;
	public static final int HUMAN_PLAYER_MARK = 2;

	public static final int STARTING_POSITION = 0;
	public static final int MAX_COLUMNS = 10;
	public static final int MAX_ROWS = 10;

	public static final int MAX_BOARD_SIZE = MAX_COLUMNS * MAX_ROWS;

	public static final int EMPTY = 0;

	public static final int MID_BOARD_LOWER_BOUND = 44;
	public static final int MID_BOARD_UPPER_BOUND = 55;

	public static final Object MAX_DIAGONAL_DOWNS_WITH_PENTA_ROOM = 11;
	public static final int PENTE_SIZE = 5;
	public static final int TESSERA_SIZE = 4;
	public static final int MAX_ROW_INDEX = MAX_ROWS - 1;
	public static final int MIN_ROW_INDEX = 0;
	public static final int MIN_COLUMN_INDEX = 0;
	public static final int MAX_COLUMN_INDEX = MAX_COLUMNS - 1;

	public enum SeriesSize {
		SIX(6), FIVE(5), FOUR(4), THREE(3), TWO(2), ONE(1);

		private int size;

		private SeriesSize(int size) {
			this.size = size;
		}

		public int getSize() {
			return size;
		}
	}

	public Board(int[] oneDimensionalBoardArrayToConvert) {
		this();
		this.boardArray = convertOneDimensionaltoTwoDimensionalBoardArray(oneDimensionalBoardArrayToConvert);
	}

	public Board() {
		boardArray = new int[MAX_ROWS][MAX_COLUMNS];
		tempBoardArrayForDimensionConversions = new int[MAX_ROWS][MAX_COLUMNS];
	}

	public static int getSingleCoordValueFor(int row, int column) {
		return row * Board.MAX_COLUMNS + column;
	}

	private int[][] convertOneDimensionaltoTwoDimensionalBoardArray(
			int[] oneDimensionalBoardArray) {
		if (oneDimensionalBoardArray.length != MAX_BOARD_SIZE) {
			throw new RuntimeException(
					"Incoming one-dimensional board array must be of length "
							+ MAX_BOARD_SIZE);
		}

		return convertAllRowsInBoard(oneDimensionalBoardArray);
	}

	private int[][] convertAllRowsInBoard(int[] oneDimensionalBoardArray) {
		int incomingBoardArrayIndex = 0;

		for (int row = 0; row < MAX_ROWS; row++) {
			incomingBoardArrayIndex = convertAllSquaresInRow(
					oneDimensionalBoardArray, incomingBoardArrayIndex, row);
		}

		return tempBoardArrayForDimensionConversions;
	}

	private int convertAllSquaresInRow(int[] oneDimensionalBoardArray,
			int incomingBoardArrayIndex, int row) {
		for (int column = 0; column < MAX_COLUMNS; column++) {
			tempBoardArrayForDimensionConversions[row][column] = oneDimensionalBoardArray[incomingBoardArrayIndex];
			incomingBoardArrayIndex++;
		}

		return incomingBoardArrayIndex;
	}

	public int getPosition(int row, int column) {
		try {
			return boardArray[row][column];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new AccessException(row, column);
		}
	}

	public void setPosition(int row, int column, int playerMark) {
		if (!within2DBoardPositionIndexBounds(row, column))
			throw new RuntimeException("Board index " + row + "," + column
					+ " is outside board bounds.");
		boardArray[row][column] = playerMark;
	}

	public int getPosition(int singleIndex) {
		if (!within1DBoardPositionIndexBounds(singleIndex))
			throw new RuntimeException("Board index " + singleIndex
					+ " is outside board bounds.");
		int row = singleIndex / Board.MAX_COLUMNS;
		int column = singleIndex % Board.MAX_COLUMNS;

		return boardArray[row][column];
	}

	public boolean isEmpty() {
		return allRowsInBoardAreEmpty();
	}

	private boolean allRowsInBoardAreEmpty() {
		for (int row = 0; row < MAX_ROWS; row++) {
			if (!allSquaresInRowAreEmpty(row))
				return false;
		}
		return true;
	}

	private boolean allSquaresInRowAreEmpty(int row) {
		for (int column = 0; column < MAX_COLUMNS; column++) {
			if (boardArray[row][column] != EMPTY)
				return false;
		}
		return true;
	}

	public int getOppositePlayerMarkFor(int thisPlayerMark) {
		return 3 - thisPlayerMark;
	}

	// TODO Use Point or Position object for row and column?
	public boolean positionIsEmpty(int trialPosition) {
		if (!within1DBoardPositionIndexBounds(trialPosition))
			throw new RuntimeException("Board index " + trialPosition
					+ " is outside board bounds.");
		int row = getRowCoordFor(trialPosition);
		int column = getColumnCoordFor(trialPosition);
		return boardArray[row][column] == EMPTY;
	}

	public boolean positionIsEmpty(int row, int column) {
		if (!within2DBoardPositionIndexBounds(row, column))
			throw new RuntimeException("Board position " + row + "," + column
					+ " is outside board bounds.");
		return boardArray[row][column] == EMPTY;
	}

	public ArrayList<Integer> getAllPositionsOccupiedByPlayer(
			int theirPlayerMark) {
		ArrayList<Integer> allPlayerPositions = new ArrayList<Integer>();

		for (int row = 0; row < MAX_ROWS; row++) {
			getAllOccupiedPositionsInRow(theirPlayerMark, allPlayerPositions,
					row);
		}
		return allPlayerPositions;
	}

	private void getAllOccupiedPositionsInRow(int theirPlayerMark,
			ArrayList<Integer> allPlayerPositions, int row) {
		for (int column = 0; column < MAX_COLUMNS; column++) {
			addOccupiedPosition(theirPlayerMark, allPlayerPositions, row,
					column);
		}
	}

	private void addOccupiedPosition(int theirPlayerMark,
			ArrayList<Integer> allPlayerPositions, int row, int column) {
		int position;
		if (boardArray[row][column] == theirPlayerMark) {
			position = (row * MAX_ROWS) + column;
			allPlayerPositions.add(position);
		}
	}

	public boolean within1DBoardPositionIndexBounds(int position) {
		return (position < Board.MAX_BOARD_SIZE) && (position > -1);
	}

	public boolean within2DBoardPositionIndexBounds(int row, int column) {
		if ((row < 0) || (row > Board.MAX_ROWS - 1))
			return false;
		if ((column < 0) || (column > Board.MAX_COLUMNS - 1))
			return false;

		return true;
	}

	public void setBoardArray(int[] incomingBoardArray) {
		boardArray = convertOneDimensionaltoTwoDimensionalBoardArray(incomingBoardArray);
	}

	public static int getColumnCoordFor(int position) {
		return position % MAX_COLUMNS;
	}

	public static int getRowCoordFor(int position) {
		return position / MAX_COLUMNS;
	}
}
