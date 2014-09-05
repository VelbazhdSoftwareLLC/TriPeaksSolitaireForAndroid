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

/*
 * import all the necessary stuff
 */
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URISyntaxException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Random;

/**
 * * it's a JFrame that listens to window events
 * 
 * Start Base64 encoding and decoding code.**NOTE*** This is NOT my code. This
 * code was written by Christian d'Heureuse to provide a more standard base64
 * coder that's fast and efficient. As such, I won't provide comments for that
 * code. Java does NOT provide a Base64 encoder/decoder as part of the API.
 * 
 * @author Christian d'Heureuse
 */
public class TriPeaks extends JFrame {

	/**
	 * 
	 */
	static final Random PRNG = new Random();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * all the letters of the alphabet
	 */
	private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";

	/**
	 * the panel with the cards
	 */
	private CardPanel board;

	/**
	 * the labels for the stats
	 */
	JLabel curGame;
	JLabel maxMin;
	JLabel curStr;
	JLabel sesWin;
	JLabel sesAvg;
	JLabel sesGame;
	JLabel plrGame;
	JLabel plrAvg;
	JLabel maxStr;

	/**
	 * 
	 */
	public static final String SCORES_DIRECTORY = "GevFpbef";

	/**
	 * the folder with the score files (ROT13 of TriScores)
	 * 
	 */
	private final String dirName = SCORES_DIRECTORY;

	/**
	 * 
	 */
	private final String SETTINGS_FILE_NAME = "TriSet";

	/**
	 * name of the player
	 */
	private String uName;

	/**
	 * the panel with the stats
	 */
	private JPanel statsPanel;

	/**
	 * 
	 */
	private JCheckBoxMenuItem[] cheatItems = new JCheckBoxMenuItem[CardPanel.NCHEATS];

	/**
	 * 
	 */
	private boolean seenWarn = false;

	/**
	 * 
	 */
	private JCheckBoxMenuItem statsCheck;

	/**
	 * returns an ImageIcon based on the path (ImageIcon implements Icon, and
	 * Image doesn't)
	 * 
	 * @param path
	 * @return
	 */
	private static ImageIcon getImageIcon(String path) {
		/*
		 * get the URL
		 */
		URL imgURL = TriPeaks.class.getResource(path);

		/*
		 * if the URL isn't null, return the ImageIcon otherwise return null
		 */
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		}

