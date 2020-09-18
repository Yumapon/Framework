import org.junit.Test;

import container.DBConfig;
import container.EnvironmentConfigReader;

public class JunitEnvironmentConfigReaderTest {

	@Test
	public void testRead() {

		EnvironmentConfigReader ecr = new EnvironmentConfigReader();
		DBConfig dbc = ecr.read();
		System.out.println(dbc.getDriver());
		System.out.println(dbc.getUrl());
		System.out.println(dbc.getUser());
		System.out.println(dbc.getPassword());
		System.out.println(dbc.getNumberOfAccess());

		String str = "DBProfile.yaml";
		dbc = ecr.read(str);
		System.out.println(dbc.getDriver());
		System.out.println(dbc.getUrl());
		System.out.println(dbc.getUser());
		System.out.println(dbc.getPassword());
		System.out.println(dbc.getNumberOfAccess());


	}

}
