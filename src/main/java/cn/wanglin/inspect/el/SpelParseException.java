package cn.wanglin.inspect.el;

public class SpelParseException extends RuntimeException {
    protected     String      expressionString;
    private final SpelMessage message;
    private final Object[]    inserts;


    public SpelParseException(String expressionString, SpelMessage message, Object... inserts) {
        super(message.formatMessage(inserts));
        this.expressionString = expressionString;
        this.message = message;
        this.inserts = inserts;
    }

    public SpelParseException(SpelMessage message, Object... inserts) {
        super(message.formatMessage(inserts));
        this.message = message;
        this.inserts = inserts;
    }

    public SpelParseException(Throwable cause, SpelMessage message, Object... inserts) {
        super(message.formatMessage(inserts), cause);
        this.message = message;
        this.inserts = inserts;
    }


    /**
     * Return the message code.
     */
    public SpelMessage getMessageCode() {
        return this.message;
    }

    /**
     * Return the message inserts.
     */
    public Object[] getInserts() {
        return this.inserts;
    }
}
