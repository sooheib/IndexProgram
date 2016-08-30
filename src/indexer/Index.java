    /*
     * @author: Selmi Souheib
     * @groupe: 2éme LA SIL
     */
package indexer;
import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
//Index: class pour enregistrer un mot et ces index
public class Index implements Serializable {
    
    private String word; //mot
    private ArrayList<String> index_list; //liste des fichiers
	
    public Index() {
	word = new String();
	index_list = new ArrayList<>();
    }
	
    public Index(String word, String init) {
	this.word = word;
	index_list = new ArrayList<>();
	index_list.add(init);
    }
	
    public void setWord(String word) {
	this.word = word;
    }
	
    public void addIndex(String index) {
	this.index_list.add(index);
    }
	
    public String getWord() {
	return word;
    }
	
    public ArrayList<String> getIndex() {
	return index_list;
    }
	
    public void display(){
	System.out.println("===== word: "+word);
	System.out.print("|-> index list: ");
	for (int i = 0; i < index_list.size(); i++) {
            System.out.print(index_list.get(i)+", ");
        }
	System.out.println();
    }
}