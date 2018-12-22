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

import java.util.Random;

/**
 * General constants class.
 * 
 * @author Todor Balabanov
 */
class Constants {

	/**
	 * Pseudo-random number generator.
	 */
	static final Random PRNG = new Random();

	/**
	 * Add 15-point bonus amount.
	 */
	static final int PEAK_BONUS = 15;

	/**
	 * Add 15-point bonus when all peaks are removed amount.
	 */
	static final int THREE_PEAKS_BONUS = 15;

	/**
	 * Subtract 5-point in no penalty amount.
	 */
	static final int NO_PENALTY_CHEAT = 5;

	/**
	 * Add 5 penalty for every card which was not removed.
	 */
	static final int CARD_REMOVED_PENALTY = 5;
}