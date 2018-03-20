package edu.nju.ticket.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import edu.nju.ticket.common.AccountType;

public final class Email {

    private Session session;
    private Properties config;
    private boolean canUse;
    private static final String configpath=Config.getInstance().get("ConfigPath");
    private static final String configname="email.properties";
    private HashMap<AccountType,Pair> htmltext;
    private String title;
    private Properties props = new Properties();

    private static final boolean isHtml=true;

	
	private static Email singleton;
	
	private Email()
	{
		config=new Properties();
		InputStream in = null;
		htmltext=new HashMap<AccountType,Pair>();
		try {
			in=new FileInputStream(configpath+configname);
			config.load(in);
			for(AccountType t:AccountType.values())
			{
				try {
					String htmlheader=configpath+t.name()+config.getProperty("HTMLHeader");
					String htmlfooter=configpath+t.name()+config.getProperty("HTMLFooter");
		    		File header=new File(htmlheader);
		    		BufferedReader headerbr=new BufferedReader(new FileReader(header));
		    		String head="";
		    		while(headerbr.ready())
		    		{
		    			head+=headerbr.readLine();
		    		}
		    		headerbr.close();
		    		File footer=new File(htmlfooter);
		    		BufferedReader footerbr=new BufferedReader(new FileReader(footer));
		    		String foot="";
		    		while(footerbr.ready())
		    		{
		    			foot+=footerbr.readLine();
		    		}
		    		footerbr.close();
		    		htmltext.put(t, new Pair(head,foot));
				}catch(IOException e)
				{
				}
			}
			title=config.getProperty("TITLE");
			props.put("mail.smtp.host", config.getProperty("HOST"));
	        props.put("mail.smtp.port", Integer.parseInt(config.getProperty("PORT")));
	        props.put("mail.smtp.auth", Boolean.parseBoolean(config.getProperty("isAUTH")));
	        props.put("fromer", config.getProperty("FROM"));
	        props.put("username", config.getProperty("USERNAME"));
	        props.put("password", config.getProperty("PASSWORD"));
	        props.put("mail.smtp.timeout", config.getProperty("TIMEOUT"));
	        props.put("mail.debug", config.getProperty("DEBUG"));

	        session = Session.getInstance(props, new Authenticator() {
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(config.getProperty("USERNAME"), config.getProperty("PASSWORD"));
	            }
	        });
	        canUse=true;
	        in.close();
	        return;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("config file not found");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("config file not found");
		}
		canUse=false;
		try {
			in.close();
		} catch (Exception e) {
		}
	}

	protected static Email getInstance()
	{
		if(singleton==null) singleton=new Email();
		return singleton;
	}
    public void sendEmail(AccountType type,String to,String idurl)
    {
    	try {
    		Pair value=this.htmltext.get(type);
    		String content=value.getHtml(idurl);
			Email.getInstance().sendEmail(to, title, content, isHtml);
		} catch (IOException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * 
     * @Title sendEmail
     * @Description 通过isHtml判断发送的邮件的内容
     * @param to 邮件接收者
     * @param content 邮件内容
     * @param isHtml 是否发送html
     * @throws MessagingException
     * @throws IOException
     * @throws FileNotFoundException
     * @throws EmailException
     */
    private void sendEmail(String to, String title, String content, boolean isHtml)
            throws FileNotFoundException, IOException, MessagingException {
    	if(!canUse)
    	{
    		return;
    	}
        String fromer = props.getProperty("fromer");
        if (isHtml) {
            sendHtmlEmail(fromer, to, title, content);
        } else {
            sendTextEmail(fromer, to, title, content);
        }
    }

    // 发送纯文字邮件
    private void sendTextEmail(String from, String to, String subject, String content)
            throws FileNotFoundException, IOException, MessagingException {

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipient(RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setText(content);
        message.setSentDate(new Date());
        Transport.send(message);
    }

    // 发送有HTML格式邮件
    private void sendHtmlEmail(String from, String to, String subject, String htmlConent)
            throws FileNotFoundException, IOException, MessagingException {

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipient(RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setSentDate(new Date());

        Multipart multi = new MimeMultipart();
        BodyPart html = new MimeBodyPart();
        html.setContent(htmlConent, "text/html; charset=utf-8");
        multi.addBodyPart(html);
        message.setContent(multi);
        Transport.send(message);
    }

}