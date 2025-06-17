package org.rholang.lang.lexer;

import com.intellij.lexer.LexerBase;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.text.CharArrayUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rholang.lang.psi.RholangTypes;

public class RholangLexer extends LexerBase {
    private CharSequence buffer;
    private int startOffset;
    private int endOffset;
    private int position;
    private IElementType currentToken;
    private int tokenEndOffset;

    @Override
    public void start(@NotNull CharSequence buffer, int startOffset, int endOffset, int initialState) {
        this.buffer = buffer;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.position = startOffset;
        advance();
    }

    @Override
    public int getState() {
        return 0;
    }

    @Nullable
    @Override
    public IElementType getTokenType() {
        return currentToken;
    }

    @Override
    public int getTokenStart() {
        return position;
    }

    @Override
    public int getTokenEnd() {
        return tokenEndOffset;
    }

    @Override
    public void advance() {
        if (tokenEndOffset >= endOffset) {
            currentToken = null;
            return;
        }

        position = tokenEndOffset;
        char c = buffer.charAt(position);

        // Handle whitespace
        if (Character.isWhitespace(c)) {
            tokenEndOffset = position + 1;
            while (tokenEndOffset < endOffset && Character.isWhitespace(buffer.charAt(tokenEndOffset))) {
                tokenEndOffset++;
            }
            currentToken = RholangTypes.WHITESPACE;
            return;
        }

        // Line comment
        if (position + 1 < endOffset && c == '/' && buffer.charAt(position + 1) == '/') {
            tokenEndOffset = position + 2;
            while (tokenEndOffset < endOffset && buffer.charAt(tokenEndOffset) != '\n') {
                tokenEndOffset++;
            }
            currentToken = RholangTypes.LINE_COMMENT;
            return;
        }

        // Block comment
        if (position + 1 < endOffset && c == '/' && buffer.charAt(position + 1) == '*') {
            tokenEndOffset = position + 2;
            while (tokenEndOffset + 1 < endOffset && 
                  !(buffer.charAt(tokenEndOffset) == '*' && buffer.charAt(tokenEndOffset + 1) == '/')) {
                tokenEndOffset++;
            }
            if (tokenEndOffset + 1 < endOffset) {
                tokenEndOffset += 2;
            }
            currentToken = RholangTypes.BLOCK_COMMENT;
            return;
        }

        // String literal
        if (c == '"') {
            tokenEndOffset = position + 1;
            boolean escaped = false;
            while (tokenEndOffset < endOffset) {
                char ch = buffer.charAt(tokenEndOffset);
                if (ch == '"' && !escaped) {
                    tokenEndOffset++;
                    break;
                }
                escaped = ch == '\\' && !escaped;
                tokenEndOffset++;
            }
            currentToken = RholangTypes.STRING_LITERAL;
            return;
        }

        // URI literal
        if (c == '`') {
            tokenEndOffset = position + 1;
            while (tokenEndOffset < endOffset && buffer.charAt(tokenEndOffset) != '`') {
                tokenEndOffset++;
            }
            if (tokenEndOffset < endOffset) {
                tokenEndOffset++;
            }
            currentToken = RholangTypes.URI_LITERAL;
            return;
        }

        // Identifiers and keywords
        if (Character.isLetter(c) || c == '_') {
            tokenEndOffset = position + 1;
            while (tokenEndOffset < endOffset && 
                  (Character.isLetterOrDigit(buffer.charAt(tokenEndOffset)) || 
                   buffer.charAt(tokenEndOffset) == '_' || 
                   buffer.charAt(tokenEndOffset) == '\'')) {
                tokenEndOffset++;
            }

            String tokenText = buffer.subSequence(position, tokenEndOffset).toString();

            // Check for keywords
            switch (tokenText) {
                case "new": currentToken = RholangTypes.NEW_KEYWORD; break;
                case "in": currentToken = RholangTypes.IN_KEYWORD; break;
                case "for": currentToken = RholangTypes.FOR_KEYWORD; break;
                case "match": currentToken = RholangTypes.MATCH_KEYWORD; break;
                case "select": currentToken = RholangTypes.SELECT_KEYWORD; break;
                case "contract": currentToken = RholangTypes.CONTRACT_KEYWORD; break;
                case "if": currentToken = RholangTypes.IF_KEYWORD; break;
                case "else": currentToken = RholangTypes.ELSE_KEYWORD; break;
                case "let": currentToken = RholangTypes.LET_KEYWORD; break;
                case "true": currentToken = RholangTypes.TRUE_KEYWORD; break;
                case "false": currentToken = RholangTypes.FALSE_KEYWORD; break;
                case "Nil": currentToken = RholangTypes.NIL_KEYWORD; break;
                default: currentToken = RholangTypes.IDENTIFIER;
            }
            return;
        }

        // Numbers
        if (Character.isDigit(c) || (c == '-' && position + 1 < endOffset && Character.isDigit(buffer.charAt(position + 1)))) {
            tokenEndOffset = position + 1;
            while (tokenEndOffset < endOffset && Character.isDigit(buffer.charAt(tokenEndOffset))) {
                tokenEndOffset++;
            }
            currentToken = RholangTypes.LONG_LITERAL;
            return;
        }

        // Operators and punctuation
        tokenEndOffset = position + 1;
        switch (c) {
            case '+': currentToken = RholangTypes.PLUS; break;
            case '-': currentToken = RholangTypes.MINUS; break;
            case '*': currentToken = RholangTypes.MULTIPLY; break;
            case '/': currentToken = RholangTypes.DIVIDE; break;
            case '%': currentToken = RholangTypes.MODULO; break;
            case '(': currentToken = RholangTypes.LPAREN; break;
            case ')': currentToken = RholangTypes.RPAREN; break;
            case '{': currentToken = RholangTypes.LBRACE; break;
            case '}': currentToken = RholangTypes.RBRACE; break;
            case '[': currentToken = RholangTypes.LBRACKET; break;
            case ']': currentToken = RholangTypes.RBRACKET; break;
            case ',': currentToken = RholangTypes.COMMA; break;
            case ';': currentToken = RholangTypes.SEMICOLON; break;
            case ':': currentToken = RholangTypes.COLON; break;
            case '.': currentToken = RholangTypes.DOT; break;
            case '|': currentToken = RholangTypes.PIPE; break;
            case '@': currentToken = RholangTypes.AT; break;
            case '<': currentToken = RholangTypes.LESS_THAN; break;
            case '>': currentToken = RholangTypes.GREATER_THAN; break;
            default: currentToken = RholangTypes.BAD_CHARACTER;
        }

        // Check for multi-character operators
        if (position + 1 < endOffset) {
            char nextChar = buffer.charAt(position + 1);
            if (c == '=' && nextChar == '=') {
                tokenEndOffset++;
                currentToken = RholangTypes.EQUALS;
            } else if (c == '!' && nextChar == '=') {
                tokenEndOffset++;
                currentToken = RholangTypes.NOT_EQUALS;
            } else if (c == '<' && nextChar == '=') {
                tokenEndOffset++;
                currentToken = RholangTypes.LESS_THAN_OR_EQUAL;
            } else if (c == '>' && nextChar == '=') {
                tokenEndOffset++;
                currentToken = RholangTypes.GREATER_THAN_OR_EQUAL;
            }
        }
    }

    @NotNull
    @Override
    public CharSequence getBufferSequence() {
        return buffer;
    }

    @Override
    public int getBufferEnd() {
        return endOffset;
    }
}
