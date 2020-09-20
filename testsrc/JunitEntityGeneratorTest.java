import org.junit.Test;

import entityCreater.generator.EntityGenerator;

public class JunitEntityGeneratorTest {

	@Test
	public void test() {
		EntityGenerator eg = new EntityGenerator("/Applications/Eclipse_2019-12.app/Contents/workspace/DIcontainer/src/entityCreater/entity");
		eg.generateEntity();
	}

}
