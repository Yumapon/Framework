import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import container.ApplicationContainerImplemention;

public class JunitInstanceGeneratorTest {

	ApplicationContainerImplemention ig = new ApplicationContainerImplemention();;

	@ParameterizedTest
	@ValueSource(strings = {"beans.TestA", "beans.TestB", "beans.TestC"})
	public void testGenerate(String instancePlace) {
		ig.setInstancePlace(instancePlace);

		@SuppressWarnings("unused")
		Object obj = ig.generator();

		//テスト対象
		/*
		TestA test = (TestA)ig.generator();
		test.setStr("powpow");
		System.out.println(test.getStr());
		*/
	}

}
