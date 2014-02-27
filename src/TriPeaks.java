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
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
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
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.imageio.ImageIO;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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

class CardPanel extends JPanel implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Color backColor = (Color.GREEN).darker().darker(); // background
																// color of the
																// board
	private Color fontColor = Color.WHITE;
	private Font textFont = new Font("Serif", Font.BOLD, 14);
	public Card[] theCards = new Card[52]; // array with the cards
	public static final int NSTATS = 5;
	public static final int NCHEATS = 3;
	private boolean[] cheats = new boolean[NCHEATS];
	private boolean hasCheatedYet = false;
	private int disIndex = 51; // index of the card in the discard pile
	private int score = 0; // player's overall score
	private int gameScore = 0; // current game score
	private int sesScore = 0; // session score
	private int streak = 0; // streak (number of cards, not the value)
	private int remCards = 0; // cards remaining in the deck
	private int cardsInPlay = 0; // cards left on the board (not removed into
									// the discard pile)
	private int remPeaks = 3; // peaks remaining (0 is a clear board)
	private int numGames = 0; // number of player games
	private int sesGames = 0; // number of session games
	private int highScore = 0; // highest score
	private int lowScore = 0; // lowest score
	private int highStreak = 0; // longest strea
	private String status = ""; // status text (used later)
	private String frontFolder = "Default"; // folder in which the fronts of the
											// cards are stored
	private String backStyle = "Default"; // style for the back of the cards

	public CardPanel() { // class constructor
		for (int q = 0; q < 52; q++) { // initialize all the cards
			theCards[q] = new Card(0, 0, false, false, 0, 0); // create a new
																// Card object -
																// random values
																// - so it
																// doesn't throw
																// NullPointerException...
			theCards[q].setVisible(false);
		}
		setPreferredSize(new Dimension(Card.WIDTH * 10, Card.HEIGHT * 4)); // sets
																			// the
																			// size
																			// of
																			// the
																			// panel
																			// (10
																			// cards
																			// by
																			// 4
																			// cards)
		addMouseListener(this); // adds a mouse-listener to the board
	}

	public void paint(Graphics g) { // custom paint method
		super.paintComponent(g); // paints the JPanel
		g.setColor(backColor); // use the background color
		g.fillRect(0, 0, getSize().width, getSize().height); // draw the
																// background
		if (hasCheatedYet) { // if the user has ever cheated
			g.setColor(new Color(fontColor.getRed(), fontColor.getGreen(),
					fontColor.getBlue(), 80)); // set the color - white,
												// somewhat transparent
			g.setFont(new Font("SansSerif", Font.BOLD, 132)); // set the font -
																// big and fat
			g.drawString("CHEATER", 0, getSize().height - 5); // print "CHEATER"
																// on the bottom
																// edge of the
																// board
		}

		for (int q = 0; q < 52; q++) { // go through each card
			if (theCards[q] == null)
				continue; // if a card is null (i.e. program was just started,
							// cards not initialized yet), skip it
			if (!theCards[q].isVisible())
				continue; // if a card isn't visible, skip it
			BufferedImage img = null; // image to be created
			URL imgURL = null; // URL of the image

			if (!theCards[q].isFacingDown()) // if it's face-up
				imgURL = TriPeaks.class.getResource("CardSets" + File.separator
						+ "Fronts" + File.separator + frontFolder
						+ File.separator + theCards[q].suitAsString()
						+ (theCards[q].getValue() + 1) + ".png"); // get the
																	// corresponding
																	// front of
																	// the card
			else {// otherwise it's face-down
				if (!cheats[0])
					imgURL = TriPeaks.class.getResource("CardSets"
							+ File.separator + "Backs" + File.separator
							+ backStyle + ".png"); // get the image for the back
													// of the card - if the
													// first cheat isn't on
				else
					imgURL = TriPeaks.class.getResource("CardSets"
							+ File.separator + "Fronts" + File.separator
							+ frontFolder + File.separator
							+ theCards[q].suitAsString()
							+ (theCards[q].getValue() + 1) + ".png"); // get the
																		// corresponding
																		// front
																		// of
																		// the
																		// card
																		// if
																		// the
																		// cheat
																		// is
																		// on...
			}
			if (imgURL == null)
				continue;
			try {
				img = ImageIO.read(imgURL); // try to read the image
			} catch (IOException eIO) {
				System.out.println("Error reading card image"); // There's an
																// error
																// (probably
																// because the
																// card doesn't
																// exist.
			}
			if (img == null)
				continue;
			int startX = theCards[q].getX() - ((int) Card.WIDTH / 2); // left
																		// edge
																		// of
																		// the
																		// laft
			int startY = theCards[q].getY() - ((int) Card.HEIGHT / 2); // top of
																		// the
																		// card
			int endX = startX + Card.WIDTH; // right
			int endY = startY + Card.HEIGHT; // bottom
			g.drawImage(img, startX, startY, endX, endY, 0, 0,
					img.getWidth(null), img.getHeight(null), null); // draws the
																	// image on
																	// the panel
																	// -
																	// resizing/scaling
																	// if
																	// necessary
		}
		String scoreStr = (score < 0) ? "Lost $" + (-1) * score : "Won $"
				+ score; // The won/lost string
		String remStr = remCards + ((remCards == 1) ? " card" : " cards")
				+ " remaining"; // display how many cards are remaining
		g.setColor(fontColor); // the text is white
		g.setFont(textFont); // set the font for the text
		g.drawString(scoreStr, 5, Card.HEIGHT * 3); // put the score on the
													// panel
		g.drawString(remStr, 5, Card.HEIGHT * 3 + 25); // put the remaining
														// cards on the panel
		g.drawString(status, 5, getSize().height - 10); // print the status
														// message.
		status = ""; // reset the status message
	}

	public void redeal() { // redeals the cards
		int penalty = getPenalty(); // get the penalty for redealing
		if (penalty != 0) { // if there is a penalty
			int uI = JOptionPane.showConfirmDialog(this,
					"Are you sure you want to redeal?\nRedealing now results in a penalty of $"
							+ penalty + "!", "Confirm Redeal",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE); // show
																				// a
																				// confimation
																				// message
			if (uI == JOptionPane.YES_OPTION)
				doPenalty(penalty); // do the penalty if the user agreed
			else
				return; // the user doesn't like the penalty, don't rededal
		}
		int[] cards = randomize(); // randomize the cards
		for (int q = 0; q < 52; q++) { // initialize all the cards
			theCards[q] = new Card(); // create a new Card object
			theCards[q].setSuit((int) cards[q] / 13); // set the card's suit
			theCards[q].setValue(cards[q] % 13); // set its value
			theCards[q].setVisible(true); // all cards are visible, so far
		}
		for (int q = 0; q < 3; q++) { // first row
			theCards[q].setX(2 * Card.WIDTH + q * 3 * Card.WIDTH); // set the
																	// X-coord
			theCards[q].setY((int) Card.HEIGHT / 2); // set the Y-coord for the
														// card
			theCards[q].flip(true); // make it face-down
		}
		for (int q = 0; q < 6; q++) { // second row
			theCards[q + 3].setX(3 * ((int) Card.WIDTH / 2) + q * Card.WIDTH
					+ ((int) q / 2) * Card.WIDTH); // set the coords
			theCards[q + 3].setY(Card.HEIGHT);
			theCards[q + 3].flip(true); // face-down
		}
		for (int q = 0; q < 9; q++) { // third row
			theCards[q + 9].setX(Card.WIDTH + q * Card.WIDTH); // set the coords
			theCards[q + 9].setY(3 * ((int) Card.HEIGHT / 2));
			theCards[q + 9].flip(true); // face-down
		}
		for (int q = 0; q < 10; q++) { // fourth row
			theCards[q + 18].setX(((int) Card.WIDTH / 2) + q * Card.WIDTH); // set
																			// the
																			// coords
			theCards[q + 18].setY(2 * Card.HEIGHT);
			theCards[q + 18].flip(false); // face-up
		}
		for (int q = 28; q < 51; q++) { // the deck
			theCards[q].setX(7 * ((int) Card.WIDTH / 2)); // same coords for all
															// of them
			theCards[q].setY(13 * ((int) Card.HEIGHT / 4));
			theCards[q].flip(true); // they're all face-down
			theCards[q].setVisible(false); // they're invisible
		}
		theCards[50].setVisible(true); // only the top one is visible (faster
										// repaint)

		theCards[51].setX(13 * ((int) Card.WIDTH / 2)); // discard pile
		theCards[51].setY(13 * ((int) Card.HEIGHT / 4)); // set the coords
		theCards[51].flip(false); // face-up

		remCards = 23; // 23 cards left in the deck
		cardsInPlay = 28; // all 28 cards are in play
		remPeaks = 3; // all three peaks are there
		streak = 0; // the streak is reset
		gameScore = 0; // the game score is reset
		disIndex = 51; // the discard pile index is back to 51
		numGames++; // increment the number of games played
		sesGames++; // increment the number of session games

		repaint(); // repaint the board
		TriPeaks theFrame = (TriPeaks) SwingUtilities.windowForComponent(this); // get
																				// the
																				// frame
																				// that
																				// contains
																				// the
																				// board
		theFrame.updateStats(); // update the stats labels
	}

	public void reset() { // resets everything
		for (int q = 0; q < 52; q++) { // go through every card
			theCards[q].setVisible(false); // make all the cards invisible
		}
		disIndex = 51; // essentially the same thing as the default values for
						// the fields
		score = 0;
		gameScore = 0;
		sesScore = 0;
		streak = 0;
		remCards = 0;
		cardsInPlay = 0;
		remPeaks = 3;
		numGames = 0;
		sesGames = 0;
		highScore = 0;
		lowScore = 0;
		highStreak = 0;
		status = "";
		cheats = new boolean[cheats.length];
		hasCheatedYet = false;

		repaint(); // repaint the board
		TriPeaks theFrame = (TriPeaks) SwingUtilities.windowForComponent(this); // get
																				// the
																				// frame
		theFrame.updateStats(); // update the stats labels
	}

	public int[] randomize() { // randomizes an array - we're working with a
								// 52-element array, so it puts the numbers 0-51
								// in random order
		int[] retVal = new int[52]; // the array for the numbers
		boolean[] check = new boolean[52]; // the checking array - which numbers
											// have been used
		int[] pass; // array to pick the random item from
		ArrayList<Integer> passList; // an ArrayList of Integers for the
										// selection of possible values
		for (int q = 0; q < 52; q++) { // walk through the array, setting values
										// for each of them
			passList = new ArrayList<Integer>(); // re-initialize the arraylist
													// every time
			for (int r = 0; r < 52; r++) { // walk through the possible numbers
				if (!check[r])
					passList.add(r); // if the number hasn't been used, add it
										// to the ArrayList
			}
			pass = new int[passList.size()]; // create a new array to pass the
												// elements into another method
			int w = 0; // index
			for (Iterator<Integer> it = passList.iterator(); it.hasNext();)
				pass[w++] = it.next().intValue(); // have an iterator get every
													// element in the list and
													// put it in the array
			retVal[q] = randItem(pass); // select a random item from the array
										// and set the value
			check[retVal[q]] = true; // flag the value as used
		}
		return retVal; // return the randomized array
	}

	private int randItem(int[] list) { // selects a random item from an array
		if (list.length == 0)
			return -1; // if the array is empty, return a -1 (usually
						// unfavorable)
		int dim = list.length; // upper bound of the random integer
		int randIndex = (int) (dim * Math.random()); // generate a random index
														// for the element
		return list[randIndex]; // return the random value
	}

	public void mouseClicked(MouseEvent e) { // when the player clicks anywhere
												// on the board
		int startX, startY, endX, endY; // placeholders for the bounds of the
										// card
		for (int q = 51; q >= 0; q--) { // go through the cards in reverse order
										// - the higher index-cards are on top
			if (theCards[q] == null)
				continue; // if the card hasn't been initialized, skip it
			if (!theCards[q].isVisible())
				continue; // if the card is invisible, skip it
			if (((q < 28) || (q == 51)) && (theCards[q].isFacingDown()))
				continue; // if the card isn't part of the deck and is
							// face-down, skip it
			if (q == disIndex)
				continue; // if the card is in the discard pile, skip it
			// all the skips make execution of the mouse-click faster
			startX = theCards[q].getX() - ((int) Card.WIDTH / 2); // left edge
																	// of the
																	// card
			startY = theCards[q].getY() - ((int) Card.HEIGHT / 2); // top edge
																	// of the
																	// card
			endX = theCards[q].getX() + ((int) Card.WIDTH / 2); // right edge of
																// the card
			endY = theCards[q].getY() + ((int) Card.HEIGHT / 2); // bottom edge
																	// of the
																	// card
			if ((startX > e.getX()) || (endX < e.getX()) || (startY > e.getY())
					|| (endY < e.getY()))
				continue; // if the mouse was clicked outside the card, skip the
							// rest
			boolean isAdjacent; // a value to check if the card is adjacent by
								// value
			if (cheats[1]) { // if the second cheat is used, the value of the
								// card won't be checked
				isAdjacent = true; // the card is adjacent automatically
			} else { // no cheat - check card
				isAdjacent = theCards[q].isAdjacentTo(theCards[disIndex]); // check
																			// if
																			// the
																			// card
																			// is
																			// adjacent
																			// by
																			// value
			}
			if ((q < 28) && isAdjacent) { // if the card isn't in the deck and
											// is adjacent to the last discarded
											// card
				theCards[q].setX(theCards[disIndex].getX()); // put the card in
																// the discard
																// pile
				theCards[q].setY(theCards[disIndex].getY()); // set the discard
																// pile's card's
																// coords
				theCards[disIndex].setVisible(false); // hide the previously
														// discarded card -
														// makes the repaint
														// faster
				disIndex = q; // the card is now in the discard pile

				streak++; // increment the strea
				cardsInPlay--; // decrement the number of cards in play
				score += streak; // add the streak to the score
				gameScore += streak; // and to the current game's score
				sesScore += streak; // and to the session score
				if (streak > highStreak)
					highStreak = streak; // set the high streak if it's higher
				if (gameScore > highScore)
					highScore = gameScore; // set the high score if it's higher

				if (q < 3) { // if it was a peak
					remPeaks--; // there's one less peak
					score += 15; // add a 15-point bonus
					gameScore += 15; // and to the game score
					sesScore += 15; // and to the session score
					if (remPeaks == 0) { // if all the peaks are gone
						score += 15; // add another 15-point bonus (for a total
										// of 30 bonus points)
						gameScore += 15; // and to the game score
						sesScore += 15; // and to the session score
						status = "You have Tri-Conquered! You get a bonus of $30"; // set
																					// the
																					// status
																					// message
						for (int w = 28; w < (remCards + 28); w++) { // the
																		// remaining
																		// deck
							theCards[w].setVisible(false); // hide the deck (so
															// you can't take
															// cards from the
															// deck after you
															// clear the board
						}
					} else
						status = "You have reached a peak! You get a bonus of $15"; // set
																					// the
																					// status
																					// message

					if (gameScore > highScore)
						highScore = gameScore; // set the high score if the
												// score is higher
					break; // "consume" the mouse click - don't go through the
							// rest of the cards
				}
				boolean noLeft, noRight; // check values for checking whether or
											// not a card has a card to the left
											// or right
				noLeft = noRight = false; // starts out as having both
				if ((q != 3) && (q != 9) && (q != 18) && (q != 5) && (q != 7)
						&& (q != 12) && (q != 15)) { // if the card isn't a left
														// end
					if (!theCards[q - 1].isVisible())
						noLeft = true; // check if the left-adjacent card is
										// visible
				}
				if ((q != 4) && (q != 6) && (q != 8) && (q != 17) && (q != 27)
						&& (q != 11) && (q != 14)) { // if the card isn't a
														// right end
					if (!theCards[q + 1].isVisible())
						noRight = true; // check if the right-adjacent card is
										// visible
				}
				// some of the cards in the third row are considered to be edge
				// cards because not all pairs of adjacent cards in the third
				// row uncover another card
				if ((!noLeft) && (!noRight))
					break; // if both the left and right cards are present,
							// don't do anything
				int offset = -1; // the "offset" is the difference in the
									// indeces of the right card of the adjacent
									// pair and the card that pair will uncover
				if ((q >= 18) && (q <= 27)) { // 4th row
					offset = 10;
				} else if ((q >= 9) && (q <= 11)) { // first 3 of 3rd row
					offset = 7;
				} else if ((q >= 12) && (q <= 14)) { // second 3 of third row
					offset = 8;
				} else if ((q >= 15) && (q <= 17)) { // last 3 of third row
					offset = 9;
				} else if ((q >= 3) && (q <= 4)) { // first 2 of second row
					offset = 4;
				} else if ((q >= 5) && (q <= 6)) { // second 2 of second row
					offset = 5;
				} else if ((q >= 7) && (q <= 8)) { // last 2 of second row
					offset = 6;
				}
				// the first row isn't here because the peaks are special and
				// were already taken care of above
				if (offset == -1)
					break; // if the offset didn't get set, don't do anything
							// (offset should get set, but just in case)
				if (noLeft)
					theCards[q - offset].flip(); // if the left card is missing,
													// use the current card as
													// the right one
				if (noRight)
					theCards[q - offset + 1].flip(); // if the right card is
														// missing, use the
														// missing card as the
														// right one
			} else if ((q >= 28) && (q < 51)) { // in the deck
				theCards[q].setX(theCards[disIndex].getX()); // move the card to
																// the deck
				theCards[q].setY(theCards[disIndex].getY()); // set the deck's
																// coordinates
				theCards[disIndex].setVisible(false); // hide the previously
														// discarded card (for
														// faster repaint)
				theCards[q].flip(); // flip the deck card
				if (q != 28)
					theCards[q - 1].setVisible(true); // show the next deck card
														// if it's not the last
														// deck card
				disIndex = q; // set the index of the dicard pile
				streak = 0; // reset the streak
				if (!cheats[2]) { // if the thrid cheat isn't on (no penalty
									// cheat)
					score -= 5; // 5-point penalty
					gameScore -= 5; // to the game score
					sesScore -= 5; // and the session score
				}
				if (gameScore < lowScore)
					lowScore = gameScore; // set the low score if score is lower
				remCards--; // decrement the number of cards in the deck
			}
			break; // "consume" the click - don't go through the rest of the
					// cards
		}
		repaint(); // repaint the board
		TriPeaks theFrame = (TriPeaks) SwingUtilities.windowForComponent(this); // get
																				// the
																				// containing
																				// frame
		theFrame.updateStats(); // update the stats labels
	}

	public int getPenalty() { // return the penalty
		if (cheats[2])
			return 0; // if the penalty cheat is on, there is no penalty
		if ((cardsInPlay != 0) && (remCards != 0))
			return (cardsInPlay * 5); // if there are cards in the deck AND in
										// play, the penalty is $5 for every
										// card removed
		else
			return 0; // otherwise the penalty is 0
	}

	public void doPenalty(int penalty) { // perform the penalty - penalty
											// doesn't affect the low score
		score -= penalty; // subtract the penalty
		sesScore -= penalty; // from the session score
		gameScore -= penalty; // and from the game score
	}

	public String getCardFront() { // returns the current front style
		return frontFolder;
	}

	public String getCardBack() { // returns the current back style
		return backStyle;
	}

	public Color getBackColor() { // returns the background color
		return backColor;
	}

	public int getScore() { // returns the player's overall score
		return score;
	}

	public int getGameScore() { // returns the current game score
		return gameScore;
	}

	public int getStreak() { // returns the current sreak
		return streak;
	}

	public int getNumGames() { // returns the number of games played
		return numGames;
	}

	public int getHighScore() { // returns the high score
		return highScore;
	}

	public int getLowScore() { // returns the low score
		return lowScore;
	}

	public int getHighStreak() { // returns the longest streak
		return highStreak;
	}

	public int getSesScore() { // returns the session score
		return sesScore;
	}

	public int getSesGames() { // returns the number of session games
		return sesGames;
	}

	public Color getFontColor() {
		return fontColor;
	}

	public Font getTextFont() {
		return textFont;
	}

	public int[] getAllStats() { // returns all the stats in an array
		int[] retVal = { getScore(), getGameScore(), getSesScore(),
				getStreak(), getNumGames(), getSesGames(), getHighScore(),
				getLowScore(), getHighStreak() }; // the array of stats
		return retVal;
	}

	public boolean isCheating() { // check if the player is currently cheating
		for (int q = 0; q < cheats.length; q++) { // go through all the cheats
			if (cheats[q])
				return true; // return true if any cheat is on
		}
		return false; // no cheat was found - return false
	}

	public boolean hasCheated() { // checks if player has ever cheated
		return hasCheatedYet;
	}

	public boolean[] getCheats() { // returns all the cheats
		return cheats; // return the cheats array
	}

	public void setStats(int[] stats) { // sets all the stats based on the array
										// values
		score = stats[0]; // the programmer knows the order of the stats to be
							// passed into this method:
		highScore = stats[1]; // overall score, high score, low score, number of
								// games, and longest streak
		lowScore = stats[2];
		numGames = stats[3];
		highStreak = stats[4];
	}

	public void setCardFront(String front) { // sets the front style
		frontFolder = front;
	}

	public void setCardBack(String back) { // sets the back style
		backStyle = back;
	}

	public void setBackColor(Color newColor) { // sets the background color
		backColor = newColor;
	}

	public void setCheat(int cheatNum, boolean newState) { // set a cheat with
															// the given index
		if (cheatNum >= cheats.length)
			return; // if the index is out of bounds
		if (newState)
			hasCheatedYet = true; // if the cheat is turned on, set the
									// "has cheated" flag
		cheats[cheatNum] = newState; // set the cheat
	}

	public void setCheats(boolean[] newCheats) { // set all the cheats in a
													// given array
		for (int q = 0; q < cheats.length; q++)
			setCheat(q, newCheats[q]); // go through the array and set the
										// cheats
	}

	public void setCheated(boolean hasCheatedYet) { // set the cheated status
													// for the player.
		this.hasCheatedYet = hasCheatedYet;
	}

	public void setDefaults() {
		frontFolder = "Default";
		backStyle = "Default";
		backColor = (Color.GREEN).darker().darker();
		fontColor = Color.WHITE;
		textFont = new Font("Serif", Font.BOLD, 14);
	}

	public void setFontColor(Color newColor) {
		fontColor = newColor;
	}

	public void setTextFont(Font newFont) {
		textFont = newFont;
	}

	// not used, but necessary to implement MouseListener
	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}
} // end class CardPanel

