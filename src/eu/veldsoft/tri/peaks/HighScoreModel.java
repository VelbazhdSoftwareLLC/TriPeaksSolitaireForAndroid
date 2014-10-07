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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.table.AbstractTableModel;

/**
 * Start Base64 encoding and decoding code.**NOTE*** This is NOT my code. This
 * code was written by Christian d'Heureuse to provide a more standard base64
 * coder that's fast and efficient. As such, I won't provide comments for that
 * code. Java does NOT provide a Base64 encoder/decoder as part of the API.
 * 
 * @author Christian d'Heureuse
 */
class HighScoreModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Statistics labels.
	 */
	public static final String[] columnNames = { "Player Name", "Score",
			"Average", "Most Won", "Most Lost", "Longest Streak", "# of games",
			"Has Cheated" };

	/**
	 * 
	 */
	public static Object[][] defaultPlayers = new Object[10][columnNames.length];

	/**
	 * 
	 */
	static {
		defaultPlayers[0][0] = "The Game";
		defaultPlayers[0][1] = new Integer(50000);
		defaultPlayers[0][2] = new Integer(150);
		defaultPlayers[0][3] = new Integer(-90);
		defaultPlayers[0][4] = new Integer(3500);
		defaultPlayers[0][5] = new Integer(17);
		defaultPlayers[0][6] = new Boolean(false);

		defaultPlayers[1][0] = "Bob";
		defaultPlayers[1][1] = new Integer(26392);
		defaultPlayers[1][2] = new Integer(160);
		defaultPlayers[1][3] = new Integer(-70);
		defaultPlayers[1][4] = new Integer(2501);
		defaultPlayers[1][5] = new Integer(18);
		defaultPlayers[1][6] = new Boolean(false);

		defaultPlayers[2][0] = "Linus T.";
		defaultPlayers[2][1] = new Integer(10000);
		defaultPlayers[2][2] = new Integer(157);
		defaultPlayers[2][3] = new Integer(-77);
		defaultPlayers[2][4] = new Integer(721);
		defaultPlayers[2][5] = new Integer(15);
		defaultPlayers[2][6] = new Boolean(false);

		defaultPlayers[3][0] = "Who am I";
		defaultPlayers[3][1] = new Integer(9876);
		defaultPlayers[3][2] = new Integer(200);
		defaultPlayers[3][3] = new Integer(-50);
		defaultPlayers[3][4] = new Integer(607);
		defaultPlayers[3][5] = new Integer(20);
		defaultPlayers[3][6] = new Boolean(false);

		defaultPlayers[4][0] = "Random";
		defaultPlayers[4][1] = new Integer(7694);
		defaultPlayers[4][2] = new Integer(404);
		defaultPlayers[4][3] = new Integer(0);
		defaultPlayers[4][4] = new Integer(20);
		defaultPlayers[4][5] = new Integer(24);
		defaultPlayers[4][6] = new Boolean(true);

		defaultPlayers[5][0] = "The CardMan";
		defaultPlayers[5][1] = new Integer(5000);
		defaultPlayers[5][2] = new Integer(137);
		defaultPlayers[5][3] = new Integer(-61);
		defaultPlayers[5][4] = new Integer(544);
		defaultPlayers[5][5] = new Integer(13);
		defaultPlayers[5][6] = new Boolean(false);

		defaultPlayers[6][0] = "The Sun";
		defaultPlayers[6][1] = new Integer(3000);
		defaultPlayers[6][2] = new Integer(128);
		defaultPlayers[6][3] = new Integer(-40);
		defaultPlayers[6][4] = new Integer(321);
		defaultPlayers[6][5] = new Integer(16);
		defaultPlayers[6][6] = new Boolean(false);

		defaultPlayers[7][0] = "CPU";
		defaultPlayers[7][1] = new Integer(1732);
		defaultPlayers[7][2] = new Integer(100);
		defaultPlayers[7][3] = new Integer(-79);
		defaultPlayers[7][4] = new Integer(109);
		defaultPlayers[7][5] = new Integer(12);
		defaultPlayers[7][6] = new Boolean(false);

		defaultPlayers[8][0] = "Your Creator";
		defaultPlayers[8][1] = new Integer(1000);
		defaultPlayers[8][2] = new Integer(99);
		defaultPlayers[8][3] = new Integer(-96);
		defaultPlayers[8][4] = new Integer(80);
		defaultPlayers[8][5] = new Integer(9);
		defaultPlayers[8][6] = new Boolean(false);

		defaultPlayers[9][0] = "Bright One";
		defaultPlayers[9][1] = new Integer(500);
		defaultPlayers[9][2] = new Integer(73);
		defaultPlayers[9][3] = new Integer(-109);
		defaultPlayers[9][4] = new Integer(25);
		defaultPlayers[9][5] = new Integer(10);
		defaultPlayers[9][6] = new Boolean(false);
	}

	/**
	 * 
	 */
	private Object[][] data;

	/**
	 * @return
	 */
	public int getColumnCount() {
		return columnNames.length;
	}

	/**
	 * @return
	 */
	public int getRowCount() {
		return data.length;
	}

	/**
	 * @param c
	 * 
	 * @return
	 */
	public String getColumnName(int c) {
		return columnNames[c];
	}

	/**
	 * 
	 */
	public Object getValueAt(int r, int c) {
		return data[r][c];
	}

	/**
	 * 
	 */
	public Class<? extends Object> getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	/**
	 * 
	 * @return
	 */
	public boolean readAndSetData() {
		File scoresDir = new File(TriPeaks.SCORES_DIRECTORY);
		if (!scoresDir.isDirectory())
			return false;
		File[] scoreFiles = scoresDir.listFiles();

		ArrayList<ArrayList<Object>> scoreLists = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> plrScores;

		String fileName, deced, line;
		int dotIndex;
		Encryptor dec;
		for (int q = 0; q < scoreFiles.length; q++) {
			plrScores = new ArrayList<Object>();
			fileName = scoreFiles[q].getName();
			if (!fileName.endsWith(".txt"))
				continue;
			dotIndex = fileName.indexOf('.');
			dec = new Encryptor(TriPeaks.backward(fileName.substring(0,
					dotIndex)));
			plrScores.add(TriPeaks.rot13(fileName.substring(0, dotIndex)));
			BufferedReader in = null;
			try {
				in = new BufferedReader(new FileReader(scoreFiles[q]));
				for (int w = 0; w < CardPanel.NUMBR_OF_STATS; w++) {
					if ((line = in.readLine()) == null)
						break;
					deced = dec.decrypt(line);
					plrScores.add(new Integer(deced));
				}
				for (int w = 0; w < CardPanel.NCHEATS; w++) {
					if ((line = in.readLine()) == null)
						break;
				}
				if ((line = in.readLine()) == null)
					continue;
				deced = dec.decrypt(line);
				plrScores.add(new Boolean(deced));
				scoreLists.add(plrScores);

				/*
				 * Should never happen b/c we are opening files listed in a
				 * folder...
				 */
			} catch (FileNotFoundException eFNF) {
				System.out.println(eFNF.getMessage());
			} catch (IOException eIO) {
				System.out.println("Error reading from file -OR- closing file");
			} finally {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}

		int remDefPlrs = 10 - scoreLists.size();
		ArrayList<Object> tempList;
		for (int q = 0; q < remDefPlrs; q++) {
			tempList = new ArrayList<Object>();
			for (int w = 0; w < getColumnCount(); w++)
				tempList.add(defaultPlayers[q][w]);
			scoreLists.add(tempList);
		}
		data = new Object[scoreLists.size()][getColumnCount()];

		int q = 0;
		for (Iterator<ArrayList<Object>> it1 = scoreLists.iterator(); it1
				.hasNext(); q++) {
			ArrayList<Object> score = it1.next();
			data[q][0] = TriPeaks.capitalize((String) score.get(0));
			data[q][1] = score.get(1);
			if (((Integer) score.get(4)).intValue() != 0)
				data[q][2] = new Double(
						(double) ((Integer) score.get(1)).intValue()
								/ ((Integer) score.get(4)).intValue());
			else
				data[q][2] = new Double(0.0);
			data[q][3] = score.get(2);
			data[q][4] = score.get(3);
			data[q][5] = score.get(5);
			data[q][6] = score.get(4);
			data[q][7] = score.get(6);
		}

		return true;
	}
}
