package io.github.wetneb.refinejs;

import java.util.Properties;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;

import com.google.refine.expr.Evaluable;
import com.google.refine.expr.HasFields;

public class RhinoEvaluable implements Evaluable {
    
    protected final ContextFactory contextFactory;
    protected final Function function;
    
    public RhinoEvaluable(ContextFactory contextFactory, Function function) {
        this.contextFactory = contextFactory;
        this.function = function;
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
                        new HasFieldsWrapper((HasFields)bindings.get("cell"), bindings),
                        // cells
                        new HasFieldsWrapper((HasFields)bindings.get("cells"), bindings),
                        // row
                        new HasFieldsWrapper((HasFields)bindings.get("row"), bindings),
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

}
