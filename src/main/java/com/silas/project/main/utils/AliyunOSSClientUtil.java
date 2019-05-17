package com.silas.project.main.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.silas.module.filemanager.apiservice.UploadTokenServiceImpl;
import org.apache.log4j.Logger;
import org.apache.commons.lang3.StringUtils;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import sun.misc.BASE64Decoder;
import javax.swing.filechooser.FileSystemView;

/**
 * @class:AliyunOSSClientUtil
 * @descript:java使用阿里云OSS存储对象上传图片
 * @date:2017年3月16日 下午5:58:08
 * @author sang
 */
public class AliyunOSSClientUtil {
    //log日志
    private static Logger logger = Logger.getLogger(AliyunOSSClientUtil.class);
    //阿里云API的内或外网域名
    private static String ENDPOINT = "oss-cn-hangzhou.aliyuncs.com";
    //阿里云API的密钥Access Key ID
    private static String ACCESS_KEY_ID = "LTAI9kYFxRfD5OXF";
    //阿里云API的密钥Access Key Secret
    private static String ACCESS_KEY_SECRET = "pTrfuJynzVU0i8jdmfq4UO6GQ2B5Jg";
    //阿里云API的bucket名称
    private static String BACKET_NAME = "u2shop";
    //阿里云API的文件夹名称
    private static String FOLDER="QrCode";
    //初始化属性
    static{
        ENDPOINT = "oss-cn-hangzhou.aliyuncs.com";
        ACCESS_KEY_ID = "LTAI9kYFxRfD5OXF";
        ACCESS_KEY_SECRET = "pTrfuJynzVU0i8jdmfq4UO6GQ2B5Jg";
        BACKET_NAME = "u2shop";
        FOLDER = "somnus/";
    }

    /**
     * 获取阿里云OSS客户端对象
     * @return ossClient
     */
    public static  OSSClient getOSSClient(){
        return new OSSClient(ENDPOINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    /**
     * 创建存储空间
     * @param ossClient      OSS连接
     * @param bucketName 存储空间
     * @return
     */
    public  static String createBucketName(OSSClient ossClient,String bucketName){
        //存储空间
        final String bucketNames=bucketName;
        if(!ossClient.doesBucketExist(bucketName)){
            //创建存储空间
            Bucket bucket=ossClient.createBucket(bucketName);
            logger.info("创建存储空间成功");
            return bucket.getName();
        }
        return bucketNames;
    }

    /**
     * 删除存储空间buckName
     * @param ossClient  oss对象
     * @param bucketName  存储空间
     */
    public static  void deleteBucket(OSSClient ossClient, String bucketName){
        ossClient.deleteBucket(bucketName);
        logger.info("删除" + bucketName + "Bucket成功");
    }


    /**
     * 上传图片至OSS
     * @param ossClient  oss连接
     * @param file 上传文件（文件全路径如：D:\\image\\cake.jpg）
     * @param bucketName  存储空间
     * @param folder 模拟文件夹名 如"qj_nanjing/"
     * @return String 返回的唯一MD5数字签名
     * */
    public static  String uploadObject2OSS(OSSClient ossClient, File file, String bucketName, String folder) {
        String resultStr = null;
        try {
            //以输入流的形式上传文件
            InputStream is = new FileInputStream(file);
            //文件名
            String fileName = file.getName();
            //文件大小
            Long fileSize = file.length();
            //创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            //上传的文件的长度
            metadata.setContentLength(is.available());
            //指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache");
            //指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");
            //指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");
            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            //如果没有扩展名则填默认值application/octet-stream
            metadata.setContentType(getContentType(fileName));
            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
            //上传文件   (上传文件流的形式)
            PutObjectResult putResult = ossClient.putObject(bucketName, folder + fileName, is, metadata);
            //解析结果
            resultStr = putResult.getETag();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
        }
        return resultStr;
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public static  String getContentType(String fileName){
        //文件的后缀名
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if(".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if(".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if(".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)  || ".png".equalsIgnoreCase(fileExtension) ) {
            return "image/jpeg";
        }
        if(".html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if(".txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if(".vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if(".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if(".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if(".xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        //默认返回类型
        return "image/jpeg";
    }

    //测试
    public static void main(String[] args) {
        String url = "http://baidu.com/1";
        String path = FileSystemView.getFileSystemView().getHomeDirectory() + File.separator + "testQrcode.jpg";
        System.out.print(path);
        try{
            //初始化OSSClient
            OSSClient ossClient=AliyunOSSClientUtil.getOSSClient();
            //上传文件
            BitMatrix bitMatrix= createQRCode(url,path, width,height);
            MatrixToImageWriter.writeToPath(bitMatrix, format, new File(path).toPath());
            File file=new File(path);
            String md5key = AliyunOSSClientUtil.uploadObject2OSS(ossClient, file, BACKET_NAME, FOLDER);
            logger.info("上传后的文件MD5数字唯一签名:" + md5key);
            file.delete();

        }catch (Exception e){
            System.out.print(path);
        }

        //上传后的文件MD5数字唯一签名:40F4131427068E08451D37F02021473A
    }

    /**
     * base64字符串转换成图片
     * @param imgStr		base64字符串
     * @param imgFilePath	图片存放路
     * @return
     */
    public static Boolean base64ToImage(String imgStr,String imgFilePath) { // 对字节数组字符串进行Base64解码并生成图片
        // 图像数据为空
        if (StringUtils.isEmpty(imgStr)) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    UploadTokenServiceImpl OssCreatePolicyService;
    private static final int width = 1000;// 默认二维码宽度
    private static final int height = 1000;// 默认二维码高度
    private static final String format = "png";// 默认二维码文件格式
    private static final Map<EncodeHintType, Object> hints = new HashMap();// 二维码参数

    static {
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");// 字符编码
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 容错等级 L、M、Q、H 其中 L 为最低, H 为最高
        hints.put(EncodeHintType.MARGIN, 2);// 二维码与图片边距
    }

//
//    public static void main(String[] args) {

//    }
    /**
     * 生成二维码图片文件
     * @param content 二维码内容
     * @param path    文件保存路径
     * @param width   宽
     * @param height  高
     */
    public static BitMatrix createQRCode(String content, String path, int width, int height) throws WriterException, IOException {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
//        MatrixToImageWriter.writeToPath(bitMatrix, format, new File(path).toPath());
        return bitMatrix;
    }

}
