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

		private final int value;
		private final String description;

		private Rank(int value, String description) {
			this.value = value;
			this.description = description;
		}

		public int getValue() {
			return value;
		}

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

		private final String description;

		private Suit(String description) {
			this.description = description;
		}

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
	}

	public Rank getRank() {
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

	public void setRank(Rank newVal) {
		rank = newVal;
	}

	public void setSuit(Suit newSuit) {
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
