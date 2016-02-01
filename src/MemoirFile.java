import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class MemoirFile {

	private String fileName;
	private String file;
	public HashMap<String, String> keyWords;
	
	
	public MemoirFile() {
		fileName = "";
		file = "";
		keyWords = new HashMap<String,String>();
	}
	
	public MemoirFile(String name) {
		fileName = name;
		file = "";
		keyWords = new HashMap<String,String>();
	}
	
	public MemoirFile(String name, String keyword, String keywordDescription) {
		fileName = name;
		file = "";
		keyWords = new HashMap<String,String>();
		keyWords.put(keyword, keywordDescription);
	}
	
	public void setFileName(String n) {
		fileName = n;
	}
	
	public void setFileText(String n) {
		file = n;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public String getFile() {
		return file;
	}
	
	public HashMap getFileKeywords() {
		return keyWords;
	}
	
	public void removeKeyword(String k) {
		keyWords.remove(k);
	}
	
	public void addKeyword(String k, String d) {
		keyWords.put(k, d);
	}
	
	public void addLineToFile(String n) {
		file += n;
		file += "\n";
	}
	

}
