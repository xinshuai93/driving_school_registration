package com.drive.driveservice.service.impl;

import com.drive.commonutils.R;
import com.drive.driveservice.dto.ApplicationDTO;
import com.drive.driveservice.entity.Application;
import com.drive.driveservice.entity.User;
import com.drive.driveservice.mapper.ApplicationMapper;
import com.drive.driveservice.service.ApplicationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.drive.driveservice.service.UserService;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * <p>
 * 学生报名表 服务实现类
 * </p>
 *
 * @author lx
 * @since 2023-06-14
 */
@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements ApplicationService {

    @Autowired
    private UserService userService;
    @Override
    public String sendMail(ApplicationDTO dto) throws GeneralSecurityException, MessagingException {
        String email = dto.getEmail();
        String name = dto.getName();
        String phone = dto.getPhone();
        //创建一个配置文件并保存
        Properties properties = new Properties();
        properties.setProperty("mail.host","smtp.qq.com");
        properties.setProperty("mail.transport.protocol","smtp");
        properties.setProperty("mail.smtp.auth","true");
        //QQ存在一个特性设置SSL加密
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);
        //创建一个session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("1104850148@qq.com","ufvtgacfqztsfejb");
            }
        });
        //开启debug模式
        session.setDebug(true);
        //获取连接对象
        Transport transport = session.getTransport();
        //连接服务器
        transport.connect("smtp.qq.com","1104850148@qq.com","ufvtgacfqztsfejb");
        //创建邮件对象
        MimeMessage mimeMessage = new MimeMessage(session);
        //邮件发送人
        mimeMessage.setFrom(new InternetAddress("1104850148@qq.com"));
        //邮件接收人
        mimeMessage.setRecipient(Message.RecipientType.TO,new InternetAddress(email));
        //邮件标题
        mimeMessage.setSubject(name + "同学,您好。恭喜您报名成功。请您查收驾考宝典系统的账号和初始密码");
        //邮件内容
        mimeMessage.setContent(name + "同学,您好。恭喜您报名成功。您的账号为:" + phone + ";初始密码为:123456。系统根据您的报名信息，为您分配了最合适您的教练：李磊。请您登陆系统后修改初始密码。","text/html;charset=UTF-8");
        //发送邮件
        transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());
        //关闭连接
        transport.close();
        User user = new User();
        user.setName(name);
        user.setPhone(phone);
        user.setPassword("123456");
        user.setRole(3);
        user.setRoleId("1");
        boolean save = userService.save(user);
        if (save) {
            return "success";
        }
        return "error";
    }

    @Override
    public void sendFailMail(ApplicationDTO dto) throws GeneralSecurityException, MessagingException {
        String message = dto.getMessage();
        String email = dto.getEmail();
        String name = dto.getName();
        //创建一个配置文件并保存
        Properties properties = new Properties();
        properties.setProperty("mail.host","smtp.qq.com");
        properties.setProperty("mail.transport.protocol","smtp");
        properties.setProperty("mail.smtp.auth","true");
        //QQ存在一个特性设置SSL加密
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);
        //创建一个session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("1104850148@qq.com","ufvtgacfqztsfejb");
            }
        });
        //开启debug模式
        session.setDebug(true);
        //获取连接对象
        Transport transport = session.getTransport();
        //连接服务器
        transport.connect("smtp.qq.com","1104850148@qq.com","ufvtgacfqztsfejb");
        //创建邮件对象
        MimeMessage mimeMessage = new MimeMessage(session);
        //邮件发送人
        mimeMessage.setFrom(new InternetAddress("1104850148@qq.com"));
        //邮件接收人
        mimeMessage.setRecipient(Message.RecipientType.TO,new InternetAddress(email));
        //邮件标题
        mimeMessage.setSubject(name + "同学,您好。");
        //邮件内容
        mimeMessage.setContent(name + "同学,您好。由于您" + message + "报名失败。请联系相关人员寻求咨询详细情况。欢迎您重新报名！","text/html;charset=UTF-8");
        //发送邮件
        transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());
        //关闭连接
        transport.close();
    }
}
