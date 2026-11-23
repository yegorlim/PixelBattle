package pixelbattle.enemies;

import pixelbattle.core.Enemy;
import pixelbattle.core.Skill;
import pixelbattle.core.Target;
import pixelbattle.engine.Sprite;

/** Скелет — перший смертельний удар залишає його з 1 HP. */
public final class Skeleton extends Enemy {

    private boolean resurrectionUsed = false;

    public Skeleton() {
        super("Скелет", 50, new Sprite("skelet_idle_anim", 4, 6));

        addSkill(new Skill("Кістяний удар", Target.ENEMY, 0, (user, target) ->
                user.getName() + " б'є кісткою " + target.getName() + ": "
                        + target.takeDamage(random(8, 12)) + " шкоди"));
    }

    /**
     * Пасивка «Воскресіння»: перший удар, який мав би вбити скелета,
     * залишає його з 1 HP. Побічний ефект (прапорець) явний тут, у перевизначеному takeDamage.
     */
    @Override
    public int takeDamage(int amount) {
        if (!resurrectionUsed && isAlive() && amount >= getHp()) {
            resurrectionUsed = true;
            return super.takeDamage(getHp() - 1);
        }
        return super.takeDamage(amount);
    }
}
