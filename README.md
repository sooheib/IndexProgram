The goal is to implement an indexing and search JAVA program very
simple and basic for text documents. The program should cover a
directory tree of files to index. The root directory
being passed as a parameter to the program. The algorithm works as follows:
Algorithm index (root: string)
START
A glossary contains common words (empty) that should not be indexed (je, tu, il, nous, faut,
faire, de, ce, se, etc.)
The algorithm uses a direct-access file for indexes. The name of that file
of indexes is index.dat.
IF the file does not exist create it.
Read the contents of each text file and met for each
CHAQ ue significant word (not
present in the lexicon of empty words), if the word does not exist in the index.dat file
then add a new entry in index.dat whose index is the word and the content
is the name of the text file.
If the word exists then
rs is the index of the entry of the index.dat file. Search the entry into
question. If the content of this entry does not reference the current text file then
add the name of the file (the file names of an entry are separated by a separator
of your choice (comma, space, etc.)
For each sub root directory and sub directory do the same.
If root is a file then just index the file in question
END
Also write a research program that receives as a parameter
u n word and displays
the list of files containing that word. This algorithm is based on the index.dat file created
previously.
