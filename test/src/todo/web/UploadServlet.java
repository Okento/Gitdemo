package todo.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet( urlPatterns={"/todo/upload"})
@MultipartConfig(location="C:/tmp/")
public class UploadServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// <INPUT type="file" name="uploadfile">����Multipart�`���̃A�b�v���[�h�R���e���c�̓��e���擾
		Part part = request.getPart("uploadfile");
		
		// �A�b�v���[�h���ꂽ�R���e���c(Part)����t�@�C����������������������͂��A�擾����B
		String filename = null;
		for (String cd : part.getHeader("Content-Disposition").split(";")) { 
			cd = cd.trim();
			
			if ( cd.startsWith("filename")) {
				// �t�@�C������=�̉E���ȍ~�̕�����B���������p���ɂ���Ă̓_�u���N�H�[�e�[�V�������܂܂�Ă���̂ŁA��菜���K�v������B
				filename = cd.substring(cd.indexOf("=") + 1).trim().replace("\"", "");
				break;
			}
		}
		
		// ���N�G�X�g�p�����[�^��id���擾����B
		String idStr = request.getParameter("id");
		log("idStr:" + idStr);
		int id = Integer.parseInt(idStr);
		
		// �A�b�v���[�h�����t�@�C���������o��
		String message = null;
		if ( filename != null ){
			
			//�A�b�v���[�h���ꂽ�t�@�C�����́AOS�ˑ��̃t�@�C���p�X�Ȃǂ��܂�ł���̂Œu������B����/�ɒu�����A���̌�t�@�C�����̂ݒ��o����B
			filename = filename.replace("\\", "/");
			
			int pos = filename.lastIndexOf("/");
			if(pos >= 0){
				filename = filename.substring(pos+1);
			}
			part.write(filename);
			
			// �A�b�v���[�h������������̓f�[�^�x�[�X�ɓo�^����B�ۑ�����̂̓t�@�C�����̂݁B���S�p�X�͊܂܂Ȃ��B
			TodoValueObject vo = new TodoValueObject();
			vo.setId(id);
			vo.setFilename(filename);
			
			TodoDAO2 dao = new TodoDAO2();
			
			try{
				
				dao.getConnection();
				
				int result = dao.updateUploadInfo(vo);
			}catch(Exception e){
				throw new ServletException(e);
			}finally{
				// DAO�̏���������������ڑ������
				dao.closeConnection();
			}
			
			message = "[ " + filename + " ]�̃A�b�v���[�h���������܂���";
		}else{
			message = "�A�b�v���[�h�����s���܂���";
		}
		
		request.setAttribute("message", message);
		
		// �ڍ׉�ʂ��ĕ\������
		request.getRequestDispatcher("/todo/detail?id=" + id).forward(request,  response);
		}
}