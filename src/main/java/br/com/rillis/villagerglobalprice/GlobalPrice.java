package br.com.rillis.villagerglobalprice;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.nbtapi.iface.ReadWriteNBTCompoundList;
import de.tr7zw.nbtapi.iface.ReadableNBT;
import de.tr7zw.nbtapi.iface.ReadableNBTList;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicInteger;

public class GlobalPrice {
    public static void editNBT(Entity e, Player p) {

        AtomicInteger max_major_pos = new AtomicInteger();
        AtomicInteger max_minor_pos = new AtomicInteger();

        AtomicInteger uuid_1 = new AtomicInteger();
        AtomicInteger uuid_2 = new AtomicInteger();
        AtomicInteger uuid_3 = new AtomicInteger();
        AtomicInteger uuid_4 = new AtomicInteger();

        NBT.get(e, nbt -> {
            ReadableNBTList gossips = nbt.getCompoundList("Gossips");
            for (int i = 0; i < gossips.size(); i++) {
                ReadableNBT gossip = (ReadableNBT) gossips.get(i);
                String type_str = gossip.getString("Type");
                if (type_str.equals("major_positive"))
                    max_major_pos.set(Math.max(max_major_pos.get(), gossip.getInteger("Value")));
                if (type_str.equals("minor_positive"))
                    max_minor_pos.set(Math.max(max_minor_pos.get(), gossip.getInteger("Value")));
            }
        });

        NBT.get(p, nbt -> {
            uuid_1.set(nbt.getIntArray("UUID")[0]);
            uuid_2.set(nbt.getIntArray("UUID")[1]);
            uuid_3.set(nbt.getIntArray("UUID")[2]);
            uuid_4.set(nbt.getIntArray("UUID")[3]);
        });


        if(max_major_pos.get() > 0 || max_minor_pos.get() > 0) {
            ReadWriteNBT new_nbt = NBT.createNBTObject();
            ReadWriteNBTCompoundList x = new_nbt.getCompoundList("Gossips");
            if(max_major_pos.get() > 0) {
                ReadWriteNBT test = x.addCompound();
                test.setIntArray("Target", new int[]{uuid_1.get(), uuid_2.get(), uuid_3.get(), uuid_4.get()});
                test.setString("Type", "major_positive");
                test.setInteger("Value", max_major_pos.get());
            }
            if (max_minor_pos.get() > 0) {
                ReadWriteNBT test = x.addCompound();
                test.setIntArray("Target", new int[]{uuid_1.get(), uuid_2.get(), uuid_3.get(), uuid_4.get()});
                test.setString("Type", "minor_positive");
                test.setInteger("Value", max_minor_pos.get());

            }

            NBT.modify(e, nbt -> {
                nbt.mergeCompound(new_nbt);
            });
        }

    }
}
