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

import java.util.EnumSet;

class CardBoard {

	/**
	 * 
	 */
	private GameState state = new GameState();

	/**
	 * status text (used later)
	 */
	private String status = "";

	/**
	 * class constructor
	 */
	public CardBoard() {
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Redeals the cards.
	 */
	public void redeal() {
		Deck.shuffle();
		Deck.setAllVisible();
		Deck.deal();
		state.redeal();
	}

	/**
	 * resets everything
	 */
	public void reset() {
		Deck.setAllInvisible();

		/*
		 * essentially the same thing as the default values for the fields
		 */
		status = "";
		state.reset();
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
		if (state.getCheats().contains(Cheat.NO_PENALTY) == true) {
			return 0;
		}

		/*
		 * if there are cards in the deck AND in play, the penalty is $5 for
		 * every card removed
		 */
		if ((state.getCardsInPlay() == 0 || state.getRemainingCards() == 0) == false) {
			return (state.getCardsInPlay() * Constants.CARD_REMOVED_PENALTY);
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
		state.setScore(state.getScore() - penalty);

		/*
		 * from the session score
		 */
		state.setSessionScore(state.getSessionScore() - penalty);

		/*
		 * and from the game score
		 */
		state.setGameScore(state.getGameScore() - penalty);
	}

	/**
	 * returns the player's overall score
	 * 
	 * @return
	 */
	public int getScore() {
		return state.getScore();
	}

	/**
	 * returns the current game score
	 * 
	 * @return
	 */
	public int getGameScore() {
		return state.getGameScore();
	}

	/**
	 * returns the current streak
	 * 
	 * @return
	 */
	public int getStreak() {
		return state.getStreak();
	}

	/**
	 * returns the number of games played
	 * 
	 * @return
	 */
	public int getNumGames() {
		return state.getNumberOfGames();
	}

	/**
	 * returns the high score
	 * 
	 * @return
	 */
	public int getHighScore() {
		return state.getHighScore();
	}

	/**
	 * returns the low score
	 * 
	 * @return
	 */
	public int getLowScore() {
		return state.getLowScore();
	}

	/**
	 * returns the longest streak
	 * 
	 * @return
	 */
	public int getHighStreak() {
		return state.getHighStreak();
	}

	/**
	 * returns the session score
	 * 
	 * @return
	 */
	public int getSessionScore() {
		return state.getSessionScore();
	}

	/**
	 * returns the number of session games
	 * 
	 * @return
	 */
	public int getSessionGames() {
		return state.getNumberOfSessionGames();
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
		return (state.getCheats().isEmpty());
	}

	/**
	 * checks if player has ever cheated
	 * 
	 * @return
	 */
	public boolean hasCheated() {
		return state.isHasCheatedYet();
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
		return state.getCheats();
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
		state.setScore(stats[0]);

		/*
		 * overall score, high score, low score, number ofgames, and longest
		 * streak
		 */
		state.setHighScore(stats[1]);
		state.setLowScore(stats[2]);
		state.setNumberOfGames(stats[3]);
		state.setHighStreak(stats[4]);
	}

	/**
	 * set a cheat with the given index
	 * 
	 * @param cheat
	 * @param newState
	 */
	public void setCheat(Cheat cheat, boolean newState) {
		if (state.getCheats().contains(cheat) == false && newState == true) {
			state.getCheats().add(cheat);
		} else if (state.getCheats().contains(cheat) == true
				&& newState == false) {
			state.getCheats().remove(cheat);
		}

		/*
		 * if the cheat is turned on, set the "has cheated" flag
		 */
		if (newState == true) {
			state.setHasCheatedYet(true);
		}
	}

	/**
	 * set all the cheats in a given array
	 * 
	 * @param newCheats
	 */
	public void setCheats(EnumSet<Cheat> newCheats) {
		state.getCheats().clear();
		state.getCheats().addAll(newCheats);
	}

	/**
	 * set the cheated status for the player.
	 * 
	 * @param hasCheatedYet
	 */
	public void setCheated(boolean hasCheatedYet) {
		state.setHasCheatedYet(hasCheatedYet);
	}

}
