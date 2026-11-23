package pixelbattle.heroes;

import pixelbattle.core.Fighter;
import pixelbattle.core.Hero;
import pixelbattle.core.Skill;
import pixelbattle.core.Target;
import pixelbattle.core.Targets;
import pixelbattle.core.Ultimate;
import pixelbattle.engine.Sprite;

/** Лучниця — швидка й спритна. */
public class Archer extends Hero {

    public Archer() {
        super("Лучниця", 80, new Sprite("elf_f_idle_anim", 4, 4));

        addSkill(new Skill("Постріл", Target.ENEMY, 0, (user, target) ->
                user.getName() + " стріляє в " + target.getName() + ": "
                        + target.takeDamage(random(10, 16)) + " шкоди"));

        // Частина 1: вміння «Подвійний постріл» лямбдою
        addSkill(new Skill("Подвійний постріл", Target.ENEMY, 1, (user, target) -> {
            int dmg = target.takeDamage(random(7, 11));
            dmg += target.takeDamage(random(7, 11));
            return user.getName() + " стріляє двічі в " + target.getName() + ": " + dmg + " шкоди";
        }));

        // Частина 3: ультимейт «Злива стріл» з полем-лічильником
        setUltimate(new Ultimate() {
            private int arrowsPerEnemy = 3;

            @Override public String getName() { return "Злива стріл"; }

            @Override public String getDescription() {
                return "Стріли по 4–8 шкоди кожному живому ворогу; з кожним разом стріл більше";
            }

            @Override public String activate(Fighter user, Fighter[] allies, Fighter[] enemies) {
                int total = 0;
                for (Fighter e : Targets.alive(enemies)) {
                    for (int i = 0; i < arrowsPerEnemy; i++) {
                        total += e.takeDamage(Fighter.random(4, 8));
                    }
                }
                String msg = user.getName() + " запускає Злива стріл ("
                        + arrowsPerEnemy + " стріл на ціль): " + total + " шкоди";
                arrowsPerEnemy++;
                return msg;
            }
        });
    }

    // Частина 2: пасивка «Ухилення» — з шансом 25% шкода дорівнює 0
    @Override
    protected int modifyIncomingDamage(int damage) {
        return chance(25) ? 0 : damage;
    }
}
