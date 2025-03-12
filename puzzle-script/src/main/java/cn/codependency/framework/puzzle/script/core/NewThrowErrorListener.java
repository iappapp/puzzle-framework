package cn.codependency.framework.puzzle.script.core;

import cn.codependency.framework.puzzle.script.core.ast.PuzzleScriptParserException;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class NewThrowErrorListener extends BaseErrorListener {
    public static final ANTLRErrorListener INSTANCE = new NewThrowErrorListener();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        throw new PuzzleScriptParserException("Parse script error: line " + line + ":" + charPositionInLine + " " + msg);
    }
}
