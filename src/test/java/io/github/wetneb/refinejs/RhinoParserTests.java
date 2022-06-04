package io.github.wetneb.refinejs;

import org.testng.annotations.Test;

import com.google.refine.expr.ParsingException;

public class RhinoParserTests {
    
    RhinoParser SUT = new RhinoParser(); 

    @Test(expectedExceptions = ParsingException.class)
    public void testInvalidSource() throws ParsingException {
        SUT.parse("this is an invalid expression");
    }
    
    @Test
    public void testValidSource() throws ParsingException {
        SUT.parse("return value");
    }
}
