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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 
 */
public class GameActivity extends Activity {

	/**
	 * 
	 */
	private View.OnClickListener cardClickListener = new View.OnClickListener() {
		/**
		 * 
		 */
		@Override
		public void onClick(View view) {
			Toast.makeText(GameActivity.this, "" + view, Toast.LENGTH_SHORT)
					.show();
		}
	};

	/**
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		((ImageView) findViewById(R.id.imageView3))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView4))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView5))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView6))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView7))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView8))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView9))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView10))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView11))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView12))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView13))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView14))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView15))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView16))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView17))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView18))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView19))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView20))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView21))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView22))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView23))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView24))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView25))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView26))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView27))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView28))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView29))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView30))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView31))
				.setOnClickListener(cardClickListener);
		((ImageView) findViewById(R.id.imageView32))
		.setOnClickListener(cardClickListener);
	}
}
