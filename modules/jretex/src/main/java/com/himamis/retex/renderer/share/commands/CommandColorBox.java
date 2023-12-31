package com.himamis.retex.renderer.share.commands;

import com.himamis.retex.renderer.share.Atom;
import com.himamis.retex.renderer.share.FBoxAtom;
import com.himamis.retex.renderer.share.TeXParser;
import com.himamis.retex.renderer.share.platform.graphics.Color;

public class CommandColorBox extends Command1A {

    Color bg;

    public CommandColorBox() {
        //
    }

    public CommandColorBox(Color bg2) {
        this.bg = bg2;
    }

    @Override
    public boolean init(TeXParser tp) {
        bg = CommandDefinecolor.getColor(tp);
        return true;
    }

    @Override
    public Atom newI(TeXParser tp, Atom a) {
        return new FBoxAtom(a, bg, bg);
    }

}
