/*
 * This file is a part of Tri Peaks Solitaire for Android
 *
 * Copyright (C) 2013-2014 by Valera Trubachev, Christian d'Heureuse, Todor 
 * Balabanov, Ina Baltadzhieva
 *
 * Tri Peaks Solitaire for Android is free software: you can redistribute it 
 * and/or modify it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation, either version 3 of the License, 
 * or (at your option) any later version.
 *
 * Tri Peaks Solitaire for Android is distributed in the hope that it will be 
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General 
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with 
 * Tri Peaks Solitaire for Android.  If not, see <http://www.gnu.org/licenses/>.
 */

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
	public static final int NUMBR_OF_STATS = 5;

	/**
	 * 
	 */
	public static final int NCHEATS = 3;

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

	/**
	 * folder in which the fronts of the cards are stored
	 */
	private String frontFolder = "Default";

	/**
	 * style for the back of the cards
	 */
	private String backStyle = "Default";

	/**
	 * Deck of cards.
	 */
	public Card[] deck = {
			new Card(Card.Rank.ACE, Card.Suit.CLUBS, true, false, 0, 0),
			new Card(Card.Rank.TWO, Card.Suit.CLUBS, true, false, 0, 0),
			new Card(Card.Rank.THREE, Card.Suit.CLUBS, true, false, 0, 0),
			new Card(Card.Rank.FOUR, Card.Suit.CLUBS, true, false, 0, 0),
			new Card(Card.Rank.FIVE, Card.Suit.CLUBS, true, false, 0, 0),
			new Card(Card.Rank.SIX, Card.Suit.CLUBS, true, false, 0, 0),
			new Card(Card.Rank.SEVEN, Card.Suit.CLUBS, true, false, 0, 0),
			new Card(Card.Rank.EIGHT, Card.Suit.CLUBS, true, false, 0, 0),
			new Card(Card.Rank.NINE, Card.Suit.CLUBS, true, false, 0, 0),
			new Card(Card.Rank.TEN, Card.Suit.CLUBS, true, false, 0, 0),
			new Card(Card.Rank.JACK, Card.Suit.CLUBS, true, false, 0, 0),
			new Card(Card.Rank.QUEEN, Card.Suit.CLUBS, true, false, 0, 0),
			new Card(Card.Rank.KING, Card.Suit.CLUBS, true, false, 0, 0),
			new Card(Card.Rank.ACE, Card.Suit.HEARTS, true, false, 0, 0),
			new Card(Card.Rank.TWO, Card.Suit.HEARTS, true, false, 0, 0),
			new Card(Card.Rank.THREE, Card.Suit.HEARTS, true, false, 0, 0),
			new Card(Card.Rank.FOUR, Card.Suit.HEARTS, true, false, 0, 0),
			new Card(Card.Rank.FIVE, Card.Suit.HEARTS, true, false, 0, 0),
			new Card(Card.Rank.SIX, Card.Suit.HEARTS, true, false, 0, 0),
			new Card(Card.Rank.SEVEN, Card.Suit.HEARTS, true, false, 0, 0),
			new Card(Card.Rank.EIGHT, Card.Suit.HEARTS, true, false, 0, 0),
			new Card(Card.Rank.NINE, Card.Suit.HEARTS, true, false, 0, 0),
			new Card(Card.Rank.TEN, Card.Suit.HEARTS, true, false, 0, 0),
			new Card(Card.Rank.JACK, Card.Suit.HEARTS, true, false, 0, 0),
			new Card(Card.Rank.QUEEN, Card.Suit.HEARTS, true, false, 0, 0),
			new Card(Card.Rank.KING, Card.Suit.HEARTS, true, false, 0, 0),
			new Card(Card.Rank.ACE, Card.Suit.DIAMONDS, true, false, 0, 0),
			new Card(Card.Rank.TWO, Card.Suit.DIAMONDS, true, false, 0, 0),
			new Card(Card.Rank.THREE, Card.Suit.DIAMONDS, true, false, 0, 0),
			new Card(Card.Rank.FOUR, Card.Suit.DIAMONDS, true, false, 0, 0),
			new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS, true, false, 0, 0),
			new Card(Card.Rank.SIX, Card.Suit.DIAMONDS, true, false, 0, 0),
			new Card(Card.Rank.SEVEN, Card.Suit.DIAMONDS, true, false, 0, 0),
			new Card(Card.Rank.EIGHT, Card.Suit.DIAMONDS, true, false, 0, 0),
			new Card(Card.Rank.NINE, Card.Suit.DIAMONDS, true, false, 0, 0),
			new Card(Card.Rank.TEN, Card.Suit.DIAMONDS, true, false, 0, 0),
			new Card(Card.Rank.JACK, Card.Suit.DIAMONDS, true, false, 0, 0),
			new Card(Card.Rank.QUEEN, Card.Suit.DIAMONDS, true, false, 0, 0),
			new Card(Card.Rank.KING, Card.Suit.DIAMONDS, true, false, 0, 0),
			new Card(Card.Rank.ACE, Card.Suit.SPADES, true, false, 0, 0),
			new Card(Card.Rank.TWO, Card.Suit.SPADES, true, false, 0, 0),
			new Card(Card.Rank.THREE, Card.Suit.SPADES, true, false, 0, 0),
			new Card(Card.Rank.FOUR, Card.Suit.SPADES, true, false, 0, 0),
			new Card(Card.Rank.FIVE, Card.Suit.SPADES, true, false, 0, 0),
			new Card(Card.Rank.SIX, Card.Suit.SPADES, true, false, 0, 0),
			new Card(Card.Rank.SEVEN, Card.Suit.SPADES, true, false, 0, 0),
			new Card(Card.Rank.EIGHT, Card.Suit.SPADES, true, false, 0, 0),
			new Card(Card.Rank.NINE, Card.Suit.SPADES, true, false, 0, 0),
			new Card(Card.Rank.TEN, Card.Suit.SPADES, true, false, 0, 0),
			new Card(Card.Rank.JACK, Card.Suit.SPADES, true, false, 0, 0),
			new Card(Card.Rank.QUEEN, Card.Suit.SPADES, true, false, 0, 0),
			new Card(Card.Rank.KING, Card.Suit.SPADES, true, false, 0, 0) };

	/**
	 * 
	 */
	private EnumSet<Cheat> cheats = EnumSet.noneOf(Cheat.class);

	/**
	 * 
	 */
	private boolean hasCheatedYet = false;

	/**
	 * index of the card in the discard pile
	 */
	private int discardIndex = 51;

	/**
	 * player's overall score
	 */
	private int score = 0;

	/**
	 * current game score
	 */
	private int gameScore = 0;

	/**
	 * session score
	 */
	private int sessionScore = 0;

	/**
	 * streak (number of cards, not the value)
	 */
	private int streak = 0;

	/**
	 * cards remaining in the deck
	 */
	private int remainingCards = 0;

	/**
	 * cards left on the board (not removed into the discard pile)
	 */
	private int cardsInPlay = 0;

	/**
	 * peaks remaining (0 is a clear board)
	 */
	private int remainingPeaks = 3;

	/**
	 * number of player games
	 */
	private int numberOfGames = 0;

	/**
	 * number of session games
	 */
	private int numberOfSessionGames = 0;

	/**
	 * highest score
	 */
	private int highScore = 0;

	/**
	 * lowest score
	 */
	private int lowScore = 0;

	/**
	 * longest streak
	 */
	private int highStreak = 0;

	/**
	 * status text (used later)
	 */
	private String status = "";

	/**
	 * Deck suffling.
	 * 
	 * @author Todor Balabanov
	 */
	private void shuffle() {
		for (int last = deck.length - 1, r = -1; last > 0; last--) {
			r = TriPeaks.PRNG.nextInt(last + 1);
			Card swap = deck[last];
			deck[last] = deck[r];
			deck[r] = swap;
		}
	}

	/**
	 * class constructor
	 */
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
	 * custom paint method
	 */
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
		 * draw the background
		 */
		if (hasCheatedYet) {
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
		 * go through each card
		 */
		for (int q = 0; q < 52; q++) {
			/*
			 * if a card is null (i.e. program was just started, cards not
			 * initialized yet), skip it
			 */
			if (deck[q] == null) {
				continue;
			}

			/*
			 * if a card isn't visible, skip it
			 */
			if (!deck[q].isVisible()) {
				continue;
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
			 * if it's face-up
			 */
			if (!deck[q].isFacingDown()) {
				imgURL = TriPeaks.class.getResource("CardSets" + File.separator
						+ "Fronts" + File.separator + frontFolder
						+ File.separator + deck[q].getSuit()
						+ (deck[q].getRank().getValue() + 1) + ".png");

				/*
				 * get the corresponding front of the card otherwise it's
				 * face-down
				 */
			} else {

				/*
				 * get the image for the back of the card - if the first cheat
				 * isn't on
				 */
				if (cheats.contains(Cheat.CARDS_FACE_UP) == false) {
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
							+ frontFolder + File.separator + deck[q].getSuit()
							+ (deck[q].getRank().getValue() + 1) + ".png");
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
			int startX = deck[q].getX() - ((int) Card.WIDTH / 2);

			/*
			 * top of the card
			 */
			int startY = deck[q].getY() - ((int) Card.HEIGHT / 2);

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
		String scoreStr = (score < 0) ? "Lost $" + (-1) * score : "Won $"
				+ score;

		/*
		 * display how many cards are remaining
		 */
		String remStr = remainingCards
				+ ((remainingCards == 1) ? " card" : " cards") + " remaining";

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
		g.drawString(status, 5, getSize().height - 10);

		/*
		 * reset the status message
		 */
		status = "";
	}

	/**
	 * redeals the cards
	 */
	public void redeal() {
		/*
		 * get the penalty for redealing
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

		shuffle();

		for (Card card : deck) {
			card.setVisible(true);
		}

		// TODO Do it in a single master loop.
		/*
		 * first row
		 */
		for (int q = 0; q < 3; q++) {
			/*
			 * set the X-coord
			 */
			deck[q].setX(2 * Card.WIDTH + q * 3 * Card.WIDTH);

			/*
			 * set the Y-coord for the card
			 */
			deck[q].setY((int) Card.HEIGHT / 2);

			/*
			 * make it face-down
			 */
			deck[q].flip(true);
		}

		/*
		 * second row
		 */
		for (int q = 0; q < 6; q++) {
			/*
			 * set the coords
			 */
			deck[q + 3].setX(3 * ((int) Card.WIDTH / 2) + q * Card.WIDTH
					+ ((int) q / 2) * Card.WIDTH);
			deck[q + 3].setY(Card.HEIGHT);

			/*
			 * face-down
			 */
			deck[q + 3].flip(true);
		}

		/*
		 * third row
		 */
		for (int q = 0; q < 9; q++) {
			/*
			 * set the coords
			 */
			deck[q + 9].setX(Card.WIDTH + q * Card.WIDTH);
			deck[q + 9].setY(3 * ((int) Card.HEIGHT / 2));

			/*
			 * face-down
			 */
			deck[q + 9].flip(true);
		}

		/*
		 * fourth row
		 */
		for (int q = 0; q < 10; q++) {
			/*
			 * set the coords
			 */
			deck[q + 18].setX(((int) Card.WIDTH / 2) + q * Card.WIDTH);
			deck[q + 18].setY(2 * Card.HEIGHT);

			/*
			 * face-up
			 */
			deck[q + 18].flip(false);
		}

		/*
		 * the deck
		 */
		for (int q = 28; q < 51; q++) {
			/*
			 * same coords for all of them
			 */
			deck[q].setX(7 * ((int) Card.WIDTH / 2));
			deck[q].setY(13 * ((int) Card.HEIGHT / 4));

			/*
			 * they're all face-down
			 */
			deck[q].flip(true);

			/*
			 * they're invisible
			 */
			deck[q].setVisible(false);
		}
		/*
		 * only the top one is visible (faster repaint)
		 */
		deck[50].setVisible(true);

		/*
		 * discard pile set the coords
		 */
		deck[51].setX(13 * ((int) Card.WIDTH / 2));
		deck[51].setY(13 * ((int) Card.HEIGHT / 4));

		/*
		 * face-up
		 */
		deck[51].flip(false);

		// TODO Add GameState class for all controlling variables.

		/*
		 * 23 cards left in the deck
		 */
		remainingCards = 23;

		/*
		 * all 28 cards are in play
		 */
		cardsInPlay = 28;

		/*
		 * all three peaks are there
		 */
		remainingPeaks = 3;

		/*
		 * the streak is reset
		 */
		streak = 0;

		/*
		 * the game score is reset
		 */
		gameScore = 0;

		/*
		 * the discard pile index is back to 51
		 */
		discardIndex = 51;

		/*
		 * increment the number of games played
		 */
		numberOfGames++;

		/*
		 * increment the number of session games
		 */
		numberOfSessionGames++;

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

	/**
	 * resets everything
	 */
	public void reset() {
		/*
		 * go through every card make all the cards invisible
		 */
		for (Card card : deck) {
			card.setVisible(false);
		}

		/*
		 * essentially the same thing as the default values for the fields
		 */
		discardIndex = 51;
		score = 0;
		gameScore = 0;
		sessionScore = 0;
		streak = 0;
		remainingCards = 0;
		cardsInPlay = 0;
		remainingPeaks = 3;
		numberOfGames = 0;
		numberOfSessionGames = 0;
		highScore = 0;
		lowScore = 0;
		highStreak = 0;
		status = "";
		cheats.clear();
		hasCheatedYet = false;

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
	 * when the player clicks anywhere on the board
	 * 
	 * @param e
	 */
	public void mouseClicked(MouseEvent e) {
		/*
		 * place holders for the bounds of the card
		 */
		int startX, startY, endX, endY;

		/*
		 * Go through the cards in reverse order - the higher index-cards are on
		 * top. All the skips make execution of the mouse-click faster.
		 */
		for (int q = 51; q >= 0; q--) {
			/*
			 * if the card is invisible, skip it
			 */
			if (deck[q].isVisible() == false) {
				continue;
			}

			/*
			 * if the card isn't part of the deck and is face-down, skip it
			 */
			if (((q < 28) || (q == 51)) && deck[q].isFacingDown() == true) {
				continue;
			}

			/*
			 * if the card is in the discard pile, skip it
			 */
			if (q == discardIndex) {
				continue;
			}

			/*
			 * left edge of the card
			 */
			startX = deck[q].getX() - ((int) Card.WIDTH / 2);

			/*
			 * top edge of the card
			 */
			startY = deck[q].getY() - ((int) Card.HEIGHT / 2);

			/*
			 * right edge of the card
			 */
			endX = deck[q].getX() + ((int) Card.WIDTH / 2);

			/*
			 * bottom edge of the card
			 */
			endY = deck[q].getY() + ((int) Card.HEIGHT / 2);

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
			if (cheats.contains(Cheat.CLICK_ANY_CARD) == true) {
				/*
				 * the card is adjacent automatically
				 */
				isAdjacent = true;
			} else {
				/*
				 * no cheat - check card check if the card is adjacent by value
				 */
				isAdjacent = deck[q].getRank().isAdjacentTo(
						deck[discardIndex].getRank());
			}

			/*
			 * if the card isn't in the deck and is adjacent to the last
			 * discarded card
			 */
			if (q < 28 && isAdjacent == true) {
				/*
				 * put the card in the discard pile
				 */
				deck[q].setX(deck[discardIndex].getX());

				/*
				 * set the discard pile's card's coords
				 */
				deck[q].setY(deck[discardIndex].getY());

				/*
				 * hide the previously discarded card - makes the repaint faster
				 */
				deck[discardIndex].setVisible(false);

				/*
				 * the card is now in the discard pile
				 */
				discardIndex = q;

				/*
				 * increment the strea
				 */
				streak++;

				/*
				 * decrement the number of cards in play
				 */
				cardsInPlay--;

				/*
				 * add the streak to the score
				 */
				score += streak;

				/*
				 * and to the current game's score
				 */
				gameScore += streak;

				/*
				 * and to the session score
				 */
				sessionScore += streak;

				/*
				 * set the high streak if it's higher
				 */
				if (streak > highStreak) {
					highStreak = streak;
				}

				/*
				 * set the high score if it's higher
				 */
				if (gameScore > highScore) {
					highScore = gameScore;
				}

				/*
				 * if it was a peak
				 */
				if (q < 3) {
					/*
					 * there's one less peak
					 */
					remainingPeaks--;

					/*
					 * add a 15-point bonus
					 */
					score += Constants.PEAK_BONUS;

					/*
					 * and to the game score
					 */
					gameScore += Constants.PEAK_BONUS;

					/*
					 * and to the session score
					 */
					sessionScore += Constants.PEAK_BONUS;

					/*
					 * if all the peaks are gone
					 */
					if (remainingPeaks == 0) {
						/*
						 * add another 15-point bonus (for a total of 30 bonus
						 * points)
						 */
						score += Constants.THREE_PEAKS_BONUS;

						/*
						 * and to the game score
						 */
						gameScore += Constants.THREE_PEAKS_BONUS;

						/*
						 * and to the session score
						 */
						sessionScore += Constants.THREE_PEAKS_BONUS;

						/*
						 * set the status message
						 */
						status = "You have Tri-Conquered! You get a bonus of $30";

						/*
						 * the remaining deck
						 */
						for (int w = 28; w < (remainingCards + 28); w++) {
							/*
							 * hide the deck (so you can't take cards from the
							 * deck after you clear the board
							 */
							deck[w].setVisible(false);
						}
					} else {
						/*
						 * set the status message
						 */
						status = "You have reached a peak! You get a bonus of $15";
					}

					/*
					 * set the high score if the score is higher
					 */
					if (gameScore > highScore) {
						highScore = gameScore;
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
					if (!deck[q - 1].isVisible()) {
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
					if (!deck[q + 1].isVisible()) {
						noRight = true;
					}
				}

				/*
				 * some of the cards in the third row are considered to be edge
				 * cards because not all pairs of adjacent cards in the third
				 * row uncover another card
				 */
				if ((!noLeft) && (!noRight)) {
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
					deck[q - offset].flip();
				}

				/*
				 * if the right card is missing, use the missing card as the
				 * right one
				 */
				if (noRight) {
					deck[q - offset + 1].flip();
				}

				/*
				 * in the deck move the card to the deck
				 */
			} else if ((q >= 28) && (q < 51)) {

				/*
				 * set the deck's coordinates
				 */
				deck[q].setX(deck[discardIndex].getX());
				deck[q].setY(deck[discardIndex].getY());

				/*
				 * hide the previously discarded card (for faster repaint)
				 */
				deck[discardIndex].setVisible(false);

				/*
				 * flip the deck card
				 */
				deck[q].flip();

				/*
				 * show the next deck card if it's not the last deck card
				 */
				if (q != 28) {
					deck[q - 1].setVisible(true);
				}

				/*
				 * set the index of the dicard pile
				 */
				discardIndex = q;

				/*
				 * reset the streak
				 */
				streak = 0;

				/*
				 * if the third cheat isn't on (no penalty cheat)
				 */
				if (cheats.contains(Cheat.NO_PENALTY) == false) {
					// TODO Call doPenalty() function.

					/*
					 * 5-point penalty
					 */
					score -= Constants.NO_PENALTY_CHEAT;

					/*
					 * to the game score
					 */
					gameScore -= Constants.NO_PENALTY_CHEAT;

					/*
					 * and the session score
					 */
					sessionScore -= Constants.NO_PENALTY_CHEAT;
				}

				/*
				 * set the low score if score is lower
				 */
				if (gameScore < lowScore) {
					lowScore = gameScore;
				}

				/*
				 * decrement the number of cards in the deck
				 */
				remainingCards--;
			}

			/*
			 * "consume" the click - don't go through the rest of the cards
			 */
			break;
		}

		/*
		 * repaint the board
		 */
		repaint();

		/*
		 * get the containing frame
		 */
		TriPeaks theFrame = (TriPeaks) SwingUtilities.windowForComponent(this);

		/*
		 * update the stats labels
		 */
		theFrame.updateStats();
	}

	/**
	 * return the penalty
	 * 
	 * @return
	 */
	public int getPenalty() {
		/*
		 * if the penalty cheat is on, there is no penalty
		 */
		if (cheats.contains(Cheat.NO_PENALTY) == true) {
			return 0;
		}

		/*
		 * if there are cards in the deck AND in play, the penalty is $5 for
		 * every card removed
		 */
		if ((cardsInPlay != 0) && (remainingCards != 0)) {
			return (cardsInPlay * Constants.CARD_REMOVED_PENALTY);
		} else {
			/*
			 * otherwise the penalty is 0
			 */
			return 0;
		}
	}

	/**
	 * perform the penalty - penalty doesn't affect the low score
	 * 
	 * @param penalty
	 */
	public void doPenalty(int penalty) {
		/*
		 * subtract the penalty
		 */
		score -= penalty;

		/*
		 * from the session score
		 */
		sessionScore -= penalty;

		/*
		 * and from the game score
		 */
		gameScore -= penalty;
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
	 * returns the background color
	 * 
	 * @return
	 */
	public Color getBackColor() {
		return backColor;
	}

	/**
	 * returns the player's overall score
	 * 
	 * @return
	 */
	public int getScore() {
		return score;
	}

	/**
	 * returns the current game score
	 * 
	 * @return
	 */
	public int getGameScore() {
		return gameScore;
	}

	/**
	 * returns the current streak
	 * 
	 * @return
	 */
	public int getStreak() {
		return streak;
	}

	/**
	 * returns the number of games played
	 * 
	 * @return
	 */
	public int getNumGames() {
		return numberOfGames;
	}

	/**
	 * returns the high score
	 * 
	 * @return
	 */
	public int getHighScore() {
		return highScore;
	}

	/**
	 * returns the low score
	 * 
	 * @return
	 */
	public int getLowScore() {
		return lowScore;
	}

	/**
	 * returns the longest streak
	 * 
	 * @return
	 */
	public int getHighStreak() {
		return highStreak;
	}

	/**
	 * returns the session score
	 * 
	 * @return
	 */
	public int getSessionScore() {
		return sessionScore;
	}

	/**
	 * returns the number of session games
	 * 
	 * @return
	 */
	public int getSessionGames() {
		return numberOfSessionGames;
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
	 * returns all the stats in an array
	 * 
	 * @return
	 */
	public int[] getAllStats() {
		/*
		 * the array of stats
		 */
		int[] retVal = { getScore(), getGameScore(), getSessionScore(),
				getStreak(), getNumGames(), getSessionGames(), getHighScore(),
				getLowScore(), getHighStreak() };

		return retVal;
	}

	/**
	 * check if the player is currently cheating
	 * 
	 * @return
	 */
	public boolean isCheating() {
		return (cheats.isEmpty());
	}

	/**
	 * checks if player has ever cheated
	 * 
	 * @return
	 */
	public boolean hasCheated() {
		return hasCheatedYet;
	}

	/**
	 * returns all the cheats
	 * 
	 * @return
	 */
	public EnumSet<Cheat> getCheats() {
		/*
		 * return the cheats array
		 */
		return cheats;
	}

	/**
	 * sets all the stats based on the array values
	 * 
	 * @param stats
	 */
	public void setStats(int[] stats) {
		/*
		 * the programmer knows the order of the stats to be passed into this
		 * method:
		 */
		score = stats[0];

		/*
		 * overall score, high score, low score, number ofgames, and longest
		 * streak
		 */
		highScore = stats[1];
		lowScore = stats[2];
		numberOfGames = stats[3];
		highStreak = stats[4];
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
	 * sets the back style
	 * 
	 * @param back
	 */
	public void setCardBack(String back) {
		backStyle = back;
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
	 * set a cheat with the given index
	 * 
	 * @param cheat
	 * @param newState
	 */
	public void setCheat(Cheat cheat, boolean newState) {
		if (cheats.contains(cheat) == false && newState == true) {
			cheats.add(cheat);
		} else if (cheats.contains(cheat) == true && newState == false) {
			cheats.remove(cheat);
		}

		/*
		 * if the cheat is turned on, set the "has cheated" flag
		 */
		if (newState == true) {
			hasCheatedYet = true;
		}
	}

	/**
	 * set all the cheats in a given array
	 * 
	 * @param newCheats
	 */
	public void setCheats(EnumSet<Cheat> newCheats) {
		cheats.clear();
		cheats.addAll(newCheats);
	}

	/**
	 * set the cheated status for the player.
	 * 
	 * @param hasCheatedYet
	 */
	public void setCheated(boolean hasCheatedYet) {
		this.hasCheatedYet = hasCheatedYet;
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

	/**
	 * not used, but necessary to implement MouseListener
	 */
	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * 
	 */
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * 
	 */
	public void mousePressed(MouseEvent e) {
	}

	/**
	 * 
	 */
	public void mouseReleased(MouseEvent e) {
	}
}
