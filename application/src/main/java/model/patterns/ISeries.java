package model.patterns;

public interface ISeries {

	public void add(int position);

	public int size();

	public int get(int i);

	public void setStartingBlockingPosition(int startingBlockingPosition);

	public void setEndingBlockingPosition(int endingBlockingPosition);

	public int getEndingBlockingPosition();

	public int getStartingBlockingPosition();

	public int getGap(int i);

	public void setBlockingPositionsDependingOnNumberOfEmptySpaces();

}