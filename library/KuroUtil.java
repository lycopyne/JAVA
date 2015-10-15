package library;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;

/*
 * SenUtil.javaの上位互換。kuromojiのほうが使いやすい。
 * https://github.com/atilika/kuromoji/downloads
 * ちなみにここからダウンロードしました。
 */
/**
 * 以下メソッドの説明。
 *
 * analyze(text)			: テキストを受け取り形態素解析してリストで返す。
 * analyze(text, hinshi)	: テキストを受け取り形態素解析し、指定した品詞のもののみリストで返す。渡すStringの品詞のみ。
 * analyze(text, hinshiSet)	: テキストを受け取り形態素解析し、指定した品詞のもののみリストで返す。渡すSet内に含まれている品詞のみ。
 * analyzeExcept(text, hinshi)		: テキストを受け取り形態素解析し、指定した品詞以外のもののみリストで返す。渡すString以外の品詞のみ。
 * analyzeExcept(text, hinshiSet)	: テキストを受け取り形態素解析し、指定した品詞以外のもののみリストで返す。渡すSet内に含まれていない品詞のみ。
 * @author yoshiki_m
 *
 */
public class KuroUtil {

	private static Tokenizer tokenizer = Tokenizer.builder().build();

	/**
	 * テキストを受け取り形態素解析してリストで返す。
	 * @param text
	 * @return
	 */
	public static List<String> analyze(String text){
		List<Token> result = tokenizer.tokenize(text);
		List<String> list = new ArrayList<>();
		for(Token token : result)
			list.add(token.getBaseForm());
		return list;
	}

	/**
	 * テキストを受け取り形態素解析し、指定した品詞のもののみリストで返す。渡すStringの品詞のみ。
	 * @param text
	 * @return
	 */
	public static List<String> analyze(String text, String hinshi){
		List<Token> result = tokenizer.tokenize(text);
		List<String> list = new ArrayList<>();
		for(Token token : result)
			if(token.getAllFeaturesArray()[0].equals(hinshi))
				list.add(token.getBaseForm());
		return list;
	}

	/**
	 * テキストを受け取り形態素解析し、指定した品詞のもののみリストで返す。渡すSet内に含まれている品詞のみ。
	 * @param text
	 * @return
	 */
	public static List<String> analyze(String text, Set<String> hinshiSet){
		List<Token> result = tokenizer.tokenize(text);
		List<String> list = new ArrayList<>();
		for(Token token : result)
			if(setContainCheck(hinshiSet, token.getAllFeaturesArray()[0]))
				list.add(token.getBaseForm());
		return list;
	}

	/**
	 * テキストを受け取り形態素解析し、指定した品詞のもののみリストで返す。渡すString以外の品詞のみ。
	 * @param text
	 * @return
	 */
	public static List<String> analyzeExcept(String text, String hinshi){
		List<Token> result = tokenizer.tokenize(text);
		List<String> list = new ArrayList<>();
		for(Token token : result)
			if(!token.getAllFeaturesArray()[0].equals(hinshi))
				list.add(token.getBaseForm());
		return list;
	}

	/**
	 * テキストを受け取り形態素解析し、指定した品詞のもののみリストで返す。渡すSet内に含まれていない品詞のみ。
	 * @param text
	 * @return
	 */
	public static List<String> analyzeExcept(String text, Set<String> hinshiSet){
		List<Token> result = tokenizer.tokenize(text);
		List<String> list = new ArrayList<>();
		for(Token token : result)
			if(!setContainCheck(hinshiSet, token.getAllFeaturesArray()[0]))
				list.add(token.getBaseForm());
		return list;
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
