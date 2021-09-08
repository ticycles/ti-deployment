package com.trackandtrail.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.trackandtrail.common.DocumentManagementDTO;
import com.trackandtrail.exception.FileStorageException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class DocumentManagementUtil {

	public static String winMobileImageUploadPath;

	@Value("${win.file.path}")
	public void setWinMobileImageUploadPath(String fp) {
		winMobileImageUploadPath = fp;
	}

	public static String linuxMobileImageUploadPath;

	@Value("${linux.file.path}")
	public void setLinuxMobileImageUploadPath(String fp) {
		linuxMobileImageUploadPath = fp;
	}

	public static String saveDocs(DocumentManagementDTO documentManagementDTO)
			throws URISyntaxException, IOException, Exception {
		String imagePath = "";
		try {

			if (null != documentManagementDTO.getDocumentString()) {
				BufferedOutputStream outputStream = null;
				byte[] imageBytes = null;
				if (documentManagementDTO.getDocumentString().startsWith("data:")) {
					imageBytes = Base64.decodeBase64(documentManagementDTO.getDocumentString().split(",")[1]);
				} else {
					imageBytes = Base64.decodeBase64(documentManagementDTO.getDocumentString());
				}
				String currTimeWithSeconds = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
				String fileName = documentManagementDTO.getDocRefCode() + "_" + currTimeWithSeconds;
				if (null != documentManagementDTO.getExtensionType()) {
					fileName = fileName.replaceAll("\\s", "") + "."
							+ documentManagementDTO.getExtensionType().toLowerCase();
				} else {
					fileName = fileName.replaceAll("\\s", "") + ".png";
				}
				imagePath = getDefaultDirectoryPathForImageSave(documentManagementDTO.getDocRefCode(), fileName,
						documentManagementDTO.getServiceName());
				File uploadFile = new File(imagePath);
				outputStream = new BufferedOutputStream(new FileOutputStream(uploadFile));
				outputStream.write(imageBytes);
				outputStream.close();

			}
			return imagePath;
		} catch (Exception e) {
			// TODO: handle exception
			log.info("< ---- DocumentManagementService.saveDocs() Exception ---->", e);
			return RequestStatusUtil.FAILURE_RESPONSE.getErrorDescription();
		}
	}

	private static String getDefaultDirectoryPathForImageSave(String docOwnerRefCode, String fileName, String serviceName) {

		Path fileStorageLocation = null;
		File imagesFolderFile = null;

		StringBuilder stringBuilder = new StringBuilder();

		if (OperatingSystemValidator.isUnix()) {
			if (linuxMobileImageUploadPath != null && linuxMobileImageUploadPath.length() > 0) {
				stringBuilder.append(linuxMobileImageUploadPath);
				stringBuilder.append(File.separator);
			}
		} else if (OperatingSystemValidator.isWindows()) {
			if (winMobileImageUploadPath != null && winMobileImageUploadPath.length() > 0) {
				stringBuilder.append(winMobileImageUploadPath);
				stringBuilder.append(File.separator);
			}
		}
		stringBuilder.append(serviceName);
		stringBuilder.append(File.separator);
		stringBuilder.append(docOwnerRefCode);
		stringBuilder.append(File.separator);
		stringBuilder.append(new SimpleDateFormat("dd_MMM_yyyy").format(new Date()));

		try {
			fileStorageLocation = Paths.get(stringBuilder.toString()).toAbsolutePath().normalize();
			if (fileStorageLocation != null) {
				imagesFolderFile = fileStorageLocation.toFile();
				if (!imagesFolderFile.exists()) {
					Files.createDirectories(fileStorageLocation);
				}
			}
		} catch (Exception ex1) {
			throw new FileStorageException(
					"Could not create the Failover directory where the uploaded files will be stored.", ex1);
		}
		if (imagesFolderFile != null) {
			return imagesFolderFile + File.separator + fileName;
		}
		return null;
	}
}
