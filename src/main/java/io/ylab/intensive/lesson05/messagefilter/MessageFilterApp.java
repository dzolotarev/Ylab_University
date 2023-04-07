package io.ylab.intensive.lesson05.messagefilter;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;

public class MessageFilterApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();

        File badWordsList = new File("/Users/denis/Downloads/badwordslist.txt");

        try {
            DbClient dbClient = applicationContext.getBean(DbClient.class);
            dbClient.uploadBadWordsToDb(badWordsList);
            MessageProcessor processor = applicationContext.getBean(MessageProcessor.class);
            processor.start();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
