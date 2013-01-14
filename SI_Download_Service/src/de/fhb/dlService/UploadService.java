package de.fhb.dlService;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.amazonaws.services.s3.AmazonS3;

/**
 * Servlet implementation class UploadService
 */

public class UploadService extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadService() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// file process
		try {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);

			for (FileItem item : items) {
				if (item.isFormField()) {
				} else {
					String fieldname = item.getFieldName();
					String filename = item.getName();
					InputStream filecontent = item.getInputStream();

					AmazonS3 awsS3 = AwsS3Credentials.getIns(
							"AwsCredentials.properties").initCredentials();
					BucketUtil bucket = new BucketUtil(awsS3);
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}

		// response write
		Writer writer = response.getWriter();
		writer.write("Erfolgreich Hochgeladen.");
		writer.write("<a href=\"/SI_Download_Service/\">Visit W3Schools</a>");

		// response.sendRedirect(response.encodeRedirectURL("/SI_Download_Service/"));

		// RequestDispatcher rd = getServletContext().getRequestDispatcher("/");
		// rd.forward(request, response);
	}

}