import java.util.ArrayList;

import org.junit.Test;

import entityCreater.info.EntityInfo;
import entityCreater.reader.DBReaderImplements;

public class JunitDBReaderTest {

	@Test
	public void test() {
		DBReaderImplements dbr = new DBReaderImplements();
		@SuppressWarnings("static-access")
		ArrayList<EntityInfo> list = dbr.read();

		System.out.println(list.size());
		EntityInfo ei1 = list.get(0);
		EntityInfo ei2 = list.get(1);

		System.out.println("**********************");
		System.out.println("TableName:" + ei1.getTableName());
		System.out.println("Primary Key:" + ei1.getId());
		for(String[] s1 : ei1.getColumns()) {
			System.out.println("Columnの型:" + s1[0]);
			System.out.println("ColumnName:" + s1[1]);
		}

		System.out.println("**********************");
		System.out.println("TableName" + ei2.getTableName());
		System.out.println("Primary Key:" + ei2.getId());
		for(String[] s2 : ei2.getColumns()) {
			System.out.println("Columnの型" + s2[0]);
			System.out.println("ColumnName:" + s2[1]);
		}

	}

}
