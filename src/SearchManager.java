import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class SearchManager {
	
	private static final String DICT_PATH = "pro-ling-dictionary.txt";
	private ArrayList<String> words;
	private KeyboardPanel panel;
	
	public SearchManager(KeyboardPanel panel) {
		this.panel = panel;
		words = new ArrayList<String>();
		loadWordsFromFile();
	}
	
	public void loadWordsFromFile() {
		CompletableFuture<Void> cfLoad = CompletableFuture.runAsync(
			new Runnable() {
				@Override
				public void run() {
					panel.setReady(false);
					panel.repaint();
					try {
						var br = new BufferedReader(new FileReader(new File(DICT_PATH)));
						var str = "";
						while ((str = br.readLine()) != null) {
							words.add(str);
						}
						try {
							Thread.sleep(1200);
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
}
