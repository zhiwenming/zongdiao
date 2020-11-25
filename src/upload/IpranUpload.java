package upload;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.alibaba.fastjson.JSON;

import dac.Dac;
import model.Doc;
import model.UpFile;

/**
 * Servlet implementation class IpranUpload
 */
@WebServlet("/IpranUpload")
@MultipartConfig
public class IpranUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IpranUpload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//		request.getRequestDispatcher("/WEB-INF/jsp/ipranUpload.jsp").forward(request, response);
		doPost(request, response);
	}
	private String getFilename(Part part) {
		String contentDispositionHeader = part.getHeader("content-disposition");
		//// (contentDispositionHeader);
		// if(contentDispositionHeader.equals("form-data; name=\"file\";
		//// filename=\"\""))
		// request.getRequestDispatcher("/WEB-INF/check.jsp").forward(request,
		//// response);

		String[] elements = contentDispositionHeader.split(";");
		for (String element : elements) {
			if (element.trim().startsWith("filename")) {
				String fileName = element.substring(element.indexOf('=') + 1).trim().replace("\"", "");
				return new File(fileName).getName();
			}
		}
		return null;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Writer out = response.getWriter();
		Collection<Part> parts = new ArrayList<Part>();
		List<UpFile> upfiles = new ArrayList<UpFile>();
		System.out.println("welcom to upload");

		
//		创建文件目录
		String uriPath = "/file/" + sdf.format(new Date());// 格式化日期做目录
		String physicalPath = getServletContext().getRealPath(uriPath);
		if (!new File(physicalPath).exists()) {
			new File(physicalPath).mkdirs();
		}
//		System.out.println("AuthType"+request.getAuthType());
//		System.out.println("method"+request.getMethod());
//		System.out.println("map"+request.getParameterMap().values());
//		System.out.println("request:"+request.getContentType());
//		System.out.println("request:"+request.getMethod());
//		System.out.println("request:"+request.getLocalName());
//		System.out.println("request:"+request.getHeader(null));
		if(request.getMethod().equals("POST")) {
			if(request.getContentType().contains("multipart/form-data")) {
				//System.out.println("request:"+request.getParts());
				
				parts = request.getParts();
				for(Part part:parts) {
					System.out.println("part type:"+part.getContentType());
					if(part.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
						
					
					String fileName = getFilename(part);
					
					System.out.println("fileName:"+fileName);
//					保存文件
					physicalPath += File.separator;
					String fn = UUID.randomUUID().toString();
					physicalPath += fn;
					uriPath += "/" + fn;
					//System.out.println(physicalPath);
					
					Doc doc = new Doc();// 创建个实例，插入数据库
					doc.setName(fileName);
					// (doc.getName()+"文件名");
					doc.setFullName(uriPath);
					// (doc.getFullName()+"全名");
					doc.setUploadedTime(new Timestamp(new java.util.Date().getTime()));
//					User curUser = (User) request.getSession().getAttribute("curUser");
					String fileFullName = getServletContext().getRealPath(uriPath);
					File file = new File(fileFullName);
//					doc.setUserId(curUser.getId());
					//System.out.println("doc:"+doc);
//					Dac.getInstance().addDoc(doc);
//					System.out.println("testdac:"+Dac.getInstance().testDac());
					
					
					
					
					
					
					
					UpFile upfile = new UpFile();
//					System.out.println("size:"+Dac.getInstance().getUpfilesByName(fileName).size());
//					查询数据库里是否有该文件
					if(Dac.getInstance().getUpfilesByName(fileName).size()==0) {
						System.out.println("size:"+Dac.getInstance().getUpfilesByName(fileName).size());
						upfile.setName(fileName);
						upfile.setSize(part.getSize()+"");
						upfile.setType(part.getContentType());
						upfile.setFile("filepath");
						upfile.setZhuanye("ipran");
						upfiles.add(upfile);
						
						Dac.getInstance().addUpfiles(upfile);
						Dac.getInstance().addDoc(doc);
						part.write(physicalPath);
					}
					
					
					
					}
				}
				//System.out.println(upfiles);
			
				String bacakTOaPP = JSON.toJSONString(upfiles);
				out.write(bacakTOaPP);
				out.flush();
				request.setAttribute("bacakTOaPP", bacakTOaPP);
				//System.out.println("bacakTOaPP:"+bacakTOaPP);
				
				
			}else if(request.getContentType().contains("application/x-www-form-urlencoded")){
//				System.out.println(request.getContentType());
//				System.out.println("选中要删除的设备"+request.getParameterMap().keySet());
				String[] files = request.getParameterMap().get("file");
				System.out.println(files[0]);
				Dac.getInstance().delUpFile(files[0]);
				String delfile = Dac.getInstance().getDoc(files[0]).getFullName();
//				System.out.println("docs:"+Dac.getInstance().getDoc(files[0]));
				File file = new File(delfile);
				System.out.println(file.getAbsolutePath());
				System.out.println(file.getCanonicalPath());
//				file.delete();
			}
			
		}else {
			
//			for(int i=0;i<5;i++) {
//				UpFile upfile = new UpFile();
//				upfile.setName("name"+i+".xlsx");
//				upfile.setSize(""+i);
//				upfile.setType("text/plain");
//				upfile.setFile("filepath"+i);
//				upfiles.add(upfile);
//			}
			upfiles=Dac.getInstance().getUpfiles("ipran");
			if(upfiles!=null) {
				String bacakTOaPP=JSON.toJSONString(upfiles);
				request.setAttribute("bacakTOaPP", bacakTOaPP);
				System.out.println("bacakTOaPP:"+bacakTOaPP);
			}
			
			
			request.getRequestDispatcher("/ipranUpload.jsp").forward(request, response);
		}
		
//		System.out.println("request:"+request.getContentType());
		
		
		
		
	}

}
