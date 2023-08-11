package committee.nova.flotage.util;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public enum WorkingMode {
    UNCONDITIONAL("unconditional"),
    SUN("sun"),
    NIGHT("night"),
    RAIN("rain"),
    SNOW("snow"),
    RAIN_AT("rain_at"),
    SNOW_AT("snow_at"),
    SMOKE("smoke");
    private final String type;
    WorkingMode(String type) {
        this.type = type;
    }

    public static WorkingMode judge(World world, BlockPos pos) {
        Biome biome = world.getBiome(pos).value();
        if (hasSnow(world, pos)) {
            return SNOW_AT;
        } else if (world.hasRain(pos.up())) {
            return RAIN_AT;
        } else if (world.getBlockState(pos.down()).isOf(Blocks.CAMPFIRE)) {
            return SMOKE;
        } else if (world.isRaining()) {
            if (biome.getPrecipitation(pos) == Biome.Precipitation.SNOW && !biome.doesNotSnow(pos))
                return SNOW;
            else
                return RAIN;
        }
        if (world.isDay()) {
            return SUN;
        }else if (world.isNight()) {
            return NIGHT;
        }
        return UNCONDITIONAL;
    }

    public static boolean hasSnow(World world, BlockPos pos) {
        if (!world.isRaining()) {
            return false;
        }
        if (!world.isSkyVisible(pos)) {
            return false;
        }
        if (world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos).getY() > pos.getY()) {
            return false;
        }
        Biome biome = world.getBiome(pos).value();
        return biome.getPrecipitation(pos) == Biome.Precipitation.SNOW && !biome.doesNotSnow(pos);
    }

    @Override
    public String toString() {
        return type;
    }

    public static WorkingMode match(String s) {
        for (WorkingMode mode : values()) {
            if (mode.toString().equals(s))
                return mode;
        }
        return WorkingMode.UNCONDITIONAL;
    }
}
