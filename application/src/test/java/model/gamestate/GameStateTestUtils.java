package model.gamestate;

import controller.gameplay.TicTacToeGame;

public class GameStateTestUtils {
	private static final String DOUBLE_BLANK_SPACE = "  ";
	private static final String SINGLE_BLANK_SPACE = " ";

	public static String returnPrintableBoard(TicTacToeGame game, String crCharacter) {
		String border = "*****************************" + crCharacter;
		String boardString = border;
		String spacer = SINGLE_BLANK_SPACE;

		spacer = DOUBLE_BLANK_SPACE;
		for (int x = 0; x < 10; x++) {
			boardString = printBoardRow(game, boardString, spacer, x);
		}
		boardString += border;

		return boardString;
	}

	public static String printBoardRow(TicTacToeGame game, String boardString, String spacer, int x) {
		int cell;
		for (int y = 0; y < 10; y++) {
			String mark = null;
			cell = game.getPosition(y);

			if (doubleDigitRow(cell))
				spacer = SINGLE_BLANK_SPACE;
			else if ((game.getPosition(0) == 0) && (game.getPosition(1) == 0))
				spacer = DOUBLE_BLANK_SPACE;
			else
				spacer = DOUBLE_BLANK_SPACE;

			if (game.getPosition(cell) == 2) {
				mark = "00";
				if (cell < 9)
					spacer = SINGLE_BLANK_SPACE;
			}
			if (game.getPosition(cell) == 1) {
				mark = "XX";
				if (cell < 9)
					spacer = SINGLE_BLANK_SPACE;
			}
			if (mark == null) {
				boardString += "" + cell + spacer;
			} else {
				boardString += "" + mark + spacer;
			}
		}
		boardString += "\n";
		return boardString;
	}

	private static boolean doubleDigitRow(int cell) {
		return cell > 9;
	}

}
