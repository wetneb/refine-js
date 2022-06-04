package io.github.wetneb.refinejs;

import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.refine.expr.CellTuple;
import com.google.refine.expr.ParsingException;
import com.google.refine.model.Cell;
import com.google.refine.model.Column;
import com.google.refine.model.ModelException;
import com.google.refine.model.Project;
import com.google.refine.model.Recon;
import com.google.refine.model.ReconCandidate;
import com.google.refine.model.Row;

public class RhinoEvaluableTests {
    
    RhinoParser parser = new RhinoParser();
    
    protected RhinoEvaluable parse(String source) {
        try {
            return parser.parse(source);
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
        Project project = new Project();
        project.columnModel.addColumn(0, new Column(0, "firstColumn"), true);
        project.columnModel.addColumn(1, new Column(1, "secondColumn"), true);

        Row row = new Row(2);
        row.setCell(0, new Cell("one", null));
        row.setCell(1, new Cell("1", null));

        bindings.put("columnName", "secondColumn");
        bindings.put("rowIndex", "0");
        bindings.put("value", 1);
        bindings.put("cells", new CellTuple(project, row));
        
        Object result = parse("return cells['firstColumn'].value").evaluate(bindings);
        Assert.assertEquals(result, "one");
    }
    
    @Test
    public void testNestedFields() {
        Recon recon = Recon.makeWikidataRecon(1234L);
        ReconCandidate reconCandidate = new ReconCandidate("Q5", "human", null, 100.0);
        recon.addCandidate(reconCandidate);
        recon.match = reconCandidate;
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
