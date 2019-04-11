
package com.easydatalink.tech.utils;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * 各种格式的编码加码工具类.
 * 
 * 集成Commons-Codec,Commons-Lang及JDK提供的编解码方法.
 */
public class EncodeUtil {

	private static final String DEFAULT_URL_ENCODING = "UTF-8";

	/**
	 * Hex编码.
	 */
	public static String hexEncode(byte[] input) {
		return Hex.encodeHexString(input);
	}

	/**
	 * Hex解码.
	 */
	public static byte[] hexDecode(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw new IllegalStateException("Hex Decoder exception", e);
		}
	}

	/**
	 * Base64编码.
	 */
	public static String base64Encode(byte[] input) {
		return new String(Base64.encodeBase64(input));
	}

	/**
	 * Base64编码, URL安全(将Base64中的URL非法字符如+,/=转为其他字符, 见RFC3548).
	 */
	public static String base64UrlSafeEncode(byte[] input) {
		return Base64.encodeBase64URLSafeString(input);
	}

	/**
	 * Base64解码.
	 */
	public static byte[] base64Decode(String input) {
		return Base64.decodeBase64(input);
	}

	/**
	 * URL 编码, Encode默认为UTF-8. 
	 */
	public static String urlEncode(String input) {
		return urlEncode(input, DEFAULT_URL_ENCODING);
	}

	/**
	 * URL 编码.
	 */
	public static String urlEncode(String input, String encoding) {
		try {
			return URLEncoder.encode(input, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unsupported Encoding Exception", e);
		}
	}

	/**
	 * URL 解码, Encode默认为UTF-8. 
	 */
	public static String urlDecode(String input) {
		return urlDecode(input, DEFAULT_URL_ENCODING);
	}

	/**
	 * URL 解码.
	 */
	public static String urlDecode(String input, String encoding) {
		try {
			return URLDecoder.decode(input, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unsupported Encoding Exception", e);
		}
	}	

	/**
	 * Html 转码.
	 */
	public static String htmlEscape(String html) {
		return StringEscapeUtils.escapeHtml3(html);
	}

	/**
	 * Html 解码.
	 */
	public static String htmlUnescape(String htmlEscaped) {
		return StringEscapeUtils.unescapeHtml3(htmlEscaped);
	}

	/**
	 * Xml 转码.
	 */
	public static String xmlEscape(String xml) {
		return StringEscapeUtils.escapeXml(xml);
	}

	/**
	 * Xml 解码.
	 */
	public static String xmlUnescape(String xmlEscaped) {
		return StringEscapeUtils.unescapeXml(xmlEscaped);
	}
	
	/**
	* @描述: 按key加密
	* @param txt
	* @param encrypt_key
	* @return
	* @return byte[]
	 */
	@SuppressWarnings("unused")
	public static byte[] encrypt(byte[] txt, byte[] key) {
		int rand = new Double(Math.random() * 32000).intValue();
		byte[] encrypt_key = DigestUtils.md5Hex(rand + "").toLowerCase().getBytes();
		byte ctr = 0;
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		for (int i = 0, j = 0; i < txt.length; i++, j += 2) {
			ctr = ctr == encrypt_key.length ? 0 : ctr;
			byteOut.write(encrypt_key[ctr]);
			byteOut.write(txt[i] ^ encrypt_key[ctr++]);
		}

		return Base64.encodeBase64(encodeKey(byteOut.toByteArray(), key));
	}
	/**
	* @描述: 按key解密
	* @param txt
	* @param encrypt_key
	* @return
	* @return byte[]
	 */
	public static String encrypt(String txt, String key) {
		return new String(encrypt(txt.getBytes(), key.getBytes()));
	}
	/**
	* @描述: 按key解密
	* @param txt
	* @param encrypt_key
	* @return
	* @return byte[]
	 */
	public static String encrypt(String txt, String key, String encoding) {
		String str = null;
		try {
			str = new String(encrypt(txt.getBytes(encoding), key.getBytes()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	* @描述: 按key解密
	* @param txt
	* @param encrypt_key
	* @return
	* @return byte[]
	 */
	public static byte[] decrypt(byte[] txt, byte[] key) {
		txt = encodeKey(Base64.decodeBase64(txt), key);
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		for (int i = 0; i < txt.length; i++) {
			byte md5 = txt[i];
			byteOut.write(txt[++i] ^ md5);
		}
		return byteOut.toByteArray();
	}

	/**
	* @描述: 按key加密
	* @param txt
	* @param encrypt_key
	* @return
	* @return byte[]
	 */
	public static String decrypt(String txt, String key) {
		return new String(decrypt(txt.getBytes(), key.getBytes()));
	}

	/**
	* @描述: 按key加密
	* @param txt
	* @param encrypt_key
	* @return
	* @return byte[]
	 */
	public static byte[] encodeKey(byte[] txt, byte[] encrypt_key) {
		encrypt_key = DigestUtils.md5Hex(new String(encrypt_key)).toLowerCase().getBytes();
		byte ctr = 0;
		byte[] tmp = new byte[txt.length];
		for (int i = 0; i < txt.length; i++) {
			ctr = ctr == encrypt_key.length ? 0 : ctr;
			tmp[i] = (byte) (txt[i] ^ encrypt_key[ctr++]);
		}
		return tmp;
	}
	
	public static void main(String[] arg) {
//		String source = "Hello3";
		String source = "<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?><XML_DATA>"
			+"<MD001>"
				+"<SupplierName></SupplierName>"
				+"<SupplierCode></SupplierCode>"
				+"<TaxNumber></TaxNumber>"			
			+"</MD001>"
			+"<MD001>"
				+"<SupplierName></SupplierName>"
				+"<SupplierCode></SupplierCode>"
				+"<TaxNumber></TaxNumber>"			
			+"</MD001>"
		+"</XML_DATA>]]>";
		String key = "20100714";
		String encryptTxt = EncodeUtil.encrypt(source, key, "UTF-8");
		System.out.println("Encypt String : " + encryptTxt);  //加密
		String decryptTxt = EncodeUtil.decrypt(encryptTxt, key);
		System.out.println("Decypt String : " + decryptTxt);  //解密
		
		System.out.println(DigestUtils.md5Hex("c12345678"));
		
	}
}
