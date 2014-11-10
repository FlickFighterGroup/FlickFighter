package jp.ac.it_college.std.flickfighter;

import java.util.Random;

class WordBook {
    public WordBook(){

    }
    public static String[][] wordBook(){
        String[][] word = new String[10][];
        word[0] = new String[]{"あうあう","あいあい","あい","あああ","いえ","いう","おいえ","おういえ","おい","あお","ええ","えい","ういお","おう","えいえいおー"};
        word[1] = new String[]{"a1","a2","a3","a4","a5","a6","a7","a8","a9","a0","a11","a12","a13","a14","a15"};
        word[2] = new String[]{"a1","a2","a3","a4","a5","a6","a7","a8","a9","a0","a11","a12","a13","a14","a15"};
        word[3] = new String[]{"a1","a2","a3","a4","a5","a6","a7","a8","a9","a0","a11","a12","a13","a14","a15"};
        word[4] = new String[]{"a1","a2","a3","a4","a5","a6","a7","a8","a9","a0","a11","a12","a13","a14","a15"};
        word[5] = new String[]{"a1","a2","a3","a4","a5","a6","a7","a8","a9","a0","a11","a12","a13","a14","a15"};
        word[6] = new String[]{"a1","a2","a3","a4","a5","a6","a7","a8","a9","a0","a11","a12","a13","a14","a15"};
        word[7] = new String[]{"a1","a2","a3","a4","a5","a6","a7","a8","a9","a0","a11","a12","a13","a14","a15"};
        word[8] = new String[]{"a1","a2","a3","a4","a5","a6","a7","a8","a9","a0","a11","a12","a13","a14","a15"};
        word[9] = new String[]{"a1","a2","a3","a4","a5","a6","a7","a8","a9","a0","a11","a12","a13","a14","a15"};
        return word;
    }

    public static String randomWordView(int i){
        Random rand = new Random();
        String[][] tmp = wordBook();
        return tmp[rand.nextInt(i)][rand.nextInt(15)];
    }
}
