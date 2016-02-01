
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FractalController {
	
	private FractalModel f_model;
	private FractalView f_view;

	FractalController(FractalModel m, FractalView v) {
		
		f_model = m;
		f_view = v;
		
		v.addDeleteKeywordListener(new DeleteKeyword());
		v.addAddKeywordListener(new AddKeyword());
		v.addKeywordListListener(new KeywordListListener());
		v.addSaveButtonListener(new Save());
		v.addNextButtonListener(new newEntry());
		v.addNewKeyWordEntryListener(new newKeywordEntry());
		v.addMoveFileListener(new MoveFile());
		
		v.addSaveAsItemListener(new SaveAsListener(f_model, f_view));
		v.addOpenFileListener(new OpenListener(f_model, f_view));	
	}
	
	private class KeywordListListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			String keywordSelection = (String)f_view.keywordList.getSelectedValue();
			//look up description in map
			String descrip = f_view.keywordMap.get(keywordSelection);
			f_view.keywordDescrip.setText(descrip);
			
		}
	}
	
	class DeleteKeyword implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String deleteKey = (String)f_view.keywordList.getSelectedValue();
			//remove from data
			f_model.removeEntryKeyword(deleteKey);
			//update everything
			f_model.saveCurrentText(f_view.getCurrentTitle(), f_view.getCurrentText());
			updateGUI();
		}
	}
	
	class AddKeyword implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String newKeyword;
			//first save the title and text to current file
			f_model.saveCurrentText(f_view.getCurrentTitle(), f_view.getCurrentText());
			//grab the highlighted word
			String highWord = f_view.entryTextArea.getSelectedText();
			//if the highlighted word is not null, use that as word; else ask for it
			if (highWord != null) {
				newKeyword = highWord;
			} else {
				newKeyword = JOptionPane.showInputDialog("Please enter a new keyword.");
			}
			String newDescrip = JOptionPane.showInputDialog("Please describe how this entry relates to your new keyword, " + newKeyword);
			if (newKeyword.length() < 1 ) {
				JOptionPane.showMessageDialog(null, "You entered a blank for your keyword.");
			} else if (newDescrip.length() < 1 || newDescrip == null) {
				JOptionPane.showMessageDialog(null, "You entered a blank in the description.");
			} else {
				//add to data
				f_model.addEntryKeyword(newKeyword.trim(), newDescrip.trim());
				//update all
				f_model.saveCurrentText(f_view.getCurrentTitle().trim(), f_view.getCurrentText());
				updateGUI();
			}	
		}
	}
	
	class Save implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//save the title and text to current file
			f_model.saveCurrentText(f_view.getCurrentTitle().trim(), f_view.getCurrentText());
			//update all due to possible filename changes
			updateGUI();
		}
	}
	
	class newEntry implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//first save the title and text to current file
			f_model.saveCurrentText(f_view.getCurrentTitle().trim(), f_view.getCurrentText());
			//throw dialog box to get new name
			String newEntryName = JOptionPane.showInputDialog("Please enter a name for the new entry.");
			if (newEntryName.length() < 1 || newEntryName == null) {
				JOptionPane.showMessageDialog(null, "You entered a blank.");
			} else {
				//create a new entry, then move to it
				f_model.newEntry(newEntryName.trim());
				//update all
				updateGUI();
			}
		}
	}
	
	class newKeywordEntry implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//first save the title and text to current file
			f_model.saveCurrentText(f_view.getCurrentTitle(), f_view.getCurrentText());
			//grab the highlighter word
			String highWord = f_view.entryTextArea.getSelectedText();
			//if not NULL, throw dialog box to get new name and how it links
			if (highWord != null) {
				String newEntryName = JOptionPane.showInputDialog("Please enter a name for the new entry for the keyword, " + highWord);
				String newKeyLink = JOptionPane.showInputDialog("And how will this new entry relate to the keyword, " + highWord);				
				//if neither are blank, create a new entry, then move to it
				if (newEntryName.length() < 1 || newKeyLink.length() < 1) {
					JOptionPane.showMessageDialog(null, "You entered a blank for either the keyword or its description.");
				} else {
					f_model.newKeywordEntry(newEntryName.trim(), highWord.trim(), newKeyLink);
				}
				//update all
				updateGUI();
			} else {
				JOptionPane.showMessageDialog(null, "You didn't highlight a word to transform into a keyword.");
			}
			
		}
	}
	
	class MoveFile implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//first save everything
			f_model.saveCurrentText(f_view.getCurrentTitle(), f_view.getCurrentText());
			//set the current file to the selection
			//NOTE - this depends on the index of the JList matching the index of the Object array
			int entrySelection = f_view.entryList.getSelectedIndex();
			f_model.setCurrentEntry(entrySelection);
			//reset all
			updateGUI();
		}
	}
	
	//function to update everything in the GUI (used alot)
		public void updateGUI() {
			//update the keyword map 
			f_view.keywordMap = f_model.getEntryKeywordMap();
			//update the keyword JList (decided to do it here)
			Vector<String> tempKeywordArray = new Vector<String>();
			for (Map.Entry<String, String> entry : f_view.keywordMap.entrySet()) {
				tempKeywordArray.add(entry.getKey());
			}
			f_view.keywordList.setListData(tempKeywordArray);
			//update text section
			f_view.filenameTextField.setText(f_model.getCurrentFileName());
			f_view.entryTextArea.setText(f_model.getCurrentFileText());
			//update file section
			f_view.entryList.setListData(f_model.grabFileNames());
			}

	

	
	

}
