package view.applet;

import java.applet.Applet;
import java.awt.Image;
import java.awt.MediaTracker;
import java.net.URL;

import model.gamestate.Board;

//TODO Import all graphic file references from properties file	

public class GameImages {
	private MediaTracker tracker;

	private Image xMark;
	private Image oMark;
	private Image filledOMark;
	private Image emptySquare;
	private Image winningImage;
	private Image losingImage;
	private Image yourTurnImage;
	private Image newGameImage;
	private Image drawImage;

	public GameImages(Applet applet) {
		tracker = new MediaTracker(applet);
		URL codeBaseUrl = applet.getCodeBase();

		String graphicsDirectory = "../graphics/";
		
		xMark = applet.getImage(codeBaseUrl, graphicsDirectory + "xMark.jpg");
		oMark = applet.getImage(codeBaseUrl, graphicsDirectory + "oMark.jpg");
		filledOMark = applet.getImage(codeBaseUrl, graphicsDirectory + "oMarkFilled.jpg");
		emptySquare = applet.getImage(codeBaseUrl, graphicsDirectory + "emptySquare.jpg");
		winningImage = applet.getImage(codeBaseUrl, graphicsDirectory + "win.jpg");
		losingImage = applet.getImage(codeBaseUrl, graphicsDirectory + "lose.jpg");
		drawImage = applet.getImage(codeBaseUrl, graphicsDirectory + "draw.jpg");
		yourTurnImage = applet.getImage(codeBaseUrl, graphicsDirectory + "yourTurn.jpg");
		newGameImage = applet.getImage(codeBaseUrl, graphicsDirectory + "newgame.jpg");

		tracker.addImage(xMark, 0);
		tracker.addImage(oMark, 0);
		tracker.addImage(filledOMark, 0);
		tracker.addImage(emptySquare, 0);
		tracker.addImage(winningImage, 0);
		tracker.addImage(losingImage, 0);
		tracker.addImage(yourTurnImage, 0);
		tracker.addImage(newGameImage, 0);
	}

	public void awaitImageLoad() {
		try {
			tracker.waitForAll();
		} catch (InterruptedException e) {
			// TODO: might want to inform the user in a friendlier way than an
			// exception.
			throw new RuntimeException(
					"interrupted exception while loading images");
		}
	}

	public Image getImageForPlayerMark(int playerMark) {
		Image image;

		switch (playerMark) {
		case Board.COMPUTER_PLAYER_MARK:
			// NOTE: the view starts with a filled O, then changes it to a
			// non-filled O with a timer
			image = filledOMark;
			break;

		case Board.HUMAN_PLAYER_MARK:
			image = xMark;
			break;

		default:
			image = emptySquare;
		}

		return image;
	}

	public MediaTracker getTracker() {
		return tracker;
	}

	public Image getXMark() {
		return xMark;
	}

	public Image getOMark() {
		return oMark;
	}

	public Image getFilledOMark() {
		return filledOMark;
	}

	public Image getEmptySquare() {
		return emptySquare;
	}

	public Image getWinningImage() {
		return winningImage;
	}

	public Image getLosingImage() {
		return losingImage;
	}

	public Image getYourTurnImage() {
		return yourTurnImage;
	}

	public Image getNewGameImage() {
		return newGameImage;
	}

	public Image getDrawImage() {
		return drawImage;
	}
}
