import java.beans.PropertyChangeSupport;
import java.util.*;


public class FractalModel {
	
	public ArrayList<MemoirFile> files = new ArrayList<MemoirFile>();
	public int currentFileNumber;
	
	boolean fractalInProgress;
	
	public FractalModel() {
		files = new ArrayList<MemoirFile>();
		MemoirFile firstFile = new MemoirFile("UNTITLED");
		files.add(firstFile);
		currentFileNumber = 0;
	}
	
	public void newEntry(String n) {
		MemoirFile newEntry = new MemoirFile(n);
		files.add(newEntry);
		currentFileNumber = files.size() - 1;
	}
	
	public void newKeywordEntry(String name, String keyword, String keywordDescrip) {
		MemoirFile newEntry = new MemoirFile(name, keyword, keywordDescrip);
		files.add(newEntry);
		currentFileNumber = files.size() - 1;
	}
	
	public void setCurrentEntry(int n) {
		currentFileNumber = n;
	}
	
	public Vector<String> grabFileNames() {
		MemoirFile currentFile = new MemoirFile();
		Vector<String> fileNames = new Vector<String>();
		
		for (int i = 0; i < files.size(); i++) {
			currentFile = files.get(i);
			fileNames.add(currentFile.getFileName());
		}
		
		return fileNames;
	}
	
	
	public HashMap getEntryKeywordMap() {
		MemoirFile key = new MemoirFile();
		HashMap<String, String> keyWords = new HashMap<String, String>();
		key = files.get(currentFileNumber);
		keyWords = key.getFileKeywords();
		return keyWords;
	}
	
	public void removeEntryKeyword(String n) {
		MemoirFile key = new MemoirFile();
		key = files.get(currentFileNumber);
		key.removeKeyword(n);	
	}
	
	public void addEntryKeyword(String k, String d) {
		MemoirFile key = new MemoirFile();
		key = files.get(currentFileNumber);
		key.addKeyword(k, d);
	}
	
	public void saveCurrentText(String title, String text) {
		MemoirFile t = new MemoirFile();
		t = files.get(currentFileNumber);
		t.setFileName(title);
		t.setFileText(text);
		
	}

	public String getCurrentFileName() {
		MemoirFile t = new MemoirFile();
		t = files.get(currentFileNumber);
		return t.getFileName();
	}
	
	public String getCurrentFileText() {
		MemoirFile t = new MemoirFile();
		t = files.get(currentFileNumber);
		return t.getFile();
	}
	
	public void clearAllData() {
		files.clear();
		currentFileNumber = 0;
	}
	
	public void addMemoir(MemoirFile m) {
		files.add(m);
	}

	

}
