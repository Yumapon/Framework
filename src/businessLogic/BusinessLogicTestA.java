package businessLogic;

import beans.TestA;

public class BusinessLogicTestA implements BusinessLogic{

	@Override
	public void testMethod() {
		System.out.println("ビジネスロジックのメソッドが実行されました！！！！");
	}

	@Override
	public void testMethod(TestA testA) {
		System.out.println("ビジネスロジックのメソッドが実行されました！！！！");
		System.out.println("testA.str1 =" + testA.getStr1() + "：testA.str2 =" + testA.getStr2());
	}

	@Override
	public boolean login(String pass) {
		System.out.println("ビジネスロジックのメソッドが実行されました！！！！");
		return false;
	}

}