class Card { // defines a card
	public static final int CLUBS = 0; // the 4 suits
	public static final int HEARTS = 1;
	public static final int DIAMONDS = 2;
	public static final int SPADES = 3;

	public static final int HEIGHT = 86; // the height and width of the card
	public static final int WIDTH = 64;

	private boolean isFaceDown; // is it facing down
	private boolean visible; // is it visible
	private int value; // value (0-12) - 0=Ace, 10=Jack, 11=Queen, 12=King
	private int suit; // suit of the card, as defined above
	private int xCoord; // coordinates of the card (center, not top-left)
	private int yCoord;

	public Card() {
		// must initialize manually, later on
	}

	public Card(int value, int suit, boolean isFaceDown, boolean visible,
			int x, int y) { // specify all the fields at once
		this.value = value; // set the value
		if ((suit == CLUBS) || (suit == HEARTS) || (suit == DIAMONDS)
				|| (suit == SPADES)) // check if it's a valid suit
			this.suit = suit; // set the suit
		this.isFaceDown = isFaceDown; // set the face-down flag
		this.visible = visible; // set the visible flag
		xCoord = x; // set the coordinates
		yCoord = y;
	}

	// accessor methods for the class
	public int getValue() {
		return value;
	}

	public int getSuit() {
		return suit;
	}

