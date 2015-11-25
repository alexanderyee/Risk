package model;

import java.util.ArrayList;

public class RiskResources
{

    // MEMBER VARIABLES
    ArrayList<Territory> allTerritories;

    // CONSTRUCTOR
    public RiskResources()
    {
        allTerritories = new ArrayList<Territory>();
        setAllTerritories();
    }

    // PUBLIC METHODS
    // getters
    public ArrayList<Territory> getTerritories()
    {
        return allTerritories;
    }

    // constructor helper method
    private void setAllTerritories()
    {
        // initialize territories
        Territory na1 = new Territory(Countries.ALASKA, 80, 150);
        Territory na2 = new Territory(Countries.NW_TERRITORY, 170, 150);
        Territory na3 = new Territory(Countries.GREENLAND, 400, 140);
        Territory na4 = new Territory(Countries.ALBERTA, 175, 250);
        Territory na5 = new Territory(Countries.ONTARIO, 240, 235);
        Territory na6 = new Territory(Countries.QUEBEC, 315, 275);
        Territory na7 = new Territory(Countries.WESTERN_US, 175, 345);
        Territory na8 = new Territory(Countries.EASTERN_US, 275, 360);
        Territory na9 = new Territory(Countries.CENT_AMERICA, 190, 385);

        Territory sa1 = new Territory(Countries.VENENZUELA, 270, 495);
        Territory sa2 = new Territory(Countries.PERU, 300, 590);
        Territory sa3 = new Territory(Countries.BRAZIL, 360, 560);
        Territory sa4 = new Territory(Countries.ARGENTINA, 300, 680);

        Territory eu1 = new Territory(Countries.ICELAND, 500, 170);
        Territory eu2 = new Territory(Countries.SCANDINAVIA, 560, 205);
        Territory eu3 = new Territory(Countries.UKRAINE, 690, 270);
        Territory eu4 = new Territory(Countries.GREAT_BRITAIN, 440, 270);
        Territory eu5 = new Territory(Countries.N_EUROPE, 570, 290);
        Territory eu6 = new Territory(Countries.W_EUROPE, 510, 360);
        Territory eu7 = new Territory(Countries.S_EUROPE, 600, 390);

        Territory af1 = new Territory(Countries.N_AFRICA, 510, 480);
        Territory af2 = new Territory(Countries.EGYPT, 580, 480);
        Territory af3 = new Territory(Countries.E_AFRICA, 650, 540);
        Territory af4 = new Territory(Countries.CONGO, 630, 630);
        Territory af5 = new Territory(Countries.S_AFRICA, 630, 720);
        Territory af6 = new Territory(Countries.MADAGASCAR, 750, 720);

        Territory as1 = new Territory(Countries.SIBERIA, 840, 200);
        Territory as2 = new Territory(Countries.YAKUTSK, 930, 150);
        Territory as3 = new Territory(Countries.KAMCHATKA, 1020, 150);
        Territory as4 = new Territory(Countries.URAL, 790, 240);
        Territory as5 = new Territory(Countries.IRKUTSK, 920, 250);
        Territory as6 = new Territory(Countries.AFGHANISTAN, 760, 350);
        Territory as7 = new Territory(Countries.MONGOLIA, 920, 320);
        Territory as8 = new Territory(Countries.JAPAN, 1070, 300);
        Territory as9 = new Territory(Countries.CHINA, 900, 400);
        Territory as10 = new Territory(Countries.MIDDLE_EAST, 690, 450);
        Territory as11 = new Territory(Countries.INDIA, 840, 450);
        Territory as12 = new Territory(Countries.SIAM, 930, 510);

        Territory ar1 = new Territory(Countries.INDONESIA, 870, 600);
        Territory ar2 = new Territory(Countries.NEW_GUINEA, 1050, 555);
        Territory ar3 = new Territory(Countries.W_AUSTRALIA, 990, 660);
        Territory ar4 = new Territory(Countries.E_AUSTRALIA, 1020, 660);

        // initialize adjecents for each territory
        Territory[] na1Adj = { na2, na4, as3 };
        Territory[] na2Adj = { na1, na3, na4, na5 };
        Territory[] na3Adj = { na2, na5, na6, eu1 };
        Territory[] na4Adj = { na1, na2, na5, na7 };
        Territory[] na5Adj = { na2, na3, na4, na6, na7, na8 };
        Territory[] na6Adj = { na3, na5, na8 };
        Territory[] na7Adj = { na4, na5, na8, na9 };
        Territory[] na8Adj = { na5, na6, na7, na9 };
        Territory[] na9Adj = { na7, na8, sa1 };

        Territory[] sa1Adj = { na9, sa2, sa3 };
        Territory[] sa2Adj = { sa1, sa3, sa4 };
        Territory[] sa3Adj = { sa1, sa2, af1, sa4 };
        Territory[] sa4Adj = { sa2, sa3 };

        Territory[] eu1Adj = { na3, eu2, eu4 };
        Territory[] eu2Adj = { eu1, eu3, eu4, eu5 };
        Territory[] eu3Adj = { eu2, eu5, eu7, as4, as6, as10 };
        Territory[] eu4Adj = { eu1, eu2, eu5, eu6 };
        Territory[] eu5Adj = { eu2, eu3, eu4, eu6, eu7 };
        Territory[] eu6Adj = { eu4, eu5, eu7, af1 };
        Territory[] eu7Adj = { eu3, eu5, eu6, as10, af1, af2 };

        Territory[] af1Adj = { sa3, eu6, eu7, af2, af3, af4 };
        Territory[] af2Adj = { eu7, as10, af1, af3 };
        Territory[] af3Adj = { as10, af1, af2, af4, af6 };
        Territory[] af4Adj = { af1, af2, af3, af5 };
        Territory[] af5Adj = { af3, af4, af6 };
        Territory[] af6Adj = { af3, af5 };

        Territory[] as1Adj = { as2, as4, as5, as7, as9 };
        Territory[] as2Adj = { as1, as3, as4 };
        Territory[] as3Adj = { as2, as5, as7, as8, na1 };
        Territory[] as4Adj = { eu3, as1, as6, as9 };
        Territory[] as5Adj = { as1, as2, as3, as7 };
        Territory[] as6Adj = { eu5, as4, as9, as10, as11 };
        Territory[] as7Adj = { as1, as5, as8, as9 };
        Territory[] as8Adj = { as3, as7 };
        Territory[] as9Adj = { as1, as4, as7, as11, as12 };
        Territory[] as10Adj = { eu3, eu7, af2, af3, as6, as11 };
        Territory[] as11Adj = { as6, as9, as10, as12 };
        Territory[] as12Adj = { as9, as11, ar1 };

        Territory[] ar1Adj = { as12, ar2, ar3 };
        Territory[] ar2Adj = { ar1, ar4 };
        Territory[] ar3Adj = { ar1, ar4 };
        Territory[] ar4Adj = { ar2, ar3 };

        // set all adjacents for each territory
        na1.setAdj(na1Adj);
        na2.setAdj(na2Adj);
        na3.setAdj(na3Adj);
        na4.setAdj(na4Adj);
        na5.setAdj(na5Adj);
        na6.setAdj(na6Adj);
        na7.setAdj(na7Adj);
        na8.setAdj(na8Adj);
        na9.setAdj(na9Adj);

        sa1.setAdj(sa1Adj);
        sa2.setAdj(sa2Adj);
        sa3.setAdj(sa3Adj);
        sa4.setAdj(sa4Adj);

        eu1.setAdj(eu1Adj);
        eu2.setAdj(eu2Adj);
        eu3.setAdj(eu3Adj);
        eu4.setAdj(eu4Adj);
        eu5.setAdj(eu5Adj);
        eu6.setAdj(eu6Adj);
        eu7.setAdj(eu7Adj);

        af1.setAdj(af1Adj);
        af2.setAdj(af2Adj);
        af3.setAdj(af3Adj);
        af4.setAdj(af4Adj);
        af5.setAdj(af5Adj);
        af6.setAdj(af6Adj);

        as1.setAdj(as1Adj);
        as2.setAdj(as2Adj);
        as3.setAdj(as3Adj);
        as4.setAdj(as4Adj);
        as5.setAdj(as5Adj);
        as6.setAdj(as6Adj);
        as7.setAdj(as7Adj);
        as8.setAdj(as8Adj);
        as9.setAdj(as9Adj);
        as10.setAdj(as10Adj);
        as11.setAdj(as11Adj);
        as12.setAdj(as12Adj);

        ar1.setAdj(ar1Adj);
        ar2.setAdj(ar2Adj);
        ar3.setAdj(ar3Adj);
        ar4.setAdj(ar4Adj);

        // add all territories
        allTerritories.add(na1);
        allTerritories.add(na2);
        allTerritories.add(na3);
        allTerritories.add(na4);
        allTerritories.add(na5);
        allTerritories.add(na6);
        allTerritories.add(na7);
        allTerritories.add(na8);
        allTerritories.add(na9);
        allTerritories.add(sa1);
        allTerritories.add(sa2);
        allTerritories.add(sa3);
        allTerritories.add(sa4);

        allTerritories.add(eu1);
        allTerritories.add(eu2);
        allTerritories.add(eu3);
        allTerritories.add(eu4);
        allTerritories.add(eu5);
        allTerritories.add(eu6);
        allTerritories.add(eu7);

        allTerritories.add(af1);
        allTerritories.add(af2);
        allTerritories.add(af3);
        allTerritories.add(af4);
        allTerritories.add(af5);
        allTerritories.add(af6);

        allTerritories.add(as1);
        allTerritories.add(as2);
        allTerritories.add(as3);
        allTerritories.add(as4);
        allTerritories.add(as5);
        allTerritories.add(as6);
        allTerritories.add(as7);
        allTerritories.add(as8);
        allTerritories.add(as9);
        allTerritories.add(as10);
        allTerritories.add(as11);
        allTerritories.add(as12);

        allTerritories.add(ar1);
        allTerritories.add(ar2);
        allTerritories.add(ar3);
        allTerritories.add(ar4);
    }

}
