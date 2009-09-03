package model.patternsearching;

import model.gamestate.Board;

public class RandomPositionFinder {
	private static final int MAX_RANDOM_GENERATION_TRIES = 1000;
	private static final int TEN = 10;
	private Board board;

	public RandomPositionFinder(Board board) {
		this.board = board;
	}

	// TODO: Test this against a board nearly full of positions; should return
	// single opening
	public int findRandomEmptyPosition() {
		int candidatePosition = (int) (Math.random() * (Board.MAX_BOARD_SIZE));

		while (candidatePositionIsNotEmpty(candidatePosition)) {
			candidatePosition = (int) (Math.random() * (Board.MAX_BOARD_SIZE));
		}

		return candidatePosition;
	}

	private boolean candidatePositionIsNotEmpty(int candidatePosition) {
		return (board.getPosition(candidatePosition) != Board.EMPTY);
	}

	public int findRandomEmptyMidBoardPosition() {
		int result = getStartingResultCandidate();

		int numberOfTries = 0;
		while (isNotWithinBounds(result) || candidatePositionIsNotEmpty(result)) {
			if (weHaveRunOutOfTries(numberOfTries))
				return -1;

			result = getAnotherRandomPositionWithinRange();

			numberOfTries++;
		}

		return result;
	}

	private int getAnotherRandomPositionWithinRange() {
		int result;
		int rawNumberWithinLowerBoardRange;

		rawNumberWithinLowerBoardRange = getPositionWithinLowerBoardRange();
		result = boostRawNumberToMidBoardRange(rawNumberWithinLowerBoardRange);
		return result;
	}

	private int getStartingResultCandidate() {
		int rawNumberWithinLowerBoardRange = getPositionWithinLowerBoardRange();
		int result = boostRawNumberToMidBoardRange(rawNumberWithinLowerBoardRange);
		return result;
	}

	private boolean weHaveRunOutOfTries(int numberOfTries) {
		return numberOfTries >= MAX_RANDOM_GENERATION_TRIES;
	}

	private boolean isNotWithinBounds(int result) {
		return ((!isWithinMidBoardHorizontalStripe(result)) || (!isWithinMidBoardVerticalStripe(result)));
	}

	private boolean isWithinMidBoardHorizontalStripe(int result) {
		return (result < Board.MID_BOARD_UPPER_BOUND)
				&& (result > Board.MID_BOARD_LOWER_BOUND);
	}

	private boolean isWithinMidBoardVerticalStripe(int result) {
		int resultModulus = result % TEN;
		int lowerBoundModulusLimit = Board.MID_BOARD_LOWER_BOUND % TEN;
		int upperBoundModulusLimit = Board.MID_BOARD_UPPER_BOUND % TEN;
		boolean isWithinMidBoardVerticalStripe = ((resultModulus > (lowerBoundModulusLimit)) && (resultModulus <= (upperBoundModulusLimit)));
		return isWithinMidBoardVerticalStripe;
	}

	private int boostRawNumberToMidBoardRange(int rawNumberWithinRange) {
		return Board.MID_BOARD_LOWER_BOUND + rawNumberWithinRange;
	}

	private int getPositionWithinLowerBoardRange() {
		int number = (int) (Math.random() * (Board.MID_BOARD_UPPER_BOUND - Board.MID_BOARD_LOWER_BOUND));
		return number;
	}
}
