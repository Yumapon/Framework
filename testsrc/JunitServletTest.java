import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.EasyMock;
import org.junit.Test;

import servlet.HogeHogeServlet;

public class JunitServletTest{

	@Test
	public void doGetTest() {
		//Mockリクエストの生成
		HttpServletRequest req = EasyMock.createMock(HttpServletRequest.class);
		req.setAttribute("str1", "wow");
		req.setAttribute("str2", "hoge");

		//Mockレスポンスの生成
		HttpServletResponse res = EasyMock.createMock(HttpServletResponse.class);

		//Mockを再生モードへ
		// モックオブジェクトを再生モードに切り替え
		EasyMock.replay(req);
		EasyMock.replay(res);

		HogeHogeServlet servlet = new HogeHogeServlet();

		try {
			servlet.doGet(req, res);

		} catch (ServletException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}


}
