package org.rholang.lang.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public class RholangParser implements PsiParser {
    private static final Logger LOG = Logger.getInstance(RholangParser.class);

    @NotNull
    @Override
    public ASTNode parse(@NotNull IElementType root, @NotNull PsiBuilder builder) {
        PsiBuilder.Marker rootMarker = builder.mark();

        // Get the full text from the builder
        String text = builder.getOriginalText().toString();

        // Check if the code is valid using the Rust parser
        boolean isValid = RholangParserCli.isValid(text);
        LOG.debug("Rholang code is valid: " + isValid);

        // For now, just consume all tokens
        // In a more complete implementation, we would parse the tree returned by the Rust parser
        // and build a corresponding PSI tree
        while (!builder.eof()) {
            builder.advanceLexer();
        }

        rootMarker.done(root);
        return builder.getTreeBuilt();
    }
}
