package model.patternsearching;

import model.gamestate.Board;
import model.gamestate.Board.SeriesSize;

public class PatternFinder {
	private RandomPositionFinder randomPositionFinder;
	private ShadowPositionFinder shadowFinder;
	private SeriesFinder humanSeriesFinder;
	private SeriesFinder computerSeriesFinder;
	private GapSeriesFinder gapSeriesFinder;

	public PatternFinder(Board board) {
		randomPositionFinder = new RandomPositionFinder(board);
		shadowFinder = new ShadowPositionFinder(board);
		humanSeriesFinder = new SeriesFinder(board, Board.HUMAN_PLAYER_MARK);
		computerSeriesFinder = new SeriesFinder(board, Board.COMPUTER_PLAYER_MARK);
		gapSeriesFinder = new GapSeriesFinder(board);
	}

	public int findRandomEmptyMidBoardPosition() {
		return randomPositionFinder.findRandomEmptyMidBoardPosition();
	}

	public int getBestShadowPosition(int ourPlayerMark) {
		return shadowFinder
				.getPositionInMostOpenSeriesNearOppositePlayer(ourPlayerMark);
	}

	public int findRandomEmptyPosition() {
		return randomPositionFinder.findRandomEmptyPosition();
	}

	public int getBestBlockingPositionForHumanWithSeriesOfSize(SeriesSize size) {
		return humanSeriesFinder.getBestBlockingPositionForSeriesOfSize(size,
				Board.HUMAN_PLAYER_MARK);
	}

	public int getBestBlockingPositionForComputerWithSeriesOfSize(SeriesSize size) {
		return computerSeriesFinder.getBestBlockingPositionForSeriesOfSize(size,
				Board.COMPUTER_PLAYER_MARK);
	}

	public int getAlternateBlockingPositionForHumanWithSeries() {
		return humanSeriesFinder.getAlternatePosition();
	}

	public int getAlternateBlockingPositionForComputerWithSeries() {
		return computerSeriesFinder.getAlternatePosition();
	}

	public int getGapForGapSeriesOfSize(SeriesSize size, int playerMark) {
		return gapSeriesFinder.getBestBlockingPositionForSeriesOfSize(size,
				playerMark);
	}

	public boolean canFindSeriesOfSize(SeriesSize size, int playerMark) {
		return humanSeriesFinder.containsSeriesOfSize(size, playerMark);
	}
}
