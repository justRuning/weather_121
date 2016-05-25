package com.hebj.forecast.util;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

public class ReadDataFromTxt {

	public static String readData(SmbFile smbFile) {
		String string = null;
		try {
			int length = smbFile.getContentLength();// 得到文件的大小
			byte buffer[] = new byte[length];
			SmbFileInputStream in = new SmbFileInputStream(smbFile);// 建立文件输入流
			while ((in.read(buffer)) != -1) {
				string = new String(buffer, "GBK");
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return string;
	}
}