	public boolean isFacingDown() {
		return isFaceDown;
	}

	public int getX() {
		return xCoord;
	}

	public int getY() {
		return yCoord;
	}

	public boolean isVisible() {
		return visible;
	}

	// mutator methods
	public void setValue(int newVal) { // sets the value of the card
		if ((newVal >= 0) && (newVal < 13)) // checks if it's a valid value
			value = newVal; // set the value
	}

	public void setSuit(int newSuit) { // sets the suit
		if ((newSuit == CLUBS) || (newSuit == HEARTS) || (newSuit == DIAMONDS)
				|| (newSuit == SPADES))
			suit = newSuit;
	}

	public void flip() {
		isFaceDown = !isFaceDown;
	}

	public void flip(boolean isFaceDown) {
		this.isFaceDown = isFaceDown;
	}

	public static String suitAsString(int aSuit) { // converts the suit to a
													// string
		switch (aSuit) {
		case CLUBS:
			return "clubs";
		case HEARTS:
			return "hearts";
		case DIAMONDS:
			return "diamonds";
		case SPADES:
			return "spades";
		default:
			System.out.println("Invalid Suit!!!");
			return "Invalid Suit";
		}
	}

	public String suitAsString() { // returns the string representation of the
									// current suit
		return suitAsString(suit);
	}

