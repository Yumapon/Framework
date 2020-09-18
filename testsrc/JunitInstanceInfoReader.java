import java.util.HashMap;

import org.junit.Test;

import container.ActionConfigReader;
import container.ActionDefinition;
import container.BeanConfigReader;

public class JunitInstanceInfoReader {

	BeanConfigReader bdr = new BeanConfigReader();
	ActionConfigReader acr = new ActionConfigReader();

	@Test
	public void beanDefinitionReaderTest() {
		HashMap<String, String> map = bdr.read();

		System.out.println("-----------------");
		System.out.println(map.get("TestA"));
		System.out.println(map.get("TestB"));

		HashMap<String, ActionDefinition> map2 = acr.read();

		System.out.println("-----------------");
		System.out.println(map2.get("actionA").getType());
		/*
		for(String s2 : map2.get("actionA").getBusinessLogicName()) {
			System.out.println(s2);
		}
		*/
	}

}
