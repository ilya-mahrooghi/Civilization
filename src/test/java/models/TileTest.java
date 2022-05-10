package models;

import models.Feature.TileFeature;
import models.Feature.TileFeatureEnum;
import models.Improvement.TileImprovement;
import models.Improvement.TileImprovementEnum;
import models.Resource.TileResource;
import models.Resource.TileResourceEnum;
import models.Tile.Tile;
import models.Tile.TileMode;
import models.Tile.TileModeEnum;
import models.Units.Combat.CombatUnits;
import models.Units.Nonecombat.NoneCombatUnits;
import models.Units.UnitNameEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TileTest {
    private Tile tile;
    private Tile tile2;
    private Player player;
    private User user;
    private GameMap gameMap;
    ArrayList<Player> players = new ArrayList<>();

    @BeforeEach
    private void setUp(){
        ArrayList<TileFeature> tileFeatures = new ArrayList<>();
        tileFeatures.add(new TileFeature(TileFeatureEnum.FOREST));
        tile2 = new Tile(null, null, null);
        tile = new Tile(new TileMode(TileModeEnum.SNOW), new TileResource(TileResourceEnum.HORSE), tileFeatures);
        tile.setImprovement(new TileImprovement(TileImprovementEnum.FARM));
        user = new User("dfc","dks","dskjhv");
        player = new Player(user);
        players.add(player);
        gameMap = new GameMap(players);
    }
    @Test
    public void checkEnumsFeatureTest(){
        ArrayList<Enum> whereCanBeFind = new ArrayList<>();
        whereCanBeFind.add(TileFeatureEnum.FOREST);
        Assertions.assertEquals(true, tile.checkEnums(whereCanBeFind));
    }

    @Test
    public void checkEnumsResourceTest(){
        ArrayList<Enum> whereCanBeFind = new ArrayList<>();
        whereCanBeFind.add(TileResourceEnum.HORSE);
        Assertions.assertEquals(true, tile.checkEnums(whereCanBeFind));
    }

    @Test
    public void checkEnumsTileModeTest(){
        ArrayList<Enum> whereCanBeFind = new ArrayList<>();
        whereCanBeFind.add(TileModeEnum.SNOW);
        Assertions.assertEquals(true, tile.checkEnums(whereCanBeFind));
    }

    @Test
    public void falseCheckEnumsTileModeTest(){
        ArrayList<Enum> whereCanBeFind = new ArrayList<>();
        Assertions.assertEquals(false, tile.checkEnums(whereCanBeFind));
    }

    @Test
    public void cloneTest(){
        Tile clonedTile = tile.clone();
        Assertions.assertEquals(tile.getResource().getResourceName(), clonedTile.getResource().getResourceName());
    }

    @Test
    public void isNeighborTest(){
        Assertions.assertEquals(false, Tile.isNeighbor(0,0,0,0));
    }

    @Test
    public void isNeighborTest2(){
        Assertions.assertEquals(false, Tile.isNeighbor(0,0,7,0));
    }

    @Test
    public void isNeighborTest3(){
        Assertions.assertEquals(true, Tile.isNeighbor(0,1,0,0));
    }

    @Test
    public void isNeighborTest4(){
        Assertions.assertEquals(true, Tile.isNeighbor(1,0,0,0));
    }

    @Test
    public void isNeighborTest5(){
        Assertions.assertEquals(true, Tile.isNeighbor(0,6,1,5));
    }

    @Test
    public void isNeighborTest6(){
        Assertions.assertEquals(true, Tile.isNeighbor(1,1,0,0));
    }

    @Test
    public void hasFeatureTest(){
        Assertions.assertEquals(true, tile.hasFeature(TileFeatureEnum.FOREST));
    }

    @Test
    public void wrongHasFeatureTest(){
        Assertions.assertEquals(false, tile.hasFeature(TileFeatureEnum.ICE));
    }

    @Test
    public void addUpFeaturesMovementCostsTest(){
        ArrayList<TileFeature> tileFeatures = new ArrayList<>();
        tileFeatures.add(new TileFeature(TileFeatureEnum.FOREST));
        Assertions.assertEquals(2.0, tile.addUpFeaturesMovementCosts(tileFeatures));
    }

    @Test
    public void addUpFeaturesTroopBoostTest(){
        ArrayList<TileFeature> tileFeatures = new ArrayList<>();
        tileFeatures.add(new TileFeature(TileFeatureEnum.FOREST));
        Assertions.assertEquals(0.25, tile.addUpFeaturesTroopBoost(tileFeatures));
    }

    @Test
    public void addUpFeaturesGoldTest(){
        ArrayList<TileFeature> tileFeatures = new ArrayList<>();
        tileFeatures.add(new TileFeature(TileFeatureEnum.FOREST));
        Assertions.assertEquals(0, tile.addUpFeaturesGold(tileFeatures));
    }

    @Test
    public void addUpFeaturesFoodTest(){
        ArrayList<TileFeature> tileFeatures = new ArrayList<>();
        tileFeatures.add(new TileFeature(TileFeatureEnum.FOREST));
        Assertions.assertEquals(1, tile.addUpFeaturesFood(tileFeatures));
    }

    @Test
    public void addUpFeaturesProductionTest(){
        ArrayList<TileFeature> tileFeatures = new ArrayList<>();
        tileFeatures.add(new TileFeature(TileFeatureEnum.FOREST));
        Assertions.assertEquals(1, tile.addUpFeaturesProduction(tileFeatures));
    }

    @Test
    public void gettersTest(){
        Double Mp = tile.getMp();
        int food = tile.getFood();
        int gold = tile.getGold();
        int production = tile.getProduction();
        int economy = tile.getEconomy();
        Double combatBonus = tile.getCombatBonus();
        Assertions.assertEquals(3.0, Mp);
    }

    @Test
    public void setUnitsTest(){
        CombatUnits combatUnits = new CombatUnits(tile, UnitNameEnum.SETTLER, player);
        NoneCombatUnits noneCombatUnits = new NoneCombatUnits(tile, UnitNameEnum.SETTLER, player);
        tile.setCombatUnits(combatUnits);;
        tile.setNoneCombatUnits(noneCombatUnits);
        tile.setHasRoad(false);
        Assertions.assertEquals(false, tile.getHasRoad());
    }

    @Test
    public void hasOwnerTest(){
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City(tile, gameMap, "lkjdn"));
        player.setCities(cities);
        Assertions.assertEquals(true, Tile.hasOwner(tile, players));
    }

    @Test
    public void wrongHasOwnerTest(){
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City(tile2, gameMap, "lkjdn"));
        player.setCities(cities);
        Assertions.assertEquals(false, Tile.hasOwner(tile, players));
    }

    @Test
    public void featureTest(){
        TileFeature tileFeature = new TileFeature(TileFeatureEnum.PLAIN);
        tile.setFeature(tileFeature);
        Assertions.assertEquals(tileFeature, tile.getFeature());
    }
}
