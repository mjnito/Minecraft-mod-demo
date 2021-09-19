package mjnito.fmltutor.block;

import mjnito.fmltutor.FMLTutor;
import mjnito.fmltutor.creativetab.TabFMLTutor;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockCompressedDirt extends Block {
    public BlockCompressedDirt()
    {
        super(new Material(MapColor.DIRT));
        this.setUnlocalizedName(FMLTutor.MODID+".compressedDirt");
        this.setCreativeTab(TabFMLTutor.Tab_FMLTutor);
        this.setRegistryName("compressed_dirt");
        this.setHarvestLevel("shovel",0);
        this.setHardness(0.5F);
    }
}
