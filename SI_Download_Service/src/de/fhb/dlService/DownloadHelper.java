package de.fhb.dlService;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.Item;

/**
 * A Helper class to get the file names which are stored in SimpleDB.
 * 
 * @author Tony
 * 
 */
public class DownloadHelper {

	public List<String> getDlLinks() {
		List<String> links = new ArrayList<String>();
		// for (int i = 0; i < 200; i++) {
		// links.add("link_" + i);
		// }

		try {
			AmazonSimpleDBClient awsSimple = AwsSimpleDBCredentials.getIns(
					"AwsCredentials.properties").initCredentials();
			DloaderDB aSdb = new DloaderDB(awsSimple);

			List<Item> itemLst = aSdb.getDloaderLinks("dloaderdomain");
			for (Item item : itemLst) {
				List<Attribute> attrLst = item.getAttributes();
				for (Attribute attribute : attrLst) {
					links.add(attribute.getName());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return links;
	}
}
