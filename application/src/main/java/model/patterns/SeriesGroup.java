package model.patterns;

import java.util.ArrayList;

import model.gamestate.Board;

public class SeriesGroup {
	ArrayList<ISeries> seriesList;

	public SeriesGroup(Board board) {
		seriesList = new ArrayList<ISeries>();
	}

	public void add(ISeries series) {
		seriesList.add(series);
	}

	public int size() {
		return seriesList.size();
	}

	public ISeries get(int i) {
		return seriesList.get(i);
	}
}
