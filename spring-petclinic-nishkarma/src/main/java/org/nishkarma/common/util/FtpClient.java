package org.nishkarma.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FtpClient {
	
	public boolean ftpUpload(String localFilePath, String remoteFilePath, String fileName, String FTP_URL, String FTP_PORT,
			String FTP_ID, String FTP_PWD) throws Exception {

		FTPClient ftp = null; // FTP Client 객체
		FileInputStream fis = null; // File Input Stream
		File uploadfile = new File(localFilePath + "//" + fileName); // File 객체

		boolean result = false;
		try {
			ftp = new FTPClient(); // FTP Client 객체 생성
			ftp.setControlEncoding("UTF-8"); // 문자 코드를 UTF-8로 인코딩
			ftp.connect(FTP_URL, Integer.parseInt(FTP_PORT)); // 서버접속 " "안에 서버
																// 주소 입력 또는
																// "서버주소", 포트번호
			ftp.login(FTP_ID, FTP_PWD); // FTP 로그인 ID, PASSWORLD 입력
			ftp.enterLocalPassiveMode(); // Passive Mode 접속일때

			String[] path = remoteFilePath.split("/");
			for (int i = 0; i < path.length; i++) {
				ftp.makeDirectory(path[i]); // 작업 디렉토리 생성
				ftp.changeWorkingDirectory(path[i]); // 작업 디렉토리 변경
			}

			ftp.setFileType(FTP.BINARY_FILE_TYPE); // 업로드 파일 타입 셋팅
			ftp.setBufferSize(1024*1024);
			
			try {
				System.out.println(uploadfile);
				fis = new FileInputStream(uploadfile); // 업로드할 File 생성
				boolean isSuccess = ftp.storeFile(fileName, fis); // File 업로드

				if (isSuccess) {
					result = true; // 성공
				}
			} catch (IOException ex) {
				System.out.println("IO Exception : " + ex.getMessage());
			} finally {
				if (fis != null) {
					try {
						fis.close(); // Stream 닫기
						return result;

					} catch (IOException ex) {
						System.out.println("IO Exception : " + ex.getMessage());
					}
				}
			}
			ftp.logout(); // FTP Log Out
		} catch (IOException e) {
			System.out.println("IO:" + e.getMessage());
		} finally {
			if (ftp != null && ftp.isConnected()) {
				try {
					ftp.disconnect(); // 접속 끊기
					return result;
				} catch (IOException e) {
					System.out.println("IO Exception : " + e.getMessage());
				}
			}
		}
		return result;
	}

	public boolean ftpDelete(String remoteFilePath, String fileName, String FTP_URL, String FTP_PORT,
			String FTP_ID, String FTP_PWD) throws Exception {

		FTPClient ftp = null; // FTP Client 객체
		FileInputStream fis = null; // File Input Stream

		boolean result = false;
		try {
			ftp = new FTPClient(); // FTP Client 객체 생성
			ftp.setControlEncoding("UTF-8"); // 문자 코드를 UTF-8로 인코딩
			ftp.connect(FTP_URL, Integer.parseInt(FTP_PORT)); // 서버접속 " "안에 서버
																// 주소 입력 또는
																// "서버주소", 포트번호
			ftp.login(FTP_ID, FTP_PWD); // FTP 로그인 ID, PASSWORLD 입력
			ftp.enterLocalPassiveMode(); // Passive Mode 접속일때
			String[] path = remoteFilePath.split("/");
			for (int i = 0; i < path.length; i++) {
				ftp.makeDirectory(path[i]); // 작업 디렉토리 생성
				ftp.changeWorkingDirectory(path[i]); // 작업 디렉토리 변경
			}
			ftp.setFileType(FTP.BINARY_FILE_TYPE); // 업로드 파일 타입 셋팅

			try {
				boolean isSuccess = ftp.deleteFile(fileName);// 파일삭제

				if (isSuccess) {
					result = true; // 성공
				}
			} catch (IOException ex) {
				System.out.println("IO Exception : " + ex.getMessage());
			} finally {
				if (fis != null) {
					try {
						fis.close(); // Stream 닫기
						return result;

					} catch (IOException ex) {
						System.out.println("IO Exception : " + ex.getMessage());
					}
				}
			}
			ftp.logout(); // FTP Log Out
		} catch (IOException e) {
			System.out.println("IO:" + e.getMessage());
		} finally {
			if (ftp != null && ftp.isConnected()) {
				try {
					ftp.disconnect(); // 접속 끊기
					return result;
				} catch (IOException e) {
					System.out.println("IO Exception : " + e.getMessage());
				}
			}
		}
		return result;
	}
}
