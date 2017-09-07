package com.hebj.forecast.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

public class ReadDataFromTxt {

	public static String readData(SmbFile smbFile) throws UnsupportedEncodingException, IOException {
		String string = null;
		int length = smbFile.getContentLength();// 得到文件的大小
		byte buffer[] = new byte[length];
		SmbFileInputStream in = new SmbFileInputStream(smbFile);// 建立文件输入流
		while ((in.read(buffer)) != -1) {
			string = new String(buffer, "GBK");
		}
		in.close();

		return string;
	}
}
