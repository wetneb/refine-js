package io.github.wetneb.refinejs;

import java.util.Properties;

import org.mozilla.javascript.Scriptable;

import org.openrefine.expr.HasFields;

/**
 * Wraps a Java object meant to be interacted with via GREL
 * to a Javascript value.
 * 
 * @author Antonin Delpeuch
 *
 */
public class HasFieldsWrapper implements Scriptable {
    
    protected final HasFields obj;
    
    public HasFieldsWrapper(HasFields obj) {
        this.obj = obj;
    }

    @Override
    public String getClassName() {
        return obj.getClass().getName();
    }

    @Override
    public Object get(String name, Scriptable start) {
        Object returnValue = obj.getField(name);
        if (returnValue instanceof HasFields) {
            return new HasFieldsWrapper((HasFields) returnValue);
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
        return obj.getField(name) != null;
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
