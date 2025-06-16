package org.rholang.lang.highlighting;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.rholang.lang.lexer.RholangLexer;
import org.rholang.lang.psi.RholangTypes;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class RholangSyntaxHighlighter extends SyntaxHighlighterBase {
    public static final TextAttributesKey KEYWORD = 
            createTextAttributesKey("RHOLANG_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey COMMENT = 
            createTextAttributesKey("RHOLANG_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey STRING = 
            createTextAttributesKey("RHOLANG_STRING", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey NUMBER = 
            createTextAttributesKey("RHOLANG_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey OPERATOR = 
            createTextAttributesKey("RHOLANG_OPERATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey PARENTHESES = 
            createTextAttributesKey("RHOLANG_PARENTHESES", DefaultLanguageHighlighterColors.PARENTHESES);
    public static final TextAttributesKey BRACES = 
            createTextAttributesKey("RHOLANG_BRACES", DefaultLanguageHighlighterColors.BRACES);
    public static final TextAttributesKey BRACKETS = 
            createTextAttributesKey("RHOLANG_BRACKETS", DefaultLanguageHighlighterColors.BRACKETS);
    public static final TextAttributesKey IDENTIFIER = 
            createTextAttributesKey("RHOLANG_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER);
    public static final TextAttributesKey BAD_CHARACTER = 
            createTextAttributesKey("RHOLANG_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);

    private static final TextAttributesKey[] KEYWORD_KEYS = new TextAttributesKey[]{KEYWORD};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
    private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[]{STRING};
    private static final TextAttributesKey[] NUMBER_KEYS = new TextAttributesKey[]{NUMBER};
    private static final TextAttributesKey[] OPERATOR_KEYS = new TextAttributesKey[]{OPERATOR};
    private static final TextAttributesKey[] PARENTHESES_KEYS = new TextAttributesKey[]{PARENTHESES};
    private static final TextAttributesKey[] BRACES_KEYS = new TextAttributesKey[]{BRACES};
    private static final TextAttributesKey[] BRACKETS_KEYS = new TextAttributesKey[]{BRACKETS};
    private static final TextAttributesKey[] IDENTIFIER_KEYS = new TextAttributesKey[]{IDENTIFIER};
    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new RholangLexer();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(RholangTypes.NEW_KEYWORD) ||
            tokenType.equals(RholangTypes.IN_KEYWORD) ||
            tokenType.equals(RholangTypes.FOR_KEYWORD) ||
            tokenType.equals(RholangTypes.MATCH_KEYWORD) ||
            tokenType.equals(RholangTypes.SELECT_KEYWORD) ||
            tokenType.equals(RholangTypes.CONTRACT_KEYWORD) ||
            tokenType.equals(RholangTypes.IF_KEYWORD) ||
            tokenType.equals(RholangTypes.ELSE_KEYWORD) ||
            tokenType.equals(RholangTypes.LET_KEYWORD) ||
            tokenType.equals(RholangTypes.TRUE_KEYWORD) ||
            tokenType.equals(RholangTypes.FALSE_KEYWORD) ||
            tokenType.equals(RholangTypes.NIL_KEYWORD)) {
            return KEYWORD_KEYS;
        } else if (tokenType.equals(RholangTypes.LINE_COMMENT) ||
                   tokenType.equals(RholangTypes.BLOCK_COMMENT)) {
            return COMMENT_KEYS;
        } else if (tokenType.equals(RholangTypes.STRING_LITERAL) ||
                   tokenType.equals(RholangTypes.URI_LITERAL)) {
            return STRING_KEYS;
        } else if (tokenType.equals(RholangTypes.LONG_LITERAL)) {
            return NUMBER_KEYS;
        } else if (tokenType.equals(RholangTypes.PLUS) ||
                   tokenType.equals(RholangTypes.MINUS) ||
                   tokenType.equals(RholangTypes.MULTIPLY) ||
                   tokenType.equals(RholangTypes.DIVIDE) ||
                   tokenType.equals(RholangTypes.MODULO) ||
                   tokenType.equals(RholangTypes.EQUALS) ||
                   tokenType.equals(RholangTypes.NOT_EQUALS) ||
                   tokenType.equals(RholangTypes.LESS_THAN) ||
                   tokenType.equals(RholangTypes.LESS_THAN_OR_EQUAL) ||
                   tokenType.equals(RholangTypes.GREATER_THAN) ||
                   tokenType.equals(RholangTypes.GREATER_THAN_OR_EQUAL) ||
                   tokenType.equals(RholangTypes.AND) ||
                   tokenType.equals(RholangTypes.OR) ||
                   tokenType.equals(RholangTypes.NOT) ||
                   tokenType.equals(RholangTypes.MATCHES)) {
            return OPERATOR_KEYS;
        } else if (tokenType.equals(RholangTypes.LPAREN) ||
                   tokenType.equals(RholangTypes.RPAREN)) {
            return PARENTHESES_KEYS;
        } else if (tokenType.equals(RholangTypes.LBRACE) ||
                   tokenType.equals(RholangTypes.RBRACE)) {
            return BRACES_KEYS;
        } else if (tokenType.equals(RholangTypes.LBRACKET) ||
                   tokenType.equals(RholangTypes.RBRACKET)) {
            return BRACKETS_KEYS;
        } else if (tokenType.equals(RholangTypes.IDENTIFIER)) {
            return IDENTIFIER_KEYS;
        } else if (tokenType.equals(TokenType.BAD_CHARACTER)) {
            return BAD_CHAR_KEYS;
        } else {
            return EMPTY_KEYS;
        }
    }
}