	public void setX(int newX) {
		xCoord = newX;
	}

	public void setY(int newY) {
		yCoord = newY;
	}

	public void setVisible(boolean newVis) {
		visible = newVis;
	}

	public String toString() { // converts the card to a string representation
		String val;
		switch (value) {
		case 12:
			val = "king";
			break;
		case 11:
			val = "queen";
			break;
		case 10:
			val = "jack";
			break;
		default:
			val = value + "";
		}
		String finVal = val + " of " + suitAsString() + ": "
				+ ((isFaceDown) ? "facing down" : "facing up") + ", "
				+ ((visible) ? "visible" : "invisible") + " :: (" + xCoord
				+ ", " + yCoord + ")";
		return finVal;
	}

	public boolean isAdjacentTo(Card that) { // checks if the value of the card
												// is 1 off from the given card
		int tempThis = value; // this card's value
		int tempThat = that.getValue(); // the given card's value
		if (((tempThis + 1) % 13 == tempThat)
				|| ((tempThat + 1) % 13 == tempThis))
			return true; // check if it's one away
		else
			return false;
	}

	public boolean equals(Card that) { // checks if the cards are equivalent
		if ((that.getValue() == value) && (that.getSuit() == suit))
			return true;
		else
			return false;
	}
} // end class Card

