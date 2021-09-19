package mjnito.fmltutor.entity;

import mjnito.fmltutor.FMLTutor;
import mjnito.fmltutor.block.BlockRegistryHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTableList;

import javax.annotation.Nullable;
import java.math.BigInteger;
import java.util.Random;
import java.util.UUID;

public class EntityDirtBallKing extends EntityMob {

    public static final String ID="dirt_ball_king";
    public static final String NAME=FMLTutor.MODID+".DirtBallKing";
    private static final UUID SPEED_BOOST=UUID.fromString("c84964ef-9f59-4983-87af-9cac1e63f446");
    private static final DataParameter<Byte> COLOR= EntityDataManager.createKey(EntityDirtBallKing.class, DataSerializers.BYTE);
    public static final Biome[] BIOMES=new Biome[] {Biomes.PLAINS,Biomes.SAVANNA,Biomes.SAVANNA_PLATEAU,Biomes.MUTATED_PLAINS};
    private static final ResourceLocation LOOT_TABLE= LootTableList.register(new ResourceLocation(FMLTutor.MODID+":entities/dirt_ball_king"));

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return LOOT_TABLE;
    }

    public EntityDirtBallKing(World worldIn){
        super(worldIn);
        this.setSize(1.2F,1.95F);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setByte("Color",this.getDataManager().get(COLOR));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.getDataManager().set(COLOR,compound.getByte("Color"));
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this,1.0,false));
        this.tasks.addTask(2, new EntityAIWanderAvoidWater(this,0.8));
        this.tasks.addTask(3, new AIChangeGrassToDirt(this));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class,8.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        this.targetTasks.addTask(0,new EntityAIHurtByTarget(this,false));
    }
    @Override
    public void setAttackTarget(EntityLivingBase entity) {
        super.setAttackTarget(entity);
        IAttributeInstance attribute=this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        if(entity==null){
            attribute.removeModifier(SPEED_BOOST);
        }else if(attribute.getModifier(SPEED_BOOST)!=null){
            attribute.applyModifier(new AttributeModifier(SPEED_BOOST,"Speed boost",0.5,2).setSaved(false));
        }
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData data) {
        IAttributeInstance attribute=this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
        attribute.applyModifier(new AttributeModifier("Health boost",this.rand.nextInt(6),0));
        this.getDataManager().set(COLOR,(byte) this.rand.nextInt(3));
        return super.onInitialSpawn(difficulty, data);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3);
    }

    private static class AIChangeGrassToDirt extends EntityAIBase{
        private final EntityDirtBallKing entity;

        private AIChangeGrassToDirt(EntityDirtBallKing entity){
            this.entity = entity;
        }

        @Override
        public void updateTask() {
            BlockPos blockPos= new BlockPos(this.entity.posX,this.entity.posY-0.2,this.entity.posZ);
            this.entity.world.setBlockState(blockPos, BlockRegistryHandler.BLOCK_COMPRESSED_DIRT.getDefaultState());
        }

        @Override
        public boolean shouldExecute() {
            BlockPos blockPos= new BlockPos(this.entity.posX,this.entity.posY-0.2,this.entity.posZ);
            return this.entity.world.getBlockState(blockPos).getBlock()==Blocks.GRASS;
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(COLOR,(byte)0);
    }

    public byte getColor(){
        return this.getDataManager().get(COLOR);
    }
}
