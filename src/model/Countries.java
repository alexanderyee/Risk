package model;

public enum Countries
{

    ALASKA("na1"), NW_TERRITORY("na2"), GREENLAND("na3"), ALBERTA(
            "na4"), ONTARIO("na5"), QUEBEC("na6"), WESTERN_US(
                    "na7"), EASTERN_US("na8"), CENT_AMERICA("na9"),

    VENENZUELA("sa1"), PERU("sa2"), BRAZIL("sa3"), ARGENTINA("sa4"),

    ICELAND("eu1"), SCANDINAVIA("eu2"), UKRAINE("eu3"), GREAT_BRITAIN(
            "eu4"), N_EUROPE("eu5"), W_EUROPE("eu6"), S_EUROPE("eu7"),

    N_AFRICA("af1"), EGYPT("af2"), E_AFRICA("af3"), CONGO("af4"), S_AFRICA(
            "af5"), MADAGASCAR("af6"),

    SIBERIA("as1"), YAKUTSK("as2"), KAMCHATKA("as3"), URAL("as4"), IRKUTSK(
            "as5"), AFGHANISTAN("as6"), MONGOLIA("as7"), JAPAN("as8"), CHINA(
                    "as9"), MIDDLE_EAST("as10"), INDIA("as11"), SIAM("as12"),

    INDONESIA("ar1"), NEW_GUINEA("ar2"), W_AUSTRALIA("ar3"), E_AUSTRALIA("ar4");
    private final String buttonTitle;

    private Countries(String str)
    {
        this.buttonTitle = str;
    }

    public Continent getContinent()
    {
        if (this.equals(this.ALASKA) || this.equals(this.NW_TERRITORY)
                || this.equals(this.GREENLAND) || this.equals(this.ALBERTA)
                || this.equals(this.ONTARIO) || this.equals(this.QUEBEC)
                || this.equals(this.WESTERN_US) || this.equals(this.EASTERN_US)
                || this.equals(CENT_AMERICA))
            return Continent.NAMERICA;
        if (this.equals(this.VENENZUELA) || this.equals(this.PERU)
                || this.equals(this.BRAZIL) || this.equals(this.ARGENTINA))
            return Continent.SAMERICA;
        if (this.equals(this.ICELAND) || this.equals(this.SCANDINAVIA)
                || this.equals(this.UKRAINE) || this.equals(this.GREAT_BRITAIN)
                || this.equals(this.N_EUROPE) || this.equals(this.W_EUROPE)
                || this.equals(this.S_EUROPE))
            return Continent.EUROPE;
        if (this.equals(this.N_AFRICA) || this.equals(this.EGYPT)
                || this.equals(this.E_AFRICA) || this.equals(this.CONGO)
                || this.equals(this.S_AFRICA) || this.equals(this.MADAGASCAR))
            return Continent.AFRICA;
        if (this.equals(this.INDONESIA) || this.equals(this.NEW_GUINEA)
                || this.equals(this.W_AUSTRALIA)
                || this.equals(this.E_AUSTRALIA))
            return Continent.AUSTRALIA;
        return Continent.ASIA;
    }

    public String getButtonTitle()
    {
        return this.buttonTitle;
    }

    public static void main(String[] args)
    {
        System.out.println(Countries.ALASKA.getButtonTitle());
    }
}
