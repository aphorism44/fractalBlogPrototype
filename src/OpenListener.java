import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;


public class OpenListener implements ActionListener {
	
	FractalModel f_model;
	FractalView f_view;
	
	String openedTextLine;
	
	int status;
	

	public OpenListener(FractalModel m, FractalView v) {
		f_model = m;
		f_view = v;
    }

public void actionPerformed(ActionEvent e) {
		
	
		JFileChooser openBox = new JFileChooser();
		
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
		openBox.setFileFilter(filter);
		openBox.setAcceptAllFileFilterUsed(false);
		
		
		status = openBox.showOpenDialog(null);
			
		
		
		//the user is saving
		if (status == JFileChooser.APPROVE_OPTION) {
			
			
			File selectedOpenFile = openBox.getSelectedFile();
			
			Scanner scanIn = null;
			try {
				scanIn = new Scanner(selectedOpenFile);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
			//clear all data
			f_model.clearAllData();
			
			MemoirFile inFile = new MemoirFile();
			
			while (scanIn.hasNext() ) {
				
				String tempKeyword;
				
				openedTextLine = scanIn.nextLine();
				
				if (openedTextLine.equals("#entry")) {
					openedTextLine = scanIn.nextLine();
					inFile.setFileName(openedTextLine);
				}
				else if (openedTextLine.equals("#keyword")) {
					openedTextLine = scanIn.nextLine();
					tempKeyword = openedTextLine;
					openedTextLine = scanIn.nextLine();
					inFile.addKeyword(tempKeyword, openedTextLine);
				}
				else if (openedTextLine.equals("#text")) {
					while (!openedTextLine.equals("#end")) {
						openedTextLine = scanIn.nextLine();	
						if (!openedTextLine.equals("#end")) {
							inFile.addLineToFile(openedTextLine);
						}
					}	
					f_model.addMemoir(inFile);
					inFile = new MemoirFile();
				}
				/*else if (openedTextLine.equals("#end")) {
					f_model.addMemoir(inFile);
					inFile = new MemoirFile();
					
				}*/
				
			}
			
			//reset data in GUI
			//reset all the fields when new data comes in
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


/*
 * The format of the text file should be like this:
 * #beginEntry
 * fileName
 * #keywords (only appears if there's keywords)
 * keyword1
 * keywordDescrip1
 * keyword2
 * keywordDesctip2
 * ...
 * #text
 * line1
 * line2
 * #endEntry
 * #beginEntry
 * */


}
