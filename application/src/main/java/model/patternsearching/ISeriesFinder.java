package model.patternsearching;

import model.gamestate.Board.SeriesSize;
import model.patterns.SeriesGroup;

public interface ISeriesFinder {

	public abstract SeriesGroup getAllSeriesOfSize(SeriesSize size,
			int playerMark);

	public abstract int getBestBlockingPositionForSeriesOfSize(SeriesSize size,
			int playerMark);

}