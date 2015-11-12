package model;

public enum Countries {
    ALASKA, NW_TERRITORY, GREENLAND, ALBERTA, ONTARIO, QUEBEC, WESTERN_US, EASTERN_US, CENT_AMERICA, VENENZUELA, PERU, BRAZIL, ARGENTINA, ICELAND, SCANDINAVIA, UKRAINE, GREAT_BRITAIN, N_EUROPE, W_EUROPE, S_EUROPE, N_AFRICA, EGYPT, E_AFRICA, CONGO, S_AFRICA, MADAGASCAR, SIBERIA, YAKUTSK, KAMCHATKA, URAL, IRKUTSK, AFGHANISTAN, MONGOLIA, JAPAN, CHINA, MIDDLE_EAST, INDIA, SIAM, INDONESIA, NEW_GUINEA, W_AUSTRALIA, E_AUSTRALIA;
	public Continent getContinent() {
		if (this.equals(this.ALASKA) || this.equals(this.NW_TERRITORY) || this.equals(this.GREENLAND)
				|| this.equals(this.ALBERTA) || this.equals(this.ONTARIO) || this.equals(this.QUEBEC)
				|| this.equals(this.WESTERN_US) || this.equals(this.EASTERN_US) || this.equals(CENT_AMERICA))
			return Continent.NAMERICA;
		if (this.equals(this.VENENZUELA) || this.equals(this.PERU) || this.equals(this.BRAZIL)
				|| this.equals(this.ARGENTINA))
			return Continent.SAMERICA;
		if (this.equals(this.ICELAND) || this.equals(this.SCANDINAVIA) || this.equals(this.UKRAINE)
				|| this.equals(this.GREAT_BRITAIN) || this.equals(this.N_EUROPE) || this.equals(this.W_EUROPE)
				|| this.equals(this.S_EUROPE))
			return Continent.EUROPE;
		if (this.equals(this.N_AFRICA) ||  this.equals(this.EGYPT) ||  this.equals(this.E_AFRICA) ||  this.equals(this.CONGO) ||  this.equals(this.S_AFRICA) ||  this.equals(this.MADAGASCAR))
				return Continent.AFRICA;
		if (this.equals(this.INDONESIA) ||  this.equals(this.NEW_GUINEA) ||  this.equals(this.W_AUSTRALIA) ||  this.equals(this.E_AUSTRALIA))
				return Continent.AUSTRALIA;
		return Continent.ASIA;
	}

}