class Encryptor { // a class used to encrypt and decrypt stuff
	Cipher encipher; // two cipher objects - one for encrypting and one for
						// decrypting
	Cipher decipher;
	byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
			(byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03 }; // salt for
																	// the
																	// encryption
																	// (more
																	// secure)
	int iterCt = 19; // number of iterations for encryption

	public Encryptor(String passPhrase) { // create the encryptor object
		try { // lots of things can go wrong
			KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt,
					iterCt); // create the PBE (Password-based ecryption) key
								// specification
			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
					.generateSecret(keySpec); // generate a secret encryption
												// key (Password-Based
												// Encryption with Message
												// Digest 5 and Data Encryption
												// Standard)
			encipher = Cipher.getInstance(key.getAlgorithm()); // create the
																// Cipher
																// objects
			decipher = Cipher.getInstance(key.getAlgorithm());
			AlgorithmParameterSpec pSpec = new PBEParameterSpec(salt, iterCt); // create
																				// the
																				// encryption
																				// algorithm
			decipher.init(Cipher.DECRYPT_MODE, key, pSpec); // initialize the
															// two Ciphers, with
															// the same keya and
															// algorithm, but
															// different modes
			encipher.init(Cipher.ENCRYPT_MODE, key, pSpec);
		} catch (InvalidAlgorithmParameterException eIAP) {
		} // catch all the exceptions that can get thrown.
		catch (InvalidKeySpecException eIKS) {
		} catch (NoSuchPaddingException eNSP) {
		} catch (NoSuchAlgorithmException eNSA) {
		} catch (InvalidKeyException eIK) {
		}
	}

	public String encrypt(String in) { // encrypts a stirng
		try { // lots of things that can go wrong
			byte[] utf8 = in.getBytes("UTF8"); // Convert the string to UTF-8
												// bytecodes
			byte[] enBytes = encipher.doFinal(utf8); // have the Cipher encrypt
														// the bytes
			String out = new String(Base64Coder.encode(enBytes)); // Create a
																	// string
																	// from the
																	// bytes
																	// using
																	// Base64
																	// encoding
			return out; // return the encrypted string
		} catch (BadPaddingException eBP) {
		} // catch all the exceptions
		catch (IllegalBlockSizeException eIBS) {
		} catch (UnsupportedEncodingException eUE) {
		} catch (IOException eIO) {
		}
		return null; // return null if there was an exception
	}

	public String decrypt(String in) { // decrypts a string
		try { // lots of things that can go wrong
			byte[] deBytes = Base64Coder.decode(in); // get the encrypted bytes
														// by decoding the
														// Base64-encoded text
			byte[] utf8 = decipher.doFinal(deBytes); // use the Cipher to
														// decrypt the bytes
														// into UTF-8 bytecodes
			String out = new String(utf8, "UTF8"); // create a new string from
													// those bytes
			return out; // return the decrypted string

			// catch all the exceptions
		} catch (BadPaddingException eBP) {
		} catch (IllegalBlockSizeException eIBS) {
		} catch (UnsupportedEncodingException eUE) {
		} catch (IOException eIO) {
		}
		return null; // return null if there was an exception
	}
} // end Encryptor class

