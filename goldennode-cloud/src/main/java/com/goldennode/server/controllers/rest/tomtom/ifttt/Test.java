package com.goldennode.server.controllers.rest.tomtom.ifttt;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

@RestController
@RequestMapping(value = { "/tomtom/ifttt/v1/test" })
@CrossOrigin(origins = "*")
public class Test {

	@RequestMapping(value = { "/setup" }, method = RequestMethod.POST, headers = {
			"Accept=application/json" }, produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> setup(HttpServletRequest request) throws IFTTTRestException {
		if (request.getHeader("IFTTT-Channel-Key") == null || !request.getHeader("IFTTT-Channel-Key")
				.equals(request.getServletContext().getInitParameter("iftttKey"))) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}

		InputStream objectData = null;
		try {

			AmazonS3 s3Client = new AmazonS3Client();
			Region euWest2 = com.amazonaws.regions.Region.getRegion(Regions.EU_WEST_2);
			s3Client.setRegion(euWest2);
			GetObjectRequest rangeObjectRequest = new GetObjectRequest("thingabled-scripts", "tomtomiftttservicetest");
			// rangeObjectRequest.setRange(0, 10); // retrieve 1st 11 bytes.
			S3Object objectPortion = s3Client.getObject(rangeObjectRequest);
			objectData = objectPortion.getObjectContent();
			String content = IOUtils.toString(objectData);
			return new ResponseEntity<String>(content, HttpStatus.OK);
			// Process the objectData stream.

		} catch (Exception e) {
			throw new IFTTTRestException("Test file not found > " + e.toString());
		} finally {
			if (objectData != null)
				try {
					objectData.close();
				} catch (IOException e) {
				}
		}
	}
}
