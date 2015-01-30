package org.moparscape.elysium.entity;

import org.moparscape.elysium.def.*;
import org.moparscape.elysium.util.PersistenceManager;
import org.moparscape.elysium.world.Point;
import org.moparscape.elysium.world.TelePoint;

import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class DefinitionHandler {

    private static HashMap<Integer, ItemArrowHeadDef> arrowHeads;
    private static HashMap<Integer, ItemBowStringDef> bowString;
    private static HashMap<Integer, CerterDef> certers;
    private static HashMap<Integer, ItemDartTipDef> dartTips;
    private static DoorDef[] doors;
    private static HashMap<Integer, FiremakingDef> firemaking;
    private static GameObjectDef[] gameObjects;
    private static HashMap<Integer, ItemGemDef> gems;
    private static ItemHerbSecond[] herbSeconds;
    private static HashMap<Integer, int[]> itemAffectedTypes;
    private static HashMap<Integer, ItemCookingDef> itemCooking;
    private static ItemCraftingDef[] itemCrafting;
    private static HashMap<Integer, Integer> itemEdibleHeals;
    private static HashMap<Integer, ItemHerbDef> itemHerb;
    private static HashMap<Integer, ItemSmeltingDef> itemSmelting;
    private static ItemSmithingDef[] itemSmithing;
    private static HashMap<Integer, ItemUnIdentHerbDef> itemUnIdentHerb;
    private static HashMap<Integer, ItemWieldableDef> itemWieldable;
    private static ItemDef[] items;
    private static List[] keyChestLoots;
    private static HashMap<Integer, ItemLogCutDef> logCut;
    private static NPCDef[] npcs;
    private static HashMap<Integer, ObjectFishingDef[]> objectFishing;
    private static HashMap<Integer, ObjectMiningDef> objectMining;
    private static HashMap<Point, TelePoint> objectTelePoints;
    private static HashMap<Integer, ObjectWoodcuttingDef> objectWoodcutting;
    private static PrayerDef[] prayers;
    private static HashMap<Integer, Integer> spellAggressiveLvl;
    private static SpellDef[] spells;
    private static HashMap<Integer, ThievableNPCDef> thievableNPCs;
    private static HashMap<Integer, ThievableObjectDef> thievableObjects;
    private static TileDef[] tiles;

    /**
     * @param id the npcs ID
     * @return the CerterDef for the given npc
     */
    public static CerterDef getCerterDef(int id) {
        return certers.get(id);
    }

    /**
     * @return the ItemCraftingDef for the requested item
     */
    public static ItemCraftingDef getCraftingDef(int id) {
        if (id < 0 || id >= itemCrafting.length) {
            return null;
        }
        return itemCrafting[id];
    }

    /**
     * @param id the entities ID
     * @return the DoorDef with the given ID
     */
    public static DoorDef getDoorDef(int id) {
        if (id < 0 || id >= doors.length) {
            return null;
        }
        return doors[id];
    }

    /**
     * @return the FiremakingDef for the given log
     */
    public static FiremakingDef getFiremakingDef(int id) {
        return firemaking.get(id);
    }

    /**
     * @param id the entities ID
     * @return the GameObjectDef with the given ID
     */
    public static GameObjectDef getGameObjectDef(int id) {
        if (id < 0 || id >= gameObjects.length) {
            return null;
        }
        return gameObjects[id];
    }

    /**
     * @param the items type
     * @return the types of items affected
     */
    public static int[] getItemAffectedTypes(int type) {
        return itemAffectedTypes.get(type);
    }

    /**
     * @return the ItemArrowHeadDef for the given arrow
     */
    public static ItemArrowHeadDef getItemArrowHeadDef(int id) {
        return arrowHeads.get(id);
    }

    /**
     * @return the ItemBowStringDef for the given bow
     */
    public static ItemBowStringDef getItemBowStringDef(int id) {
        return bowString.get(id);
    }

    /**
     * @param id the entities ID
     * @return the ItemCookingDef with the given ID
     */
    public static ItemCookingDef getItemCookingDef(int id) {
        return itemCooking.get(id);
    }

    /**
     * @return the ItemDartTipDef for the given tip
     */
    public static ItemDartTipDef getItemDartTipDef(int id) {
        return dartTips.get(id);
    }

    /**
     * @param id the entities ID
     * @return the ItemDef with the given ID
     */
    public static ItemDef getItemDef(int id) {
        if (id < 0 || id >= items.length) {
            return null;
        }
        return items[id];
    }

    /**
     * @param the items id
     * @return the amount eating the item should heal
     */
    public static int getItemEdibleHeals(int id) {
        Integer heals = itemEdibleHeals.get(id);
        if (heals != null) {
            return heals.intValue();
        }
        return 0;
    }

    /**
     * @return the ItemGemDef for the given gem
     */
    public static ItemGemDef getItemGemDef(int id) {
        return gems.get(id);
    }

    /**
     * @param id the entities ID
     * @return the ItemHerbDef with the given ID
     */
    public static ItemHerbDef getItemHerbDef(int id) {
        return itemHerb.get(id);
    }

    /**
     * @return the ItemHerbSecond for the given second ingredient
     */
    public static ItemHerbSecond getItemHerbSecond(int secondID, int unfinishedID) {
        for (ItemHerbSecond def : herbSeconds) {
            if (def.getSecondID() == secondID && def.getUnfinishedID() == unfinishedID) {
                return def;
            }
        }
        return null;
    }

    /**
     * @return the ItemLogCutDef for the given log
     */
    public static ItemLogCutDef getItemLogCutDef(int id) {
        return logCut.get(id);
    }

    /**
     * @param id the entities ID
     * @return the ItemSmeltingDef with the given ID
     */
    public static ItemSmeltingDef getItemSmeltingDef(int id) {
        return itemSmelting.get(id);
    }

    /**
     * @param id the entities ID
     * @return the ItemUnIdentHerbDef with the given ID
     */
    public static ItemUnIdentHerbDef getItemUnIdentHerbDef(int id) {
        return itemUnIdentHerb.get(id);
    }

    /**
     * @param id the entities ID
     * @return the ItemWieldableDef with the given ID
     */
    public static ItemWieldableDef getItemWieldableDef(int id) {
        return itemWieldable.get(id);
    }

    public static List[] getKeyChestLoots() {
        return keyChestLoots.clone();
    }

    /**
     * @param id the entities ID
     * @return the NPCDef with the given ID
     */
    public static NPCDef getNpcDef(int id) {
        if (id < 0 || id >= npcs.length) {
            return null;
        }
        return npcs[id];
    }

    /**
     * @param id the entities ID
     * @return the ObjectFishingDef with the given ID
     */
    public static ObjectFishingDef getObjectFishingDef(int id, int click) {
        ObjectFishingDef[] defs = objectFishing.get(id);
        if (defs == null) {
            return null;
        }
        return defs[click];
    }

    /**
     * @param id the entities ID
     * @return the ObjectMiningDef with the given ID
     */
    public static ObjectMiningDef getObjectMiningDef(int id) {
        return objectMining.get(id);
    }

    /**
     * @param the point we are currently at
     * @return the point we should be teleported to
     */
    public static Point getObjectTelePoint(Point location, String command) {
        TelePoint point = objectTelePoints.get(location);
        if (point == null) {
            return null;
        }
        if (command == null || point.getCommand().equalsIgnoreCase(command)) {
            return point;
        }
        return null;
    }

    /**
     * @param id the entities ID
     * @return the ObjectWoodcuttingDef with the given ID
     */
    public static ObjectWoodcuttingDef getObjectWoodcuttingDef(int id) {
        return objectWoodcutting.get(id);
    }

    /**
     * @param id the entities ID
     * @return the PrayerDef with the given ID
     */
    public static PrayerDef getPrayerDef(int id) {
        if (id < 0 || id >= prayers.length) {
            return null;
        }
        return prayers[id];
    }

    /**
     * @return the ItemSmithingDef for the requested item
     */
    public static ItemSmithingDef getSmithingDef(int id) {
        if (id < 0 || id >= itemSmithing.length) {
            return null;
        }
        return itemSmithing[id];
    }

    /**
     * @param the spells id
     * @return the lvl of the spell (for calculating what it hits)
     */
    public static int getSpellAggressiveLvl(int id) {
        Integer lvl = spellAggressiveLvl.get(id);
        if (lvl != null) {
            return lvl.intValue();
        }
        return 0;
    }

    /**
     * @param id the entities ID
     * @return the SpellDef with the given ID
     */
    public static SpellDef getSpellDef(int id) {
        if (id < 0 || id >= spells.length) {
            return null;
        }
        return spells[id];
    }

    public static ThievableNPCDef getThievableNPCDef(int id) {
        ThievableNPCDef def = thievableNPCs.get(id);
        return def;
    }

    static {
        doors = (DoorDef[]) PersistenceManager.load("defs/DoorDef.xml");
        gameObjects = (GameObjectDef[]) PersistenceManager.load("defs/GameObjectDef.xml");
        npcs = (NPCDef[]) PersistenceManager.load("defs/NPCDef.xml");

        thievableNPCs = (HashMap) PersistenceManager.load("defs/ThievableNpcs.xml");
        thievableObjects = (HashMap) PersistenceManager.load("defs/ThievableObjects.xml");

        prayers = (PrayerDef[]) PersistenceManager.load("defs/PrayerDef.xml");
        items = (ItemDef[]) PersistenceManager.load("defs/ItemDef.xml");
        spells = (SpellDef[]) PersistenceManager.load("defs/SpellDef.xml");
        tiles = (TileDef[]) PersistenceManager.load("defs/TileDef.xml");

        keyChestLoots = (List[]) PersistenceManager.load("defs/KeyChestLoot.xml");
        herbSeconds = (ItemHerbSecond[]) PersistenceManager.load("defs/ItemHerbSecond.xml");
        dartTips = (HashMap) PersistenceManager.load("defs/ItemDartTipDef.xml");
        gems = (HashMap) PersistenceManager.load("defs/ItemGemDef.xml");
        logCut = (HashMap) PersistenceManager.load("defs/ItemLogCutDef.xml");
        bowString = (HashMap) PersistenceManager.load("defs/ItemBowStringDef.xml");
        arrowHeads = (HashMap) PersistenceManager.load("defs/ItemArrowHeadDef.xml");
        firemaking = (HashMap) PersistenceManager.load("defs/FiremakingDef.xml");
        itemAffectedTypes = (HashMap) PersistenceManager.load("defs/ItemAffectedTypes.xml");
        itemWieldable = (HashMap) PersistenceManager.load("defs/ItemWieldableDef.xml");
        itemUnIdentHerb = (HashMap) PersistenceManager.load("defs/ItemUnIdentHerbDef.xml");
        itemHerb = (HashMap) PersistenceManager.load("defs/ItemHerbDef.xml");
        itemEdibleHeals = (HashMap) PersistenceManager.load("defs/ItemEdibleHeals.xml");
        itemCooking = (HashMap) PersistenceManager.load("defs/ItemCookingDef.xml");
        itemSmelting = (HashMap) PersistenceManager.load("defs/ItemSmeltingDef.xml");
        itemSmithing = (ItemSmithingDef[]) PersistenceManager.load("defs/ItemSmithingDef.xml");
        itemCrafting = (ItemCraftingDef[]) PersistenceManager.load("defs/ItemCraftingDef.xml");
        objectMining = (HashMap) PersistenceManager.load("defs/ObjectMining.xml");
        objectWoodcutting = (HashMap) PersistenceManager.load("defs/ObjectWoodcutting.xml");
        objectFishing = (HashMap) PersistenceManager.load("defs/ObjectFishing.xml");
        spellAggressiveLvl = (HashMap) PersistenceManager.load("defs/SpellAggressiveLvl.xml");

        objectTelePoints = (HashMap) PersistenceManager.load("locs/ObjectTelePoints.xml");
        certers = (HashMap) PersistenceManager.load("defs/NpcCerters.xml");
    }

    /**
     * @param id the entities ID
     * @return the TileDef with the given ID
     */
    public static TileDef getTileDef(int id) {
        if (id < 0 || id >= tiles.length) {
            return null;
        }
        return tiles[id];
    }
}
