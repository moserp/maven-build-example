package model.patterns;

import model.gamestate.Board;

public class DirectionalBoardPositionsListFactory {
	private int winningSeriesSize;

	private int getFirstSquareOfLastLowerDiagonalUp() {
		return Board.MAX_COLUMNS * Board.MAX_ROWS - winningSeriesSize;
	}

	private int getFirstSquareOfFirstLowerDiagonalUp() {
		return (Board.MAX_ROWS * (Board.MAX_COLUMNS - 1)) + 1;
	}

	private int getFirstSquareOfFirstUpperDiagonalUp() {
		return Board.MAX_COLUMNS * (winningSeriesSize -1);
	}
	
	private int getFirstSquareOfFirstLowerDiagonalDown() {
		return (Board.MAX_ROWS - winningSeriesSize) * Board.MAX_COLUMNS;
	}
	
	public DirectionalBoardPositionsListFactory(int playerMark) {
		if (playerMark == Board.HUMAN_PLAYER_MARK)
			this.winningSeriesSize = 5;
		else if (playerMark == Board.COMPUTER_PLAYER_MARK)
			this.winningSeriesSize = 6;
		else
			throw new RuntimeException("Invalid playerMark: " + playerMark);
	}
	
	public static final int DIAGONAL_UP_AFTER_INCREMENT = -(Board.MAX_COLUMNS - 1);
	public static final int DIAGONAL_UP_BEFORE_INCREMENT = (Board.MAX_COLUMNS - 1);
	public static final int DIAGONAL_DOWN_AFTER_INCREMENT = (Board.MAX_COLUMNS + 1);
	public static final int DIAGONAL_DOWN_BEFORE_INCREMENT = -(Board.MAX_COLUMNS + 1);
	public static final int VERTICAL_AFTER_INCREMENT = Board.MAX_COLUMNS;
	public static final int VERTICAL_BEFORE_INCREMENT = -Board.MAX_COLUMNS;
	public static final int HORIZONTAL_AFTER_INCREMENT = 1;
	public static final int HORIZONTAL_BEFORE_INCREMENT = -1;

	public GroupOfDirectionalBoardPositionLists getAllIndexLists() {
		GroupOfDirectionalBoardPositionLists listGroup = new GroupOfDirectionalBoardPositionLists();

		listGroup = getIndexHorizontalRows(listGroup);
		listGroup = getIndexVerticalColumns(listGroup);
		listGroup = getIndexDiagonalDowns(listGroup);
		listGroup = getIndexDiagonalUps(listGroup);

		return listGroup;
	}

	public GroupOfDirectionalBoardPositionLists getIndexHorizontalRows(GroupOfDirectionalBoardPositionLists listGroup) {
		for (int row = 0; row < Board.MAX_ROWS; row++) {
			DirectionalBoardPositionsList list = new DirectionalBoardPositionsList();

			getAllSpacesInThisRow(row, list);
			listGroup.add(list);
		}
		return listGroup;
	}

	private void getAllSpacesInThisRow(int row, DirectionalBoardPositionsList list) {
		int spaceIndex;
		for (int space = 0; space < Board.MAX_COLUMNS; space++) {
			spaceIndex = row * Board.MAX_COLUMNS + space;

			list.add(spaceIndex);
		}
	}

	public GroupOfDirectionalBoardPositionLists getIndexVerticalColumns(GroupOfDirectionalBoardPositionLists listGroup) {
		for (int column = 0; column < Board.MAX_COLUMNS; column++) {
			DirectionalBoardPositionsList list = new DirectionalBoardPositionsList();

			getAllSpacesInThisColumn(column, list);
			listGroup.add(list);
		}

		return listGroup;
	}

	private void getAllSpacesInThisColumn(int column, DirectionalBoardPositionsList list) {
		int spaceIndex;
		for (int space = 0; space < Board.MAX_BOARD_SIZE; space = space	+ Board.MAX_ROWS) {
			spaceIndex = column + space;

			list.add(spaceIndex);
		}
	}

	public GroupOfDirectionalBoardPositionLists getIndexDiagonalDowns(
			GroupOfDirectionalBoardPositionLists listGroup) {
		assembleLowerBoardDiagonalDowns(listGroup);
		assembleUpperBoardDiagonalDowns(listGroup);

		return listGroup;
	}

	public GroupOfDirectionalBoardPositionLists getIndexDiagonalUps(GroupOfDirectionalBoardPositionLists listGroup) {
		assembleUpperBoardDiagonalUps(listGroup);
		assembleLowerBoardDiagonalUps(listGroup);

		return listGroup;
	}

