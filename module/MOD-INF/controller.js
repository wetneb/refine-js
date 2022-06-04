
/*
 * Function invoked to initialize the extension.
 */
function init() {
   
  Packages.com.google.refine.expr.MetaParser.registerLanguageParser(
    "js_rhino",
    "Javascript",
    Packages.io.github.wetneb.refinejs.RhinoEvaluable.createParser(),
    "return value"
  );
}

