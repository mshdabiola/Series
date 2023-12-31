package com.himamis.retex.renderer.share.commands;

import com.himamis.retex.renderer.share.Atom;
import com.himamis.retex.renderer.share.RotateAtom;
import com.himamis.retex.renderer.share.TeXParser;

import java.util.HashMap;

public class CommandT extends Command1A {

    @Override
    public Atom newI(TeXParser tp, Atom a) {
        return new RotateAtom(a, 180., new HashMap<String, String>() {
            {
                put("origin", "cc");
            }
        });
    }

}
