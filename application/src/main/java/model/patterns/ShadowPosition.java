package model.patterns;

public class ShadowPosition {

	private int position;
	private int score;

	public ShadowPosition(int positionInteger) {
		this.position = positionInteger;
	}

	public int getPosition() {
		return position;
	}

	public void setScore(int numberOfOpenLinesFound) {
		this.score = numberOfOpenLinesFound;
	}

	public int getScore() {
		return score;
	}

	public String toString() {
		return Integer.toString(position);
	}
}
