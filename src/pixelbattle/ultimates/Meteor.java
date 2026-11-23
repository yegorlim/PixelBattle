package pixelbattle.ultimates;

import pixelbattle.core.Fighter;
import pixelbattle.core.Targets;
import pixelbattle.core.Ultimate;

import java.util.List;

/** Ультимейт «Метеорит»: удар по всіх живих ворогах. */
public class Meteor implements Ultimate {

    @Override
    public String getName() { return "Метеорит"; }

    @Override
    public String getDescription() { return "Кожному живому ворогу 20–30 шкоди"; }

    @Override
    public String activate(Fighter user, Fighter[] allies, Fighter[] enemies) {
        List<Fighter> targets = Targets.alive(enemies);
        int total = 0;
        for (Fighter e : targets) {
            total += e.takeDamage(Fighter.random(20, 30));
        }
        return user.getName() + " викликає Метеорит: " + total
                + " шкоди (" + targets.size() + " цілей)";
    }
}
