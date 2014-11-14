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

import java.util.EnumSet;

/**
 * Game board object model.
 * 
 * @author Todor Balabanov
 */
class CardBoard {

	/**
	 * Game state information.
	 */
	private GameState state = new GameState();

	/**
	 * Status text.
	 */
	private String status = "";

	/**
	 * Constructor without parameters.
	 * 
	 * @author Todor Balabanov
	 */
	public CardBoard() {
	}

	/**
	 * Game state getter.
	 * 
	 * @return Game state.
	 * 
	 * @author Todor Balabanov
	 */
	public GameState getState() {
		return state;
	}

	/**
	 * Game state setter.
	 * 
	 * @param state New game state.
	 * 
	 * @author Todor Balabanov
	 */
	public void setState(GameState state) {
		this.state = state;
	}

	/**
	 * Status message getter.
	 * 
	 * @return Status message.
	 * 
	 * @author Todor Balabanov
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Status message setter.
	 * 
	 * @param status New status message.
	 * 
	 * @author Todor Balabanov
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Redeals the cards.
	 * 
	 * @author Todor Balabanov
	 */
	public void redeal() {
		Deck.shuffle();
		Deck.setAllVisible();
		Deck.deal();
		state.redeal();
	}

	/**
	 * Resets all internal variables.
	 * 
	 * @author Todor Balabanov
	 */
	public void reset() {
		Deck.setAllInvisible();

		/*
		 * Essentially the same thing as the default values for the fields.
		 */
		status = "";
		state.reset();
	}

	/**
	 * Penalty getter.
	 * 
	 * @return Penalty value.
	 * 
	 * @author Todor Balabanov
	 */
	public int getPenalty() {
		/*
		 * If the penalty cheat is on, there is no penalty.
		 */
		if (state.getCheats().contains(Cheat.NO_PENALTY) == true) {
			return 0;
		}

		/*
		 * If there are cards in the deck AND in play, the penalty is $5 for
		 * every card removed.
		 */
		if ((state.getCardsInPlay() == 0 || state.getRemainingCards() == 0) == false) {
			return (state.getCardsInPlay() * Constants.CARD_REMOVED_PENALTY);
		} else {
			/*
			 * Otherwise the penalty is 0.
			 */
			return 0;
		}
	}

	/**
	 * Perform the penalty - penalty doesn't affect the low score.
	 * 
	 * @param penalty Size of the penalty to be applied.
	 * 
	 * @author Todor Balabanov
	 */
	public void doPenalty(int penalty) {
		/*
		 * Subtract the penalty.
		 */
		state.setScore(state.getScore() - penalty);

		/*
		 * Subtract from the session score.
		 */
		state.setSessionScore(state.getSessionScore() - penalty);

		/*
		 * Subtract from the game score.
		 */
		state.setGameScore(state.getGameScore() - penalty);
	}

	/**
	 * Player's overall score getter.
	 * 
	 * @return Overall score.
	 * 
	 * @author Todor Balabanov
	 */
	public int getScore() {
		return state.getScore();
	}

	/**
	 * Current game score getter.
	 * 
	 * @return Current game score.
	 * 
	 * @author Todor Balabanov
	 */
	public int getGameScore() {
		return state.getGameScore();
	}

	/**
	 * Current streak getter.
	 * 
	 * @return Current streak.
	 * 
	 * @author Todor Balabanov
	 */
	public int getStreak() {
		return state.getStreak();
	}

	/**
	 * Number of games played getter.
	 * 
	 * @return Number of games.
	 * 
	 * @author Todor Balabanov
	 */
	public int getNumGames() {
		return state.getNumberOfGames();
	}

	/**
	 * High score getter.
	 * 
	 * @return High score.
	 * 
	 * @author Todor Balabanov
	 */
	public int getHighScore() {
		return state.getHighScore();
	}

	/**
	 * Low score getter.
	 * 
	 * @return Low score.
	 * 
	 * @author Todor Balabanov
	 */
	public int getLowScore() {
		return state.getLowScore();
	}

	/**
	 * Longest streak getter.
	 * 
	 * @return Longest streak.
	 * 
	 * @author Todor Balabanov
	 */
	public int getHighStreak() {
		return state.getHighStreak();
	}

	/**
	 * Session score getter.
	 * 
	 * @return Session score.
	 * 
	 * @author Todor Balabanov
	 */
	public int getSessionScore() {
		return state.getSessionScore();
	}

	/**
	 * Number of session games getter.
	 * 
	 * @return Number of session games.
	 * 
	 * @author Todor Balabanov
	 */
	public int getSessionGames() {
		return state.getNumberOfSessionGames();
	}

