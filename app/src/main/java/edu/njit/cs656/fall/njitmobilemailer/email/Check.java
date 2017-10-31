package edu.njit.cs656.fall.njitmobilemailer.email;

/**
 * Created by Eugen on 10/13/2017.
 */


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import edu.njit.cs656.fall.njitmobilemailer.auth.Authentication;

public class Check {

    private static final String TAG = "CheckMail";
    private Message[] messages;
    private Lock lock;

    public Check() {
        lock = new ReentrantLock();
    }

    public synchronized Message[] getMessages() {
        return messages;
    }

    private synchronized void setMessages(Message[] messages) {
        messages = messages;
    }

    public synchronized int getNumberMessages() {
        return messages.length;
    }

    public synchronized boolean isEmptyMessages() {
        return messages.length == 0;
    }

    public void setCallback(MessageService messageService) {
        messages = messageService.callback();
    }

    public void Email() {
        Thread sub = new Thread(new Runnable() {
            @Override
            public void run() {
                setCallback(new MessageService() {
                    @Override
                    public Message[] callback() {
                        try {
                            Authentication authentication = new Authentication();

                            Session emailSession = Session.getDefaultInstance(authentication.getIMAPProperties());

                            Store store = emailSession.getStore("imaps");

                            store.connect("imap.gmail.com", authentication.getUsername(), authentication.getPassword());

                            Folder emailFolder = store.getFolder("INBOX");

                            emailFolder.open(Folder.READ_WRITE);

                            Message[] subMessages = emailFolder.getMessages();

                            emailFolder.close(false);
                            store.close();

                            return subMessages.clone();
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                });
            }
        });

        try

        {
            sub.start();
            sub.join();
        } catch (InterruptedException e)

        {
            e.printStackTrace();
        }
    }

    private interface MessageService {
        Message[] callback();
    }
}
