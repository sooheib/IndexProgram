    /*
     * @author: Selmi Souheib
     * @groupe: 2éme LA SIL
     */
package indexer;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;
        
public class IndexER {
    
    public static void main(String[] args) {
        my_menu();
    }

    //my_menu(): affichage des modes d'utilisation du programme.
    public static void my_menu() {
	while(true){
            System.out.println("1: IndexER\n2: Affichier les mots indexer\n3: exit");
            try {
                Scanner read_choix = new Scanner(System.in);
                int choix = read_choix.nextInt();
                            
                switch (choix) {
                    case 1:
                        System.out.println("nom de repertoire a IndexER:");
                        Scanner read_rep = new Scanner(System.in);
                        String nom_rep = read_rep.nextLine();
                        long start_time = System.currentTimeMillis();
                        File dat_file = new File("index.dat");
                        ArrayList<Index> index_dat;
                        index_dat = new ArrayList<>(lire_dat(dat_file));
                        indexer(nom_rep,index_dat);
                        ecrire_dat(index_dat, dat_file);
                        System.out.println("indexation términer dans "+(System.currentTimeMillis()-start_time)+"ms");
                        break;
                    case 2:
                        afficher();
                        break;
                    case 3:
                        System.exit(0);
                        break;
                    default: 
                        System.out.println("votre choix doit etre (1, 2 ou 3)");
                        break;
                }	
            }catch(InputMismatchException e){
		System.out.println("votre choix doit etre (1, 2 ou 3)");
            }
	}
    }

    /*indexer(String, ArrayList<Index>):
    * parcourir l'arboréssance
    * verifier si un mot existe dans un fichier:
    *   si le mot existe: verifier si le nom de fichier existe comme index on l'ajoute sinon
    *   si le mot n'existe pas, on l'ajoute au fichier d'index
    */ 
    public static void indexer(String nom_rep, ArrayList<Index> index_dat) {
	BufferedReader lire_txt;
	File rep = new File(nom_rep);
	if (!rep.isDirectory()) {
            System.out.println(rep.getName()+" n'est pas un repertoire");
            //System.exit(0);
	}else {
            for(File f_name : rep.listFiles()) {
		String ext = f_name.getName().substring(f_name.getName().lastIndexOf(".") + 1, f_name.getName().length());
		if((f_name.isFile())&&(ext.equalsIgnoreCase("txt"))) {
                    try {
			lire_txt = new BufferedReader(new FileReader(f_name));
			while (lire_txt.ready()) {
                            String[] line_word = lire_txt.readLine().split(" ");
                            for (int i = 0; i < line_word.length; i++) {
				int j = word_exsits(line_word[i], index_dat);
				if (j>=0) {
                                    if(!index_dat.get(j).getIndex().contains(f_name.getName())==true) {
					index_dat.get(j).addIndex(f_name.getName());
                                    }
				} else {
                                    Index index = new Index(line_word[i],f_name.getName());
                                    index_dat.add(index);
				}
                            }
			}
			lire_txt.close();
                    } catch (IOException e) {
			System.out.print("Erreur de lecture!");
			System.exit(0);
                    }
		} else if(f_name.isDirectory()) {
                    indexer(f_name.getPath(),index_dat);
                }
            }
        }
    }

    //afficher(): affichage du fichier index.dat
    public static void afficher() {
	File dat_file = new File("index.dat");
	ArrayList<Index> indexed_list = new ArrayList<>(lire_dat(dat_file));
	for (int i = 0; i < indexed_list.size(); i++) {
            indexed_list.get(i).display();
	}
    }

    /* ArrayList<Index> lire_dat(File): lire index.dat dans un variable
    * local du programme afin d'etre utiliser dans indexer(String,ArrayList)
    * ou dans afficher()
    */
    public static ArrayList<Index> lire_dat(File dat_file) {
        ArrayList<Index> index_dat = new ArrayList<>();
	ObjectInputStream lecture;
	try {
            lecture = new ObjectInputStream(new BufferedInputStream(new FileInputStream(dat_file)));
            try {	
		while(true) {
                    try {
			index_dat.add((Index)lecture.readObject());
                    } catch (ClassNotFoundException e) {
			System.out.println("Could Not Convert Bytes to an 'Index' object");
                    }
		}
            } catch(EOFException e) {
                lecture.close();
            }
        } catch (IOException e) {
            System.out.println(dat_file.getName()+" Not found...Creating file ");
            ecrire_dat(index_dat, dat_file);
            return lire_dat(dat_file);
        }
        return index_dat;
    }

    /* ecrire_dat(ArrayList<Index>, File): ecrire la liste des mots indexer par indexer(String,ArrayList)
    * dans index.dat
    */
    public static void ecrire_dat(ArrayList<Index> indexed, File dat_file) {
        ObjectOutputStream ecriture;
	try {
            ecriture = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(dat_file)));
            if(indexed.size()!=0) {
		for (int i = 0; i < indexed.size(); i++) {
                    ecriture.writeObject(indexed.get(i));
		}
            }else {
		System.out.println("Initializing...");
		Index obj = new Index("Selmi","Souheib");
		ecriture.writeObject(obj);
            }
            ecriture.close();
	} catch (IOException e) {
            System.out.println("Erreur d'ouverture en mode ecriture");
	}
    }

    /* word_exists(String,ArrayList): retourne -1 si le mot n'existe pas dans index.dat
    *                                 retourne l'emplacement si le mot existe dans index.dat
    */
    public static int word_exsits(String str, ArrayList<Index> indexed_dat) {
	for(int i = 0; i < indexed_dat.size(); i++ ) {
            if (str.equalsIgnoreCase(indexed_dat.get(i).getWord())) {
		return i;
            }
	}
	return -1;
    }
}
