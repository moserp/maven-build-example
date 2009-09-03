package model.gamestate;

import java.util.ArrayList;

import controller.gameplay.TicTacToeGame.MoveScore;

public class MoveGroup {
	private ArrayList<Move> allMoves;
	private Move bestMove;

	public MoveGroup() {
		allMoves = new ArrayList<Move>();
	}

	public void add(Move move) {
		if (move.getScore() == MoveScore.NONE.getScore())
			return;
		if (move.getPosition() == -1)
			return;

		allMoves.add(move);
	}

	public int size() {
		return allMoves.size();
	}

	public Move bestOverallMove() {
		if (allMoves.size() == 0)
			return new Move(-1, MoveScore.NONE, "Couldnt find a decent move");

		// REFACTOR Only need highest score, not entire move
		Move highestScoringMove = getFirstHighestScoringSingleMove();
		Move twoBirdsMove = getTwoBirdsMove(highestScoringMove, allMoves);

		return twoBirdsMove;
	}

	public Move getFirstHighestScoringSingleMove() {
		Move highestScoringMove = allMoves.get(0);
		for (int i = 1; i < allMoves.size(); i++) {
			highestScoringMove = getNextHighScoringMove(highestScoringMove, i);
		}
		return highestScoringMove;
	}

	private Move getNextHighScoringMove(Move highestScoringMove, int i) {
		Move nextMove = allMoves.get(i);
		if (nextMove.getScore() > highestScoringMove.getScore()) {
			highestScoringMove = nextMove;
		}
		return highestScoringMove;
	}

	public Move getTwoBirdsMove(Move highestScoringMove,
			ArrayList<Move> allMoves) {
		ArrayList<Move> bestScoringMoves = getAllMovesWithSameScoreAsHighestScoringMove(
				allMoves, highestScoringMove);
		Move twoBirdsMove = findMoveThatRepeatsMost(bestScoringMoves);

		return twoBirdsMove;
	}

	public Move findMoveThatRepeatsMost(ArrayList<Move> bestMoves) {
		bestMove = bestMoves.get(0);
		int numberOfBestMoveOccurrences = 1;

		for (int i = 1; i < bestMoves.size(); i++) {
			Move moveA = bestMoves.get(i);
			int numberOfThisMovesOccurrences = calculateNumberOfMoveRecurrences(moveA);
			bestMove = saveThisMoveAsNewBestMoveIfPossible(moveA,
					numberOfBestMoveOccurrences, numberOfThisMovesOccurrences);
		}

		return bestMove;
	}

	private Move saveThisMoveAsNewBestMoveIfPossible(Move moveA,
			int numberOfBestMoveOccurences, int numberOfThisMovesOccurences) {
		if (numberOfThisMovesOccurences > numberOfBestMoveOccurences) {
			numberOfBestMoveOccurences = numberOfThisMovesOccurences;
			bestMove = moveA;
		}
		return bestMove;
	}

	private int calculateNumberOfMoveRecurrences(Move moveA) {
		int numberOfMoveRecurrences = 0;

		for (int j = 0; j < allMoves.size(); j++) {
			numberOfMoveRecurrences = incrementMoveOccurrencesIfMoveRecurs(
					moveA, numberOfMoveRecurrences, j);
		}

		return numberOfMoveRecurrences;
	}

	private int incrementMoveOccurrencesIfMoveRecurs(Move moveA,
			int numberOfThisMovesOccurences, int j) {
		Move moveB = allMoves.get(j);
		if (moveB.getPosition() == moveA.getPosition()) {
			numberOfThisMovesOccurences++;
		}
		return numberOfThisMovesOccurences;
	}

	public ArrayList<Move> getAllMovesWithSameScoreAsHighestScoringMove(
			ArrayList<Move> allMoves, Move highestScoringMove) {
		ArrayList<Move> bestMoves = new ArrayList<Move>();

		for (int i = 0; i < allMoves.size(); i++) {
			bestMoves = addValidMoveIfSameScoreAsHighestScoringMove(
					highestScoringMove, bestMoves, i);
		}

		return bestMoves;
	}

	private ArrayList<Move> addValidMoveIfSameScoreAsHighestScoringMove(
			Move highestScoringMove, ArrayList<Move> bestMoves, int i) {
		if (allMoves.get(i).getScore() == highestScoringMove.getScore()) {
			if (allMoves.get(i).isValidMove()) {
				bestMoves.add(allMoves.get(i));
			}
		}

		return bestMoves;
	}

	public ArrayList<Move> getAllMoves() {
		return allMoves;
	}

	public void add(ArrayList<Integer> positions, MoveScore score,
			String message) {
		for (int i = 0; i < positions.size(); i++) {
			allMoves.add(new Move(positions.get(i), score, message));
		}
	}
}
