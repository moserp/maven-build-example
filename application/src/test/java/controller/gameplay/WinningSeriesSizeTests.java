package controller.gameplay;

import junit.framework.TestCase;
import model.gamestate.Board;
import strategy.StrategyFake;

public class WinningSeriesSizeTests extends TestCase {
	private static final int SERIES_SIZE_OF_FOUR = 4;
	private static final int SERIES_SIZE_OF_FIVE = 5;
	
	private StrategyFake fakeStrategy;
	private TicTacToeGame game;

	protected void setUp() throws Exception {
		fakeStrategy = new StrategyFake();	
		game = new TicTacToeGame(fakeStrategy, new StubView());
	}
	
	public void testThatComputerDoesNotWinWithFourInARow() throws Exception {
		fakeStrategy.playerSeriesSizes.put(Board.COMPUTER_PLAYER_MARK, SERIES_SIZE_OF_FOUR);	
		
		assertTrue("there was a winner when there shouldn't be", game.noWinnerYet());		
	}
	
	public void testThatHumanDoesNotWinWithFourInARow() throws Exception {
		fakeStrategy.playerSeriesSizes.put(Board.HUMAN_PLAYER_MARK, SERIES_SIZE_OF_FOUR);	
		
		assertTrue("there was a winner when there shouldn't be", game.noWinnerYet());		
	}

	public void testThatItTakesFiveForTheComputerToWin() {
		fakeStrategy.playerSeriesSizes.put(Board.COMPUTER_PLAYER_MARK, SERIES_SIZE_OF_FIVE);
		game.updateGameState();
		
		assertFalse(game.noWinnerYet());
		assertTrue(game.computerWon());
	}
	
	public void testThatItTakesFiveForTheHumanPlayerToWin() {
		fakeStrategy.playerSeriesSizes.put(Board.HUMAN_PLAYER_MARK, SERIES_SIZE_OF_FIVE);
		game.updateGameState();
		
		assertFalse(game.noWinnerYet());
		assertTrue(game.humanPlayerWon());
	}

}
