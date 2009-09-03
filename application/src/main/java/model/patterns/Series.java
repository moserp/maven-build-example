package model.patterns;

import java.util.ArrayList;

public class Series implements ISeries {

	ArrayList<Integer> seriesList;
	int startingBlockingPosition;
	int endingBlockingPosition;

	public Series() {
		seriesList = new ArrayList<Integer>();
	}

	public void add(int position) {
		seriesList.add(position);
	}

	public int size() {
		return seriesList.size();
	}

	public int get(int i) {
		return seriesList.get(i);
	}

	public void setStartingBlockingPosition(int startingBlockingPosition) {
		this.startingBlockingPosition = startingBlockingPosition;
	}

	public void setEndingBlockingPosition(int endingBlockingPosition) {
		this.endingBlockingPosition = endingBlockingPosition;
	}

	public int getEndingBlockingPosition() {
		return endingBlockingPosition;
	}

	public int getStartingBlockingPosition() {
		return startingBlockingPosition;
	}

	public String toString() {
		String listString = "";
		for (int i = 0; i < seriesList.size(); i++) {
			listString += seriesList.get(i) + " ";
		}

		return listString;
	}

	public int getGap(int i) {
		throw new RuntimeException("Should only be invoked on GapSeries.");
	}

	public void setBlockingPositionsDependingOnNumberOfEmptySpaces() {
		throw new RuntimeException("Should only be invoked on GapSeries.");
	}

}
