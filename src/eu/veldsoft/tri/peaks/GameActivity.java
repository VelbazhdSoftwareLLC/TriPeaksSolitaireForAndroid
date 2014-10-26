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

import java.awt.Color;
import java.awt.Font;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 */
public class GameActivity extends Activity {

	/**
	 * the panel with the cards
	 */
	private CardBoard board = null;

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
	 * Repaint all images.
	 * 
	 * @author Todor Balabanov
	 */
	private void repaint() {
		/*
		 * Draw the background.
		 */
		if (board.getState().isHasCheatedYet() == true) {
			((TextView)findViewById(R.id.textView13)).setVisibility(View.VISIBLE);
		} else {
			((TextView)findViewById(R.id.textView13)).setVisibility(View.INVISIBLE);
		}
	}
	
	/**
	 * 
	 * @param menu
	 * @return
	 * @author Todor Balabanov
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.layout.menu_game, menu);
		return true;
	}

	/**
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		((ImageView) findViewById(R.id.imageView2))
				.setOnClickListener(cardClickListener);
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

		board = new CardBoard();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_deal:

			/*
			 * Get the penalty for redealing if there is a penalty.
			 */
			if (board.getPenalty() != 0) {
				/*
				 * Show a confirmation message.
				 */
				new AlertDialog.Builder(this)
						.setTitle("Game Cancel Penalty")
						.setMessage("Do you really want to start new deal?")
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setPositiveButton(android.R.string.yes,
								new DialogInterface.OnClickListener() {
									/*
									 * Do the penalty if the user agreed.
									 */
									public void onClick(DialogInterface dialog,
											int whichButton) {
										board.doPenalty(board.getPenalty());
										board.redeal();
										repaint();
									}
								}).setNegativeButton(android.R.string.no, null)
						.show();
			} else {
				board.redeal();
				repaint();
			}

			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
