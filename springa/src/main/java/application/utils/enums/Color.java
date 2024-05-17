package application.utils.enums;

public enum Color {
    RED("red"),
    BLUE("blue"),
    WHITE("white"),
    GREEN("green"),
    YELLOW("yellow"),
    GRAY("gray"),
    BLACK("black");


    private final String stringColor;

    Color(String stringColor) {
        this.stringColor = stringColor;
    }

    public String getStringColor() {
        return stringColor;
    }
}
