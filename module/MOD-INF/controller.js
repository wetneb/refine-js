
/*
 * Function invoked to initialize the extension.
 */
function init() {
   
  Packages.org.openrefine.expr.MetaParser.registerLanguageParser(
    "js_rhino",
    "Javascript",
    Packages.io.github.wetneb.refinejs.RhinoEvaluable.createParser(),
    "return value"
  );
}

