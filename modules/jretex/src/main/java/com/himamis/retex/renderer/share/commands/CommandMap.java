package com.himamis.retex.renderer.share.commands;

import com.himamis.retex.renderer.share.AccentedAtom;
import com.himamis.retex.renderer.share.Atom;
import com.himamis.retex.renderer.share.Symbols;
import com.himamis.retex.renderer.share.TeXParser;

public class CommandMap extends Command1A {

    @Override
    public Atom newI(TeXParser tp, Atom a) {
        return new AccentedAtom(a, Symbols.CHECK);
    }

    @Override
    public boolean close(TeXParser tp) {
        tp.closeConsumer(Symbols.CHECK);
        return true;
    }

    @Override
    public boolean isClosable() {
        return true;
    }

}
