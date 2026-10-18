package net.m3tte.ego_weapons.procedures;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;

public class TeamLockedPredicate extends EntityPredicate {


    public static final TeamLockedPredicate ONLY_ALLIES = new TeamLockedPredicate(true, false);
    public static final TeamLockedPredicate ONLY_HOSTILES = new TeamLockedPredicate(false, true);
    public static final TeamLockedPredicate EVERYONE_AND_ANYONE = new TeamLockedPredicate(true, true);

    private boolean acceptAllies = false;
    private boolean acceptHostiles = false;

    private Function<Entity, Boolean> additionalCondition = null;


    public TeamLockedPredicate additionalCondition(Function<Entity, Boolean> condition) {
        return new TeamLockedPredicate(this.acceptAllies, this.acceptHostiles, condition);
    }
    public TeamLockedPredicate(boolean acceptAllies, boolean acceptHostiles) {
        this.acceptAllies = acceptAllies;
        this.acceptHostiles = acceptHostiles;
        this.allowSameTeam();

    }

    public TeamLockedPredicate(boolean acceptAllies, boolean acceptHostiles, Function<Entity, Boolean> additionalCondition) {
        this.acceptAllies = acceptAllies;
        this.acceptHostiles = acceptHostiles;
        this.additionalCondition = additionalCondition;
        this.allowSameTeam();

    }

    @Override
    public boolean test(@Nullable LivingEntity source, LivingEntity target) {
        boolean previousResult = super.test(source, target);

        if (!previousResult)
            return previousResult;


        //System.out.println("TESTING ACROSS "+source+" team "+source.getTeam()+"AND "+target+" SAME TEAM IS : team "+target.getTeam()+ " additional condition is : "+additionalCondition+ " result is : "+(additionalCondition != null ? (additionalCondition.apply(target)) : "NONE GIVEN"));

        if (additionalCondition != null) {
            if (!additionalCondition.apply(target)) {
                return false;
            }
        }

        if (target.getTeam() == null || source.getTeam() == null)
            return acceptHostiles;

        boolean sameTeam = target.getTeam().isAlliedTo(source.getTeam());

        boolean friendlyFireBypass = false;

        if (source.getTeam() != null) {
            friendlyFireBypass = source.getTeam().isAllowFriendlyFire();
        }


        return (sameTeam && acceptAllies) || (!sameTeam && acceptHostiles) || (sameTeam && acceptHostiles && friendlyFireBypass);
    }
}
