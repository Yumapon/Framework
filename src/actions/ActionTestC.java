package actions;

import beans.TestC;
import businessLogic.BusinessLogic;

public class ActionTestC {

	@Service
	BusinessLogic bl3;

	@FormInjection
	TestC testC;

	@ActionMethod("hogehoge")
	public void hogehogehogeMethod() {

		if(bl3.login(testC.getS())) {
			System.out.println("成功： パスワードは、passで正解です！");
		}else {
			System.out.println("エラー： パスワードは、piyopiyoではありません。");
		}
	}

}