/*
 * Start Base64 encoding and decoding code.**NOTE*** This is NOT my code. This
 * code was written by Christian d'Heureuse to provide a more standard base64
 * coder that's fast and efficient. As such, I won't provide comments for that
 * code. Java does NOT provide a Base64 encoder/decoder as part of the API.
 */

class Base64Coder {
	private static char[] map1 = new char[64];
	static {
		int i = 0;
		for (char c = 'A'; c <= 'Z'; c++)
			map1[i++] = c;
		for (char c = 'a'; c <= 'z'; c++)
			map1[i++] = c;
		for (char c = '0'; c <= '9'; c++)
			map1[i++] = c;
		map1[i++] = '+';
		map1[i++] = '/';
	}

	private static byte[] map2 = new byte[128];
	static {
		for (int i = 0; i < map2.length; i++)
			map2[i] = -1;
		for (int i = 0; i < 64; i++)
			map2[map1[i]] = (byte) i;
	}

	public static String encodeString(String s) {
		return new String(encode(s.getBytes()));
	}

	public static char[] encode(byte[] in) {
		return encode(in, in.length);
	}

	public static char[] encode(byte[] in, int iLen) {
		int oDataLen = (iLen * 4 + 2) / 3;
		int oLen = ((iLen + 2) / 3) * 4;
		char[] out = new char[oLen];
		int ip = 0;
		int op = 0;
		while (ip < iLen) {
			int i0 = in[ip++] & 0xff;
			int i1 = ip < iLen ? in[ip++] & 0xff : 0;
			int i2 = ip < iLen ? in[ip++] & 0xff : 0;
			int o0 = i0 >>> 2;
			int o1 = ((i0 & 3) << 4) | (i1 >>> 4);
			int o2 = ((i1 & 0xf) << 2) | (i2 >>> 6);
			int o3 = i2 & 0x3F;
			out[op++] = map1[o0];
			out[op++] = map1[o1];
			out[op] = op < oDataLen ? map1[o2] : '=';
			op++;
			out[op] = op < oDataLen ? map1[o3] : '=';
			op++;
		}
		return out;
	}

