import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import container.ApplicationContainerImplemention;

public class JunitgetActionTest {

	ApplicationContainerImplemention ac = new ApplicationContainerImplemention();

	@Test
	public void getActionTest() {
		String actionName = "actionA";
		Object actionObj = ac.getAction(actionName);
		Class<?> clazz = actionObj.getClass();
		try {
			Method m = clazz.getMethod("actionMethod");
			m.invoke(actionObj);
		} catch (NoSuchMethodException | SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
