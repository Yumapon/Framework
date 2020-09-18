import org.junit.Before;
import org.junit.Test;

import db_access.ConnectionPool;
import db_access.DBAccess;
import exception.DoNotHaveDBAccessException;
import exception.NoDBAccessException;

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
	public void test() {
		//インスタンスが取得できるか確認
		ConnectionPool cp = ConnectionPool.getInstance();
		//DBAccessが取得できるかのテスト
		try {
			@SuppressWarnings("unused")
			DBAccess  dba1;
			int i = 1;
			while(i < 4) {
				dba1 = cp.getDBAccess("" + i);
				i++;
			}
			i = 1;
			while(i < 4) {
				try {
					cp.returnDBAccess("" + i);
				} catch (DoNotHaveDBAccessException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				i++;
			}
			i = 1;
			while(i < 4) {
				dba1 = cp.getDBAccess("" + i);
				i++;
			}
			i = 1;
			while(i < 4) {
				try {
					cp.returnDBAccess("" + i);
				} catch (DoNotHaveDBAccessException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				i++;
			}
			i = 1;
			while(i < 4) {
				try {
					cp.returnDBAccess("" + i);
				} catch (DoNotHaveDBAccessException e) {
					System.out.println("DoNotHaveDBAccessExceptionが発生しました。");
				}
				i++;
			}
			i = 1;
			while(i < 100) {
				dba1 = cp.getDBAccess("" + i);
				try {
					cp.returnDBAccess("" + i);
				} catch (DoNotHaveDBAccessException e) {
					System.out.println("DoNotHaveDBAccessExceptionが発生しました。");
				}
				i++;
			}


		} catch (NoDBAccessException e) {
			System.out.print("NoDBAccessExceptionが発生しました。");
		}

	}

}
