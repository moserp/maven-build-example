package model.gamestate;

import junit.framework.TestCase;

public class BoardBoundariesAccessTests extends TestCase {
	private Board board;

	@Override
	protected void setUp() {
		board = new Board();
	}

	public void testCannotGetPositionOutsideOfBoard() {
		board.getPosition(Board.MIN_ROW_INDEX, Board.MIN_COLUMN_INDEX);
		board.getPosition(Board.MAX_ROW_INDEX, Board.MAX_COLUMN_INDEX);

		assertGetPositionFailure(Board.MIN_ROW_INDEX,
				Board.MIN_COLUMN_INDEX - 1,
				Board.AccessException.Offender.COLUMN);
		assertGetPositionFailure(Board.MIN_ROW_INDEX - 1,
				Board.MIN_COLUMN_INDEX, Board.AccessException.Offender.ROW);
		assertGetPositionFailure(Board.MAX_ROW_INDEX + 1,
				Board.MAX_COLUMN_INDEX, Board.AccessException.Offender.ROW);
		assertGetPositionFailure(Board.MAX_ROW_INDEX,
				Board.MAX_COLUMN_INDEX + 1,
				Board.AccessException.Offender.COLUMN);
		assertGetPositionFailure(Board.MIN_ROW_INDEX - 1,
				Board.MIN_COLUMN_INDEX - 1, Board.AccessException.Offender.BOTH);

	}

	private void assertGetPositionFailure(int row, int column,
			Board.AccessException.Offender offending) {
		try {
			board.getPosition(row, column);
			fail("Should't have been able to get position outside board boundary at row- "
					+ row + " column- " + column);
		} catch (Board.AccessException e) {
			assertEquals(offending, e.offending());
			assertEquals(row, e.row());
			assertEquals(column, e.column());
		}
	}
}
