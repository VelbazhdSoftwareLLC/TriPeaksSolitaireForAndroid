package eu.veldsoft.tri.peaks;

//import all the necessary stuff

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
import java.util.Iterator;

/**
 * it's a JFrame that listens to window events
 */
public class TriPeaks extends JFrame implements WindowListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CardPanel board; // the panel with the cards
	JLabel curGame, maxMin, curStr, sesWin, sesAvg, sesGame, plrGame, plrAvg,
			maxStr; // the labels for the stats
	public static final String scoresDir = "GevFpbef";
	private final String dirName = scoresDir; // the folder with the score files
												// (ROT13 of TriScores)
	private final String settingsFile = "TriSet";
	private String uName; // name of the player
	private JPanel statsPanel; // the pnael with the stats
	private JCheckBoxMenuItem[] cheatItems = new JCheckBoxMenuItem[CardPanel.NCHEATS];
	private boolean seenWarn = false;
	private JCheckBoxMenuItem statsCheck;

	public TriPeaks(String title) { // class constructor
		super(title); // call the JFrame contructor
	}

	public static void main(String[] args) { // entry point for the application
		TriPeaks TP = new TriPeaks("TriPeaks"); // create the frame
		/*
		 * don't do anything when user presses the X - custom close handling
		 */
		TP.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		TP.createGUI(); // create the GUI
		TP.pack(); // give everything enough room
		TP.setIconImage(getIcon("Images" + File.separator + "TriPeaks.png"));
		TP.setResizable(false); // can't resize the window
		TP.setVisible(true); // show it.
	}

	public static Image getIcon(String path) { // returns an Image based on the
												// path
		ImageIcon img = getImageIcon(path); // gets the image icon based on the
											// path
		if (img != null)
			return img.getImage(); // if the image icon isn't null, get the
									// image from it
		else
			return null; // otherwise return null
	}

	public static ImageIcon getImageIcon(String path) { // returns an ImageIcon
														// based on the path
														// (ImageIcon implements
														// Icon, and Image
														// doesn't)
		URL imgURL = TriPeaks.class.getResource(path); // get the URL
		if (imgURL != null)
			return new ImageIcon(imgURL); // if the URL isn't null, return the
											// ImageIcon
		else
			return null; // otherwise return null
	}

	public void createGUI() { // creates the GUI with the given frame
		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS)); // align
																		// stuff
																		// on
																		// the
																		// Y-Axis
		setJMenuBar(createMenuBar()); // set the menu bar for the frame

		board = new CardPanel(); // create the panel with the cards
		getContentPane().add(board); // add it to the frame

		statsPanel = new JPanel(); // create the statistics panel
		statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.LINE_AXIS)); // align
																				// stuff
																				// on
																				// the
																				// X-Axis
		getContentPane().add(statsPanel); // add it to the frame

		JPanel col1 = new JPanel(); // create the panel for the first column (of
									// 3)
		col1.setLayout(new BoxLayout(col1, BoxLayout.PAGE_AXIS)); // align stuff
																	// on the
																	// Y-Axis
		col1.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5)); // give it
																		// some
																		// room
																		// (5 px
																		// on
																		// each
																		// side,
																		// 10 on
																		// the
																		// left)
		statsPanel.add(col1); // add it to the stats panel

		statsPanel.add(Box.createHorizontalGlue()); // add horizontal "glue" -
													// even out the space
													// between the columns

		JPanel col2 = new JPanel(); // same thing for the second column
		col2.setLayout(new BoxLayout(col2, BoxLayout.PAGE_AXIS));
		col2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // top,
																		// left,
																		// bottom,
																		// right
		statsPanel.add(col2);

		statsPanel.add(Box.createHorizontalGlue()); // more "glue"

		JPanel col3 = new JPanel(); // and the third
		col3.setLayout(new BoxLayout(col3, BoxLayout.PAGE_AXIS));
		col3.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10)); // 10 on
																		// the
																		// right
		statsPanel.add(col3);

		curGame = new JLabel("Game Winnings: ?"); // create the label, with the
													// default text
		curGame.setAlignmentX(Component.LEFT_ALIGNMENT); // it should be
															// left-aligned
															// within the panel
		col1.add(curGame); // add it to the first column
		// same thing for the rest of the labels
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

		addWindowListener(this); // add a window-event listner to the frame
	}

	public JMenuBar createMenuBar() { // creates the menu bar
		JMenuBar menuBar = new JMenuBar(); // init the menu bar

		JMenu gameMenu = new JMenu("Game"); // game menu
		gameMenu.setMnemonic(KeyEvent.VK_G); // can be opened with Alt+G
		gameMenu.getAccessibleContext().setAccessibleDescription(
				"Game Playing and Operation"); // the tool-tip text
		menuBar.add(gameMenu); // add the menu to the menu bar

		JMenuItem deal = new JMenuItem("Deal"); // redeal menu item
		deal.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0)); // accessed
																		// with
																		// F2
		deal.addActionListener(new ActionListener() { // add an action listener
														// to it
			public void actionPerformed(ActionEvent e) {
				board.redeal(); // call the redeal method of the board
			}
		});
		gameMenu.add(deal); // add the menu item to the menu

		JMenuItem switchPlr = new JMenuItem("Switch Player..."); // switch
																	// players
		switchPlr.setMnemonic(KeyEvent.VK_P); // Alt+P
		switchPlr.getAccessibleContext().setAccessibleDescription(
				"Change the current player"); // Tool-tip text
		switchPlr.addActionListener(new ActionListener() { // add an action
					// listener
					public void actionPerformed(ActionEvent e) {
						int penalty = board.getPenalty(); // get the penalty for
															// switching players
						if (penalty != 0) { // if there's some penalty
							int uI = JOptionPane
									.showConfirmDialog(
											TriPeaks.this,
											"Are you sure you want to switch players?\nSwitching now results in a penalty of $"
													+ penalty + "!",
											"Confirm Player Switch",
											JOptionPane.YES_NO_OPTION,
											JOptionPane.WARNING_MESSAGE); // show
																			// a
																			// confirmation
																			// dialog
							if (uI == JOptionPane.YES_OPTION)
								board.doPenalty(penalty); // if the user clicked
															// Yes, perform the
															// penalty
							else
								return; // Otherwise, the user clicked No, so
										// don't do anything
						}
						String tempName = JOptionPane.showInputDialog(
								TriPeaks.this, "Player Name:", uName); // ask
																		// for
																		// the
																		// user's
																		// name
						if ((tempName != null) && (!tempName.equals(""))) { // if
																			// it's
																			// not
																			// null
																			// or
																			// empty
							writeScoreSets(); // write the current user's score
							board.reset();
							uName = tempName; // change the user
							try {
								readScoreSets(); // read the new user's scores
							} catch (NewPlayerException eNP) {
								board.setDefaults();
							}
							updateStats();
							board.repaint();
						}
					}
				});
		gameMenu.add(switchPlr); // add the item to the menu

		JMenuItem highScores = new JMenuItem("High Scores");
		highScores.setMnemonic(KeyEvent.VK_H);
		highScores.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
				ActionEvent.CTRL_MASK));
		highScores.getAccessibleContext().setAccessibleDescription(
				"Show high score table");
		highScores.addActionListener(new ActionListener() {
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
				if (!hsModel.readAndSetData())
					System.out.println("Error setting table values!");
				JTable scoreTable = new JTable(hsModel) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

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

					protected JTableHeader createDefaultTableHeader() {
						return new JTableHeader(columnModel) {
							/**
							 * 
							 */
							private static final long serialVersionUID = 1L;

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

				final JTextField searchField = new JTextField();
				searchField.setHorizontalAlignment(JTextField.LEFT);
				searchField.setColumns(20);
				searchField.setAlignmentX(Component.LEFT_ALIGNMENT);
				searchField.getDocument().addDocumentListener(
						new DocumentListener() {
							public void changedUpdate(DocumentEvent evt) {
								setRowFilter(sorter, searchField.getText());
							}

							public void insertUpdate(DocumentEvent evt) {
								setRowFilter(sorter, searchField.getText());
							}

							public void removeUpdate(DocumentEvent evt) {
								setRowFilter(sorter, searchField.getText());
							}
						});
				searchPanel.add(searchField);

				JButton clearSearch = new JButton("Clear");
				clearSearch.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						searchField.setText("");
					}
				});
				clearSearch.setAlignmentX(Component.LEFT_ALIGNMENT);
				searchPanel.add(clearSearch);

				JButton closeButton = new JButton("Close");
				closeButton.addActionListener(new ActionListener() {
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

		JMenuItem resetStats = new JMenuItem("Reset"); // reset all stats/scores
		resetStats.setMnemonic(KeyEvent.VK_R); // Alt+R
		resetStats.getAccessibleContext().setAccessibleDescription(
				"Reset all stats and scores!"); // tooltip
		resetStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int uI = JOptionPane
						.showConfirmDialog(
								TriPeaks.this,
								"Are you sure you want to reset your game?\nResetting results in a PERMANENT loss of score and stats!",
								"Confirm Game Reset",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE); // show a
																// confirmation
																// dialog
				if (uI == JOptionPane.YES_OPTION) { // If the user clicked yes
					board.reset(); // reset the board
					setTitle("TriPeaks");
				}
			}
		});
		gameMenu.add(resetStats); // add the item to the menu

		gameMenu.addSeparator(); // add a separator to the menu

		JMenuItem exitGame = new JMenuItem("Exit"); // exit the game
		exitGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				ActionEvent.CTRL_MASK)); // accessed with Ctrl+Q
		getAccessibleContext().setAccessibleDescription("Exit the Game"); // tooltip
		exitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int penalty = board.getPenalty(); // get penalty for quitting
				if (penalty != 0) { // if there's a penalty, show the
									// confirmation dialog
					int uI = JOptionPane.showConfirmDialog(TriPeaks.this,
							"Are you sure you want to exit?\nExiting now results in a penalty of $"
									+ penalty + "!", "Confirm Exit",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE);
					if (uI == JOptionPane.YES_OPTION) { // the user agrees to
														// the penalty
						board.doPenalty(penalty); // perform the penalty
					} else
						return;
				}
				writeScoreSets(); // write the user's scores
				System.exit(0); // exit the program
			}
		});
		gameMenu.add(exitGame); // add it to the menu

		JMenu optionMenu = new JMenu("Options"); // game options menu
		optionMenu.setMnemonic(KeyEvent.VK_O); // accessed with Alt+O
		optionMenu.getAccessibleContext().setAccessibleDescription(
				"Game Options"); // set the tool-tip text
		menuBar.add(optionMenu); // add it to the menu bar

		JMenuItem cardStyle = new JMenuItem("Card Style"); // Change the image
															// that appears on
															// the front and
															// back of the cards
		cardStyle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.ALT_MASK)); // Alt+C
		getAccessibleContext().setAccessibleDescription(
				"Change the picture on the front and back of the cards"); // tooltip
		cardStyle.addActionListener(new ActionListener() { // add an action
					// listener
					public void actionPerformed(ActionEvent e) {
						final JDialog styleDialog = new JDialog(TriPeaks.this,
								"Card Style"); // create a dialog box for the
												// style
						final String oldFront = board.getCardFront();
						final String oldBack = board.getCardBack();
						final JTabbedPane stylesTabs = new JTabbedPane(); // create
																			// a
																			// tabbed
																			// pane

						ActionListener changeBack = new ActionListener() { // the
																			// action
																			// listener
																			// for
																			// the
																			// "back"
																			// buttons
																			// -
																			// one
																			// listener
																			// handles
																			// all
							public void actionPerformed(ActionEvent evt) {
								board.setCardBack(evt.getActionCommand()); // the
																			// action
																			// command
																			// is
																			// set
																			// when
																			// the
																			// button
																			// is
																			// created
								board.repaint(); // repaint - with the new style
							}
						};

						File backsDir = new File("CardSets" + File.separator
								+ "Backs"); // the folder with the back designs
						if ((!backsDir.exists()) || (!backsDir.isDirectory())) { // if
																					// the
																					// folder
																					// doesn't
																					// exist
																					// or
																					// isn't
																					// a
																					// folder
							JOptionPane.showMessageDialog(TriPeaks.this,
									"Invalid Structure for Card folders"); // give
																			// an
																			// error
							return; // stop the execution
						}
						File[] backFiles = backsDir.listFiles(); // get the list
																	// of files
																	// in the
																	// folder

						final ArrayList<JToggleButton> backButtons = new ArrayList<JToggleButton>(); // create
																										// and
																										// ArrayList
																										// of
																										// Toggle
																										// Buttons
						String fileName, picName; // fileName is the path of the
													// image. picName is the
													// image name (w/o
													// extension)
						JToggleButton newBut; // a placeholder for the button
						ButtonGroup backGroup = new ButtonGroup(); // a button
																	// group for
																	// the
																	// toggle
																	// buttons
																	// (so only
																	// one can
																	// be
																	// selected)
						for (int q = 0; q < backFiles.length; q++) { // go
																		// through
																		// each
																		// file
																		// in
																		// the
																		// folder
							if (!backFiles[q].getName().endsWith(".png"))
								continue; // if the file isn't a .png, skip it
							fileName = backFiles[q].toString(); // the path to
																// the image
							picName = backFiles[q].getName().substring(0,
									backFiles[q].getName().length() - 4); // the
																			// file
																			// name,
																			// w/o
																			// extension
							newBut = new JToggleButton(getImageIcon(fileName),
									false); // create a new toggle button for
											// the image, no text, with the
											// image, unselected
							if (picName.equals(board.getCardBack()))
								newBut.setSelected(true); // if that's the
															// current back,
															// select the button
							newBut.setActionCommand(picName); // set the buttons
																// action
																// command -
																// used by the
																// action
																// listener to
																// determine
																// what to use
																// for setting
																// the image
							newBut.addActionListener(changeBack); // add the
																	// action
																	// listener
																	// to the
																	// button -
																	// created
																	// before
							newBut.getAccessibleContext()
									.setAccessibleDescription(picName); // tool-tip
																		// is
																		// the
																		// image
																		// name
							backGroup.add(newBut); // add the button to the
													// group, which handles
													// selection
							backButtons.add(newBut); // add the button to the
														// arrayList
						}

						ActionListener changeFront = new ActionListener() { // action
																			// listener
																			// for
																			// changing
																			// the
																			// front
							public void actionPerformed(ActionEvent evt) {
								board.setCardFront(evt.getActionCommand()); // use
																			// the
																			// action
																			// command
																			// to
																			// set
																			// the
																			// front
																			// design
								board.repaint(); // repaint with the new design
							}
						};

						File frontsDir = new File("CardSets" + File.separator
								+ "Fronts"); // the folder with the fronts
						if ((!frontsDir.exists()) || (!frontsDir.isDirectory())) { // if
																					// the
																					// folder
																					// doesn't
																					// exist
																					// or
																					// isn't
																					// a
																					// folder
							JOptionPane.showMessageDialog(TriPeaks.this,
									"Invalid Structure for Card folders"); // error
																			// message
							return; // stop the creation of the dialog
						}
						File[] frontsDirs = frontsDir.listFiles(); // get the
																	// list of
																	// files in
																	// the
																	// folder

						final ArrayList<JToggleButton> frontButtons = new ArrayList<JToggleButton>(); // re-instantiate
																										// the
																										// arraylist
						int randCard; // a placeholder for the random card value
						String previewName, dirName; // previewName is the path
														// to the preview image,
														// dirName is the name
														// of the folder with
														// the card styles
						ButtonGroup frontGroup = new ButtonGroup(); // a button
																	// group for
																	// the
																	// "front"
																	// buttons
						for (int q = 0; q < frontsDirs.length; q++) { // go
																		// through
																		// each
																		// file
																		// in
																		// the
																		// Fronts
																		// folder
							if (!frontsDirs[q].isDirectory())
								continue; // if it's a file (not a directory),
											// skip it
							dirName = frontsDirs[q].getName(); // the name of
																// the folder
							randCard = randInt(52); // generate a random value
													// to get the random card
							previewName = frontsDirs[q].toString()
									+ File.separator
									+ Card.suitAsString((int) (randCard / 13))
									+ ((randCard % 13) + 1) + ".png"; // get a
																		// random
																		// card
																		// from
																		// the
																		// folder
							newBut = new JToggleButton(
									getImageIcon(previewName), false); // create
																		// the
																		// button,
																		// with
																		// the
																		// random
																		// card
																		// as
																		// the
																		// image,
																		// no
																		// text,
																		// and
																		// not
																		// selected
							if (dirName.equals(board.getCardFront()))
								newBut.setSelected(true); // if the style is
															// current, make the
															// button selected
							newBut.setActionCommand(dirName); // set the action
																// command as
																// the folder
																// name
							newBut.addActionListener(changeFront); // add the
																	// action
																	// listener
																	// to the
																	// button
							newBut.getAccessibleContext()
									.setAccessibleDescription(dirName); // set
																		// the
																		// tooltip
																		// text
																		// to
																		// the
																		// folder
																		// name
							frontGroup.add(newBut); // add the button to the
													// group
							frontButtons.add(newBut); // and to the arraylist
						}

						int[] backDims = genGrid(backButtons.size()); // generate
																		// the
																		// best
																		// dimensions
																		// for
																		// the
																		// grid
																		// layout
						JPanel backsPanel = new JPanel(new GridLayout(
								backDims[0], backDims[1])); // create a panel to
															// hold the buttons,
															// using grid layout
															// with the
															// best-dimensions
						for (Iterator<JToggleButton> it = backButtons
								.iterator(); it.hasNext();)
							backsPanel.add(it.next()); // go through the
														// arrayList, and add
														// each button to the
														// panel

						int[] frontDims = genGrid(frontButtons.size()); // generate
																		// a new
																		// grid
																		// for
																		// these
																		// buttons
						JPanel frontsPanel = new JPanel(new GridLayout(
								frontDims[0], frontDims[1])); // create the
																// panel with
																// the best grid
						for (Iterator<JToggleButton> it = frontButtons
								.iterator(); it.hasNext();)
							frontsPanel.add(it.next()); // go through the
														// buttons and add each
														// to the panel

						int[] useDims = new int[2]; // the effective dimensions
						if (backDims[0] > frontDims[0])
							useDims[0] = backDims[0]; // use the greater
														// dimension (x)
						else
							useDims[0] = frontDims[0];
						if (backDims[1] > frontDims[1])
							useDims[1] = backDims[1]; // use the greater
														// dimension (y)
						else
							useDims[1] = frontDims[1];
						backsPanel.setPreferredSize(new Dimension(useDims[1]
								* (Card.WIDTH + 15), useDims[0]
								* (Card.HEIGHT + 15))); // set the panel sizes
														// with the effective
														// dimensions
						frontsPanel.setPreferredSize(new Dimension(useDims[1]
								* (Card.WIDTH + 15), useDims[0]
								* (Card.HEIGHT + 15)));

						stylesTabs.addTab("Backs", getImageIcon("Images"
								+ File.separator + "Back.png"), backsPanel,
								"Card Backs"); // add a tab to the tabbed panel
												// - Backs is the tab text. give
												// it an icon, use the panel as
												// the tab content and Card
												// Backs for the tooltip
						stylesTabs.setMnemonicAt(0, KeyEvent.VK_B); // the tab
																	// can be
																	// accessed
																	// with
																	// Alt+B

						stylesTabs.addTab("Fronts", getImageIcon("Images"
								+ File.separator + "Front.png"), frontsPanel,
								"Card Fronts"); // same thing - add a tab for
												// the front styles
						stylesTabs.setMnemonicAt(1, KeyEvent.VK_F); // Alt+F

						JButton closeButton = new JButton("Close"); // button to
																	// close the
																	// dialog
						closeButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								styleDialog.setVisible(false); // hide the
																// dialog
								styleDialog.dispose(); // let go of the
														// resources for the
														// dialog
							}
						});

						JButton revertButton = new JButton("Revert"); // revert
																		// button
						revertButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								board.setCardBack(oldBack); // set the old
															// values
								board.setCardFront(oldFront);
								board.repaint(); // repaint
								styleDialog.setVisible(false); // hide the
																// dialog
								styleDialog.dispose(); // dispose of the dialog
							}
						});

						JPanel buttonPanel = new JPanel(new FlowLayout()); // create
																			// a
																			// panel
																			// for
																			// the
																			// buttons
						buttonPanel.add(revertButton);
						buttonPanel.add(closeButton); // add the buttons to the
														// panel

						JPanel contentPanel = new JPanel(new BorderLayout(5, 5)); // create
																					// a
																					// new
																					// panel
																					// to
																					// be
																					// the
																					// content
																					// panel
						contentPanel.add(stylesTabs, BorderLayout.CENTER); // add
																			// the
																			// tabbed
																			// pane
																			// to
																			// the
																			// panel
						contentPanel.add(buttonPanel, BorderLayout.PAGE_END); // add
																				// the
																				// panel
																				// with
																				// the
																				// close-button
																				// to
																				// the
																				// panel
						contentPanel.setOpaque(true); // paint all the pixels,
														// don't skip any
						styleDialog.setContentPane(contentPanel); // the the
																	// content
																	// pane of
																	// the
																	// dialog
																	// box

						styleDialog.pack(); // pack the dialog
						styleDialog.setLocationRelativeTo(TriPeaks.this); // set
																			// the
																			// location
																			// relative
																			// to
																			// the
																			// frame
																			// (in
																			// its
																			// center)
						styleDialog.setVisible(true); // show the dialog
					}
				});
		optionMenu.add(cardStyle); // add it to the menu

		JMenuItem boardColor = new JMenuItem("Board Background"); // change the
																	// boackground
																	// color of
																	// the board
		boardColor.setMnemonic(KeyEvent.VK_B); // Alt+B
		boardColor.getAccessibleContext().setAccessibleDescription(
				"Change the Background Color of the board"); // tool-tip
		boardColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = JColorChooser.showDialog(TriPeaks.this,
						"Choose Background Color", board.getBackColor()); // show
																			// a
																			// color
																			// chooser,
																			// with
																			// the
																			// current
																			// color
																			// as
																			// the
																			// default
				if (newColor != null)
					board.setBackColor(newColor); // if the user didn't click
													// Cancel, set the color
				board.repaint(); // repaint the baord.
			}
		});
		optionMenu.add(boardColor); // add the item to the menu

		JMenuItem fontSelect = new JMenuItem("Text Font"); // change the font of
															// the text on the
															// board
		fontSelect.setMnemonic(KeyEvent.VK_F); // Alt+F
		fontSelect.getAccessibleContext().setAccessibleDescription(
				"Change the font of the text on the board"); // tool-tip text
		fontSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JDialog fontDialog = new JDialog(TriPeaks.this,
						"Choose Board Font", true); // create the dialog
				final Color oldColor = board.getFontColor(); // get the old
																// color - in
																// order to
																// revert
				final Font oldFont = board.getTextFont(); // get the old color

				JPanel contentPanel = new JPanel(); // a panel to hold
													// everything
				contentPanel.setLayout(new BoxLayout(contentPanel,
						BoxLayout.PAGE_AXIS)); // align stuff on the y-axis

				JLabel title = new JLabel("Font Chooser"); // a title
				title.setFont(new Font("Serif", Font.BOLD, 20)); // make it big
																	// & bold
				title.setAlignmentX(Component.CENTER_ALIGNMENT); // center it
				title.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // give
																				// it
																				// 5
																				// pixels
																				// padding
																				// on
																				// each
																				// side
				contentPanel.add(title); // add it to the main panel

				JPanel selPanel = new JPanel(new FlowLayout()); // the selection
																// panel
				contentPanel.add(selPanel); // add it to the main panel

				final JLabel preview = new JLabel("TriPeaks = Good Game"); // a
																			// preview
																			// label
																			// -
																			// very
																			// important.
																			// All
																			// values
																			// are
																			// "stored"
																			// in
																			// it
																			// because
																			// any
																			// change
																			// is
																			// reflected
																			// in
																			// the
																			// label
				preview.setFont(oldFont); // set the old font (current)
				preview.setOpaque(true); // make the label opaque
				preview.setForeground(oldColor); // set the color of the text
				preview.setBackground(board.getBackColor()); // set the
																// background as
																// the
																// background
																// color of the
																// board
				preview.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3)); // give
																				// it
																				// 3
																				// px.
																				// padding
																				// on
																				// each
																				// side
				preview.setAlignmentX(Component.CENTER_ALIGNMENT); // center-align
																	// it

				final String[] fonts = GraphicsEnvironment
						.getLocalGraphicsEnvironment()
						.getAvailableFontFamilyNames(); // get a list of
														// available fonts
				int selIndex = 0; // initial selection index
				for (int q = 0; q < fonts.length; q++) { // go through available
															// fonts
					if (oldFont.getFamily().equals(fonts[q]))
						selIndex = q; // find the old font's index
				}

				JList fontList = new JList(fonts); // a list for the fonts
				fontList.addListSelectionListener(new ListSelectionListener() { // add
																				// a
																				// list
																				// selection
																				// listener
																				// (when
																				// the
																				// selection
																				// changes)
					public void valueChanged(ListSelectionEvent evt) {
						if (evt.getValueIsAdjusting())
							return; // if the user isn't done selecting, don't
									// do anything
						int selected = evt.getLastIndex(); // get the new
															// selection index
						int bold = (preview.getFont().isBold()) ? Font.BOLD : 0; // get
																					// the
																					// bold
																					// and
																					// italic
																					// status
																					// of
																					// the
																					// preview
						int ital = (preview.getFont().isItalic()) ? Font.ITALIC
								: 0;
						int size = preview.getFont().getSize(); // get the font
																// size of the
																// preview
						preview.setFont(new Font(fonts[selected], bold | ital,
								size)); // set the new font
					}
				});
				fontList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // only
																				// one
																				// font
																				// can
																				// be
																				// selected
				fontList.setSelectedIndex(selIndex); // set the initial
														// selection index
				fontList.setLayoutOrientation(JList.VERTICAL); // give it a
																// vertical
																// orientation
																// (all in one
																// column)
				fontList.setVisibleRowCount(10); // 10 items are visible

				JScrollPane fontScroll = new JScrollPane(fontList); // give the
																	// list
																	// scrollbars
				fontScroll.setBorder(BorderFactory.createTitledBorder(
						BorderFactory.createEtchedBorder(), "Font")); // give
																		// the
																		// scroll
																		// pane
																		// an
																		// etched
																		// border
																		// with
																		// the
																		// title
																		// "Font"

				selPanel.add(fontScroll); // add it to the selection panel
				fontList.ensureIndexIsVisible(selIndex); // scroll so the
															// initial selection
															// is visible

				JPanel otrPanel = new JPanel(); // a panel for other stuff
				otrPanel.setLayout(new BoxLayout(otrPanel, BoxLayout.PAGE_AXIS)); // align
																					// stuff
																					// on
																					// the
																					// y-axis
				otrPanel.setBorder(BorderFactory.createTitledBorder(
						BorderFactory.createEtchedBorder(), "Other Options")); // give
																				// the
																				// panel
																				// a
																				// border
																				// (etched,
																				// with
																				// title)
				selPanel.add(otrPanel); // add it to the selection panel

				JLabel sizeLabel = new JLabel("Size:"); // a label for the size
														// spinner
				sizeLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // left-align
																	// it.
				otrPanel.add(sizeLabel); // add it to the panel

				SpinnerModel sizeSpinModel = new SpinnerNumberModel(oldFont
						.getSize(), 8, 18, 1); // create a spinner model - from
												// 8 to 18 by 1's, starting at
												// the current size.
				final JSpinner sizeSpin = new JSpinner(sizeSpinModel); // the
																		// spinner
																		// (final
																		// because
																		// it's
																		// accessed
																		// in a
																		// nested
																		// class
				sizeSpin.addChangeListener(new ChangeListener() { // add a
					// listener
					// for
					// changes
					public void stateChanged(ChangeEvent evt) {
						SpinnerNumberModel model = (SpinnerNumberModel) sizeSpin
								.getModel(); // get the spinner's model
						String fontName = preview.getFont().getFamily(); // get
																			// the
																			// font,
																			// bold,
																			// and
																			// italic
																			// status
																			// from
																			// the
																			// preview
						int bold = (preview.getFont().isBold()) ? Font.BOLD : 0;
						int ital = (preview.getFont().isItalic()) ? Font.ITALIC
								: 0;
						int size = model.getNumber().intValue(); // get the new
																	// size from
																	// the
																	// spinner
																	// model
						preview.setFont(new Font(fontName, bold | ital, size)); // set
																				// the
																				// font
																				// on
																				// the
																				// preview
					}
				});
				JFormattedTextField textField = ((JSpinner.DefaultEditor) sizeSpin
						.getEditor()).getTextField(); // get the text field part
														// of the spinner
				textField.setColumns(4); // 4 columns is more that adequate
				textField.setHorizontalAlignment(JTextField.LEFT); // left-align
																	// the
																	// number
				sizeSpin.setAlignmentX(Component.LEFT_ALIGNMENT); // left-align
																	// the
																	// spinner
				otrPanel.add(sizeSpin); // add it to the panel

				JCheckBox boldCheck = new JCheckBox("Bold", oldFont.isBold()); // a
																				// checkbox
																				// for
																				// the
																				// bold
																				// status,
																				// with
																				// the
																				// old
																				// status
																				// as
																				// the
																				// default
				boldCheck.addItemListener(new ItemListener() { // add a listener
							public void itemStateChanged(ItemEvent evt) {
								String fontName = preview.getFont().getFamily(); // get
																					// the
																					// stuff
																					// from
																					// the
																					// preview
																					// panel
																					// (except
																					// bold)
								int bold = (evt.getStateChange() == ItemEvent.SELECTED) ? Font.BOLD
										: 0; // set it to bold if the checkbox
												// was checked
								int ital = (preview.getFont().isItalic()) ? Font.ITALIC
										: 0;
								int size = preview.getFont().getSize();
								preview.setFont(new Font(fontName, bold | ital,
										size)); // set the new font
							}
						});
				boldCheck.setAlignmentX(Component.LEFT_ALIGNMENT); // left-align
																	// the
																	// checkbox
				otrPanel.add(boldCheck); // add it to the panel

				JCheckBox italCheck = new JCheckBox("Italic", oldFont
						.isItalic()); // a checkbox for the italic status - same
										// as above
				italCheck.addItemListener(new ItemListener() {
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

				final JButton colorBut = new JButton("Font Color"); // a button
																	// to select
																	// the text
																	// color
				colorBut.addActionListener(new ActionListener() { // add an
																	// action
																	// listener
					public void actionPerformed(ActionEvent evt) {
						Color newColor = JColorChooser.showDialog(
								TriPeaks.this, "Choose Font Color",
								preview.getForeground()); // show a color
															// chooser - default
															// color is the
															// current color
						if (newColor != null) { // if the user didn't click
												// 'Cancel'
							colorBut.setForeground(newColor); // set the text
																// color on the
																// button
							preview.setForeground(newColor); // and on the
																// preview label
						}
					}
				});
				colorBut.setForeground(oldColor); // set the default text color
				colorBut.setBackground(board.getBackColor()); // set the
																// background
																// color of the
																// button
				colorBut.setAlignmentX(Component.LEFT_ALIGNMENT); // left-align
																	// the
																	// button
				otrPanel.add(colorBut); // add it to the panel

				JPanel previewPanel = new JPanel(); // a panel for the preview
													// label (so the label's
													// background color works
													// properly)
				previewPanel.setLayout(new BoxLayout(previewPanel,
						BoxLayout.PAGE_AXIS)); // align stuff on the y-axis
				previewPanel.add(preview); // add the label to it
				previewPanel.setBorder(BorderFactory.createTitledBorder(
						BorderFactory.createEtchedBorder(), "Preview")); // give
																			// it
																			// an
																			// etched,
																			// titled
																			// border.
				contentPanel.add(previewPanel); // add it to the main panel

				JPanel buttonPanel = new JPanel(new FlowLayout()); // a panel to
																	// hold the
																	// buttons

				JButton closeButton = new JButton("OK"); // OK button
				closeButton.getAccessibleContext().setAccessibleDescription(
						"Apply the font and close"); // tool-tip text
				closeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						board.setFontColor(preview.getForeground()); // set the
																		// font
																		// color
						board.setTextFont(preview.getFont()); // set the font
						board.repaint(); // repaint the board
						fontDialog.setVisible(false); // hide the dialog
						fontDialog.dispose(); // dispose of it
					}
				});
				buttonPanel.add(closeButton); // add it to the panel

				JButton revertButton = new JButton("Cancel"); // revert button
				revertButton.getAccessibleContext().setAccessibleDescription(
						"Revert to the previously used font"); // tool-tip
				revertButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						board.setFontColor(oldColor); // set the old values
						board.setTextFont(oldFont);
						board.repaint(); // repaint the board
						fontDialog.setVisible(false); // hide the dialog
						fontDialog.dispose(); // dispose of it
					}
				});
				buttonPanel.add(revertButton); // add it to the panel

				JButton applyButton = new JButton("Apply"); // apply changes
															// button
				applyButton.getAccessibleContext().setAccessibleDescription(
						"Apply the new Font"); // tool-ip
				applyButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						board.setFontColor(preview.getForeground()); // set new
																		// values
						board.setTextFont(preview.getFont());
						board.repaint(); // repaint the board
					}
				});
				buttonPanel.add(applyButton); // add it to the panel

				contentPanel.add(buttonPanel); // add the button panel to the
												// main panel

				fontDialog.setContentPane(contentPanel); // set the main panel
															// for the dialog
				fontDialog.pack(); // pack the dialog
				fontDialog.setResizable(false);
				fontDialog.setLocationRelativeTo(TriPeaks.this); // center it
																	// relative
																	// to the
																	// frame
				fontDialog.setVisible(true); // show it.
			}
		});
		optionMenu.add(fontSelect); // add the item to the menu.

		statsCheck = new JCheckBoxMenuItem("Show stats", true); // a checkbox to
																// show/hide
																// stats (show
																// by default)
		statsCheck.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.ALT_MASK));
		statsCheck.getAccessibleContext().setAccessibleDescription(
				"Show / Hide stats");
		statsCheck.addItemListener(new ItemListener() { // add an Item-event
														// listener - changes to
														// the item
					public void itemStateChanged(ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED) { // if it
																		// got
																		// selected
							statsPanel.setVisible(true); // show the stats panel
							updateStats(); // set the labels
						} else
							statsPanel.setVisible(false); // hide the stats
															// panel
						pack(); // re-pack the frame
					}
				});
		optionMenu.add(statsCheck); // add it to the menu

		JMenuItem resetDefs = new JMenuItem("Reset Defaults"); // Resets
																// settings to
																// their
																// defaults
		resetDefs.getAccessibleContext().setAccessibleDescription(
				"Reset the settings to their default values"); // set the
																// tooltip text
		resetDefs.addActionListener(new ActionListener() { // add action
															// listener
					public void actionPerformed(ActionEvent e) {
						int uI = JOptionPane.showConfirmDialog(TriPeaks.this,
								"Are you sure you want to reset ALL settings?",
								"Confirm Reset", JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE); // show a
																// confirmation
																// dialog
						if (uI == JOptionPane.YES_OPTION) { // if the user chose
															// 'yes'
							board.setDefaults(); // set the defaults on the
													// board
							board.repaint(); // repaint the board
							statsCheck.setSelected(true); // show the stats
															// panel
						}
					}
				});
		optionMenu.add(resetDefs); // add it to the menu

		JMenu cheatMenu = new JMenu("Cheats"); // a menu with cheats
		cheatMenu.addMenuListener(new MenuListener() { // add a menu listener to
					// it
					public void menuSelected(MenuEvent e) { // when the menu was
															// selected
						if (!board.hasCheated() && !seenWarn)
							JOptionPane
									.showMessageDialog(
											TriPeaks.this,
											"Using Cheats will SCAR your name!!!\nThe only way to un-scar is to RESET!!!\nProceed at your own risk!!!",
											"Cheat Warning!",
											JOptionPane.WARNING_MESSAGE); // if
																			// the
																			// user
																			// hasn't
																			// cheated
																			// yet,
																			// display
																			// a
																			// warning.
						seenWarn = true;
					}

					public void menuDeselected(MenuEvent e) {
					} // not interested in these, but necessary for
						// implementation

					public void menuCanceled(MenuEvent e) {
					}
				});
		menuBar.add(cheatMenu); // add it to the menu bar

		cheatItems[0] = new JCheckBoxMenuItem("Cards face up"); // cheat 1 - all
																// cards appear
																// face-up
																// (doesn't
																// actually make
																// them face-up)
		cheatItems[0].addItemListener(new ItemListener() { // add item listener
					public void itemStateChanged(ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED)
							board.setCheat(0, true); // if it was checked,
														// enable the cheat
						else
							board.setCheat(0, false); // if it was unchecked,
														// disable the cheat
						board.repaint(); // repaint the board
						setTitle("TriPeaks - Cheat Mode"); // set the cheating
															// title bar
					}
				});
		cheatMenu.add(cheatItems[0]);
		// same thing for the rest of the cheats
		cheatItems[1] = new JCheckBoxMenuItem("Click any card"); // cheat 2 -
																	// click any
																	// card
																	// that's
																	// face-up
																	// (regardless
																	// of value)
		cheatItems[1].addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					board.setCheat(1, true);
				else
					board.setCheat(1, false);
				board.repaint();
				setTitle("TriPeaks - Cheat Mode");
			}
		});
		cheatMenu.add(cheatItems[1]);

		cheatItems[2] = new JCheckBoxMenuItem("No Penalty"); // cheat 3 - no
																// penalty
																// (score can
																// never go
																// down)
		cheatItems[2].addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					board.setCheat(2, true);
				else
					board.setCheat(2, false);
				board.repaint();
				setTitle("TriPeaks - Cheat Mode");
			}
		});
		cheatMenu.add(cheatItems[2]);

		menuBar.add(Box.createHorizontalGlue()); // The next menu will be on the
													// right

		JMenu helpMenu = new JMenu("Help"); // Help menu
		helpMenu.setMnemonic(KeyEvent.VK_H); // Accessed with Alt+H
		helpMenu.getAccessibleContext().setAccessibleDescription(
				"Game Help and Information"); // tool-tip text
		menuBar.add(helpMenu); // add it to the menu bar

		JMenuItem gameHelp = new JMenuItem("Help", getImageIcon("Images"
				+ File.separator + "help.png")); // basic explanation of
													// gameplay
		gameHelp.getAccessibleContext().setAccessibleDescription(
				"How to Play & Strategies");
		gameHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		gameHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JDialog helpDialog = new JDialog(TriPeaks.this,
						"How to Play"); // create a new dialog box

				Font titleFont = new Font("SansSerif", Font.BOLD, 16);
				Font textFont = new Font("Serif", Font.PLAIN, 14);

				JLabel titleHelp = new JLabel("How to Play"); // the title text
				titleHelp.setFont(titleFont); // make it big and bold
				titleHelp.setHorizontalAlignment(JLabel.CENTER); // make it
																	// centered

				JTextArea textHelp = new JTextArea(); // create the area for the
														// text
				textHelp.setText("   The goal of the game is to remove all the cards: you can remove any card that is adjacent in value. (e.g. If you have an Ace, you can remove a King or a Two). Suit doesn't matter.\n   If there is no adjacent card, you can take a card from the deck, with a penalty of $5. For the first card you remove, you get $1; for the second $2; $3 for the third; and so on. However, when you take a card from the deck, the streak gets reset to 0.\n   You get $15 for the first two peaks that you reach, and $30 for the last one (i.e. clearing the board). You can redeal before you clear the board AND still have some cards in the deck, but with a penalty of $5 for every card on the board. There is no penalty for redealing if your deck is empty or if you've cleared the board."); // set
																																																																																																																																																																																																										// the
																																																																																																																																																																																																										// text
																																																																																																																																																																																																										// of
																																																																																																																																																																																																										// the
																																																																																																																																																																																																										// text
																																																																																																																																																																																																										// area
				textHelp.setEditable(false); // the user can't change the help
												// text
				textHelp.setFont(textFont); // set the font for the text
				textHelp.setLineWrap(true); // the text will wrap at the edges
				textHelp.setWrapStyleWord(true); // the text will only wrap
													// whole words

				JScrollPane helpScroll = new JScrollPane(textHelp); // used to
																	// add
																	// scrollbars
																	// to the
																	// text area
				helpScroll
						.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

				JPanel helpPanel = new JPanel(new BorderLayout(3, 3)); // create
																		// a
																		// panel
																		// to
																		// hold
																		// the
																		// scroll
																		// pane
																		// and
																		// title
				helpPanel.add(titleHelp, BorderLayout.PAGE_START); // add the
																	// title to
																	// the top
				helpPanel.add(helpScroll, BorderLayout.CENTER); // add the
																// scroll pane
																// to the center
				// same thing for the srategy and cheat text
				JLabel titleStrat = new JLabel("Game Strategies");
				titleStrat.setFont(titleFont);
				titleStrat.setHorizontalAlignment(JLabel.CENTER);

				JTextArea textStrat = new JTextArea();
				textStrat
						.setText("   The more cards you get in a row, the higher your score. However, there are times when you have to choose between cards. If those cards get you the same score, there are several strategies involved:\n   1)  Pick the card that opens up more cards That will give you more to choose from on your next move. It might go with the card you just took.\n   2)  If one on the choices is a peak, don't choose the peak. It doesn't open any cards.\n   Other than choosing cards, try working out a streak in your head. If they're the same, go with the one that opens more cards.");
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

				JTabbedPane helpTabs = new JTabbedPane(); // Initialize the
															// tabbed pane

				helpTabs.addTab("How To Play", getImageIcon("Images"
						+ File.separator + "help.png"), helpPanel,
						"How to Play"); // add the tab to the tabbed pane
				helpTabs.setMnemonicAt(0, KeyEvent.VK_P); // Alt+P
				helpTabs.addTab("Strategies", getImageIcon("Images"
						+ File.separator + "Strategy.png"), stratPanel,
						"Game Strategies");
				helpTabs.setMnemonicAt(1, KeyEvent.VK_S); // Alt+S
				helpTabs.addTab("Cheats", getImageIcon("Images"
						+ File.separator + "cheat.png"), cheatPanel,
						"Game Cheats");
				helpTabs.setMnemonicAt(2, KeyEvent.VK_C); // Alt+C

				helpScroll.getVerticalScrollBar().setValue(0);
				stratScroll.getVerticalScrollBar().setValue(0);
				cheatScroll.getVerticalScrollBar().setValue(0);

				JButton closeButton = new JButton("Close"); // button to close
															// the dialog
				closeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						helpDialog.setVisible(false); // hide the dialog
						helpDialog.dispose(); // dispose of the resources for
												// the dialog
					}
				});

				JPanel closePanel = new JPanel(); // a panel for the butotn
				closePanel.setLayout(new BoxLayout(closePanel,
						BoxLayout.LINE_AXIS)); // Align stuff on the X-Axis
				closePanel.add(Box.createHorizontalGlue()); // right-align the
															// button
				closePanel.add(closeButton); // add the button to the panel
				closePanel.setBorder(BorderFactory
						.createEmptyBorder(0, 0, 5, 5));

				JPanel contentPanel = new JPanel(new BorderLayout(5, 5)); // create
																			// a
																			// panel
																			// to
																			// be
																			// the
																			// content
																			// panel,
																			// with
																			// a
																			// 5-pixel
																			// gap
																			// between
																			// elements
				contentPanel.add(helpTabs, BorderLayout.CENTER); // add the
																	// tabbed
																	// pane to
																	// the
																	// center
				contentPanel.add(closePanel, BorderLayout.PAGE_END); // add the
																		// panel
																		// with
																		// the
																		// close-button
																		// to
																		// the
																		// bottom
				helpDialog.setContentPane(contentPanel); // set the panel as the
															// content pane

				helpDialog.setSize(new Dimension(400, 400)); // make the dialog
																// 400 x 400
																// pixels
				helpDialog.setLocationRelativeTo(TriPeaks.this); // make it
																	// relative
																	// to the
																	// frame (in
																	// the
																	// center of
																	// the
																	// frame)
				helpDialog.setVisible(true); // show the dialog
			}
		});
		helpMenu.add(gameHelp); // add the item to the menu

		helpMenu.addSeparator(); // add a separator to the menu

		JMenuItem about = new JMenuItem("About..."); // about the
														// program/creator
		about.setMnemonic(KeyEvent.VK_A);
		about.getAccessibleContext().setAccessibleDescription(
				"About the creator and program");
		about.addActionListener(new ActionListener() { // add an action listener
			public void actionPerformed(ActionEvent e) {
				JOptionPane
						.showMessageDialog(
								TriPeaks.this,
								"TriPeaks Solitaire implementation by Valera Trubachev.\nWritten in Java using Kate in Linux.\n(C) 2008\nSpecial thanks to Christian d'Heureuse\nfor his Base64 encoder/decoder."); // kind
																																																					// of
																																																					// like
																																																					// some
																																																					// credits...
			}
		});
		helpMenu.add(about); // add the item to the menu

		return menuBar; // return the finished menu bar
	}

	public void updateStats() { // sets the text of the stats labels
		if (!statsPanel.isVisible())
			return; // if the stats panel isn't shown, don't do anything
		int[] stats = board.getAllStats(); // get the stats, which are stored in
											// the board
		DecimalFormat intFmt = new DecimalFormat("$###,###");
		DecimalFormat dblFmt = new DecimalFormat("$###,##0.00");
		curGame.setText("Game Winnings:  " + intFmt.format(stats[1])); // what
																		// was
																		// won/lost
																		// in
																		// the
																		// current
																		// game
		maxMin.setText("Most - Won:  " + intFmt.format(stats[6]) + ", Lost:  "
				+ intFmt.format(stats[7])); // record win/loss during any game
		curStr.setText("Current Streak:  " + stats[3] + " = "
				+ intFmt.format((stats[3] * (stats[3] + 1) / 2))); // current
																	// streak
		sesWin.setText("Session Winnings:  " + intFmt.format(stats[2])); // what
																			// was
																			// won/lost
																			// during
																			// the
																			// session
																			// (start
																			// program
																			// =
																			// new
																			// session)
		if (stats[5] != 0) { // if some games were played (so denominator
								// doesn't equal 0)
			double avg = ((double) stats[2]) / ((double) stats[5]); // calulate
																	// the
																	// average
			sesAvg.setText("Session Average:  " + dblFmt.format(avg)); // round
																		// the
																		// average
		} else
			sesAvg.setText("Session Average:  $0.00"); // set it to 0 if no
														// games were played
		sesGame.setText("Session Games:  " + stats[5]); // how many games were
														// played during the
														// session
		plrGame.setText("Player Games:  " + stats[4]); // how many games the
														// player played
														// altogether
		if (stats[4] != 0) { // if the player has played any games
			double avg = ((double) stats[0]) / ((double) stats[4]); // calculate
																	// the
																	// average
			plrAvg.setText("Player Average:  " + dblFmt.format(avg)); // round
																		// the
																		// average
		} else
			plrAvg.setText("Player Average:  $0.00"); // set it to 0 is no games
														// were ever played
		maxStr.setText("Longest Streak:  " + stats[8] + " = "
				+ intFmt.format((stats[8] * (stats[8] + 1) / 2))); // longest
																	// streak
																	// ever by
																	// the
																	// player
	}

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

	public static String capitalize(String in) {
		if (in.length() == 0)
			return "";
		if (in.length() == 1)
			return in.toUpperCase();
		return Character.toUpperCase(in.charAt(0)) + in.substring(1);
	}

	public static String dSign(int in) { // add a dollar sign to a number
		if (in < 0)
			return ("-$" + (-1) * in); // put the negative sign out in front if
										// it's negative
		else
			return "$" + in; // otherwise just add the dollar sign
	}

	public int randInt(int max) { // returns a random integer - 0 <= anInt < max
		int anInt = (int) (max * Math.random()); // generate a the random value
		return anInt; // return it
	}

	public int[] genGrid(int num) { // generates an "optimal" grid based on the
									// number of elements
		int[] dim = new int[2]; // the array for the dimensions
		for (int q = 1; q <= num; q++) { // go through each of the numbers to
											// the given one
			if (q * q == num) { // if it's a perfect square
				dim[0] = dim[1] = q; // set both values as the given number's
										// square root
				return dim; // return the dimensions
			}
		}
		for (int q = 1; q <= num; q++) { // go through the numbers again - check
											// for something else
			int w; // a placeholder
			for (w = 1; w <= q + 2; w++) { // go from 1 to 2 more than the
											// current number
				if (q * w >= num) { // if the grid will fit
					dim[0] = q; // set the first value
					dim[1] = w; // and the second
					return dim; // return the dimensions
				}
			}
			if ((q + 1) * (q + 2) >= num) { // if +1 and +2 will satisfy the
											// number
				dim[0] = q + 1; // set the first value
				dim[1] = q + 2; // set the second value
				return dim; // return the dimensions
			}
			for (; w <= q + 4; w++) { // go to the 4 more than the current
										// number (no initialization statement -
										// go from the previous for left off)
				if (q * w >= num) { // if the grid will fit
					dim[0] = q; // set the first value
					dim[1] = w; // and the second
					return dim; // return the dimensions
				}
			}
		}
		return dim; // if something BAD happened, return 0 x 0
	}

	public static String rot13(String in) { // calculates the ROT13 cipher of a
											// string
		String low = in.toLowerCase(); // only lowercase characters are wanted
		StringBuffer out = new StringBuffer(); // a buffer for the output string
		final String letters = "abcdefghijklmnopqrstuvwxyz"; // all the letters
																// of the
																// alphabet
		int index, newIndex; // two index holders
		for (int q = 0; q < low.length(); q++) { // go through the letters in
													// the input string
			index = letters.indexOf(low.charAt(q)); // find the current
													// character's index in the
													// alphabet string
			if (index == -1)
				continue; // if the letter wasn't found, skip it
			newIndex = (index + 13) % 26; // do the rotation by 13
			out.append(letters.charAt(newIndex)); // append the ciphered
													// characted
		}
		return out.toString(); // return the ciphered string
	}

	public static String backward(String in) { // reverse a string
		StringBuffer out = new StringBuffer(); // buffer for output
		for (int q = 1; q <= in.length(); q++) { // go through the characters
			out.append(in.charAt(in.length() - q)); // append that character
													// from the end of the
													// string
		}
		return out.toString(); // return the reversed string
	}

	public void readScoreSets() throws NewPlayerException { // reads the scores
															// from the current
															// user's file.
		String fileName = rot13(uName); // the filename is the ROT13 cipher of
										// their name
		File file = new File(dirName + File.separator + fileName + ".txt"); // get
																			// the
																			// file
		String line = null; // placeholder for the line
		int[] stats = new int[CardPanel.NSTATS]; // the array for the stats
		boolean[] cheats = new boolean[CardPanel.NCHEATS]; // cheats array for
															// the cheat menu
															// items
		boolean hasCheated = false; // the cheat status
		int lNum = -1; // line number (incremented before setting value)
		Encryptor dec = new Encryptor(backward(fileName)); // set up the
															// encryptor to
															// decrypt the lines
															// (the passphrase
															// is the filename
															// backwards)
		try {
			if (file == null)
				throw new NewPlayerException("New Player: " + uName); // if the
																		// file
																		// is
																		// null,
																		// don't
																		// do
																		// anything
			BufferedReader in = new BufferedReader(new FileReader(file)); // create
																			// a
																			// buffered
																			// reader
																			// for
																			// the
																			// file
			String deced;
			while ((line = in.readLine()) != null) { // read the lines
														// one-by-one
				lNum++; // increment the line number
				if (lNum > (stats.length + cheats.length + 6))
					break; // stop if there are more lines than needed
				deced = dec.decrypt(line);
				if ((lNum >= 0) && (lNum < stats.length))
					stats[lNum] = Integer.parseInt(deced); // set the value
															// based on the
															// decrypted line,
															// if the line
															// belongs to the
															// stats array
				else if ((lNum >= stats.length)
						&& (lNum < (stats.length + cheats.length)))
					cheats[lNum - stats.length] = Boolean.parseBoolean(deced); // set
																				// the
																				// values
																				// based
																				// on
																				// the
																				// decrypted
																				// line,
																				// if
																				// the
																				// line
																				// belongs
																				// to
																				// the
																				// cheats
																				// array
				else if (lNum == stats.length + cheats.length)
					hasCheated = Boolean.parseBoolean(deced);
				else if (lNum == stats.length + cheats.length + 1)
					board.setCardFront(deced);
				else if (lNum == stats.length + cheats.length + 2)
					board.setCardBack(deced);
				else if (lNum == stats.length + cheats.length + 3) {
					int cm1, cm2; // two commas
					cm1 = deced.indexOf(','); // get the indexes of the two
												// commas
					cm2 = deced.lastIndexOf(',');
					if ((cm1 == -1) || (cm2 == -1) || (cm1 == cm2))
						continue; // if either comma isn't found, exit
					board.setBackColor(new Color(Integer.parseInt(deced
							.substring(0, cm1)), Integer.parseInt(deced
							.substring(cm1 + 1, cm2)), Integer.parseInt(deced
							.substring(cm2 + 1)))); // convert to integer and
													// set the color
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
						JOptionPane
								.showMessageDialog(
										this,
										"Score file has been modified since\nlast used by TriPeaks!\nThe file HAS BEEN DELETED!!!\nPlease don't cheat like that again!",
										"Cheating Error",
										JOptionPane.ERROR_MESSAGE);
						board.setDefaults();
						board.reset();
						return;
					}
				}
			}
			board.setStats(stats); // set the stats in the board
			board.setCheated(hasCheated); // set the cheat status
			setTitle(hasCheated ? "TriPeaks - Cheat Mode" : "TriPeaks"); // set
																			// the
																			// title
																			// based
																			// on
																			// the
																			// cheat
																			// status
			for (int q = 0; q < cheats.length; q++) { // go through the cheats
				cheatItems[q].setSelected(cheats[q]); // set the selected status
														// of the menu items
														// used for the cheats
			}
			updateStats(); // update the labels
			board.repaint(); // repaint the board
			in.close(); // close the file
		} catch (FileNotFoundException eFNF) { // file wasn't found (probalby
												// because the user doesn't
												// exist yet
			System.out
					.println("File not found (probably because the User hasn't played before): "
							+ eFNF.getMessage());
		} catch (IOException eIO) { // other IO error
			System.out.println("Error reading from file -OR- closing file");
		}
	}

	public void writeScoreSets() { // writes the scores for the current player
		String fileName = rot13(uName); // filename is the ROT13 cipher of the
										// username
		File setFile = new File(dirName + File.separator + fileName + ".txt"); // create
																				// the
																				// file
		Encryptor enc = new Encryptor(backward(fileName)); // set up the
															// encryptor to
															// encrpyt the lines
		try {
			if (setFile == null)
				return; // if the file doesn't exist, don't do anything
			BufferedWriter out = new BufferedWriter(new FileWriter(setFile)); // create
																				// a
																				// buffered
																				// writer
																				// for
																				// the
																				// file
			boolean[] cheats = board.getCheats();
			Color boardColor = board.getBackColor();
			Font textFont = board.getTextFont();
			Color fontColor = board.getFontColor();
			long dtMod = new Date().getTime();
			out.write(enc.encrypt("" + board.getScore())); // player's overall
															// score
			out.newLine(); // new line
			out.write(enc.encrypt("" + board.getHighScore())); // player's
																// highes score
			out.newLine();
			out.write(enc.encrypt("" + board.getLowScore())); // player's lowest
																// score
			out.newLine();
			out.write(enc.encrypt("" + board.getNumGames())); // number of games
																// played by the
																// user
			out.newLine();
			out.write(enc.encrypt("" + board.getHighStreak())); // player's
																// longest
																// streak
			out.newLine();
			out.write(enc.encrypt("" + cheats[0])); // first cheat
			out.newLine();
			out.write(enc.encrypt("" + cheats[1])); // second cheat
			out.newLine();
			out.write(enc.encrypt("" + cheats[2])); // third cheat
			out.newLine();
			out.write(enc.encrypt("" + board.hasCheated())); // player's cheat
																// status
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
			out.close(); // close the file
			setFile.setLastModified(dtMod);
		} catch (FileNotFoundException eFNF) { // file wasn't found
			System.out.println("File not found: " + eFNF.getMessage());
		} catch (IOException eIO) { // other IO exception
			System.out.println("Error writing to file -OR- closing file");
		}
	}

	public void windowOpened(WindowEvent e) { // the window is opened
		InputStream is = TriPeaks.class.getResourceAsStream(settingsFile); // get
																			// the
																			// file
																			// as
																			// a
																			// stream
		String line = null; // placeholder for the line
		String defName = "";

		try {
			if (is == null)
				throw new Exception("First Time Running");
			BufferedReader in = new BufferedReader(new InputStreamReader(is)); // create
																				// a
																				// buffered
																				// reader
																				// for
																				// the
																				// file
			if ((line = in.readLine()) != null) { // read the line
				defName = line;
			}
			in.close(); // close the file
		} catch (FileNotFoundException eFNF) { // file wasn't found (probably
												// first time running)
			System.out
					.println("File not found (probably because the User hasn't played before): "
							+ eFNF.getMessage());
		} catch (IOException eIO) { // other IO error
			System.out.println("Error reading from file -OR- closing file");
		} catch (Exception eE) {
			System.out.println("First time run");
		}
		uName = JOptionPane.showInputDialog(this, "Player Name:", defName); // ask
																			// for
																			// the
																			// player's
																			// name
		if ((uName == null) || (uName.equals("")))
			System.exit(0); // if the name is empty or Cancel was pressed, exit
		try {
			readScoreSets(); // read the scores for the player
		} catch (NewPlayerException eNP) {
			board.setDefaults();
		}
	}

	public void windowClosing(WindowEvent e) { // the X is clicked (not when the
												// window disappears - that's
												// windowClosed
		int penalty = board.getPenalty(); // get the penalty for quitting
		if (penalty != 0) { // if there is a penalty at all
			int uI = JOptionPane.showConfirmDialog(this,
					"Are you sure you want to quit?\nQuitting now results in a penalty of $"
							+ penalty + "!", "Confirm Quit",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE); // show
																				// a
																				// confirmation
																				// message
			if (uI == JOptionPane.YES_OPTION) { // if the user clicked Yes
				board.doPenalty(penalty); // perform the penalty
			} else
				return; // no was clicked - don't do anything
		}
		File setFile = new File(settingsFile); // create the file
		try {
			if (setFile == null)
				return; // if the file doesn't exist, don't do anything
			BufferedWriter out = new BufferedWriter(new FileWriter(setFile)); // create
																				// a
																				// buffered
																				// writer
																				// for
																				// the
																				// file
			out.write(uName); // write the default username
			out.close(); // close the file
		} catch (FileNotFoundException eFNF) { // file wasn't found
			System.out.println("File not found: " + eFNF.getMessage());
		} catch (IOException eIO) { // other IO exception
			System.out.println("Error writing to file -OR- closing file");
		}
		writeScoreSets(); // write the scores for the user
		System.exit(0); // exit
	}

	// the following methods aren't used, but necessary to implement KeyListener
	// and WindowListener
	public void windowClosed(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}
} // end class TriPeaks
