package application.entity;

public enum Material {
    PLASTIC("plastic"),
    WOOD("wood"),
    MARBLE("marble"),
    FIBERBOARD("fiberboard");

    private final String stringMaterial;

    Material(String stringMaterial) {
        this.stringMaterial = stringMaterial;
    }

    public String getStringMaterial() {
        return stringMaterial;
    }
}
