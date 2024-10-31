package net.m3tte.ego_weapons.data;

import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleTeleportationData extends DataStat {

    BlockPos coordinates;

    public BlockPos getCoordinates() {
        return coordinates;
    }

    public SimpleTeleportationData(BlockPos coordinates) {
        this.coordinates = coordinates;
    }

    public SimpleTeleportationData(String data) {
        List<Float> coordinationData = Arrays.stream(data.split("\\|")[1].split(",")).map((val -> Float.parseFloat(val))).collect(Collectors.toList());

        this.coordinates = new BlockPos(coordinationData.get(0), coordinationData.get(1), coordinationData.get(2));
    }

    @Override
    public String toString() {
        return "SIMPLETP|"+coordinates.getX()+","+coordinates.getY()+","+coordinates.getZ();
    }
}
