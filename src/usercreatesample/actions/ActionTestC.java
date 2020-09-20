package usercreatesample.actions;

import annotation.ActionMethod;
import annotation.FormInjection;
import annotation.Service;
import usercreatesample.beans.TestC;
import usercreatesample.businessLogic.BusinessLogic;

/**
 * 動作確認用クラス
 * @author okamotoyuuma
 *
 */
public class ActionTestC {

	@Service
	BusinessLogic bl3;

	@FormInjection
	TestC testC;

	@ActionMethod("hogehoge")
	public String hogehogehogeMethod() {

		if(bl3.login(testC.getS())) {
			System.out.println("成功： パスワードは、passで正解です！");
		}else {
			System.out.println("エラー： パスワードは、piyopiyoではありません。");
		}

		return "index.html : redirect";
	}

}
