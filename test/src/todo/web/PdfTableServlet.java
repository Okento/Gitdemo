package todo.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Servlet implementation class PdfBasicServlet
 */
@WebServlet("/PdfTableServlet.pdf")
public class PdfTableServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try{
			// �h�L�������g�{�̂𐶐�(�y�[�W�T�C�Y�ƃ}�[�W���̐ݒ�)
			Document doc = new Document(PageSize.A4, 50, 20, 270, 20);
			
			// �h�L�������g�̏o�͂��`
			PdfWriter writer = PdfWriter.getInstance(doc, response.getOutputStream());
			
			// �Z�L�����e�B�֘A�̐ݒ���s��
			writer.setEncryption("".getBytes(), "".getBytes(), PdfWriter.ALLOW_PRINTING | PdfWriter.ALLOW_COPY, PdfWriter.STANDARD_ENCRYPTION_128);
			
			// �h�L�������g���I�[�v��
			doc.open();
			
			// �t�H���g�̒�`
			Font fTitle = new Font(BaseFont.createFont
					("HeiseiKakuGo-W5","UniJIS-UCS2-H",BaseFont.NOT_EMBEDDED), 11, Font.BOLD);
			Font fData = new Font(BaseFont.createFont
					("HeiseiKakuGo-W5","UniJIS-UCS2-H",BaseFont.NOT_EMBEDDED), 10, Font.NORMAL);
			
			// ��4�̃e�[�u�����`
			PdfPTable tbl = new PdfPTable(4);
			
			// �e�Z���̕�(����)���`
			int[] ws = {55, 10, 20, 15};
			tbl.setWidths(ws);
			
			// �^�C�g���s���`
			String[] headers = {"����", "���i", "�o�Ŏ�", "���s��"};
			for(int i = 0; i < headers.length; i++){
				PdfPCell c = new PdfPCell(new Phrase(headers[i], fTitle));
				c.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
				tbl.addCell(c);
			}
			tbl.setHeaderRows(1);
			
			// book�e�[�u�����炷�ׂẴ��R�[�h���擾
			Connection db = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			try{
				Context context = new InitialContext();
				DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/dbtest");
				db = ds.getConnection();
				ps = db.prepareStatement("SELECT title, price, publish, published FROM book ORDER BY published DESC");
				rs = ps.executeQuery();
				ResultSetMetaData meta = rs.getMetaData();
				
				// ���ʃZ�b�g������o�������R�[�h���e�[�u���ɃZ�b�g
				while(rs.next()){
					for(int i = 1; i <= meta.getColumnCount(); i++){
					PdfPCell c = new PdfPCell(new Phrase(rs.getString(i), fData));
					tbl.addCell(c);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			
			// �o���オ�����e�[�u���𕶏��ɖ��ߍ���
			doc.add(tbl);
			// �h�L�������g���N���[�Y
			doc.close();
		}catch(DocumentException e){
			throw new ServletException(e);
		}
	}
}