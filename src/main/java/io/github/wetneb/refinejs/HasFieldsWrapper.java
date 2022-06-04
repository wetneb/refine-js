package io.github.wetneb.refinejs;

import java.util.Properties;

import org.mozilla.javascript.Scriptable;

import com.google.refine.expr.HasFields;

/**
 * Wraps a Java object meant to be interacted with via GREL
 * to a Javascript value.
 * 
 * @author Antonin Delpeuch
 *
 */
public class HasFieldsWrapper implements Scriptable {
    
    protected final HasFields obj;
    protected final Properties bindings;
    
    public HasFieldsWrapper(HasFields obj, Properties bindings) {
        this.obj = obj;
        this.bindings = bindings;
    }

    @Override
    public String getClassName() {
        return obj.getClass().getName();
    }

    @Override
    public Object get(String name, Scriptable start) {
        Object returnValue = obj.getField(name, bindings);
        if (returnValue instanceof HasFields) {
            return new HasFieldsWrapper((HasFields) returnValue, bindings);
        } else {
            return returnValue;
        }
    }

    @Override
    public Object get(int index, Scriptable start) {
        return null;
    }

    @Override
    public boolean has(String name, Scriptable start) {
        return obj.getField(name, bindings) != null;
    }

    @Override
    public boolean has(int index, Scriptable start) {
        return false;
    }

    @Override
    public void put(String name, Scriptable start, Object value) {
        // no-op
    }

    @Override
    public void put(int index, Scriptable start, Object value) {
        // no-op
    }

    @Override
    public void delete(String name) {
        // no-op
    }

    @Override
    public void delete(int index) {
        // no-op
    }

    @Override
    public Scriptable getPrototype() {
        return null;
    }

    @Override
    public void setPrototype(Scriptable prototype) {
        // no-op
    }

    @Override
    public Scriptable getParentScope() {
        return null;
    }

    @Override
    public void setParentScope(Scriptable parent) {
        // no-op
    }

    @Override
    public Object[] getIds() {
        return null;
    }

    @Override
    public Object getDefaultValue(Class<?> hint) {
        return null;
    }

    @Override
    public boolean hasInstance(Scriptable instance) {
        return false;
    }

}
