package mjnito.fmltutor.creativetab;

import mjnito.fmltutor.item.ItemRegistryHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabFMLTutor extends CreativeTabs {

    public static  final TabFMLTutor Tab_FMLTutor=new TabFMLTutor();

    public TabFMLTutor() {
        super("fmltutor");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ItemRegistryHandler.ITEM_COMPRESSED_DIRT);
    }

    @Override
    public boolean hasSearchBar(){
        return true;
    }

    @Override
    public int getSearchbarWidth(){
        return 45;
    }

    @Override
    public String getBackgroundImageName() {
        return "fmltutor.png";
    }
}
