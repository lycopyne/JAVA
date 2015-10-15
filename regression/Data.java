package regression;


public class Data {


	public int y;
	public double[] x;
	Data(){
		clear();
	}
	Data(int input_y, double[] input_x){
		input(input_y, input_x);
	}

	/**
	 * 初期化
	 */
	public void clear(){
		y = 0;
//		x = new ArrayList<>();
		x = null;
	}
	/**
	 * 読み込み
	 */
	public void input(int input_y, double[] input_x){
		y = input_y;
		x = input_x;
	}
//	/**
//	 * yの出力
//	 */
//	public int output_y(){
//		return y;
//	}
//	/**
//	 * xの出力
//	 * @return
//	 */
//	public List<Integer> output_x(){
//		return x;
//	}
}
