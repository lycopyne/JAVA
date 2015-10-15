package regression;

public class TestLogistic01 {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		TestLogistic01 ob = new TestLogistic01();
		ob.logistic_start();
	}
	private void logistic_start(){
		String path = "D:/WSL研究/SONY/2014/アンケート/回帰分析/外向内向2_教師.csv";
		int iterator = 3;
		double lambda = 0.01;
		LogisticRegression lr = new LogisticRegression(iterator, lambda);
		lr.cross10_sgd_inference(true, path, "D:/WSL研究/SONY/2014/アンケート/回帰分析/外向内向2_ロジスティック_教師10交叉.csv");
		lr.print_all();
	}
}
