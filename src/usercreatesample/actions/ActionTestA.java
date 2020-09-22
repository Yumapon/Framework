package usercreatesample.actions;

import annotation.ActionMethod;
import annotation.FormInjection;
import annotation.Service;
import usercreatesample.beans.TestA;
import usercreatesample.beans.TestB;
import usercreatesample.businessLogic.BusinessLogic;
/**
 * 動作確認用クラス
 * @author okamotoyuuma
 *
 */
public class ActionTestA {

	@Service
	BusinessLogic bl1;

	@FormInjection
	TestA testA;

	@FormInjection
	TestB testB;

	/*
	@FormInjection
	TestB testb;
	*/

	@ActionMethod("testMethod")
	public String actionMethod() {
		System.out.println(testA.getStr1());
		System.out.println(testA.getStr2());
		System.out.println("アクションクラスのメソッドが実行されました！！！！！");
		bl1.testMethod();

		return "index2.html : forword";
	}

}
