package model.patterns;

import model.gamestate.Board.SeriesSize;

public class GapSeries extends Series implements ISeries {
	private DirectionalBoardPositionsList emptyList;

	public GapSeries() {
		super();
		emptyList = new DirectionalBoardPositionsList();
	}

	public int getGap(int i) {
		return emptyList.get(i);
	}

	public void setBlockingPositionsDependingOnNumberOfEmptySpaces() {
		if (weHaveNoEmptySpaces())
			return;

		if (weOnlyHaveOneEmptySpace()) {
			setBothBlockingPositionsToSameEmptySpace();
		} else {
			setDifferentStartingAndEndingBlockingPositions();
		}
	}

	private boolean weHaveNoEmptySpaces() {
		return emptyList.size() == 0;
	}

	private void setDifferentStartingAndEndingBlockingPositions() {
		setStartingBlockingPosition(emptyList.get(0));
		setEndingBlockingPosition(emptyList.get(1));
	}

	private void setBothBlockingPositionsToSameEmptySpace() {
		setStartingBlockingPosition(emptyList.get(0));
		setEndingBlockingPosition(emptyList.get(0));
	}

	private boolean weOnlyHaveOneEmptySpace() {
		return emptyList.size() < SeriesSize.TWO.getSize();
	}

	public void addEmptyPosition(int position) {
		emptyList.add(position);
	}
}
