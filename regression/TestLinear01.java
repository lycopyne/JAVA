package regression;

public class TestLinear01 {





	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		TestLinear01 ob = new TestLinear01();
		ob.linear_start();
	}

	private void linear_start(){
//		String path = "D:/WSL研究/opt/train_vector3.csv";
//		int iterator = 3;
//		double lambda = 0.01;
//		LinearRegression lr = new LinearRegression(iterator, lambda);
//		lr.sgd_learning(true, path);
//		lr.write_inference(true, "D:/WSL研究/opt/test_vector3.csv", "D:/WSL研究/opt/inference32.csv");
//		lr.print_all();









		String path = "D:/WSL研究/SONY/2014/アンケート/回帰分析/下宿実家2/下宿実家2_教師2.csv";
		int iterator = 3;
		double lambda = 0.01;
		LogisticRegression lr = new LogisticRegression(iterator, lambda);
		lr.sgd_learning(true, path);
		lr.write_inference(true, "D:/WSL研究/SONY/2014/アンケート/回帰分析/下宿実家2/下宿実家2_入力.csv", "D:/WSL研究/SONY/2014/アンケート/回帰分析/下宿実家2/下宿実家2_logistic_推論x2.csv");
		lr.print_all();
//		String path = "D:/WSL研究/SONY/2014/アンケート/回帰分析/下宿実家2/下宿実家2_教師2.csv";
//		int iterator = 3;
//		double lambda = 0.01;
//		LinearRegression lr = new LinearRegression(iterator, lambda);
//		lr.sgd_learning(true, path);
//		lr.write_inference(true, "D:/WSL研究/SONY/2014/アンケート/回帰分析/下宿実家2/下宿実家2_入力.csv", "D:/WSL研究/SONY/2014/アンケート/回帰分析/下宿実家2/下宿実家2_推論x2.csv");
//		lr.print_all();
	}

}
