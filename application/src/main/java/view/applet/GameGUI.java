package view.applet;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import model.gamestate.Board;
import model.strategy.ExampleStrategy;
import controller.gameplay.TicTacToeGame;

public class GameGUI extends Applet implements IGameView {
	private static final long serialVersionUID = 4873261510528018302L;

	private static final int WINDOW_HEIGHT = 350;
	private static final int WINDOW_WIDTH = 320;

	private static final int CELL_WIDTH = 30;
	private static final int CELL_HEIGHT = 40;
	private static final int BOARD_LOWER_BOUNDS = 40;
	private static final int BOARD_LEFT_BORDER = 100;

	private TicTacToeGame game;
	private GameImages gameImages;
	private Timer lastPlayedImageTimer = new Timer(true);

	// We maintain an offscreen image buffer, so as to not have to maintain or
	// query total game state on every repaint
	// (instead we just react to requests to repaint things).
	private Image imageBuffer;
	private Graphics imageGraphics;

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		imageBuffer = createImage(WINDOW_WIDTH, WINDOW_HEIGHT);
		imageGraphics = imageBuffer.getGraphics();

		gameImages = new GameImages(this);
		gameImages.awaitImageLoad();

		// NOTE: creating a new game actually calls back on us to update the UI
		game = new TicTacToeGame(new ExampleStrategy(), this);

		addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent e) {
				handleMouseEvent(e.getX(), e.getY());
			}
		});
		repaint();
	}

	public void update(Graphics g) {
		// Overridden so it doesn't clear the canvas on every redraw
		paint(g);
	}

	public void paint(Graphics g) {
		// Blit the offscreen image buffer to the screen
		g.drawImage(imageBuffer, 0, 0, this);
	}

	public void handleMouseEvent(int mouseX, int mouseY) {
		if (gameNotInPlay(mouseX, mouseY))
			return;

		int playerPosition = getPlayerPosition(mouseX, mouseY);
		if (!game.positionIsAvailable(playerPosition))
			return;

		game.makeCompleteMoveCycle(playerPosition);
	}

	private boolean gameNotInPlay(int mouseX, int mouseY) {
		if (playerClickedNewGameButton(mouseX, mouseY)) {
			System.out.println("Player clicked new game button...");
			game.startNewGame();
			return true;
		}
		if (cannotMakeAMove(mouseX, mouseY))
			return true;
		return false;
	}

	private boolean cannotMakeAMove(int mouseX, int mouseY) {
		if (game.weHaveAWinner())
			return true;
		if (game.moveNumber() > TicTacToeGame.MAX_NUMBER_OF_MOVES)
			return true;

		return false;
	}

	private int getPlayerPosition(int mouseX, int mouseY) {
		int boardX = mouseX / CELL_WIDTH;
		int boardY = (mouseY - CELL_HEIGHT) / CELL_WIDTH;
		int playerMove = boardY * Board.MAX_COLUMNS + boardX;
		return playerMove;
	}

	private boolean playerClickedNewGameButton(int mouseX, int mouseY) {
		return (mouseY < BOARD_LOWER_BOUNDS) && (mouseX < BOARD_LEFT_BORDER);
	}

	private void drawEmptyBoard() {
		for (int row = 0; row < Board.MAX_ROWS; row++) {
			for (int column = 0; column < Board.MAX_COLUMNS; column++) {
				drawMark(row, column, Board.EMPTY);
			}
		}
	}

	public void restartGame() {
		drawEmptyBoard();
		resetToolbar();
	}

	private void resetToolbar() {
		imageGraphics.drawImage(gameImages.getNewGameImage(), 0, 0, this);
		imageGraphics.drawImage(gameImages.getYourTurnImage(),
				Board.MAX_BOARD_SIZE, 0, this);
	}

	public void gameIsADraw() {
		imageGraphics.drawImage(gameImages.getDrawImage(),
				Board.MAX_BOARD_SIZE, 0, this);
	}

	public void humanComputerWonGame() {
		imageGraphics.drawImage(gameImages.getWinningImage(),
				Board.MAX_BOARD_SIZE, 0, this);
	}

	public void computerWonGame() {
		imageGraphics.drawImage(gameImages.getLosingImage(),
				Board.MAX_BOARD_SIZE, 0, this);
	}

	public void drawMark(final int row, final int column, int playerMark) {
		int x = column * CELL_WIDTH;
		int y = row * CELL_WIDTH + CELL_HEIGHT;

		imageGraphics.drawImage(gameImages.getImageForPlayerMark(playerMark),
				x, y, this);

		if (playerMark == Board.COMPUTER_PLAYER_MARK) {
			scheduleUpdateLastPlayedImage(x, y);
		}
		repaint();
	}

	private void scheduleUpdateLastPlayedImage(final int x, final int y) {
		// Update the last played image to change from a filled o mark to a
		// normal o mark after 1/2 a second.

		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				imageGraphics.drawImage(gameImages.getOMark(), x, y,
						GameGUI.this);
				repaint();
			}
		};
		lastPlayedImageTimer.schedule(timerTask, 500);
	}

}
