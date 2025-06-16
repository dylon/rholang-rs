package org.rholang.lang.psi;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.rholang.lang.RholangLanguage;

public class RholangElementType extends IElementType {
    public RholangElementType(@NotNull @NonNls String debugName) {
        super(debugName, RholangLanguage.INSTANCE);
    }
}