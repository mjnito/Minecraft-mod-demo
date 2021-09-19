package mjnito.fmltutor;

import mjnito.fmltutor.capability.CapabilityRegistryHandler;
import mjnito.fmltutor.client.renderer.RenderRegistryHandler;
import mjnito.fmltutor.crafting.FurnaceRecipeRegistryHandler;
import mjnito.fmltutor.network.NetworkRegistryHandler;
import mjnito.fmltutor.potion.PotionRegistryHandler;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

//mod的主类
@Mod(modid = FMLTutor.MODID, name = FMLTutor.NAME, version = FMLTutor.VERSION, acceptedMinecraftVersions = "[1.12.2]")//acceptedMinecraftVersions是mod支持的游戏版本
public class FMLTutor
{
    public static final String MODID = "fmltutor";//mod的标识符，是一个mod的身份
    public static final String NAME = "FMLTutor";//一个直接暴露给玩家看的mod名称
    public static final String VERSION = "1.0";//mod的版本号

    private static Logger logger;//mod的日志系统

    //主类中的带@EvenHandler的方法是生命周期事件，1.12的Minecraft不需要通过生命周期事件注册方块、物品
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();//调用了event对象的getModLog方法，并将调用得到的返回值给logger字段，logger字段代表Mod的日志系统。所以需要向控制台输出内容的代码，都会用到这一字段
        CapabilityRegistryHandler.register();
        NetworkRegistryHandler.register();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());//在控制台输出信息
        FurnaceRecipeRegistryHandler.reister();
        PotionRegistryHandler.register();
    }

    @EventHandler
    @SideOnly(Side.CLIENT)
    public void preInitClient(FMLPreInitializationEvent event){
        RenderRegistryHandler.register();
    }

}