	public static String decodeString(String s) {
		return new String(decode(s));
	}

	public static byte[] decode(String s) {
		return decode(s.toCharArray());
	}

	public static byte[] decode(char[] in) {
		int iLen = in.length;
		if (iLen % 4 != 0)
			throw new IllegalArgumentException(
					"Length of Base64 encoded input string is not a multiple of 4.");
		while (iLen > 0 && in[iLen - 1] == '=')
			iLen--;
		int oLen = (iLen * 3) / 4;
		byte[] out = new byte[oLen];
		int ip = 0;
		int op = 0;
		while (ip < iLen) {
			int i0 = in[ip++];
			int i1 = in[ip++];
			int i2 = ip < iLen ? in[ip++] : 'A';
			int i3 = ip < iLen ? in[ip++] : 'A';
			if (i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127)
				throw new IllegalArgumentException(
						"Illegal character in Base64 encoded data.");
			int b0 = map2[i0];
			int b1 = map2[i1];
			int b2 = map2[i2];
			int b3 = map2[i3];
			if (b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0)
				throw new IllegalArgumentException(
						"Illegal character in Base64 encoded data.");
			int o0 = (b0 << 2) | (b1 >>> 4);
			int o1 = ((b1 & 0xf) << 4) | (b2 >>> 2);
			int o2 = ((b2 & 3) << 6) | b3;
			out[op++] = (byte) o0;
			if (op < oLen)
				out[op++] = (byte) o1;
			if (op < oLen)
				out[op++] = (byte) o2;
		}
		return out;
	}