	/**
	 * All stats in an array getter.
	 * 
	 * @return All stats.
	 * 
	 * @author Todor Balabanov
	 */
	public int[] getAllStats() {
		/*
		 * Array of stats.
		 */
		int[] retVal = { getScore(), getGameScore(), getSessionScore(),
				getStreak(), getNumGames(), getSessionGames(), getHighScore(),
				getLowScore(), getHighStreak() };

		return retVal;
	}

	/**
	 * Check if the player is currently cheating.
	 * 
	 * @return True if the player is cheating, false otherwise.
	 * 
	 * @author Todor Balabanov
	 */
	public boolean isCheating() {
		return (state.getCheats().isEmpty());
	}

	/**
	 * Checks if player has ever cheated.
	 * 
	 * @return True if hte player has cheated, false otherwise.
	 * 
	 * @author Todor Balabanov
	 */
	public boolean hasCheated() {
		return state.isHasCheatedYet();
	}

	/**
	 * Returns all the cheats.
	 * 
	 * @return Cheats set.
	 * 
	 * @author Todor Balabanov
	 */
	public EnumSet<Cheat> getCheats() {
		/*
		 * return the cheats array
		 */
		return state.getCheats();
	}

	/**
	 * Sets all the stats based on the array values.
	 * 
	 * @param stats New stats values.
	 * 
	 * @author Todor Balabanov
	 */
	public void setStats(int[] stats) {
		/*
		 * The programmer knows the order of the stats to be passed into this
		 * method:
		 */
		state.setScore(stats[0]);

		/*
		 * Overall score, high score, low score, number ofgames, and longest
		 * streak.
		 */
		state.setHighScore(stats[1]);
		state.setLowScore(stats[2]);
		state.setNumberOfGames(stats[3]);
		state.setHighStreak(stats[4]);
	}

	/**
	 * Set a cheat with the given index.
	 * 
	 * @param cheat Cheat to be set.
	 * @param newState New state of the cheat.
	 * 
	 * @author Todor Balabanov
	 */
	public void setCheat(Cheat cheat, boolean newState) {
		if (state.getCheats().contains(cheat) == false && newState == true) {
			state.getCheats().add(cheat);
		} else if (state.getCheats().contains(cheat) == true
				&& newState == false) {
			state.getCheats().remove(cheat);
		}

		/*
		 * If the cheat is turned on, set the "has cheated" flag.
		 */
		if (newState == true) {
			state.setHasCheatedYet(true);
		}
	}

	/**
	 * Set all the cheats in a given array.
	 * 
	 * @param newCheats New cheats values.
	 * 
	 * @author Todor Balabanov
	 */
	public void setCheats(EnumSet<Cheat> newCheats) {
		state.getCheats().clear();
		state.getCheats().addAll(newCheats);
	}

	/**
	 * Set the cheated status for the player.
	 * 
	 * @param hasCheatedYet New player's cheat status.
	 * 
	 * @author Todor Balabanov
	 */
	public void setCheated(boolean hasCheatedYet) {
		state.setHasCheatedYet(hasCheatedYet);
	}

