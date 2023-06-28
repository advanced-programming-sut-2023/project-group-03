package Model.Units.Enums;

import graphicsTest.TestMap3;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Arrays;

public enum UnitGraphics {
    //troops
    KING(TroopTypes.KING.getName(), new ArrayList<>(
            Arrays.asList(new Image(TestMap3.class.getResource("/images/troops/king.png").toExternalForm())))),

    ARCHER(TroopTypes.ARCHER.getName(), new ArrayList<>(
            Arrays.asList(new Image(TestMap3.class.getResource("/images/troops/archer.png").toExternalForm())))),

    CROSSBOWMEN(TroopTypes.CROSSBOWMEN.getName(), new ArrayList<>(
            Arrays.asList(new Image(TestMap3.class.getResource("/images/troops/crossbowmen.png").toExternalForm())))),

    SPEARMEN(TroopTypes.SPEARMEN.getName(), new ArrayList<>(
            Arrays.asList(new Image(TestMap3.class.getResource("/images/troops/spearmen.png").toExternalForm())))),

    PIKEMEN(TroopTypes.PIKEMEN.getName(), new ArrayList<>(
            Arrays.asList(new Image(TestMap3.class.getResource("/images/troops/pikemen.png").toExternalForm())))),
    MACEMEN(TroopTypes.MACEMEN.getName(), new ArrayList<>(
            Arrays.asList(new Image(TestMap3.class.getResource("/images/troops/macemen.png").toExternalForm())))),

    SWORDMEN(TroopTypes.SWORDMEN.getName(), new ArrayList<>() {{
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/soldier/walk/down/anim1.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/soldier/walk/down/anim2.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/soldier/walk/down/anim3.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/soldier/walk/down/anim4.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/soldier/walk/down/anim5.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/soldier/walk/down/anim6.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/soldier/walk/down/anim7.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/soldier/walk/down/anim8.png").toExternalForm()));

    }}),
    KNIGHT(TroopTypes.KNIGHT.getName(), new ArrayList<>(
            Arrays.asList(new Image(TestMap3.class.getResource("/images/troops/knight.png").toExternalForm())))),

    TUNELLER(TroopTypes.TUNELLER.getName(), new ArrayList<>(
            Arrays.asList(new Image(TestMap3.class.getResource("/images/troops/tunneler.png").toExternalForm())))),

    LADDERMEN(TroopTypes.LADDERMEN.getName(), new ArrayList<>(
            Arrays.asList(new Image(TestMap3.class.getResource("/images/troops/laddermen.png").toExternalForm())))),

    BLACKMONK(TroopTypes.BLACKMONK.getName(), new ArrayList<>(
            Arrays.asList(new Image(TestMap3.class.getResource("/images/troops/black_monk.png").toExternalForm())))),

    ARABIAN_BOWS(TroopTypes.ARABIAN_BOWS.getName(), new ArrayList<>(
            Arrays.asList(new Image(TestMap3.class.getResource("/images/troops/arabArcher.png").toExternalForm())))),

    SLAVES(TroopTypes.SLAVES.getName(), new ArrayList<>(
            Arrays.asList(new Image(TestMap3.class.getResource("/images/troops/slave.png").toExternalForm())))),

    SLINGERS(TroopTypes.SLINGERS.getName(), new ArrayList<>(
            Arrays.asList(new Image(TestMap3.class.getResource("/images/troops/slinger.png").toExternalForm())))),

    ASSASSINS(TroopTypes.ASSASSINS.getName(), new ArrayList<>(
            Arrays.asList(new Image(TestMap3.class.getResource("/images/troops/assasin.png").toExternalForm())))),

    HORSE_ARCHERS(TroopTypes.HORSE_ARCHERS.getName(), new ArrayList<>(
            Arrays.asList(new Image(TestMap3.class.getResource("/images/troops/horse_archer.png").toExternalForm())))),
    //throwers
    CATAPULTS(ThrowerTypes.CATAPULTS.getName(), new ArrayList<>(
            Arrays.asList(new Image(TestMap3.class.getResource("/images/troops/catapults.png").toExternalForm())))),

    TREBUCHETS(ThrowerTypes.TREBUCHETS.getName(), new ArrayList<>(
            Arrays.asList(new Image(TestMap3.class.getResource("/images/troops/trebuchets.png").toExternalForm())))),

    FIRE_BALLISTA(ThrowerTypes.FIRE_BALLISTA.getName(), new ArrayList<>(
            Arrays.asList(new Image(TestMap3.class.getResource("/images/troops/ballista.png").toExternalForm())))),

    FIRE_BALLISTA_ON_TOWER(ThrowerTypes.FIRE_BALLISTA_ON_TOWER.getName(), new ArrayList<>(
            Arrays.asList(new Image(TestMap3.class.getResource("/images/troops/ballista.png").toExternalForm())))),

    //others
    ENGINEER("engineer", null),

    OX("ox", new ArrayList<>(
            Arrays.asList(new Image(TestMap3.class.getResource("/images/troops/ox.png").toExternalForm())))),

    WORKER("worker", new ArrayList<>() {{
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/worker/walk/down/anim1.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/worker/walk/down/anim2.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/worker/walk/down/anim3.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/worker/walk/down/anim4.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/worker/walk/down/anim5.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/worker/walk/down/anim6.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/worker/walk/down/anim7.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/worker/walk/down/anim8.png").toExternalForm()));
    }}),
    BATTERING_RAM("battering ram", new ArrayList<>(
            Arrays.asList(new Image(TestMap3.class.getResource("/images/troops/battering_ram.png").toExternalForm())))),

    PORTABLE_SHIELDS("portable shields", new ArrayList<>(
            Arrays.asList(new Image(TestMap3.class.getResource("/images/troops/portable_shield.png").toExternalForm())))),

    SIEGE_TOWER("siege tower", new ArrayList<>(
            Arrays.asList(new Image(TestMap3.class.getResource("/images/troops/siegeTower.png").toExternalForm())))),

    WALL_CLIMBER("wall climber", null),


    ;
    private String name;
    private ArrayList<Image> images;
    private static ArrayList<Image> soldierImages = new ArrayList<>() {{
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/soldier/walk/down/anim1.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/soldier/walk/down/anim2.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/soldier/walk/down/anim3.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/soldier/walk/down/anim4.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/soldier/walk/down/anim5.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/soldier/walk/down/anim6.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/soldier/walk/down/anim7.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/soldier/walk/down/anim8.png").toExternalForm()));

    }};

    private static ArrayList<Image> workerImages = new ArrayList<>() {{
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/worker/walk/down/anim1.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/worker/walk/down/anim2.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/worker/walk/down/anim3.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/worker/walk/down/anim4.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/worker/walk/down/anim5.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/worker/walk/down/anim6.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/worker/walk/down/anim7.png").toExternalForm()));
        add(new Image(TestMap3.class.getResource("/images/troops/Humans/worker/walk/down/anim8.png").toExternalForm()));
    }};

    UnitGraphics (String name, ArrayList<Image> images) {
        this.name = name;
        this.images = images;
    }

    public UnitGraphics getUnitGraphicsByName(String name) {
        for (UnitGraphics unitGraphic : UnitGraphics.values()) {
            if (unitGraphic.name.equals(name)) return unitGraphic;
        }
        return null;
    }

    public ArrayList<Image> getImages() {
        return images;
    }
}
