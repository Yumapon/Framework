import org.junit.Test;

import container.ApplicationContainerImplemention;
import usercreatesample.businessLogic.BusinessLogic;

public class JunitGetProxyTest {

	ApplicationContainerImplemention apc = new ApplicationContainerImplemention();

	@Test
	public void test() throws NoSuchMethodException, SecurityException {
		BusinessLogic bl = (BusinessLogic) apc.getbl("bl1");
		bl.login("pass");
		bl.login("a", "b");
	}

}
