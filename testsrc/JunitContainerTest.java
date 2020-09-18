import org.junit.Before;
import org.junit.Test;

import container.ApplicationContainer;
import container.InstanceAndClassObjectforServlet;

public class JunitContainerTest {

	ApplicationContainer ac;

	@Before
	public void setUp(){
		//引数なしコンストラクタでの生成
		ac = new ApplicationContainer();
	}

	//@Test
	/*
	public void generateTest() {
		//コンテナによるBean生成
		TestA testA = (TestA) ac.generator("TestA");
		testA.setStr("hogehoge");
		System.out.println(testA.getStr());

		TestB testB = (TestB) ac.generator("TestB");
		testB.setA(100);
		System.out.println(testB.getA());
	}
	*/

	@Test
	public void test3() {
		InstanceAndClassObjectforServlet cams = ac.getCAMS("TestA");
		System.out.println(cams.getObj());
	}

}
