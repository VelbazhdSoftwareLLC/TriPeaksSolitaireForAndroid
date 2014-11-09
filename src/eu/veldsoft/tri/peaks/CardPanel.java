package eu.veldsoft.tri.peaks;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.EnumSet;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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

	/**
	 * folder in which the fronts of the cards are stored
	 */
	private String frontFolder = "Default";

	/**
	 * style for the back of the cards
	 */
	private String backStyle = "Default";

	/**
	 * background color of the board
	 */
	private Color backColor = Color.GREEN.darker().darker();

	/**
	 * 
	 */
	private Color fontColor = Color.WHITE;

	/**
	 * 
	 */
	private Font textFont = new Font("Serif", Font.BOLD, 14);

	public CardPanel() {
		/*
		 * sets the size of the panel (10 cards by 4 cards)
		 */
		setPreferredSize(new Dimension(Card.WIDTH * 10, Card.HEIGHT * 4));

		/*
		 * adds a mouse-listener to the board
		 */
		addMouseListener(this);
	}

	/**
	 * 
	 */
	public void setDefaults() {
		frontFolder = "Shiny";
		backStyle = "Default";
		backColor = (Color.GREEN).darker().darker();
		fontColor = Color.WHITE;
		textFont = new Font("Serif", Font.BOLD, 14);
	}

	/**
	 * Custom paint method.
	 * 
	 * @param g
	 *            Graphic context.
	 * @author Kamelia Ivanova
	 */
	@Override
	public void paint(Graphics g) {
		/*
		 * paints the JPanel
		 */
		super.paintComponent(g);

		/*
		 * use the background color
		 */
		g.setColor(backColor);
		g.fillRect(0, 0, getSize().width, getSize().height);

		/*
		 * Draw the background.
		 */
		if (board.getState().isHasCheatedYet() == true) {
			/*
			 * if the user has ever cheated set the color - white, somewhat
			 * transparent
			 */
			g.setColor(new Color(fontColor.getRed(), fontColor.getGreen(),
					fontColor.getBlue(), 80));

			/*
			 * set the font - big and fat
			 */
			g.setFont(new Font("SansSerif", Font.BOLD, 132));

			/*
			 * print "CHEATER" on the bottom edge of the board
			 */
			g.drawString("CHEATER", 0, getSize().height - 5);
		}

		/*
		 * image to be created
		 */
		BufferedImage img = null;

		/*
		 * URL of the image
		 */
		URL imgURL = null;

		/*
		 * Go through each card.
		 */
		for (int q = 0; q < Deck.SIZE; q++) {
			Card card = Deck.cardAtPosition(q);

			/*
			 * If the card is not visible, skip it.
			 */
			if (card.isInvisible() == true) {
				continue;
			}

			/*
			 * if it's face-up
			 */
			if (card.isFacingUp() == true) {
				imgURL = TriPeaks.class.getResource("CardSets" + File.separator
						+ "Fronts" + File.separator + frontFolder
						+ File.separator + card.getSuit()
						+ (card.getRank().getValue() + 1) + ".png");

				/*
				 * get the corresponding front of the card otherwise it's
				 * face-down
				 */
			} else {

				/*
				 * get the image for the back of the card - if the first cheat
				 * isn't on
				 */
				if (board.getState().getCheats().contains(Cheat.CARDS_FACE_UP) == false) {
					imgURL = TriPeaks.class.getResource("CardSets"
							+ File.separator + "Backs" + File.separator
							+ backStyle + ".png");

					/*
					 * get the corresponding front of the card if the cheat is
					 * on...
					 */
				} else {
					imgURL = TriPeaks.class.getResource("CardSets"
							+ File.separator + "Fronts" + File.separator
							+ frontFolder + File.separator + card.getSuit()
							+ (card.getRank().getValue() + 1) + ".png");
				}
			}

			if (imgURL == null) {
				continue;
			}

			try {
				/*
				 * try to read the image
				 */
				img = ImageIO.read(imgURL);
			} catch (IOException eIO) {
				/*
				 * There's an error (probably because the card doesn't exist.
				 */
				System.out.println("Error reading card image");
			}

			if (img == null) {
				continue;
			}

			/*
			 * left edge of the loft
			 */
			int startX = card.getX() - ((int) Card.WIDTH / 2);

			/*
			 * top of the card
			 */
			int startY = card.getY() - ((int) Card.HEIGHT / 2);

			/*
			 * right
			 */
			int endX = startX + Card.WIDTH;

			/*
			 * bottom
			 */
			int endY = startY + Card.HEIGHT;

			/*
			 * draws the image on the panel - resizing/scaling if necessary
			 */
			g.drawImage(img, startX, startY, endX, endY, 0, 0,
					img.getWidth(null), img.getHeight(null), null);
		}

		/*
		 * The won/lost string
		 */
		String scoreStr = (board.getState().getScore() < 0) ? "Lost $" + (-1)
				* board.getState().getScore() : "Won $"
				+ board.getState().getScore();

		/*
		 * display how many cards are remaining
		 */
		String remStr = board.getState().getRemainingCards()
				+ ((board.getState().getRemainingCards() == 1) ? " card"
						: " cards") + " remaining";

		/*
		 * the text is white
		 */
		g.setColor(fontColor);

		/*
		 * set the font for the text
		 */
		g.setFont(textFont);

		/*
		 * put the score on the panel
		 */
		g.drawString(scoreStr, 5, Card.HEIGHT * 3);

		/*
		 * put the remaining cards on the panel
		 */
		g.drawString(remStr, 5, Card.HEIGHT * 3 + 25);

		/*
		 * print the status message.
		 */
		g.drawString(board.getStatus(), 5, getSize().height - 10);

		/*
		 * reset the status message
		 */
		board.setStatus("");
	}

	/**
	 * When the player clicks anywhere on the board.
	 * 
	 * @param e
	 * @author Todor Balabanov
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		/*
		 * place holders for the bounds of the card
		 */
		int startX, startY, endX, endY;

		/*
		 * Go through the cards in reverse order - the higher index-cards are on
		 * top. All the skips make execution of the mouse-click faster.
		 */
		for (int q = Deck.SIZE - 1; q >= 0; q--) {
			Card card = Deck.cardAtPosition(q);

			/*
			 * if the card is invisible, skip it
			 */
			if (card.isInvisible() == true) {
				continue;
			}

			/*
			 * if the card isn't part of the deck and is face-down, skip it
			 */
			if (((q < 28) || (q == 51)) && card.isFacingDown() == true) {
				continue;
			}

			/*
			 * if the card is in the discard pile, skip it
			 */
			if (q == board.getState().getDiscardIndex()) {
				continue;
			}

			/*
			 * left edge of the card
			 */
			startX = card.getX() - ((int) Card.WIDTH / 2);

			/*
			 * top edge of the card
			 */
			startY = card.getY() - ((int) Card.HEIGHT / 2);

			/*
			 * right edge of the card
			 */
			endX = card.getX() + ((int) Card.WIDTH / 2);

			/*
			 * bottom edge of the card
			 */
			endY = card.getY() + ((int) Card.HEIGHT / 2);

			/*
			 * if the mouse was clicked outside the card, skip the rest
			 */
			if (e.getX() < startX) {
				continue;
			}
			if (endX < e.getX()) {
				continue;
			}
			if (e.getY() < startY) {
				continue;
			}
			if (endY < e.getY()) {
				continue;
			}

			/*
			 * a value to check if the card is adjacent by value
			 */
			boolean isAdjacent;

			/*
			 * if the second cheat is used, the value of the card won't be
			 * checked
			 */
			if (board.getState().getCheats().contains(Cheat.CLICK_ANY_CARD) == true) {
				/*
				 * the card is adjacent automatically
				 */
				isAdjacent = true;
			} else {
				/*
				 * no cheat - check card check if the card is adjacent by value
				 */
				isAdjacent = card.getRank().isAdjacentTo(
						Deck.cardAtPosition(board.getState().getDiscardIndex())
								.getRank());
			}

			/*
			 * if the card isn't in the deck and is adjacent to the last
			 * discarded card
			 */
			if (q < 28 && isAdjacent == true) {
				/*
				 * put the card in the discard pile
				 */
				card.setX(Deck.cardAtPosition(
						board.getState().getDiscardIndex()).getX());

				/*
				 * set the discard pile's card's coords
				 */
				card.setY(Deck.cardAtPosition(
						board.getState().getDiscardIndex()).getY());

				/*
				 * hide the previously discarded card - makes the repaint faster
				 */
				Deck.cardAtPosition(board.getState().getDiscardIndex())
						.setInvisible();

				/*
				 * Take the card from the peaks and put it in the discard pile.
				 */
				board.getState().doValidMove(q);

				/*
				 * if it was a peak
				 */
				if (q < 3) {
					/*
					 * if all the peaks are gone
					 */
					if (board.getState().getRemainingCards() == 0) {
						/*
						 * set the status message
						 */
						board.setStatus("You have Tri-Conquered! You get a bonus of $30");
					} else {
						/*
						 * set the status message
						 */
						board.setStatus("You have reached a peak! You get a bonus of $15");
					}

					/*
					 * "consume" the mouse click - don't go through the rest of
					 * the cards
					 */
					break;
				}

				/*
				 * check values for checking whether or not a card has a card to
				 * the left or right
				 */
				boolean noLeft, noRight;

				/*
				 * starts out as having both
				 */
				noLeft = noRight = false;

				/*
				 * if the card isn't a left end
				 */
				if ((q != 3) && (q != 9) && (q != 18) && (q != 5) && (q != 7)
						&& (q != 12) && (q != 15)) {
					/*
					 * check if the left-adjacent card is visible
					 */
					if (Deck.cardAtPosition(q - 1).isInvisible() == true) {
						noLeft = true;
					}
				}

				/*
				 * if the card isn't a right end
				 */
				if ((q != 4) && (q != 6) && (q != 8) && (q != 17) && (q != 27)
						&& (q != 11) && (q != 14)) {
					/*
					 * check if the right-adjacent card is visible
					 */
					if (Deck.cardAtPosition(q + 1).isInvisible() == true) {
						noRight = true;
					}
				}

				/*
				 * some of the cards in the third row are considered to be edge
				 * cards because not all pairs of adjacent cards in the third
				 * row uncover another card
				 */
				if ((noLeft || noRight) == false) {
					/*
					 * if both the left and right cards are present, don't do
					 * anything
					 */
					break;
				}

				/*
				 * the "offset" is the difference in the indeces of the right
				 * card of the adjacent pair and the card that pair will uncover
				 */
				int offset = -1;

				if ((q >= 18) && (q <= 27)) {
					/*
					 * 4th row
					 */
					offset = 10;
				} else if ((q >= 9) && (q <= 11)) {
					/*
					 * first 3 of 3rd row
					 */
					offset = 7;
				} else if ((q >= 12) && (q <= 14)) {
					/*
					 * second 3 of third row
					 */
					offset = 8;
				} else if ((q >= 15) && (q <= 17)) {
					/*
					 * last 3 of third row
					 */
					offset = 9;
				} else if ((q >= 3) && (q <= 4)) {
					/*
					 * first 2 of second row
					 */
					offset = 4;
				} else if ((q >= 5) && (q <= 6)) {
					/*
					 * second 2 of second row
					 */
					offset = 5;
				} else if ((q >= 7) && (q <= 8)) {
					/*
					 * last 2 of second row
					 */
					offset = 6;
				}

				/*
				 * the first row isn't here because the peaks are special and
				 * were already taken care of above
				 */
				if (offset == -1) {
					/*
					 * if the offset didn't get set, don't do anything (offset
					 * should get set, but just in case)
					 */
					break;
				}

				/*
				 * if the left card is missing, use the current card as the
				 * right one
				 */
				if (noLeft) {
					Deck.cardAtPosition(q - offset).flip();
				}

				/*
				 * if the right card is missing, use the missing card as the
				 * right one
				 */
				if (noRight) {
					Deck.cardAtPosition(q - offset + 1).flip();
				}

				/*
				 * in the deck move the card to the deck
				 */
			} else if ((q >= 28) && (q < 51)) {

				/*
				 * set the deck's coordinates
				 */
				card.setX(Deck.cardAtPosition(
						board.getState().getDiscardIndex()).getX());
				card.setY(Deck.cardAtPosition(
						board.getState().getDiscardIndex()).getY());

				/*
				 * hide the previously discarded card (for faster repaint)
				 */
				Deck.cardAtPosition(board.getState().getDiscardIndex())
						.setInvisible();

				/*
				 * flip the deck card
				 */
				card.flip();

				/*
				 * show the next deck card if it's not the last deck card
				 */
				if (q != 28) {
					Deck.cardAtPosition(q - 1).setVisible();
				}

				/*
				 * set the index of the dicard pile
				 */
				board.getState().setDiscardIndex(q);

				/*
				 * reset the streak
				 */
				board.getState().setStreak(0);

				/*
				 * if the third cheat isn't on (no penalty cheat)
				 */
				if (board.getState().getCheats().contains(Cheat.NO_PENALTY) == false) {
					// TODO Call doPenalty() function.

					/*
					 * 5-point penalty
					 */
					board.getState().setScore(
							board.getState().getScore()
									- Constants.NO_PENALTY_CHEAT);

					/*
					 * to the game score
					 */
					board.getState().setGameScore(
							board.getState().getGameScore()
									- Constants.NO_PENALTY_CHEAT);

					/*
					 * and the session score
					 */
					board.getState().setSessionScore(
							board.getState().getSessionScore()
									- Constants.NO_PENALTY_CHEAT);
				}

				/*
				 * set the low score if score is lower
				 */
				if (board.getState().getGameScore() < board.getState()
						.getLowScore()) {
					board.getState().setLowScore(
							board.getState().getGameScore());
				}

				/*
				 * decrement the number of cards in the deck
				 */
				board.getState().setRemainingCards(
						board.getState().getRemainingCards() - 1);
			}

			/*
			 * "consume" the click - don't go through the rest of the cards
			 */
			break;
		}

		/*
		 * Repaint the board.
		 */
		repaint();

		/*
		 * Get the containing frame.
		 */
		TriPeaks theFrame = (TriPeaks) SwingUtilities.windowForComponent(this);

		/*
		 * Update the status labels.
		 */
		theFrame.updateStats();
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

	public int getPenalty() {
		return board.getPenalty();
	}

	public void doPenalty(int penalty) {
		board.doPenalty(penalty);
	}

	/**
	 * returns the current front style
	 * 
	 * @return
	 */
	public String getCardFront() {
		return frontFolder;
	}

	/**
	 * returns the current back style
	 * 
	 * @return
	 */
	public String getCardBack() {
		return backStyle;
	}

	/**
	 * sets the back style
	 * 
	 * @param back
	 */
	public void setCardBack(String back) {
		backStyle = back;
	}

	/**
	 * sets the front style
	 * 
	 * @param front
	 */
	public void setCardFront(String front) {
		frontFolder = front;
	}

	/**
	 * returns the background color
	 * 
	 * @return
	 */
	public Color getBackColor() {
		return backColor;
	}

	/**
	 * sets the background color
	 * 
	 * @param newColor
	 */
	public void setBackColor(Color newColor) {
		backColor = newColor;
	}

	/**
	 * 
	 * @return
	 */
	public Color getFontColor() {
		return fontColor;
	}

	/**
	 * 
	 * @return
	 */
	public Font getTextFont() {
		return textFont;
	}

	/**
	 * 
	 * @param newColor
	 */
	public void setFontColor(Color newColor) {
		fontColor = newColor;
	}

	/**
	 * 
	 * @param newFont
	 */
	public void setTextFont(Font newFont) {
		textFont = newFont;
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

	/**
	 * resets everything
	 */
	public void reset() {
		board.reset();

		/*
		 * repaint the board
		 */
		repaint();

		/*
		 * get the frame
		 */
		TriPeaks theFrame = (TriPeaks) SwingUtilities.windowForComponent(this);

		/*
		 * update the stats labels
		 */
		theFrame.updateStats();
	}

	/**
	 * Redeals the cards.
	 */
	public void redeal() {
		/*
		 * Get the penalty for redealing.
		 */
		int penalty = getPenalty();

		/*
		 * if there is a penalty
		 */
		if (penalty != 0) {
			/*
			 * show a confirmation message
			 */
			int uI = JOptionPane.showConfirmDialog(this,
					"Are you sure you want to redeal?\nRedealing now results in a penalty of $"
							+ penalty + "!", "Confirm Redeal",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			/*
			 * do the penalty if the user agreed
			 */
			if (uI == JOptionPane.YES_OPTION) {
				doPenalty(penalty);
			} else {
				/*
				 * the user doesn't like the penalty, don't redeal
				 */
				return;
			}
		}

		board.redeal();

		/*
		 * repaint the board
		 */
		repaint();

		/*
		 * get the frame that contains the board
		 */
		TriPeaks theFrame = (TriPeaks) SwingUtilities.windowForComponent(this);

		/*
		 * update the stats labels
		 */
		theFrame.updateStats();
	}
}