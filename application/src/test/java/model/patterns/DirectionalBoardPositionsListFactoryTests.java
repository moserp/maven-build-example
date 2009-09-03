package model.patterns;

import static org.junit.Assert.*;
import model.gamestate.Board;

import org.junit.Before;
import org.junit.Test;

public class DirectionalBoardPositionsListFactoryTests {

	private DirectionalBoardPositionsListFactory playerFactory;
	private DirectionalBoardPositionsListFactory computerFactory;
	private GroupOfDirectionalBoardPositionLists listGroup;
	
	@Before
	public void setUp() throws Exception {
		playerFactory = new DirectionalBoardPositionsListFactory(Board.HUMAN_PLAYER_MARK);
		computerFactory = new DirectionalBoardPositionsListFactory(Board.COMPUTER_PLAYER_MARK);
		listGroup = new GroupOfDirectionalBoardPositionLists();
	}

	@Test
	public void number_of_rows_should_be_number_of_board_rows() {
		playerFactory.getIndexHorizontalRows(listGroup);
		assertEquals(Board.MAX_ROWS, listGroup.size());
	}

	@Test
	public void number_of_columns_should_be_number_of_board_columns() {
		playerFactory.getIndexHorizontalRows(listGroup);
		assertEquals(Board.MAX_COLUMNS, listGroup.size());
	}

	@Test
	public void number_of_diagonal_downs_for_five_in_row_should_be_11() {
		int topDiagonalDowns = Board.MAX_COLUMNS - 4;
		int bottomDiagonalDowns = Board.MAX_COLUMNS - 4;
		int commonDiagonalDowns = 1;
		
		playerFactory.getIndexDiagonalDowns(listGroup);
		assertEquals(topDiagonalDowns + bottomDiagonalDowns - commonDiagonalDowns, listGroup.size());
	}	

	@Test
	public void number_of_diagonal_ups_for_five_in_row_should_be_11() {
		int topDiagonalUps = Board.MAX_COLUMNS - 4;
		int bottomDiagonalUps = Board.MAX_COLUMNS - 4;
		int commonDiagonalUps = 1;
		
		playerFactory.getIndexDiagonalDowns(listGroup);
		assertEquals(topDiagonalUps + bottomDiagonalUps - commonDiagonalUps, listGroup.size());
	}	

	@Test
	public void number_of_diagonal_downs_for_six_in_row_should_be_99() {
		int topDiagonalDowns = Board.MAX_COLUMNS - 5;
		int bottomDiagonalDowns = Board.MAX_COLUMNS - 5;
		int commonDiagonalDowns = 1;
		
		computerFactory.getIndexDiagonalDowns(listGroup);
		assertEquals(topDiagonalDowns + bottomDiagonalDowns - commonDiagonalDowns, listGroup.size());
	}	
}
