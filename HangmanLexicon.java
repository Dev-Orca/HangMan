import acm.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;

public class HangmanLexicon {

/** array of words */
	private ArrayList<String> words = new ArrayList<String>(); 
	
/** reads file hangmanlexion.txt and gets words from it */
	public HangmanLexicon(){
		try{
			BufferedReader rd = new BufferedReader(new FileReader("HangmanLexicon.txt"));
			while(true){
				words.add(rd.readLine());
				if(rd.readLine() == null){
					break;
				}
			}
			rd.close();
		}
		catch(IOException ex){
			throw new ErrorException(ex);
		}
	}
	
/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return words.size();
	}

/** Returns the word at the specified index. */
	public String getWord(int index) {
		return words.get(index);

	}
}
