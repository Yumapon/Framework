package businessLogic;

import beans.TestA;

public class BusinessLogicTestC implements BusinessLogic{

	//ログインメソッド
	@Override
	public boolean login(String pass) {
		if(pass.equals("pass")) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public void testMethod() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void testMethod(TestA testA) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
