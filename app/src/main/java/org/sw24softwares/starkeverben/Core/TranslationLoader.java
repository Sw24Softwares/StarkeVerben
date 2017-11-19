package org.sw24softwares.starkeverben;

import java.io.BufferedReader;
import java.util.Vector;
import java.util.regex.Pattern;

class TranslationLoader extends Loader {
        Vector<Translation> mTranslations = new Vector<Translation>();
        
        public TranslationLoader () {
        }

        public boolean checkLineFormat(String line)
                throws Exception {
                // Check number of parts
                String parts[] = line.split(" ");
                if(parts.length != 2)
                        throw new Exception("Wrong number of parts in checkLineFormat() for line = " + line);

                // Check last part is number
                if(!isInteger(parts[0]))
                        throw new Exception("Last part is not a number in checkLineFormat() for line = " + line);

                // Check four first parts contains only letters * / _ ( ) '
                if(!parts[1].matches("[-\\p{L}\\*\\/\\_\\)\\(\\']+"))
                        throw new Exception("Word containing non accepted characters in checkLineFormat() for line = " + line + ", word " + parts[1]);
                return true;
        }
        public Translation buildTranslation(String line) {
                String parts[] = line.split(" ");
                return new Translation(Integer.parseInt(parts[0]),buildPossibilities(cleanWord(parts[1])));
        }
        public boolean load (BufferedReader reader)
                throws Exception {
                String line;
                while((line = reader.readLine()) != null) {
                        line = cleanLine(line);
                        checkLineFormat(line);
                        mTranslations.addElement(buildTranslation(line));
                }
                return true;
        }

        public Vector<Translation> getTranslations() {
                return mTranslations;
        }
}
