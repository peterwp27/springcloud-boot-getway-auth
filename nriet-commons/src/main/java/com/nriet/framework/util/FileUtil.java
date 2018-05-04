package com.nriet.framework.util;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

/**
 * 文件访问及其他辅助类
 * @version 1.0
 * @author 王文彬
 */
public class FileUtil {
	protected FileUtil() {}
	/**
	 * 以字节形式读取二进制文件或文本文件
	 * @param path 文件绝对路径
	 * @return
	 * @throws IOException 
	 */
	public static byte[] readAsByte(String path) throws IOException {
		byte[] buf;
		buf = localReadAsByte(path);
		return buf;
	}

	/**
	 * 以指定字符编码读取文本文件
	 * @param path 文件绝对路径
	 * @param encoding 字符编码
	 * @return
	 * @throws IOException 
	 */
	public static String read(String path, String encoding) throws IOException {
		String content = "";
		content = localRead(path, encoding);
		return content;
	}

	/**
	 *  以指定字符编码读取文本文件
	 * @param file 文件
	 * @param encoding 字符编码
	 * @return
	 * @throws IOException 
	 */
	public static String read(File file, String encoding) throws IOException {
		String content = "";
		content = localRead(file, encoding);
		return content;
	}
	/**
	 * 读取本地文件内容(字节形式)
	 * 
	 * @param path
	 *            文件路径
	 * @return 文件内容
	 * @throws IOException 
	 */
	private static byte[] localReadAsByte(String path) throws IOException {
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(path));
			int bufferSize = bis.available();
			byte[] buf = new byte[bufferSize];
			bis.read(buf);
			bis.close();
			return buf;
	}
	/**
	 * 读取本地文件内容(字节形式)
	 * 
	 * @param file
	 *            文件路径
	 * @return 文件内容
	 * @throws IOException 
	 */
	private static byte[] localReadAsByte(File file) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(
				new FileInputStream(file));
		int bufferSize = bis.available();
		byte[] buf = new byte[bufferSize];
		bis.read(buf);
		bis.close();
		return buf;
	}

	/**
	 * 以指定字符编码读取本地文本文件
	 * 
	 * @param path 文件路径
	 * @param encoding 字符编码
	 * @return 文件内容
	 * @throws IOException 
	 */
	public static String localRead(String path, String encoding) throws IOException {
		byte[] temp = localReadAsByte(path);
		String str = new String(temp, encoding);
		return str;
//		return new String(localReadAsByte(path),encoding);
	}
	/**
	 * 读取本地文件内容
	 * 
	 * @param file
	 *            文件
	 * @param encoding
	 *            字符编码
	 * @return 文件内容
	 * @throws IOException 
	 */
	public static String localRead(File file, String encoding) throws IOException {
		byte[] temp = localReadAsByte(file);
		String str = new String(temp, encoding);
		return str;
	}
	/**
	 * 按照little-endian 字节顺序写入浮点数
	 * @param dos
	 * @param value
	 */
	public static void writeFloatByLE (DataOutputStream dos, float value) throws IOException {
		ByteBuffer buff = ByteBuffer.allocate(4);
		buff.order(ByteOrder.LITTLE_ENDIAN);
		buff.putFloat(value);
		dos.write(buff.array());
	}
	/**
	 * 按照little-endian 字节顺序写入短整数
	 * @param dos
	 * @param value
	 */
	public static void writeShortByLE (DataOutputStream dos, short value) throws IOException {
		ByteBuffer buff = ByteBuffer.allocate(2);
		buff.order(ByteOrder.LITTLE_ENDIAN);
		buff.putShort(value);
		dos.write(buff.array());
	}
	/**
	 * 判断文件的编码格式
	 * 
	 * @param fileName
	 *            :file
	 * @return 文件编码格式
	 * @throws Exception
	 *             异常
	 */
	@SuppressWarnings("resource")
	public static String codeString(final String fileName) throws Exception {
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName));
		int p = (bin.read() << 8) + bin.read();
		String code = null;

		switch (p) {
		case 0xefbb:
			code = "UTF-8";
			break;
		case 0xfffe:
			code = "Unicode";
			break;
		case 0xfeff:
			code = "UTF-16BE";
			break;
		default:
			code = "GBK";
		}

		return code;
	}
	/**
	 * @param fileName
	 *            文件名
	 * @return 字符串
	 * @throws Exception
	 *             异常
	 */
	public static String readLocalFileByChar(final String fileName) throws Exception {
		File file = new File(fileName);
		String fileCode = codeString(fileName);
		String resultstr = FileUtils.readFileToString(file, fileCode);
		return resultstr;
	}
	/**
	 * 按照little-endian 字节顺序写入无符号短整数
	 * @param dos
	 * @param value
	 */
	public static void writeUnsignedShortByLE (DataOutputStream dos, int value) throws IOException {
		ByteBuffer buff = ByteBuffer.allocate(4);
		buff.order(ByteOrder.LITTLE_ENDIAN);
		buff.putInt(value);
		dos.write(Arrays.copyOfRange(buff.array(), 0, 2));
	}
	/**
	 * 按照little-endian 字节顺序写入整数
	 * @param dos
	 * @param value
	 */
	public static void writeIntByLE (DataOutputStream dos, int value) throws IOException {
		ByteBuffer buff = ByteBuffer.allocate(4);
		buff.order(ByteOrder.LITTLE_ENDIAN);
		buff.putInt(value);
		dos.write(buff.array());
	}
	
	public static void copyFile(String oldPath, String newPath) {				
		try {
			File oldFile = new File(oldPath);
			File newFile = new File(newPath);
			if (oldFile.exists()) {
				FileUtils.copyFile(oldFile, newFile);
			} else {
				oldFile.createNewFile();
				FileUtils.copyFile(oldFile, newFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
