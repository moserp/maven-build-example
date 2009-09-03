package strategy;

import java.util.HashMap;
import java.util.Map;

import model.gamestate.Board;
import model.gamestate.Board.SeriesSize;
import model.strategy.IStrategy;

public class StrategyFake implements IStrategy {
	public Map<Integer, Integer> playerSeriesSizes;
	
	public StrategyFake() {
		playerSeriesSizes = new HashMap<Integer, Integer>();
	}

	public Board getBoard() {
		return null;
	}

	public int makeMove() {
		return 0;
	}

	public IStrategy newInstance() {
		return this;
	}


	public boolean wonTheGame(int playerMark, SeriesSize winningSeriesSize) {
		Integer playersSeriesSize = playerSeriesSizes.get(playerMark);
		
		return (playerHasSeriesOfWinningSize(winningSeriesSize, playersSeriesSize));

	}

	private boolean playerHasSeriesOfWinningSize(SeriesSize winningSeriesSize,
			Integer seriesSize) {
		return (seriesSize != null) && (seriesSize >= winningSeriesSize.getSize());
	}
}
