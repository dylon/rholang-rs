package org.rholang.lang.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import org.rholang.lang.RholangLanguage;

public interface RholangTypes {
    // Comments
    IElementType LINE_COMMENT = new RholangElementType("LINE_COMMENT");
    IElementType BLOCK_COMMENT = new RholangElementType("BLOCK_COMMENT");

    // Whitespace
    IElementType WHITESPACE = new RholangElementType("WHITESPACE");

    // Bad character
    IElementType BAD_CHARACTER = new RholangElementType("BAD_CHARACTER");

    // Literals
    IElementType STRING_LITERAL = new RholangElementType("STRING_LITERAL");
    IElementType URI_LITERAL = new RholangElementType("URI_LITERAL");
    IElementType LONG_LITERAL = new RholangElementType("LONG_LITERAL");
    IElementType BOOL_LITERAL = new RholangElementType("BOOL_LITERAL");

    // Keywords
    IElementType NEW_KEYWORD = new RholangElementType("NEW_KEYWORD");
    IElementType IN_KEYWORD = new RholangElementType("IN_KEYWORD");
    IElementType FOR_KEYWORD = new RholangElementType("FOR_KEYWORD");
    IElementType MATCH_KEYWORD = new RholangElementType("MATCH_KEYWORD");
    IElementType SELECT_KEYWORD = new RholangElementType("SELECT_KEYWORD");
    IElementType CONTRACT_KEYWORD = new RholangElementType("CONTRACT_KEYWORD");
    IElementType IF_KEYWORD = new RholangElementType("IF_KEYWORD");
    IElementType ELSE_KEYWORD = new RholangElementType("ELSE_KEYWORD");
    IElementType LET_KEYWORD = new RholangElementType("LET_KEYWORD");
    IElementType TRUE_KEYWORD = new RholangElementType("TRUE_KEYWORD");
    IElementType FALSE_KEYWORD = new RholangElementType("FALSE_KEYWORD");
    IElementType NIL_KEYWORD = new RholangElementType("NIL_KEYWORD");

    // Operators
    IElementType PLUS = new RholangElementType("PLUS");
    IElementType MINUS = new RholangElementType("MINUS");
    IElementType MULTIPLY = new RholangElementType("MULTIPLY");
    IElementType DIVIDE = new RholangElementType("DIVIDE");
    IElementType MODULO = new RholangElementType("MODULO");
    IElementType EQUALS = new RholangElementType("EQUALS");
    IElementType NOT_EQUALS = new RholangElementType("NOT_EQUALS");
    IElementType LESS_THAN = new RholangElementType("LESS_THAN");
    IElementType LESS_THAN_OR_EQUAL = new RholangElementType("LESS_THAN_OR_EQUAL");
    IElementType GREATER_THAN = new RholangElementType("GREATER_THAN");
    IElementType GREATER_THAN_OR_EQUAL = new RholangElementType("GREATER_THAN_OR_EQUAL");
    IElementType AND = new RholangElementType("AND");
    IElementType OR = new RholangElementType("OR");
    IElementType NOT = new RholangElementType("NOT");
    IElementType MATCHES = new RholangElementType("MATCHES");

    // Punctuation
    IElementType LPAREN = new RholangElementType("LPAREN");
    IElementType RPAREN = new RholangElementType("RPAREN");
    IElementType LBRACE = new RholangElementType("LBRACE");
    IElementType RBRACE = new RholangElementType("RBRACE");
    IElementType LBRACKET = new RholangElementType("LBRACKET");
    IElementType RBRACKET = new RholangElementType("RBRACKET");
    IElementType COMMA = new RholangElementType("COMMA");
    IElementType SEMICOLON = new RholangElementType("SEMICOLON");
    IElementType COLON = new RholangElementType("COLON");
    IElementType DOT = new RholangElementType("DOT");
    IElementType ARROW = new RholangElementType("ARROW");
    IElementType PIPE = new RholangElementType("PIPE");
    IElementType AT = new RholangElementType("AT");
    IElementType ASTERISK = new RholangElementType("ASTERISK");

    // Identifiers
    IElementType IDENTIFIER = new RholangElementType("IDENTIFIER");

    // Factory for creating PSI elements
    class Factory {
        public static PsiElement createElement(ASTNode node) {
            IElementType type = node.getElementType();
            // This is a simplified version, in a real implementation you would create specific PSI elements
            return new RholangPsiElement(node);
        }
    }
}
