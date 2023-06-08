package committee.nova.flotage.datagen;

import committee.nova.flotage.Flotage;
import committee.nova.flotage.init.BlockMember;
import net.minecraft.data.DataGenerator;

public class LangZHCNProvider extends RLangProvider {
    public LangZHCNProvider(DataGenerator generator) {
        super(generator, "zh_cn");
    }

    @Override
    protected void init() {
        add(Flotage.TAB, "漂浮物");
        add("modmenu.descriptionTranslation.flotage", "生存于大海之上！");
        add("tip.flotage.rack.processtime", "需要处理 %s 秒。");
        add("tip.flotage.rack.mode", "处理条件：");
        add("block.flotage.rack", "置物架");
        add("config.jade.plugin_flotage.rack_blockentity", "置物架模式");
        add("emi.category.flotage.rack", "置物架");

        add("tip.flotage.rack.mode.unconditional", "无条件");
        add("tip.flotage.rack.mode.sun", "白天");
        add("tip.flotage.rack.mode.night", "夜晚");
        add("tip.flotage.rack.mode.rain", "雨天");
        add("tip.flotage.rack.mode.snow", "雪天");
        add("tip.flotage.rack.mode.rain_at", "淋雨");
        add("tip.flotage.rack.mode.snow_at", "淋雪");
        add("tip.flotage.rack.mode.smoke", "烟熏");

        for (BlockMember member : BlockMember.values()) {
            block(member.raft(), member.chinese + "筏");
            block(member.brokenRaft(), "损坏的" + member.chinese + "筏");
            block(member.fence(), "简易" + member.chinese + "栅栏");
            block(member.crossedFence(), member.chinese + "十字栅栏");
            block(member.rack(), member.chinese + "置物架");
        }
    }
}
