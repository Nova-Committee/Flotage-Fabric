package committee.nova.flotage.datagen;

import committee.nova.flotage.util.BlockMember;
import committee.nova.flotage.util.WorkingMode;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class FloLangProvider {

    public static class English extends FabricLanguageProvider {
        public English(FabricDataOutput dataOutput) {
            super(dataOutput);
        }

        @Override
        public void generateTranslations(TranslationBuilder builder) {
            builder.add("itemGroup.flotage.tab", "Flotage");
            builder.add("modmenu.descriptionTranslation.flotage", "Survive on the sea!");
            builder.add("tip.flotage.rack.processtime", "%s second(s) processing");
            builder.add("tip.flotage.rack.mode", "Condition: ");
            builder.add("block.flotage.rack", "Rack Processing");
            builder.add("config.jade.plugin_flotage.rack_blockentity", "Rack Working Mode");
            builder.add("emi.category.flotage.rack", "Rack Processing");

            for (WorkingMode mode : WorkingMode.values()) {
                builder.add("tip.flotage.rack.mode." + mode.toString(), beautifyName(mode.toString()));
            }

            for (BlockMember member : BlockMember.values()) {
                builder.add(member.raft(), beautifyName(member.raft()));
                builder.add(member.brokenRaft(), beautifyName(member.brokenRaft()));
                builder.add(member.fence(), beautifyName(member.fence()));
                builder.add(member.crossedFence(), beautifyName(member.crossedFence()));
                builder.add(member.rack(), beautifyName(member.rack()));
            }
        }
    }

    public static class Chinese extends FabricLanguageProvider {
        public Chinese(FabricDataOutput dataOutput) {
            super(dataOutput, "zh_cn");
        }

        @Override
        public void generateTranslations(TranslationBuilder builder) {
            builder.add("itemGroup.flotage.tab", "漂浮物");
            builder.add("modmenu.descriptionTranslation.flotage", "生存于大海之上！");
            builder.add("tip.flotage.rack.processtime", "需要处理 %s 秒。");
            builder.add("tip.flotage.rack.mode", "处理条件：");
            builder.add("block.flotage.rack", "置物架");
            builder.add("config.jade.plugin_flotage.rack_blockentity", "置物架模式");
            builder.add("emi.category.flotage.rack", "置物架");

            builder.add("tip.flotage.rack.mode.unconditional", "无条件");
            builder.add("tip.flotage.rack.mode.sun", "白天");
            builder.add("tip.flotage.rack.mode.night", "夜晚");
            builder.add("tip.flotage.rack.mode.rain", "雨天");
            builder.add("tip.flotage.rack.mode.snow", "雪天");
            builder.add("tip.flotage.rack.mode.rain_at", "淋雨");
            builder.add("tip.flotage.rack.mode.snow_at", "淋雪");
            builder.add("tip.flotage.rack.mode.smoke", "烟熏");

            for (BlockMember member : BlockMember.values()) {
                builder.add(member.raft(), member.chinese + "筏");
                builder.add(member.brokenRaft(), "损坏的" + member.chinese + "筏");
                builder.add(member.fence(), "简易" + member.chinese + "栅栏");
                builder.add(member.crossedFence(), member.chinese + "十字栅栏");
                builder.add(member.rack(), member.chinese + "置物架");
            }
        }
    }

    public static String beautifyName(String name) {
        String[] str1 = name.split("_");
        StringBuilder str2 = new StringBuilder();
        for(int i = 0 ; i < str1.length; i++) {
            str1[i] = str1[i].substring(0,1).toUpperCase() + str1[i].substring(1);
            if(i == str1.length-1)
                str2.append(str1[i]);
            else
                str2.append(str1[i]).append(" ");
        }
        return str2.toString();
    }
}
