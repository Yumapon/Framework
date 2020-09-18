package businessLogic;

import beans.TestA;

public interface BusinessLogic {

	void testMethod();

	void testMethod(TestA testA);

	boolean login(String pass);

}
