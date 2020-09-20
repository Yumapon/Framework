import org.junit.Test;

import container.ApplicationContainer;
import usercreatesample.businessLogic.BusinessLogic;

public class JunitGetProxyTest {

	ApplicationContainer apc = new ApplicationContainer();

	@Test
	public void test() {
		BusinessLogic bl = (BusinessLogic) apc.getbl("bl1");
		bl.login("pass");
	}

}
