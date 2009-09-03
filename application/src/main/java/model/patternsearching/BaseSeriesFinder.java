package model.patternsearching;

import model.gamestate.Board;
import model.gamestate.Board.SeriesSize;
import model.patterns.ISeries;
import model.patterns.DirectionalBoardPositionsList;
import model.patterns.GroupOfDirectionalBoardPositionLists;
import model.patterns.SeriesGroup;

public abstract class BaseSeriesFinder {
	protected Board board;
	protected GroupOfDirectionalBoardPositionLists listGroup;
	protected PenteRoomVerifier penteRoomVerifier;
	protected int bestPosition;
	protected int alternatePosition;
	private SeriesGroup allSeriesFound;

	public BaseSeriesFinder() {
		super();
	}

	public SeriesGroup getAllSeriesOfSize(SeriesSize size, int playerMark) {
		allSeriesFound = new SeriesGroup(board);

		for (int listGroupIndex = 0; listGroupIndex < listGroup.size(); listGroupIndex++) {
			DirectionalBoardPositionsList currentIndexList = listGroup.get(listGroupIndex);
			addAllQualifyingSeriesInThisIndexList(size, playerMark,
					currentIndexList);
		}

		return allSeriesFound;
	}

	private void addAllQualifyingSeriesInThisIndexList(SeriesSize size,
			int playerMark, DirectionalBoardPositionsList currentIndexList) {
		ISeries currentSeries;
		currentSeries = searchIndexListForSeriesOfSize(currentIndexList, size,
				playerMark);
		allSeriesFound = addQualifyingSeries(playerMark, currentSeries,
				currentIndexList);
	}

	public SeriesGroup addQualifyingSeries(int playerMark,
			ISeries currentSeries, DirectionalBoardPositionsList currentIndexList) {
		if (currentSeries.size() != 0) {
			addSeriesWithPenteRoom(playerMark, currentSeries, currentIndexList);
		}

		return allSeriesFound;
	}

	private void addSeriesWithPenteRoom(int playerMark, ISeries currentSeries,
			DirectionalBoardPositionsList currentIndexList) {
		if (penteRoomVerifier.hasPenteRoom(currentSeries, currentIndexList,
				playerMark)) {
			allSeriesFound.add(currentSeries);
			currentSeries = addBlockingPositionsTo(currentSeries,
					currentIndexList);
		}
	}

	protected abstract ISeries searchIndexListForSeriesOfSize(
			DirectionalBoardPositionsList currentIndexList, SeriesSize size, int playerMark);

	protected abstract ISeries addBlockingPositionsTo(ISeries currentSeries,
			DirectionalBoardPositionsList currentIndexList);

	protected abstract int getBlockingPositionAfter(
			int lastSeriesPositionListIndex, DirectionalBoardPositionsList currentList);

	protected abstract int getBlockingPositionBefore(
			int startingPositionListIndex, DirectionalBoardPositionsList currentList);
}