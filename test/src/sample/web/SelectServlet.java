package sample.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.sun.xml.internal.bind.v2.TODO;

import sample.dao.DatabaseConnection;
import sample.dao.TodoDAO;
import sample.entity.Todo;

/**
 * Servlet implementation class InsertServlet
 */
@WebServlet("/SelectServlet")
public class SelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Connection connection = null;
		
		try {
			// JNDI�Q�ƃR���e�L�X�g���擾
			Context context = new InitialContext();
			
			// Tomcat�ŊǗ����ꂽ�f�[�^�x�[�X�ڑ���JNDI�o�R�Ŏ擾�Bjava:comp/env/ ��K���t����
			DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/dbtest");
			
			// �f�[�^�x�[�X�ڑ����擾����
			connection = ds.getConnection();
			
			
			// �ڑ�����������������ƃR���\�[���Ƀ��b�Z�[�W���o��
			log("�ڑ����J���܂���");
			
			// �������s��
			List<Todo> resultList = select(connection);
			
			// �������ʂ����N�G�X�g�����֊i�[
			request.setAttribute("list", resultList);
			
			// �������ʂ�\������list.jsp�փt�H���[�h
			request.getRequestDispatcher("/list.jsp").forward(request, response);
			
		} catch(Exception e) {
			throw new ServletException(e);
		} finally {
			try {
				connection.close();
				log("�ڑ�����܂���");
			} catch(SQLException e) {
				throw new ServletException(e);
			}
		}
	}
	
	private List<Todo> select(Connection connection) throws Exception {
		// ���s����SQL���쐬
		String sql = "INSERT INTO lecture.todo_list ( title,task,limitdate,lastupdate,userid,status " + 
		 "FROM todo_list WHERE useid like CONCAT('%',?,'%')";
		
		// �X�e�[�g�����g(SQL���i�[�E���s����R���e�i)���擾�B
		PreparedStatement statement = connection.prepareStatement(sql);
			
		// PreparedStatement�̃v���[�X�z���_�[�ɕϐ������ԂɊi�[����B
		statement.setString(1, "USER");
		ResultSet rs = statement.executeQuery();
		
		List<Todo> resultList = new ArrayList<Todo>();
		
		while(rs.next()){
			// �������ʂP���R�[�h�̓��e���擾����
			Todo todo = new Todo();
			todo.setTitle(rs.getString("title"));
			todo.setTask(rs.getString("task"));
			todo.setLimitdate(rs.getString("limitdate"));
			todo.setLastupdate(rs.getString("lastupdate"));
			todo.setUserid(rs.getString("userid"));
			todo.setStatus(rs.getString("status"));
			
			// �������ʂ�List�֊i�[
			resultList.add(todo);
		}
		
		return resultList;
	}
}