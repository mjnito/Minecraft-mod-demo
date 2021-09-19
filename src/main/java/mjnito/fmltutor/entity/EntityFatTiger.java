package mjnito.fmltutor.entity;

import mjnito.fmltutor.FMLTutor;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class EntityFatTiger extends EntityMob {
    public static final String ID = "fat_tiger";
    public static final String NAME = FMLTutor.MODID + ".FatTiger";

    public EntityFatTiger(World worldIn) {
        super(worldIn);
        this.setSize(2.0f,4.0f);
    }
}