	/**
	 * Update internal game state.
	 * 
	 * @param index
	 *            Index of the card to be handled.
	 * 
	 * @author Todor Balabanov
	 */
	public void updateState(int index) {
		Card card = null;

		try {
			card = Deck.cardAtPosition(index);
		} catch (Exception ex) {
			return;
		}

		/*
		 * A value to check if the card is adjacent by value.
		 */
		boolean isAdjacent = false;

		/*
		 * If the second cheat is used, the value of the card won't be checked.
		 */
		if (getState().getCheats().contains(Cheat.CLICK_ANY_CARD) == true) {
			/*
			 * The card is adjacent automatically.
			 */
			isAdjacent = true;
		} else {
			/*
			 * No cheat - check card check if the card is adjacent by value.
			 */
			isAdjacent = card.getRank()
					.isAdjacentTo(
							Deck.cardAtPosition(getState().getDiscardIndex())
									.getRank());
		}

		/*
		 * If the card isn't in the deck and is adjacent to the last discarded
		 * card.
		 */
		if (index < 28 && isAdjacent == true) {
			/*
			 * Hide the previously discarded card - makes the repaint faster.
			 */
			Deck.cardAtPosition(getState().getDiscardIndex()).setInvisible();

			/*
			 * Take the card from the peaks and put it in the discard pile.
			 */
			getState().doValidMove(index);

			/*
			 * If it was a peak.
			 */
			if (index < 3) {
				/*
				 * If all the peaks are gone.
				 */
				if (getState().getRemainingCards() == 0) {
					/*
					 * Set the status message.
					 */
					setStatus("You have Tri-Conquered! You get a bonus of $30");
				} else {
					/*
					 * Set the status message.
					 */
					setStatus("You have reached a peak! You get a bonus of $15");
				}

				/*
				 * The click was consumed - do not go through the rest of the
				 * cards.
				 */
				return;
			}

			/*
			 * Check values for checking whether or not a card has a card to the
			 * left or right.
			 */
			boolean noLeft = false, noRight = false;

			/*
			 * If the card is not a left end.
			 */
			if ((index != 3) && (index != 9) && (index != 18) && (index != 5)
					&& (index != 7) && (index != 12) && (index != 15)) {
				/*
				 * Check if the left-adjacent card is visible.
				 */
				if (Deck.cardAtPosition(index - 1).isInvisible() == true) {
					noLeft = true;
				}
			}

			/*
			 * If the card is not a right end.
			 */
			if ((index != 4) && (index != 6) && (index != 8) && (index != 17)
					&& (index != 27) && (index != 11) && (index != 14)) {
				/*
				 * Check if the right-adjacent card is visible.
				 */
				if (Deck.cardAtPosition(index + 1).isInvisible() == true) {
					noRight = true;
				}
			}

			/*
			 * Some of the cards in the third row are considered to be edge
			 * cards because not all pairs of adjacent cards in the third row
			 * uncover another card.
			 */
			if ((noLeft || noRight) == false) {
				/*
				 * If both the left and right cards are present, don't do
				 * anything.
				 */
				return;
			}

			/*
			 * The offset is the difference in the indexes of the right card of
			 * the adjacent pair and the card that pair will uncover.
			 */
			int offset = -1;

			if ((index >= 18) && (index <= 27)) {
				/*
				 * 4th row
				 */
				offset = 10;
			} else if ((index >= 9) && (index <= 11)) {
				/*
				 * first 3 of 3rd row
				 */
				offset = 7;
			} else if ((index >= 12) && (index <= 14)) {
				/*
				 * second 3 of third row
				 */
				offset = 8;
			} else if ((index >= 15) && (index <= 17)) {
				/*
				 * last 3 of third row
				 */
				offset = 9;
			} else if ((index >= 3) && (index <= 4)) {
				/*
				 * first 2 of second row
				 */
				offset = 4;
			} else if ((index >= 5) && (index <= 6)) {
				/*
				 * second 2 of second row
				 */
				offset = 5;
			} else if ((index >= 7) && (index <= 8)) {
				/*
				 * last 2 of second row
				 */
				offset = 6;
			}

			/*
			 * The first row is not here because the peaks are special and were
			 * already taken care of above.
			 */
			if (offset == -1) {
				/*
				 * If the offset didn't get set, do not do anything (offset
				 * should get set, but just in case).
				 */
				return;
			}

			/*
			 * If the left card is missing, use the current card as the right
			 * one.
			 */
			if (noLeft) {
				Deck.cardAtPosition(index - offset).flip();
			}

			/*
			 * If the right card is missing, use the missing card as the right
			 * one.
			 */
			if (noRight) {
				Deck.cardAtPosition(index - offset + 1).flip();
			}
		}

		/*
		 * In the deck move the card to the deck.
		 */
		else if ((index >= 28) && (index < 51)) {

			/*
			 * Set the deck's coordinates.
			 */
			card.setX(Deck.cardAtPosition(getState().getDiscardIndex()).getX());
			card.setY(Deck.cardAtPosition(getState().getDiscardIndex()).getY());

			/*
			 * Hide the previously discarded card (for faster repaint).
			 */
			Deck.cardAtPosition(getState().getDiscardIndex()).setInvisible();

			/*
			 * Flip the deck card.
			 */
			card.flip();

			/*
			 * Show the next deck card if it's not the last deck card.
			 */
			if (index != 28) {
				Deck.cardAtPosition(index - 1).setVisible();
			}

			/*
			 * Set the index of the discard pile.
			 */
			getState().setDiscardIndex(index);

			/*
			 * Reset the streak.
			 */
			getState().setStreak(0);

			/*
			 * If the third cheat is not on (no penalty cheat).
			 */
			if (getState().getCheats().contains(Cheat.NO_PENALTY) == false) {
				/*
				 * 5-point penalty.
				 */
				getState().setScore(
						getState().getScore() - Constants.NO_PENALTY_CHEAT);

				/*
				 * To the game score.
				 */
				getState().setGameScore(
						getState().getGameScore() - Constants.NO_PENALTY_CHEAT);

				/*
				 * And the session score.
				 */
				getState().setSessionScore(
						getState().getSessionScore()
								- Constants.NO_PENALTY_CHEAT);
			}

			/*
			 * Set the low score if score is lower.
			 */
			if (getState().getGameScore() < getState().getLowScore()) {
				getState().setLowScore(getState().getGameScore());
			}

			/*
			 * Decrement the number of cards in the deck.
			 */
			getState().setRemainingCards(getState().getRemainingCards() - 1);
		}
	}
}

