/*
 * This file is a part of Tri Peaks Solitaire for Android
 *
 * Copyright (C) 2013-2014 by Valera Trubachev, Christian d'Heureuse, Todor 
 * Balabanov, Ina Baltadzhieva, Maria Barova, Kamelia Ivanova, Victor Vangelov
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
 * 
 * @author Vasil Ivanov
 */
public class GameState {

	/**
	 * 
	 */
	public static final int NUMBR_OF_STATS = 5;

	/**
	 * 
	 */
	public static final int NUMBER_OF_CHEATS = 3;

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
	 * 
	 * @return
	 * 
	 * @author Stoyan Pavlov
	 */
	public EnumSet<Cheat> getCheats() {
		return cheats;
	}

	/**
	 * 
	 * @author Stoyan Pavlov
	 */
	public void setCheats(EnumSet<Cheat> cheats) {
		this.cheats = cheats;
	}

	/**
	 * 
	 * @return
	 * @author Strahil Terziyski
	 */
	public boolean isHasCheatedYet() {
		return hasCheatedYet;
	}

	/**
	 * 
	 * @param hasCheatedYet
	 * @author Strahil Terziyski
	 */
	public void setHasCheatedYet(boolean hasCheatedYet) {
		this.hasCheatedYet = hasCheatedYet;
	}

	/**
	 * 
	 * @return
	 * @author Strahil Terziyski
	 */
	public int getDiscardIndex() {
		return discardIndex;
	}

	/**
	 * 
	 * @param discardIndex
	 * @author Strahil Terziyski
	 */
	public void setDiscardIndex(int discardIndex) {
		this.discardIndex = discardIndex;
	}

	/**
	 * 
	 * @return
	 * @author Strahil Terziyski
	 */
	public int getScore() {
		return score;
	}

	/**
	 * 
	 * @param score
	 * @author Strahil Terziyski
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * 
	 * @return
	 * @author Strahil Terziyski
	 */
	public int getGameScore() {
		return gameScore;
	}

	/**
	 * 
	 * @param gameScore
	 * @author Strahil Terziyski
	 */
	public void setGameScore(int gameScore) {
		this.gameScore = gameScore;
	}

	/**
	 * 
	 * @return
	 * @author Strahil Terziyski
	 */
	public int getSessionScore() {
		return sessionScore;
	}

	/**
	 * 
	 * @param sessionScore
	 * @author Strahil Terziyski
	 */
	public void setSessionScore(int sessionScore) {
		this.sessionScore = sessionScore;
	}

	/**
	 * 
	 * @return
	 * @author Strahil Terziyski
	 */
	public int getStreak() {
		return streak;
	}

	/**
	 * 
	 * @param streak
	 * @author Strahil Terziyski
	 */
	public void setStreak(int streak) {
		this.streak = streak;
	}

	/**
	 * 
	 * @return
	 * @author Strahil Terziyski
	 */
	public int getRemainingCards() {
		return remainingCards;
	}

	/**
	 * 
	 * @param remainingCards
	 * @author Strahil Terziyski
	 */
	public void setRemainingCards(int remainingCards) {
		this.remainingCards = remainingCards;
	}

	/**
	 * 
	 * @return
	 * @author Strahil Terziyski
	 */
	public int getCardsInPlay() {
		return cardsInPlay;
	}

	/**
	 * 
	 * @param cardsInPlay
	 * @author Strahil Terziyski
	 */
	public void setCardsInPlay(int cardsInPlay) {
		this.cardsInPlay = cardsInPlay;
	}

	/**
	 * 
	 * @return
	 * @author Strahil Terziyski
	 */
	public int getRemainingPeaks() {
		return remainingPeaks;
	}

	/**
	 * 
	 * @param remainingPeaks
	 * @author Strahil Terziyski
	 */
	public void setRemainingPeaks(int remainingPeaks) {
		this.remainingPeaks = remainingPeaks;
	}

	/**
	 * 
	 * @return
	 * @author Strahil Terziyski
	 */
	public int getNumberOfGames() {
		return numberOfGames;
	}

	/**
	 * 
	 * @param numberOfGames
	 * @author Strahil Terziyski
	 */
	public void setNumberOfGames(int numberOfGames) {
		this.numberOfGames = numberOfGames;
	}

	/**
	 * 
	 * @return
	 * @author Strahil Terziyski
	 */
	public int getNumberOfSessionGames() {
		return numberOfSessionGames;
	}

	/**
	 * 
	 * @param numberOfSessionGames
	 * @author Strahil Terziyski
	 */
	public void setNumberOfSessionGames(int numberOfSessionGames) {
		this.numberOfSessionGames = numberOfSessionGames;
	}

	/**
	 * 
	 * @return
	 * @author Strahil Terziyski
	 */
	public int getHighScore() {
		return highScore;
	}

