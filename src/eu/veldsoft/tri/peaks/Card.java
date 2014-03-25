package eu.veldsoft.tri.peaks;

import static eu.veldsoft.tri.peaks.Card.Suit.*;

/**
 * defines a card
 * 
 * @author Todor Balabanov
 */
class Card {
	/**
	 * the 4 suits
	 * 
	 * @author Todor Balabanov
	 */
	public static enum Suit {
		CLUBS("clubs"), HEARTS("hearts"), DIAMONDS("diamands"), SPADES("spades");

		private final String description;

		private Suit(String description) {
			this.description = description;
		}

		@Override
		public String toString() {
			return (description);
		}
	}

	// the height and width of the card
	public static final int HEIGHT = 86;
	public static final int WIDTH = 64;

	/**
	 * is it facing down
	 */
	private boolean isFaceDown;

	/**
	 * is it visible
	 */
	private boolean visible;

	/**
	 * value (0-12) - 0=Ace, 10=Jack, 11=Queen, 12=King
	 */
	private int rank;

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

	// public Card() {
	// // must initialize manually, later on
	// this(-1, -1, false, false, 0, 0);
	// }

	// specify all the fields at once
	public Card(int value, Suit suit, boolean isFaceDown, boolean visible,
			int x, int y) {
		// set the value
		this.rank = value;

		// check if it's a valid suit
		// set the suit
		if ((suit == CLUBS) || (suit == HEARTS) || (suit == DIAMONDS)
				|| (suit == SPADES))
			this.suit = suit;

		// set the face-down flag
		this.isFaceDown = isFaceDown;

		// set the visible flag
		this.visible = visible;

		// set the coordinates
		xCoord = x;
		yCoord = y;
	}

	// accessor methods for the class
	public int getValue() {
		return rank;
	}

	public Suit getSuit() {
		return suit;
	}

	public boolean isFacingDown() {
		return isFaceDown;
	}

	public int getX() {
		return xCoord;
	}

	public int getY() {
		return yCoord;
	}

	public boolean isVisible() {
		return visible;
	}

	// mutator methods
	// sets the value of the card
	public void setValue(int newVal) {
		// checks if it's a valid value
		// set the value
		if ((newVal >= 0) && (newVal < 13))
			rank = newVal;
	}

	// sets the suit
	public void setSuit(Suit newSuit) {
		if ((newSuit == CLUBS) || (newSuit == HEARTS) || (newSuit == DIAMONDS)
				|| (newSuit == SPADES))
			suit = newSuit;
	}

	public void flip() {
		isFaceDown = !isFaceDown;
	}

	public void flip(boolean isFaceDown) {
		this.isFaceDown = isFaceDown;
	}

	public void setX(int newX) {
		xCoord = newX;
	}

	public void setY(int newY) {
		yCoord = newY;
	}

	public void setVisible(boolean newVis) {
		visible = newVis;
	}

	// converts the card to a string representation
	public String toString() {
		String val;
		switch (rank) {
		case 12:
			val = "king";
			break;
		case 11:
			val = "queen";
			break;
		case 10:
			val = "jack";
			break;
		default:
			val = rank + "";
		}
		String finVal = val + " of " + suit + ": "
				+ ((isFaceDown) ? "facing down" : "facing up") + ", "
				+ ((visible) ? "visible" : "invisible") + " :: (" + xCoord
				+ ", " + yCoord + ")";
		return finVal;
	}

	// checks if the value of the card
	// is 1 off from the given card
	public boolean isAdjacentTo(Card that) {
		// this card's value
		int tempThis = rank;

		// the given card's value
		int tempThat = that.getValue();

		// check if it's one away
		if (((tempThis + 1) % 13 == tempThat)
				|| ((tempThat + 1) % 13 == tempThis))
			return true;
		else
			return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + (isFaceDown ? 1231 : 1237);
		result = prime * result + rank;
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		result = prime * result + (visible ? 1231 : 1237);
		result = prime * result + xCoord;
		result = prime * result + yCoord;
		return result;
	}

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
} // end class Card