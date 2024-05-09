package com.mshdabiola.ui

// import androidx.compose.foundation.onClick
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import com.mshdabiola.retex.Latex

@OptIn(
    ExperimentalLayoutApi::class,
)
@Composable
fun TemplateUi() {
    var state by remember {
        mutableStateOf(0)
    }

    val listOfMap = listOf(
        mapOf(
            "Overhead" to listOf(
                "\\overline{abc}",
                "\\underline{abc}",
                "\\widehat{abc}",
                "\\widetilde{abc}",
                "\\overrightarrow{abc}",
                "\\overleftarrow{abc}",
                "\\overbrace{abc}",
                "\\underbrace{abc}",
            ),
            "Equation" to listOf(
                "\\frac{0}{0}",
                "^0/_0",
                "0_0",
                "0^0",
                "\\sqrt{6}",
                "\\sqrt[2]{0}",
                "\\sum_{k=1}^n k^2",
                "\\prod_{x=4}^x",
                "\\int_a^b f(x)\\,dx.",
                "\\left.\\frac{dx}{dy}\\right|_x",
                "\\frac{\\partial u}{\\partial t}",
            ),
            "Functions" to listOf(
                "\\arccos(x)",
                "\\cos(x)",
                "\\csc(x)",
                "\\exp(x)",
                "\\ker(x)",
                "\\limsup(x)",
                "\\min(x)",
                "\\sinh(x)",
                "\\arcsin(x)",
                "\\cosh(x)",
                "\\deg(x)",
                "\\gcd(x)",
                "\\lg(x)",
                "\\in(x)",
                "\\Pr(x)",
                "\\arctan(x)",
                "\\cot(x)",
                "\\det(x)",
                "\\hom(x)",
                "\\lim_{x \\to \\infty}(x)",
                "\\log(x)",
                "\\sec(x)",
                "\\tan(x)",
                "\\arg(x)",
                "\\coth(x)",
                "\\dim(x)",
                "\\inf(x)",
                "\\liminf(x)",
                "\\max(x)",
                "\\sin(x)",
                "\\tanh(x)",
            ),
            "Delimiter" to listOf(

                "|",
                "\\vert",
                "\\|",
                "\\Vert",
                "\\{",
                "\\}",
                "\\langle",
                "\\rangle",
                "\\lfloor",
                "\\rfloor",
                "\\lceil",
                "\\rceil",
                "/",
                "\\backslash",
                "[",
                "]",
                "\\Uparrow",
                "\\uparrow",
                "\\Downarrow",
                "\\downarrow",
                "\\llcorner",
                "\\lrcorner",
                "\\ulcorner",
                "\\urcorner",
            ),
            "array" to listOf(
                "\\mbox{~and~} ",
                "\\left[ \\begin{array}{cc|r} 3 & 4 & 5 \\\\ 1 & 3 & 729 \\end{array} \\right]",
                "\\begin{eqnarray}\n" +
                        "\\cos 2\\theta & = & \\cos^2 \\theta - \\sin^2 \\theta \\\\\n" +
                        "& = & 2 \\cos^2 \\theta - 1.\n" +
                        "\\end{eqnarray}",
                "\\begin{tabular}{|l|l|l|}\\hline\n" +
                        "Chicago&U.S.A.&1893\\\\\n" +
                        "Z\\\"{u}rich&Switzerland&1897\\\\\n" +
                        "Paris&France&1900\\\\\n" +
                        "Heidelberg&Germany&1904\\\\\n" +
                        "Rome&Italy&1908\\hline\\end{tabular}",
            ),
        ), // equation
        mapOf(
            "Acute" to listOf(
                "\\acute{a}",
                "\\breve{a}",
                "\\ddot{a}",
                "\\grave{a}",
                "\\tilde{a}",
                "\\bar{a}",
                "\\check{a}",
                "\\dot{a}",
                "\\hat{a}",
                "\\vec{a}",

                ),
            "Font" to listOf(
                " \\mathcal{A}",
                " \\mathbb{A}",
                " \\mathfrak{A}",
                " \\mathsf{A}",
                " \\mathbf{A}",
                "\\text{ a text }",
            ),
            "Greek" to listOf(
                "\\alpha", "\\beta", "\\gamma", "\\delta", "\\epsilon",
                "\\zeta", "\\eta", "\\theta", "\\iota", "\\kappa", "\\lambda",
                "\\mu", "\\nu", "\\xi", "\\psi", "\\pi", "\\rho", "\\sigma", "\\tau",
                "\\upsilon", "\\phi", "\\omega",
                "\\varepsilon", "\\vartheta", "\\varpi", "\\varrho", "\\varsigma", "\\varphi",
                "\\Gamma", "\\Delta", "\\Epsilon",
                "\\Theta", "\\Lambda", "\\Xi", "\\Psi", "\\Pi", "\\Sigma",
                "\\Upsilon", "\\Phi", "\\Omega",
            ),
        ), // letter
        mapOf(
            "Binary" to listOf(
                "\\ast",
                "\\star",
                "\\cdot",
                "\\circ",
                "\\bullet",
                "\\bigcirc",
                "\\diamond",
                "\\times",
                "\\div",
                "\\centerdot",
                "\\circledast",
                "\\circledcirc",
                "\\circleddash",
                "\\dotplus",
                "\\divideontimes",
                "\\pm",
                "\\mp",
                "\\amalg",
                "\\odot",
                "\\ominus",
                "\\oplus",
                "\\oslash",
                "\\otimes",
                "\\wr",
                "\\Box",
                "\\boxplus",
                "\\boxminus",
                "\\boxtimes",
                "\\boxdot",
                "\\square",
                "\\cap",
                "\\cup",
                "\\uplus",
                "\\sqcap",
                "\\sqcup",
                "\\wedge",
                "\\vee",
                "\\dagger",
                "\\ddagger",
                "\\barwedge",
                "\\curlywedge",
                "\\Cap",
                "\\bot",
                "\\intercal",
                "\\doublebarwedge",
                "\\lhd",
                "\\rhd",
                "\\triangleleft",
                "\\triangleright",
                "\\unlhd",
                "\\unrhd",
                "\\bigtriangledown",
                "\\bigtriangleup",
                "\\setminus",
                "\\veebar",
                "\\curlyvee",
                "\\Cup",
                "\\top",
                "\\rightthreetimes",
                "\\leftthreetimes",
            ),
            "Variable Symbol" to listOf(
                "\\sum_0",
                "\\bigcap_0",
                "\\bigodot_0",
                "\\prod_0",
                "\\bigcup_0",
                "\\bigotimes_0",
                "\\coprod_0",
                "\\bigsqcup_0",
                "\\bigoplus_0",
                "\\int_0",
                "\\bigvee_0",
                "\\biguplus_0",
                "\\oint_0",
                "\\oint\\limits_0^y",
                "\\bigwedge_0",
                "\\leqslant_0",
                "\\geqslant_0",
            ),
            "Relation 1" to listOf(
                // eqution
                "\\equiv",
                "\\cong",
                "\\neq",
                "\\sim",
                "\\simeq",
                "\\approx",
                "\\asymp",
                "\\doteq",
                "\\propto",
                "\\models",
                "\\leq",
                "\\prec",
                "\\preceq",
                "\\ll",
                "\\subset",
                "\\subseteq",
                "\\sqsubset",
                "\\sqsubseteq",
                "\\dashv",
                "\\in",
                "\\geq",
                "\\succ",
                "\\succeq",
                "\\gg",
                "\\supset",
                "\\supseteq",
                "\\sqsupset",
                "\\sqsupseteq",
                "\\vdash",
                "\\ni",
                "\\perp",
                "\\mid",
                "\\parallel",
                "\\bowtie",
                "\\Join",
                "\\ltimes",
                "\\rtimes",
                "\\smile",
                "\\frown",
                "\\notin",
            ),
            "Relation 2" to listOf(
                // equal
                "\\approxeq",
                "\\thicksim",
                "\\backsim",
                "\\backsimeq",
                "\\triangleq",
                "\\circeq",
                "\\bumpeq",
                "\\Bumpeq",
                "\\doteqdot",
                "\\thickapprox",
                "\\fallingdotseq",
                "\\risingdotseq",
                "\\varpropto",
                "\\therefore",
                "\\because",
                "\\eqcirc",
                "\\neq",
                "\\leqq",
                "\\leqslant",
                "\\lessapprox",
                "\\lll",
                "\\lessdot",
                "\\lesssim",
                "\\eqslantless",
                "\\precsim",
                "\\precapprox",
                "\\Subset",
                "\\subseteqq",
                "\\sqsubset",
                "\\preccurlyeq",
                "\\curlyeqprec",
                "\\blacktriangleleft",
                "\\trianglelefteq",
                "\\vartriangleleft",
                "\\geqq",
                "\\geqslant",
                "\\gtrapprox",
                "\\ggg",
                "\\gtrdot",
                "\\gtrsim",
                "\\eqslantgtr",
                "\\succsim",
                "\\succapprox",
                "\\Supset",
                "\\supseteqq",
                "\\sqsupset",
                "\\succcurlyeq",
                "\\curlyeqsucc",
                "\\blacktriangleright",
                "\\trianglerighteq",
                "\\vartriangleright",
                "\\lessgtr",
                "\\lesseqgtr",
                "\\lesseqqgtr",
                "\\gtreqqless",
                "\\gtreqless",
                "\\gtrless",
                "\\backepsilon",
                "\\between",
                "\\pitchfork",
                "\\shortmid",
                "\\smallfrown",
                "\\smallsmile",
                "\\Vdash",
                "\\vDash",
                "\\Vvdash",
                "\\shortparallel",
                "\\nshortparallel",
            ),
            "Relation 3" to listOf(

                "\\ncong",
                "\\nmid",
                "\\nparallel",
                "\\nshortmid",
                "\\nshortparallel",
                "\\nsim",
                "\\nVDash",
                "\\nvDash",
                "\\nvdash",
                "\\ntriangleleft",
                "\\ntrianglelefteq",
                "\\ntriangleright",
                "\\ntrianglerighteq",
                "\\nleq",
                "\\nleqq",
                "\\nleqslant",
                "\\nless",
                "\\nprec",
                "\\npreceq",
                "\\precnapprox",
                "\\precnsim",
                "\\lnapprox",
                "\\lneq",
                "\\lneqq",
                "\\lnsim",
                "\\lvertneqq",
                "\\ngeq",
                "\\ngeqq",
                "\\ngeqslant",
                "\\ngtr",
                "\\nsucc",
                "\\nsucceq",
                "\\succnapprox",
                "\\succnsim",
                "\\gnapprox",
                "\\gneq",
                "\\gneqq",
                "\\gnsim",
                "\\gvertneqq",
                "\\nsubseteq",
                "\\nsupseteq",
                "\\nsubseteqq",
                "\\nsupseteqq",
                "\\subsetneq",
                "\\supsetneq",
                "\\subsetneqq",
                "\\supsetneqq",
                "\\varsubsetneq",
                "\\varsupsetneq",
                "\\varsubsetneqq",
                "\\varsupsetneqq",
            ),
            "Arrow 1" to listOf(
                "\\leftarrow", "\\longleftarrow", "\\uparrow",
                "\\Leftarrow", "\\Longleftarrow", "\\Uparrow",
                "\\rightarrow", "\\longrightarrow", "\\downarrow",
                "\\Rightarrow", "\\Longrightarrow", "\\Downarrow",
                "\\leftrightarrow", "\\longleftrightarrow", "\\updownarrow",
                "\\Leftrightarrow", "\\Longleftrightarrow", "\\Updownarrow",
            ),
            "Arrow 2" to listOf(
                "\\mapsto", "\\longmapsto", "\\nearrow",
                "\\hookleftarrow", "\\hookrightarrow", "\\searrow",
                "\\leftharpoonup", "\\rightharpoonup", "\\swarrow",
                "\\leftharpoondown", "\\rightharpoondown", "\\nwarrow",
                "\\rightleftharpoons", "\\leadsto",
            ),
            "Arrow 3" to listOf(
                "\\leftleftarrows",
                "\\leftrightarrows", "\\Lleftarrow", "\\twoheadleftarrow",
                "\\leftarrowtail", "\\looparrowleft", "\\leftrightharpoons",
                "\\curvearrowleft", "\\circlearrowleft", "\\Lsh",
                "\\upuparrows", "\\upharpoonleft", "\\downharpoonleft",
                "\\multimap", "\\leftrightsquigarrow", "\\rightrightarrows",
                "\\rightleftarrows", "\\rightrightarrows", "\\rightleftarrows",
                "\\twoheadrightarrow", "\\rightarrowtail", "\\looparrowright",
                "\\rightleftharpoons", "\\curvearrowright", "\\circlearrowright",
                "\\Rsh", "\\downdownarrows", "\\upharpoonright",
            ),
            "Arrow 4" to listOf(
                "\\nleftarrow",
                "\\nrightarrow",
                "\\nLeftarrow",
                "\\nRightarrow",
                "\\nleftrightarrow",
                "\\nLeftrightarrow",
            ),
            "Miscellaneous" to listOf(
                "\\aleph",
                "\\angle",
                "\\backprime",
                "\\backslash",
                "\\Bbbk",
                "\\bigstar",
                "\\blacklozenge",
                "\\blacksquare",
                "\\blacktriangle",
                "\\blacktriangledown",
                "\\bot",
                "\\cdots",
                "\\circledS",
                "\\clubsuit",
                "\\complement",
                "\\ddots",
                "\\diagdown",
                "\\diagup",
                "\\diamondsuit",
                "\\ell",
                "\\emptyset",
                "\\eth",
                "\\exists",
                "\\Finv",
                "\\flat",
                "\\forall",
                "\\Game",
                "\\hbar",
                "\\heartsuit",
                "\\hslash",
                "\\Im",
                "\\imath",
                "\\infty",
                "\\jmath",
                "\\ldots",
                "\\lozenge",
                "\\measuredangle",
                "\\mho",
                "\\nabla",
                "\\natural",
                "\\neg",
                "\\nexists",
                "\\partial",
                "\\prime",
                "\\Re",
                "\\sharp",
                "\\spadesuit",
                "\\sphericalangle",
                "\\square",
                "\\top",
                "\\triangle",
                "\\triangledown",
                "\\varnothing",
                "\\vartriangle",
                "\\vdots",
                "\\wp",
                "\\|",

                ),
        ), // symbol

    )

//    "surd"
    val clipboard = LocalClipboardManager.current
    ScrollableTabRow(selectedTabIndex = state) {
        Tab(selected = false, onClick = {
            state = 0
        }, text = { Text("Mark up") })
        Tab(selected = false, onClick = {
            state = 1
        }, text = { Text("Equation") })
        Tab(selected = false, onClick = {
            state = 2
        }, text = { Text("Letters") })
        Tab(selected = false, onClick = {
            state = 3
        }, text = { Text("Symbols") })
    }
    // HorizontalPager(state = state, userScrollEnabled = false) {

    if (state == 0) {
        FlowRow(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            MarkUpEngine.list.keys.toList().forEach {
                SuggestionChip(onClick = {
                    clipboard.setText(
                        buildAnnotatedString {
                            append("*")
                            append(it)
                            append("*content*e*")
                        },
                    )
                }, label = { MarkUpText("*$it*key-$it*e*") })
            }
        }
    } else {
        TemptRow(listOfMap[state - 1])
    }

    // }
}

