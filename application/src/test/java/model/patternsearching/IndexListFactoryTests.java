package model.patternsearching;

import junit.framework.TestCase;
import model.gamestate.Board;
import model.patterns.DirectionalBoardPositionsList;
import model.patterns.DirectionalBoardPositionsListFactory;
import model.patterns.GroupOfDirectionalBoardPositionLists;

public class IndexListFactoryTests extends TestCase {

	// TODO Need test coverage to bracket each of these groups of lists:
	/*
	 * 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27
	 * 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51
	 * 52 53 54 55 56 57 58 59 60 61 62 63 64 65 66 67 68 69 70 71 72 73 74 75
	 * 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94 95 96 97 98 99
	 * 
	 * 0 10 20 30 40 50 60 70 80 90 1 11 21 31 41 51 61 71 81 91 2 12 22 32 42
	 * 52 62 72 82 92 3 13 23 33 43 53 63 73 83 93 4 14 24 34 44 54 64 74 84 94
	 * 5 15 25 35 45 55 65 75 85 95 6 16 26 36 46 56 66 76 86 96 7 17 27 37 47
	 * 57 67 77 87 97 8 18 28 38 48 58 68 78 88 98 9 19 29 39 49 59 69 79 89 99
	 * 
	 * 50 61 72 83 94 40 51 62 73 84 95 30 41 52 63 74 85 96 20 31 42 53 64 75
	 * 86 97 10 21 32 43 54 65 76 87 98
	 * 
	 * 0 11 22 33 44 55 66 77 88 99 1 12 23 34 45 56 67 78 89 2 13 24 35 46 57
	 * 68 79 3 14 25 36 47 58 69 4 15 26 37 48 59 5 16 27 38 49
	 * 
	 * 40 31 22 13 4 50 41 32 23 14 5 60 51 42 33 24 15 6 70 61 52 43 34 25 16 7
	 * 80 71 62 53 44 35 26 17 8 90 81 72 63 54 45 36 27 18 9
	 * 
	 * 91 82 73 64 55 46 37 28 19 92 83 74 65 56 47 38 29 93 84 75 66 57 48 39
	 * 94 85 76 67 58 49 95 86 77 68 59
	 */

	private DirectionalBoardPositionsListFactory listFactory;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		listFactory = new DirectionalBoardPositionsListFactory(Board.HUMAN_PLAYER_MARK);
	}

	public void testReturnsAllRowIndices() throws Exception {
		GroupOfDirectionalBoardPositionLists listGroup = new GroupOfDirectionalBoardPositionLists();
		GroupOfDirectionalBoardPositionLists horizontalGroup = listFactory
				.getIndexHorizontalRows(listGroup);

		assertEquals(Board.MAX_ROWS, horizontalGroup.size());
		DirectionalBoardPositionsList firstRow = horizontalGroup.get(0);
		assertEquals(9, firstRow.get(9));
	}

	public void testReturnsAllColumnIndices() throws Exception {
		GroupOfDirectionalBoardPositionLists listGroup = new GroupOfDirectionalBoardPositionLists();
		GroupOfDirectionalBoardPositionLists verticalGroup = listFactory
				.getIndexVerticalColumns(listGroup);

		assertEquals(Board.MAX_COLUMNS, verticalGroup.size());
		DirectionalBoardPositionsList firstColumn = verticalGroup.get(0);
		assertEquals(Board.getSingleCoordValueFor(9, 0), firstColumn.get(9));
	}

	public void testReturnsAllDiagonalDownsWithPentaRoomIndices()
			throws Exception {
		GroupOfDirectionalBoardPositionLists listGroup = new GroupOfDirectionalBoardPositionLists();
		GroupOfDirectionalBoardPositionLists diagonalDownGroup = listFactory
				.getIndexDiagonalDowns(listGroup);

		assertEquals(Board.MAX_DIAGONAL_DOWNS_WITH_PENTA_ROOM,
				diagonalDownGroup.size());
		DirectionalBoardPositionsList firstDiagonalDown = diagonalDownGroup.get(0);
		assertEquals(Board.getSingleCoordValueFor(5, 0), firstDiagonalDown
				.get(0));

		DirectionalBoardPositionsList fithDiagonalDown = diagonalDownGroup.get(5);
		assertEquals(Board.getSingleCoordValueFor(4, 4), fithDiagonalDown
				.get(4));
	}

	public void testReturnsAllDiagonalUpsWithPentaRoomIndices()
			throws Exception {
		GroupOfDirectionalBoardPositionLists listGroup = new GroupOfDirectionalBoardPositionLists();
		GroupOfDirectionalBoardPositionLists diagonalUpGroup = listFactory
				.getIndexDiagonalUps(listGroup);

		assertEquals(Board.MAX_DIAGONAL_DOWNS_WITH_PENTA_ROOM, diagonalUpGroup
				.size());
		DirectionalBoardPositionsList firstDiagonalUp = diagonalUpGroup.get(0);
		assertEquals(Board.getSingleCoordValueFor(4, 0), firstDiagonalUp.get(0));

		DirectionalBoardPositionsList fithDiagonalDown = diagonalUpGroup.get(5);
		assertEquals(Board.getSingleCoordValueFor(5, 4), fithDiagonalDown
				.get(4));
	}

	public void testReturnsAllIndexGroupsForAllDirections() throws Exception {
		GroupOfDirectionalBoardPositionLists listGroup = listFactory.getAllIndexLists();

		assertEquals(Board.getSingleCoordValueFor(4, 2), listGroup.size());
	}

}
