package com.trackandtrail.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.service.gcp.GCPFileService;

@RestController
public class CloudStorageController {

	@Autowired
	GCPFileService gcpFileService;

	@Value("${image.size}")
	private Integer imageSize;

	@PostMapping("/uploadFile")
	public ResponseEntity<?> upload(@RequestBody MultipartFile file) {
		BaseResponseDTO resp = new BaseResponseDTO();
		Long size = file.getSize();
		if (size > imageSize * 1048576) {
			resp.setMsg("Image Size Exceeds 20 MB");			
			return new ResponseEntity<>(resp, HttpStatus.PAYLOAD_TOO_LARGE);
		}

		/*try {
			resp.setMsg(gcpFileService.upload(file));
			return new ResponseEntity<Object>(resp, HttpStatus.OK);
		} catch (IOException e) {
			resp.setMsg(e.getMessage());
			return new ResponseEntity<Object>(resp, HttpStatus.BAD_REQUEST);
		} */
		
		resp.setMsg("https://wallpapercave.com/wp/wIYKEQP.jpg");
		return new ResponseEntity<Object>(resp, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/delete/{objectUrl}")
	public ResponseEntity<?> delete(@RequestParam String objectUrl) {
		BaseResponseDTO resp = new BaseResponseDTO();		
		try {
			String fileName = StringUtils.getFilename(objectUrl);
			boolean isDeleted = gcpFileService.delete(fileName);
			resp.setMsg(isDeleted ? "Deleted Successfully" : "Could not delete the file");			
			return new ResponseEntity<Object>(resp, HttpStatus.OK);
		} catch (Exception e) {
			resp.setMsg(e.getMessage());
			return new ResponseEntity<Object>(resp, HttpStatus.BAD_REQUEST);
		}
		

	}

}
