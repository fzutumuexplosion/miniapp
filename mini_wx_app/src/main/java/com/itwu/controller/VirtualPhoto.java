package com.itwu.controller;


import com.alibaba.fastjson.JSONObject;
import com.itwu.entity.R;
import com.itwu.service.BackGroundService;
import com.itwu.utils.AuthorService;
import com.itwu.utils.HttpUtil;
import com.itwu.utils.ToImgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

import static com.itwu.utils.ImageUtil.multipartFileToBASE64;

@RestController
@RequestMapping("/virtual")
public class VirtualPhoto {
    @Autowired
    private BackGroundService backGroundService;

    //获取所有背景信息
    @GetMapping()
    public R getAll(){
        return new R(true,backGroundService.list());
    }

    @PostMapping("/cut")
    public R cut(HttpServletRequest request, @RequestParam(value = "file") MultipartFile file){
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/body_seg";
        try {
            String imgStr = multipartFileToBASE64(file);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String param = "image=" + imgParam;
            //获取token
            String accessToken = AuthorService.getAuth();
                //System.out.println(accessToken);
            //获取信息
            String result = HttpUtil.post(url, accessToken, param);
            JSONObject jsonObject = JSONObject.parseObject(result);
                //System.out.println(result);
            //取出人像
            String img=jsonObject.getString("foreground");
                //System.out.println(img);
            //获取协议号
            String basePath = request.getScheme()
                    + "://"
                    + request.getServerName()//获取IP地址
                    + ":"
                    + request.getServerPort()//获取端口号
                    + request.getContextPath();//获取工程路径

            //base64转为图片
            ToImgUtil toImgUtil=new ToImgUtil();
            ToImgUtil toImgUtil1= toImgUtil.ToImg(img,basePath);

                System.out.println(toImgUtil1.getBase());


        } catch (Exception e) {

            return new R(false,"错误");
        }
        return new R(true);
    }



}
