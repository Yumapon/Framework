package usercreatesample.actions;

import annotation.ActionMethod;
import annotation.FormInjection;
import annotation.Service;
import usercreatesample.beans.TestB;
import usercreatesample.businessLogic.BusinessLogic;

/**
 * 動作確認用クラス
 * @author okamotoyuuma
 *
 */
public class ActionTestB {


	@Service
	BusinessLogic bl1;

	@FormInjection
	TestB testB;

	@ActionMethod("actionMethod")
	public String actionMethod() {
		System.out.println(testB.getA());
		System.out.println(testB.getB());
		System.out.println("アクションクラスのメソッドが実行されました！！！！！");
		bl1.testMethod();

		return "index3.html : forword";
	}

}
