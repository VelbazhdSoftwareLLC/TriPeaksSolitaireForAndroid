/*
 * This file is a part of Tri Peaks Solitaire for Android
 *
 * Copyright (C) 2013-2014 by Valera Trubachev, Christian d'Heureuse, Todor 
 * Balabanov, Ina Baltadzhieva, Maria Barova, Kamelia Ivanova, Victor Vangelov, Daniela Pancheva
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
 * defines a card
 * 
 * @author Todor Balabanov
 */
class Card {

	/**
	 * value (0-12) - 0=Ace, 10=Jack, 11=Queen, 12=King
	 * 
	 * @author Todor Balabanov
	 */
	public static enum Rank {
		ACE(0, "ace"), TWO(1, "two"), THREE(2, "three"), FOUR(3, "four"), FIVE(
				4, "five"), SIX(5, "six"), SEVEN(6, "seven"), EIGHT(7, "eight"), NINE(
				8, "nine"), TEN(9, "ten"), JACK(10, "jack"), QUEEN(11, "queen"), KING(
				12, "king");

		/**
		 * 
		 */
		private final int value;

		/**
		 * 
		 */
		private final String description;

		/**
		 * 
		 * @param value
		 * @param description
		 */
		private Rank(int value, String description) {
			this.value = value;
			this.description = description;
		}

		/**
		 * 
		 * @return
		 */
		public int getValue() {
			return value;
		}

		/**
		 * 
		 * @param rank
		 * @return
		 */
		public boolean isAdjacentTo(Rank rank) {
			// TODO Do not use internal numbering.
			if ((this.value + 1) % 13 == rank.value) {
				return (true);
			}
			if ((rank.value + 1) % 13 == this.value) {
				return (true);
			}
			return (false);
		}

		/**
		 * 
		 */
		@Override
		public String toString() {
			return (description);
		}
	}

	/**
	 * the 4 suits
	 * 
	 * @author Todor Balabanov
	 */
	public static enum Suit {
		CLUBS("clubs"), HEARTS("hearts"), DIAMONDS("diamonds"), SPADES("spades");

		/**
		 * 
		 */
		private final String description;

		/**
		 * 
		 * @param description
		 */
		private Suit(String description) {
			this.description = description;
		}

		/**
		 * 
		 */
		@Override
		public String toString() {
			return (description);
		}
	}

	/**
	 * 
	 */
	public static final int HEIGHT = 86;

	/**
	 * 
	 */
	public static final int WIDTH = 64;

	/**
	 * 
	 */
	private static int count = 0;

	/**
	 * is it facing down
	 */
	private boolean isFaceDown;

	/**
	 * is it visible
	 */
	private boolean visible;

	/**
	 * Card rank.
	 */
	private Rank rank;

	/**
	 * suit of the card, as defined above
	 */
	private Suit suit;

	/**
	 * coordinates of the card (center, not top-left)
	 */
	private int xCoord;

	/**
	 * coordinates of the card (center, not top-left)
	 */
	private int yCoord;

	/**
	 * Custom index.
	 */
	private int index;

	/**
	 * specify all the fields at once
	 * 
	 * @param rank
	 * @param suit
	 * @param isFaceDown
	 * @param visible
	 * @param x
	 * @param y
	 */
	public Card(Rank rank, Suit suit, boolean isFaceDown, boolean visible,
			int x, int y) {
		this.rank = rank;
		this.suit = suit;
		this.isFaceDown = isFaceDown;
		this.visible = visible;
		xCoord = x;
		yCoord = y;
		index = count++;
	}

	/**
	 * 
	 * @return
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * 
	 * @return
	 */
	public Rank getRank() {
		return rank;
	}

	/**
	 * 
	 * @return
	 */
	public Suit getSuit() {
		return suit;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isFacingDown() {
		return isFaceDown;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isFacingUp() {
		return !isFaceDown;
	}

	/**
	 * 
	 * @return
	 */
	public int getX() {
		return xCoord;
	}

	/**
	 * 
	 * @return
	 */
	public int getY() {
		return yCoord;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isInvisible() {
		return !visible;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setRank(Rank newVal) {
		rank = newVal;
	}

	/**
	 * 
	 * @param newSuit
	 */
	public void setSuit(Suit newSuit) {
		suit = newSuit;
	}

	/**
	 * 
	 */
	public void flip() {
		isFaceDown = !isFaceDown;
	}

	/**
	 * 
	 * @param isFaceDown
	 */
	public void flip(boolean isFaceDown) {
		this.isFaceDown = isFaceDown;
	}

	/**
	 * 
	 * @param newX
	 */
	public void setX(int newX) {
		xCoord = newX;
	}

	/**
	 * 
	 * @param newY
	 */
	public void setY(int newY) {
		yCoord = newY;
	}

	/**
	 * 
	 */
	public void setVisible() {
		visible = true;
	}

	/**
	 * 
	 */
	public void setInvisible() {
		visible = false;
	}

	/**
	 * converts the card to a string representation
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		String finVal = rank + " of " + suit + ": "
				+ ((isFaceDown) ? "facing down" : "facing up") + ", "
				+ ((visible) ? "visible" : "invisible") + " :: (" + xCoord
				+ ", " + yCoord + ")";
		return finVal;
	}

	/**
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime
				* result
				+ (isFaceDown ? Boolean.TRUE.hashCode() : Boolean.FALSE
						.hashCode());
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		result = prime
				* result
				+ (visible ? Boolean.TRUE.hashCode() : Boolean.FALSE.hashCode());
		result = prime * result + xCoord;
		result = prime * result + yCoord;
		return result;
	}

	/**
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (isFaceDown != other.isFaceDown)
			return false;
		if (rank != other.rank)
			return false;
		if (suit != other.suit)
			return false;
		if (visible != other.visible)
			return false;
		if (xCoord != other.xCoord)
			return false;
		if (yCoord != other.yCoord)
			return false;
		return true;
	}
}
