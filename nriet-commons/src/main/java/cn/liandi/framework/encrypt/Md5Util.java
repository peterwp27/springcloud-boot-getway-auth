package cn.liandi.framework.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Md5Util {
	private static Logger	logger	= LoggerFactory.getLogger(Md5Util.class);
	private static char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static String getMD5(String source) {
		try {
			StringBuilder result = new StringBuilder();
			byte[] temp = (source!=null? source: "").getBytes();
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(temp);
			byte[] b = digest.digest();
			for (int i = 0; i < b.length; i++) {
				char[] ob = new char[2];
				ob[0] = digit[(b[i] >>> 4) & 0X0F];
				ob[1] = digit[b[i] & 0X0F];
				result.append(new String(ob));
			}
			return result.toString();
		}
		catch (NoSuchAlgorithmException e) {
			logger.error("MD5加密失败："+source, e.getMessage());
			return "";
		}
	}
}
