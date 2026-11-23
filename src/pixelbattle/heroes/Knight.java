package pixelbattle.heroes;

import pixelbattle.core.Fighter;
import pixelbattle.core.Hero;
import pixelbattle.core.Skill;
import pixelbattle.core.Target;
import pixelbattle.core.Targets;
import pixelbattle.core.Ultimate;
import pixelbattle.engine.Sprite;

import java.util.List;

/** Лицар — міцний воїн ближнього бою. */
public class Knight extends Hero {

    public Knight() {
        super("Лицар", 110, new Sprite("knight_m_idle_anim", 4, 4));

        // Звичайна атака
        addSkill(new Skill("Удар мечем", Target.ENEMY, 0, (user, target) -> {
            int dmg = target.takeDamage(random(12, 18));
            return user.getName() + " рубає " + target.getName() + ": " + dmg + " шкоди";
        }));

        // Частина 1: вміння «Щит»
        addSkill(new Skill("Щит", Target.SELF, 2, (user, target) -> {
            user.setGuarding(true);
            return user.getName() + " стає в захист";
        }));

        // Частина 3: ультимейт «Небесний молот» (анонімний клас)
        setUltimate(new Ultimate() {
            @Override public String getName() { return "Небесний молот"; }

            @Override public String getDescription() {
                return "Кожному живому ворогу 15 шкоди і оглушення на 1 хід";
            }

            @Override public String activate(Fighter user, Fighter[] allies, Fighter[] enemies) {
                List<Fighter> targets = Targets.alive(enemies);
                int total = 0;
                for (Fighter e : targets) {
                    total += e.takeDamage(15);
                    e.stun(1);
                }
                return user.getName() + " викликає Небесний молот: " + total
                        + " шкоди (" + targets.size() + " цілей)";
            }
        });
    }

    // Частина 2: пасивка «Важка броня» — шкода менша на 4, але не менше 0
    @Override
    protected int modifyIncomingDamage(int damage) {
        return Math.max(0, damage - 4);
    }
}
