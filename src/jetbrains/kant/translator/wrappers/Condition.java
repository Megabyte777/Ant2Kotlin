package jetbrains.kant.translator.wrappers;

import org.xml.sax.SAXException;

public class Condition extends Wrapper {
    public Condition(Wrapper wrapper) {
        super(wrapper);
    }

    @Override
    public Wrapper addChild(Wrapper child) throws SAXException {
        child.setParent(this);
        Condition childCondition = new Condition(child);
        children.add(childCondition);
        return childCondition;
    }

    @Override
    public String getDSLClassName() {
        if (parent != null && (name.equals("not") || name.equals("and") || name.equals("or") || name.equals("xor"))) {
            return parent.getDSLClassName();
        } else {
            return super.getDSLClassName();
        }
    }

    @Override
    public String toString() {
        String result;
        switch (name) {
            case "not":
                return "!" + children.get(0).toString();
            case "and":
                result = children.get(0).toString() + " && " + children.get(1).toString();
                if (parent != null && "not".equals(parent.name)) {
                    result = "(" + result + ")";
                }
                return result;
            case "or":
                result = children.get(0).toString() + " || " + children.get(1).toString();
                if (parent != null && ("not".equals(parent.name) || "and".equals(parent.name))) {
                    result = "(" + result + ")";
                }
                return result;
            case "xor":
                result = children.get(0).toString() + " ^ " + children.get(1).toString();
                if (parent != null && ("not".equals(parent.name) || "and".equals(parent.name))) {
                    result = "(" + result + ")";
                }
                return result;
            case "istrue":
                return attributes.get(0).getDefaultValue(context);
            case "isfalse":
                return "!" + attributes.get(0).getDefaultValue(context);
            default:
                return super.toString();
        }
    }
}