	private void assembleUpperBoardDiagonalDowns(
			GroupOfDirectionalBoardPositionLists listGroup) {
		int currentDiagonalDownSize = 9;

		for (int diagonalGroup = 1; diagonalGroup < (Board.MAX_COLUMNS-winningSeriesSize+1); diagonalGroup++) {
			DirectionalBoardPositionsList list = new DirectionalBoardPositionsList();

			getAllSpacesInThisUpperBoardDiagonalDown(currentDiagonalDownSize,
					diagonalGroup, list);
			listGroup.add(list);
			currentDiagonalDownSize--;
		}
	}

	private void getAllSpacesInThisUpperBoardDiagonalDown(
			int currentDiagonalDownSize, int diagonalGroup, DirectionalBoardPositionsList list) {
		int spaceIndex;
		for (int space = 0; space < currentDiagonalDownSize; space++) {
			spaceIndex = diagonalGroup
					+ (space * DIAGONAL_DOWN_AFTER_INCREMENT);

			list.add(spaceIndex);
		}
	}

	private int assembleLowerBoardDiagonalDowns(
			GroupOfDirectionalBoardPositionLists listGroup) {
		int currentDiagonalDownSize = winningSeriesSize;

		for (int diagonalGroup = getFirstSquareOfFirstLowerDiagonalDown(); diagonalGroup > -1; diagonalGroup = diagonalGroup
				- VERTICAL_AFTER_INCREMENT) {
			DirectionalBoardPositionsList list = new DirectionalBoardPositionsList();

			getAllSpacesInThisLowerBoardDiagonalDown(currentDiagonalDownSize,
					diagonalGroup, list);

			listGroup.add(list);
			currentDiagonalDownSize++;
		}
		return currentDiagonalDownSize;
	}

	private void getAllSpacesInThisLowerBoardDiagonalDown(
			int currentDiagonalDownSize, int diagonalGroup, DirectionalBoardPositionsList list) {
		int spaceIndex;
		for (int space = 0; space < currentDiagonalDownSize; space++) {
			spaceIndex = diagonalGroup + (space * DIAGONAL_DOWN_AFTER_INCREMENT);

			list.add(spaceIndex);
		}
	}

	private void assembleLowerBoardDiagonalUps(GroupOfDirectionalBoardPositionLists listGroup) {
		int currentDiagonalUpSize = 9;

		for (int diagonalGroup = getFirstSquareOfFirstLowerDiagonalUp(); diagonalGroup <= getFirstSquareOfLastLowerDiagonalUp(); diagonalGroup++) {
			DirectionalBoardPositionsList list = new DirectionalBoardPositionsList();

			getAllSpacesInThisLowerBoardDiagonalUp(currentDiagonalUpSize,
					diagonalGroup, list);

			listGroup.add(list);
			currentDiagonalUpSize--;
		}
	}

	private void getAllSpacesInThisLowerBoardDiagonalUp(
			int currentDiagonalUpSize, int diagonalGroup, DirectionalBoardPositionsList list) {
		int spaceIndex;
		for (int space = 0; space < currentDiagonalUpSize; space++) {
			spaceIndex = diagonalGroup - (space * DIAGONAL_UP_BEFORE_INCREMENT);

			list.add(spaceIndex);
		}
	}

	private int assembleUpperBoardDiagonalUps(GroupOfDirectionalBoardPositionLists listGroup) {
		int currentDiagonalUpSize = winningSeriesSize;

		for (int diagonalGroup = getFirstSquareOfFirstUpperDiagonalUp(); diagonalGroup < Board.MAX_BOARD_SIZE; diagonalGroup = diagonalGroup
				+ VERTICAL_AFTER_INCREMENT) {
			DirectionalBoardPositionsList list = new DirectionalBoardPositionsList();

			getAllSpacesInThisUpperBoardDiagonalUp(currentDiagonalUpSize,
					diagonalGroup, list);
			listGroup.add(list);
			currentDiagonalUpSize++;
		}
		return currentDiagonalUpSize;
	}

	private void getAllSpacesInThisUpperBoardDiagonalUp(
			int currentDiagonalUpSize, int diagonalGroup, DirectionalBoardPositionsList list) {
		int spaceIndex;
		for (int space = 0; space < currentDiagonalUpSize; space++) {
			spaceIndex = diagonalGroup - (space * DIAGONAL_UP_BEFORE_INCREMENT);

			list.add(spaceIndex);
		}
	}
}
