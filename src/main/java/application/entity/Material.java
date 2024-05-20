package application.entity;

public enum Material {

    PLASTIC("plastic"),
    WOOD("wood"),
    MARBLE("marble"),
    FIBERBOARD("fiberboard"),
    STEEL("steel");

    private final String stringMaterial;

    Material(String stringMaterial) {
        this.stringMaterial = stringMaterial;
    }

    public String getStringMaterial() {
        return stringMaterial;
    }
}
