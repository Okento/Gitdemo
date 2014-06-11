package todo.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AuthServlet
 */
@WebServlet("/AuthServlet")
public class AuthServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String user = request.getParameter("j_username");
		String passwd = request.getParameter("j_password");
		
		try{
			// �F�؂ɕt�����鏈��
			request.login(user, passwd);
			out.println("����ɂ��́A" + request.getRemoteUser() + "����I");
		}catch(ServletException e){
			e.printStackTrace();
			out.println("�F�؍ς݂ł��邩�A���[�U���^�p�X���[�h���Ԉ���Ă��܂��c�c");
		}
	}
}