package com.itwu.utils;


import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

public class ImageUtil {
    // MultipartFile转BASE64字符串
    public static String multipartFileToBASE64(MultipartFile mFile) throws Exception{
        BASE64Encoder bEncoder=new BASE64Encoder();
        String[] suffixArra=mFile.getOriginalFilename().split("\\.");
        String preffix="data:image/jpg;base64,".replace("jpg", suffixArra[suffixArra.length - 1]);
        String base64EncoderImg=preffix + bEncoder.encode(mFile.getBytes()).replaceAll("[\\s*\t\n\r]", "");
        return base64EncoderImg;
    }



}
