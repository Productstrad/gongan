/**
 * 
 */
package util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import service.LoginService;
import vo.Medialib;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import common.Config;
import common.Constant;
import dao.MedialibDao;

/**
 * @author mendz 2014年10月17日
 */
public class UploadFile {

	private Logger logger = Logger.getLogger(UploadFile.class);
	private static final UploadFile instance = new UploadFile();

	public static UploadFile getInstance() {
		return instance;
	}

	private UploadFile() {

	}

	private int orginWidth = 0, orginHeight = 0;

	private SimpleDateFormat df = new SimpleDateFormat("HHmmssSSS");

	/**
	 * 根据相对路径path上传图片,只支持上传单张
	 * 
	 * @param request
	 * @param path
	 * @param dealMethods
	 *            original,cut,waterText,waterImage
	 * @return 上传后图片相对地址列表.多个;分割
	 * @throws Exception
	 * @author mengdz 2014年10月17日
	 */
	public String uploadPic(HttpServletRequest request, String path,
			String dealMethods) throws Exception {

		// @TODO 后续增加从配置文件读取参数逻辑
		String rootPicDrectoty = Config.get(
				"config/uploadPicConfig/rootPicDrectoty/text()", "uploadPic");

		long size = Long.valueOf(Config.get(
				"config/uploadPicConfig/size/text()", "1024"));

		int width = Integer.valueOf(Config.get(
				"config/uploadPicConfig/width/text()", "1024"));// 原图宽
		int height = Integer.valueOf(Config.get(
				"config/uploadPicConfig/height/text()", "768"));// 原图高
		int cutImageWidth = Integer.valueOf(Config.get(
				"config/uploadPicConfig/cutImageWidth/text()", "720"));// 裁剪后图宽
		int cutImageHeight = Integer.valueOf(Config.get(
				"config/uploadPicConfig/cutImageHeight/text()", "350"));// 裁剪后图高

		// String waterFontName="";
		// int waterFontStyle=0;
		// int waterFontColor=0;
		// int waterFontSize=0;
		String waterText = Config.get(
				"config/uploadPicConfig/waterText/text()", null);
		String waterImage = Config.get(
				"config/uploadPicConfig/waterImage/text()", null);

		String rootRealDrectory = request.getSession().getServletContext()
				.getRealPath("/");
		String uploadPath = rootRealDrectory;
		uploadPath = uploadPath + rootPicDrectoty + "/" + path;

		File file = new File(uploadPath);
		if (!file.isDirectory()) {
			boolean mkdirFlag = file.mkdirs();
			if (!mkdirFlag) {
				return "error:未能创建目录，请检查权限";
			}
		}

		if (!ServletFileUpload.isMultipartContent(request)) {
			return "error:请选择文件";
		}
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List items = new ArrayList();
		items = upload.parseRequest(request);
		Iterator iter = items.iterator();

		Random r = new Random();
		String fileName = "", upFileName = "", shortFileName = "", extName = "";
		StringBuilder result = new StringBuilder();
		String[] array;
		Map<String, Object> reqParamsMap=new HashMap<String, Object>();
		while (iter.hasNext()) {

			FileItem item = (FileItem) iter.next();

			if (item.isFormField()) {
//				String name = item.getFieldName();
//				String value = item.getString("utf-8");
				reqParamsMap.put(item.getFieldName(), item.getString("utf-8"));
			} else {
				shortFileName = df.format(new Date()) + "_"
						+ new Random().nextInt(1000);
				upFileName = item.getName();
				array = upFileName.split("\\.");
				extName = "." + array[array.length - 1].toLowerCase();
				fileName = shortFileName + extName;
				// String fieldName = item.getFieldName();
				// String contentType = item.getContentType();
				// boolean isInMemory = item.isInMemory();
				long sizeInBytes = item.getSize();

				// 检测是否图片、是否符合尺寸
				BufferedImage mImage = ImageIO.read(item.getInputStream());
				try {
					if (mImage == null || mImage.getWidth(null) <= 0
							|| mImage.getHeight(null) <= 0) {
						return "error:"+upFileName + "不是图片文件";
					}
					if (mImage.getWidth() > width
							|| mImage.getHeight() > height) {
						return "error:"+upFileName + "尺寸不合,最大尺寸" + width + "*" + height;
					}
					orginWidth = mImage.getWidth();
					orginHeight = mImage.getHeight();
				} finally {
					mImage = null;
				}
				// 检测是否符合上传类型
				if (!allowPicType(fileName)) {
					return "error:"+upFileName + "不支持的文件格式;";
				}
				// 检测是否符合大小
				if (sizeInBytes > (size * 1024l)) {
					return "error:"+upFileName + "文件大于" + size + "k";
				}
				
				if ("waterImage".equals(dealMethods)) {// 加图片水印
					if (StringUtil.isNotNullorEmpty(waterImage)) {
						String waterImagePic = uploadPath + "/" + shortFileName
								+ "_wi" + extName;
						pressImage(rootRealDrectory + waterImage,
								item.getInputStream(), waterImagePic);
					}
				}else if ("original".equals(dealMethods)) {//上传存储原图
					File uploadedFile = new File(uploadPath + "/" + fileName);// 用/符合linux,windows也可以用
					item.write(uploadedFile);
				}else if ("cut".equals(dealMethods)) {// 存储剪裁图
					if (orginWidth > cutImageWidth
							&& orginHeight > cutImageHeight) {
						int x = (orginWidth - cutImageWidth) / 2;
						int y = (orginHeight - cutImageHeight) / 2;
						cut("jpg", item.getInputStream(), x, y,
								cutImageWidth, cutImageHeight, uploadPath
										+ "/" + shortFileName + "_cut"
										+ extName);
					}
				}else if ("waterText".equals(dealMethods)) {// 加文字水印
					if(StringUtil.isNotNullorEmpty(waterText)){
						String waterTextPic = uploadPath + "/" + shortFileName
								+ "_wt" + extName;
						Color color = new Color(255, 0, 0);
						Font font = new Font("宋体", Font.BOLD, 12);
						pressText(waterText, item.getInputStream(), waterTextPic,
								color, font);
					}
				}else{
					return "error:没有正确配置图片上传方式dealMethod";
				}
				//构造返回的图片地址
				result.append("/").append(rootPicDrectoty).append("/").append(path).append("/").append(shortFileName);
				if ("waterImage".equals(dealMethods)) {
					result.append("_wi");
				}else if("waterText".equals(dealMethods)){
					result.append("_wt");
				}else if("cut".equals(dealMethods)){
					result.append("_cut");
				}
				result.append(extName);
				result.append(";");// 上传后图片相对地址列表
			}
		}		
	    //插入用户媒体库
		if(StringUtil.isNotNullorEmpty(result.toString())){
		    Medialib vo=new Medialib();
		    vo.setMediaPath(result.toString().replace(";", ""));
		    vo.setMediaTitle(reqParamsMap.get("mediaTitle")!=null?reqParamsMap.get("mediaTitle").toString():"");
		    vo.setMediaType(1);
		    vo.setUserid(LoginService.getLoginUserId(request));
		    vo.setCreateDate(new Date());
		    vo.setStatus(1);
		    MedialibDao.getInstance().insert(vo);
		}
		return result.toString();
	}

