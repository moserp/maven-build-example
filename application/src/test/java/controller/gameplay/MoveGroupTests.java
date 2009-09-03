package controller.gameplay;

import java.util.ArrayList;

import junit.framework.TestCase;
import model.gamestate.Move;
import model.gamestate.MoveGroup;
import controller.gameplay.TicTacToeGame.MoveScore;

public class MoveGroupTests extends TestCase {
	MoveGroup moveGroup = new MoveGroup();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Move move = new Move(1, MoveScore.NONE, "");
		moveGroup.add(move);

		move = new Move(1, MoveScore.NONE, "");
		moveGroup.add(move);

		move = new Move(13, MoveScore.NINE, "");
		moveGroup.add(move);

		move = new Move(4, MoveScore.NINE, "");
		moveGroup.add(move);

		move = new Move(4, MoveScore.TWO, "");
		moveGroup.add(move);

		move = new Move(4, MoveScore.ONE, "");
		moveGroup.add(move);

		move = new Move(13, MoveScore.TWO, "");
		moveGroup.add(move);

		move = new Move(25, MoveScore.TWO, "");
		moveGroup.add(move);

		move = new Move(25, MoveScore.TWO, "");
		moveGroup.add(move);

		move = new Move(25, MoveScore.TWO, "");
		moveGroup.add(move);

		move = new Move(25, MoveScore.TWO, "");
		moveGroup.add(move);
	}

	public void testOnlyNonNegativeMovePositionsAndScoresAreStored()
			throws Exception {
		assertEquals(9, moveGroup.size());
		ArrayList<Move> allMoves = moveGroup.getAllMoves();
		assertEquals(9, allMoves.size());
	}

	public void testGetBestMoveIsHighestScoringMoveThatRepeatsMostTimes()
			throws Exception {
		assertEquals(4, moveGroup.bestOverallMove().getPosition());
	}

	public void testHighestScoringSingleMove() throws Exception {
		ArrayList<Move> allMoves = moveGroup.getAllMoves();
		Move firstHighestScoringMove = allMoves.get(0);
		assertEquals(13, firstHighestScoringMove.getPosition());
		assertEquals(13, moveGroup.getFirstHighestScoringSingleMove()
				.getPosition());
	}

	public void testbestScoringMoves() throws Exception {
		ArrayList<Move> allMoves = moveGroup.getAllMoves();
		Move highestScoringMove = allMoves.get(0);
		ArrayList<Move> bestScoringMoves = new ArrayList<Move>();
		bestScoringMoves = moveGroup
				.getAllMovesWithSameScoreAsHighestScoringMove(allMoves,
						highestScoringMove);

		assertEquals(2, bestScoringMoves.size());
	}

	public void testMoveFrequentlyOccurringMoves() throws Exception {
		ArrayList<Move> allMoves = moveGroup.getAllMoves();
		Move highestScoringMove = allMoves.get(0);
		ArrayList<Move> bestScoringMoves = new ArrayList<Move>();
		bestScoringMoves = moveGroup
				.getAllMovesWithSameScoreAsHighestScoringMove(allMoves,
						highestScoringMove);

		Move mostFrequentlyRepeatingMove = moveGroup
				.findMoveThatRepeatsMost(bestScoringMoves);
		assertEquals(4, mostFrequentlyRepeatingMove.getPosition());
	}

	public void testGetTwoBirdsMove() throws Exception {
		ArrayList<Move> allMoves = moveGroup.getAllMoves();
		Move highestScoringMove = allMoves.get(0);

		Move twoBirdsMove = moveGroup.getTwoBirdsMove(highestScoringMove,
				allMoves);
		assertEquals(4, twoBirdsMove.getPosition());
	}

}
