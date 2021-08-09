package me.qwerty80;

import java.util.ArrayList;

import org.bukkit.Bukkit;

public interface Utils {
    static int random(double min, double max) {
        return (int) Math.round(Math.random() * (max - min) + min);
    }

    static boolean percentage(double percent) {
        return Math.random() < (percent / 100);
    }

    static boolean range(double num, double min, double max) {
        return (min <= num) && (num <= max);
    }

    static <T> T choose(T[] array) {
        return array[random(0, array.length - 1)];
    }

    static <From, To> To[] convertArray(From[] from, Class<To> to) {
        ArrayList<To> ret = new ArrayList<To>();
        for (int i = 0; i < from.length; i++) {
            try {
                ret.add(to.getDeclaredConstructor().newInstance(from[i]));
            }
            catch (Exception e) {
                Bukkit.getLogger().warning("ERROR: " + e);
            }
        }
        @SuppressWarnings("unchecked")
        To[] finalArray = (To[]) ret.toArray();
        return finalArray;
    }

    static <T> boolean arrayIncludes(T[] array, T includes) {
        for (T item : array) {
            if (item == includes) {
                return true;
            }
        }
        return false;
    }

    static <T> T choosePercentage(T[] array, int[] percentages) { // choosepercentage([1,2,7,3]) then it'll return int. || choosepercentage["6","9","6","9"] then it'll return string.
        int chance = random(0, 100);
        int currentChance = 0;

        for (int uwu = 0; uwu < array.length; uwu++) { // if uwu >= 1: && ++ == 
            if (range(chance, currentChance, percentages[uwu])) { // (uwu - 1)st/nd/rd/th argument of percentages hi :D
                return array[uwu];
            }
        }

        return null; // Should never happen (pls). :L
    }
}
