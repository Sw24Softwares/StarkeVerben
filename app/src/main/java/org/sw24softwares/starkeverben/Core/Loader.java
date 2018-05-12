package org.sw24softwares.starkeverben;

import java.util.Vector;

class Loader {
    public Loader() {
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }
    public String cleanLine(String line) {
        line = line.trim();
        return line;
    }
    public String cleanWord(String word) {
        word = word.replace("_", " ");
        return word;
    }
    public Vector<String> buildPossibilities(String line) {
        Vector<String> vec     = new Vector<String>();
        String possibilities[] = line.split("/");
        for(String p : possibilities)
            vec.addElement(p);
        return vec;
    }
}
