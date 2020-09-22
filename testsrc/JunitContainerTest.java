import org.junit.Before;

import container.ApplicationContainerImplemention;

public class JunitContainerTest {

	ApplicationContainerImplemention ac;

	@Before
	public void setUp(){
		//引数なしコンストラクタでの生成
		ac = new ApplicationContainerImplemention();
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

}
