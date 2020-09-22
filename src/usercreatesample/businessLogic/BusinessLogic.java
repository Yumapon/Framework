package usercreatesample.businessLogic;

import usercreatesample.beans.TestA;

/**
 * 動作確認用クラス
 * @author okamotoyuuma
 *
 */
public interface BusinessLogic {

	void testMethod();

	void testMethod(TestA testA);

	boolean login(String pass);

	void login(String a, String b);

}
