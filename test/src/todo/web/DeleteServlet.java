package todo.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import todo.web.TodoDAO2;

/**
 * �폜�������s��
 */
@WebServlet("/todo/delete")
public class DeleteServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// DAO�̎擾
		TodoDAO2 dao = new TodoDAO2();
		
		// ���N�G�X�g�p�����[�^����I�������^�X�Nid���擾����
		String paramId = request.getParameter("id");
		
		try{
			
			dao.getConnection();
			
			// int�֕ϊ���NumberFormatException����������\������B�`�F�b�N�ΏہB
			int id = Integer.parseInt(paramId);
			
			// String����int�֕ϊ����Adao�ŏ������s���B�Ώۂ̃^�X�N���P���폜���A��������ƂP���Ԃ����B
			int result = dao.delete(id);
		}catch(Exception e){
			throw new ServletException(e);
		}finally{
			// DAO�̏���������������ڑ������
			dao.closeConnection();
		}
		
		setMessage(request, "�^�X�N[ " + paramId + "]�̍폜�������������܂����B");
		
		// �ꗗ��ʂփt�H���[�h����
		RequestDispatcher rd = request.getRequestDispatcher("SearchServlet");
		rd.forward(request, response);
	}
			
	protected void setMessage(HttpServletRequest request, String message) {
		request.setAttribute("message", message);
	}
}
