package io.github.wetneb.refinejs;

import java.util.Arrays;
import java.util.Properties;

import org.openrefine.model.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import org.openrefine.expr.CellTuple;
import org.openrefine.expr.ParsingException;
import org.openrefine.model.recon.Recon;
import org.openrefine.model.recon.ReconCandidate;

public class RhinoEvaluableTests {
    
    RhinoParser parser = new RhinoParser();
    
    protected RhinoEvaluable parse(String source) {
        try {
            return parser.parse(source, "js");
        } catch (ParsingException e) {
            Assert.fail("Parsing the expression raised an unexpected syntax error: "+e.getLocalizedMessage());
            return null;
        }
    }
    
    @Test
    public void testReturnConstant() {
        Object result = parse("return 'foo'").evaluate(new Properties());
        Assert.assertEquals(result, "foo");
    }
    
    @Test
    public void testReturnValue() {
        Properties bindings = new Properties();
        bindings.put("value", "bar");
        Object result = parse("return value").evaluate(bindings);
        Assert.assertEquals(result, "bar");
    }
   
    @Test
    public void testFields() throws ModelException {
        Properties bindings = new Properties();
        ColumnModel columnModel = new ColumnModel(Arrays.asList(
                new ColumnMetadata("firstColumn"),
                new ColumnMetadata("secondColumn")
        ));

        Row row = new Row(Arrays.asList(
                new Cell("one", null),
                new Cell("1", null)));

        bindings.put("columnName", "secondColumn");
        bindings.put("rowIndex", "0");
        bindings.put("value", 1);
        bindings.put("cells", new CellTuple(columnModel, row));
        
        Object result = parse("return cells['firstColumn'].value").evaluate(bindings);
        Assert.assertEquals(result, "one");
    }
    
    @Test
    public void testNestedFields() {
        ReconCandidate reconCandidate = new ReconCandidate("Q5", "human", null, 100.0);
        Recon recon = Recon.makeWikidataRecon(1234L)
                .withCandidate(reconCandidate)
                .withMatch(reconCandidate);
        Cell cell = new Cell("some value", recon);
        
        Properties bindings = new Properties();
        bindings.put("cell", cell);
        
        Object result = parse("return cell.recon.match.id").evaluate(bindings);
        Assert.assertEquals(result, "Q5");
    }
    
    @Test
    public void testUndefined() {
        Object result = parse("return undefined").evaluate(new Properties());
        Assert.assertEquals(result, null);
    }

}