@Composable
fun TemptRow(
    listMap: Map<String, List<String>>,
) {
    Column {
        listMap.forEach { (t, u) ->

            TemplateContent(t, u)
        }
    }
}

@OptIn(
    ExperimentalLayoutApi::class,
    ExperimentalFoundationApi::class,
)
@Composable
internal fun TemplateContent(name: String, list: List<String>) {
    val clipboard = LocalClipboardManager.current
    var showAll by remember {
        mutableStateOf(false)
    }

    Column(
        Modifier
            .fillMaxWidth()
            .animateContentSize { _, _ -> },
    ) {
        Row(
            Modifier
                .clickable(onClick = { showAll = !showAll })
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(name)
            IconButton(modifier = Modifier, onClick = { showAll = !showAll }) {
                Icon(
                    if (!showAll) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    "down",
                )
            }
        }
        if (showAll) {
            FlowRow(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                list.forEach {
                    SuggestionChip(onClick = {
                        clipboard.setText(
                            buildAnnotatedString {
                                append(it)
                            },
                        )
                    }, label = { Latex(text = it) })
                }
            }
        }
    }
}

// @Preview
// @Composable
// fun TemplatePreview() {
//    //TemplateUi()
//    // TemplateContent("Arrow", listOf("abioa", "avvii"))
//    TemptRow(
//        mapOf(
//            "Text" to listOf("aviol", "aksl"),
//            "Text2" to listOf("aviol", "aksl"),
//        )
//    )
// }
