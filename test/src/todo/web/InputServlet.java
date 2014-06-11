package todo.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * �V�K�o�^�̓��͉�ʂ�\������
 */
@WebServlet("/todo/input")
public class InputServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// vo�̍쐬
		TodoValueObject vo = new TodoValueObject();
		
		// �V�K�o�^�ł��邱�Ƃ𔻕ʂ��邽��id=0�Ƃ��Ă���B
		vo.setId(0);
		
		// �^�X�N�P����vo�����N�G�X�g�����փo�C���h
		request.setAttribute("vo", vo);
		
		// �ڍ׉�ʂ�\������
		RequestDispatcher rd = request.getRequestDispatcher("/detail.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// POST���M����GET�Ɠ����������s��
		doGet(request, response);
	}
}