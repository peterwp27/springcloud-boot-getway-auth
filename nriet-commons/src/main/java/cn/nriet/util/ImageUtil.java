package cn.nriet.util;

import java.io.File;
import java.io.FileOutputStream;

import cn.nriet.entity.Book;

public class ImageUtil {

	// 将图片的 byte 数组输出到文件
	public static String writeToImage(Book book, String path) {
		try {
			String fileName = "cover_" + book.getId() + ".png";
			File tempFile = new File(path, fileName);
			// 图片已存在，则不生成
			if(tempFile.exists()) {
				return "/temp/" + fileName;
			}
			// 生成新的目录与图片
			if (!tempFile.getParentFile().exists()) {
				tempFile.getParentFile().mkdir();
			}
			if (!tempFile.exists()) {
				tempFile.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(tempFile);
			out.write(book.getCover());
			out.flush();
			out.close();
			return "/temp/" + fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
