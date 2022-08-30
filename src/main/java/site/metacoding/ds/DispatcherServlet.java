package site.metacoding.ds;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet{
// extends HttpServlet 이것만으로소 소켓 구현 완료
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);	
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);	
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// System.out.println("doProcess 요청됨");
		
		String httpMethod = req.getMethod();
		// System.out.println(httpMethod);
		
		String identifier = req.getRequestURI();
		// System.out.println(identifier);
		
		// 공통 로직 처리
		UserController c = new UserController();

		Method[] methodList = c.getClass().getDeclaredMethods();
		for (Method method : methodList) {
			// System.out.println(method.getName()); 
			Annotation annotation = 
					method.getDeclaredAnnotation(RequestMapping.class);
			
			RequestMapping requestMapping = (RequestMapping) annotation; // 다운캐스팅
			try {
				// System.out.println(requestMapping.value());
				if(identifier.equals(requestMapping.value())) {
					method.invoke(c);
					// c라는 heap 공간에 있는 메서드를 때리는 것이다. (내가 메서드의 이름을 몰라도 된다)
				}
			} catch (Exception e) {
				System.out.println(method.getName()+"은 어노테이션이 없습니다.");
			}

			
	
		}

	}
}
