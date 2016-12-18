package com.piana.message;

import com.piana.restModel.Email;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

/**
 * Created by yuwang on 12/4/16.
 */
public class TestMessage {

    private static final Logger logger = LoggerFactory.getLogger(TestMessage.class);


    @Autowired
    JmsTemplate jmsTemplate;


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    @Ignore
    public void testGetIsLogin() throws Exception {

        System.out.println("Sending an email message.");
        jmsTemplate.convertAndSend("mailbox", new Email("info@example.com", "Hello"));
    }
}
