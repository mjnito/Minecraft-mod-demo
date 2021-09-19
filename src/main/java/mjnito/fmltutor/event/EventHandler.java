package mjnito.fmltutor.event;

import mjnito.fmltutor.FMLTutor;
import mjnito.fmltutor.capability.CapabilityRegistryHandler;
import mjnito.fmltutor.capability.DirtBallPower;
import mjnito.fmltutor.capability.DirtBallPowerProvider;
import mjnito.fmltutor.enchantment.EnchantmentRegistryHandler;
import mjnito.fmltutor.entity.EntityDirtBallKing;
import mjnito.fmltutor.item.ItemRegistryHandler;
import mjnito.fmltutor.network.NetworkRegistryHandler;
import mjnito.fmltutor.potion.PotionRegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber//代表这个类中有若干的事件监听器
public class EventHandler {
    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinWorldEvent event){
        Entity entity=event.getEntity();
        if(!entity.world.isRemote&&entity instanceof EntityPlayer){
//            String message="Welcome to FMLTutor,"+entity.getName()+"! ";
//            TextComponentString text=new TextComponentString(message);
//            entity.sendMessage(text);
            entity.sendMessage(new TextComponentTranslation("message.fmltutor.welcome",FMLTutor.NAME,entity.getName()));

            NetworkRegistryHandler.Power.sendClientCustomPacket((EntityPlayer)entity);

            DirtBallPower power=entity.getCapability(CapabilityRegistryHandler.DIRT_BALL_POWER,null);
            float orange=power.getOrangePower(),green = power.getGreenPower(),blue = power.getBluePower();

            String message2="Your power:"+orange+"(Orange),"+green+"(Green),"+blue+"(Blue).";
            TextComponentString text2=new TextComponentString(message2);
            entity.sendMessage(text2);
        }
    }

    @SubscribeEvent
    public static void onLivingDead(LivingDeathEvent event){
        Entity source=event.getSource().getImmediateSource();
        if(source instanceof EntityPlayer && !source.world.isRemote){
             EntityPlayer player=(EntityPlayer)source;
            ItemStack heldItemMainhand=player.getHeldItemMainhand();
            int level= EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistryHandler.EXPLOSION,heldItemMainhand);
            if(level>0){
                Entity target=event.getEntity();
                target.world.createExplosion(null,target.posX,target.posY,target.posZ,2*level,false);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurtEvent(LivingHurtEvent event){
        DamageSource damageSource=event.getSource();
        if("fall".equals(damageSource.getDamageType())){
            EntityLivingBase target=event.getEntityLiving();
            Potion potion= PotionRegistryHandler.POTION_DIRT_PROTECTION;
            if(target.isPotionActive(potion)){
                PotionEffect effect=target.getActivePotionEffect(potion);
                BlockPos pos=new BlockPos(target.posX,target.posY-0.2,target.posZ);
                Block block=target.world.getBlockState(pos).getBlock();
                if(block == Blocks.DIRT || block == Blocks.GRASS){
                    event.setAmount(effect.getAmplifier()>0 ? 0 : event.getAmount()/2);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityStuckByLightning(EntityStruckByLightningEvent event){
        Entity entity=event.getEntity();
        if(entity instanceof EntitySlime && !entity.world.isRemote&& !entity.isDead){
            EntityDirtBallKing newEntity=new EntityDirtBallKing(entity.world);
            newEntity.setPosition(entity.posX,entity.posY,entity.posZ);
            DifficultyInstance difficulty=entity.world.getDifficultyForLocation(new BlockPos(entity));
            newEntity.onInitialSpawn(difficulty,null);
            if(entity.hasCustomName()){
                newEntity.setAlwaysRenderNameTag(entity.getAlwaysRenderNameTag());
                newEntity.setCustomNameTag(entity.getCustomNameTag());
            }
            entity.world.spawnEntity(newEntity);
            entity.setDead();
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof EntityPlayer){
            DirtBallPowerProvider provider=new DirtBallPowerProvider();
            event.addCapability(new ResourceLocation(FMLTutor.MODID+":dirt_ball_power"),provider);
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event){
        DirtBallPower instance=event.getEntityPlayer().getCapability(CapabilityRegistryHandler.DIRT_BALL_POWER,null);
        DirtBallPower original=event.getOriginal().getCapability(CapabilityRegistryHandler.DIRT_BALL_POWER,null);

        instance.setOrangePower(original.getOrangePower());
        instance.setGreenPower(original.getGreenPower());
        instance.setBluePower(original.getBluePower());
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event){
        EntityLivingBase entity=event.getEntityLiving();
        if(entity instanceof EntityDirtBallKing){
            float amount=Math.min(entity.getHealth(),event.getAmount());
            Entity source=event.getSource().getTrueSource();
            if(source instanceof EntityPlayer){
                DirtBallPower power =source.getCapability(CapabilityRegistryHandler.DIRT_BALL_POWER,null);
                TextComponentTranslation text=addPower((EntityDirtBallKing)entity,power,amount);

                //同步数据
                NetworkRegistryHandler.Power.sendClientCustomPacket((EntityPlayer)source);

                source.sendMessage(text);
            }
        }
    }



    public static TextComponentTranslation addPower(EntityDirtBallKing entity,DirtBallPower power,float amount){
        byte color=entity.getColor();
        if(color ==2){
            power.setGreenPower(power.getGreenPower()+amount);
            return new TextComponentTranslation("message.fmltutor.power.add.blue",amount);
        }
        if(color == 1){
            power.setBluePower(power.getBluePower()+amount);
            return new TextComponentTranslation("message.fmltutor.power.add.green",amount);
        }
        power.setOrangePower(power.getOrangePower()+amount);
        return new TextComponentTranslation("message.fmltutor.power.add.orange",amount);
    }
}
