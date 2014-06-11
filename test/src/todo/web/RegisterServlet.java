package todo.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import todo.web.TodoDAO2;
import todo.web.TodoValueObject;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/todo/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * POST���\�b�h�̂ݎ󂯕t����B
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// ���N�G�X�g�p�����[�^���󂯎��A�X�VVO�Ɋi�[���鏀��������
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String task = request.getParameter("task");
		String inputLimitdate = request.getParameter("limitdate");
		String userid = request.getParameter("userid");
		int status = Integer.parseInt(request.getParameter("status"));
		
		// VO�֊i�[����B�o�^�����ValueObject�̊���(limit)��vo�ł�inputLimit�ɂȂ�B
		TodoValueObject vo = new TodoValueObject();
		vo.setId(id);
		vo.setTitle(title);
		vo.setTask(task);
		vo.setInputLimit(inputLimitdate);
		vo.setUserid(userid);
		vo.setStatus(status);
		
		// DAO�̎擾
		TodoDAO2 dao = new TodoDAO2();
		String message = "";
		
		try{
			
			dao.getConnection();
			
			// �X�V�܂��͓o�^�������s���Bid��0�̎��͐V�K�o�^�Aid>=1�̎��͍X�V
			if(id == 0){
				dao.registerInsert(vo);
				setMessage(request, "�^�X�N�̐V�K�o�^�������������܂����B");
			}else{
				dao.registerUpdate(vo);
				setMessage(request, "�^�X�N[ " + id + " ]�̍X�V�������������܂����B");
			}
		}catch(Exception e){
			throw new ServletException(e);
		}finally{
			// DAO�̏���������������ڑ������
			dao.closeConnection();
		}
	
	String toAddr = "databasetest1991@yahoo.co.jp";
	String fromAddr = "databasetest1991@yahoo.co.jp";
	String personName = "��������";
	String subject = "TODO�Ǘ��A�v���P�[�V��������̕񍐂ł�";
	
	// �������Ƀ��[���𑗐M����
	SimpleMailSender mail = new SimpleMailSender();
	
	try {
		mail.sendMessage(toAddr, fromAddr, personName, subject, message);
	}catch(Exception e){
		e.printStackTrace();
	}
	
	// ������ʂ�\������
	RequestDispatcher rd = request.getRequestDispatcher("/complete.jsp");
	rd.forward(request, response);
	}
	
	/**
	 * JSP�ŕ\�����郁�b�Z�[�W��ݒ肷��B
	 */
	protected void setMessage(HttpServletRequest request, String message){
		request.setAttribute("message", message);
	}
}