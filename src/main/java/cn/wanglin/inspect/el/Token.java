package cn.wanglin.inspect.el;


public class Token {
    public TokenKind kind;
    public String    data;
    public Token     last;


    Token(TokenKind tokenKind, Token last) {
        this.kind = tokenKind;
        this.last = last;
    }

    Token(TokenKind tokenKind, char[] tokenData, Token last) {
        this(tokenKind, last);
        this.data = new String(tokenData);
    }


    public boolean isRootIdentifier() {
        return this.kind == TokenKind.IDENTIFIER && (this.last == null || !this.last.kind.equals(TokenKind.DOT));
    }

    @Override
    public String toString() {
        return "Token{" +
                "kind=" + kind +
                ", data='" + data + '\'' +
                ", last=" + (null == last ? null : last.kind) +
                '}';
    }
}