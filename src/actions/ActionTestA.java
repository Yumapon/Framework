package actions;

import beans.TestA;
import businessLogic.BusinessLogic;

public class ActionTestA {

	@Service
	BusinessLogic bl1;

	@FormInjection
	TestA testA = new TestA();


	@ActionMethod("actionMethod")
	public void actionMethod() {
		testA.setStr1("hoge");
		testA.setStr2("wow");
		System.out.println(testA.getStr1());
		System.out.println(testA.getStr2());
		System.out.println("アクションクラスのメソッドが実行されました！！！！！");
		bl1.testMethod();
	}

}
