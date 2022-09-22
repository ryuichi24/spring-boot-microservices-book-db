package com.juniordevmind.shared.constants;

public class RabbitMQKeys {
    public static final String AUTHOR_EXCHANGE = "message.exchange.fanout.authors.events";
    public static final String BOOK_API_QUEUE = "bookapi.message.queue.authors.created";
    public static final String COMMENT_API_QUEUE = "commentapi.message.queue.authors.created";
}
