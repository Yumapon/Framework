import org.junit.Before;
import org.junit.Test;

import db_access.ConnectionPool;
import db_access.DBAccess;
import exception.AlreadyTransactionBeganException;
import exception.notBeginTransactionException;
import transactionManager.DefaultTransactionManager;
import transactionManager.TransactionManager;

public class JunitConnectionPoolTest {

	String transactionID;

	@Before
	public void setUp() {
		//トランザクションIDの発行
		Thread currentThread = Thread.currentThread(); // 自分自身のスレッドを取得
		long threadID = currentThread.getId();
		transactionID = String.valueOf(threadID);
	}

	@Test
	public void test() throws notBeginTransactionException, AlreadyTransactionBeganException {
		//インスタンスが取得できるか確認
		ConnectionPool cp = ConnectionPool.getInstance();
		TransactionManager tm = new DefaultTransactionManager();
		tm.beginTransaction();

		@SuppressWarnings("unused")
		DBAccess dba1;
		int i = 1;

		dba1 = cp.getDBAccess("" + i);
		tm.endTransaction();

		dba1 = cp.getDBAccess("" + i);

	}
}
