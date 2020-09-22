import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import container.ApplicationContainerImplemention;
import container.InstanceAndClassObjectforServlet;

public class JunitgetActionTest {

	ApplicationContainerImplemention ac = new ApplicationContainerImplemention();

	@Test
	public void getActionTest() {
		String actionName = "actionA";
		InstanceAndClassObjectforServlet cams = ac.getAction(actionName);
		Class<?> clazz = cams.getClazz();
		try {
			Method m = clazz.getMethod("actionMethod");
			m.invoke(cams.getObj());
		} catch (NoSuchMethodException | SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
