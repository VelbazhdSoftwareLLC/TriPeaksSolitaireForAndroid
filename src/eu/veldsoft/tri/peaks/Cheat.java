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

/**
 * Possible cheats enumeration.
 * 
 * @author Todor Balabanov
 */
enum Cheat {

	/**
	 * All cards face up cheat.
	 */
	CARDS_FACE_UP(0),

	/**
	 * Any card collection cheat.
	 */
	CLICK_ANY_CARD(1),

	/**
	 * No penalty cheat constant.
	 */
	NO_PENALTY(2);

	/**
	 * Each constant has numeric representation.
	 */
	private int index;

	/**
	 * 
	 * @param index
	 */
	Cheat(int index) {
		this.index = index;
	}

	/**
	 * Constant numerical representation getter.
	 * 
	 * @return Numerical value of the constant.
	 * 
	 * @author Todor Balabanov
	 */
	public int getIndex() {
		return index;
	}
}
