package com.juniordevmind.shared.constants;

public class RabbitMQKeys {
    // author created event
    public static final String AUTHOR_CREATED_EXCHANGE = "message.exchange.fanout.author.created";
    public static final String BOOK_API_AUTHOR_CREATED_QUEUE = "book-api.message.queue.author.created";
    public static final String COMMENT_API_AUTHOR_CREATED_QUEUE = "comment-api.message.queue.author.created";
    // book created event
    public static final String BOOK_CREATED_EXCHANGE = "message.exchange.fanout.boo.created";
    public static final String AUTHOR_API_BOOK_CREATED_QUEUE = "author-api.message.queue.book.created";
    public static final String COMMENT_API_BOOK_CREATED_QUEUE = "comment-api.message.queue.book.created";
}
