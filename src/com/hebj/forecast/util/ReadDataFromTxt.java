package com.hebj.forecast.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

import javax.sound.sampled.AudioFormat.Encoding;

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

	public static String readDataFromTxt(String filePath, String encode) throws IOException {

		String pathname = filePath;
		File filename = new File(pathname); // 要读取以上路径的input。txt文件
		InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
		BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
		String line = "";
		line = br.readLine();
		while (line != null) {
			line = line + br.readLine(); // 一次读入一行数据
		}
		return line;
	}

	public static String readDataFromTxtByGBK(String filePath) throws IOException {

		String pathname = filePath;
		File filename = new File(pathname); // 要读取以上路径的input。txt文件
		InputStreamReader reader = new InputStreamReader(new FileInputStream(filename), "GBK"); // 建立一个输入流对象reader
		BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
		String line = "";
		String content = "";
		line = br.readLine();
		while (line != null) {
			content = content + line + "\r\n"; // 一次读入一行数据
			line = br.readLine();
		}
		return content;
	}
}
