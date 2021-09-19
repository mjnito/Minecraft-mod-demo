package mjnito.fmltutor.item;

import mjnito.fmltutor.FMLTutor;
import mjnito.fmltutor.creativetab.TabFMLTutor;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public class ItemDirtArmor extends ItemArmor {
    public ItemDirtArmor(EntityEquipmentSlot equipmentSlot){
        super(ItemRegistryHandler.Dirt_ARMOR_MATERIAL,0,equipmentSlot);
        this.setUnlocalizedName(FMLTutor.MODID+".dirtArmor."+equipmentSlot.getName());
        this.setRegistryName("dirt_armor_"+ equipmentSlot.getName());
        this.setCreativeTab(TabFMLTutor.Tab_FMLTutor);
    }
}