	/**
	 * 把图片印刷到图片上
	 * 
	 * @param pressImg
	 *            -- 水印文件
	 * @param srcStream
	 *            -- 源文件
	 * @param targetImg
	 *            -- 目标文件
	 */
	public void pressImage(String pressImg, InputStream srcStream,
			String targetImg) {
		BufferedImage image = null;
		try {
			// 目标文件
			Image src = ImageIO.read(srcStream);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			image = new BufferedImage(wideth, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);
			// 水印文件
			File _filebiao = new File(pressImg);
			Image src_biao = ImageIO.read(_filebiao);
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, Float.valueOf(Config.get(
					"config/uploadPicConfig/waterAlpha/text()", "0.5f"))));
			g.drawImage(src_biao, wideth - wideth_biao - 5, height
					- height_biao - 2, wideth_biao, height_biao, null);
			// 水印文件结束
			g.dispose();
			FileOutputStream out = new FileOutputStream(targetImg);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("加水印图片出错", e);
		} finally {
			image = null;
		}
	}

	/**
	 * 打印文字水印图片
	 * 
	 * @param pressText
	 *            --水印文字
	 * @param srcStream
	 *            --源文件
	 * @param targetImg
	 *            -- 目标图片
	 * @param color
	 *            -- 字体颜色
	 * @param font
	 *            -- 字体
	 */
	public void pressText(String pressText, InputStream srcStream,
			String targetImg, Color color, Font font) {
		BufferedImage image = null;
		try {
			Image src = ImageIO.read(srcStream);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			image = new BufferedImage(wideth, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);
			g.setColor(color);
			g.setFont(font);
			FontMetrics fm = sun.font.FontDesignMetrics.getMetrics(font);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, Float.valueOf(Config.get(
					"config/uploadPicConfig/waterAlpha/text()", "0.5f"))));
			g.drawString(pressText, orginWidth - fm.stringWidth(pressText) - 2,
					orginHeight - 5);
			g.dispose();
			FileOutputStream out = new FileOutputStream(targetImg);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("加水印文字出错", e);
		} finally {
			image = null;
		}
	}

	public void cut(String lastdir, InputStream srcStream, int x, int y, int width,
			int height, String subpath) throws IOException {
		FileInputStream is = null;
		ImageInputStream iis = null;
		BufferedImage bi = null;
		try {
			// 读取图片文件
//			is = new FileInputStream(srcStream);
			/*
			 * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 声称能够解码指定格式。
			 * 参数：formatName - 包含非正式格式名称 .（例如 "jpeg" 或 "tiff"）等 。
			 */
			Iterator<ImageReader> it = ImageIO
					.getImageReadersByFormatName(lastdir);
			ImageReader reader = it.next();
			// 获取图片流
			iis = ImageIO.createImageInputStream(srcStream);
			/*
			 * <p>iis:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索’。
			 * 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
			 */
			reader.setInput(iis, true);

			/*
			 * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O
			 * 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件 将从其 ImageReader 实现的
			 * getDefaultReadParam 方法中返回 ImageReadParam 的实例。
			 */
			ImageReadParam param = reader.getDefaultReadParam();
			/*
			 * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象
			 * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。
			 */
			Rectangle rect = new Rectangle(x, y, width, height);
			// 提供一个 BufferedImage，将其用作解码像素数据的目标。
			param.setSourceRegion(rect);
			/*
			 * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 它作为一个完整的
			 * BufferedImage 返回。
			 */
			bi = reader.read(0, param);

			// 保存新图片
			ImageIO.write(bi, lastdir, new File(subpath));
		} finally {
			if (is != null) {
				is.close();
			}
			if (iis != null) {
				iis.close();
			}
			bi = null;
		}
	}

	private boolean allowPicType(String fileName) {
		String[] type = Config.get("config/uploadPicConfig/type/text()",
				"jpg,jpeg,png,gif,bmp").split(",");
		boolean result = false;
		for (String string : type) {
			if (fileName.endsWith(string)) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	private boolean allowVideoType(String fileName) {
		String[] type = Config.get("config/uploadMediaConfig/videoType/text()",
				"swf,flv,wma,wmv,mid,avi,mpg,asf,rm,rmvb").split(",");
		boolean result = false;
		for (String string : type) {
			if (fileName.endsWith(string)) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	private boolean allowAudioType(String fileName) {
		String[] type = Config.get("config/uploadMediaConfig/audioType/text()",
				"mp3").split(",");
		boolean result = false;
		for (String string : type) {
			if (fileName.endsWith(string)) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	/**
	 * 取文件form的其它参数
	 * @param request
	 * @return
	 * @throws FileUploadException
	 * @author mengdz
	 * 2014年10月22日
	 * @throws UnsupportedEncodingException 
	 */
//	public Map<String, Object> getParmas(HttpServletRequest request) throws FileUploadException, UnsupportedEncodingException {
//		FileItemFactory factory = new DiskFileItemFactory();
//		ServletFileUpload upload = new ServletFileUpload(factory);
//		upload.setHeaderEncoding("UTF-8");
//		List items = new ArrayList();
//		items = upload.parseRequest(request);
//		Iterator iter = items.iterator();
//
//		Random r = new Random();
//		String fileName = "", upFileName = "", shortFileName = "", extName = "";
//		StringBuilder result = new StringBuilder();
//		String[] array;
//		Map<String, Object> map=new HashMap<String, Object>();
//		while (iter.hasNext()) {
//
//			FileItem item = (FileItem) iter.next();
//
//			if (item.isFormField()) {
////				String name = item.getFieldName();
////				String value = item.getString();
//				map.put(item.getFieldName(), item.getString("utf-8"));
//			}
//		}
//		return map;
//	}
	
	/**
	 * 上传图片
	 * 
	 * @param request
	 * @param flag
	 *            0 默认按年、月划分图片上传目录 1……待扩展
	 * @param dealMethods
	 *            original,cut,waterText,waterImage
	 * @return
	 * @throws Exception
	 * @author mengdz 2014年10月17日
	 */
	public String uploadPic(HttpServletRequest request, int pathType,
			String dealMethods) throws Exception {
		String[] curDateArray = Constant.dateFormat.format(new Date()).split(
				"-");
		String path = curDateArray[0] + "/" + curDateArray[1] + "/"
				+ curDateArray[2];// 默认按年、月、日划分图片上传目录
		switch (pathType) {// 设置相对路径path
		case 1:

			break;

		default:
			break;
		}
		return uploadPic(request, path, dealMethods);
	}

	/**
	 * 上传音/视频
	 * 
	 * @param request
	 * @param pathType
	 * @return
	 * @throws Exception
	 * @author mengdz 2014年10月21日
	 */
	public String uploadMedia(HttpServletRequest request, int pathType)
			throws Exception {
		String[] curDateArray = Constant.dateFormat.format(new Date()).split(
				"-");
		String path = curDateArray[0] + "/" + curDateArray[1] + "/"
				+ curDateArray[2];// 默认按年、月、日划分图片上传目录
		switch (pathType) {// 设置相对路径path
		case 1:

			break;

		default:
			break;
		}
		return uploadMedia(request, path);
	}

	/**
	 * 上传音/视频
	 * 
	 * @param request
	 * @param path
	 * @return
	 * @throws Exception
	 * @author mengdz 2014年10月21日
	 */
	public String uploadMedia(HttpServletRequest request, String path)
			throws Exception {
		// @TODO 后续增加从配置文件读取参数逻辑
		String rootMediaDrectoty = Config.get(
				"config/uploadMediaConfig/rootMediaDrectoty/text()",
				"uploadMedia");
		long videoSize = Long.valueOf(Config.get(
				"config/uploadMediaConfig/videoSize/text()", "0"));
		long audioSize = Long.valueOf(Config.get(
				"config/uploadMediaConfig/audioSize/text()", "0"));
		String rootRealDrectory = request.getSession().getServletContext()
				.getRealPath("/");
		String uploadPath = rootRealDrectory;
		uploadPath = uploadPath + rootMediaDrectoty + "/" + path;

		File file = new File(uploadPath);
		if (!file.isDirectory()) {
			boolean mkdirFlag = file.mkdirs();
			if (!mkdirFlag) {
				return "error:未能创建目录，请检查权限";
			}
		}

		if (!ServletFileUpload.isMultipartContent(request)) {
			return "error:请选择文件";
		}
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List items = new ArrayList();
		items = upload.parseRequest(request);
		Iterator iter = items.iterator();

		Random r = new Random();
		String fileName = "", upFileName = "", shortFileName = "", extName = "";
		StringBuilder result = new StringBuilder();
		String[] array;
		Map<String, Object> reqParamsMap=new HashMap<String, Object>();
		while (iter.hasNext()) {

			FileItem item = (FileItem) iter.next();

			if (item.isFormField()) {
				reqParamsMap.put(item.getFieldName(), item.getString("utf-8"));
			} else {
				shortFileName = df.format(new Date()) + "_"
						+ new Random().nextInt(1000);
				upFileName = item.getName();
				array = upFileName.split("\\.");
				extName = "." + array[array.length - 1].toLowerCase();
				fileName = shortFileName + extName;
				// String fieldName = item.getFieldName();
				// String contentType = item.getContentType();
				// boolean isInMemory = item.isInMemory();
				long sizeInBytes = item.getSize();

				// 检测是否符合上传类型
				if (!allowVideoType(fileName)&&!allowAudioType(fileName)) {
					return "error:"+upFileName + "不支持的文件格式;";
				}
				//设置媒体文件类型
				if(allowVideoType(fileName)){
					// 检测是否符合大小
					if (sizeInBytes > (videoSize * 1024l*1024l)) {
						return "error:"+upFileName + "文件大于" + videoSize + "M";
					}
					reqParamsMap.put("type", 2);
				}else if(allowAudioType(fileName)){
					// 检测是否符合大小
					if (sizeInBytes > (audioSize * 1024l*1024l)) {
						return "error:"+upFileName + "文件大于" + audioSize + "M";
					}
					reqParamsMap.put("type", 3);
				}
				// 上传
				File uploadedFile = new File(uploadPath + "/" + fileName);// 用/符合linux,windows也可以用
				item.write(uploadedFile);
				result.append("/").append(rootMediaDrectoty).append("/").append(path).append("/").append(fileName)
				.append(";");// 上传后图片相对地址列表
			}
		}
		//插入用户媒体库
	    Medialib vo=new Medialib();
	    vo.setMediaPath(result.toString().replace(";", ""));
	    vo.setMediaTitle(reqParamsMap.get("mediaTitle")!=null?reqParamsMap.get("mediaTitle").toString():"");
	    vo.setMediaType(reqParamsMap.get("type")!=null?Integer.valueOf(reqParamsMap.get("type").toString()):0);
	    vo.setUserid(LoginService.getLoginUserId(request));
	    vo.setCreateDate(new Date());
	    vo.setStatus(1);
	    MedialibDao.getInstance().insert(vo);
		return result.toString();
	}
}
