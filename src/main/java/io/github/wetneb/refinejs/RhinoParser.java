package io.github.wetneb.refinejs;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import org.openrefine.expr.LanguageSpecificParser;
import org.openrefine.expr.ParsingException;


public class RhinoParser implements LanguageSpecificParser {
    
    ContextFactory contextFactory = ContextFactory.getGlobal();

    @Override
    public RhinoEvaluable parse(String s, String languagePrefix) throws ParsingException {
        String source = "function(value, cell, cells, row, rowIndex) {\n" + s + "}";
        ContextAction<Function> contextAction = new ContextAction<Function>() {

            @Override
            public Function run(Context cx) {
                
                Scriptable scope = cx.initSafeStandardObjects(null);
                
                return cx.compileFunction(scope, source, "source", 0, null);

            }
        };
        
        try {
            Function function = contextFactory.call(contextAction);
            return new RhinoEvaluable(contextFactory, function, s, languagePrefix);
        } catch(EvaluatorException e) {
            throw new ParsingException(e.getLocalizedMessage());
        }
    }

}
