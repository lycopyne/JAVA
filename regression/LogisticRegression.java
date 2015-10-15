package regression;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import library.TimeCounter;

public class LogisticRegression {

	private final double DEFAULT = 1.0;
	// シグモイド関数のaの値
	private final double a = 1.0;
	// 切片
	private double B;
	private double Before_B;

	// イテレーション回数
	private int Iterator;
	// 任意の値(なるべく小さく、小さすぎず)
	private double Lambda;
	// wの値のリスト
	private double W[];
	private double Before_W[];





	// 学習メソッドを呼び出したか否か
	private boolean learning_flag;
	// 時間を測る
	private TimeCounter Time;
	private int e_count;
	private int data_size;

	/**
	 * テスト用
	 */
	// 読み込んだデータにラベルがあった場合に保管
	public String Label;
	// E(x)の更新式の確認
	public double before_e;
//	private Random r = new Random(1);








/////////////// クラスの呼び出し ////////////////
	/**
	 * 初期値を何も与えず呼び出し
	 * 500, 0.05, 500 が初期値
	 */
	public LogisticRegression(){
		score_default();
	}
	/**
	 * 何も初期値を与えなかった時のデフォルト値
	 */
	private void score_default(){
		clear(10, 0.01);
	}

	/**
	 * 初期値与えて呼び出し
	 * @param iterator
	 * @param lambda
	 * @param dimension
	 */
	public LogisticRegression(int iterator, double lambda){
		clear(iterator, lambda);
	}
	/**
	 * 全変数の初期化、作成
	 */
	public void clear(int iterator, double lambda){
		learning_flag = false;
		Iterator = iterator;
		Lambda = lambda;
//		B = r.nextDouble();
//		Before_B = r.nextDouble();
		B = DEFAULT;
		Before_B = DEFAULT;
		W = new double[100];
		Before_W = new double[100];
		clear_W(W);
		clear_W(Before_W);
		Label = null;
		Time = new TimeCounter();
		before_e = Double.MIN_VALUE;
		e_count = 0;
		data_size = 0;
	}
	/**
	 * 配列Wの初期化
	 * 初期値はランダムではなく全て1.0を入れる。
	 */
	private void clear_W(double[] x){
		for(int i = 0; i < x.length; i++){
//			x[i] = r.nextDouble();
			x[i] = DEFAULT;
		}
	}






///////////////// データの読み込み ///////////////
	/**
	 * pathのデータを読み込み、List<Data>型にして返す。
	 *
	 * 読み込むデータの型はcsv形式で
	 * y1, x0, x1, x2, …
	 * という形式。yは目的変数でxは説明変数
	 */
	private List<Data> read_learn_csv(boolean label,String path){
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "SJIS"))){
			String line;
			// 一行目にラベルが含まれていれば保管
			if(label == true)
				Label = br.readLine();
			List<Data> dataList = new ArrayList<>();
			int dimension = 0;
			while((line = br.readLine()) != null){
				String[] split = line.split(",");
				dimension = split.length-1;
				int y = 0;
				y = Integer.valueOf(split[0]);
				double[] x = new double[split.length-1];
				for(int i = 1 ; i < split.length; i++){
					if(split[i] != null)
						if(!split[i].equals("")){
							x[i-1] = Double.valueOf(split[i]);
						}
				}
				Data data = new Data(y, x);
				dataList.add(data);
			}
			br.close();
			make_W(dimension);
			return dataList;
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * pathのデータを読み込み、List<Data>型にして返す。
	 *
	 * 読み込むデータの型はcsv形式で
	 * y, x0, x1, x2, …
	 * という形式。xは説明変数で説明変数のみのデータを読み込ませる。
	 */
	private List<Data> read_test_csv(boolean label,String path){
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "SJIS"))){
			String line;
			// 一行目にラベルが含まれていれば保管
			if(label == true)
				Label = br.readLine();
			List<Data> dataList = new ArrayList<>();
			while((line = br.readLine()) != null){
				String[] split = line.split(",");
				int y = 0;
				double[] x = new double[split.length-1];
				for(int i = 1 ; i < split.length; i++){
					if(split[i] != null)
						if(!split[i].equals("")){
							x[i-1] = Double.valueOf(split[i]);
						}
				}
				Data data = new Data(y, x);
				dataList.add(data);
			}
			br.close();
			return dataList;
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Wの初期化
	 * @param dimension
	 */
	private void make_W(int dimension){
		W = new double[dimension];
		Before_W = new double[dimension];
		clear_W(W);
		clear_W(Before_W);
	}





////////////////// データの書き出し ///////////////////
	/**
	 * pathのデータにListの中身を全て書きだす。
	 * 受け取るListは出力の推論値。
	 */
	private void write_csv(String path, List<Double> writeList){
		try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "SJIS"))){
			for(int i = 0; i < writeList.size(); i++){
				double d = writeList.get(i);
				String text = String.valueOf(d);
				bw.write(text);
				bw.write("\n");
			}
			bw.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}








/////////////// 学習 //////////////////
	/**
	 * sgd学習
	 * イテレーション回数試行
	 * ファイルを読み込み学習
	 * label は一行目にラベルがあるかどうか
	 */
	public void sgd_learning(boolean label, String path){
		List<Data> dataList = read_learn_csv(label, path);
		sgd_learning(dataList);
	}
	/**
	 * sgd学習
	 * イテレーション回数試行
	 * DataのListを読み込み学習
	 */
	public void sgd_learning(List<Data> dataList){
		learning_flag = true;
		data_size = dataList.size();
		for(int hoge = 1; hoge <= Iterator; hoge++){
			System.out.println("◆◆    Iteration = " + hoge + "回目   :    " + Time.getElapsedTimeString() + "    ◆◆");
			sgd(dataList);
		}
	}
	/**
	 * 1つ1つのData読み込み
	 */
	private void sgd(List<Data> dataList){
		for(int i = 0; i < dataList.size(); i++){
			Data data = dataList.get(i);
			sgd_renew(data.y, data.x);
//			double e = E(data.y, data.x);
//			if(before_e < e){
//				e_count++;
//			}
//			before_e = e;
		}
//		System.out.println(e_count);
	}









