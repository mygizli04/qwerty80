package me.qwerty80;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class LootTable {

    boolean rare = Utils.percentage(10);
    LootType weapons = new LootType(100, rare);
    LootType healing = new LootType(30, rare);
    LootType ammo = new LootType(20, rare);
    LootType building = new LootType(100, rare);
    LootType armor = new LootType(100, rare); // not going to use for now.

    ItemStack[] chooseItems(Items[] items) {
        Items chosen = Utils.choose(items);
        ItemStack[] ret = new ItemStack[chosen.items.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = chosen.items[i].item;
        }
        return ret;
    }

    Lootable[] getLootable() {
        ArrayList<Lootable> list = new ArrayList<Lootable>();
        if (weapons.win) {
            switch (weapons.rarity) {
                case COMMON:
                    for ( ItemStack item: chooseItems(Table.Weapon.common)){
                        list.add(new Lootable(item));
                    }
                    break;
                case RARE:
                    for ( ItemStack item: chooseItems(Table.Weapon.rare)){
                        list.add(new Lootable(item));
                    }
                    break;
                case EPIC:
                    for ( ItemStack item: chooseItems(Table.Weapon.rare)){
                        list.add(new Lootable(item));
                    }
                    break;
                case LEGENDARY:
                    for ( ItemStack item: chooseItems(Table.Weapon.legendary)){
                        list.add(new Lootable(item));
                    }
                    break;
            }
        }

        if (healing.win) {
            switch (healing.rarity) {
                case COMMON:
                    for ( ItemStack item: chooseItems(Table.Healing.common)){
                        list.add(new Lootable(item));
                    }
                    break;
                case RARE:
                    for ( ItemStack item: chooseItems(Table.Healing.rare)){
                        list.add(new Lootable(item));
                    }
                    break;
                case EPIC:
                    for ( ItemStack item: chooseItems(Table.Healing.rare)){
                        list.add(new Lootable(item));
                    }
                    break;
                case LEGENDARY:
                    for ( ItemStack item: chooseItems(Table.Healing.legendary)){
                        list.add(new Lootable(item));
                    }
                    break;
            }
        }

        if (ammo.win) {
            switch (ammo.rarity) {
                case COMMON:
                    for ( ItemStack item: chooseItems(Table.Ammo.common)){
                        list.add(new Lootable(item));
                    }
                    break;
                case RARE:
                    for ( ItemStack item: chooseItems(Table.Ammo.rare)){
                        list.add(new Lootable(item));
                    }
                    break;
                case EPIC:
                    for ( ItemStack item: chooseItems(Table.Ammo.rare)){
                        list.add(new Lootable(item));
                    }
                    break;
                case LEGENDARY:
                    for ( ItemStack item: chooseItems(Table.Ammo.legendary)){
                        list.add(new Lootable(item));
                    }
                    break;
            }
        }

        if (building.win) {
            switch (building.rarity) {
                case COMMON:
                    for ( ItemStack item: chooseItems(Table.Building.common)){
                        list.add(new Lootable(item));
                    }
                    break;
                case RARE:
                    for ( ItemStack item: chooseItems(Table.Building.rare)){
                        list.add(new Lootable(item));
                    }
                    break;
                case EPIC:
                    for ( ItemStack item: chooseItems(Table.Building.rare)){
                        list.add(new Lootable(item));
                    }
                    break;
                case LEGENDARY:
                    for ( ItemStack item: chooseItems(Table.Building.legendary)){
                        list.add(new Lootable(item));
                    }
                    break;
            }
        }
        
        return list.toArray(new Lootable[0]);
    }

    Loot generate() {
        return new Loot(getLootable());
    }
}

class Item {
    public ItemStack item;
    int amount = 1;

    public Item(Material _item) {
        item = new ItemStack(_item);
    }

    public Item(Material _item, int _amount) {
        item = new ItemStack(_item);
        amount = _amount;
    }
}

class Items {
    Item[] items;

    public Items(Material item) {
        items = new Item[1];
        items[0] = new Item(item);
    }

