/**
 * 
 */
package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author mendz 2014年10月21日
 */
public class Encryption {

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
	'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 加密
	 * 
	 * @param str
	 *            待加密字符串
	 * @param algorithm
	 *            算法 MD5,SHA1,SHA-256,SHA-512
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @author mengdz 2014年10月21日
	 */
	public static String encode(String str, String algorithm)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (str == null) {
			return null;
		}
		MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
		messageDigest.update(str.getBytes("UTF-8"));
		return getFormattedText(messageDigest.digest());
	}

	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}
}
