package todo.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class InsertServlet
 */
@WebServlet("/InsertServletT")
public class InsertServletT extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Connection connection = null;
		
		try {
			// JNDI�Q�ƃR���e�L�X�g���擾
			InitialContext initCtx = new InitialContext();
			
			// Tomcat�ŊǗ����ꂽ�f�[�^�x�[�X�ڑ���JNDI�o�R�Ŏ擾�Bjava:comp/env/ ��K���t����
			DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/dbtest");
			
			// �f�[�^�x�[�X�ڑ����擾����
			connection = ds.getConnection();
			
			// �����R�~�b�g��false�ɂ���
			connection.setAutoCommit(false);
			
			// �ڑ�����������������ƃR���\�[���Ƀ��b�Z�[�W���o��
			log("�ڑ����J���܂���");
			
			// �o�^�������s��
			insert(connection);
			
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
		
		request.getRequestDispatcher("/complete.html").forward(request,response);

	}
	
	private void insert(Connection connection) throws Exception {
		// ���s����SQL���쐬
		String sql = "INSERT INTO play.todo_list ( title,task,limitdate,lastupdate,userid,status " + 
		") VALUES (?,?,?, now() ,?,?);";
		
		try{
			// �X�e�[�g�����g(SQL���i�[�E���s����R���e�i)���擾�B
			PreparedStatement statement = connection.prepareStatement(sql);
			
			// PreparedStatement�̃v���[�X�z���_�[�ɕϐ������ԂɊi�[����B
			statement.setString(1, "�u�K���");
			statement.setString(2, "�u�K��̃X���C�h�쐬�A�z�z�\�[�X�R�[�h�̏���");
			statement.setString(3, "2012-06-12");
			statement.setString(4, "USER99");
			statement.setInt(5, 0);
			
			// �X�V�n�����executeUpdate()�𗘗p���A���̑Ώی������擾���邱�Ƃ��ł���B
			int count = statement.executeUpdate();
			log("�P�ڂ̒ǉ�:" + count);
			
			// PreparedStatement�̃v���[�X�z���_�[�ɕϐ������ԂɊi�[����B
			statement.setString(1, "�u�K��");
			statement.setString(2, "2012�N7���̍u�K������{");
			statement.setString(3, "2017-07-01");
			statement.setString(4, "USER99");
			statement.setInt(5, 0);
			
			// �X�V�n�����executeUpdate()�𗘗p���A���̑Ώی������擾���邱�Ƃ��ł���B
			count = statement.executeUpdate();
			log("�Q�ڂ̒ǉ�:" + count);
				
			// �g�����U�N�V�������R�~�b�g����B
			connection.commit();
		} catch(Exception e) {
			connection.rollback();
			throw e;
		}
	}
}