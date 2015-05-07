package com.simelabs.kmb.managers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import android.util.Log;

/**
 * 
 * @author Jovita
 * 
 *         This is to download files from FTP Server
 */
public class FTPUtil {

	/**
	 * Download a whole directory from a FTP server.
	 * 
	 * @param ftpClient
	 *            an instance of org.apache.commons.net.ftp.FTPClient class.
	 * @param parentDir
	 *            Path of the parent directory of the current directory being
	 *            downloaded.
	 * @param currentDir
	 *            Path of the current directory being downloaded.
	 * @param saveDir
	 *            path of directory where the whole remote directory will be
	 *            downloaded and saved.
	 * @throws IOException
	 *             if any network or IO error occurred.
	 */
	public static void downloadDirectory(FTPClient ftpClient, String parentDir,
			String currentDir, String saveDir) throws IOException {
		String dirToList = parentDir;
		/*
		 * if (!currentDir.equals("")) { dirToList += "/" + currentDir; }
		 */

		// FTPFile[] subFiles = ftpClient.listFiles(dirToList);

		OutputStream outputStream = null;

		boolean success = false;

		try {

			outputStream = new BufferedOutputStream(new FileOutputStream(
					saveDir));

			success = ftpClient.retrieveFile(parentDir, outputStream);

		} finally {

			if (outputStream != null) {

				outputStream.close();

			}

		}

	}

	/**
	 * Download a single file from the FTP server
	 * 
	 * @param ftpClient
	 *            an instance of org.apache.commons.net.ftp.FTPClient class.
	 * @param remoteFilePath
	 *            path of the file on the server
	 * @param savePath
	 *            path of directory where the file will be stored
	 * @return true if the file was downloaded successfully, false otherwise
	 * @throws IOException
	 *             if any network or IO error occurred.
	 */
	public static boolean downloadSingleFile(FTPClient ftpClient,
			String remoteFilePath, String savePath) throws IOException {
		File downloadFile = new File(savePath);

		File parentDir = downloadFile.getParentFile();
		if (!parentDir.exists()) {
			parentDir.mkdir();
		}

		OutputStream outputStream = new BufferedOutputStream(
				new FileOutputStream(downloadFile));
		try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			return ftpClient.retrieveFile(remoteFilePath, outputStream);
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}
}