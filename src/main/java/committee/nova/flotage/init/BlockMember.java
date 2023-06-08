package committee.nova.flotage.init;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public enum BlockMember {
    OAK(Blocks.OAK_LOG, "橡木"),
    SPRUCE(Blocks.SPRUCE_LOG, "云杉木"),
    BIRCH(Blocks.BIRCH_LOG, "白桦木"),
    JUNGLE(Blocks.JUNGLE_LOG, "丛林木"),
    ACACIA(Blocks.ACACIA_LOG, "金合欢木"),
    DARK_OAK(Blocks.DARK_OAK_LOG, "深色橡木"),
    CRIMSON(Blocks.CRIMSON_STEM, "绯红木"),
    WARPED(Blocks.WARPED_STEM, "诡异木");

    public final Block repairBlock;
    public final String chinese;
    BlockMember(Block repairBlock, String chinese) {
        this.repairBlock = repairBlock;
        this.chinese = chinese;
    }

    public String rack() {
        return name().toLowerCase() + "_rack";
    }

    public String fence() {
        return name().toLowerCase() + "_fence";
    }

    public String crossedFence() {
        return name().toLowerCase() + "_crossed_fence";
    }

    public Identifier log() {
        return Registry.BLOCK.getId(repairBlock);
    }

    public String raft() {
        return name().toLowerCase() + "_raft";
    }

    public String brokenRaft() {
        return "broken_" + name().toLowerCase() + "_raft";
    }
}
