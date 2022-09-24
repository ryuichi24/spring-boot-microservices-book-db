package com.juniordevmind.shared.constants;

public class RabbitMQKeys {
    // author created event
    public static final String AUTHOR_CREATED_EXCHANGE = "message.exchange.fanout.author.created";
    public static final String BOOK_API_AUTHOR_CREATED_QUEUE = "book-api.message.queue.author.created";
    public static final String COMMENT_API_AUTHOR_CREATED_QUEUE = "comment-api.message.queue.author.created";
    // author updated event
    public static final String AUTHOR_UPDATED_EXCHANGE = "message.exchange.fanout.author.updated";
    public static final String BOOK_API_AUTHOR_UPDATED_QUEUE = "book-api.message.queue.author.updated";
    public static final String COMMENT_API_AUTHOR_UPDATED_QUEUE = "comment-api.message.queue.author.updated";
    // book created event
    public static final String BOOK_CREATED_EXCHANGE = "message.exchange.fanout.book.created";
    public static final String AUTHOR_API_BOOK_CREATED_QUEUE = "author-api.message.queue.book.created";
    public static final String COMMENT_API_BOOK_CREATED_QUEUE = "comment-api.message.queue.book.created";
}
