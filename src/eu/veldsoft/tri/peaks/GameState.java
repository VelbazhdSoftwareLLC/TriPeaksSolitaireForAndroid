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

}
