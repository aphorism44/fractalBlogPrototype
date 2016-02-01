
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class FractalView extends JFrame {
	
	
	private FractalModel model;
	public HashMap<String,String> keywordMap;
	
	//the panels
	private JPanel textBoxPanel;
	private JPanel keywordPanel;
	private JPanel entryListPanel;
	
	//components
	//textarea
	public JTextArea entryTextArea;
	public JTextField filenameTextField;
	private JButton newEntryButton;
	private JButton newEntryByKeyword;
	private JButton saveButton;
	
	//keyword
	private JLabel keywordLabel;
	public JList keywordList;
	private JLabel keyDescripLabel;
	public JTextArea keywordDescrip;
	private JLabel highlightLabel;
	private JTextField highlightedTextField;
	private JButton addKeywordButton;
	private JButton removeKeywordButton;
	
	//entries
	private JLabel entryListLabel;
	public JList entryList;
	private JButton entryButton;
	
	//the menu bar
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem saveAsItem;
	private JMenuItem exitItem;
	private JMenuItem openItem;
	
	
	public FractalView(FractalModel model) {
		
		
		setTitle("The Skeleton for Life Fractal Writer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		
		//on initialization, should be nothing in keyword map
		keywordMap = new HashMap<String,String>();
		
		//the keyword panel
		keywordPanel = new JPanel();
		keywordPanel.setLayout(new BoxLayout(keywordPanel, BoxLayout.Y_AXIS));
				
		keywordLabel = new JLabel("Keywords");
		keywordPanel.add(keywordLabel);
		
		
		//on initialization, there should be no keywords		
		keywordList = new JList();
		keywordList.setFixedCellWidth(100);
		keywordList.setVisibleRowCount(10);
		keywordList.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		JScrollPane keywordListScroll = new JScrollPane(keywordList);
		keywordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//add the listener via the controller below
		//keywordList.addListSelectionListener(new KeywordListListener());
		keywordPanel.add(keywordListScroll);
		
		keyDescripLabel  = new JLabel("Keyword Description");
		keywordPanel.add(keyDescripLabel);
		
		keywordDescrip = new JTextArea(10, 10);
		keywordDescrip.setLineWrap(true);
		keywordDescrip.setWrapStyleWord(true);
		keywordPanel.add(keywordDescrip);
		
		//highlightLabel = new JLabel("Currently Highlighted Word");
		//keywordPanel.add(highlightLabel);
		
		//highlightedTextField = new JTextField(25);
		//highlightedTextField.setEditable(false);
		//keywordPanel.add(highlightedTextField);
		
		addKeywordButton = new JButton("Add Keyword");
		keywordPanel.add(addKeywordButton);
		
		removeKeywordButton = new JButton("Delete Keyword");
		keywordPanel.add(removeKeywordButton);
				
		add(keywordPanel);
		
		
		//the textbox panel
		textBoxPanel = new JPanel();
		textBoxPanel.setLayout(new BoxLayout(textBoxPanel, BoxLayout.Y_AXIS));
		
		filenameTextField = new JTextField(25);
		//on creation, would be populated only with the default first file name
		filenameTextField.setText(model.getCurrentFileName());
		filenameTextField.setEditable(true);
		textBoxPanel.add(filenameTextField);
		
		
		entryTextArea = new JTextArea(20, 30);
		entryTextArea.setLineWrap(true);
		entryTextArea.setWrapStyleWord(true);
		entryTextArea.setEditable(true);
		entryTextArea.setBorder(BorderFactory.createEtchedBorder());
		JScrollPane textScroll = new JScrollPane(entryTextArea);
		textBoxPanel.add(textScroll);
		
		newEntryButton = new JButton("New Blank Entry");
		textBoxPanel.add(newEntryButton);	
		
		newEntryByKeyword = new JButton("New Keyword Entry");
		textBoxPanel.add(newEntryByKeyword);
		
		saveButton = new JButton("Save");
		saveButton.setMnemonic(KeyEvent.VK_S);
		textBoxPanel.add(saveButton);
		
		add(textBoxPanel);
		
		
		//the entrylist panel
		entryListPanel = new JPanel();
		entryListPanel.setLayout(new BoxLayout(entryListPanel, BoxLayout.Y_AXIS));
			
		entryListLabel = new JLabel("Current Entries");
		entryListPanel.add(entryListLabel);
		
		//when initialized, should be only a single file name
		entryList = new JList(model.grabFileNames());
		entryList.setFixedCellWidth(100);
		entryList.setVisibleRowCount(20);
		entryList.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		entryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane entryListScroll = new JScrollPane(entryList);
		//no listener for now
		entryListPanel.add(entryListScroll);
	
		entryButton = new JButton("Go To Entry");
		entryListPanel.add(entryButton);
		
		add(entryListPanel);
		

		buildMenuBar();
		pack();
		setSize(1000,550);
		
		setVisible(true);
		
	}


	
	private void buildMenuBar() {
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("File");
		
		buildFileMenu();
		
		menuBar.add(fileMenu);
		
		setJMenuBar(menuBar);
		
	}
	
	private void buildFileMenu() {
			
		saveAsItem = new JMenuItem("Save As");
		saveAsItem.setMnemonic(KeyEvent.VK_A);
			
		openItem = new JMenuItem("Open");
		openItem.setMnemonic(KeyEvent.VK_O);
		
		exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.addActionListener(new ExitListener());
		
		fileMenu.add(saveAsItem);
		fileMenu.add(openItem);
		fileMenu.add(exitItem);
	}

	
	
	
	
	//listeners for menubar
	
	void addSaveAsItemListener(ActionListener saveAs) {
		saveAsItem.addActionListener(saveAs);
	}
	
	void addOpenFileListener(ActionListener open) {
		openItem.addActionListener(open);
	}
	
	
	
	//keyword section functions
	
	//keyword ListSelectionListener
	
	
	void addKeywordListListener(ListSelectionListener keyword) {
		keywordList.addListSelectionListener(keyword);
	}
	
	
	void addDeleteKeywordListener(ActionListener delKey) {
		removeKeywordButton.addActionListener(delKey);
	}
	
	void addAddKeywordListener(ActionListener addKey) {
		addKeywordButton.addActionListener(addKey);
	}
	
	//text area  functions

	void addSaveButtonListener(ActionListener saveText) {
		saveButton.addActionListener(saveText);
	}
	
	void addNextButtonListener(ActionListener newEntry) {
		newEntryButton.addActionListener(newEntry);
	}
	
	void addNewKeyWordEntryListener(ActionListener keyEntry) {
		newEntryByKeyword.addActionListener(keyEntry);
	}
	
	public String getCurrentTitle() {
		return filenameTextField.getText();
	}
	
	public String getCurrentText() {
		return entryTextArea.getText();
	}
	
	
	//file name area
	
	void addMoveFileListener(ActionListener moveFile) {
		entryButton.addActionListener(moveFile);
	}
	
	
	
	
	
	
	

}
