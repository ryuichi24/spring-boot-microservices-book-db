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
    // author deleted event
    public static final String AUTHOR_DELETED_EXCHANGE = "message.exchange.fanout.author.deleted";
    public static final String BOOK_API_AUTHOR_DELETED_QUEUE = "book-api.message.queue.author.deleted";
    public static final String COMMENT_API_AUTHOR_DELETED_QUEUE = "comment-api.message.queue.author.deleted";
    // book created event
    public static final String BOOK_CREATED_EXCHANGE = "message.exchange.fanout.book.created";
    public static final String AUTHOR_API_BOOK_CREATED_QUEUE = "author-api.message.queue.book.created";
    public static final String COMMENT_API_BOOK_CREATED_QUEUE = "comment-api.message.queue.book.created";
    // book updated event
    public static final String BOOK_UPDATED_EXCHANGE = "message.exchange.fanout.book.updated";
    public static final String AUTHOR_API_BOOK_UPDATED_QUEUE = "author-api.message.queue.book.updated";
    public static final String COMMENT_API_BOOK_UPDATED_QUEUE = "comment-api.message.queue.book.updated";
    // book deleted event
    public static final String BOOK_DELETED_EXCHANGE = "message.exchange.fanout.book.deleted";
    public static final String AUTHOR_API_BOOK_DELETED_QUEUE = "author-api.message.queue.book.deleted";
    public static final String COMMENT_API_BOOK_DELETED_QUEUE = "comment-api.message.queue.book.deleted";
}
