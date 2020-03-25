package cn.tianyu.springboottemplate.global;

/**
 * 系统常量
 */
public interface Constants {

    String QINIU_ACCESSKEY = "H53dV78UoxU-qewQVH3xUtXRM7HAfQrZmHgneBgw";
    String QINIU_SECRETKEY = "RQC91vJpQaAFi83sMWf-MvVbrEsAAZi9BA-6n8RF";
    String QINIU_BUCKET = "tianyu-blog";

    String SESSION_KEY_ROLE = "ROLE";
    String ROLE_ADMIN = "ADMIN";

    /**
     * qiniu token 有效期，单位：秒
     */
    long QINIU_TOKEN_VALID_TIME = 55 * 60;

    String TEMPLATE_NAME = "article";

    String ARTICLE_DIR = "/static/archives/";
}
