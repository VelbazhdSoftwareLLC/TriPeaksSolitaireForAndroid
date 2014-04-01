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
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class CardPanel extends JPanel implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Color backColor = (Color.GREEN).darker().darker(); // background
																// color of the
																// board
	private Color fontColor = Color.WHITE;
	private Font textFont = new Font("Serif", Font.BOLD, 14);
	public Card[] theCards = new Card[52]; // array with the cards
	public static final int NSTATS = 5;
	public static final int NCHEATS = 3;
	private boolean[] cheats = new boolean[NCHEATS];
	private boolean hasCheatedYet = false;
	private int disIndex = 51; // index of the card in the discard pile
	private int score = 0; // player's overall score
	private int gameScore = 0; // current game score
	private int sesScore = 0; // session score
	private int streak = 0; // streak (number of cards, not the value)
	private int remCards = 0; // cards remaining in the deck
	private int cardsInPlay = 0; // cards left on the board (not removed into
									// the discard pile)
	private int remPeaks = 3; // peaks remaining (0 is a clear board)
	private int numGames = 0; // number of player games
	private int sesGames = 0; // number of session games
	private int highScore = 0; // highest score
	private int lowScore = 0; // lowest score
	private int highStreak = 0; // longest strea
	private String status = ""; // status text (used later)
	private String frontFolder = "Default"; // folder in which the fronts of the
											// cards are stored
	private String backStyle = "Default"; // style for the back of the cards

	public CardPanel() { // class constructor
		// initialize all the cards
		theCards[0] = new Card(Card.Rank.ACE, Card.Suit.CLUBS, true, true, 0, 0);
		theCards[1] = new Card(Card.Rank.TWO, Card.Suit.CLUBS, true, true, 0, 0);
		theCards[2] = new Card(Card.Rank.THREE, Card.Suit.CLUBS, true, true, 0, 0);
		theCards[3] = new Card(Card.Rank.FOUR, Card.Suit.CLUBS, true, true, 0, 0);
		theCards[4] = new Card(Card.Rank.FIVE, Card.Suit.CLUBS, true, true, 0, 0);
		theCards[5] = new Card(Card.Rank.SIX, Card.Suit.CLUBS, true, true, 0, 0);
		theCards[6] = new Card(Card.Rank.SEVEN, Card.Suit.CLUBS, true, true, 0, 0);
		theCards[7] = new Card(Card.Rank.EIGHT, Card.Suit.CLUBS, true, true, 0, 0);
		theCards[8] = new Card(Card.Rank.NINE, Card.Suit.CLUBS, true, true, 0, 0);
		theCards[9] = new Card(Card.Rank.TEN, Card.Suit.CLUBS, true, true, 0, 0);
		theCards[10] = new Card(Card.Rank.JACK, Card.Suit.CLUBS, true, true, 0, 0);
		theCards[11] = new Card(Card.Rank.QUEEN, Card.Suit.CLUBS, true, true, 0, 0);
		theCards[12] = new Card(Card.Rank.KING, Card.Suit.CLUBS, true, true, 0, 0);
		theCards[13] = new Card(Card.Rank.ACE, Card.Suit.HEARTS, true, true, 0, 0);
		theCards[14] = new Card(Card.Rank.TWO, Card.Suit.HEARTS, true, true, 0, 0);
		theCards[15] = new Card(Card.Rank.THREE, Card.Suit.HEARTS, true, true, 0, 0);
		theCards[16] = new Card(Card.Rank.FOUR, Card.Suit.HEARTS, true, true, 0, 0);
		theCards[17] = new Card(Card.Rank.FIVE, Card.Suit.HEARTS, true, true, 0, 0);
		theCards[18] = new Card(Card.Rank.SIX, Card.Suit.HEARTS, true, true, 0, 0);
		theCards[19] = new Card(Card.Rank.SEVEN, Card.Suit.HEARTS, true, true, 0, 0);
		theCards[20] = new Card(Card.Rank.EIGHT, Card.Suit.HEARTS, true, true, 0, 0);
		theCards[21] = new Card(Card.Rank.NINE, Card.Suit.HEARTS, true, true, 0, 0);
		theCards[22] = new Card(Card.Rank.TEN, Card.Suit.HEARTS, true, true, 0, 0);
		theCards[23] = new Card(Card.Rank.JACK, Card.Suit.HEARTS, true, true, 0, 0);
		theCards[24] = new Card(Card.Rank.QUEEN, Card.Suit.HEARTS, true, true, 0, 0);
		theCards[25] = new Card(Card.Rank.KING, Card.Suit.HEARTS, true, true, 0, 0);
		theCards[26] = new Card(Card.Rank.ACE, Card.Suit.DIAMONDS, true, true, 0, 0);
		theCards[27] = new Card(Card.Rank.TWO, Card.Suit.DIAMONDS, true, true, 0, 0);
		theCards[28] = new Card(Card.Rank.THREE, Card.Suit.DIAMONDS, true, true, 0, 0);
		theCards[29] = new Card(Card.Rank.FOUR, Card.Suit.DIAMONDS, true, true, 0, 0);
		theCards[30] = new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS, true, true, 0, 0);
		theCards[31] = new Card(Card.Rank.SIX, Card.Suit.DIAMONDS, true, true, 0, 0);
		theCards[32] = new Card(Card.Rank.SEVEN, Card.Suit.DIAMONDS, true, true, 0, 0);
		theCards[33] = new Card(Card.Rank.EIGHT, Card.Suit.DIAMONDS, true, true, 0, 0);
		theCards[34] = new Card(Card.Rank.NINE, Card.Suit.DIAMONDS, true, true, 0, 0);
		theCards[35] = new Card(Card.Rank.TEN, Card.Suit.DIAMONDS, true, true, 0, 0);
		theCards[36] = new Card(Card.Rank.JACK, Card.Suit.DIAMONDS, true, true, 0, 0);
		theCards[37] = new Card(Card.Rank.QUEEN, Card.Suit.DIAMONDS, true, true, 0, 0);
		theCards[38] = new Card(Card.Rank.KING, Card.Suit.DIAMONDS, true, true, 0, 0);
		theCards[39] = new Card(Card.Rank.ACE, Card.Suit.SPADES, true, true, 0, 0);
		theCards[40] = new Card(Card.Rank.TWO, Card.Suit.SPADES, true, true, 0, 0);
		theCards[41] = new Card(Card.Rank.THREE, Card.Suit.SPADES, true, true, 0, 0);
		theCards[42] = new Card(Card.Rank.FOUR, Card.Suit.SPADES, true, true, 0, 0);
		theCards[43] = new Card(Card.Rank.FIVE, Card.Suit.SPADES, true, true, 0, 0);
		theCards[44] = new Card(Card.Rank.SIX, Card.Suit.SPADES, true, true, 0, 0);
		theCards[45] = new Card(Card.Rank.SEVEN, Card.Suit.SPADES, true, true, 0, 0);
		theCards[46] = new Card(Card.Rank.EIGHT, Card.Suit.SPADES, true, true, 0, 0);
		theCards[47] = new Card(Card.Rank.NINE, Card.Suit.SPADES, true, true, 0, 0);
		theCards[48] = new Card(Card.Rank.TEN, Card.Suit.SPADES, true, true, 0, 0);
		theCards[49] = new Card(Card.Rank.JACK, Card.Suit.SPADES, true, true, 0, 0);
		theCards[50] = new Card(Card.Rank.QUEEN, Card.Suit.SPADES, true, true, 0, 0);
		theCards[51] = new Card(Card.Rank.KING, Card.Suit.SPADES, true, true, 0, 0);

		// create a new
		// Card object -
		// random values
		// - so it
		// doesn't throw
		// NullPointerException...
		for (int q = 0; q < 52; q++) { 
			theCards[q].setVisible(false);
		}
		setPreferredSize(new Dimension(Card.WIDTH * 10, Card.HEIGHT * 4)); // sets
																			// the
																			// size
																			// of
																			// the
																			// panel
																			// (10
																			// cards
																			// by
																			// 4
																			// cards)
		addMouseListener(this); // adds a mouse-listener to the board
	}

	public void paint(Graphics g) { // custom paint method
		super.paintComponent(g); // paints the JPanel
		g.setColor(backColor); // use the background color
		g.fillRect(0, 0, getSize().width, getSize().height); // draw the
																// background
		if (hasCheatedYet) { // if the user has ever cheated
			g.setColor(new Color(fontColor.getRed(), fontColor.getGreen(),
					fontColor.getBlue(), 80)); // set the color - white,
												// somewhat transparent
			g.setFont(new Font("SansSerif", Font.BOLD, 132)); // set the font -
																// big and fat
			g.drawString("CHEATER", 0, getSize().height - 5); // print "CHEATER"
																// on the bottom
																// edge of the
																// board
		}

		for (int q = 0; q < 52; q++) { // go through each card
			if (theCards[q] == null)
				continue; // if a card is null (i.e. program was just started,
							// cards not initialized yet), skip it
			if (!theCards[q].isVisible())
				continue; // if a card isn't visible, skip it
			BufferedImage img = null; // image to be created
			URL imgURL = null; // URL of the image

			if (!theCards[q].isFacingDown()) // if it's face-up
				imgURL = TriPeaks.class.getResource("CardSets" + File.separator
						+ "Fronts" + File.separator + frontFolder
						+ File.separator + theCards[q].getSuit()
						+ (theCards[q].getRank().getValue() + 1) + ".png"); // get the
																	// corresponding
																	// front of
																	// the card
			else {// otherwise it's face-down
				if (!cheats[0])
					imgURL = TriPeaks.class.getResource("CardSets"
							+ File.separator + "Backs" + File.separator
							+ backStyle + ".png"); // get the image for the back
													// of the card - if the
													// first cheat isn't on
				else
					imgURL = TriPeaks.class.getResource("CardSets"
							+ File.separator + "Fronts" + File.separator
							+ frontFolder + File.separator
							+ theCards[q].getSuit()
							+ (theCards[q].getRank().getValue() + 1) + ".png"); // get the
																		// corresponding
																		// front
																		// of
																		// the
																		// card
																		// if
																		// the
																		// cheat
																		// is
																		// on...
			}
			if (imgURL == null)
				continue;
			try {
				img = ImageIO.read(imgURL); // try to read the image
			} catch (IOException eIO) {
				System.out.println("Error reading card image"); // There's an
																// error
																// (probably
																// because the
																// card doesn't
																// exist.
			}
			if (img == null)
				continue;
			int startX = theCards[q].getX() - ((int) Card.WIDTH / 2); // left
																		// edge
																		// of
																		// the
																		// laft
			int startY = theCards[q].getY() - ((int) Card.HEIGHT / 2); // top of
																		// the
																		// card
			int endX = startX + Card.WIDTH; // right
			int endY = startY + Card.HEIGHT; // bottom
			g.drawImage(img, startX, startY, endX, endY, 0, 0,
					img.getWidth(null), img.getHeight(null), null); // draws the
																	// image on
																	// the panel
																	// -
																	// resizing/scaling
																	// if
																	// necessary
		}
		String scoreStr = (score < 0) ? "Lost $" + (-1) * score : "Won $"
				+ score; // The won/lost string
		String remStr = remCards + ((remCards == 1) ? " card" : " cards")
				+ " remaining"; // display how many cards are remaining
		g.setColor(fontColor); // the text is white
		g.setFont(textFont); // set the font for the text
		g.drawString(scoreStr, 5, Card.HEIGHT * 3); // put the score on the
													// panel
		g.drawString(remStr, 5, Card.HEIGHT * 3 + 25); // put the remaining
														// cards on the panel
		g.drawString(status, 5, getSize().height - 10); // print the status
														// message.
		status = ""; // reset the status message
	}

	public void redeal() { // redeals the cards
		int penalty = getPenalty(); // get the penalty for redealing
		if (penalty != 0) { // if there is a penalty
			int uI = JOptionPane.showConfirmDialog(this,
					"Are you sure you want to redeal?\nRedealing now results in a penalty of $"
							+ penalty + "!", "Confirm Redeal",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE); // show
																				// a
																				// confimation
																				// message
			if (uI == JOptionPane.YES_OPTION)
				doPenalty(penalty); // do the penalty if the user agreed
			else
				return; // the user doesn't like the penalty, don't rededal
		}
		int[] cards = randomize(); // randomize the cards
		theCards[cards[0]] = new Card(Card.Rank.ACE, Card.Suit.CLUBS, true, true, 0, 0);
		theCards[cards[1]] = new Card(Card.Rank.TWO, Card.Suit.CLUBS, true, true, 0, 0);
		theCards[cards[2]] = new Card(Card.Rank.THREE, Card.Suit.CLUBS, true, true, 0, 0);
		theCards[cards[3]] = new Card(Card.Rank.FOUR, Card.Suit.CLUBS, true, true, 0, 0);
		theCards[cards[4]] = new Card(Card.Rank.FIVE, Card.Suit.CLUBS, true, true, 0, 0);
		theCards[cards[5]] = new Card(Card.Rank.SIX, Card.Suit.CLUBS, true, true, 0, 0);
		theCards[cards[6]] = new Card(Card.Rank.SEVEN, Card.Suit.CLUBS, true, true, 0, 0);
		theCards[cards[7]] = new Card(Card.Rank.EIGHT, Card.Suit.CLUBS, true, true, 0, 0);
		theCards[cards[8]] = new Card(Card.Rank.NINE, Card.Suit.CLUBS, true, true, 0, 0);
		theCards[cards[9]] = new Card(Card.Rank.TEN, Card.Suit.CLUBS, true, true, 0, 0);
		theCards[cards[10]] = new Card(Card.Rank.JACK, Card.Suit.CLUBS, true, true, 0, 0);
		theCards[cards[11]] = new Card(Card.Rank.QUEEN, Card.Suit.CLUBS, true, true, 0, 0);
		theCards[cards[12]] = new Card(Card.Rank.KING, Card.Suit.CLUBS, true, true, 0, 0);
		theCards[cards[13]] = new Card(Card.Rank.ACE, Card.Suit.HEARTS, true, true, 0, 0);
		theCards[cards[14]] = new Card(Card.Rank.TWO, Card.Suit.HEARTS, true, true, 0, 0);
		theCards[cards[15]] = new Card(Card.Rank.THREE, Card.Suit.HEARTS, true, true, 0, 0);
		theCards[cards[16]] = new Card(Card.Rank.FOUR, Card.Suit.HEARTS, true, true, 0, 0);
		theCards[cards[17]] = new Card(Card.Rank.FIVE, Card.Suit.HEARTS, true, true, 0, 0);
		theCards[cards[18]] = new Card(Card.Rank.SIX, Card.Suit.HEARTS, true, true, 0, 0);
		theCards[cards[19]] = new Card(Card.Rank.SEVEN, Card.Suit.HEARTS, true, true, 0, 0);
		theCards[cards[20]] = new Card(Card.Rank.EIGHT, Card.Suit.HEARTS, true, true, 0, 0);
		theCards[cards[21]] = new Card(Card.Rank.NINE, Card.Suit.HEARTS, true, true, 0, 0);
		theCards[cards[22]] = new Card(Card.Rank.TEN, Card.Suit.HEARTS, true, true, 0, 0);
		theCards[cards[23]] = new Card(Card.Rank.JACK, Card.Suit.HEARTS, true, true, 0, 0);
		theCards[cards[24]] = new Card(Card.Rank.QUEEN, Card.Suit.HEARTS, true, true, 0, 0);
		theCards[cards[25]] = new Card(Card.Rank.KING, Card.Suit.HEARTS, true, true, 0, 0);
		theCards[cards[26]] = new Card(Card.Rank.ACE, Card.Suit.DIAMONDS, true, true, 0, 0);
		theCards[cards[27]] = new Card(Card.Rank.TWO, Card.Suit.DIAMONDS, true, true, 0, 0);
		theCards[cards[28]] = new Card(Card.Rank.THREE, Card.Suit.DIAMONDS, true, true, 0, 0);
		theCards[cards[29]] = new Card(Card.Rank.FOUR, Card.Suit.DIAMONDS, true, true, 0, 0);
		theCards[cards[30]] = new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS, true, true, 0, 0);
		theCards[cards[31]] = new Card(Card.Rank.SIX, Card.Suit.DIAMONDS, true, true, 0, 0);
		theCards[cards[32]] = new Card(Card.Rank.SEVEN, Card.Suit.DIAMONDS, true, true, 0, 0);
		theCards[cards[33]] = new Card(Card.Rank.EIGHT, Card.Suit.DIAMONDS, true, true, 0, 0);
		theCards[cards[34]] = new Card(Card.Rank.NINE, Card.Suit.DIAMONDS, true, true, 0, 0);
		theCards[cards[35]] = new Card(Card.Rank.TEN, Card.Suit.DIAMONDS, true, true, 0, 0);
		theCards[cards[36]] = new Card(Card.Rank.JACK, Card.Suit.DIAMONDS, true, true, 0, 0);
		theCards[cards[37]] = new Card(Card.Rank.QUEEN, Card.Suit.DIAMONDS, true, true, 0, 0);
		theCards[cards[38]] = new Card(Card.Rank.KING, Card.Suit.DIAMONDS, true, true, 0, 0);
		theCards[cards[39]] = new Card(Card.Rank.ACE, Card.Suit.SPADES, true, true, 0, 0);
		theCards[cards[40]] = new Card(Card.Rank.TWO, Card.Suit.SPADES, true, true, 0, 0);
		theCards[cards[41]] = new Card(Card.Rank.THREE, Card.Suit.SPADES, true, true, 0, 0);
		theCards[cards[42]] = new Card(Card.Rank.FOUR, Card.Suit.SPADES, true, true, 0, 0);
		theCards[cards[43]] = new Card(Card.Rank.FIVE, Card.Suit.SPADES, true, true, 0, 0);
		theCards[cards[44]] = new Card(Card.Rank.SIX, Card.Suit.SPADES, true, true, 0, 0);
		theCards[cards[45]] = new Card(Card.Rank.SEVEN, Card.Suit.SPADES, true, true, 0, 0);
		theCards[cards[46]] = new Card(Card.Rank.EIGHT, Card.Suit.SPADES, true, true, 0, 0);
		theCards[cards[47]] = new Card(Card.Rank.NINE, Card.Suit.SPADES, true, true, 0, 0);
		theCards[cards[48]] = new Card(Card.Rank.TEN, Card.Suit.SPADES, true, true, 0, 0);
		theCards[cards[49]] = new Card(Card.Rank.JACK, Card.Suit.SPADES, true, true, 0, 0);
		theCards[cards[50]] = new Card(Card.Rank.QUEEN, Card.Suit.SPADES, true, true, 0, 0);
		theCards[cards[51]] = new Card(Card.Rank.KING, Card.Suit.SPADES, true, true, 0, 0);

		for (int q = 0; q < 3; q++) { // first row
			theCards[q].setX(2 * Card.WIDTH + q * 3 * Card.WIDTH); // set the
																	// X-coord
			theCards[q].setY((int) Card.HEIGHT / 2); // set the Y-coord for the
														// card
			theCards[q].flip(true); // make it face-down
		}
		for (int q = 0; q < 6; q++) { // second row
			theCards[q + 3].setX(3 * ((int) Card.WIDTH / 2) + q * Card.WIDTH
					+ ((int) q / 2) * Card.WIDTH); // set the coords
			theCards[q + 3].setY(Card.HEIGHT);
			theCards[q + 3].flip(true); // face-down
		}
		for (int q = 0; q < 9; q++) { // third row
			theCards[q + 9].setX(Card.WIDTH + q * Card.WIDTH); // set the coords
			theCards[q + 9].setY(3 * ((int) Card.HEIGHT / 2));
			theCards[q + 9].flip(true); // face-down
		}
		for (int q = 0; q < 10; q++) { // fourth row
			theCards[q + 18].setX(((int) Card.WIDTH / 2) + q * Card.WIDTH); // set
																			// the
																			// coords
			theCards[q + 18].setY(2 * Card.HEIGHT);
			theCards[q + 18].flip(false); // face-up
		}
		for (int q = 28; q < 51; q++) { // the deck
			theCards[q].setX(7 * ((int) Card.WIDTH / 2)); // same coords for all
															// of them
			theCards[q].setY(13 * ((int) Card.HEIGHT / 4));
			theCards[q].flip(true); // they're all face-down
			theCards[q].setVisible(false); // they're invisible
		}
		theCards[50].setVisible(true); // only the top one is visible (faster
										// repaint)

		theCards[51].setX(13 * ((int) Card.WIDTH / 2)); // discard pile
		theCards[51].setY(13 * ((int) Card.HEIGHT / 4)); // set the coords
		theCards[51].flip(false); // face-up

		remCards = 23; // 23 cards left in the deck
		cardsInPlay = 28; // all 28 cards are in play
		remPeaks = 3; // all three peaks are there
		streak = 0; // the streak is reset
		gameScore = 0; // the game score is reset
		disIndex = 51; // the discard pile index is back to 51
		numGames++; // increment the number of games played
		sesGames++; // increment the number of session games

		repaint(); // repaint the board
		TriPeaks theFrame = (TriPeaks) SwingUtilities.windowForComponent(this); // get
																				// the
																				// frame
																				// that
																				// contains
																				// the
																				// board
		theFrame.updateStats(); // update the stats labels
	}

	public void reset() { // resets everything
		for (int q = 0; q < 52; q++) { // go through every card
			theCards[q].setVisible(false); // make all the cards invisible
		}
		disIndex = 51; // essentially the same thing as the default values for
						// the fields
		score = 0;
		gameScore = 0;
		sesScore = 0;
		streak = 0;
		remCards = 0;
		cardsInPlay = 0;
		remPeaks = 3;
		numGames = 0;
		sesGames = 0;
		highScore = 0;
		lowScore = 0;
		highStreak = 0;
		status = "";
		cheats = new boolean[cheats.length];
		hasCheatedYet = false;

		repaint(); // repaint the board
		TriPeaks theFrame = (TriPeaks) SwingUtilities.windowForComponent(this); // get
																				// the
																				// frame
		theFrame.updateStats(); // update the stats labels
	}

	public int[] randomize() { // randomizes an array - we're working with a
								// 52-element array, so it puts the numbers 0-51
								// in random order
		int[] retVal = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51}; // the array for the numbers
		boolean[] check = new boolean[52]; // the checking array - which numbers
											// have been used
		int[] pass; // array to pick the random item from
		ArrayList<Integer> passList; // an ArrayList of Integers for the
										// selection of possible values
		for (int q = 0; q < 52; q++) { // walk through the array, setting values
										// for each of them
			passList = new ArrayList<Integer>(); // re-initialize the arraylist
													// every time
			for (int r = 0; r < 52; r++) { // walk through the possible numbers
				if (!check[r])
					passList.add(r); // if the number hasn't been used, add it
										// to the ArrayList
			}
			pass = new int[passList.size()]; // create a new array to pass the
												// elements into another method
			int w = 0; // index
			for (Iterator<Integer> it = passList.iterator(); it.hasNext();)
				pass[w++] = it.next().intValue(); // have an iterator get every
													// element in the list and
													// put it in the array
			retVal[q] = randItem(pass); // select a random item from the array
										// and set the value
			check[retVal[q]] = true; // flag the value as used
		}
		return retVal; // return the randomized array
	}

	private int randItem(int[] list) { // selects a random item from an array
		if (list.length == 0)
			return -1; // if the array is empty, return a -1 (usually
						// unfavorable)
		int dim = list.length; // upper bound of the random integer
		int randIndex = (int) (dim * Math.random()); // generate a random index
														// for the element
		return list[randIndex]; // return the random value
	}

	public void mouseClicked(MouseEvent e) { // when the player clicks anywhere
												// on the board
		int startX, startY, endX, endY; // placeholders for the bounds of the
										// card
		for (int q = 51; q >= 0; q--) { // go through the cards in reverse order
										// - the higher index-cards are on top
			if (theCards[q] == null)
				continue; // if the card hasn't been initialized, skip it
			if (!theCards[q].isVisible())
				continue; // if the card is invisible, skip it
			if (((q < 28) || (q == 51)) && (theCards[q].isFacingDown()))
				continue; // if the card isn't part of the deck and is
							// face-down, skip it
			if (q == disIndex)
				continue; // if the card is in the discard pile, skip it
			// all the skips make execution of the mouse-click faster
			startX = theCards[q].getX() - ((int) Card.WIDTH / 2); // left edge
																	// of the
																	// card
			startY = theCards[q].getY() - ((int) Card.HEIGHT / 2); // top edge
																	// of the
																	// card
			endX = theCards[q].getX() + ((int) Card.WIDTH / 2); // right edge of
																// the card
			endY = theCards[q].getY() + ((int) Card.HEIGHT / 2); // bottom edge
																	// of the
																	// card
			if ((startX > e.getX()) || (endX < e.getX()) || (startY > e.getY())
					|| (endY < e.getY()))
				continue; // if the mouse was clicked outside the card, skip the
							// rest
			boolean isAdjacent; // a value to check if the card is adjacent by
								// value
			if (cheats[1]) { // if the second cheat is used, the value of the
								// card won't be checked
				isAdjacent = true; // the card is adjacent automatically
			} else { // no cheat - check card
				isAdjacent = theCards[q].getRank().isAdjacentTo(theCards[disIndex].getRank()); // check
																			// if
																			// the
																			// card
																			// is
																			// adjacent
																			// by
																			// value
			}
			if ((q < 28) && isAdjacent) { // if the card isn't in the deck and
											// is adjacent to the last discarded
											// card
				theCards[q].setX(theCards[disIndex].getX()); // put the card in
																// the discard
																// pile
				theCards[q].setY(theCards[disIndex].getY()); // set the discard
																// pile's card's
																// coords
				theCards[disIndex].setVisible(false); // hide the previously
														// discarded card -
														// makes the repaint
														// faster
				disIndex = q; // the card is now in the discard pile

				streak++; // increment the strea
				cardsInPlay--; // decrement the number of cards in play
				score += streak; // add the streak to the score
				gameScore += streak; // and to the current game's score
				sesScore += streak; // and to the session score
				if (streak > highStreak)
					highStreak = streak; // set the high streak if it's higher
				if (gameScore > highScore)
					highScore = gameScore; // set the high score if it's higher

				if (q < 3) { // if it was a peak
					remPeaks--; // there's one less peak
					score += 15; // add a 15-point bonus
					gameScore += 15; // and to the game score
					sesScore += 15; // and to the session score
					if (remPeaks == 0) { // if all the peaks are gone
						score += 15; // add another 15-point bonus (for a total
										// of 30 bonus points)
						gameScore += 15; // and to the game score
						sesScore += 15; // and to the session score
						status = "You have Tri-Conquered! You get a bonus of $30"; // set
																					// the
																					// status
																					// message
						for (int w = 28; w < (remCards + 28); w++) { // the
																		// remaining
																		// deck
							theCards[w].setVisible(false); // hide the deck (so
															// you can't take
															// cards from the
															// deck after you
															// clear the board
						}
					} else
						status = "You have reached a peak! You get a bonus of $15"; // set
																					// the
																					// status
																					// message

					if (gameScore > highScore)
						highScore = gameScore; // set the high score if the
												// score is higher
					break; // "consume" the mouse click - don't go through the
							// rest of the cards
				}
				boolean noLeft, noRight; // check values for checking whether or
											// not a card has a card to the left
											// or right
				noLeft = noRight = false; // starts out as having both
				if ((q != 3) && (q != 9) && (q != 18) && (q != 5) && (q != 7)
						&& (q != 12) && (q != 15)) { // if the card isn't a left
														// end
					if (!theCards[q - 1].isVisible())
						noLeft = true; // check if the left-adjacent card is
										// visible
				}
				if ((q != 4) && (q != 6) && (q != 8) && (q != 17) && (q != 27)
						&& (q != 11) && (q != 14)) { // if the card isn't a
														// right end
					if (!theCards[q + 1].isVisible())
						noRight = true; // check if the right-adjacent card is
										// visible
				}
				// some of the cards in the third row are considered to be edge
				// cards because not all pairs of adjacent cards in the third
				// row uncover another card
				if ((!noLeft) && (!noRight))
					break; // if both the left and right cards are present,
							// don't do anything
				int offset = -1; // the "offset" is the difference in the
									// indeces of the right card of the adjacent
									// pair and the card that pair will uncover
				if ((q >= 18) && (q <= 27)) { // 4th row
					offset = 10;
				} else if ((q >= 9) && (q <= 11)) { // first 3 of 3rd row
					offset = 7;
				} else if ((q >= 12) && (q <= 14)) { // second 3 of third row
					offset = 8;
				} else if ((q >= 15) && (q <= 17)) { // last 3 of third row
					offset = 9;
				} else if ((q >= 3) && (q <= 4)) { // first 2 of second row
					offset = 4;
				} else if ((q >= 5) && (q <= 6)) { // second 2 of second row
					offset = 5;
				} else if ((q >= 7) && (q <= 8)) { // last 2 of second row
					offset = 6;
				}
				// the first row isn't here because the peaks are special and
				// were already taken care of above
				if (offset == -1)
					break; // if the offset didn't get set, don't do anything
							// (offset should get set, but just in case)
				if (noLeft)
					theCards[q - offset].flip(); // if the left card is missing,
													// use the current card as
													// the right one
				if (noRight)
					theCards[q - offset + 1].flip(); // if the right card is
														// missing, use the
														// missing card as the
														// right one
			} else if ((q >= 28) && (q < 51)) { // in the deck
				theCards[q].setX(theCards[disIndex].getX()); // move the card to
																// the deck
				theCards[q].setY(theCards[disIndex].getY()); // set the deck's
																// coordinates
				theCards[disIndex].setVisible(false); // hide the previously
														// discarded card (for
														// faster repaint)
				theCards[q].flip(); // flip the deck card
				if (q != 28)
					theCards[q - 1].setVisible(true); // show the next deck card
														// if it's not the last
														// deck card
				disIndex = q; // set the index of the dicard pile
				streak = 0; // reset the streak
				if (!cheats[2]) { // if the thrid cheat isn't on (no penalty
									// cheat)
					score -= 5; // 5-point penalty
					gameScore -= 5; // to the game score
					sesScore -= 5; // and the session score
				}
				if (gameScore < lowScore)
					lowScore = gameScore; // set the low score if score is lower
				remCards--; // decrement the number of cards in the deck
			}
			break; // "consume" the click - don't go through the rest of the
					// cards
		}
		repaint(); // repaint the board
		TriPeaks theFrame = (TriPeaks) SwingUtilities.windowForComponent(this); // get
																				// the
																				// containing
																				// frame
		theFrame.updateStats(); // update the stats labels
	}

	public int getPenalty() { // return the penalty
		if (cheats[2])
			return 0; // if the penalty cheat is on, there is no penalty
		if ((cardsInPlay != 0) && (remCards != 0))
			return (cardsInPlay * 5); // if there are cards in the deck AND in
										// play, the penalty is $5 for every
										// card removed
		else
			return 0; // otherwise the penalty is 0
	}

	public void doPenalty(int penalty) { // perform the penalty - penalty
											// doesn't affect the low score
		score -= penalty; // subtract the penalty
		sesScore -= penalty; // from the session score
		gameScore -= penalty; // and from the game score
	}

	public String getCardFront() { // returns the current front style
		return frontFolder;
	}

	public String getCardBack() { // returns the current back style
		return backStyle;
	}

	public Color getBackColor() { // returns the background color
		return backColor;
	}

	public int getScore() { // returns the player's overall score
		return score;
	}

	public int getGameScore() { // returns the current game score
		return gameScore;
	}

	public int getStreak() { // returns the current sreak
		return streak;
	}

	public int getNumGames() { // returns the number of games played
		return numGames;
	}

	public int getHighScore() { // returns the high score
		return highScore;
	}

	public int getLowScore() { // returns the low score
		return lowScore;
	}

	public int getHighStreak() { // returns the longest streak
		return highStreak;
	}

	public int getSesScore() { // returns the session score
		return sesScore;
	}

	public int getSesGames() { // returns the number of session games
		return sesGames;
	}

	public Color getFontColor() {
		return fontColor;
	}

	public Font getTextFont() {
		return textFont;
	}

	public int[] getAllStats() { // returns all the stats in an array
		int[] retVal = { getScore(), getGameScore(), getSesScore(),
				getStreak(), getNumGames(), getSesGames(), getHighScore(),
				getLowScore(), getHighStreak() }; // the array of stats
		return retVal;
	}

	public boolean isCheating() { // check if the player is currently cheating
		for (int q = 0; q < cheats.length; q++) { // go through all the cheats
			if (cheats[q])
				return true; // return true if any cheat is on
		}
		return false; // no cheat was found - return false
	}

	public boolean hasCheated() { // checks if player has ever cheated
		return hasCheatedYet;
	}

	public boolean[] getCheats() { // returns all the cheats
		return cheats; // return the cheats array
	}

	public void setStats(int[] stats) { // sets all the stats based on the array
										// values
		score = stats[0]; // the programmer knows the order of the stats to be
							// passed into this method:
		highScore = stats[1]; // overall score, high score, low score, number of
								// games, and longest streak
		lowScore = stats[2];
		numGames = stats[3];
		highStreak = stats[4];
	}

	public void setCardFront(String front) { // sets the front style
		frontFolder = front;
	}

	public void setCardBack(String back) { // sets the back style
		backStyle = back;
	}

	public void setBackColor(Color newColor) { // sets the background color
		backColor = newColor;
	}

	public void setCheat(int cheatNum, boolean newState) { // set a cheat with
															// the given index
		if (cheatNum >= cheats.length)
			return; // if the index is out of bounds
		if (newState)
			hasCheatedYet = true; // if the cheat is turned on, set the
									// "has cheated" flag
		cheats[cheatNum] = newState; // set the cheat
	}

	public void setCheats(boolean[] newCheats) { // set all the cheats in a
													// given array
		for (int q = 0; q < cheats.length; q++)
			setCheat(q, newCheats[q]); // go through the array and set the
										// cheats
	}

	public void setCheated(boolean hasCheatedYet) { // set the cheated status
													// for the player.
		this.hasCheatedYet = hasCheatedYet;
	}

	public void setDefaults() {
		frontFolder = "Default";
		backStyle = "Default";
		backColor = (Color.GREEN).darker().darker();
		fontColor = Color.WHITE;
		textFont = new Font("Serif", Font.BOLD, 14);
	}

	public void setFontColor(Color newColor) {
		fontColor = newColor;
	}

	public void setTextFont(Font newFont) {
		textFont = newFont;
	}

	// not used, but necessary to implement MouseListener
	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}
} // end class CardPanel