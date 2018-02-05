package cn.wanglin.inspect.el;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tokenizer {
    private static final String[] ALTERNATIVE_OPERATOR_NAMES = new String[]{"DIV", "EQ", "GE", "GT", "LE", "LT", "MOD", "NE", "NOT"};
    private static final byte[]   FLAGS                      = new byte[256];
    private String expressionString;
    private char[] charsToProcess;
    private int    pos;
    private int    max;
    private List<Token> tokens = new ArrayList();

    public Tokenizer(String inputData) {
        this.expressionString = inputData;
        this.charsToProcess = (inputData + "\u0000").toCharArray();
        this.max = this.charsToProcess.length;
        this.pos = 0;
    }

    public List<Token> process() {
        while (this.pos < this.max) {
            char ch = this.charsToProcess[this.pos];
            if (this.isAlphabetic(ch)) {
                this.lexIdentifier();
            } else {
                switch (ch) {
                    case '\u0000':
                        ++this.pos;
                        break;
                    case '\u0001':
                    case '\u0002':
                    case '\u0003':
                    case '\u0004':
                    case '\u0005':
                    case '\u0006':
                    case '\u0007':
                    case '\b':
                    case '\u000b':
                    case '\f':
                    case '\u000e':
                    case '\u000f':
                    case '\u0010':
                    case '\u0011':
                    case '\u0012':
                    case '\u0013':
                    case '\u0014':
                    case '\u0015':
                    case '\u0016':
                    case '\u0017':
                    case '\u0018':
                    case '\u0019':
                    case '\u001a':
                    case '\u001b':
                    case '\u001c':
                    case '\u001d':
                    case '\u001e':
                    case '\u001f':
                    case ';':
                    case 'A':
                    case 'B':
                    case 'C':
                    case 'D':
                    case 'E':
                    case 'F':
                    case 'G':
                    case 'H':
                    case 'I':
                    case 'J':
                    case 'K':
                    case 'L':
                    case 'M':
                    case 'N':
                    case 'O':
                    case 'P':
                    case 'Q':
                    case 'R':
                    case 'S':
                    case 'T':
                    case 'U':
                    case 'V':
                    case 'W':
                    case 'X':
                    case 'Y':
                    case 'Z':
                    case '`':
                    case 'a':
                    case 'b':
                    case 'c':
                    case 'd':
                    case 'e':
                    case 'f':
                    case 'g':
                    case 'h':
                    case 'i':
                    case 'j':
                    case 'k':
                    case 'l':
                    case 'm':
                    case 'n':
                    case 'o':
                    case 'p':
                    case 'q':
                    case 'r':
                    case 's':
                    case 't':
                    case 'u':
                    case 'v':
                    case 'w':
                    case 'x':
                    case 'y':
                    case 'z':
                    default:
                        throw new IllegalStateException("Cannot handle (" + Integer.valueOf(ch) + ") '" + ch + "'");
                    case '\t':
                    case '\n':
                    case '\r':
                    case ' ':
                        ++this.pos;
                        break;
                    case '!':
                        if (this.isTwoCharToken(TokenKind.NE)) {
                            this.pushPairToken(TokenKind.NE);
                        } else {
                            if (this.isTwoCharToken(TokenKind.PROJECT)) {
                                this.pushPairToken(TokenKind.PROJECT);
                                continue;
                            }

                            this.pushCharToken(TokenKind.NOT);
                        }
                        break;
                    case '"':
                        this.lexDoubleQuotedStringLiteral();
                        break;
                    case '#':
                        this.pushCharToken(TokenKind.HASH);
                        break;
                    case '$':
                        if (this.isTwoCharToken(TokenKind.SELECT_LAST)) {
                            this.pushPairToken(TokenKind.SELECT_LAST);
                            break;
                        }

                        this.lexIdentifier();
                        break;
                    case '%':
                        this.pushCharToken(TokenKind.MOD);
                        break;
                    case '&':
                        if (this.isTwoCharToken(TokenKind.SYMBOLIC_AND)) {
                            this.pushPairToken(TokenKind.SYMBOLIC_AND);
                            break;
                        }

                        this.pushCharToken(TokenKind.FACTORY_BEAN_REF);
                        break;
                    case '\'':
                        this.lexQuotedStringLiteral();
                        break;
                    case '(':
                        this.pushCharToken(TokenKind.LPAREN);
                        break;
                    case ')':
                        this.pushCharToken(TokenKind.RPAREN);
                        break;
                    case '*':
                        this.pushCharToken(TokenKind.STAR);
                        break;
                    case '+':
                        if (this.isTwoCharToken(TokenKind.INC)) {
                            this.pushPairToken(TokenKind.INC);
                            break;
                        }

                        this.pushCharToken(TokenKind.PLUS);
                        break;
                    case ',':
                        this.pushCharToken(TokenKind.COMMA);
                        break;
                    case '-':
                        if (this.isTwoCharToken(TokenKind.DEC)) {
                            this.pushPairToken(TokenKind.DEC);
                            break;
                        }

                        this.pushCharToken(TokenKind.MINUS);
                        break;
                    case '.':
                        this.pushCharToken(TokenKind.DOT);
                        break;
                    case '/':
                        this.pushCharToken(TokenKind.DIV);
                        break;
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        this.lexNumericLiteral(ch == '0');
                        break;
                    case ':':
                        this.pushCharToken(TokenKind.COLON);
                        break;
                    case '<':
                        if (this.isTwoCharToken(TokenKind.LE)) {
                            this.pushPairToken(TokenKind.LE);
                            break;
                        }

                        this.pushCharToken(TokenKind.LT);
                        break;
                    case '=':
                        if (this.isTwoCharToken(TokenKind.EQ)) {
                            this.pushPairToken(TokenKind.EQ);
                            break;
                        }

                        this.pushCharToken(TokenKind.ASSIGN);
                        break;
                    case '>':
                        if (this.isTwoCharToken(TokenKind.GE)) {
                            this.pushPairToken(TokenKind.GE);
                            break;
                        }

                        this.pushCharToken(TokenKind.GT);
                        break;
                    case '?':
                        if (this.isTwoCharToken(TokenKind.SELECT)) {
                            this.pushPairToken(TokenKind.SELECT);
                        } else if (this.isTwoCharToken(TokenKind.ELVIS)) {
                            this.pushPairToken(TokenKind.ELVIS);
                        } else {
                            if (this.isTwoCharToken(TokenKind.SAFE_NAVI)) {
                                this.pushPairToken(TokenKind.SAFE_NAVI);
                                continue;
                            }

                            this.pushCharToken(TokenKind.QMARK);
                        }
                        break;
                    case '@':
                        this.pushCharToken(TokenKind.BEAN_REF);
                        break;
                    case '[':
                        this.pushCharToken(TokenKind.LSQUARE);
                        break;
                    case '\\':
                        this.raiseParseException(this.pos, SpelMessage.UNEXPECTED_ESCAPE_CHAR);
                        break;
                    case ']':
                        this.pushCharToken(TokenKind.RSQUARE);
                        break;
                    case '^':
                        if (this.isTwoCharToken(TokenKind.SELECT_FIRST)) {
                            this.pushPairToken(TokenKind.SELECT_FIRST);
                            break;
                        }

                        this.pushCharToken(TokenKind.POWER);
                        break;
                    case '_':
                        this.lexIdentifier();
                        break;
                    case '{':
                        this.pushCharToken(TokenKind.LCURLY);
                        break;
                    case '|':
                        if (!this.isTwoCharToken(TokenKind.SYMBOLIC_OR)) {
                            this.raiseParseException(this.pos, SpelMessage.MISSING_CHARACTER, "|");
                        }

                        this.pushPairToken(TokenKind.SYMBOLIC_OR);
                        break;
                    case '}':
                        this.pushCharToken(TokenKind.RCURLY);
                }
            }
        }

        return this.tokens;
    }

    private void lexQuotedStringLiteral() {
        int     start      = this.pos;
        boolean terminated = false;

        while (!terminated) {
            ++this.pos;
            char ch = this.charsToProcess[this.pos];
            if (ch == '\'') {
                if (this.charsToProcess[this.pos + 1] == '\'') {
                    ++this.pos;
                } else {
                    terminated = true;
                }
            }

            if (this.isExhausted()) {
                this.raiseParseException(start, SpelMessage.NON_TERMINATING_QUOTED_STRING);
            }
        }

        ++this.pos;
        this.tokens.add(new Token(TokenKind.LITERAL_STRING, this.subarray(start, this.pos),
                this.tokens.size() > 1 ? this.tokens.get(this.tokens.size() - 1) : null));
    }

    private void lexDoubleQuotedStringLiteral() {
        int     start      = this.pos;
        boolean terminated = false;

        while (!terminated) {
            ++this.pos;
            char ch = this.charsToProcess[this.pos];
            if (ch == '"') {
                if (this.charsToProcess[this.pos + 1] == '"') {
                    ++this.pos;
                } else {
                    terminated = true;
                }
            }

            if (this.isExhausted()) {
                this.raiseParseException(start, SpelMessage.NON_TERMINATING_DOUBLE_QUOTED_STRING);
            }
        }

        ++this.pos;
        this.tokens.add(new Token(TokenKind.LITERAL_STRING, this.subarray(start, this.pos),
                this.tokens.size() > 1 ? this.tokens.get(this.tokens.size() - 1) : null));
    }

    private void lexNumericLiteral(boolean firstCharIsZero) {
        boolean isReal = false;
        int     start  = this.pos;
        char    ch     = this.charsToProcess[this.pos + 1];
        boolean isHex  = ch == 'x' || ch == 'X';
        if (firstCharIsZero && isHex) {
            ++this.pos;

            do {
                ++this.pos;
            } while (this.isHexadecimalDigit(this.charsToProcess[this.pos]));

            if (this.isChar('L', 'l')) {
                this.pushHexIntToken(this.subarray(start + 2, this.pos), true, start, this.pos);
                ++this.pos;
            } else {
                this.pushHexIntToken(this.subarray(start + 2, this.pos), false, start, this.pos);
            }

        } else {
            do {
                ++this.pos;
            } while (this.isDigit(this.charsToProcess[this.pos]));

            ch = this.charsToProcess[this.pos];
            int endOfNumber;
            if (ch == '.') {
                isReal = true;
                endOfNumber = this.pos;

                do {
                    ++this.pos;
                } while (this.isDigit(this.charsToProcess[this.pos]));

                if (this.pos == endOfNumber + 1) {
                    this.pos = endOfNumber;
                    this.pushIntToken(this.subarray(start, this.pos), false, start, this.pos);
                    return;
                }
            }

            endOfNumber = this.pos;
            if (this.isChar('L', 'l')) {
                if (isReal) {
                    this.raiseParseException(start, SpelMessage.REAL_CANNOT_BE_LONG);
                }

                this.pushIntToken(this.subarray(start, endOfNumber), true, start, endOfNumber);
                ++this.pos;
            } else if (this.isExponentChar(this.charsToProcess[this.pos])) {
                isReal = true;
                ++this.pos;
                char possibleSign = this.charsToProcess[this.pos];
                if (this.isSign(possibleSign)) {
                    ++this.pos;
                }

                do {
                    ++this.pos;
                } while (this.isDigit(this.charsToProcess[this.pos]));

                boolean isFloat = false;
                if (this.isFloatSuffix(this.charsToProcess[this.pos])) {
                    isFloat = true;
                    endOfNumber = ++this.pos;
                } else if (this.isDoubleSuffix(this.charsToProcess[this.pos])) {
                    endOfNumber = ++this.pos;
                }

                this.pushRealToken(this.subarray(start, this.pos), isFloat, start, this.pos);
            } else {
                ch = this.charsToProcess[this.pos];
                boolean isFloat = false;
                if (this.isFloatSuffix(ch)) {
                    isReal = true;
                    isFloat = true;
                    endOfNumber = ++this.pos;
                } else if (this.isDoubleSuffix(ch)) {
                    isReal = true;
                    endOfNumber = ++this.pos;
                }

                if (isReal) {
                    this.pushRealToken(this.subarray(start, endOfNumber), isFloat, start, endOfNumber);
                } else {
                    this.pushIntToken(this.subarray(start, endOfNumber), false, start, endOfNumber);
                }
            }

        }
    }

    private void lexIdentifier() {
        int start = this.pos;

        do {
            ++this.pos;
        } while (this.isIdentifier(this.charsToProcess[this.pos]));

        char[] subarray = this.subarray(start, this.pos);
        if (this.pos - start == 2 || this.pos - start == 3) {
            String asString = (new String(subarray)).toUpperCase();
            int    idx      = Arrays.binarySearch(ALTERNATIVE_OPERATOR_NAMES, asString);
            if (idx >= 0) {
                this.pushOneCharOrTwoCharToken(TokenKind.valueOf(asString), start, subarray);
                return;
            }
        }

        this.tokens.add(new Token(TokenKind.IDENTIFIER, subarray,
                this.tokens.size() > 1 ? this.tokens.get(this.tokens.size() - 1) : null));
    }

    private void pushIntToken(char[] data, boolean isLong, int start, int end) {
        if (isLong) {
            this.tokens.add(new Token(TokenKind.LITERAL_LONG, data,
                    this.tokens.size() > 1 ? this.tokens.get(this.tokens.size() - 1) : null));
        } else {
            this.tokens.add(new Token(TokenKind.LITERAL_INT, data,
                    this.tokens.size() > 1 ? this.tokens.get(this.tokens.size() - 1) : null));
        }

    }

    private void pushHexIntToken(char[] data, boolean isLong, int start, int end) {
        if (data.length == 0) {
            if (isLong) {
                this.raiseParseException(start, SpelMessage.NOT_A_LONG, this.expressionString.substring(start, end + 1));
            } else {
                this.raiseParseException(start, SpelMessage.NOT_AN_INTEGER, this.expressionString.substring(start, end));
            }
        }

        if (isLong) {
            this.tokens.add(new Token(TokenKind.LITERAL_HEXLONG, data,
                    this.tokens.size() > 1 ? this.tokens.get(this.tokens.size() - 1) : null));
        } else {
            this.tokens.add(new Token(TokenKind.LITERAL_HEXINT, data,
                    this.tokens.size() > 1 ? this.tokens.get(this.tokens.size() - 1) : null));
        }

    }

    private void pushRealToken(char[] data, boolean isFloat, int start, int end) {
        if (isFloat) {
            this.tokens.add(new Token(TokenKind.LITERAL_REAL_FLOAT, data,
                    this.tokens.size() > 1 ? this.tokens.get(this.tokens.size() - 1) : null));
        } else {
            this.tokens.add(new Token(TokenKind.LITERAL_REAL, data,
                    this.tokens.size() > 1 ? this.tokens.get(this.tokens.size() - 1) : null));
        }

    }

    private char[] subarray(int start, int end) {
        char[] result = new char[end - start];
        System.arraycopy(this.charsToProcess, start, result, 0, end - start);
        return result;
    }

    private boolean isTwoCharToken(TokenKind kind) {
        return kind.tokenChars.length == 2 && this.charsToProcess[this.pos] == kind.tokenChars[0] && this.charsToProcess[this.pos + 1] == kind.tokenChars[1];
    }

    private void pushCharToken(TokenKind kind) {
        this.tokens.add(new Token(kind, this.tokens.size() > 1 ? this.tokens.get(this.tokens.size() - 1) : null));
        ++this.pos;
    }

    private void pushPairToken(TokenKind kind) {
        this.tokens.add(new Token(kind, this.tokens.size() > 1 ? this.tokens.get(this.tokens.size() - 1) : null));
        this.pos += 2;
    }

    private void pushOneCharOrTwoCharToken(TokenKind kind, int pos, char[] data) {
        this.tokens.add(new Token(kind, data, this.tokens.size() > 1 ? this.tokens.get(this.tokens.size() - 1) : null));
    }

    private boolean isIdentifier(char ch) {
        return this.isAlphabetic(ch) || this.isDigit(ch) || ch == '_' || ch == '$';
    }

    private boolean isChar(char a, char b) {
        char ch = this.charsToProcess[this.pos];
        return ch == a || ch == b;
    }

    private boolean isExponentChar(char ch) {
        return ch == 'e' || ch == 'E';
    }

    private boolean isFloatSuffix(char ch) {
        return ch == 'f' || ch == 'F';
    }

    private boolean isDoubleSuffix(char ch) {
        return ch == 'd' || ch == 'D';
    }

    private boolean isSign(char ch) {
        return ch == '+' || ch == '-';
    }

    private boolean isDigit(char ch) {
        if (ch > 255) {
            return false;
        } else {
            return (FLAGS[ch] & 1) != 0;
        }
    }

    private boolean isAlphabetic(char ch) {
        if (ch > 255) {
            return false;
        } else {
            return (FLAGS[ch] & 4) != 0;
        }
    }

    private boolean isHexadecimalDigit(char ch) {
        if (ch > 255) {
            return false;
        } else {
            return (FLAGS[ch] & 2) != 0;
        }
    }

    private boolean isExhausted() {
        return this.pos == this.max - 1;
    }

    private void raiseParseException(int start, SpelMessage msg, Object... inserts) {
        throw new InternalParseException(new SpelParseException(this.expressionString, msg, inserts));
    }

    static {
        int ch;
        for (ch = 48; ch <= 57; ++ch) {
            FLAGS[ch] = (byte) (FLAGS[ch] | 3);
        }

        for (ch = 65; ch <= 70; ++ch) {
            FLAGS[ch] = (byte) (FLAGS[ch] | 2);
        }

        for (ch = 97; ch <= 102; ++ch) {
            FLAGS[ch] = (byte) (FLAGS[ch] | 2);
        }

        for (ch = 65; ch <= 90; ++ch) {
            FLAGS[ch] = (byte) (FLAGS[ch] | 4);
        }

        for (ch = 97; ch <= 122; ++ch) {
            FLAGS[ch] = (byte) (FLAGS[ch] | 4);
        }

    }
}