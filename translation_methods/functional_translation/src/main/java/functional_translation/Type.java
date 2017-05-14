package functional_translation;

public enum Type {
    INT("Int"),
    BOOL("Bool");

    private final String typeName;

    Type(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    static final Type fromString(String value) {
        switch (value) {
            case "Int":
                return Type.INT;
            case "Bool":
                return Type.BOOL;
            default:
                throw new IllegalArgumentException("Value must be either Int or Bool");
        }
    }
}
