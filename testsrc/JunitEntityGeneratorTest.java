import org.junit.Test;

import entityCreater.generator.EntityGenerator;
import entityCreater.generator.EntityGeneratorImplements;

public class JunitEntityGeneratorTest {

	@Test
	public void test() {
		EntityGenerator eg = (EntityGenerator) new EntityGeneratorImplements("/Applications/Eclipse_2019-12.app/Contents/workspace/DIcontainer/src/entityCreater/entity");
		eg.generateEntity();
	}

}
