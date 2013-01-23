package de.fhb.dlService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.amazonaws.services.s3.AmazonS3;

/**
 * Servlet implementation class DownloadService. This class extracts the
 * filename of the url, initiates a connection to s3 service and gets the file
 * with the extracted file name from there. The file is provided to the client
 * as a download.
 */
@WebServlet("/DownloadService/*")
public class DownloadService extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DownloadService() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("application/force-download");
			String key = request.getPathInfo();
			key = key.substring(1);

			AmazonS3 awsS3 = AwsS3Credentials.getIns(
					"AwsCredentials.properties").initCredentials();
			BucketUtil bucket = new BucketUtil(awsS3);

			InputStream in = bucket.downloadFromBucket("dloaderbucket", key);

			OutputStream out = response.getOutputStream();

			IOUtils.copy(in, out);
		} catch (Exception e) {
			response.setContentType("text/html");
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/error.html");
			rd.include(request, response);
		}
	}

}
