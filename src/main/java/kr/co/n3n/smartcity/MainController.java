package kr.co.n3n.smartcity;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	@RequestMapping("/")
	public void goMain(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		response.sendRedirect("/index.html");		
	}

}
