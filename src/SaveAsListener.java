import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SaveAsListener  implements ActionListener  {

	
	private FractalModel f_model;
	private FractalView f_view;
	
	private String saveText;
	
	private JFileChooser saveBox;
	private int status;
	private String saveFileName;
	private String ext = ".txt";
	private PrintWriter outputSaveFile;
	
	
	public SaveAsListener(FractalModel m, FractalView v) {
    	f_model = m;
    	f_view = v;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		//to be safe, first save everything
		f_model.saveCurrentText(f_view.getCurrentTitle(), f_view.getCurrentText());
		
    	
		JFileChooser saveBox = new JFileChooser();
		
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
		saveBox.setFileFilter(filter);
		saveBox.setAcceptAllFileFilterUsed(false);
		
		
		status = saveBox.showSaveDialog(null);
			
		
		
		//the user is saving
		if (status == JFileChooser.APPROVE_OPTION) {
			
			File selectedSaveFile = saveBox.getSelectedFile();
			saveFileName = selectedSaveFile.getPath() + ext;

			outputSaveFile = null;
			try {
				outputSaveFile = new PrintWriter(saveFileName);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
			if (f_model.files.size() > 0) { //if there's existing data
			//now start looping throgh the model
				for (int i = 0; i < f_model.files.size(); i++) {
					MemoirFile saveTemp = f_model.files.get(i);
					HashMap<String, String> tempKeys = new HashMap<String,String>();
					tempKeys = saveTemp.getFileKeywords();
					outputSaveFile.println("#entry");
					outputSaveFile.println(saveTemp.getFileName());
					if (tempKeys.size() > 0) { //only if there are keywords for this entry
						for (Map.Entry<String, String> entry : tempKeys.entrySet()) {
							outputSaveFile.println("#keyword");
							outputSaveFile.println(entry.getKey());
							outputSaveFile.println(entry.getValue());
						}
					}
					//need to preserve the line breaks in the text area
					String ln = System.getProperty("line.separator");
					String tempText = saveTemp.getFile();
					String moddedText = tempText.replaceAll("\n", ln);
					outputSaveFile.println("#text");
					outputSaveFile.write(moddedText.toString(),0, moddedText.length());
					outputSaveFile.println();
					outputSaveFile.println("#end");
				}
			}
			
			outputSaveFile.close();
    }
	


		
		/*
		 * The format of the text file should be like this:
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
		 * fileName2
		 * ...
		 * */

		
	}

}
