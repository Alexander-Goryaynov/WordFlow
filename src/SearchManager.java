import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class SearchManager {
	
	private static final String DICT_PATH = "frequency-dictionary.txt";
	private String[] words;
	private KeyboardPanel panel;
	
	public SearchManager(KeyboardPanel panel) {
		this.panel = panel;
		loadWordsFromFile();
	}
	
	public void loadWordsFromFile() {
		@SuppressWarnings("unused")
		CompletableFuture<Void> cfLoad = CompletableFuture.runAsync(
			new Runnable() {
				@Override
				public void run() {
					panel.setReady(false);
					panel.repaint();
					try {
						var br = new BufferedReader(new FileReader(new File(DICT_PATH)));
						var linesCount = (int)br.lines().count();
						br.close();
						words = new String[linesCount];
						br = new BufferedReader(new FileReader(new File(DICT_PATH)));
						var str = "";
						var i = 0;
						while ((str = br.readLine()) != null) {
							words[i] = str;
							i++;
						}
						br.close();
						try {
							Thread.sleep(1500);
						} catch (InterruptedException e) {}
						panel.setReady(true);
						panel.repaint();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		);
	}
	
	public void addNewWordToFile(String word) throws Exception {
		checkNewWord(word);
		try {
			File sourceFile = new File(DICT_PATH);
			File destFile = new File(DICT_PATH + "1");
			var br = new BufferedReader(new FileReader(sourceFile));
			var bw = new BufferedWriter(new FileWriter(destFile));
			String str = null;
			while ((str = br.readLine()) != null) {
				bw.write(str);
				bw.newLine();
			}
			bw.write(word);
			br.close();
			bw.close();
			sourceFile.delete();
			destFile.renameTo(sourceFile);
			loadWordsFromFile();
		} catch (Exception e) {
			throw new Exception("Ошибка записи в файл");
		}
	}
	
	public void checkNewWord(String word) throws Exception {
		var isWrong = word.matches(
				"(.*)(\\d)(.*)|(.*)(\\s)(.*)|(.*)([a-z])(.*)|(.*)(\\p{P})(.*)"
		);
		for (var w : words) {
			if (w.equals(word)) {
				throw new Exception("Это слово уже есть в словаре");
			}
		}
		if (isWrong) {
			throw new Exception("Слово должно состоять только из русских букв");
		}
	}
	
	public void clearHistory() throws Exception {
		try {
			File sourceFile = new File(DICT_PATH);
			File destFile = new File(DICT_PATH + "1");
			var br = new BufferedReader(new FileReader(sourceFile));
			var bw = new BufferedWriter(new FileWriter(destFile));
			String str = null;
			while ((str = br.readLine()) != null) {
				bw.write(str);
				if (str.equals("-----")) {
					break;
				}
				bw.newLine();
			}
			br.close();
			bw.close();
			sourceFile.delete();
			destFile.renameTo(sourceFile);
			loadWordsFromFile();
		} catch (Exception e) {
			throw new Exception("Ошибка при работе с файлом");
		}
	}
	
	public ArrayList<String> searchByPattern(String pattern) {
		var result = new ArrayList<String>(3);
		var wordEqual = searchEqualWord(pattern);
		if (wordEqual != null) {
			result.add(wordEqual);
		}
		var foundEdge = searchEdgeLettersAndLength(pattern);
		if (foundEdge.size() + result.size() <= 3) {
			result.addAll(foundEdge);
			return result;
		}
		var foundByPresence = searchByLettersPresence(pattern, foundEdge);
		if (foundByPresence.size() + result.size() <= 3) {
			result.addAll(foundByPresence);
			return result;
		}
		return searchByLettersSequence(pattern, foundByPresence);
	}
	
	private String searchEqualWord(String pattern) {
		String result = null;
		var foundWord = Arrays.stream(words).filter(w -> w.equals(pattern)).limit(1).findFirst();
		if (foundWord.isPresent()) {
			result = foundWord.get();
		}
		return result;
	}
	
	private ArrayList<String> searchEdgeLettersAndLength(String pattern) {
		var patChars = pattern.toCharArray();
		var result = new ArrayList<String>();
		Arrays.stream(words)
				.filter(w ->
				w.length() <= patChars.length &&
				w.charAt(0) == patChars[0] &&
				w.charAt(w.length() - 1) == patChars[patChars.length - 1])
				.forEach(w -> result.add(w));
		return result;
	}
	
	private ArrayList<String> searchByLettersPresence(String pattern, ArrayList<String> input) {
		var rates = new ArrayList<WordRateInfo>();
		var patChars = pattern.toCharArray();
		for (var word : input) {
			var wordChars = word.toCharArray();
			var rating = 0;
			for (var wordChar : wordChars) {
				if (containsLetter(patChars, wordChar)) {
					rating++;
				}
			}
			rates.add(new WordRateInfo(word, rating));
		}
		// Sort by rating descending.
		Collections.sort(rates, 
				(x, y) -> y.getRating() - x.getRating());
		ArrayList<String> result = new ArrayList<>();
		for (var r : rates) {
			result.add(r.getWord());
		}
		return result;
	}
	
	private boolean containsLetter(char[] arr, char letter) {
		for (var character : arr) {
			if (character == letter) {
				return true;
			}
		}
		return false;
	}
	
	private ArrayList<String> searchByLettersSequence(String pattern, ArrayList<String> input) {
		var rates = new ArrayList<WordRateInfo>();
		for (var word : input) {
			rates.add(new WordRateInfo(word, getSequenceRating(word, pattern)));
		}
		// Sort by rating descending.
		Collections.sort(rates, 
				(x, y) -> y.getRating() - x.getRating());
		ArrayList<String> result = new ArrayList<>();
		for (var r : rates) {
			result.add(r.getWord());
		}
		return result;
	}
	
	private int getSequenceRating(String word, String pattern) {
		var wordChars = word.toCharArray();
		var rating = 0;
		var index = 0;
		for (int i = 1; i < wordChars.length - 1; i++) {		
			// Try to search letter after previous letter.
			var curIndex = pattern.indexOf(wordChars[i], index);
			// If smth found, increase word rating.
			if (curIndex != -1) {
				rating++;
			} else {
				// Find any letter entry in the string.
				// Always finds smth due to the previous "LettersPresence" method.
				curIndex = pattern.indexOf(wordChars[i], index);
			}
			// Remember index anyway.
			index = curIndex;			
		}
		return rating;
	}
}
