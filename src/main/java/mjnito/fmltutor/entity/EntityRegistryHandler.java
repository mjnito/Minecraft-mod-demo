package mjnito.fmltutor.entity;

import net.minecraft.entity.EnumCreatureType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class EntityRegistryHandler {
    public static final EntityEntry DIRT_BALL_KING= EntityEntryBuilder.create().entity(EntityDirtBallKing.class)
            .id(EntityDirtBallKing.ID,0).name(EntityDirtBallKing.NAME).egg(0x998051,0x402722)
            .spawn(EnumCreatureType.MONSTER,5,4,4,EntityDirtBallKing.BIOMES)
            .tracker(80,3,true).build();
    public static final EntityEntry DIRT_BALL=EntityEntryBuilder.create().entity(EntityDirtBall.class).
            id(EntityDirtBall.ID,1).name(EntityDirtBall.NAME).
            tracker(64,10,true).build();
    public static final EntityEntry FAT_TIGER = EntityEntryBuilder.create().entity(EntityFatTiger.class).
            id(EntityFatTiger.ID,2).name(EntityFatTiger.NAME).
            tracker(80,3,true).build();

    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<EntityEntry> event){
        IForgeRegistry registry=event.getRegistry();
        registry.register(DIRT_BALL_KING);
        registry.register(DIRT_BALL);
        registry.register(FAT_TIGER);
    }

}
