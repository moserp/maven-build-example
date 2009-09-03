package view.applet;

public interface IGameView {

	void restartGame();

	void drawMark(int row, int column, int playerMark);

	void computerWonGame();

	void humanComputerWonGame();

	void gameIsADraw();

}
