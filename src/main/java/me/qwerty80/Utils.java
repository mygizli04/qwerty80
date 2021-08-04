package me.qwerty80;

import java.util.ArrayList;

import org.bukkit.Bukkit;

public interface Utils {
    static int random(int min, int max) {
        return (int) Math.round(Math.random() * (max - min) + min);
    }

    static boolean percentage(int percent) {
        if (Math.random() < (percent / 100)) {
            return true;
        }
        else {
            return false;
        }
    }

    static boolean range(int num, int min, int max) {
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
}
