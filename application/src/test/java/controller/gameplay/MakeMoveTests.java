package controller.gameplay;

import model.gamestate.Board;
import model.strategy.ExampleStrategy;

import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;

import view.applet.IGameView;

public class MakeMoveTests extends MockObjectTestCase {

	private TicTacToeGame game;
	private Board board;

	@Override
	protected void setUp() throws Exception {
		game = new TicTacToeGame(new ExampleStrategy(), new StubView());
		board = game.getBoard();
	}

	public void testWePickRandomMidBoardSpotForNewGame() throws Exception {
		int position = game.makeMove();

		assertTrue(position < Board.MID_BOARD_UPPER_BOUND);
		assertTrue(position >= Board.MID_BOARD_LOWER_BOUND);
	}

	public void testOnSecondMoveWePickShadowPosition() throws Exception {
		board.setPosition(4, 5, Board.HUMAN_PLAYER_MARK);

		int position = game.makeMove();
		assertEquals(Board.getSingleCoordValueFor(5, 4), position);
	}

	public void testWeCanBlockASeriesOfFour() throws Exception {
		board.setPosition(4, 5, Board.HUMAN_PLAYER_MARK);
		board.setPosition(4, 6, Board.HUMAN_PLAYER_MARK);
		board.setPosition(4, 7, Board.HUMAN_PLAYER_MARK);
		board.setPosition(4, 8, Board.HUMAN_PLAYER_MARK);

		assertEquals(Board.getSingleCoordValueFor(4, 9), game.makeMove());
	}

	public void testFindTesseraEndingBlockingPositionBeforePairStartingPosition()
			throws Exception {
		board.setPosition(2, 3, Board.HUMAN_PLAYER_MARK);
		board.setPosition(3, 2, Board.HUMAN_PLAYER_MARK);
		board.setPosition(3, 3, Board.COMPUTER_PLAYER_MARK);
		board.setPosition(3, 4, Board.COMPUTER_PLAYER_MARK);
		board.setPosition(3, 5, Board.COMPUTER_PLAYER_MARK);
		board.setPosition(3, 6, Board.COMPUTER_PLAYER_MARK);

		assertEquals(Board.getSingleCoordValueFor(3, 7), game.makeMove());
	}

	public void testFillGapInPotentialOpposingTessera() throws Exception {
		board.setPosition(4, 7, Board.HUMAN_PLAYER_MARK);
		board.setPosition(5, 6, Board.HUMAN_PLAYER_MARK);
		board.setPosition(7, 3, Board.HUMAN_PLAYER_MARK);
		board.setPosition(7, 4, Board.HUMAN_PLAYER_MARK);

		assertEquals(Board.getSingleCoordValueFor(6, 5), game.makeMove());
	}

	public void testViewGetsRestartedOnCreation() throws Exception {
		final IGameView mockGameView = mock(IGameView.class);

		checking(new Expectations() {
			{
				one(mockGameView).restartGame();
			}
		});
		game = new TicTacToeGame(new ExampleStrategy(), mockGameView);
	}

	public void testViewGetsRestartedOnStartNewGame() throws Exception {
		final IGameView mockGameView = mock(IGameView.class);

		checking(new Expectations() {
			{
				exactly(2).of(mockGameView).restartGame();
			}
		});
		game = new TicTacToeGame(new ExampleStrategy(), mockGameView);
		game.startNewGame();
	}

	public void testViewUpdatedWithNewMarkOnMove() throws Exception {
		final IGameView mockGameView = mock(IGameView.class);

		checking(new Expectations() {
			{
				one(mockGameView).restartGame();

				// Ensure the view gets updated with the one the user selected
				one(mockGameView).drawMark(1, 2, Board.HUMAN_PLAYER_MARK);

				// Allow any choice for the selection of the computer's position
				one(mockGameView).drawMark(with(any(int.class)),
						with(any(int.class)),
						with(equal(Board.COMPUTER_PLAYER_MARK)));
			}
		});
		game = new TicTacToeGame(new ExampleStrategy(), mockGameView);
		game.makeCompleteMoveCycle(12);
	}

	// TODO: test that the game updates the view with the spot where the last
	// move was
}
