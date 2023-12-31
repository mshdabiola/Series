package com.himamis.retex.renderer.share.commands;

import com.himamis.retex.renderer.share.Atom;
import com.himamis.retex.renderer.share.RomanAtom;
import com.himamis.retex.renderer.share.StyleAtom;
import com.himamis.retex.renderer.share.TeXConstants;
import com.himamis.retex.renderer.share.TeXParser;
import com.himamis.retex.renderer.share.TextStyle;
import com.himamis.retex.renderer.share.TextStyleAtom;
import com.himamis.retex.renderer.share.exception.ParseException;

public class CommandInterText extends Command {

    boolean mode;

    @Override
    public boolean init(TeXParser tp) {
        if (!tp.isArrayMode()) {
            throw new ParseException(tp,
                    "The macro \\intertext is only available in array mode !");
        }
        mode = tp.setTextMode();
        return true;
    }

    @Override
    public void add(TeXParser tp, Atom a) {
        tp.setMathMode(mode);
        a = new TextStyleAtom(a, TextStyle.MATHNORMAL);
        a = new StyleAtom(TeXConstants.STYLE_TEXT, new RomanAtom(a));
        tp.closeConsumer(a.changeType(TeXConstants.TYPE_INTERTEXT));
    }

}
