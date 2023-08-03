package com.himamis.retex.renderer.android.parser

import com.himamis.retex.renderer.share.platform.parser.Parser
import com.himamis.retex.renderer.share.platform.parser.ParserFactory

class ParserFactoryAndroid : ParserFactory() {
    override fun createParser(): Parser {
        return ParserA()
    }
}
