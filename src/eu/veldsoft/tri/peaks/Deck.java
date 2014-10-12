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

/**
 * Deck of cards.
 * 
 * @author Todor Balabanov
 */
public class Deck {
	/**
	 * Deck of cards.
	 */
	static private final Card[] cards = {
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
	 * Deck suffling.
	 * 
	 * @author Todor Balabanov
	 */
	static void shuffle() {
		for (int last = cards.length - 1, r = -1; last > 0; last--) {
			r = Constants.PRNG.nextInt(last + 1);
			Card swap = cards[last];
			cards[last] = cards[r];
			cards[r] = swap;
		}
	}

	/**
	 * Make all cards visible.
	 * 
	 * @author Todor Balabanov
	 */
	static void setAllVisible() {
		for (Card card : cards) {
			card.setVisible(true);
		}
	}

	/**
	 * Make all cards visible.
	 * 
	 * @author Todor Balabanov
	 */
	static void setAllInvisible() {
		for (Card card : cards) {
			card.setVisible(false);
		}
	}

	/**
	 * 
	 * @param index
	 *            Index of the card in the deck.
	 * 
	 * @return Card object reference.
	 * 
	 * @throws RuntimeException
	 * 
	 * @author Todor Balabanov
	 */
	static Card cardAtPosition(int index) throws RuntimeException {
		if (index < 0 || index >= cards.length) {
			throw (new IndexOutOfBoundsException("No such card!"));
		}

		return (cards[index]);
	}

	static void deal() {
		// TODO Do it in a single master loop.
		/*
		 * first row
		 */
		for (int q = 0; q < 3; q++) {
			/*
			 * set the X-coord
			 */
			cards[q].setX(2 * Card.WIDTH + q * 3 * Card.WIDTH);

			/*
			 * set the Y-coord for the card
			 */
			cards[q].setY((int) Card.HEIGHT / 2);

			/*
			 * make it face-down
			 */
			cards[q].flip(true);
		}

		/*
		 * second row
		 */
		for (int q = 0; q < 6; q++) {
			/*
			 * set the coords
			 */
			cards[q + 3].setX(3 * ((int) Card.WIDTH / 2) + q * Card.WIDTH
					+ ((int) q / 2) * Card.WIDTH);
			cards[q + 3].setY(Card.HEIGHT);

			/*
			 * face-down
			 */
			cards[q + 3].flip(true);
		}

		/*
		 * third row
		 */
		for (int q = 0; q < 9; q++) {
			/*
			 * set the coords
			 */
			cards[q + 9].setX(Card.WIDTH + q * Card.WIDTH);
			cards[q + 9].setY(3 * ((int) Card.HEIGHT / 2));

			/*
			 * face-down
			 */
			cards[q + 9].flip(true);
		}

		/*
		 * fourth row
		 */
		for (int q = 0; q < 10; q++) {
			/*
			 * set the coords
			 */
			cards[q + 18].setX(((int) Card.WIDTH / 2) + q * Card.WIDTH);
			cards[q + 18].setY(2 * Card.HEIGHT);

			/*
			 * face-up
			 */
			cards[q + 18].flip(false);
		}

		/*
		 * the deck
		 */
		for (int q = 28; q < 51; q++) {
			/*
			 * same coords for all of them
			 */
			cards[q].setX(7 * ((int) Card.WIDTH / 2));
			cards[q].setY(13 * ((int) Card.HEIGHT / 4));

			/*
			 * they're all face-down
			 */
			cards[q].flip(true);

			/*
			 * they're invisible
			 */
			cards[q].setVisible(false);
		}
		/*
		 * only the top one is visible (faster repaint)
		 */
		cards[50].setVisible(true);

		/*
		 * discard pile set the coords
		 */
		cards[51].setX(13 * ((int) Card.WIDTH / 2));
		cards[51].setY(13 * ((int) Card.HEIGHT / 4));

		/*
		 * face-up
		 */
		cards[51].flip(false);
	}

	/**
	 * There should be only one deck in the game.
	 * 
	 * @author Todor Balabanov
	 */
	private Deck() {
	}
}