    public Items(Material item, int count) {
        items = new Item[1];
        items[0] = new Item(item, count);
    }

    public Items(Item[] _items) {
        items = _items;
    }

    public Items(Material[] materials) {
        items = new Item[materials.length];
        for (int i = 0; i < materials.length; i++) {
            items[i] = new Item(materials[i]);
        }
    }
}

interface Table {

    static interface Weapon {
        static Items[] common = {
            new Items(Material.WOODEN_SWORD),
            new Items(Material.STONE_SWORD),
            new Items(Material.GOLDEN_SWORD)
        };

        static Items[] rare = {
            new Items(Material.IRON_SWORD),
            new Items(new Item[]{
                new Item(Material.CROSSBOW),
                new Item(Material.ARROW, 8)
            }),
            new Items(Material.BOW)
        };

        static Items[] epic = {
            new Items(Material.DIAMOND_SWORD),
            new Items(new Item[]{
                new Item(Material.BOW),
                new Item(Material.ARROW, 8)
            })
        };

        static Items[] legendary = {
            new Items(Material.NETHERITE_SWORD),
            new Items(new Item[]{
                new Item(Material.BOW),
                new Item(Material.ARROW, 8)
            }),
            new Items(Material.TRIDENT)
        };
    }

    static interface Healing {
        static Items[] common = {
            new Items(Material.BEEF, Utils.random(1, 4)),
            new Items(Utils.choose(new Material[]{
                Material.CARROT,
                Material.POTATO,
                Material.BEETROOT
            }), Utils.random(4, 8))
        };

        static Items[] rare = {
            new Items(Material.BEEF, Utils.random(2, 6)),
            new Items(Utils.choose(new Material[]{
                Material.CARROT,
                Material.POTATO,
                Material.BEETROOT
            }), Utils.random(6, 10))
        };

        static Items[] epic = {
            new Items(Material.COOKED_BEEF, Utils.random(2, 6))
        };

        static Items[] legendary = {
            new Items(Utils.choose(new Material[]{
                Material.GOLDEN_APPLE,
                Material.GOLDEN_CARROT
            }), Utils.random(1, 4))
        };
    }

    static interface Ammo {
        static Items[] common = {
            new Items(Material.SNOWBALL, 8)
        };

        static Items[] rare = {
            new Items(Material.FIRE_CHARGE, Utils.random(1, 2))
        };

        static Items[] epic = {
            new Items(Material.FISHING_ROD)
        };

        static Items[] legendary = {
            new Items(Material.FISHING_ROD),
            new Items(Material.FIRE_CHARGE, Utils.random(2, 4))
        };
    }

    static interface Building {
        static Items[] common = {
            new Items(Material.STONE, 10)
        };

        static Items[] rare = {
            new Items(Material.STONE, 20)
        };

        static Items[] epic = {
            new Items(Material.COAL, 10)
        };

        static Items[] legendary = {
            new Items(Material.ANCIENT_DEBRIS, 5)
        };
    }
}

enum Rarity {
    COMMON,
    RARE,
    EPIC,
    LEGENDARY
}

class LootType {

    boolean win;
    Rarity rarity;

    public LootType(int percentage, boolean endChest) {
        win = Utils.percentage(percentage);
        rarity = determineRarity(endChest);
    }

    Rarity determineRarity(boolean rare) {
        int chance = Utils.random(0, 100);

        if (rare) {
            if (Utils.range(chance, 0, 60)) {
                return Rarity.EPIC;
            }
            else if (Utils.range(chance, 60, 100)) {
                return Rarity.LEGENDARY;
            }
            else {
                return null; // Should never happen.
            }
        }
        else {
            if (Utils.range(chance, 0, 5)) {
                return Rarity.LEGENDARY;
            }
            else if (Utils.range(chance, 5, 20)) {
                return Rarity.EPIC;
            }
            else if (Utils.range(chance, 20, 55)) {
                return Rarity.RARE;
            }
            else if (Utils.range(chance, 55, 100)) {
                return Rarity.COMMON;
            }
            else {
                return null; // Should never happen.
            }
        }
    }
}