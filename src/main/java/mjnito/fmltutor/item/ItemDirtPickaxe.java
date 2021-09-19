package mjnito.fmltutor.item;

import mjnito.fmltutor.FMLTutor;
import mjnito.fmltutor.creativetab.TabFMLTutor;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

public class ItemDirtPickaxe extends ItemPickaxe {

    protected ItemDirtPickaxe() {
        super(ItemRegistryHandler.DIRT_TOOL_MATERIAL);
        this.setUnlocalizedName(FMLTutor.MODID+".dirtPickaxe");
        this.setCreativeTab(TabFMLTutor.Tab_FMLTutor);
        this.setRegistryName("dirt_pickaxe");
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        Block block =state.getBlock();
        float speed=super.getDestroySpeed(stack, state);
        return (block == Blocks.DIRT || block == Blocks.GRASS)? speed * 10 : speed;
    }
}
