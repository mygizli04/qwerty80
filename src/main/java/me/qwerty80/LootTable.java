package me.qwerty80;

import java.util.ArrayList;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class LootTable {

    boolean rare;
    LootType weapons;
    LootType healing;
    LootType ammo;
    LootType building;
    LootType armor;

    public void forEachCategory(Consumer<LootType> lambda) {
        lambda.accept(weapons);
        lambda.accept(healing);
        lambda.accept(ammo);
        lambda.accept(building);
        lambda.accept(armor);
    }

    public LootTable() {
        rare = Utils.percentage(10);
        armor = new LootType(40, rare);
        weapons = new LootType(100, rare);
        building = new LootType(100, rare);
        ammo = new LootType(50 , rare);
        healing = new LootType(60, rare);
    }

    public LootTable(boolean _rare) {
        rare = _rare;
        armor = new LootType(40, rare);
        weapons = new LootType(100, rare);
        building = new LootType(100, rare);
        ammo = new LootType(50 , rare);
        healing = new LootType(60, rare);
    }

    ItemStack[] chooseItems(Items[] items) {
        Items chosen = Utils.choose(items);
        ItemStack[] ret = new ItemStack[chosen.items.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = chosen.items[i].item;
        }
        return ret;
    }

    ItemStack[] getItemStack() {
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        if (weapons.selected) { // select
            switch (weapons.rarity) {
                case COMMON:
                    for ( ItemStack item: chooseItems(Table.Weapon.common)){
                        list.add(item);
                    }
                    break;
                case RARE:
                    for ( ItemStack item: chooseItems(Table.Weapon.rare)){
                        list.add(item);
                    }
                    break;
                case EPIC:
                    for ( ItemStack item: chooseItems(Table.Weapon.epic)){
                        list.add(item);
                    }
                    break;
                case LEGENDARY:
                    for ( ItemStack item: chooseItems(Table.Weapon.legendary)){
                        list.add(item);
                    }
                    break;
            }
        }

        if (healing.selected) {
            switch (healing.rarity) {
                case COMMON:
                    for ( ItemStack item: chooseItems(Table.Healing.common)){
                        list.add(item);
                    }
                    break;
                case RARE:
                    for ( ItemStack item: chooseItems(Table.Healing.rare)){
                        list.add(item);
                    }
                    break;
                case EPIC:
                    for ( ItemStack item: chooseItems(Table.Healing.epic)){
                        list.add(item);
                    }
                    break;
                case LEGENDARY:
                    for ( ItemStack item: chooseItems(Table.Healing.legendary)){
                        list.add(item);
                    }
                    break;
            }
        }

        if (ammo.selected) {
            switch (ammo.rarity) {
                case COMMON:
                    for ( ItemStack item: chooseItems(Table.Ammo.common)){
                        list.add(item);
                    }
                    break;
                case RARE:
                    for ( ItemStack item: chooseItems(Table.Ammo.rare)){
                        list.add(item);
                    }
                    break;
                case EPIC:
                    for ( ItemStack item: chooseItems(Table.Ammo.epic)){
                        list.add(item);
                    }
                    break;
                case LEGENDARY:
                    for ( ItemStack item: chooseItems(Table.Ammo.legendary)){
                        list.add(item);
                    }
                    break;
            }
        }

        if (building.selected) {
            switch (building.rarity) {
                case COMMON:
                    for ( ItemStack item: chooseItems(Table.Building.common)){
                        list.add(item);
                    }
                    break;
                case RARE:
                    for ( ItemStack item: chooseItems(Table.Building.rare)){
                        list.add(item);
                    }
                    break;
                case EPIC:
                    for ( ItemStack item: chooseItems(Table.Building.epic)){
                        list.add(item);
                    }
                    break;
                case LEGENDARY:
                    for ( ItemStack item: chooseItems(Table.Building.legendary)){
                        list.add(item);
                    }
                    break;
            }
        }

        if (armor.selected) {
            int rng = Utils.random(1, 4);
            ArmorItem[] items;
            switch (rng) {
                case 1:
                    items = Table.Armor.one;
                    break; // brak
                case 2:
                    items = Table.Armor.two;
                    break;
                case 3:
                    items = Table.Armor.three;
                    break;
                case 4:
                    items = Table.Armor.four;
                    break;
                default:
                    items = null; // Once again, this should (hopefully) never happen. mhm.healing. 
                    break;
            }

            for (ArmorItem item : items) {
                Rarity rarity = LootType.determineRarity(rare);
                if (Utils.percentage(item.chance)) { // If the item is selected
                    for (ArmorType type : item.items) {
                        list.add(new ItemStack(ArmorMaterial.combine(rarity, type)));
                    }
                    break;
                }
            }
        }
        
        return list.toArray(new ItemStack[0]);
    }
}

