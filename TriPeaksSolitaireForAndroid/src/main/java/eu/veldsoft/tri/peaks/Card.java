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
 * Defines cards.
 * 
 * @author Todor Balabanov
 */
class Card {

	/**
	 * Card rank enumeration. All cards have rank value in the range of 0 to 12 
	 * (0=Ace, ..., 10=Jack, 11=Queen, 12=King).
	 * 
	 * @author Todor Balabanov
	 */
	static enum Rank {
		ACE(0, "ace"), TWO(1, "two"), THREE(2, "three"), FOUR(3, "four"), FIVE(
				4, "five"), SIX(5, "six"), SEVEN(6, "seven"), EIGHT(7, "eight"), NINE(
				8, "nine"), TEN(9, "ten"), JACK(10, "jack"), QUEEN(11, "queen"), KING(
				12, "king");

		/**
		 * Rank numeric value used in some of the calculations.
		 */
		private final int value;

		/**
		 * Rank description as text.
		 */
		private final String description;

		/**
		 * Constructor with all fields.
		 * 
		 * @param value Rank index.
		 * @param description
		 * 
		 * @author Todor Balabanov
		 */
		private Rank(int value, String description) {
			this.value = value;
			this.description = description;
		}

		/**
		 * Card rank as index getter.
		 * 
		 * @return Card rank index.
		 * 
		 * @author Todor Balabanov
		 */
		public int getValue() {
			return value;
		}

		/**
		 * Card are adjacent by rank. This function checks the adjecent.
		 * 
		 * @param rank External card rank.
		 * 
		 * @return True if cards are adjecent, false otherwise.
		 * 
		 * @author Todor Balabanov
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
		 * Card rank representation as string.
		 * 
		 * @return String representation.
		 * 
		 * @author Todor Balabanov
		 */
		@Override
		public String toString() {
			return (description);
		}
	}

	/**
	 * Card suits enumeration.
	 * 
	 * @author Todor Balabanov
	 */
	static enum Suit {
		CLUBS("clubs"), HEARTS("hearts"), DIAMONDS("diamonds"), SPADES("spades");

		/**
		 * Text descrption of the card suit.
		 */
		private final String description;

		/**
		 * Constructor with all fields.
		 * 
		 * @param description Card suit description as text.
		 * 
		 * @author Todor Balabanov
		 */
		private Suit(String description) {
			this.description = description;
		}

		/**
		 * String representation of the card suit.
		 * 
		 * @return String representation.
		 * 
		 * @author Todor Balabanov
		 */
		@Override
		public String toString() {
			return (description);
		}
	}

	/**
	 * Swing information for the card height.
	 */
	static final int HEIGHT = 86;

	/**
	 * Swing information for the card width.
	 */
	static final int WIDTH = 64;

	/**
	 * Counter is used to map cards in the collections.
	 */
	private static int count = 0;

	/**
	 * Is card facing down flag.
	 */
	private boolean isFaceDown;

	/**
	 * Is card visible flag.
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
	 * Swing coordinates of the card (center, not top-left).
	 */
	private int xCoord;

	/**
	 * Swing coordinates of the card (center, not top-left).
	 */
	private int yCoord;

	/**
	 * Custom index used for collections mapping.
	 */
	private int index;

	/**
	 * Constructor for all the fields at once.
	 * 
	 * @param rank Card rank.
	 * @param suit Card suit.
	 * @param isFaceDown Card facing down flag.
	 * @param visible Card visible flag.
	 * @param x Swing x coordinate.
	 * @param y Swing y coordinate.
	 * 
	 * @author Todor Balabanov
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
	 * Card custom index getter.
	 *
	 * @return Custom index.
	 * 
	 * @author Todor Balabanov
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Card rank getter.
	 * 
	 * @return Card rank.
	 * 
	 * @author Todor Balabanov
	 */
	public Rank getRank() {
		return rank;
	}

	/**
	 * Card suit getter.
	 * 
	 * @return Card suit.
	 * 
	 * @author Todor Balabanov
	 */
	public Suit getSuit() {
		return suit;
	}

	/**
	 * Is card facing down getter.
	 * 
	 * @return True if the card is facing down, false otherwise.
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isFacingDown() {
		return isFaceDown;
	}

	/**
	 * Is card facing up getter.
	 * 
	 * @return True if the card is facing up, false otherwise.
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isFacingUp() {
		return !isFaceDown;
	}

	/**
	 * Swing x coordinate getter.
	 * 
	 * @return X coordinate.
	 * 
	 * @author Todor Balabanov
	 */
	public int getX() {
		return xCoord;
	}

	/**
	 * Swing y coordiante getter.
	 * 
	 * @return Y coordinate.
	 * 
	 * @author Todor Balabanov
	 */
	public int getY() {
		return yCoord;
	}

	/**
	 * Is card visible gettter.
	 * 
	 * @return True if the card is visible, false otherwise.
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Is card invisible getter.
	 * 
	 * @return True if the card is invisible, false otherwise.
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isInvisible() {
		return !visible;
	}

	/**
	 * Card rank setter.
	 * 
	 * @param newVal New value for the card rank.
	 * 
	 * @author Todor Balabanov
	 */
	public void setRank(Rank newVal) {
		rank = newVal;
	}

	/**
	 * Card suit setter.
	 * 
	 * @param newSuit New value for the card suit.
	 * 
	 * @author Todor Balabanov
	 */
	public void setSuit(Suit newSuit) {
		suit = newSuit;
	}

	/**
	 * Flip the card. If the card is facing down it will be fliped and otherwise.
	 * 
	 * @author Todor Balabanov
	 */
	public void flip() {
		isFaceDown = !isFaceDown;
	}

	/**
	 * Flip the card.
	 * 
	 * @param isFaceDown It gives particular valu for flipping.
	 * 
	 * @author Todor Balabanov
	 */
	public void flip(boolean isFaceDown) {
		this.isFaceDown = isFaceDown;
	}

	/**
	 * Swing x coordinate setter.
	 * 
	 * @param newX New card x coordinate.
	 * 
	 * @author Todor Balabanov
	 */
	public void setX(int newX) {
		xCoord = newX;
	}

	/**
	 * Swing y coordinate setter.
	 * 
	 * @param newY New card y coordinate.
	 * 
	 * @author Todor Balabanov
	 */
	public void setY(int newY) {
		yCoord = newY;
	}

	/**
	 * Set card to be visible.
	 * 
	 * @author Todor Balabanov
	 */
	public void setVisible() {
		visible = true;
	}

	/**
	 * Set card to be invisible.
	 * 
	 * @author Todor Balabanov
	 */
	public void setInvisible() {
		visible = false;
	}

	/**
	 * Converts the card to a string representation.
	 * 
	 * @return String representation.
	 * 
	 * @author Todor Balabanov
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
	 * Hash code calculation.
	 * 
	 * @return Hash code.
	 * 
	 * @author Todor Balabanov
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
	 * Equals method implementation.
	 * 
	 * @param obj External object to compare with.
	 * 
	 * @return True if the objects are equal, false otherwise.
	 * 
	 * @author Todor Balabanov
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

