package model;

public enum Continent {
	NAMERICA, SAMERICA, EUROPE, AFRICA, ASIA, AUSTRALIA;
	
	public Color getColor(Continent cont) {
		if (cont.equals(this.NAMERICA))
			return Color.YELLOW;
		if (cont.equals(this.SAMERICA))
			return Color.RED;
		if (cont.equals(this.EUROPE))
			return Color.BLUE;
		if (cont.equals(this.AFRICA))
			return Color.ORANGE;
		if (cont.equals(this.ASIA))
			return Color.GREEN;
		return Color.PURPLE;
	}
}
