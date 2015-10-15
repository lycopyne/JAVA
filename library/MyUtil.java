package library;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * ライブラリ
 */
public class MyUtil {

	/**
	 * 高速版split
	 * @param str splitする文字列
	 * @param delimiter splitのデリミタ
	 * @return split後の文字列配列
	 */
	// strの文字列をdelimiterで分割するメソッド
	public static String[] split(String str, String delimiter) {
		// StringTokenizer(s,x)でsの文字列をxで分割することができる
		StringTokenizer tokenizer = new StringTokenizer(str, delimiter);
		String[] resultStr = new String[tokenizer.countTokens()];

		int i = 0;
		while (tokenizer.hasMoreTokens()) {
			resultStr[i++] = tokenizer.nextToken();
		}
		return resultStr;
	}


	/**
	 * 高速版split
	 * @param str splitする文字列
	 * @param delimiter splitのデリミタ
	 * @return split後の文字列配列
	 */
	// strの文字列をdelimiterで分割するメソッド
	public static String[] splitAll(String str, String delimiter) {
		// StringTokenizer(s,x)でsの文字列をxで分割することができる
		StringTokenizer tokenizer = new StringTokenizer(str, delimiter, true);
		String[] resultStr = new String[tokenizer.countTokens()];

		int i = 0;
		while (tokenizer.hasMoreTokens()) {
			resultStr[i++] = tokenizer.nextToken();
		}
		return resultStr;
	}

	/**
	 * int型で表せるか否か
	 */
	public static boolean intCheck(String str){
		try{
			Integer.parseInt(str);
			return true;
		}
		catch(NumberFormatException e){
			return false;
		}
	}

	/**
	 * double型で表せるか否か
	 */
	public static boolean doubleCheck(String str){
		try{
			Double.parseDouble(str);
			return true;
		}
		catch(NumberFormatException e){
			return false;
		}
	}
	/**
	 * long型で表せるか否か
	 */
	public static boolean longCheck(String str){
		try{
			Long.parseLong(str);
			return true;
		}
		catch(NumberFormatException e){
			return false;
		}
	}

	/**
	 * textを1文字ずつに分割した配列にして返す。
	 * @param text
	 * @return
	 */
	public static String[] stringCharSplit(String text){
		char[] c = text.toCharArray();
		String[] terms = new String[c.length];
		for(int i = 0; i < c.length; i++){
			terms[i] = String.valueOf(c[i]);
		}
		return terms;
	}


	/**
	 * Mapをvalue(Double)で昇順ソートして返す
	 * @param map ソートするMap
	 * @return ソート済のTreeMap
	 */
	public static Map<String, Double> sortAscendingMapDouble(Map<String, Double> map) {
		List<Map.Entry<String, Double>> entries = new LinkedList<Map.Entry<String, Double>>(map.entrySet());

		Collections.sort(entries, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Map.Entry<String, Double> o1,
					Map.Entry<String, Double> o2) {
				Map.Entry<String, Double> entry1 = (Map.Entry<String, Double>) o1;
				Map.Entry<String, Double> entry2 = (Map.Entry<String, Double>) o2;
				Double d1 = entry1.getValue(), d2 = entry2.getValue();

				if(d2 > d1)
					return -1;
				else if(d2 < d1)
					return 1;
				else
					return 0;
			}
		});
		//return用
		Map<String, Double> ranking = new LinkedHashMap<String, Double>();
		for (Map.Entry<String, Double> entry : entries) {
			ranking.put(entry.getKey(), entry.getValue());
		}
		return ranking;
	}

	/**
	 * Mapをvalue(Double)で降順ソートして返す
	 * @param map ソートするMap
	 * @return ソート済のTreeMap
	 */
	public static Map<String, Double> sortDescendingMapDouble(Map<String, Double> map) {
		List<Map.Entry<String, Double>> entries = new LinkedList<Map.Entry<String, Double>>(map.entrySet());

		Collections.sort(entries, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Map.Entry<String, Double> o1,
					Map.Entry<String, Double> o2) {
				Map.Entry<String, Double> entry1 = (Map.Entry<String, Double>) o1;
				Map.Entry<String, Double> entry2 = (Map.Entry<String, Double>) o2;
				Double d1 = entry1.getValue(), d2 = entry2.getValue();

				if(d2 > d1)
					return 1;
				else if(d2 < d1)
					return -1;
				else
					return 0;
			}
		});

		//return用
		Map<String, Double> ranking = new LinkedHashMap<String, Double>();
		for (Map.Entry<String, Double> entry : entries) {
			ranking.put(entry.getKey(), entry.getValue());
		}
		return ranking;
	}

	/**
	 * Mapをvalue(Integer)で昇順ソートして返す
	 * @param map ソートするMap
	 * @return ソート済のTreeMap
	 */
	public static Map<String, Integer> sortAscendingMapInt(Map<String, Integer> map) {
		List<Map.Entry<String, Integer>> entries = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());

		Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
					Map.Entry<String, Integer> o2) {
				Map.Entry<String, Integer> entry1 = (Map.Entry<String, Integer>) o1;
				Map.Entry<String, Integer> entry2 = (Map.Entry<String, Integer>) o2;
				Integer d1 = entry1.getValue(), d2 = entry2.getValue();

				if(d2 > d1)
					return -1;
				else if(d2 < d1)
					return 1;
				else
					return 0;
			}
		});

		//return用
		Map<String, Integer> ranking = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> entry : entries) {
			ranking.put(entry.getKey(), entry.getValue());
		}
		return ranking;
	}

	/**
	 * Mapをvalue(Integer)で降順ソートして返す
	 * @param map ソートするMap
	 * @return ソート済のTreeMap
	 */
	public static Map<String, Integer> sortDescendingMapInt(Map<String, Integer> map) {
		List<Map.Entry<String, Integer>> entries = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());

		Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
					Map.Entry<String, Integer> o2) {
				Map.Entry<String, Integer> entry1 = (Map.Entry<String, Integer>) o1;
				Map.Entry<String, Integer> entry2 = (Map.Entry<String, Integer>) o2;
				Integer d1 = entry1.getValue(), d2 = entry2.getValue();

				if(d2 > d1)
					return 1;
				else if(d2 < d1)
					return -1;
				else
					return 0;
			}
		});

		//return用
		Map<String, Integer> ranking = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> entry : entries) {
			ranking.put(entry.getKey(), entry.getValue());
		}
		return ranking;
	}

	/**
	 * setの中身にnounが入ってるか否か
	 */
	public static boolean setContainCheck(Set<String> set, String noun){
		for(String key : set){
			if(noun.contains(key))
				return true;
		}
		return false;
	}
	/**
	 * setの中身にnounが入ってる場合は、そのsetの中身を
	 */
	public static String setContainWord(Set<String> set, String noun){
		for(String key : set){
			if(noun.contains(key))
				return key;
		}
		return null;
	}

	/**
	 * strが数字か否か
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }

}