	private Base64Coder() {
	}
} // end Base64Coder class

class NewPlayerException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NewPlayerException() {
		super();
	}

	public NewPlayerException(String msg) {
		super(msg);
	}
}

class HighScoreModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String[] columnNames = { "Player Name", "Score",
			"Average", "Most Won", "Most Lost", "Longest Streak", "# of games",
			"Has Cheated" };
	public static Object[][] defaultPlrs = new Object[10][columnNames.length];
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

	private Object[][] data;

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.length;
	}

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
		BufferedReader in;

		ArrayList<ArrayList> scoreLists = new ArrayList<ArrayList>();
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
			} catch (FileNotFoundException eFNF) { // Should never happen b/c we
													// are opening files listed
													// in a folder...
				System.out.println(eFNF.getMessage());
			} catch (IOException eIO) {
				System.out.println("Error reading from file -OR- closing file");
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
		for (Iterator<ArrayList> it1 = scoreLists.iterator(); it1.hasNext(); q++) {
			ArrayList score = it1.next();
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

class CurrencyRenderer extends DefaultTableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CurrencyRenderer() {
		super();
	}

	public void setValue(Object value) {
		if (value == null)
			setText("");
		DecimalFormat format = null;
		double num = 0.0;
		if (value.getClass() == Integer.class) {
			format = new DecimalFormat("$###,###");
			num = ((Integer) value).intValue();
		} else if (value.getClass() == Double.class) {
			format = new DecimalFormat("$###,##0.00");
			num = ((Double) value).doubleValue();
		} else {
			setText("");
			return;
		}
		if (format == null) {
			setText("");
			return;
		}
		setText(format.format(num));
	}
}
