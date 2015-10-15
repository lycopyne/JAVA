package library;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.java.sen.StringTagger;
import net.java.sen.Token;

/*
 * ◆◇◆Read Me!!◆◇◆
 * ビルドパスの構築で3つ外部jarの追加が必要。commons-logging.jar、junit.jar、sen.jarの3つを追加。
 *                  Dドライブ直下で解凍した場合はD:/sen/sen/ においてあるはず。
 * http://www.unixuser.org/~euske/doc/postag/
 * 抜き出したい品詞情報は上記サイトを参考。名詞の絞り方の例 ("名詞" or "名詞-固有名詞")など
 *
 * ちなみにこのライブラリで返す形態素は全て原型です。単語そのままの型で返したい場合は
 * token[i].getBasicString()をtoken[i].toString()に変えればOK
 *
 * また、このライブラリを使用するときは必ずsenConstractorを呼び出すこと。詳細は以下に。
 */
/**
 * 以下メソッドの説明。
 * senConstructor(senFile)	: 引数はsen.xmlファイルがあるパス。このメソッドを呼び出さないと動かない。 ex.) D:/library/sen/conf/sen.xml
 * printTextInfo(text)		: テキストを渡し、そのテキストを形態素解析して品詞情報を出力する。
 * analyze(text)			: テキストを受け取り形態素解析してリストで返す。
 * analyze(text, hinshi)	: テキストを受け取り形態素解析し、指定した品詞のもののみリストで返す。渡すStringの品詞のみ。
 * analyze(text, hinshiSet)	: テキストを受け取り形態素解析し、指定した品詞のもののみリストで返す。渡すSet内に含まれている品詞のみ。
 * analyzeExcept(text, hinshi)		: テキストを受け取り形態素解析し、指定した品詞以外のもののみリストで返す。渡すString以外の品詞のみ。
 * analyzeExcept(text, hinshiSet)	: テキストを受け取り形態素解析し、指定した品詞以外のもののみリストで返す。渡すSet内に含まれていない品詞のみ。
 * @author yoshiki_m
 */
public class SenUtil {

	private static int flag = 0;
	private static StringTagger tagger;

	public static void senConstructor(String senFile){
		tagger = null;
		try {
			tagger = StringTagger.getInstance(senFile);
			flag = 1;
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void printTextInfo(String text){
		readCheck();
		try{
			text = remove(text);
			//形態素解析
			Token[] token = tagger.analyze(text);
			for(int i = 0; i < token.length; i++){
				System.out.println(token[i].getBasicString() + "\t" + token[i].getPos());
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * @param text : 形態素解析するテキスト
	 * @return		形態素解析後の単語配列
	 */
	public static List<String> analyze(String text){
		readCheck();
		try{
			text = remove(text);
			//形態素解析
			Token[] token = tagger.analyze(text);
			List<String> words = new ArrayList<>();
			for(int i = 0; i < token.length; i++){
				words.add(token[i].getBasicString());
			}
			return words;
		}
		catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param text : 形態素解析するテキスト
	 * @param hinshi : 絞る品詞の型
	 * @return		形態素解析後の単語配列
	 */
	public static List<String> analyze(String text, String hinshi){
		readCheck();
		try{
			text = remove(text);
			//形態素解析
			Token[] token = tagger.analyze(text);
			List<String> words = new ArrayList<>();
			for(int i = 0; i < token.length; i++){
				if(token[i].getPos().contains(hinshi)){
					words.add(token[i].getBasicString());
				}
			}
			return words;
		}
		catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param text : 形態素解析するテキスト
	 * @param hinshiSet : 絞る品詞の型
	 * @return		形態素解析後の単語配列
	 */
	public static List<String> analyze(String text, Set<String> hinshiSet){
		readCheck();
		try{
			text = remove(text);
			//形態素解析
			Token[] token = tagger.analyze(text);
			List<String> words = new ArrayList<>();
			for(int i = 0; i < token.length; i++){
				if(setContainCheck(hinshiSet, token[i].getPos())){
					words.add(token[i].getBasicString());
				}
			}
			return words;
		}
		catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param text : 形態素解析するテキスト
	 * @param hinshi : Listに入れたくない品詞の型
	 * @return		形態素解析後の単語配列
	 */
	public static List<String> analyzeExcept(String text, String hinshi){
		readCheck();
		try{
			text = remove(text);
			//形態素解析
			Token[] token = tagger.analyze(text);
			List<String> words = new ArrayList<>();
			for(int i = 0; i < token.length; i++){
				if(!token[i].getPos().contains(hinshi)){
					words.add(token[i].getBasicString());
				}
			}
			return words;
		}
		catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param text : 形態素解析するテキスト
	 * @param hinshiSet : Listに入れたくない品詞の型
	 * @return		形態素解析後の単語配列
	 */
	public static List<String> analyzeExcept(String text, Set<String> hinshiSet){
		readCheck();
		try{
			text = remove(text);
			//形態素解析
			Token[] token = tagger.analyze(text);
			List<String> words = new ArrayList<>();
			for(int i = 0; i < token.length; i++){
				if(!setContainCheck(hinshiSet, token[i].getPos())){
					words.add(token[i].getBasicString());
				}
			}
			return words;
		}
		catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}


	/*
	 * 文字化け取り除き
	 * sen辞書だと「?」は文字化けになる
	 */
	private static String remove(String text){
		String returnText = text;
		if(text.contains("?")){
			returnText = returnText.replace("?", " ");
		}
		return returnText;
	}
	/*
	 * senFileの読み込みチェック
	 */
	private static void readCheck(){
		if(flag == 0)
			System.err.println("ERROR!!! CANNNOT READ SENFILE.\t\tFirst, You must call \"senConstructor\" method!!");
	}
	/*
	 * setの中身にnounが入ってるか否か
	 */
	private static boolean setContainCheck(Set<String> set, String noun){
		for(String key : set){
			if(noun.contains(key))
				return true;
		}
		return false;
	}
}
