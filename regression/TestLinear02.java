package regression;



public class TestLinear02 {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		TestLinear02 ob = new TestLinear02();
		ob.linear_start();
	}


	private void linear_start(){
//		String path = "D:/WSL研究/SONY/2014/アンケート/回帰分析/下宿実家2/下宿実家2_教師2.csv";
//		int iterator = 3;
//		double lambda = 0.01;
//		LogisticRegression lr = new LogisticRegression(iterator, lambda);
//		lr.cross10_sgd_inference(true, path, "D:/WSL研究/SONY/2014/アンケート/回帰分析/下宿実家2/下宿実家2_logistic_教師10交叉x2.csv");
//		lr.print_all();
		String path = "D:/WSL研究/SONY/2014/アンケート/回帰分析/下宿実家2/下宿実家2_教師2.csv";
		int iterator = 3;
		double lambda = 0.01;
		LinearRegression lr = new LinearRegression(iterator, lambda);
		lr.cross10_sgd_inference(true, path, "D:/WSL研究/SONY/2014/アンケート/回帰分析/下宿実家2/下宿実家2_教師10交叉x2.csv");
		lr.print_all();
	}
}