		return null;
	}

	/**
	 * returns an Image based on the path
	 * 
	 * @param path
	 * @return
	 */
	private static Image getIcon(String path) {
		/*
		 * gets the image icon based on the path
		 */
		ImageIcon img = getImageIcon(path);

		/*
		 * if the image icon isn't null, get the image from it otherwise return
		 * null
		 */
		if (img != null) {
			return img.getImage();
		}

		return null;
	}

	/**
	 * 
	 * @param sorter
	 * @param pattern
	 */
	private static void setRowFilter(TableRowSorter<HighScoreModel> sorter,
			String pattern) {
		RowFilter<HighScoreModel, Object> filter = null;
		try {
			filter = RowFilter.regexFilter(pattern);
		} catch (java.util.regex.PatternSyntaxException ePSE) {
			return;
		}
		sorter.setRowFilter(filter);
	}

	/**
	 * class constructor
	 * 
	 * @param title
	 */
	public TriPeaks(String title) {
		/*
		 * call the JFrame contructor
		 */
		super(title);
	}

	/**
	 * creates the menu bar
	 * 
	 * @return
	 */
	private JMenuBar createMenuBar() {
		/*
		 * init the menu bar
		 */
		JMenuBar menuBar = new JMenuBar();

		/*
		 * game menu
		 */
		JMenu gameMenu = new JMenu("Game");

		/*
		 * can be opened with Alt+G
		 */
		gameMenu.setMnemonic(KeyEvent.VK_G);

		/*
		 * the tool-tip text
		 */
		gameMenu.getAccessibleContext().setAccessibleDescription(
				"Game Playing and Operation");

		/*
		 * add the menu to the menu bar
		 */
		menuBar.add(gameMenu);

		/*
		 * redeal menu item
		 */
		JMenuItem deal = new JMenuItem("Deal");

		/*
		 * accessed with F2
		 */
		deal.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));

		/**
		 * add an action listener to it
		 */
		deal.addActionListener(new ActionListener() {
			/**
			 * 
			 */
			public void actionPerformed(ActionEvent e) {
				/*
				 * call the redeal method of the board
				 */
				board.redeal();
			}
		});

		/*
		 * add the menu item to the menu
		 */
		gameMenu.add(deal);

		/*
		 * switch players
		 */
		JMenuItem switchPlr = new JMenuItem("Switch Player...");

		/*
		 * Alt+P
		 */
		switchPlr.setMnemonic(KeyEvent.VK_P);

		/*
		 * Tool-tip text
		 */
		switchPlr.getAccessibleContext().setAccessibleDescription(
				"Change the current player");

		/**
		 * add an action listener
		 */
		switchPlr.addActionListener(new ActionListener() {
			/**
			 * @param e
			 */
			public void actionPerformed(ActionEvent e) {
				/*
				 * get the penalty for switching players
				 */
				int penalty = board.getPenalty();

				/*
				 * if there's some penalty
				 */
				if (penalty != 0) {
					/*
					 * show a confirmation dialog
					 */
					int uI = JOptionPane
							.showConfirmDialog(
									TriPeaks.this,
									"Are you sure you want to switch players?\nSwitching now results in a penalty of $"
											+ penalty + "!",
									"Confirm Player Switch",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.WARNING_MESSAGE);

					/*
					 * if the user clicked Yes, perform the penalty Otherwise,
					 * the user clicked No, so don't do anything
					 */
					if (uI == JOptionPane.YES_OPTION) {
						board.doPenalty(penalty);
					} else {
						return;
					}
				}

				/*
				 * ask for the user's name
				 */
				String tempName = JOptionPane.showInputDialog(TriPeaks.this,
						"Player Name:", uName);

				/*
				 * if it's not null or empty
				 */
				if ((tempName != null) && (!tempName.equals(""))) {
					/*
					 * write the current user's score
					 */
					writeScoreSets();
					board.reset();

					/*
					 * change the user
					 */
					uName = tempName;

					try {
						/*
						 * read the new user's scores
						 */
						readScoreSets();
					} catch (NewPlayerException eNP) {
						board.setDefaults();
					}

					updateStats();
					board.repaint();
				}
			}
		});

		/*
		 * add the item to the menu
		 */
		gameMenu.add(switchPlr);

		JMenuItem highScores = new JMenuItem("High Scores");
		highScores.setMnemonic(KeyEvent.VK_H);
		highScores.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
				ActionEvent.CTRL_MASK));
		highScores.getAccessibleContext().setAccessibleDescription(
				"Show high score table");

		/**
		 * 
		 */
		highScores.addActionListener(new ActionListener() {
			/**
			 * @param e
			 */
			public void actionPerformed(ActionEvent e) {
				final JDialog scoresDialog = new JDialog(TriPeaks.this,
						"High Scores", true);

				JPanel contentPanel = new JPanel();
				contentPanel.setLayout(new BoxLayout(contentPanel,
						BoxLayout.PAGE_AXIS));

				JLabel title = new JLabel("High Score Table");
				title.setFont(new Font("Serif", Font.BOLD, 20));
				title.setAlignmentX(Component.CENTER_ALIGNMENT);
				title.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				contentPanel.add(title);

				HighScoreModel hsModel = new HighScoreModel();
				writeScoreSets();

				if (!hsModel.readAndSetData()) {
					System.out.println("Error setting table values!");
				}

				/**
				 * 
				 */
				JTable scoreTable = new JTable(hsModel) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					/**
					 * 
					 */
					public String getToolTipText(MouseEvent evt) {
						String tip = null;
						Point p = evt.getPoint();
						if (rowAtPoint(p) == -1) {
							tip = super.getToolTipText(evt);
							return tip;
						}
						int r = convertRowIndexToModel(rowAtPoint(p));
						int c = convertColumnIndexToModel(columnAtPoint(p));
						HighScoreModel tm = (HighScoreModel) getModel();
						DecimalFormat format = null;

						if (getColumnClass(c) == Double.class)
							format = new DecimalFormat("$###,##0.00");
						else if (getColumnClass(c) == Integer.class)
							format = new DecimalFormat("$###,###");
						if (format == null)
							return super.getToolTipText(evt);
						switch (c) {
						case 1:
							int score = ((Integer) tm.getValueAt(r, 1))
									.intValue();
							tip = (String) tm.getValueAt(r, 0)
									+ " is "
									+ ((score < 0) ? "losing $" + -1 * score
											: "winning $" + score) + ".";
							break;
						case 2:
							double avg = ((Double) tm.getValueAt(r, 2))
									.doubleValue();
							tip = (String) tm.getValueAt(r, 0)
									+ "'s average is " + format.format(avg)
									+ " per game.";
							break;
						case 3:
							double max = (double) ((Integer) tm
									.getValueAt(r, 3)).intValue();
							tip = (String) tm.getValueAt(r, 0)
									+ " has won a maximum of "
									+ format.format(max) + " in one game.";
							break;
						case 4:
							int min = ((Integer) tm.getValueAt(r, 4))
									.intValue();
							tip = (String) tm.getValueAt(r, 0)
									+ " has lost a maximum of $" + -1 * min
									+ " in one game.";
							break;
						case 5:
							int maxStr = ((Integer) tm.getValueAt(r, 5))
									.intValue();
							tip = (String) tm.getValueAt(r, 0)
									+ "'s longest streak is " + maxStr
									+ " cards in a row ($"
									+ ((int) maxStr * (maxStr + 1) / 2) + ").";
							break;
						case 6:
							int nGames = ((Integer) tm.getValueAt(r, 6))
									.intValue();
							tip = (String) tm.getValueAt(r, 0) + " has played "
									+ nGames + " "
									+ ((nGames == 1) ? "game." : "games.");
							break;
						case 7:
							boolean cheater = ((Boolean) tm.getValueAt(r, 7))
									.booleanValue();
							tip = (String) tm.getValueAt(r, 0)
									+ ((cheater) ? " has cheated already."
											: " has never cheated yet.");
							break;
						default:
							tip = super.getToolTipText(evt);
						}
						return tip;
					}

					/**
					 * 
					 */
					protected JTableHeader createDefaultTableHeader() {
						return new JTableHeader(columnModel) {

							/**
							 * 
							 */
							private static final long serialVersionUID = 1L;

							/**
							 * 
							 */
							public String getToolTipText(MouseEvent evt) {
								Point p = evt.getPoint();
								int cF = columnModel.getColumnIndexAtX(p.x);
								int c = columnModel.getColumn(cF)
										.getModelIndex();
								switch (c) {
								case 0:
									return "The Player's Name.";
								case 1:
									return "The Player's current score.";
								case 2:
									return "The Player's per-game average.";
								case 3:
									return "The maximum the Player has won in one game.";
								case 4:
									return "The maximum the Player has lost in one game.";
								case 5:
									return "The Player's longest streak.";
								case 6:
									return "The number of games played by the Player.";
								case 7:
									return "Whether or not the Player has ever cheated.";
								default:
									return "";
								}
							}
						};
					}

					/**
					 * 
					 */
					public TableCellRenderer getCellRenderer(int r, int c) {
						if ((c >= 1) && (c <= 4))
							return new CurrencyRenderer();
						return super.getCellRenderer(r, c);
					}
				};

				scoreTable.setAutoCreateRowSorter(true);
				scoreTable
						.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				scoreTable.setPreferredScrollableViewportSize(new Dimension(
						500, 150));
				scoreTable.setFillsViewportHeight(true);

				/**
				 * 
				 */
				final TableRowSorter<HighScoreModel> sorter = new TableRowSorter<HighScoreModel>(
						hsModel);

				java.util.List<RowSorter.SortKey> keys = new ArrayList<RowSorter.SortKey>();
				keys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
				sorter.setSortKeys(keys);

				setRowFilter(sorter, "");

				scoreTable.setRowSorter(sorter);

				JScrollPane scoreScroll = new JScrollPane(scoreTable,
						JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
						JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				scoreScroll.setBorder(BorderFactory.createEmptyBorder(5, 5, 5,
						5));
				contentPanel.add(scoreScroll);

				JPanel searchPanel = new JPanel(new FlowLayout());

				// searchPanel.setLayout(new BoxLayout(searchPanel,
				// BoxLayout.LINE_AXIS));

				searchPanel.setBorder(BorderFactory.createTitledBorder(
						BorderFactory.createEtchedBorder(),
						"Search (All Columns)"));
				contentPanel.add(searchPanel);

				/**
				 * 
				 */
				final JTextField searchField = new JTextField();
				searchField.setHorizontalAlignment(JTextField.LEFT);
				searchField.setColumns(20);
				searchField.setAlignmentX(Component.LEFT_ALIGNMENT);
				searchField.getDocument().addDocumentListener(
						new DocumentListener() {

							/**
							 * 
							 */
							public void changedUpdate(DocumentEvent evt) {
								setRowFilter(sorter, searchField.getText());
							}

							/**
							 * 
							 */
							public void insertUpdate(DocumentEvent evt) {
								setRowFilter(sorter, searchField.getText());
							}

							/**
							 * 
							 */
							public void removeUpdate(DocumentEvent evt) {
								setRowFilter(sorter, searchField.getText());
							}
						});
				searchPanel.add(searchField);

				JButton clearSearch = new JButton("Clear");
				clearSearch.addActionListener(new ActionListener() {

					/**
					 * 
					 */
					public void actionPerformed(ActionEvent evt) {
						searchField.setText("");
					}
				});
				clearSearch.setAlignmentX(Component.LEFT_ALIGNMENT);
				searchPanel.add(clearSearch);

				JButton closeButton = new JButton("Close");
				closeButton.addActionListener(new ActionListener() {

					/**
					 * 
					 */
					public void actionPerformed(ActionEvent evt) {
						scoresDialog.setVisible(false);
						scoresDialog.dispose();
					}
				});
				closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
				contentPanel.add(closeButton);

				scoresDialog.setContentPane(contentPanel);
				scoresDialog.pack();
				scoresDialog.setLocationRelativeTo(TriPeaks.this);
				scoresDialog.setVisible(true);
			}
		});

		gameMenu.add(highScores);

		/*
		 * reset all stats/scores
		 */
		JMenuItem resetStats = new JMenuItem("Reset");

		/*
		 * Alt+R
		 */
		resetStats.setMnemonic(KeyEvent.VK_R);

		/*
		 * tooltip
		 */
		resetStats.getAccessibleContext().setAccessibleDescription(
				"Reset all stats and scores!");
		resetStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * show a confirmation dialog
				 */
				int uI = JOptionPane
						.showConfirmDialog(
								TriPeaks.this,
								"Are you sure you want to reset your game?\nResetting results in a PERMANENT loss of score and stats!",
								"Confirm Game Reset",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE);
				/*
				 * If the user clicked yes
				 */
				if (uI == JOptionPane.YES_OPTION) {
					/*
					 * reset the board
					 */
					board.reset();
					setTitle("TriPeaks");
				}
			}
		});

		/*
		 * add the item to the menu
		 */
		gameMenu.add(resetStats);

		/*
		 * add a separator to the menu
		 */
		gameMenu.addSeparator();

		/*
		 * exit the game
		 */
		JMenuItem exitGame = new JMenuItem("Exit");
		exitGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,

		/*
		 * accessed with Ctrl+Q
		 */
		ActionEvent.CTRL_MASK));

		/*
		 * tooltip
		 */
		getAccessibleContext().setAccessibleDescription("Exit the Game");
		exitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * get penalty for quitting
				 */
				int penalty = board.getPenalty();

				/*
				 * if there's a penalty, show the confirmation dialog
				 */
				if (penalty != 0) {
					/*
					 * the user agrees to the penalty
					 */
					int uI = JOptionPane.showConfirmDialog(TriPeaks.this,
							"Are you sure you want to exit?\nExiting now results in a penalty of $"
									+ penalty + "!", "Confirm Exit",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE);

					if (uI == JOptionPane.YES_OPTION) {
						/*
						 * perform the penalty
						 */
						board.doPenalty(penalty);
					} else {
						return;
					}
				}

				/*
				 * write the user's scores
				 */
				writeScoreSets();

				/*
				 * exit the program
				 */
				System.exit(0);
			}
		});

		/*
		 * add it to the menu
		 */
		gameMenu.add(exitGame);

		/*
		 * game options menu
		 */
		JMenu optionMenu = new JMenu("Options");

		/*
		 * accessed with Alt+O
		 */
		optionMenu.setMnemonic(KeyEvent.VK_O);

		/*
		 * set the tool-tip text
		 */
		optionMenu.getAccessibleContext().setAccessibleDescription(
				"Game Options");

		/*
		 * add it to the menu bar
		 */
		menuBar.add(optionMenu);

		/*
		 * Change the image that appears on the front and back of the cards
		 */
		JMenuItem cardStyle = new JMenuItem("Card Style");

		/*
		 * Alt+C
		 */
		cardStyle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.ALT_MASK));

		/*
		 * tooltip
		 */
		getAccessibleContext().setAccessibleDescription(
				"Change the picture on the front and back of the cards");

		/**
		 * add an action listener
		 */
		cardStyle.addActionListener(new ActionListener() {
			/**
			 * 
			 */
			public void actionPerformed(ActionEvent e) {
				/*
				 * create a dialog box for the style
				 */
				final JDialog styleDialog = new JDialog(TriPeaks.this,
						"Card Style");
				final String oldFront = board.getCardFront();
				final String oldBack = board.getCardBack();

				/*
				 * create a tabbed pane
				 */
				final JTabbedPane stylesTabs = new JTabbedPane();

				/**
				 * the action listener for the "back" buttons - one listener
				 * handles all
				 */
				ActionListener changeBack = new ActionListener() {
					/**
					 * 
					 */
					public void actionPerformed(ActionEvent evt) {
						/*
						 * the action command is set when the button is created
						 */
						board.setCardBack(evt.getActionCommand());
						/*
						 * repaint - with the new style
						 */
						board.repaint();
					}
				};

				/*
				 * the folder with the back designs
				 */
				File backsDir = null;
				try {
					backsDir = new File(TriPeaks.class.getResource("CardSets")
							.toURI() + File.separator + "Backs");
				} catch (URISyntaxException e1) {
				}

				/*
				 * if the folder doesn't exist or isn't a folder
				 */
				if ((!backsDir.exists()) || (!backsDir.isDirectory())) {
					/*
					 * give an error stop the execution
					 */
					JOptionPane.showMessageDialog(
							TriPeaks.this,
							"Invalid Structure for Card folders: "
									+ backsDir.getPath());
					return;
				}

				/*
				 * get the list of files in the folder
				 */
				File[] backFiles = backsDir.listFiles();

				/*
				 * create and ArrayList of Toggle Buttons
				 */
				final ArrayList<JToggleButton> backButtons = new ArrayList<JToggleButton>();

				/*
				 * fileName is the path of the image. picName is the image name
				 * (w/o extension)
				 */
				String fileName, picName;

				/*
				 * a placeholder for the button
				 */
				JToggleButton newBut;

				/*
				 * a button group for the toggle buttons (so only one can be
				 * selected)
				 */
				ButtonGroup backGroup = new ButtonGroup();

				/*
				 * go through each file in the folder
				 */
				for (int q = 0; q < backFiles.length; q++) {
					/*
					 * if the file isn't a .png, skip it
					 */
					if (!backFiles[q].getName().endsWith(".png")) {
						continue;
					}

					/*
					 * the path to the image
					 */
					fileName = backFiles[q].toString();
					/*
					 * the file name, w/o extension
					 */
					picName = backFiles[q].getName().substring(0,
							backFiles[q].getName().length() - 4);

					/*
					 * create a new toggle button for the image, no text, with
					 * the image, unselected
					 */
					newBut = new JToggleButton(getImageIcon(fileName), false);

					/*
					 * if that's the current back, select the button
					 */
					if (picName.equals(board.getCardBack())) {
						newBut.setSelected(true);
					}

					/*
					 * set the buttons action command - used by the action
					 * listener to determine what to use for setting the image
					 */
					newBut.setActionCommand(picName);

					/*
					 * add the action listener to the button - created before
					 */
					newBut.addActionListener(changeBack);

					/*
					 * tool-tip is the image name
					 */
					newBut.getAccessibleContext().setAccessibleDescription(
							picName);

					/*
					 * add the button to the group, which handles selection
					 */
					backGroup.add(newBut);

					/*
					 * add the button to the arrayList
					 */
					backButtons.add(newBut);
				}

				/*
				 * action listener for changing the front
				 */
				ActionListener changeFront = new ActionListener() {
					/*
					 * 
					 */
					public void actionPerformed(ActionEvent evt) {
						/*
						 * use the action command to set the front design
						 */
						board.setCardFront(evt.getActionCommand());

						/*
						 * repaint with the new design
						 */
						board.repaint();
					}
				};

				/*
				 * the folder with the fronts
				 */
				File frontsDir = null;
				try {
					frontsDir = new File(TriPeaks.class.getResource("CardSets")
							.toURI() + File.separator + "Fronts");
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}

				/*
				 * if the folder doesn't exist or isn't a folder
				 */
				if ((!frontsDir.exists()) || (!frontsDir.isDirectory())) {
					/*
					 * error message stop the creation of the dialog
					 */
					JOptionPane.showMessageDialog(
							TriPeaks.this,
							"Invalid Structure for Card folders: "
									+ frontsDir.getPath());
					return;
				}

				/*
				 * get the list of files in the folder
				 */
				File[] frontsDirs = frontsDir.listFiles();
				/*
				 * re-instantiate the arraylist
				 */
				final ArrayList<JToggleButton> frontButtons = new ArrayList<JToggleButton>();

				/*
				 * a placeholder for the random card value
				 */
				int randCard;

				/*
				 * previewName is the path to the preview image, dirName is the
				 * name of the folder with the card styles
				 */
				String previewName, dirName;

				/*
				 * a button group for the "front" buttons
				 */
				ButtonGroup frontGroup = new ButtonGroup();

				/*
				 * go through each file in the Fronts folder
				 */
				for (int q = 0; q < frontsDirs.length; q++) {
					/*
					 * if it's a file (not a directory), skip it
					 */
					if (!frontsDirs[q].isDirectory()) {
						continue;
					}

					/*
					 * the name of the folder
					 */
					dirName = frontsDirs[q].getName();

					/*
					 * generate a random value to get the random card
					 */
					randCard = PRNG.nextInt(52);
					String suit = null;
					if (randCard < 13) {
						suit = Card.Suit.CLUBS.toString();
					} else if (randCard < 26) {
						suit = Card.Suit.HEARTS.toString();
					} else if (randCard < 39) {
						suit = Card.Suit.DIAMONDS.toString();
					} else if (randCard < 52) {
						suit = Card.Suit.SPADES.toString();
					}

					/*
					 * get a random card from the folder
					 */
					previewName = frontsDirs[q].toString() + File.separator
							+ suit + ((randCard % 13) + 1) + ".png";

					/*
					 * create the button, with the random card as the image, no
					 * text, and not selected
					 */
					newBut = new JToggleButton(getImageIcon(previewName), false);

					/*
					 * if the style is current, make the button selected
					 */
					if (dirName.equals(board.getCardFront())) {
						newBut.setSelected(true);
					}

					/*
					 * set the action command as the folder name
					 */
					newBut.setActionCommand(dirName);

					/*
					 * add the action listener to the button
					 */
					newBut.addActionListener(changeFront);

					/*
					 * set the tool tip text to the folder name
					 */
					newBut.getAccessibleContext().setAccessibleDescription(
							dirName);

					/*
					 * add the button to the group
					 */
					frontGroup.add(newBut);

					/*
					 * and to the array list
					 */
					frontButtons.add(newBut);
				}

				/*
				 * generate the best dimensions for the grid layout
				 */
				int[] backDims = genGrid(backButtons.size());

				/*
				 * create a panel to hold the buttons, using grid layout with
				 * the best-dimensions
				 */
				JPanel backsPanel = new JPanel(new GridLayout(backDims[0],
						backDims[1]));

				/*
				 * go through the arrayList, and add each button to the panel
				 */
				for (Iterator<JToggleButton> it = backButtons.iterator(); it
						.hasNext();) {
					backsPanel.add(it.next());
				}

				/*
				 * generate a new grid for these buttons
				 */
				int[] frontDims = genGrid(frontButtons.size());

				/*
				 * create the panel with the best grid
				 */
				JPanel frontsPanel = new JPanel(new GridLayout(frontDims[0],
						frontDims[1]));

				/*
				 * go through the buttons and add each to the panel
				 */
				for (Iterator<JToggleButton> it = frontButtons.iterator(); it
						.hasNext();) {
					frontsPanel.add(it.next());
				}

				/*
				 * the effective dimensions
				 */
				int[] useDims = new int[2];

				/*
				 * use the greater dimension (x)
				 */
				if (backDims[0] > frontDims[0]) {
					useDims[0] = backDims[0];
				} else {
					useDims[0] = frontDims[0];
				}

				/*
				 * use the greater dimension (y)
				 */
				if (backDims[1] > frontDims[1]) {
					useDims[1] = backDims[1];
				} else {
					useDims[1] = frontDims[1];
				}

				/*
				 * set the panel sizes with the effective dimensions
				 */
				backsPanel.setPreferredSize(new Dimension(useDims[1]
						* (Card.WIDTH + 15), useDims[0] * (Card.HEIGHT + 15)));

				frontsPanel.setPreferredSize(new Dimension(useDims[1]
						* (Card.WIDTH + 15), useDims[0] * (Card.HEIGHT + 15)));

				/*
				 * add a tab to the tabbed panel - Backs is the tab text. give
				 * it an icon, use the panel as the tab content and Card Backs
				 * for the tooltip
				 */
				stylesTabs.addTab("Backs", getImageIcon("Images"
						+ File.separator + "Back.png"), backsPanel,
						"Card Backs");

				/*
				 * the tab can be accessed with Alt+B
				 */
				stylesTabs.setMnemonicAt(0, KeyEvent.VK_B);

				/*
				 * same thing - add a tab for the front styles
				 */
				stylesTabs.addTab("Fronts", getImageIcon("Images"
						+ File.separator + "Front.png"), frontsPanel,
						"Card Fronts");

				/*
				 * Alt+F
				 */
				stylesTabs.setMnemonicAt(1, KeyEvent.VK_F);

				/*
				 * button to close the dialog
				 */
				JButton closeButton = new JButton("Close");

				closeButton.addActionListener(new ActionListener() {
					/*
					 * 
					 */
					public void actionPerformed(ActionEvent evt) {
						/*
						 * hide the dialog
						 */
						styleDialog.setVisible(false);

						/*
						 * let go of the resources for the dialog
						 */
						styleDialog.dispose();
					}
				});

				/*
				 * revert button
				 */
				JButton revertButton = new JButton("Revert");
				revertButton.addActionListener(new ActionListener() {
					/*
					 * 
					 */
					public void actionPerformed(ActionEvent evt) {
						/*
						 * set the old values
						 */
						board.setCardBack(oldBack);
						board.setCardFront(oldFront);

						/*
						 * repaint
						 */
						board.repaint();

						/*
						 * hide the dialog
						 */
						styleDialog.setVisible(false);

						/*
						 * dispose of the dialog
						 */
						styleDialog.dispose();
					}
				});

				/*
				 * create a panel for the buttons
				 */
				JPanel buttonPanel = new JPanel(new FlowLayout());
				buttonPanel.add(revertButton);

				/*
				 * add the buttons to the panel
				 */
				buttonPanel.add(closeButton);

				/*
				 * create a new panel to be the content panel
				 */
				JPanel contentPanel = new JPanel(new BorderLayout(5, 5));

				/*
				 * add the tabbed pane to the panel
				 */
				contentPanel.add(stylesTabs, BorderLayout.CENTER);

				/*
				 * add the panel with the close-button to the panel
				 */
				contentPanel.add(buttonPanel, BorderLayout.PAGE_END);

				/*
				 * paint all the pixels, don't skip any
				 */
				contentPanel.setOpaque(true);

				/*
				 * the the content pane of the dialog box
				 */
				styleDialog.setContentPane(contentPanel);

				/*
				 * pack the dialog
				 */
				styleDialog.pack();

				/*
				 * set the location relative to the frame (in its center)
				 */
				styleDialog.setLocationRelativeTo(TriPeaks.this);

				/*
				 * show the dialog
				 */
				styleDialog.setVisible(true);
			}
		});

		/*
		 * add it to the menu
		 */
		optionMenu.add(cardStyle);

		/*
		 * change the boackground color of the board
		 */
		JMenuItem boardColor = new JMenuItem("Board Background");

		/*
		 * Alt+B
		 */
		boardColor.setMnemonic(KeyEvent.VK_B);

		/*
		 * tool-tip
		 */
		boardColor.getAccessibleContext().setAccessibleDescription(
				"Change the Background Color of the board");
		boardColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * show a color chooser, with the current color as the default
				 */
				Color newColor = JColorChooser.showDialog(TriPeaks.this,
				"Choose Background Color", board.getBackColor());

				/*
				 * if the user didn't click Cancel, set the color
				 */
				if (newColor != null){
					board.setBackColor(newColor);
				}
				
				/*
				 * repaint the baord.
				 */
				board.repaint();
			}
		});
		
		/*
		 * add the item to the menu
		 */
		optionMenu.add(boardColor);
		
		/*
		 * change the font of the text on the board
		 */
		JMenuItem fontSelect = new JMenuItem("Text Font");
		
		/*
		 * Alt+F
		 */
		fontSelect.setMnemonic(KeyEvent.VK_F);
		
		/*
		 * tool-tip text
		 */
		fontSelect.getAccessibleContext().setAccessibleDescription(
				"Change the font of the text on the board");
		
		fontSelect.addActionListener(new ActionListener() {

			/*
			 * 
			 */
			public void actionPerformed(ActionEvent e) {
				/*
				 * create the dialog
				 */
				final JDialog fontDialog = new JDialog(TriPeaks.this,
						"Choose Board Font", true);
				
				/*
				 * get the old color - in order to revert
				 */
				final Color oldColor = board.getFontColor();
				
				/*
				 * get the old color
				 */
				final Font oldFont = board.getTextFont();
				
				/*
				 * a panel to hold everything
				 */
				JPanel contentPanel = new JPanel();
				
				/*
				 * align stuff on the y-axis
				 */
				contentPanel.setLayout(new BoxLayout(contentPanel,
						BoxLayout.PAGE_AXIS));
				
				/*
				 * a title
				 */
				JLabel title = new JLabel("Font Chooser");
				
				/*
				 * make it big & bold
				 */
				title.setFont(new Font("Serif", Font.BOLD, 20));
				
				/*
				 * center it
				 */
				title.setAlignmentX(Component.CENTER_ALIGNMENT);
				
				/*
				 * give it 5 pixels padding on each side
				 */
				title.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				
				/*
				 * add it to the main panel
				 */
				contentPanel.add(title);
				
				/*
				 * the selection panel
				 */
				JPanel selPanel = new JPanel(new FlowLayout());
				
				/*
				 * add it to the main panel
				 */
				contentPanel.add(selPanel);
				
				/*
				 * a preview label - very important. All values are "stored" in
				 * it because any change is reflected in the label
				 */
				final JLabel preview = new JLabel("TriPeaks = Good Game");
				
				/*
				 * set the old font (current)
				 */
				preview.setFont(oldFont);
				
				/*
				 * make the label opaque
				 */
				preview.setOpaque(true);
				
				/*
				 * set the color of the text
				 */
				preview.setForeground(oldColor);
				
				/*
				 * set the background as the background color of the board
				 */
				preview.setBackground(board.getBackColor());
				
				/*
				 * give it 3 px. padding on each side
				 */
				preview.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
				
				/*
				 * center-align it
				 */
				preview.setAlignmentX(Component.CENTER_ALIGNMENT);

				/*
				 * get a list of available fonts
				 */
				final String[] fonts = GraphicsEnvironment
						.getLocalGraphicsEnvironment()
						.getAvailableFontFamilyNames();
				
				/*
				 * initial selection index
				 */
				int selIndex = 0;
				
				/*
				 * go through available fonts
				 */
				for (int q = 0; q < fonts.length; q++) {
					/*
					 * find the old font's index
					 */
					if (oldFont.getFamily().equals(fonts[q])) {
						selIndex = q;
					}
				}
				
				/*
				 * a list for the fonts
				 */
				JList<Object> fontList = new JList<Object>(fonts);
				
				/*
				 * add a list selection listener (when the selection changes)
				 */
				fontList.addListSelectionListener(new ListSelectionListener() {

					/*
					 * 
					 */
					public void valueChanged(ListSelectionEvent evt) {
						/*
						 * if the user isn't done selecting, don't do
						 * anything
						 */
						if (evt.getValueIsAdjusting()) {
							return;
						}
						
						/*
						 * get the new selection index
						 */
						int selected = evt.getLastIndex();
						
						/*
						 * get the bold and italic status of the preview
						 */
						int bold = (preview.getFont().isBold()) ? Font.BOLD : 0;
						int ital = (preview.getFont().isItalic()) ? Font.ITALIC
								: 0;
						
						/*
						 * get the font size of the preview
						 */
						int size = preview.getFont().getSize();
						
						/*
						 * set the new font
						 */
						preview.setFont(new Font(fonts[selected], bold | ital,
								size));
					}
				});
				
				/*
				 * only one font can be selected
				 */
				fontList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
				/*
				 * set the initial selection index
				 */
				fontList.setSelectedIndex(selIndex);
				
				/*
				 * give it a vertical orientation (all in one column)
				 */
				fontList.setLayoutOrientation(JList.VERTICAL);
				
				/*
				 * 10 items are visible
				 */
				fontList.setVisibleRowCount(10);
				
				/*
				 * give the list scroll bars
				 */
				JScrollPane fontScroll = new JScrollPane(fontList);
				
				/*
				 * give the scroll pane an etched border with the title "Font"
				 */
				fontScroll.setBorder(BorderFactory.createTitledBorder(
						BorderFactory.createEtchedBorder(), "Font"));
				
				/*
				 * add it to the selection panel
				 */
				selPanel.add(fontScroll);
				
				/*
				 * scroll so the initial selection is visible
				 */
				fontList.ensureIndexIsVisible(selIndex);
				
				/*
				 * a panel for other stuff
				 */
				JPanel otrPanel = new JPanel();
				
				/*
				 * align stuff on the y-axis
				 */
				otrPanel.setLayout(new BoxLayout(otrPanel, BoxLayout.PAGE_AXIS));
				
				/*
				 * give the panel a border (etched, with title)
				 */
				otrPanel.setBorder(BorderFactory.createTitledBorder(
						BorderFactory.createEtchedBorder(), "Other Options"));
				
				/*
				 * add it to the selection panel
				 */
				selPanel.add(otrPanel);
				
				/*
				 * a label for the size spinner
				 */
				JLabel sizeLabel = new JLabel("Size:");
				
				/*
				 * left-align it.
				 */
				sizeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
				
				/*
				 * add it to the panel
				 */
				otrPanel.add(sizeLabel);
				
				/*
				 * create a spinner model - from 8 to 18 by 1's, starting at the
				 * current size.
				 */
				SpinnerModel sizeSpinModel = new SpinnerNumberModel(oldFont
						.getSize(), 8, 18, 1);
				
				/*
				 * the spinner (final because it's accessed in a nested class
				 */
				final JSpinner sizeSpin = new JSpinner(sizeSpinModel);
				
				/*
				 * add a listener for changes
				 */
				sizeSpin.addChangeListener(new ChangeListener() {

					/*
					 * 
					 */
					public void stateChanged(ChangeEvent evt) {
						/*
						 * get the spinner's model
						 */
						SpinnerNumberModel model = (SpinnerNumberModel) sizeSpin
								.getModel();
						
						/*
						 * get the font, bold, and italic status from the
						 * preview
						 */
						String fontName = preview.getFont().getFamily();
						int bold = (preview.getFont().isBold()) ? Font.BOLD : 0;
						int ital = (preview.getFont().isItalic()) ? Font.ITALIC
								: 0;
						
						/*
						 * get the new size from the spinner model
						 */
						int size = model.getNumber().intValue();
						
						/*
						 * set the font on the preview
						 */
						preview.setFont(new Font(fontName, bold | ital, size));
					}
				});
				
				/*
				 * get the text field part of the spinner
				 */
				JFormattedTextField textField = ((JSpinner.DefaultEditor) sizeSpin
						.getEditor()).getTextField();
				
				/*
				 * 4 columns is more that adequate
				 */
				textField.setColumns(4);
				
				/*
				 * left-align the number
				 */
				textField.setHorizontalAlignment(JTextField.LEFT);
				
				/*
				 * left-align the spinner
				 */
				sizeSpin.setAlignmentX(Component.LEFT_ALIGNMENT);
				
				/*
				 * add it to the panel
				 */
				otrPanel.add(sizeSpin);
				
				/*
				 * a checkbox for the bold status, with the old status as the
				 * default
				 */
				JCheckBox boldCheck = new JCheckBox("Bold", oldFont.isBold());
				
				/*
				 * add a listener
				 */
				boldCheck.addItemListener(new ItemListener() {

					/*
					 * 
					 */
					public void itemStateChanged(ItemEvent evt) {
						/*
						 * get the stuff from the preview panel (except bold)
						 */
						String fontName = preview.getFont().getFamily();
						
						/*
						 * set it to bold if the checkbox was checked
						 */
						int bold = (evt.getStateChange() == ItemEvent.SELECTED) ? Font.BOLD
								: 0;
						int ital = (preview.getFont().isItalic()) ? Font.ITALIC
								: 0;
						int size = preview.getFont().getSize();
						
						/*
						 * set the new font
						 */
						preview.setFont(new Font(fontName, bold | ital, size));
					}
				});
				
				/*
				 * left-align the checkbox
				 */
				boldCheck.setAlignmentX(Component.LEFT_ALIGNMENT);
				
				/*
				 * add it to the panel
				 */
				otrPanel.add(boldCheck);
				
				/*
				 * a checkbox for the italic status - same as above
				 */
				JCheckBox italCheck = new JCheckBox("Italic", oldFont
						.isItalic());
				
				italCheck.addItemListener(new ItemListener() {

					/*
					 * 
					 */
					public void itemStateChanged(ItemEvent evt) {
						String fontName = preview.getFont().getFamily();
						int bold = (preview.getFont().isBold()) ? Font.BOLD : 0;
						int ital = (evt.getStateChange() == ItemEvent.SELECTED) ? Font.ITALIC
								: 0;
						int size = preview.getFont().getSize();
						preview.setFont(new Font(fontName, bold | ital, size));
					}
				});
				
				italCheck.setAlignmentX(Component.LEFT_ALIGNMENT);
				otrPanel.add(italCheck);
				
				/*
				 * a button to select the text color
				 */
				final JButton colorBut = new JButton("Font Color");
				
				/*
				 * add an action listener
				 */
				colorBut.addActionListener(new ActionListener() {

					/*
					 * 
					 */
					public void actionPerformed(ActionEvent evt) {
						/*
						 * show a color chooser - default color is the current
						 * color
						 */
						Color newColor = JColorChooser.showDialog(
								TriPeaks.this, "Choose Font Color",
								preview.getForeground());
						
						/*
						 * if the user didn't click 'Cancel'
						 */
						if (newColor != null) {
							/*
							 * set the text color on the button
							 */
							colorBut.setForeground(newColor);
							
							/*
							 * and on the preview label
							 */
							preview.setForeground(newColor);
						}
					}
				});
				
				/*
				 * set the default text color
				 */
				colorBut.setForeground(oldColor);
				
				/*
				 * set the background color of the button
				 */
				colorBut.setBackground(board.getBackColor());
				
				/*
				 * left-align the button
				 */
				colorBut.setAlignmentX(Component.LEFT_ALIGNMENT);
				
				/*
				 * add it to the panel
				 */
				otrPanel.add(colorBut);
				
				/*
				 * a panel for the preview label (so the label's background
				 * color works properly)
				 */
				JPanel previewPanel = new JPanel();
				
				/*
				 * align stuff on the y-axis
				 */
				previewPanel.setLayout(new BoxLayout(previewPanel,
						BoxLayout.PAGE_AXIS));
				
				/*
				 * add the label to it
				 */
				previewPanel.add(preview);
				
				/*
				 * give it an etched, titled border.
				 */
				previewPanel.setBorder(BorderFactory.createTitledBorder(
						BorderFactory.createEtchedBorder(), "Preview"));
				
				/*
				 * add it to the main panel
				 */
				contentPanel.add(previewPanel);
				
				/*
				 * a panel to hold the buttons
				 */
				JPanel buttonPanel = new JPanel(new FlowLayout());
				
				/*
				 * OK button
				 */
				JButton closeButton = new JButton("OK");
				
				/*
				 * tool-tip text
				 */
				closeButton.getAccessibleContext().setAccessibleDescription(
						"Apply the font and close");
				
				closeButton.addActionListener(new ActionListener() {

					/*
					 * 
					 */
					public void actionPerformed(ActionEvent evt) {
						/*
						 * set the font color
						 */
						board.setFontColor(preview.getForeground());
						
						/*
						 * set the font
						 */
						board.setTextFont(preview.getFont());
						
						/*
						 * repaint the board
						 */
						board.repaint();
						
						/*
						 * hide the dialog
						 */
						fontDialog.setVisible(false);
						
						/*
						 * dispose of it
						 */
						fontDialog.dispose();
					}
				});
				
				/*
				 * add it to the panel
				 */
				buttonPanel.add(closeButton);
				
				/*
				 * revert button
				 */
				JButton revertButton = new JButton("Cancel");
				
				/*
				 * tool-tip
				 */
				revertButton.getAccessibleContext().setAccessibleDescription(
						"Revert to the previously used font");
				
				revertButton.addActionListener(new ActionListener() {

					/*
					 * set the old values
					 */
					public void actionPerformed(ActionEvent evt) {
						board.setFontColor(oldColor);
						board.setTextFont(oldFont);
						
						/*
						 * repaint the board
						 */
						board.repaint();
						
						/*
						 * hide the dialog
						 */
						fontDialog.setVisible(false);
						
						/*
						 * dispose of it
						 */
						fontDialog.dispose();
					}
				});
				
				/*
				 * add it to the panel
				 */
				buttonPanel.add(revertButton);
				
				/*
				 * apply changes button
				 */
				JButton applyButton = new JButton("Apply");
				
				/*
				 * tool-up
				 */
				applyButton.getAccessibleContext().setAccessibleDescription(
						"Apply the new Font");
				
				applyButton.addActionListener(new ActionListener() {

					/*
					 * 
					 */
					public void actionPerformed(ActionEvent evt) {
						/*
						 * set new values
						 */
						board.setFontColor(preview.getForeground());
						board.setTextFont(preview.getFont());
						
						/*
						 * repaint the board
						 */
						board.repaint();
					}
				});
				
				/*
				 * add it to the panel
				 */
				buttonPanel.add(applyButton);
				
				/*
				 * add the button panel to the main panel
				 */
				contentPanel.add(buttonPanel);
				
				/*
				 * set the main panel for the dialog
				 */
				fontDialog.setContentPane(contentPanel);
				
				/*
				 * pack the dialog
				 */
				fontDialog.pack();
				fontDialog.setResizable(false);
				
				/*
				 * center it relative to the frame
				 */
				fontDialog.setLocationRelativeTo(TriPeaks.this);
				
				/*
				 * show it.
				 */
				fontDialog.setVisible(true);
			}
		});
		
		/*
		 * add the item to the menu.
		 */
		optionMenu.add(fontSelect);
		
		/*
		 * a checkbox to show/hide stats (show by default)
		 */
		statsCheck = new JCheckBoxMenuItem("Show stats", true);
		statsCheck.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.ALT_MASK));
		statsCheck.getAccessibleContext().setAccessibleDescription(
				"Show / Hide stats");
		
		/*
		 * add an Item-event listener - changes to the item
		 */
		statsCheck.addItemListener(new ItemListener() {

			/*
			 * 
			 */
			public void itemStateChanged(ItemEvent e) {
				/*
				 * if it got selected
				 */
				if (e.getStateChange() == ItemEvent.SELECTED) {
					/*
					 * show the stats panel
					 */
					statsPanel.setVisible(true);
					/*
					 * set the labels
					 */
					updateStats();
				} else
					/*
					 * hide the stats panel
					 */
					statsPanel.setVisible(false);
				/*
				 * re-pack the frame
				 */
				pack();
			}
		});
		
		/*
		 * add it to the menu
		 */
		optionMenu.add(statsCheck);
		
		/*
		 * Resets settings to their defaults
		 */
		JMenuItem resetDefs = new JMenuItem("Reset Defaults");
		
		/*
		 * set the tooltip text
		 */
		resetDefs.getAccessibleContext().setAccessibleDescription(
				"Reset the settings to their default values");
		
		/*
		 * add action listener
		 */
		resetDefs.addActionListener(new ActionListener() {

			/*
			 * 
			 */
			public void actionPerformed(ActionEvent e) {
				/*
				 * show a confirmation dialog
				 */
				int uI = JOptionPane.showConfirmDialog(TriPeaks.this,
						"Are you sure you want to reset ALL settings?",
						"Confirm Reset", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);
				
				/*
				 * if the user chose 'yes'
				 */
				if (uI == JOptionPane.YES_OPTION) {
					/*
					 * set the defaults on the board
					 */
					board.setDefaults();
					/*
					 * repaint the board
					 */
					board.repaint();
					/*
					 * show the stats panel
					 */
					statsCheck.setSelected(true);
				}
			}
		});
		
		/*
		 * add it to the menu
		 */
		optionMenu.add(resetDefs);
		
		/*
		 * a menu with cheats
		 */
		JMenu cheatMenu = new JMenu("Cheats");
		
		/*
		 * add a menu listener to it
		 */
		cheatMenu.addMenuListener(new MenuListener() {

			/*
			 * when the menu was selected
			 */
			public void menuSelected(MenuEvent e) {
				/*
				 * if the user hasn't cheated yet, display a warning.
				 */
				if (!board.hasCheated() && !seenWarn) {
					JOptionPane
							.showMessageDialog(
									TriPeaks.this,
									"Using Cheats will SCAR your name!!!\nThe only way to un-scar is to RESET!!!\nProceed at your own risk!!!",
									"Cheat Warning!",
									JOptionPane.WARNING_MESSAGE);
				}
				
				seenWarn = true;
			}

			/*
			 * not interested in these, but necessary for implementation
			 */
			public void menuDeselected(MenuEvent e) {
			}

			/*
			 * 
			 */
			public void menuCanceled(MenuEvent e) {
			}
		});
		
		/*
		 * add it to the menu bar
		 */
		menuBar.add(cheatMenu);
		
		/*
		 * cheat 1 - all cards appear face-up (doesn't actually make them
		 * face-up)
		 */
		cheatItems[0] = new JCheckBoxMenuItem("Cards face up");
		
		/*
		 * add item listener
		 */
		cheatItems[0].addItemListener(new ItemListener() {

			/*
			 * 
			 */
			public void itemStateChanged(ItemEvent e) {
				/*
				 * if it was checked, enable the cheat
				 */
				if (e.getStateChange() == ItemEvent.SELECTED) {
					board.setCheat(Cheat.CARDS_FACE_UP, true);
				}
				else {
					/*
					 * if it was unchecked, disable the cheat
					 */
					board.setCheat(Cheat.CARDS_FACE_UP, false);
				}
				
				/*
				 * repaint the board
				 */
				board.repaint();
				/*
				 * set the cheating title bar
				 */
				setTitle("TriPeaks - Cheat Mode");
			}
		});
		
		/*
		 * same thing for the rest of the cheats
		 */
		cheatMenu.add(cheatItems[0]);
		
		/*
		 * cheat 2 - click any card that's face-up (regardless of value)
		 */
		cheatItems[1] = new JCheckBoxMenuItem("Click any card");
		cheatItems[1].addItemListener(new ItemListener() {

			/*
			 * 
			 */
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					board.setCheat(Cheat.CLICK_ANY_CARD, true);
				else
					board.setCheat(Cheat.CLICK_ANY_CARD, false);
				board.repaint();
				setTitle("TriPeaks - Cheat Mode");
			}
		});
		cheatMenu.add(cheatItems[1]);
		
		/*
		 * cheat 3 - no penalty (score can never go down)
		 */
		cheatItems[2] = new JCheckBoxMenuItem("No Penalty");
		cheatItems[2].addItemListener(new ItemListener() {

			/*
			 * 
			 */
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					board.setCheat(Cheat.NO_PENALTY, true);
				else
					board.setCheat(Cheat.NO_PENALTY, false);
				board.repaint();
				setTitle("TriPeaks - Cheat Mode");
			}
		});
		cheatMenu.add(cheatItems[2]);
		
		/*
		 * The next menu will be on the right
		 */
		menuBar.add(Box.createHorizontalGlue());
		
		/*
		 * Help menu
		 */
		JMenu helpMenu = new JMenu("Help");
		
		/*
		 * Accessed with Alt+H
		 */
		helpMenu.setMnemonic(KeyEvent.VK_H);
		
		/*
		 * tool-tip text
		 */
		helpMenu.getAccessibleContext().setAccessibleDescription(
				"Game Help and Information");
		
		/*
		 * add it to the menu bar
		 */
		menuBar.add(helpMenu);
		
		/*
		 * basic explanation of gameplay
		 */
		JMenuItem gameHelp = new JMenuItem("Help", getImageIcon("Images"
				+ File.separator + "help.png"));
		gameHelp.getAccessibleContext().setAccessibleDescription(
				"How to Play & Strategies");
		gameHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		gameHelp.addActionListener(new ActionListener() {

			/*
			 * 
			 */
			public void actionPerformed(ActionEvent e) {
				/*
				 * create a new dialog box
				 */
				final JDialog helpDialog = new JDialog(TriPeaks.this,
						"How to Play");
				Font titleFont = new Font("SansSerif", Font.BOLD, 16);
				Font textFont = new Font("Serif", Font.PLAIN, 14);
				
				/*
				 * the title text
				 */
				JLabel titleHelp = new JLabel("How to Play");
				
				/*
				 * make it big and bold
				 */
				titleHelp.setFont(titleFont);
				
				/*
				 * make it centered
				 */
				titleHelp.setHorizontalAlignment(JLabel.CENTER);
				
				/*
				 * create the area for the text
				 */
				JTextArea textHelp = new JTextArea();
				
				/*
				 * set area
				 */
				textHelp.setText("   The goal of the game is to remove all the"
						+ " cards: you can remove any card that is adjacent in "
						+ "value. (e.g. If you have an Ace, you can remove a"
						+ " King or a Two). Suit doesn't matter.\n   If there "
						+ "is no adjacent card, you can take a card from the "
						+ "deck, with a penalty of $5. For the first card you "
						+ "remove, you get $1; for the second $2; $3 for the "
						+ "third; and so on. However, when you take a card from"
						+ " the deck, the streak gets reset to 0.\n   You get "
						+ "$15 for the first two peaks that you reach, and $30 "
						+ "for the last one (i.e. clearing the board). You can "
						+ "redeal before you clear the board AND still have "
						+ "some cards in the deck, but with a penalty of $5 for"
						+ " every card on the board. There is no penalty for "
						+ "redealing if your deck is empty or if you've cleared"
						+ " the board.");
				
				/*
				 * the user can't change the help text
				 */
				textHelp.setEditable(false);
				
				/*
				 * set the font for the text
				 */
				textHelp.setFont(textFont);
				
				/*
				 * the text will wrap at the edges
				 */
				textHelp.setLineWrap(true);
				
				/*
				 * the text will only wrap whole words
				 */
				textHelp.setWrapStyleWord(true);
				
				/*
				 * used to add scrollbars to the text area
				 */
				JScrollPane helpScroll = new JScrollPane(textHelp);

				helpScroll
						.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				
				/*
				 * create a panel to hold the scroll pane and title
				 */
				JPanel helpPanel = new JPanel(new BorderLayout(3, 3));
				
				/*
				 * add the title to the top
				 */
				helpPanel.add(titleHelp, BorderLayout.PAGE_START);
				
				/*
				 * add the scroll pane to the center
				 */
				helpPanel.add(helpScroll, BorderLayout.CENTER);
				
				/*
				 * same thing for the srategy and cheat text
				 */
				JLabel titleStrat = new JLabel("Game Strategies");
				titleStrat.setFont(titleFont);
				titleStrat.setHorizontalAlignment(JLabel.CENTER);
				JTextArea textStrat = new JTextArea();
				textStrat.setText("   The more cards you get in a row, the "
						+ "higher your score. However, there are times"
						+ " when you have to choose between cards. If "
						+ "those cards get you the same score, there "
						+ "are several strategies involved:\n   1)  "
						+ "Pick the card that opens up more cards That "
						+ "will give you more to choose from on your "
						+ "next move. It might go with the card you "
						+ "just took.\n   2)  If one on the choices is "
						+ "a peak, don't choose the peak. It doesn't "
						+ "open any cards.\n   Other than choosing "
						+ "cards, try working out a streak in your head."
						+ " If they're the same, go with the one that "
						+ "opens more cards.");
				textStrat.setEditable(false);
				textStrat.setFont(textFont);
				textStrat.setLineWrap(true);
				textStrat.setWrapStyleWord(true);

				JScrollPane stratScroll = new JScrollPane(textStrat);
				stratScroll
						.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

				JPanel stratPanel = new JPanel(new BorderLayout(3, 3));
				stratPanel.add(titleStrat, BorderLayout.PAGE_START);
				stratPanel.add(stratScroll, BorderLayout.CENTER);

				JLabel titleCheat = new JLabel("Game Cheats");
				titleCheat.setFont(titleFont);
				titleCheat.setHorizontalAlignment(JLabel.CENTER);

				JTextArea textCheat = new JTextArea();
				textCheat
						.setText("I HIGHLY DISCOURAGE CHEATING!!!\n\n   There is a penalty for chating! Your account will be \"scarred\" - \"CHEATER\" will be displayed in the backgournd and \"Cheat Mode\" will appear in the titlebar once you enable any cheat. Even if you disable all cheats, your username will still be scarred. The only was to un-scar is to RESET! Here is what the cheats do:\n    - All cards face up = all cards appear to be face-up, but act normally, as without the cheat.\n    - Click any card = click any face-up card. Beware when using with previous cheat - cards only appear face-up\n    - No Penalty = no penalty for anything. So your score never goes down.");
				textCheat.setEditable(false);
				textCheat.setFont(textFont);
				textCheat.setLineWrap(true);
				textCheat.setWrapStyleWord(true);

				JScrollPane cheatScroll = new JScrollPane(textCheat);
				cheatScroll
						.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

				JPanel cheatPanel = new JPanel(new BorderLayout(3, 3));
				cheatPanel.add(titleCheat, BorderLayout.PAGE_START);
				cheatPanel.add(cheatScroll, BorderLayout.CENTER);

				/*
				 * Initialize the tabbed pane
				 */
				JTabbedPane helpTabs = new JTabbedPane();

				/*
				 * add the tab to the tabbed pane
				 */
				helpTabs.addTab("How To Play", getImageIcon("Images"
						+ File.separator + "help.png"), helpPanel,
						"How to Play");
				
				/*
				 * Alt+P
				 */
				helpTabs.setMnemonicAt(0, KeyEvent.VK_P);
				helpTabs.addTab("Strategies", getImageIcon("Images"
						+ File.separator + "Strategy.png"), stratPanel,
						"Game Strategies");
				
				/*
				 * Alt+S
				 */
				helpTabs.setMnemonicAt(1, KeyEvent.VK_S);
				helpTabs.addTab("Cheats", getImageIcon("Images"
						+ File.separator + "cheat.png"), cheatPanel,
						"Game Cheats");
				
				/*
				 * Alt+C
				 */
				helpTabs.setMnemonicAt(2, KeyEvent.VK_C); //

				helpScroll.getVerticalScrollBar().setValue(0);
				stratScroll.getVerticalScrollBar().setValue(0);
				cheatScroll.getVerticalScrollBar().setValue(0);

				/*
				 * button to close the dialog
				 */
				JButton closeButton = new JButton("Close");
				closeButton.addActionListener(new ActionListener() {

					/**
					 * 
					 */
					public void actionPerformed(ActionEvent evt) {
						/*
						 * hide the dialog
						 */
						helpDialog.setVisible(false);
						
						/*
						 * dispose of the resources for the dialog
						 */
						helpDialog.dispose();
					}
				});
				
				/*
				 * a panel for the button
				 */
				JPanel closePanel = new JPanel();
				
				/*
				 * Align stuff on the X-Axis
				 */
				closePanel.setLayout(new BoxLayout(closePanel,
						BoxLayout.LINE_AXIS));
				
				/*
				 * right-align the button
				 */
				closePanel.add(Box.createHorizontalGlue());
				
				/*
				 * add the button to the panel
				 */
				closePanel.add(closeButton);
				closePanel.setBorder(BorderFactory
						.createEmptyBorder(0, 0, 5, 5));
				
				/*
				 * create a panel to be the content panel, with a 5-pixel gap
				 * between elements
				 */
				JPanel contentPanel = new JPanel(new BorderLayout(5, 5));
				
				/*
				 * add the tabbed pane to the center
				 */
				contentPanel.add(helpTabs, BorderLayout.CENTER);
				
				/*
				 * add the panel with the close-button to the bottom
				 */
				contentPanel.add(closePanel, BorderLayout.PAGE_END);
				
				/*
				 * set the panel as the content pane
				 */
				helpDialog.setContentPane(contentPanel);
				
				/*
				 * make the dialog 400 x 400 pixels
				 */
				helpDialog.setSize(new Dimension(400, 400));
				
				/*
				 * make it relative to the frame (in the center of the frame)
				 */
				helpDialog.setLocationRelativeTo(TriPeaks.this);
				
				/*
				 * show the dialog
				 */
				helpDialog.setVisible(true);
			}
		});
		
		/*
		 * add the item to the menu
		 */
		helpMenu.add(gameHelp);
		
		/*
		 * add a separator to the menu
		 */
		helpMenu.addSeparator();
		
		/*
		 * about the program/creator
		 */
		JMenuItem about = new JMenuItem("About...");
		about.setMnemonic(KeyEvent.VK_A);
		about.getAccessibleContext().setAccessibleDescription(
				"About the creator and program");
		
		/*
		 * add an action listener
		 */
		about.addActionListener(new ActionListener() {

			/*
			 * kind
			 */
			public void actionPerformed(ActionEvent e) {
				JOptionPane
						.showMessageDialog(
								TriPeaks.this,
								"TriPeaks Solitaire implementation by Valera "
										+ "Trubachev.\nWritten in Java using Kate in "
										+ "Linux.\n(C) 2008\nSpecial thanks to "
										+ "Christian d'Heureuse\nfor his Base64 encoder/"
										+ "decoder."); // credits...
			}
		});
		
		/*
		 * add the item to the menu
		 */
		helpMenu.add(about);
		
		/*
		 * return the finished menu bar
		 */
		return menuBar;
	}

	/**
	 * align stuff on the Y-Axis
	 * 
	 * creates the GUI with the given frame
	 */
	private void createGUI() {
		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		/*
		 * set the menu bar for the frame
		 */
		setJMenuBar(createMenuBar());

		/*
		 * create the panel with the cards
		 */
		board = new CardPanel();

		/*
		 * add it to the frame
		 */
		getContentPane().add(board);

		/*
		 * create the statistics panel
		 */
		statsPanel = new JPanel();

		/*
		 * align stuff on the X-Axis
		 */
		statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.LINE_AXIS));

		/*
		 * add it to the frame
		 */
		getContentPane().add(statsPanel);

		/*
		 * create the panel for the first column (of 3)
		 */
		JPanel col1 = new JPanel();

		/*
		 * align stuff on the Y-Axis
		 */
		col1.setLayout(new BoxLayout(col1, BoxLayout.PAGE_AXIS));

		/*
		 * give it some room (5 px on each side, 10 on the left)
		 */
		col1.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));

		/*
		 * add it to the stats panel
		 */
		statsPanel.add(col1);

		/*
		 * add horizontal "glue" - even out the space between the columns
		 */
		statsPanel.add(Box.createHorizontalGlue());

		/*
		 * same thing for the second column
		 */
		JPanel col2 = new JPanel();

		col2.setLayout(new BoxLayout(col2, BoxLayout.PAGE_AXIS));

		/*
		 * top, left, bottom, right
		 */
		col2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		statsPanel.add(col2);

		/*
		 * more "glue"
		 */
		statsPanel.add(Box.createHorizontalGlue());

		/*
		 * and the third
		 */
		JPanel col3 = new JPanel();

		col3.setLayout(new BoxLayout(col3, BoxLayout.PAGE_AXIS));

		/*
		 * 10 on the right
		 */
		col3.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10));

		statsPanel.add(col3);

		/*
		 * create the label, with the default text
		 */
		curGame = new JLabel("Game Winnings: ?");

		/*
		 * it should be left-aligned within the panel
		 */
		curGame.setAlignmentX(Component.LEFT_ALIGNMENT);

		/*
		 * add it to the first column same thing for the rest of the labels
		 */
		col1.add(curGame);

		maxMin = new JLabel("Most - Won: ?, Lost ?");
		maxMin.setAlignmentX(Component.LEFT_ALIGNMENT);
		col1.add(maxMin);

		curStr = new JLabel("Current Streak: ?=?");
		curStr.setAlignmentX(Component.LEFT_ALIGNMENT);
		col1.add(curStr);

		sesWin = new JLabel("Session Winnings: ?");
		sesWin.setAlignmentX(Component.LEFT_ALIGNMENT);
		col2.add(sesWin);

		sesAvg = new JLabel("Session Average: ?");
		sesAvg.setAlignmentX(Component.LEFT_ALIGNMENT);
		col2.add(sesAvg);

		sesGame = new JLabel("Session Games: ?");
		sesGame.setAlignmentX(Component.LEFT_ALIGNMENT);
		col2.add(sesGame);

		plrGame = new JLabel("Player Games: ?");
		plrGame.setAlignmentX(Component.LEFT_ALIGNMENT);
		col3.add(plrGame);

		plrAvg = new JLabel("Player Average: ?");
		plrAvg.setAlignmentX(Component.LEFT_ALIGNMENT);
		col3.add(plrAvg);

		maxStr = new JLabel("Longest Streak: ?=?");
		maxStr.setAlignmentX(Component.LEFT_ALIGNMENT);
		col3.add(maxStr);

		/*
		 * add a window-event listner to the frame
		 */
		addWindowListener(new WindowListener() {

			/*
			 * the window is opened
			 * 
			 * @param e
			 */
			public void windowOpened(WindowEvent e) {
				/*
				 * get the file as a stream
				 */
				InputStream is = TriPeaks.class
						.getResourceAsStream(SETTINGS_FILE_NAME);
				
				/*
				 * placeholder for the line
				 */
				String line = null;
				String defName = "";

				try {
					if (is == null) {
						throw new Exception("First Time Running");
					}
					
					/*
					 * create a buffered reader for the file
					 */
					BufferedReader in = new BufferedReader(
							new InputStreamReader(is));

					/*
					 * read the line
					 */
					if ((line = in.readLine()) != null) {
						defName = line;
					}

					/*
					 * close the file
					 */
					in.close();
				} catch (FileNotFoundException eFNF) {
					/*
					 * file wasn't found (probably first time running)
					 */

					System.out
							.println("File not found (probably because the User hasn't played before): "
									+ eFNF.getMessage());
				} catch (IOException eIO) {
					/*
					 * other IO error
					 */
					System.out
							.println("Error reading from file -OR- closing file");
				} catch (Exception eE) {
					System.out.println("First time run");
				}

				/*
				 * ask for the player's name
				 */
				uName = JOptionPane.showInputDialog(TriPeaks.this,
						"Player Name:", defName);

				/*
				 * if the name is empty or Cancel was pressed, exit
				 */
				if ((uName == null) || (uName.equals("")))
					System.exit(0);

				try {
					/*
					 * read the scores for the player
					 */
					readScoreSets();
				} catch (NewPlayerException eNP) {
					board.setDefaults();
				}
			}

			/*
			 * the X is clicked (not when the window disappears - that's
			 * windowClosed
			 */
			public void windowClosing(WindowEvent e) {
				/*
				 * get the penalty for quitting
				 */
				int penalty = board.getPenalty();

				/*
				 * if there is a penalty at all
				 */
				if (penalty != 0) {
					/*
					 * show a confirmation message
					 */
					int uI = JOptionPane.showConfirmDialog(TriPeaks.this,
							"Are you sure you want to quit?\nQuitting now results in a penalty of $"
									+ penalty + "!", "Confirm Quit",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE);

					/*
					 * if the user clicked Yes
					 */
					if (uI == JOptionPane.YES_OPTION) {
						/*
						 * perform the penalty
						 */
						board.doPenalty(penalty);
					} else {
						/*
						 * no was clicked - don't do anything
						 */
						return;
					}
				}

				/*
				 * create the file
				 */
				File setFile = new File(SETTINGS_FILE_NAME);
				if (setFile.canWrite() == false) {
					/*
					 * if the file doesn't exist, don't do anything
					 */
					return;
				}

				try {
					/*
					 * create a buffered writer for the file
					 */
					BufferedWriter out = new BufferedWriter(new FileWriter(
							setFile));

					/*
					 * write the default username
					 */
					out.write(uName);

					/*
					 * close the file
					 */
					out.close();
				} catch (FileNotFoundException eFNF) {
					/*
					 * file wasn't found
					 */
					System.out.println("File not found: " + eFNF.getMessage());
				} catch (IOException eIO) {
					/*
					 * other IO exception
					 */
					System.out
							.println("Error writing to file -OR- closing file");
				}

				/*
				 * write the scores for the user
				 */
				writeScoreSets();

				/*
				 * exit
				 */
				System.exit(0);
			}

			/*
			 * the following methods aren't used, but necessary to implement
			 * KeyListener and WindowListener
			 */
			public void windowClosed(WindowEvent e) {
			}

			/*
			 * 
			 */
			public void windowIconified(WindowEvent e) {
			}

			/*
			 * 
			 */
			public void windowDeiconified(WindowEvent e) {
			}

			/*
			 * 
			 */
			public void windowActivated(WindowEvent e) {
			}

			/*
			 * 
			 */
			public void windowDeactivated(WindowEvent e) {
			}
		});
	}

	/**
	 * 
	 * @param in
	 * @return
	 */
	public static String capitalize(final String in) {
		if (in.length() == 0){
			return "";
		}
		
		if (in.length() == 1) {
			return in.toUpperCase();
		}
		
		return Character.toUpperCase(in.charAt(0)) + in.substring(1);
	}

	/**
	 * entry point for the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * create the frame
		 */
		TriPeaks frame = new TriPeaks("TriPeaks");
		
		/*
		 * don't do anything when user presses the X - custom close handling
		 */
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		/*
		 * create the GUI
		 */
		frame.createGUI();
		
		/*
		 * give everything enough room
		 */
		frame.pack();
		frame.setIconImage(getIcon("Images" + File.separator + "TriPeaks.png"));
		
		/*
		 * can't resize the window
		 */
		frame.setResizable(false);
		
		/*
		 * show it.
		 */
		frame.setVisible(true);
	}

	/**
	 * sets the text of the stats labels
	 */
	public void updateStats() {
		/*
		 * if the stats panel isn't shown, don't do anything
		 */
		if (statsPanel.isVisible() == false){
			return;
		}
		
		/*
		 * get the stats, which are stored in the board
		 */
		int[] stats = board.getAllStats();

		DecimalFormat intFmt = new DecimalFormat("$###,###");
		DecimalFormat dblFmt = new DecimalFormat("$###,##0.00");

		/*
		 * what was won/lost in the current game
		 */
		curGame.setText("Game Winnings:  " + intFmt.format(stats[1]));

		/*
		 * record win/loss during any game
		 */
		maxMin.setText("Most - Won:  " + intFmt.format(stats[6]) + ", Lost:  "
				+ intFmt.format(stats[7]));
		
		/*
		 * current streak
		 */
		curStr.setText("Current Streak:  " + stats[3] + " = "
				+ intFmt.format((stats[3] * (stats[3] + 1) / 2)));
		
		/*
		 * what was won/lost during the session (start program = new session)
		 */
		sesWin.setText("Session Winnings:  " + intFmt.format(stats[2]));

		/*
		 * if some games were played (so denominator doesn't equal 0)
		 */
		if (stats[5] != 0) {
			/*
			 * calulate the average
			 */
			double avg = ((double) stats[2]) / ((double) stats[5]);
			
			/*
			 * round the average
			 */
			sesAvg.setText("Session Average:  " + dblFmt.format(avg));
		} else {
			/*
			 * set it to 0 if no games were played
			 */
			sesAvg.setText("Session Average:  $0.00");
		}
		
		/*
		 * how many games were played during the session
		 */
		sesGame.setText("Session Games:  " + stats[5]);
		
		/*
		 * how many games the player played altogether
		 */
		plrGame.setText("Player Games:  " + stats[4]);
		
		/*
		 * if the player has played any games
		 */
		if (stats[4] != 0) {
			/*
			 * calculate the average
			 */
			double avg = ((double) stats[0]) / ((double) stats[4]);
			/*
			 * round the average
			 */
			plrAvg.setText("Player Average:  " + dblFmt.format(avg));
		} else {
			/*
			 * set it to 0 is no games were ever played
			 */
			plrAvg.setText("Player Average:  $0.00");
		}
		
		/*
		 * longest streak ever by the player
		 */
		maxStr.setText("Longest Streak:  " + stats[8] + " = "
				+ intFmt.format((stats[8] * (stats[8] + 1) / 2)));
	}

	/**
	 * add a dollar sign to a number
	 * 
	 * @param in
	 * @return
	 */
	public static String dSign(final int in) {
		if (in < 0) {
			/*
			 * put the negative sign out in front if it's negative
			 */
			return ("-$" + (-1) * in);
		} else {
			/*
			 * otherwise just add the dollar sign
			 */
			return "$" + in;
		}
	}

	/**
	 * generates an "optimal" grid based on the number of elements
	 * 
	 * @param num
	 * @return
	 */
	private int[] genGrid(int num) {
		/*
		 * the array for the dimensions
		 */
		int[] dim = new int[2];

		/*
		 * go through each of the numbers to the given one
		 */
		for (int q = 1; q <= num; q++) {
			/*
			 * if it's a perfect square
			 */
			if (q * q == num) {
				/*
				 * set both values as the given number's square root
				 */
				dim[0] = dim[1] = q;
				
				/*
				 * return the dimensions
				 */
				return dim;
			}
		}

		/*
		 * go through the numbers again - check for something else
		 */
		for (int q = 1; q <= num; q++) {
			/*
			 * a placeholder
			 */
			int w;
			
			/*
			 * go from 1 to 2 more than the current number
			 */
			for (w = 1; w <= q + 2; w++) {
				/*
				 * if the grid will fit
				 */
				if (q * w >= num) {
					/*
					 * set the first value
					 */
					dim[0] = q;
					
					/*
					 * and the second
					 */
					dim[1] = w;
					
					/*
					 * return the dimensions
					 */
					return dim;
				}
			}

			/*
			 * if +1 and +2 will satisfy the number
			 */
			if ((q + 1) * (q + 2) >= num) {
				/*
				 * set the first value
				 */
				dim[0] = q + 1;
				
				/*
				 * set the second value
				 */
				dim[1] = q + 2;
				
				/*
				 * return the dimensions
				 */
				return dim;
			}
			
			/*
			 * go to the 4 more than the current number (no initialization
			 * statement - go from the previous for left off)
			 */
			for (; w <= q + 4; w++) {
				/*
				 * if the grid will fit
				 */
				if (q * w >= num) {
					/*
					 * set the first value
					 */
					dim[0] = q;
					
					/*
					 * and the second
					 */
					dim[1] = w;
					
					/*
					 * return the dimensions
					 */
					return dim;
				}
			}
		}
		
		/*
		 * if something BAD happened, return 0 x 0
		 */
		return dim;
	}

	/**
	 * calculates the ROT13 cipher of a string
	 * 
	 * @param in
	 * @return
	 */
	public static String rot13(String in) {
		/*
		 * only lowercase characters are wanted
		 */
		String low = in.toLowerCase();
		
		/*
		 * a buffer for the output string
		 */
		StringBuffer out = new StringBuffer();
		
		/*
		 * two index holders
		 */
		int index, newIndex;
		
		/*
		 * go through the letters in the input string
		 */
		for (int q = 0; q < low.length(); q++) {
			/*
			 * find the current character's index in the alphabet string
			 */
			index = LETTERS.indexOf(low.charAt(q));
			
			/*
			 * if the letter wasn't found, skip it
			 */
			if (index == -1) {
				continue;
			}
			
			/*
			 * do the rotation by 13
			 */
			newIndex = (index + LETTERS.length() / 2) % LETTERS.length();
			
			/*
			 * append the ciphered characted
			 */
			out.append(LETTERS.charAt(newIndex));
		}
		
		/*
		 * return the ciphered string
		 */
		return out.toString();
	}

	/**
	 * reverse a string
	 * 
	 * @param in
	 * @return
	 */
	public static String backward(String in) {
		/*
		 * buffer for output
		 */
		StringBuffer out = new StringBuffer(in);
		
		/*
		 * return the reversed string
		 */
		return out.reverse().toString();
	}

	/**
	 * reads the scores from the current user's file.
	 * 
	 * @throws NewPlayerException
	 */
	public void readScoreSets() throws NewPlayerException {
		/*
		 * the filename is the ROT13 cipher of their name
		 */
		String fileName = rot13(uName);
		
		/*
		 * get
		 */
		File file = new File(dirName + File.separator + fileName + ".txt");
		
		/*
		 * if the file is null, don't do anything the file
		 */
		if (file.canRead() == false) {
			throw new NewPlayerException("New Player: " + uName);
		}
		
		/*
		 * placeholder for the line
		 */
		String line = null;
		
		/*
		 * the array for the stats
		 */
		int[] stats = new int[CardPanel.NSTATS];
		
		/*
		 * cheats array for the cheat menu items
		 */
		boolean[] cheats = new boolean[CardPanel.NCHEATS];
		
		/*
		 * the cheat status
		 */
		boolean hasCheated = false;
		
		/*
		 * line number (incremented before setting value)
		 */
		int lNum = -1;
		
		/*
		 * set up the encryptor to decrypt the lines (the passphrase is the
		 * filename backwards)
		 */
		Encryptor dec = new Encryptor(backward(fileName));
		BufferedReader in = null;
		try {
			/*
			 * create a buffered reader for the file
			 */
			in = new BufferedReader(new FileReader(file));
			/*
			 * read the lines one-by-one
			 */
			String deced;
			while ((line = in.readLine()) != null) {
				/*
				 * increment the line number
				 */
				lNum++;
				/*
				 * stop if there are more lines than needed
				 */
				if (lNum > (stats.length + cheats.length + 6))
					break;
				deced = dec.decrypt(line);
				if ((lNum >= 0) && (lNum < stats.length)) {
					/*
					 * set the value based on the decrypted line, if the line
					 * belongs to the stats array
					 */
					stats[lNum] = Integer.parseInt(deced);
				} else if ((lNum >= stats.length)
						&& (lNum < (stats.length + cheats.length))) {
					/*
					 * set the values based on the decrypted line, if the line
					 * belongs to the cheats array
					 */
					cheats[lNum - stats.length] = Boolean.parseBoolean(deced);
				} else if (lNum == stats.length + cheats.length)
					hasCheated = Boolean.parseBoolean(deced);
				else if (lNum == stats.length + cheats.length + 1)
					board.setCardFront(deced);
				else if (lNum == stats.length + cheats.length + 2)
					board.setCardBack(deced);
				else if (lNum == stats.length + cheats.length + 3) {
					/*
					 * two commas
					 */
					int cm1, cm2;
					
					/*
					 * get the indexes of the two commas
					 */
					cm1 = deced.indexOf(',');
					cm2 = deced.lastIndexOf(',');
					
					/*
					 * if either comma isn't found, exit
					 */
					if ((cm1 == -1) || (cm2 == -1) || (cm1 == cm2)) {
						continue;
					}
					
					/*
					 * convert to integer and set the color
					 */
					board.setBackColor(new Color(Integer.parseInt(deced
							.substring(0, cm1)), Integer.parseInt(deced
							.substring(cm1 + 1, cm2)), Integer.parseInt(deced
							.substring(cm2 + 1))));
				} else if (lNum == stats.length + cheats.length + 4) {
					int dash, cm1, cm2;
					dash = deced.indexOf('-');
					cm1 = deced.indexOf(',');
					cm2 = deced.lastIndexOf(',');
					if ((dash == -1) || (cm1 == -1) || (cm2 == -1)
							|| (cm1 == cm2))
						continue;
					int bold = (Boolean.parseBoolean(deced.substring(dash + 1,
							cm1))) ? Font.BOLD : 0;
					int ital = (Boolean.parseBoolean(deced.substring(cm1 + 1,
							cm2))) ? Font.ITALIC : 0;
					board.setTextFont(new Font(deced.substring(0, dash), bold
							| ital, Integer.parseInt(deced.substring(cm2 + 1))));
				} else if (lNum == stats.length + cheats.length + 5) {
					int cm1, cm2;
					cm1 = deced.indexOf(',');
					cm2 = deced.lastIndexOf(',');
					if ((cm1 == -1) || (cm2 == -1) || (cm1 == cm2))
						continue;
					board.setFontColor(new Color(Integer.parseInt(deced
							.substring(0, cm1)), Integer.parseInt(deced
							.substring(cm1 + 1, cm2)), Integer.parseInt(deced
							.substring(cm2 + 1))));
				} else if (lNum == stats.length + cheats.length + 6) {
					if (Long.parseLong(deced) != file.lastModified()) {
						file.delete();
						JOptionPane.showMessageDialog(this,
								"Score file has been modified since"
										+ "\nlast used by TriPeaks!\nThe file "
										+ "HAS BEEN DELETED!!!\nPlease don't "
										+ "cheat like that again!",
								"Cheating Error", JOptionPane.ERROR_MESSAGE);
						board.setDefaults();
						board.reset();
						in.close();
						return;
					}
				}
			}
			
			/*
			 * set the stats in the board
			 */
			board.setStats(stats);
			
			/*
			 * set the cheat status
			 */
			board.setCheated(hasCheated);
			
			/*
			 * set the title based on the cheat status
			 */
			setTitle(hasCheated ? "TriPeaks - Cheat Mode" : "TriPeaks");
			
			/*
			 * go through the cheats
			 */
			for (int q = 0; q < cheats.length; q++) {
				/*
				 * set the selected status of the menu items used for the cheats
				 */
				cheatItems[q].setSelected(cheats[q]);
			}

			/*
			 * update the labels
			 */
			updateStats();
			
			/*
			 * repaint the board
			 */
			board.repaint();
		} catch (FileNotFoundException eFNF) {
			/*
			 * file wasn't found (probalby because the user doesn't exist yet
			 */
			System.out
					.println("File not found (probably because the User hasn't played before): "
							+ eFNF.getMessage());
		} catch (IOException eIO) {
			/*
			 * other IO error
			 */
			System.out.println("Error reading from file -OR- closing file");
		} finally {
			/*
			 * close the file
			 */
			try {
				in.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * writes the scores for the current player
	 */
	public void writeScoreSets() {
		/*
		 * filename is the ROT13 cipher of the username
		 */
		String fileName = rot13(uName);
		
		/*
		 * create
		 */
		File setFile = new File(dirName + File.separator + fileName + ".txt");
		
		/*
		 * if the file doesn't exist, don't do anything the file
		 */
		if (setFile.canWrite() == false) {
			return;
		}
		
		/*
		 * set up the encryptor to encrpyt the lines
		 */
		Encryptor enc = new Encryptor(backward(fileName));
		try {
			/*
			 * create a buffered writer for the file
			 */
			BufferedWriter out = new BufferedWriter(new FileWriter(setFile));

			EnumSet<Cheat> cheats = board.getCheats();

			Color boardColor = board.getBackColor();

			Font textFont = board.getTextFont();

			Color fontColor = board.getFontColor();

			long dtMod = new Date().getTime();

			/*
			 * player's overall score
			 */
			out.write(enc.encrypt("" + board.getScore()));
			/*
			 * new line
			 */
			out.newLine();
			/*
			 * player's highes score
			 */
			out.write(enc.encrypt("" + board.getHighScore()));
			out.newLine();
			/*
			 * player's lowest score
			 */
			out.write(enc.encrypt("" + board.getLowScore()));
			out.newLine();
			/*
			 * number of games played by the user
			 */
			out.write(enc.encrypt("" + board.getNumGames()));
			out.newLine();
			/*
			 * player's longest streak
			 */
			out.write(enc.encrypt("" + board.getHighStreak()));
			out.newLine();
			/*
			 * first cheat
			 */
			out.write(enc.encrypt("" + cheats.contains(Cheat.CARDS_FACE_UP)));
			out.newLine();
			/*
			 * second cheat
			 */
			out.write(enc.encrypt("" + cheats.contains(Cheat.CLICK_ANY_CARD)));
			out.newLine();
			/*
			 * third cheat
			 */
			out.write(enc.encrypt("" + cheats.contains(Cheat.NO_PENALTY)));
			out.newLine();
			/*
			 * player's cheat status
			 */
			out.write(enc.encrypt("" + board.hasCheated()));

			out.newLine();
			out.write(enc.encrypt("" + board.getCardFront()));
			out.newLine();
			out.write(enc.encrypt("" + board.getCardBack()));
			out.newLine();
			out.write(enc.encrypt(boardColor.getRed() + ","
					+ boardColor.getGreen() + "," + boardColor.getBlue()));
			out.newLine();
			out.write(enc.encrypt(textFont.getFamily() + "-"
					+ textFont.isBold() + "," + textFont.isItalic() + ","
					+ textFont.getSize()));
			out.newLine();
			out.write(enc.encrypt(fontColor.getRed() + ","
					+ fontColor.getGreen() + "," + boardColor.getBlue()));
			out.newLine();
			out.write(enc.encrypt("" + 1000 * ((long) dtMod / 1000)));

			/*
			 * close the file
			 */
			out.close();

			setFile.setLastModified(dtMod);
		} catch (FileNotFoundException eFNF) {
			/*
			 * file wasn't found
			 */
			System.out.println("File not found: " + eFNF.getMessage());
		} catch (IOException eIO) {
			/*
			 * other IO exception
			 */
			System.out.println("Error writing to file -OR- closing file");
		}
	}
}
