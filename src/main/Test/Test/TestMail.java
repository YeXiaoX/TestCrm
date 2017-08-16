/**
 * Created by yexiaoxin on 2017/8/1.
 */

import com.sun.mail.imap.IMAPMessage;

import java.io.*;
import java.security.Security;
import java.util.Enumeration;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;

public class TestMail {
    private static String host = "mail.qq.com";
    private static final String IMAP = "imap";

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();

        props.setProperty("mail.store.protocol", "imap");
        props.setProperty("mail.imap.host", "imap.exmail.qq.com");
        props.setProperty("mail.imap.port", "143");
        props.setProperty("mail.imap.ssl.enable", "true");

        // 创建Session实例对象
        Session session = Session.getInstance(props);

        // 创建IMAP协议的Store对象
        Store store = session.getStore("imap");

        // 连接邮件服务器
        store.connect("2837713599@qq.com", "hiklaxdlrpczdfee");

        // 获得收件箱
        Folder folder = store.getFolder("INBOX");
        // 以读写模式打开收件箱
        folder.open(Folder.READ_WRITE);

        // 获得收件箱的邮件列表
        Message[] messages = folder.getMessages();

        // 打印不同状态的邮件数量
        System.out.println("收件箱中共" + messages.length + "封邮件!");
        System.out.println("收件箱中共" + folder.getUnreadMessageCount() + "封未读邮件!");
        System.out.println("收件箱中共" + folder.getNewMessageCount() + "封新邮件!");
        System.out.println("收件箱中共" + folder.getDeletedMessageCount() + "封已删除邮件!");

    }

    public static void receiveQQEmail(String username, String password) throws Exception {

        String host = "mail.sogou-inc.com";
        String port = "143";

        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        Properties props = System.getProperties();
        props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
//  props.setProperty("mail.imap.socketFactory.fallback", "false");
//     props.setProperty("mail.imap.port", port);
        props.setProperty("mail.imap.socketFactory.port", port);


        props.setProperty("mail.store.protocol", "imap");
        props.setProperty("mail.imap.host", host);
        props.setProperty("mail.imap.port", port);
        props.setProperty("mail.imap.auth.login.disable", "true");
        Session session = Session.getDefaultInstance(props, null);
        session.setDebug(false);
        Store store = session.getStore(IMAP);
        store.connect(host, username, password);
        Folder inbox = null;
        try {

            inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_ONLY);
            FetchProfile profile = new FetchProfile();
            profile.add(FetchProfile.Item.ENVELOPE);
            Message[] messages = inbox.getMessages();
            inbox.fetch(messages, profile);
            System.out.println("收件箱的邮件数：" + messages.length);

            IMAPMessage msg;
            for (Message message : messages) {
                msg = (IMAPMessage) message;
                String from = decodeText(msg.getFrom()[0].toString());
                InternetAddress ia = new InternetAddress(from);
                System.out.println("FROM:" + ia.getPersonal() + '(' + ia.getAddress() + ')');
                System.out.println("TITLE:" + msg.getSubject());
                System.out.println("SIZE:" + msg.getSize());
                System.out.println("DATE:" + msg.getSentDate());
                Enumeration headers = msg.getAllHeaders();
                System.out.println("----------------------allHeaders-----------------------------");
                while (headers.hasMoreElements()) {
                    Header header = (Header) headers.nextElement();
                    System.out.println(header.getName() + " ======= " + header.getValue());
                }
                parseMultipart((Multipart) msg.getContent());
            }

        } finally {
            try {
                if (inbox != null) {
                    inbox.close(false);
                }
            } catch (Exception ignored) {
            }
            try {
                store.close();
            } catch (Exception ignored) {
            }
        }
    }

    protected static String decodeText(String text) throws UnsupportedEncodingException {
        if (text == null)
            return null;
        if (text.startsWith("=?GB") || text.startsWith("=?gb"))
            text = MimeUtility.decodeText(text);
        else
            text = new String(text.getBytes("ISO8859_1"));
        return text;
    }


    /**
     * 对复杂邮件的解析
     *
     * @param multipart
     * @throws MessagingException
     * @throws IOException
     */
    public static void parseMultipart(Multipart multipart) throws MessagingException, IOException {
        int count = multipart.getCount();
        System.out.println("couont =  " + count);
        for (int idx = 0; idx < count; idx++) {
            BodyPart bodyPart = multipart.getBodyPart(idx);
            System.out.println(bodyPart.getContentType());
            if (bodyPart.isMimeType("text/plain")) {
                System.out.println("plain................." + bodyPart.getContent());
            } else if (bodyPart.isMimeType("text/html")) {
                System.out.println("html..................." + bodyPart.getContent());
            } else if (bodyPart.isMimeType("multipart/*")) {
                Multipart mpart = (Multipart) bodyPart.getContent();
                parseMultipart(mpart);

            } else if (bodyPart.isMimeType("application/octet-stream")) {
                String disposition = bodyPart.getDisposition();
                System.out.println(disposition);
                if (disposition.equalsIgnoreCase(BodyPart.ATTACHMENT)) {
                    String fileName = bodyPart.getFileName();
                    InputStream is = bodyPart.getInputStream();
                    copy(is, new FileOutputStream("D:\\" + fileName));
                }
            }
        }
    }

    /**
     * 文件拷贝，在用户进行附件下载的时候，可以把附件的InputStream传给用户进行下载
     *
     * @param is
     * @param os
     * @throws IOException
     */
    public static void copy(InputStream is, OutputStream os) throws IOException {
        byte[] bytes = new byte[1024];
        int len;
        while ((len = is.read(bytes)) != -1) {
            os.write(bytes, 0, len);
        }
        if (os != null)
            os.close();
        is.close();
    }

    /**
     * 解析综合数据
     *
     * @param part
     * @throws Exception
     */
    private static void getAllMultipart(Part part) throws Exception {
        String contentType = part.getContentType();
        int index = contentType.indexOf("name");
        boolean conName = false;
        if (index != -1) {
            conName = true;
        }
        //判断part类型
        if (part.isMimeType("text/plain") && !conName) {
            System.out.println((String) part.getContent());
        } else if (part.isMimeType("text/html") && !conName) {
            System.out.println((String) part.getContent());
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int counts = multipart.getCount();
            for (int i = 0; i < counts; i++) {
                //递归获取数据
                getAllMultipart(multipart.getBodyPart(i));
                //附件可能是截图或上传的(图片或其他数据)
                if (multipart.getBodyPart(i).getDisposition() != null) {
                    //附件为截图
                    if (multipart.getBodyPart(i).isMimeType("image/*")) {
                        InputStream is = multipart.getBodyPart(i)
                                .getInputStream();
                        String name = multipart.getBodyPart(i).getFileName();
                        String fileName;
                        //截图图片
                        if (name.startsWith("=?")) {
                            fileName = name.substring(name.lastIndexOf(".") - 1, name.lastIndexOf("?="));
                        } else {
                            //上传图片
                            fileName = name;
                        }

                        FileOutputStream fos = new FileOutputStream("D:\\"
                                + fileName);
                        int len = 0;
                        byte[] bys = new byte[1024];
                        while ((len = is.read(bys)) != -1) {
                            fos.write(bys, 0, len);
                        }
                        fos.close();
                    } else {
                        //其他附件
                        InputStream is = multipart.getBodyPart(i)
                                .getInputStream();
                        String name = multipart.getBodyPart(i).getFileName();
                        FileOutputStream fos = new FileOutputStream("D:\\"
                                + name);
                        int len = 0;
                        byte[] bys = new byte[1024];
                        while ((len = is.read(bys)) != -1) {
                            fos.write(bys, 0, len);
                        }
                        fos.close();
                    }
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            getAllMultipart((Part) part.getContent());
        }
    }

    /**
     * 解析附件内容
     *
     * @param part
     * @throws Exception
     */
    private static void getAttachmentMultipart(Part part) throws Exception {
        if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int count = multipart.getCount();
            for (int i = 0; i < count; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.getDisposition() != null) {
                    InputStream is = bodyPart.getInputStream();
                    FileOutputStream fos = new FileOutputStream("路径+文件名");
                    int len = 0;
                    byte[] bys = new byte[1024];
                    while ((len = is.read(bys)) != -1) {
                        fos.write(bys, 0, len);
                    }
                    fos.close();
                }
            }
        }

    }

    /**
     * 解析图片内容
     *
     * @param part
     * @throws Exception
     */
    private static void getPicMultipart(Part part) throws Exception {
        if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int count = multipart.getCount();
            for (int i = 0; i < count; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.isMimeType("image/*")) {
                    InputStream is = bodyPart.getInputStream();
                    FileOutputStream fos = new FileOutputStream("路径+文件名");
                    int len = 0;
                    byte[] bys = new byte[1024];
                    while ((len = is.read(bys)) != -1) {
                        fos.write(bys, 0, len);
                    }
                    fos.close();
                }
            }
        }
    }

    /**
     * 解析文本内容
     *
     * @param part
     * @throws Exception
     */
    private static void getTextMultipart(Part part) throws Exception {
        if (part.isMimeType("text/html")) {
            String content = (String) part.getContent();
            System.out.println(content);
        } else if (part.isMimeType("text/plain")) {
            String content = (String) part.getContent();
            System.out.println(content);
        }
    }
}

