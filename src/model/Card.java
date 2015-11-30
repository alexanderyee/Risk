package model;

public class Card
{

    // MEMBER VARIABLES
    private CardType type;
    private Countries country;

    // CONSTRUCTOR
    public Card(CardType t, Countries c)
    {
        type = t;
        country = c;
    }

    // PUBLIC METHODS
    // getters
    public CardType getCardType()
    {
        return type;
    }

    public Countries getCountry()
    {
        return country;
    }

    public String toString()
    {
        return country.toString() + type.toString();
    }

}
