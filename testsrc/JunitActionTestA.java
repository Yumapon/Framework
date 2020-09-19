import java.lang.reflect.Field;

import org.junit.Test;

import annotation.Service;
import usercreatesample.actions.ActionTestA;

public class JunitActionTestA {

	ActionTestA a = new ActionTestA();

	@Test
	public void test() {

		Class<?> clazz = ActionTestA.class;
		Field[] fields = clazz.getDeclaredFields();

		System.out.println(fields.length);
		for(Field f : fields) {
			System.out.println(f.getName());
			if(f.isAnnotationPresent(Service.class)) {
				System.out.println("@Service見つかりました。");
			}else {
				System.out.println("見つかりませんでした");
			}
		}
	}

}