	/**
	 * 
	 * @param highScore
	 * @author Strahil Terziyski
	 */
	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}

	/**
	 * 
	 * @return
	 * @author Strahil Terziyski
	 */
	public int getLowScore() {
		return lowScore;
	}

	/**
	 * 
	 * @param lowScore
	 * @author Strahil Terziyski
	 */
	public void setLowScore(int lowScore) {
		this.lowScore = lowScore;
	}

	/**
	 * 
	 * @return
	 * @author Strahil Terziyski
	 */
	public int getHighStreak() {
		return highStreak;
	}

	/**
	 * 
	 * @param highStreak
	 * @author Strahil Terziyski
	 */
	public void setHighStreak(int highStreak) {
		this.highStreak = highStreak;
	}

	/**
	 * Reset to state object internal variables.
	 * 
	 * @author Todor Balabanov
	 */
	public void reset() {
		setDiscardIndex(51);
		setScore(0);
		setGameScore(0);
		setSessionScore(0);
		setStreak(0);
		setRemainingCards(0);
		setCardsInPlay(0);
		setRemainingPeaks(3);
		setNumberOfGames(0);
		setNumberOfSessionGames(0);
		setHighScore(0);
		setLowScore(0);
		setHighStreak(0);
		getCheats().clear();
		setHasCheatedYet(false);
	}

	/**
	 * Set state object internal variables after redeal.
	 * 
	 * @author Todor Balabanov
	 */
	public void redeal() {
		/*
		 * 23 cards left in the deck
		 */
		setRemainingCards(23);

		/*
		 * all 28 cards are in play
		 */
		setCardsInPlay(28);

		/*
		 * all three peaks are there
		 */
		setRemainingPeaks(3);

		/*
		 * the streak is reset
		 */
		setStreak(0);

		/*
		 * the game score is reset
		 */
		setGameScore(0);

		/*
		 * the discard pile index is back to 51
		 */
		setDiscardIndex(51);

		/*
		 * increment the number of games played
		 */
		setNumberOfGames(getNumberOfGames() + 1);

		/*
		 * increment the number of session games
		 */
		setNumberOfSessionGames(getNumberOfSessionGames() + 1);
	}

	/**
	 * Collect card from the peaks to the discard pile.
	 * 
	 * @param index
	 *            Index in the deck.
	 * 
	 * @author Todor Balabanov
	 */
	public void doValidMove(int index) {

		/*
		 * the card is now in the discard pile
		 */
		setDiscardIndex(index);

		/*
		 * increment the streak
		 */
		setStreak(getStreak() + 1);

		/*
		 * decrement the number of cards in play
		 */
		setCardsInPlay(getCardsInPlay() - 1);

		/*
		 * add the streak to the score
		 */
		setScore(getScore() + getStreak());

		/*
		 * and to the current game's score
		 */
		setGameScore(getGameScore() + getStreak());

		/*
		 * and to the session score
		 */
		setSessionScore(getSessionScore() + getStreak());

		/*
		 * set the high streak if it's higher
		 */
		if (getStreak() > getHighStreak()) {
			setHighStreak(getStreak());
		}

		/*
		 * set the high score if it's higher
		 */
		if (getGameScore() > getHighScore()) {
			setHighScore(getGameScore());
		}

		/*
		 * if it was a peak
		 */
		if (index < 3) {
			/*
			 * there's one less peak
			 */
			setRemainingPeaks(getRemainingPeaks() - 1);

			/*
			 * add a 15-point bonus
			 */
			setScore(getScore() + Constants.PEAK_BONUS);

			/*
			 * and to the game score
			 */
			setGameScore(getGameScore() + Constants.PEAK_BONUS);

			/*
			 * and to the session score
			 */
			setSessionScore(getSessionScore() + Constants.PEAK_BONUS);

			/*
			 * if all the peaks are gone
			 */
			if (getRemainingCards() == 0) {
				/*
				 * add another 15-point bonus (for a total of 30 bonus points)
				 */
				setScore(getScore() + Constants.THREE_PEAKS_BONUS);

				/*
				 * and to the game score
				 */
				setGameScore(getGameScore() + Constants.THREE_PEAKS_BONUS);

				/*
				 * and to the session score
				 */
				setSessionScore(getSessionScore() + Constants.THREE_PEAKS_BONUS);

				/*
				 * the remaining deck
				 */
				for (int w = 28; w < (getRemainingCards() + 28); w++) {
					/*
					 * hide the deck (so you can't take cards from the deck
					 * after you clear the board
					 */
					Deck.cardAtPosition(w).setInvisible();
				}
			}

			/*
			 * set the high score if the score is higher
			 */
			if (getGameScore() > getHighScore()) {
				setHighScore(getGameScore());
			}

		}
	}

}
