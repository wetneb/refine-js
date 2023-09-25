package io.github.wetneb.refinejs;

import java.util.Properties;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;

import org.openrefine.expr.Evaluable;
import org.openrefine.expr.HasFields;

public class RhinoEvaluable implements Evaluable {
    
    protected final ContextFactory contextFactory;
    protected final Function function;
    protected final String source;
    protected final String languagePrefix;
    
    public RhinoEvaluable(ContextFactory contextFactory, Function function, String source, String languagePrefix) {
        this.contextFactory = contextFactory;
        this.function = function;
        this.source = source;
        this.languagePrefix = languagePrefix;
    }

    public static RhinoParser createParser() {
        return new RhinoParser();
    }

    @Override
    public Object evaluate(Properties bindings) {
        Object returnValue = contextFactory.call(new ContextAction<Object>() {

            @Override
            public Object run(Context cx) {
                Scriptable scope = cx.initSafeStandardObjects(null);
                Object[] arguments = new Object[] {
                        // value
                        bindings.get("value"),
                        // cell
                        new HasFieldsWrapper((HasFields)bindings.get("cell")),
                        // cells
                        new HasFieldsWrapper((HasFields)bindings.get("cells")),
                        // row
                        new HasFieldsWrapper((HasFields)bindings.get("row")),
                        // rowIndex
                        bindings.get("rowIndex")
                };
                return function.call(cx, scope, null, arguments);
            }
            
        });
        if (returnValue instanceof Undefined) {
            return null;
        }
        return returnValue;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public String getLanguagePrefix() {
        return languagePrefix;
    }

}
