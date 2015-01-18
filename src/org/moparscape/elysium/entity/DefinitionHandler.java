package org.moparscape.elysium.entity;

import org.moparscape.elysium.def.ItemDef;

/**
 * Created by IntelliJ IDEA.
 *
 * @author lothy
 */
public final class DefinitionHandler {

    private static final ItemDef[] items;

    public static ItemDef getItemDef(int itemId) {
        return items[itemId];
    }

    static {
        try {
//            DataLayer data = new MysqlDataLayer();
//
//            items = data.loadItemDefinitions();
            items = new ItemDef[2000];
            for (int i = 0; i < items.length; i++)
                items[i] = new ItemDef("fake", "fake", "fake", 100, 995, false, false, 1, false, false);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }
}
