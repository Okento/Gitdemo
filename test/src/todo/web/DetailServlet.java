package todo.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DetailServlet
 */
@WebServlet("/todo/detail")
public class DetailServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// DAO�̎擾
		TodoDAO2 dao = new TodoDAO2();
		
		// ���N�G�X�g�p�����[�^����I�������^�X�Nid���擾����
		String paramId = request.getParameter("id");
		
		// String����int�֕ϊ����Adao�ŏ������s���B�X�V�Ώۂ̃^�X�N���P���擾����B
		TodoValueObject vo;
		try {
			
			dao.getConnection();
			
			// int�֕ϊ���NumberFormatException����������\������B�`�F�b�N�ΏہB
			int id = Integer.parseInt(paramId);
			
			// �^�X�N�ڍ׌��ʂ��擾
			vo = dao.detail(id);
			
		}catch(Exception e) {
			throw new ServletException(e);
		}finally{
			// DAO�̏���������������ڑ������
			dao.closeConnection();
		}
		
		// �^�X�N�P����vo�����N�G�X�g�����փo�C���h
		request.setAttribute("vo", vo);
		
		// ��ʂ�Ԃ��B�����ꗗ��\������
		RequestDispatcher rd = request.getRequestDispatcher("/detail.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// POST���M����GET�Ɠ����������s��
		doGet(request, response);
	}
}