package eu.veldsoft.tri.peaks;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EnumSet;

import javax.swing.JPanel;

/**
 * Start Base64 encoding and decoding code.**NOTE*** This is NOT my code. This
 * code was written by Christian d'Heureuse to provide a more standard base64
 * coder that's fast and efficient. As such, I won't provide comments for that
 * code. Java does NOT provide a Base64 encoder/decoder as part of the API.
 * 
 * @author Christian d'Heureuse
 */
class CardPanel extends JPanel implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private CardBoard board = new CardBoard();

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	public void redeal() {
		board.redeal();
	}

	public int getPenalty() {
		return board.getPenalty();
	}

	public void doPenalty(int penalty) {
		board.doPenalty(penalty);
	}

	public void reset() {
		board.reset();
	}

	public void setDefaults() {
		board.setDefaults();
	}

	public String getCardFront() {
		return board.getCardFront();
	}

	public String getCardBack() {
		return board.getCardBack();
	}

	public void setCardBack(String actionCommand) {
		board.setCardBack(actionCommand);
	}

	public void setCardFront(String actionCommand) {
		board.setCardFront(actionCommand);
	}

	public Color getBackColor() {
		return board.getBackColor();
	}

	public void setBackColor(Color newColor) {
		board.setBackColor(newColor);
	}

	public Color getFontColor() {
		return board.getFontColor();
	}

	public Font getTextFont() {
		return board.getTextFont();
	}

	public void setFontColor(Color foreground) {
		board.setFontColor(foreground);
	}

	public void setTextFont(Font font) {
		board.setTextFont(font);
	}

	public boolean hasCheated() {
		return board.hasCheated();
	}

	public int[] getAllStats() {
		return board.getAllStats();
	}

	public void setStats(int[] stats) {
		board.setStats(stats);
	}

	public void setCheated(boolean hasCheated) {
		board.setCheated(hasCheated);
	}

	public EnumSet<Cheat> getCheats() {
		return board.getCheats();
	}

	public void setCheat(Cheat cardsFaceUp, boolean newState) {
		board.setCheat(cardsFaceUp, newState);
	}

	public int getScore() {
		return board.getScore();
	}

	public int getHighScore() {
		return board.getHighScore();
	}

	public int getLowScore() {
		return board.getLowScore();
	}

	public int getNumGames() {
		return board.getNumGames();
	}

	public int getHighStreak() {
		return board.getHighStreak();
	}
}