enum LCG {
    LEATHER,
    CHAINMAIL,
    GOLD
}

interface ArmorMaterial {
    static Material combine(Rarity rarity, ArmorType type) {
        switch (rarity) {
            case COMMON: // LCG
                LCG lcg = Utils.choose(new LCG[]{
                    LCG.LEATHER,
                    LCG.CHAINMAIL,
                    LCG.GOLD
                });

                switch (type) {
                    case HELMET:
                        switch (lcg) {
                            case LEATHER:
                                return Material.LEATHER_HELMET;
                            case CHAINMAIL:
                                return Material.CHAINMAIL_HELMET;
                            case GOLD:
                                return Material.GOLDEN_HELMET;
                            default:
                                return null;
                        }
                    case CHESTPLATE:
                        switch (lcg) {
                            case LEATHER:
                                return Material.LEATHER_CHESTPLATE;
                            case CHAINMAIL:
                                return Material.CHAINMAIL_CHESTPLATE;
                            case GOLD:
                                return Material.GOLDEN_CHESTPLATE;
                            default:
                                return null;
                        }
                    case LEGGINGS:
                        switch (lcg) {
                            case LEATHER:
                                return Material.LEATHER_LEGGINGS;
                            case CHAINMAIL:
                                return Material.CHAINMAIL_LEGGINGS;
                            case GOLD:
                                return Material.GOLDEN_LEGGINGS;
                            default:
                                return null;
                        }
                    case BOOTS:
                        switch (lcg) {
                            case LEATHER:
                                return Material.LEATHER_BOOTS;
                            case CHAINMAIL:
                                return Material.CHAINMAIL_BOOTS;
                            case GOLD:
                                return Material.GOLDEN_BOOTS;
                            default:
                                return null;
                        }
                    default:
                        return null;
                }
            case RARE: // Iron
                switch (type) {
                    case HELMET:
                        return Material.IRON_HELMET;
                    case CHESTPLATE:
                        return Material.IRON_CHESTPLATE;
                    case LEGGINGS:
                        return Material.IRON_LEGGINGS;
                    case BOOTS:
                        return Material.IRON_BOOTS;
                    default:
                        return null;
                }
            case EPIC: // Diamond
                switch (type) {
                    case HELMET:
                        return Material.DIAMOND_HELMET;
                    case CHESTPLATE:
                        return Material.DIAMOND_CHESTPLATE;
                    case LEGGINGS:
                        return Material.DIAMOND_LEGGINGS;
                    case BOOTS:
                        return Material.DIAMOND_BOOTS;
                    default:
                        return null;
                }
            case LEGENDARY: // Netherite
                switch (type) {
                    case HELMET:
                        return Material.NETHERITE_HELMET;
                    case CHESTPLATE:
                        return Material.NETHERITE_CHESTPLATE;
                    case LEGGINGS:
                        return Material.NETHERITE_LEGGINGS;
                    case BOOTS:
                        return Material.NETHERITE_BOOTS;
                    default:
                        return null;
                }
            default:
                return null;
        }
    }
}

class Item {
    public ItemStack item;

    public Item(Material _item) {
        item = new ItemStack(_item);
    }

    public Item(Material _item, int _amount) {
        item = new ItemStack(_item, _amount);
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
            new Items(Material.FISHING_ROD),
            new Items(Material.FIRE_CHARGE, Utils.random(3, 4))
        };

