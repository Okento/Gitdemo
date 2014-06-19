package test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import test.TBook;
import test.MySQLDAO;
import test.UserTable;

public class BookDB extends MySQLDAO{
	public List<TBook> getListbook()throws Exception{
		
		// returnList��ArrayList�^�ɂ���
		List<TBook> returnList = new ArrayList<TBook>();
		
		// ���s����SQL�����L�q
		String sql = "SELECT id, isbn, title, author, publisher, price " + 
		" FROM t_book ";
		
		// �v���y�A�X�e�[�g�����g���擾���A���sSQL��n��
		PreparedStatement statement = connection.prepareStatement(sql);
		
		// SQL�����s���Ă��̌��ʂ��擾����B
		ResultSet rs = statement.executeQuery();
		
		// �������ʂ̍s�����t�F�b�`���s���A�擾���ʂ�TBook�֊i�[����
		while (rs.next()) {
			TBook tb = new TBook();
			
			// �N�G���[���ʂ�tb�֊i�[(���炩���߃N�G���[���ʂ�tb�̕ϐ����͈�v�����Ă���)
			tb.setId(rs.getInt("id"));
			tb.setIsbn(rs.getString("isbn"));
			tb.setTitle(rs.getString("title"));
			tb.setAuthor(rs.getString("author"));
			tb.setPublisher(rs.getString("publisher"));
			tb.setPrice(rs.getInt("price"));
			
			returnList.add(tb);
		}
		
		return returnList;
	}
	
	
	public TBook findBookByISBN(String inISBN) throws Exception{
		
		// ���s����SQL�����L�q
		String sql = "SELECT * FROM t_book WHERE isbn = ?";
			
		// �v���y�A�X�e�[�g�����g���擾���A���sSQL��n��
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1, inISBN);		// 42�s�ڂƂ������蓝�ꂳ����B
		
		// SQL�����s���Ă��̌��ʂ��擾����B
		ResultSet rs = statement.executeQuery();
						
		// �������ʂ̍s�����t�F�b�`���s���A�擾���ʂ�TBook�֊i�[����
		TBook tb = new TBook();
		while (rs.next()) {
			
			// �N�G���[���ʂ�tb�֊i�[(���炩���߃N�G���[���ʂ�tb�̕ϐ����͈�v�����Ă���)
			tb.setId(rs.getInt("id"));
			tb.setIsbn(rs.getString("isbn"));
			tb.setTitle(rs.getString("title"));
			tb.setAuthor(rs.getString("author"));
			tb.setPublisher(rs.getString("publisher"));
			tb.setPrice(rs.getInt("price"));
			
		}
		return tb;
	}
	
	public int total(List<String> cart) throws Exception{
		
		int returnsum = 0;
		String sqlcart = "";
		
		// �ʂ�('isbn',)�����
		for(String isbn :cart){
			sqlcart += "'" + isbn + "'" + ",";
		}
		
		// ���s����SQL�����L�q
		// �J���}�ŏI���ƃG���[���o��̂ŁA1���͂��ށB
		String sql = "select sum(price) from t_book where isbn in (" + sqlcart + "1);";
		
		// �v���y�A�X�e�[�g�����g���擾���A���sSQL��n��
		PreparedStatement statement = connection.prepareStatement(sql);
		
		// SQL�����s���Ă��̌��ʂ��擾����B
		ResultSet rs = statement.executeQuery();
		
		connection = getConnection();
		
		// �������ʂ̍s�����t�F�b�`���s���A�擾���ʂ�TBook�֊i�[����
		while (rs.next()) {
			
			// ���[�v�̒��ŏW�߂�price��sum��returnsum�ɕԂ�
			returnsum = rs.getInt("sum(price)");
			
		}
		return returnsum;
		
	}
	
	public int registerInsert(UserTable ut) throws Exception {
		String sql = "INSERT INTO usr (uid,passwd,uname) " 
				+ "VALUES (?,?,?) ";
		int result = 0;
		
		// �v���y�A�X�e�[�g�����g�擾���A���sSQL��n��
		try{
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, ut.getUid());
			statement.setString(2, ut.getPasswd());
			statement.setString(3, ut.getUname());
			
			result = statement.executeUpdate();
			
			// �R�~�b�g���s��
			connection.commit();
		}catch (Exception e){
			// ���[���o�b�N���s���A�X���[������O��DAO����E�o����
			connection.rollback();
			throw e;
		}
		
		return result;
	}
	
	public int ItemregisterInsert(TBook tb) throws Exception {
		String sql = "INSERT INTO t_book (isbn,title,author,publisher,price) " 
				+ "VALUES (?,?,?,?,?) ";
		int result = 0;
		
		// �v���y�A�X�e�[�g�����g�擾���A���sSQL��n��
		try{
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, tb.getIsbn());
			statement.setString(2, tb.getTitle());
			statement.setString(3, tb.getAuthor());
			statement.setString(4, tb.getPublisher());
			statement.setInt(5, tb.getPrice());
			
			result = statement.executeUpdate();
			
			// �R�~�b�g���s��
			connection.commit();
		}catch (Exception e){
			// ���[���o�b�N���s���A�X���[������O��DAO����E�o����
			connection.rollback();
			throw e;
		}
		
		return result;
	}	
}