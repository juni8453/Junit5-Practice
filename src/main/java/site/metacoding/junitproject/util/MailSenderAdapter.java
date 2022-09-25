package site.metacoding.junitproject.util;

import org.springframework.stereotype.Component;

// 추후에 Mail 클래스가 완성되면 코드를 완성하면 끝.
// Mail 클래스가 완성되었다면, MailSenderAdapter 만 수정해주면 본 코드를 수정할 필요가 없어진다.
@Component
public class MailSenderAdapter implements MailSender {

    private final Mail mail;

    public MailSenderAdapter() {
        this.mail = new Mail();
    }

    @Override
    public boolean sendMail() {
        return mail.sendMail();
    }
}