        static Items[] legendary = {
            new Items(Material.FISHING_ROD),
        };
    }

    static interface Building {
        static Items[] common = {
            new Items(Material.STONE, 15)
        };

        static Items[] rare = {
            new Items(Material.COAL_BLOCK, 5),
            new Items(Material.STONE, 20)
        };

        static Items[] epic = {
            new Items(Material.COAL_BLOCK, 10)
        };

        static Items[] legendary = {
            new Items(Material.ANCIENT_DEBRIS, 5)
        };
    }

    static interface Armor {
        static ArmorItem[] one = {
            new ArmorItem(ArmorType.HELMET, 10),
            new ArmorItem(ArmorType.CHESTPLATE, 40),
            new ArmorItem(ArmorType.LEGGINGS, 40),
            new ArmorItem(ArmorType.BOOTS, 10)
        };

        static ArmorItem[] two = {
            new ArmorItem(new ArmorType[]{ // Helmet + boots
                ArmorType.HELMET,
                ArmorType.BOOTS
            }, 45),
            
            new ArmorItem(new ArmorType[]{
                ArmorType.HELMET,
                ArmorType.CHESTPLATE
            }, 10),
            
            new ArmorItem(new ArmorType[]{
                ArmorType.HELMET,
                ArmorType.LEGGINGS
            }, 15),
            
            new ArmorItem(new ArmorType[]{
                ArmorType.CHESTPLATE,
                ArmorType.BOOTS
            }, 10),
            
            new ArmorItem(new ArmorType[]{
                ArmorType.CHESTPLATE,
                ArmorType.LEGGINGS
            }, 5),

            new ArmorItem(new ArmorType[]{
                ArmorType.LEGGINGS,
                ArmorType.BOOTS
            }, 15),
        };

        static ArmorItem[] three = {
            new ArmorItem(new ArmorType[]{
                ArmorType.HELMET,
                ArmorType.CHESTPLATE,
                ArmorType.LEGGINGS
            }, 15),
            new ArmorItem(new ArmorType[]{
                ArmorType.HELMET,
                ArmorType.CHESTPLATE,
                ArmorType.BOOTS
            }, 35),
            new ArmorItem(new ArmorType[]{
                ArmorType.HELMET,
                ArmorType.LEGGINGS,
                ArmorType.BOOTS
            }, 35),
            new ArmorItem(new ArmorType[]{
                ArmorType.CHESTPLATE,
                ArmorType.LEGGINGS,
                ArmorType.BOOTS
            }, 15),
        };

        static ArmorItem[] four = {
            new ArmorItem(new ArmorType[]{
                ArmorType.HELMET,
                ArmorType.CHESTPLATE,
                ArmorType.LEGGINGS, // i did something :D
                ArmorType.BOOTS
            }, 100)
        };
    }
}

class ArmorItem {
    ArmorType[] items;
    int chance;

    public ArmorItem(ArmorType[] _items, int _chance) { // with array (list) ie multiple piece
        items = _items;
        chance = _chance;
    }

    public ArmorItem(ArmorType _item, int _chance) { // without array ie 1 SINGLE PIECE ;-;
        items = new ArmorType[1];
        items[0] = _item;
        chance = _chance;
    }
}

enum ArmorType {
    HELMET, // lever was here :D
    CHESTPLATE, // yeah, he was!
    LEGGINGS,
    BOOTS
}

enum Rarity {
    COMMON,
    RARE,
    EPIC,
    LEGENDARY
}

class LootType {

    boolean selected;
    Rarity rarity;

    public LootType(int percentage, boolean endChest) {
        selected = Utils.percentage(percentage);
        rarity = determineRarity(endChest);
    }

    static Rarity determineRarity(boolean rare) {
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
            if (Utils.range(chance, 0, 5)) { // 5%
                return Rarity.LEGENDARY;
            }
            else if (Utils.range(chance, 5, 20)) { // 15%
                return Rarity.EPIC;
            }
            else if (Utils.range(chance, 20, 55)) { // 35%
                return Rarity.RARE;
            }
            else if (Utils.range(chance, 55, 100)) { // 45%
                return Rarity.COMMON;
            }
            else {
                return null; // Should never happen.
            }
        }
    }
}