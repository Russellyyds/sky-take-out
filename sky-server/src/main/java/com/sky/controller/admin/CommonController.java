package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用的controller")
@Slf4j
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;
    @ApiOperation("上传图片到阿里云oss")
    @PostMapping("/upload")
    public Result<String> uploadPicture(MultipartFile file) {
        log.info("Uploading,文件上传阿里云:{}", file);
        try {
            String originalFilename = file.getOriginalFilename();
            String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
            String objectName = UUID.randomUUID().toString() + substring;
            String uploadedFilePath = aliOssUtil.upload(file.getBytes(), objectName);
            return Result.success(uploadedFilePath);
        } catch (IOException e) {
            log.error("文件上传失败" +
                    "{}",e.getMessage());
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
