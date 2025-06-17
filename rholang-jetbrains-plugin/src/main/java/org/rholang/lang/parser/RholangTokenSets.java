package org.rholang.lang.parser;

import com.intellij.psi.tree.TokenSet;
import org.rholang.lang.psi.RholangTypes;

public interface RholangTokenSets {
    TokenSet COMMENTS = TokenSet.create(
        RholangTypes.LINE_COMMENT,
        RholangTypes.BLOCK_COMMENT
    );

    TokenSet STRINGS = TokenSet.create(
        RholangTypes.STRING_LITERAL,
        RholangTypes.URI_LITERAL
    );
}