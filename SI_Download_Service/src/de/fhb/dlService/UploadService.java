package de.fhb.dlService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import au.com.bytecode.opencsv.CSVReader;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.sqs.AmazonSQS;

/**
 * Servlet implementation class UploadService
 */
@WebServlet("/UploadService")
public class UploadService extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

			// init sqs
			AmazonSQS aSqs = AwsSQSCredentials.getIns(
					"AwsCredentials.properties").initSqsCredentials();
			SqsUtil sqs = new SqsUtil(aSqs);
			sqs.createQueue("Links2013");

			for (FileItem item : items) {
				if (item.isFormField()) {
				} else {
					String fieldname = item.getFieldName();
					String filename = item.getName();
					InputStream filecontent = item.getInputStream();

					InputStreamReader inReader = new InputStreamReader(
							filecontent);
					CSVReader csvReader = new CSVReader(inReader);

					String[] nextLine;
					while ((nextLine = csvReader.readNext()) != null) {
						sqs.sendSqsMessage("Links2013", nextLine[0]);
					}
				}
			}
		} catch (Exception e) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/error.html");
			rd.include(request, response);
		}

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/succes.html");
		rd.include(request, response);
	}
}
