package pixelbattle.enemies;

import pixelbattle.core.Enemy;
import pixelbattle.core.Fighter;
import pixelbattle.core.Skill;
import pixelbattle.core.Target;
import pixelbattle.core.Targets;
import pixelbattle.core.Ultimate;
import pixelbattle.engine.Sprite;

/** Огр — бос. Повільний, але дуже сильний. */
public final class Ogre extends Enemy {

    public Ogre() {
        super("Огр", 200, new Sprite("ogre_idle_anim", 4, 4));

        addSkill(new Skill("Удар кийком", Target.ENEMY, 0, (user, target) -> {
            int dmg = random(14, 20);
            if (user.getHp() < user.getMaxHp() / 2) dmg += 8;    // лють
            return user.getName() + " б'є кийком " + target.getName() + ": "
                    + target.takeDamage(dmg) + " шкоди";
        }));

        // Частина 3: ультимейт «Землетрус» (анонімний клас)
        setUltimate(new Ultimate() {
            @Override public String getName() { return "Землетрус"; }

            @Override public String getDescription() {
                return "Кожному живому герою 12 шкоди, огр лікується на 20";
            }

            @Override public String activate(Fighter user, Fighter[] allies, Fighter[] enemies) {
                int total = 0;
                for (Fighter hero : Targets.alive(enemies)) {
                    total += hero.takeDamage(12);
                }
                int healed = user.heal(20);
                return user.getName() + " викликає Землетрус: " + total
                        + " шкоди, лікується на " + healed;
            }
        });
    }

    // Пасивка «Товста шкіра»: за один удар огр втрачає не більше 25 HP
    @Override
    protected int modifyIncomingDamage(int damage) {
        return Math.min(damage, 25);
    }
}
