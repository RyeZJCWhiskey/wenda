package com.nowcoder.wenda.util;

import freemarker.template.Template;
//import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
//import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Map;
import java.util.Properties;

/**
 * 邮件的相关服务
 */
@Service
public class MailSender implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(MailSender.class);

    private JavaMailSenderImpl mailSender;

//    @Autowired
//    private VelocityEngine velocityEngine;

    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;

    public boolean sendWithHTMLTemplate(String to, String subject,
                                        String template, Map<String, Object> model) {
        try {
            //使用模板生成html邮件内容
            String nick = MimeUtility.encodeText("牛客中级课");//昵称
            InternetAddress from = new InternetAddress(nick + "<jc_zhu1994@163.com>");//发件人
            MimeMessage mimeMessage = mailSender.createMimeMessage();//创建邮件正文
            //由于是html邮件，不是mulitpart类型
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,false,"utf8");

//            String result = VelocityEngineUtils
//                    .mergeTemplateIntoString(velocityEngine, template, "UTF-8", model);

            Template tpl=freeMarkerConfigurer.getConfiguration().getTemplate(template);
            String result= FreeMarkerTemplateUtils.processTemplateIntoString(tpl,model);

            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(result, true);//true表示发的是html邮件
            //发送模板邮件
            mailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            logger.error("发送邮件失败" + e.getMessage());
            return false;
        }
    }

    @Override
    //初始化发件人的信息
    public void afterPropertiesSet() throws Exception {
        mailSender = new JavaMailSenderImpl();
        mailSender.setUsername("jc_zhu1994@163.com");
        //注意这里不是邮箱密码而是客户端授权密码，很坑，参考：https://blog.csdn.net/hughnes/article/details/52070878
        mailSender.setPassword("xyq641272");
        mailSender.setHost("smtp.163.com");//服务器主机域名
        //mailSender.setHost("smtp.email.qq.com");
        mailSender.setPort(465);//端口号
        mailSender.setProtocol("smtps");
        mailSender.setDefaultEncoding("utf8");
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.ssl.enable", true);
        //javaMailProperties.put("mail.smtp.auth", true);
        //javaMailProperties.put("mail.smtp.starttls.enable", true);
        mailSender.setJavaMailProperties(javaMailProperties);
    }
}
