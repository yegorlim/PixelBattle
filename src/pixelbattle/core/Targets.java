package pixelbattle.core;

import java.util.ArrayList;
import java.util.List;

/** Допоміжні методи для пошуку цілей. */
public final class Targets {

    private Targets() { }

    /** Список живих бійців із команди (порядок збережено). */
    public static List<Fighter> alive(Fighter[] team) {
        List<Fighter> result = new ArrayList<>();
        for (Fighter f : team) {
            if (f.isAlive()) result.add(f);
        }
        return result;
    }
}
