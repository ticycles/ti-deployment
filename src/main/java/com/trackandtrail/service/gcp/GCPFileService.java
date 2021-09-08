package com.trackandtrail.service.gcp;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class GCPFileService {
	@Value("${bucket-name}")
	private String bucketName;
	@Value("${storage-baseurl}")
	private String storageBaseUrl;
	
	
	@Autowired
	private Storage storage;
	
	public String upload(MultipartFile file) throws IOException {
			String fileName = 	generateFileName(file.getOriginalFilename());
		     BlobId blobId = BlobId.of(bucketName, fileName);
			 BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
			 Blob blob = storage.create(blobInfo, file.getBytes());			
		    return getResourceUrl(fileName);				
	}
	
	
	
	public String getResourceUrl(String objectName) {
    return storageBaseUrl.concat(bucketName).concat("/").concat(objectName);		
	}
			
	
	public boolean delete(String objectName) throws IOException {			
								
//			 BlobId blobId = BlobId.of(bucketName, objectName);
//	         Blob blob = storage.get(blobId);
//	         if(blob.exists())
//	         blob.delete();
//				
			
			return storage.delete(bucketName, objectName);		
	}
	
     
    
    private String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }

    private String generateFileName(String originalFileName) {
    	 Date date = new Date();
         long time = date.getTime();
        return UUID.randomUUID().toString() + String.valueOf(time) + "." + getExtension(originalFileName);
    }
    
	
}
