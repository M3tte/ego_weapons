package net.m3tte.ego_weapons.procedures;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class TeamLockedPredicate extends EntityPredicate {


    public static final TeamLockedPredicate ONLY_ALLIES = new TeamLockedPredicate(true, false);
    public static final TeamLockedPredicate ONLY_HOSTILES = new TeamLockedPredicate(false, true);
    public static final TeamLockedPredicate EVERYONE_AND_ANYONE = new TeamLockedPredicate(true, true);

    boolean acceptAllies = false;
    boolean acceptHostiles = false;


    public TeamLockedPredicate(boolean acceptAllies, boolean acceptHostiles) {
        this.acceptAllies = acceptAllies;
        this.acceptHostiles = acceptHostiles;
        this.allowSameTeam();

    }

    @Override
    public boolean test(@Nullable LivingEntity source, LivingEntity target) {
        boolean previousResult = super.test(source, target);

        if (!previousResult)
            return previousResult;


        System.out.println("TESTING ACROSS "+source+" team "+source.getTeam()+"AND "+target+" SAME TEAM IS : team "+target.getTeam());

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
