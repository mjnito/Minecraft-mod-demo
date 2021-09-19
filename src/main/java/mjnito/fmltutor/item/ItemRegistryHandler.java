package mjnito.fmltutor.item;

import mjnito.fmltutor.FMLTutor;
import mjnito.fmltutor.block.BlockRegistryHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class ItemRegistryHandler
{
    public static final ItemDirtBall DIRT_BALL= new ItemDirtBall();
    public static final ItemBlock ITEM_COMPRESSED_DIRT=withRegistryName(new ItemBlock(BlockRegistryHandler.BLOCK_COMPRESSED_DIRT));
    public static final Item.ToolMaterial DIRT_TOOL_MATERIAL= EnumHelper.addToolMaterial("DIRT",1,44,3.0F,1.0F,5);
    public static final ItemDirtPickaxe DIRT_PICKAXE=new ItemDirtPickaxe();
    public static final ItemArmor.ArmorMaterial Dirt_ARMOR_MATERIAL =
            EnumHelper.addArmorMaterial("DIRT", FMLTutor.MODID+":dirt",5,new int[]{1,2,2,1},9, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,0);
    public static final ItemDirtArmor DIRT_BOOTS = new ItemDirtArmor(EntityEquipmentSlot.FEET);
    public static final ItemDirtArmor DIRT_LEGGINGS = new ItemDirtArmor(EntityEquipmentSlot.LEGS);
    public static final ItemDirtArmor DIRT_CHESTPLATE = new ItemDirtArmor(EntityEquipmentSlot.CHEST);
    public static final ItemDirtArmor DIRT_HELMET = new ItemDirtArmor(EntityEquipmentSlot.HEAD);
    public static final ItemBlock ITEM_DIRT_COMPRESSOR=withRegistryName(new ItemBlock(BlockRegistryHandler.BLOCK_DIRT_COMPRESSOR));

    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<Item> event)
    {
        IForgeRegistry<Item> registry=event.getRegistry();
        registry.register(DIRT_BALL);
        registry.register(ITEM_COMPRESSED_DIRT);
        registry.register(DIRT_PICKAXE);
        registry.register(DIRT_HELMET);
        registry.register(DIRT_CHESTPLATE);
        registry.register(DIRT_LEGGINGS);
        registry.register(DIRT_BOOTS);
        registry.register(ITEM_DIRT_COMPRESSOR);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegistry(ModelRegistryEvent event)
    {
//        ModelLoader.setCustomModelResourceLocation(DIRT_BALL,0,new ModelResourceLocation(DIRT_BALL.getRegistryName(),"inventory"));
//        ModelLoader.setCustomModelResourceLocation(ITEM_COMPRESSED_DIRT, 0, new ModelResourceLocation(ITEM_COMPRESSED_DIRT.getRegistryName(),"inventory"));
        registryModel(DIRT_BALL);
        registryModel(ITEM_COMPRESSED_DIRT);
        registryModel(DIRT_PICKAXE);
        registryModel(DIRT_BOOTS);
        registryModel(DIRT_CHESTPLATE);
        registryModel(DIRT_HELMET);
        registryModel(DIRT_LEGGINGS);
        registryModel(ITEM_DIRT_COMPRESSOR);
    }
    private static ItemBlock withRegistryName(ItemBlock item){
        item.setRegistryName(item.getBlock().getRegistryName());
        return item;
    }

    @SideOnly(Side.CLIENT)
    private static void registryModel(Item item){
        ModelResourceLocation modelResourceLocation=new ModelResourceLocation(item.getRegistryName(),"inventory");
        ModelLoader.setCustomModelResourceLocation(item,0,modelResourceLocation);
    }
}
