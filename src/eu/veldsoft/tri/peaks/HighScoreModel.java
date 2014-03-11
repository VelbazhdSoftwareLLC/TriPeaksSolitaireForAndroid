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
 * 
 * @author
 */
class HighScoreModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String[] columnNames = { "Player Name", "Score",
			"Average", "Most Won", "Most Lost", "Longest Streak", "# of games",
			"Has Cheated" };

	/**
	 * 
	 */
	public static Object[][] defaultPlrs = new Object[10][columnNames.length];
	
	/**
	 * 
	 */
	static {
		defaultPlrs[0][0] = "The Game";
		defaultPlrs[0][1] = new Integer(50000);
		defaultPlrs[0][2] = new Integer(150);
		defaultPlrs[0][3] = new Integer(-90);
		defaultPlrs[0][4] = new Integer(3500);
		defaultPlrs[0][5] = new Integer(17);
		defaultPlrs[0][6] = new Boolean(false);

		defaultPlrs[1][0] = "Bob";
		defaultPlrs[1][1] = new Integer(26392);
		defaultPlrs[1][2] = new Integer(160);
		defaultPlrs[1][3] = new Integer(-70);
		defaultPlrs[1][4] = new Integer(2501);
		defaultPlrs[1][5] = new Integer(18);
		defaultPlrs[1][6] = new Boolean(false);

		defaultPlrs[2][0] = "Linus T.";
		defaultPlrs[2][1] = new Integer(10000);
		defaultPlrs[2][2] = new Integer(157);
		defaultPlrs[2][3] = new Integer(-77);
		defaultPlrs[2][4] = new Integer(721);
		defaultPlrs[2][5] = new Integer(15);
		defaultPlrs[2][6] = new Boolean(false);

		defaultPlrs[3][0] = "Who am I";
		defaultPlrs[3][1] = new Integer(9876);
		defaultPlrs[3][2] = new Integer(200);
		defaultPlrs[3][3] = new Integer(-50);
		defaultPlrs[3][4] = new Integer(607);
		defaultPlrs[3][5] = new Integer(20);
		defaultPlrs[3][6] = new Boolean(false);

		defaultPlrs[4][0] = "Random";
		defaultPlrs[4][1] = new Integer(7694);
		defaultPlrs[4][2] = new Integer(404);
		defaultPlrs[4][3] = new Integer(0);
		defaultPlrs[4][4] = new Integer(20);
		defaultPlrs[4][5] = new Integer(24);
		defaultPlrs[4][6] = new Boolean(true);

		defaultPlrs[5][0] = "The CardMan";
		defaultPlrs[5][1] = new Integer(5000);
		defaultPlrs[5][2] = new Integer(137);
		defaultPlrs[5][3] = new Integer(-61);
		defaultPlrs[5][4] = new Integer(544);
		defaultPlrs[5][5] = new Integer(13);
		defaultPlrs[5][6] = new Boolean(false);

		defaultPlrs[6][0] = "The Sun";
		defaultPlrs[6][1] = new Integer(3000);
		defaultPlrs[6][2] = new Integer(128);
		defaultPlrs[6][3] = new Integer(-40);
		defaultPlrs[6][4] = new Integer(321);
		defaultPlrs[6][5] = new Integer(16);
		defaultPlrs[6][6] = new Boolean(false);

		defaultPlrs[7][0] = "CPU";
		defaultPlrs[7][1] = new Integer(1732);
		defaultPlrs[7][2] = new Integer(100);
		defaultPlrs[7][3] = new Integer(-79);
		defaultPlrs[7][4] = new Integer(109);
		defaultPlrs[7][5] = new Integer(12);
		defaultPlrs[7][6] = new Boolean(false);

		defaultPlrs[8][0] = "Your Creator";
		defaultPlrs[8][1] = new Integer(1000);
		defaultPlrs[8][2] = new Integer(99);
		defaultPlrs[8][3] = new Integer(-96);
		defaultPlrs[8][4] = new Integer(80);
		defaultPlrs[8][5] = new Integer(9);
		defaultPlrs[8][6] = new Boolean(false);

		defaultPlrs[9][0] = "Bright One";
		defaultPlrs[9][1] = new Integer(500);
		defaultPlrs[9][2] = new Integer(73);
		defaultPlrs[9][3] = new Integer(-109);
		defaultPlrs[9][4] = new Integer(25);
		defaultPlrs[9][5] = new Integer(10);
		defaultPlrs[9][6] = new Boolean(false);
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

	public Object getValueAt(int r, int c) {
		return data[r][c];
	}

	public Class<? extends Object> getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	public boolean readAndSetData() {
		File scoresDir = new File(TriPeaks.scoresDir);
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
				for (int w = 0; w < CardPanel.NSTATS; w++) {
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

				// Should never happen b/c we
				// are opening files listed
				// in a folder...
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
				tempList.add(defaultPlrs[q][w]);
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
