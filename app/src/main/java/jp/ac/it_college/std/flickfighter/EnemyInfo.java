package jp.ac.it_college.std.flickfighter;

import java.util.Random;

public class EnemyInfo {
    private static Random rand = new Random();
    public static int[] bossPath = {
            R.drawable.boss1 ,R.drawable.boss2
            ,R.drawable.boss3 ,R.drawable.boss4
            ,R.drawable.boss5 ,R.drawable.boss6
            ,R.drawable.boss7 ,R.drawable.boss8
            ,R.drawable.boss9 ,R.drawable.boss10
    };
    //敵キャラのパスを登録
    public static int[] enemyPath = {
            R.drawable.zako1
            , R.drawable.zako2
            , R.drawable.zako3
            , R.drawable.zako4
    };

    public static String[][] wordBook(){
        String[][] word = new String[10][];
        word[0] = new String[]{"あうあう","あいあい","あい","あああ","いえ","いう","おいえ","おういえ","おい","あお","ええ","えい","ういお","おう","えいえいおー"};
        word[1] = new String[]{"かき","かい","かかお","かいが","かくい","かきくけこ","こくご","けいご","いいこ","えかき","いか","かいき","きく","くき","ごかく"};
        word[2] = new String[]{"さき","ささ","きさい","さしすせそ","すかい","すいか","すし","しきさい","そすう","せいそう","さしえ","そうじき","えくささいず","そうさい","かくせいき"};
        word[3] = new String[]{"たちつてと","とうだい","ちかてつどう","どせい","せいどう","どうじおし","かたくそうさく","てすとたいさく","かだい","だいどうげい","だかいさく","ちっそく","たんこう","こうだい","どくさいせいじ"};
        word[4] = new String[]{"なにぬねの","なっとう","のうかがく","ないないてい","かいねこ","ななかいだて","えすえぬえす","のうどう","なかおち","ないぞう","ながねぎ","いなりずし","なきおとし","こなおとし","あなごのつくだに"};
        word[5] = new String[]{"はひふへほ","はいけい","だいほうさく","ほうそくせい","ほっかいどう","ふっそかこう","ぱいぷいす","はっくつ","はいたてき","はとぽっぽ","はっかざい","ほっとどっぐ","さとうきびばたけ","いふうどうどう","ほうそうじこ"};
        word[6] = new String[]{"まみむめも","もうしあげます","まつのき","みそにこみ","むだばなし","もえないごみ","ごもくそば","まほうつかい","まかだみあなっつ","ごまどうふ","しものせき","かがみもち","まつばづえ","むかしばなし","まぐねしうむ"};
        word[7] = new String[]{"やさいいため","しょうゆさし","ようしょうき","やっきょく","やくざいし","ゆうきゅうきゅうか","ゆうとうせい","よていひょう","ひょうしょうじょう","やきゅうじょう","しょうぼうしゃ","ゆうゆうじてき","ゆくえふめい","しょうがくせい","きゅうきゅうびょうとう"};
        word[8] = new String[]{"ごまふあざらし","あるごりずむ","くれおぱとら","らいふすたいる","りゅうがくせい","りょうりきょうしつ","れいきゃくざい","ろうごしせつ","るりいろのぐらす","ろうどうしせつ","しりめつれつ","まだがすかる","くろっくしゅはすう","れべるあっぷ","れいがいしょり"};
        word[9] = new String[]{"ぷらっとふぉーむ","わんだーらんど","らんどせるかばー","ばんぐらでぃっしゅ","あいでんてぃてぃー","あいんしゅたいん","いんでぺんでんと","うんてんめんきょ","えんしんぶんりき","おおばんくるわせ","わしんとんしゅう","わいどすくりーん","ぷーちんだいとうりょう","ろしあんるーれっと","くりすますぷれぜんと"};
        return word;
    }

    public static String randomWordView(int i){
        String[][] tmp = wordBook();
        return tmp[rand.nextInt(i)][rand.nextInt(15)];
    }

    public static String bossWordView(int i) {
        String[][] tmp = wordBook();
        return tmp[i - 1][rand.nextInt(15)];
    }

    public static int randomEnemySummons(int i) {
        return rand.nextInt(i);
    }

    public static int enemyLifeSetting(int i) {
        int[] enemyLife = {
                3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6
        };
        return enemyLife[i];
    }

    public static int enemyPowSetting(int i) {
        int[] enemyPow = {
                1, 1, 1, 2, 3, 3, 3, 3, 4, 4, 4, 5
        };
        return enemyPow[i];
    }

    public static int bossLifeSetting(int i) {
        int[] bossLife = {
                5, 5, 6, 7, 7, 8, 9, 10, 10, 15
        };
        return bossLife[i];
    }

    public static int bossPowSetting(int i) {
        int[] bossPow = {
                2, 2, 3, 3, 4, 4, 5, 5, 6, 6
        };
        return bossPow[i];
    }
}
