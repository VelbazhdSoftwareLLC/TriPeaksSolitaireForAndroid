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

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
	 * 
	 */
	private Map<Integer, Integer> cardDrawableMapping = new HashMap<Integer, Integer>();

	/**
	 * 
	 */
	private DecimalFormat intFmt = new DecimalFormat("$###,###");

	/**
	 * 
	 */
	private DecimalFormat dblFmt = new DecimalFormat("$###,##0.00");

	/**
	 * the panel with the cards
	 */
	private CardBoard board = null;

	/**
	 * 
	 */
	private ImageView cardsViews[] = null;

	/**
	 * 
	 */
	private View.OnClickListener cardClickListener = new View.OnClickListener() {
		/**
		 * @param view
		 * 
		 * @author Todor Balabanov
		 */
		@Override
		public void onClick(View view) {
			/*
			 * Go through the cards in reverse order - the higher index-cards
			 * are on top. All the skips make execution of the mouse-click
			 * faster.
			 */
			int q = 0;
			for (q = Deck.SIZE - 1; q >= 0; q--) {
				Card card = Deck.cardAtPosition(q);

				/*
				 * If the card is invisible, skip it.
				 */
				if (card.isInvisible() == true) {
					continue;
				}

				/*
				 * If the card isn't part of the deck and is face-down, skip it.
				 */
				if (((q < 28) || (q == 51)) && card.isFacingDown() == true) {
					continue;
				}

				/*
				 * If the card is in the discard pile, skip it.
				 */
				if (q == board.getState().getDiscardIndex()) {
					continue;
				}

				/*
				 * If the click is not for the particular card, skip the rest.
				 */
				if (q < cardsViews.length && view == cardsViews[q]) {
					break;
				}

				/*
				 * If the clicked card is not the deck itself, skip it.
				 */
				if (view == findViewById(R.id.imageView30)) {
					break;
				}
			}

			/*
			 * Update board internal state.
			 */
			board.updateState(q);

			/*
			 * Repaint the board.
			 */
			repaint();
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
			((TextView) findViewById(R.id.textView13))
					.setVisibility(View.VISIBLE);
		} else {
			((TextView) findViewById(R.id.textView13))
					.setVisibility(View.INVISIBLE);
		}

		/*
		 * Go through each card.
		 */
		for (int q = 0; q < Deck.SIZE; q++) {
			Card card = Deck.cardAtPosition(q);

			/*
			 * If the card is in the discard pile, skip it.
			 */
			if (q == board.getState().getDiscardIndex()) {
				if (q < cardsViews.length) {
					cardsViews[q].setImageBitmap(null);
				}

				continue;
			}

			/*
			 * If the card is not visible, skip it.
			 */
			if (card.isInvisible() == true) {
				if (q < cardsViews.length) {
					cardsViews[q].setImageBitmap(null);
				}

				continue;
			}

			/*
			 * If the card is face-up.
			 */
			if (card.isFacingUp() == true
					|| board.getState().getCheats()
							.contains(Cheat.CARDS_FACE_UP) == true) {
				if (q < cardsViews.length) {
					cardsViews[q].setImageResource(cardDrawableMapping.get(card
							.getIndex()));
				}
			} else if (card.isFacingDown() == true) {
				if (q < cardsViews.length) {
					cardsViews[q].setImageResource(R.drawable.back03);
				}
			}
		}

		/*
		 * Display last discarded card.
		 */
		((ImageView) findViewById(R.id.imageView31))
				.setImageResource(cardDrawableMapping.get(Deck.cardAtPosition(
						board.getState().getDiscardIndex()).getIndex()));

		/*
		 * Display deal deck.
		 */
		if (board.getState().getRemainingCards() > 0) {
			((ImageView) findViewById(R.id.imageView30))
					.setImageResource(R.drawable.back03);
		} else {
			((ImageView) findViewById(R.id.imageView30)).setImageBitmap(null);
		}

		if (board.getState().getScore() < 0) {
			((TextView) findViewById(R.id.textView1))
					.setText(R.string.lost_label);
		} else {
			((TextView) findViewById(R.id.textView1))
					.setText(R.string.won_label);
		}

		int score = Math.abs(board.getState().getScore());
		((TextView) findViewById(R.id.textView14)).setText("" + score);

		((TextView) findViewById(R.id.textView15)).setText(""
				+ board.getState().getRemainingCards());

		int[] stats = board.getAllStats();
		((TextView) findViewById(R.id.textView16)).setText("" + stats[1]);

		((TextView) findViewById(R.id.textView17)).setText("" + stats[6]);
		((TextView) findViewById(R.id.textView18)).setText("" + stats[7]);

		((TextView) findViewById(R.id.textView19)).setText("" + stats[3]
				+ " = " + intFmt.format((stats[3] * (stats[3] + 1) / 2)));

		((TextView) findViewById(R.id.textView20)).setText(intFmt
				.format(stats[2]));

		double avg = 0.0;
		if (stats[5] != 0) {
			avg = ((double) stats[2]) / ((double) stats[5]);
		}
		((TextView) findViewById(R.id.textView21)).setText(dblFmt.format(avg));

		((TextView) findViewById(R.id.textView22)).setText("" + stats[5]);

		((TextView) findViewById(R.id.textView23)).setText("" + stats[4]);

		if (stats[4] != 0) {
			avg = ((double) stats[0]) / ((double) stats[4]);
		} else {
			avg = 0;
		}
		((TextView) findViewById(R.id.textView24)).setText(""
				+ dblFmt.format(avg));

		((TextView) findViewById(R.id.textView25)).setText("" + stats[8]
				+ " = " + intFmt.format((stats[8] * (stats[8] + 1) / 2)));

		if (board.getStatus().equals("") == false) {
			Toast.makeText(this, board.getStatus(), Toast.LENGTH_LONG).show();
			board.setStatus("");
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
	 * 
	 */
	@Override
	protected void onResume() {
		super.onResume();
		repaint();
	}

	/**
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		cardDrawableMapping.put(Deck.cardAtPosition(0).getIndex(),
				R.drawable.clubs1);
		cardDrawableMapping.put(Deck.cardAtPosition(1).getIndex(),
				R.drawable.clubs2);
		cardDrawableMapping.put(Deck.cardAtPosition(2).getIndex(),
				R.drawable.clubs3);
		cardDrawableMapping.put(Deck.cardAtPosition(3).getIndex(),
				R.drawable.clubs4);
		cardDrawableMapping.put(Deck.cardAtPosition(4).getIndex(),
				R.drawable.clubs5);
		cardDrawableMapping.put(Deck.cardAtPosition(5).getIndex(),
				R.drawable.clubs6);
		cardDrawableMapping.put(Deck.cardAtPosition(6).getIndex(),
				R.drawable.clubs7);
		cardDrawableMapping.put(Deck.cardAtPosition(7).getIndex(),
				R.drawable.clubs8);
		cardDrawableMapping.put(Deck.cardAtPosition(8).getIndex(),
				R.drawable.clubs9);
		cardDrawableMapping.put(Deck.cardAtPosition(9).getIndex(),
				R.drawable.clubs10);
		cardDrawableMapping.put(Deck.cardAtPosition(10).getIndex(),
				R.drawable.clubs11);
		cardDrawableMapping.put(Deck.cardAtPosition(11).getIndex(),
				R.drawable.clubs12);
		cardDrawableMapping.put(Deck.cardAtPosition(12).getIndex(),
				R.drawable.clubs13);
		cardDrawableMapping.put(Deck.cardAtPosition(13).getIndex(),
				R.drawable.hearts1);
		cardDrawableMapping.put(Deck.cardAtPosition(14).getIndex(),
				R.drawable.hearts2);
		cardDrawableMapping.put(Deck.cardAtPosition(15).getIndex(),
				R.drawable.hearts3);
		cardDrawableMapping.put(Deck.cardAtPosition(16).getIndex(),
				R.drawable.hearts4);
		cardDrawableMapping.put(Deck.cardAtPosition(17).getIndex(),
				R.drawable.hearts5);
		cardDrawableMapping.put(Deck.cardAtPosition(18).getIndex(),
				R.drawable.hearts6);
		cardDrawableMapping.put(Deck.cardAtPosition(19).getIndex(),
				R.drawable.hearts7);
		cardDrawableMapping.put(Deck.cardAtPosition(20).getIndex(),
				R.drawable.hearts8);
		cardDrawableMapping.put(Deck.cardAtPosition(21).getIndex(),
				R.drawable.hearts9);
		cardDrawableMapping.put(Deck.cardAtPosition(22).getIndex(),
				R.drawable.hearts10);
		cardDrawableMapping.put(Deck.cardAtPosition(23).getIndex(),
				R.drawable.hearts11);
		cardDrawableMapping.put(Deck.cardAtPosition(24).getIndex(),
				R.drawable.hearts12);
		cardDrawableMapping.put(Deck.cardAtPosition(25).getIndex(),
				R.drawable.hearts13);
		cardDrawableMapping.put(Deck.cardAtPosition(26).getIndex(),
				R.drawable.diamonds1);
		cardDrawableMapping.put(Deck.cardAtPosition(27).getIndex(),
				R.drawable.diamonds2);
		cardDrawableMapping.put(Deck.cardAtPosition(28).getIndex(),
				R.drawable.diamonds3);
		cardDrawableMapping.put(Deck.cardAtPosition(29).getIndex(),
				R.drawable.diamonds4);
		cardDrawableMapping.put(Deck.cardAtPosition(30).getIndex(),
				R.drawable.diamonds5);
		cardDrawableMapping.put(Deck.cardAtPosition(31).getIndex(),
				R.drawable.diamonds6);
		cardDrawableMapping.put(Deck.cardAtPosition(32).getIndex(),
				R.drawable.diamonds7);
		cardDrawableMapping.put(Deck.cardAtPosition(33).getIndex(),
				R.drawable.diamonds8);
		cardDrawableMapping.put(Deck.cardAtPosition(34).getIndex(),
				R.drawable.diamonds9);
		cardDrawableMapping.put(Deck.cardAtPosition(35).getIndex(),
				R.drawable.diamonds10);
		cardDrawableMapping.put(Deck.cardAtPosition(36).getIndex(),
				R.drawable.diamonds11);
		cardDrawableMapping.put(Deck.cardAtPosition(37).getIndex(),
				R.drawable.diamonds12);
		cardDrawableMapping.put(Deck.cardAtPosition(38).getIndex(),
				R.drawable.diamonds13);
		cardDrawableMapping.put(Deck.cardAtPosition(39).getIndex(),
				R.drawable.spades1);
		cardDrawableMapping.put(Deck.cardAtPosition(40).getIndex(),
				R.drawable.spades2);
		cardDrawableMapping.put(Deck.cardAtPosition(41).getIndex(),
				R.drawable.spades3);
		cardDrawableMapping.put(Deck.cardAtPosition(42).getIndex(),
				R.drawable.spades4);
		cardDrawableMapping.put(Deck.cardAtPosition(43).getIndex(),
				R.drawable.spades5);
		cardDrawableMapping.put(Deck.cardAtPosition(44).getIndex(),
				R.drawable.spades6);
		cardDrawableMapping.put(Deck.cardAtPosition(45).getIndex(),
				R.drawable.spades7);
		cardDrawableMapping.put(Deck.cardAtPosition(46).getIndex(),
				R.drawable.spades8);
		cardDrawableMapping.put(Deck.cardAtPosition(47).getIndex(),
				R.drawable.spades9);
		cardDrawableMapping.put(Deck.cardAtPosition(48).getIndex(),
				R.drawable.spades10);
		cardDrawableMapping.put(Deck.cardAtPosition(49).getIndex(),
				R.drawable.spades11);
		cardDrawableMapping.put(Deck.cardAtPosition(50).getIndex(),
				R.drawable.spades12);
		cardDrawableMapping.put(Deck.cardAtPosition(51).getIndex(),
				R.drawable.spades13);

		cardsViews = new ImageView[] {
				(ImageView) findViewById(R.id.imageView2),
				(ImageView) findViewById(R.id.imageView3),
				(ImageView) findViewById(R.id.imageView4),
				(ImageView) findViewById(R.id.imageView5),
				(ImageView) findViewById(R.id.imageView6),
				(ImageView) findViewById(R.id.imageView7),
				(ImageView) findViewById(R.id.imageView8),
				(ImageView) findViewById(R.id.imageView9),
				(ImageView) findViewById(R.id.imageView10),
				(ImageView) findViewById(R.id.imageView11),
				(ImageView) findViewById(R.id.imageView12),
				(ImageView) findViewById(R.id.imageView13),
				(ImageView) findViewById(R.id.imageView14),
				(ImageView) findViewById(R.id.imageView15),
				(ImageView) findViewById(R.id.imageView16),
				(ImageView) findViewById(R.id.imageView17),
				(ImageView) findViewById(R.id.imageView18),
				(ImageView) findViewById(R.id.imageView19),
				(ImageView) findViewById(R.id.imageView20),
				(ImageView) findViewById(R.id.imageView21),
				(ImageView) findViewById(R.id.imageView22),
				(ImageView) findViewById(R.id.imageView23),
				(ImageView) findViewById(R.id.imageView24),
				(ImageView) findViewById(R.id.imageView25),
				(ImageView) findViewById(R.id.imageView26),
				(ImageView) findViewById(R.id.imageView27),
				(ImageView) findViewById(R.id.imageView28),
				(ImageView) findViewById(R.id.imageView29) };

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

		((ImageView) findViewById(R.id.imageView100))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						GameActivity.this.startActivity(new Intent(
								Intent.ACTION_VIEW, Uri.parse(getResources()
										.getString(R.string.ebinqo_url))));

					}
				});

		board = new CardBoard();

		board.redeal();
		repaint();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_exit:
			GameActivity.this.finish();
			break;

		case R.id.menu_reset:
			/*
			 * Show a confirmation message.
			 */
			new AlertDialog.Builder(this)
					.setTitle("Confirm Game Reset")
					.setMessage(
							"Are you sure you want to reset your game?\nResetting results in a PERMANENT loss of score and stats!")
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setPositiveButton(android.R.string.yes,
							new DialogInterface.OnClickListener() {
								/*
								 * Do the penalty if the user agreed.
								 */
								public void onClick(DialogInterface dialog,
										int whichButton) {
									board.reset();
									board.redeal();
									repaint();
								}
							}).setNegativeButton(android.R.string.no, null)
					.show();
			break;

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