//////////////////// 更新＆計算 //////////////////////////
	/**
	 * wの更新
	 */
	private void sgd_renew(int y, double[] x){
		B = B - Lambda*delta_E(Integer.MIN_VALUE, y, x);
		for(int w = 0; w < x.length; w++){
			// 微分値の計算
//			double differential = delta_E(w, y, x);
			// 過学習防止のノイズ(正則化)
			double differential = delta_E(w, y, x)+Lambda*W[w];
			// Wn(i+1) = Wn(i)*λ*ΔE/ΔWn
			W[w] = W[w]-Lambda*differential;
		}
		Before_B = B;
		Before_W = W.clone();
	}
	/**
	 * ΔE/Δw
	 * の計算。引数としてどの次元で微分するか渡す。
	 */
	private double delta_E(int delta_w, int y, double[] x){
		// σ(W0*X0 + W1*X1 + W2*X2+ … + WnXn)
		double score = sigmoid(cal(x))-y;
		if(delta_w == Integer.MIN_VALUE)
			return score;
		score = score*x[delta_w];
		return score;
	}
	/**
	 * E
	 * の計算
	 */
	public double E(int y, double[] x){
		// (W)
		double score = B-y;
		for(int w = 0; w < x.length; w++){
			score += W[w]*x[w];
		}
		score = score*score/2;
		return score;
	}
	/**
	 * シグモイドの計算
	 */
	private double sigmoid(double x){
		double score = 1 / (1 + Math.exp(-a*x));
		return score;
	}


	/**
	 * 計算したWの値を入力にあてはめてyの推測値の出力。
	 */
	public double cal(double[] x){
		if(!learning_flag){
			System.err.println("not call learning method");
			return 0.0;
		}
		double score = Before_B;
		for(int i = 0; i < x.length; i++){
			score += Before_W[i]*x[i];
		}
		return score;
	}











///////////////////// 分類 /////////////////////////
	/**
	 * 学習したWの値を用いて入力データが与えられた時の予測値の計算
	 * test_pathを読み込み学習
	 * 推論結果をinf_pathに出力
	 * label は一行目にラベルがあるかどうか
	 */
	public void write_inference(boolean label, String test_path, String inf_path){
		List<Data> dataList = read_test_csv(label, test_path);
		List<Double> writeList = inference(dataList);
		write_csv(inf_path, writeList);
	}

	/**
	 * 学習したWの値を用いて入力データが与えられた時の予測値の計算
	 * DataのListを読み込み学習
	 */
	public List<Double> inference(List<Data> dataList){
		if(!learning_flag){
			System.err.println("not call learning method");
			return null;
		}
		List<Double> writeList = new ArrayList<>();
		for(int i = 0; i < dataList.size(); i++){
			Data data = dataList.get(i);
			// 推論値
			double score = cal(data.x);
			score = sigmoid(score);
			writeList.add(score);
		}
		return writeList;
	}



	/**
	 * 受け取った学習データを10交叉検定にかけて、精度の出力
	 */
	public void cross10_sgd_inference(boolean label, String input_path, String output_path){
		List<Data> dataList = read_learn_csv(label, input_path);
		List<Double> score = cross10_sgd_inference(dataList);
		write_csv(output_path, score);
	}
	/**
	 * 受け取った学習データを10交叉検定にかけて、精度の出力
	 */
	public List<Double> cross10_sgd_inference(List<Data> dataList){
		int x = dataList.size()/10+1;
		List<Double> score = new ArrayList<>();;
		for(int i = 0; i < 10; i++){
			System.out.println("交叉" + i + "回目");
			List<Data> input = new ArrayList<>();
			List<Data> output = new ArrayList<>();
			// 入力の作成
			for(int j = 0; j < dataList.size(); j++){
				if(j/x != i)
					continue;
				Data data = dataList.get(j);
				input.add(data);
//				System.out.println("OK");
			}
			// 出力の作成
			for(int j = 0; j < dataList.size(); j++){
				if(j/x == i)
					continue;
//				System.out.print(j + ",");
				Data data = dataList.get(j);
				output.add(data);
			}
			sgd_learning(output);
			List<Double> score1 = inference(input);
			for(int j = 0; j < score1.size(); j++){
				double d = score1.get(j);
				score.add(d);
			}
		}
		return score;
	}










//////////////////// 出力 /////////////////////////
	/**
	 * bとwの計算した結果を全て出力
	 */
	public void print_all(){
		System.out.println(output_all());
	}
	/**
	 * bとwの計算した結果を全て返す(String型)
	 */
	public String output_all(){
		StringBuilder sb = new StringBuilder();
		sb.append("e_count = ");
		sb.append(String.valueOf(e_count));
		sb.append("\n");
		long count = data_size*Iterator*(W.length-1);
		sb.append("renew_count = ");
		sb.append(String.valueOf(count));
		sb.append("\n");
		sb.append("\n");
		sb.append("b = ");
		sb.append(String.valueOf(B));
		sb.append("\n");
		for(int i = 0; i < W.length; i++){
			sb.append("w");
			sb.append(String.valueOf(i));
			sb.append(" = ");
			sb.append(String.valueOf(W[i]));
			sb.append("\n");
		}
		return sb.toString();
	}




}
