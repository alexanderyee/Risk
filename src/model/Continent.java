package model;

public enum Continent
{
    NAMERICA, SAMERICA, EUROPE, AFRICA, ASIA, AUSTRALIA;
    public Color getColor()
    {
        if (this.equals(this.NAMERICA)) return Color.YELLOW;
        if (this.equals(this.SAMERICA)) return Color.RED;
        if (this.equals(this.EUROPE)) return Color.BLUE;
        if (this.equals(this.AFRICA)) return Color.ORANGE;
        if (this.equals(this.ASIA)) return Color.GREEN;
        return Color.PURPLE;
    }
}
