package org.sw24softwares.starkeverben;

import java.io.BufferedReader;
import java.util.Vector;
import java.util.regex.Pattern;

class VerbsLoader extends Loader {
        Vector<Verb> mVerbs = new Vector<Verb>();
        
        public VerbsLoader () {
        }

        public boolean checkLineFormat(String line)
                throws Exception {
                // Check number of parts
                String parts[] = line.split(" ");
                if(parts.length != 5)
                        throw new Exception("Wrong number of parts in checkLineFormat() for line = " + line);

                // Check last part is number
                if(!isInteger(parts[parts.length -1]))
                        throw new Exception("Last part is not a number in checkLineFormat() for line = " + line);

                // Check four first parts contains only letters * / _ ( ) '
                for(int i = 0; i < parts.length -1; i++)
                        if(!parts[i].matches("[\\p{L}\\*\\/\\_\\)\\(\\']+"))
                                throw new Exception("Word containing non accepted characters in checkLineFormat() for line = " + line + ", word " + parts[i]);
                return true;
        }

        public Verb buildVerb(String line) {
                String parts[] = line.split(" ");
                Vector<Vector<String>> verbParts = new Vector<Vector<String>>();
                Boolean auxiliary = false;
                if(parts[0].contains("*")) {
                        auxiliary = true;
                        parts[0] = parts[0].replace("*","");
                }
                for(int i = 0; i < parts.length - 1; i++)
                        verbParts.addElement(buildPossibilities(cleanWord(parts[i])));
                int index = Integer.parseInt(parts[4]);
                return new Verb(index, verbParts, auxiliary);
        }

        public boolean load (BufferedReader reader)
                throws Exception {
                String line;
                while((line = reader.readLine()) != null) {
                        line = cleanLine(line);
                        checkLineFormat(line);
                        mVerbs.addElement(buildVerb(line));
                }
                return true;
        }

        public Vector<Verb> getVerbs() {
                return mVerbs;
        }
}
