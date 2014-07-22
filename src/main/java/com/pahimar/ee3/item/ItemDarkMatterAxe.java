package com.pahimar.ee3.item;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.pahimar.ee3.reference.*;
import com.pahimar.ee3.util.IChargeable;
import com.pahimar.ee3.util.IKeyBound;
import com.pahimar.ee3.util.NBTHelper;
import com.pahimar.ee3.util.NetworkSoundHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ItemDarkMatterAxe extends ItemToolModalEE implements IKeyBound, IChargeable
{
    private static final Set blocksEffectiveAgainst = Sets.newHashSet(new Block[]{Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin});

    public ItemDarkMatterAxe()
    {
        super(3.0f, Material.Tools.DARK_MATTER, blocksEffectiveAgainst);
        this.setUnlocalizedName(Names.Tools.DARK_MATTER_AXE);
    }

    @Override
    public float func_150893_a(ItemStack itemStack, Block block)
    {
        return block.getMaterial() != net.minecraft.block.material.Material.wood && block.getMaterial() != net.minecraft.block.material.Material.plants && block.getMaterial() != net.minecraft.block.material.Material.vine ? super.func_150893_a(itemStack, block) : this.efficiencyOnProperMaterial;
    }

    @Override
    public Set<String> getToolClasses(ItemStack itemStack)
    {
        return ImmutableSet.of("axe");
    }

    @Override
    public short getMaxChargeLevel()
    {
        return 3;
    }

    @Override
    public short getChargeLevel(ItemStack itemStack)
    {
        return NBTHelper.getShort(itemStack, Names.NBT.CHARGE_LEVEL);
    }

    @Override
    public void setChargeLevel(ItemStack itemStack, short chargeLevel)
    {
        if (chargeLevel <= this.getMaxChargeLevel())
        {
            NBTHelper.setShort(itemStack, Names.NBT.CHARGE_LEVEL, chargeLevel);
        }
    }

    @Override
    public void increaseChargeLevel(ItemStack itemStack)
    {
        if (NBTHelper.getShort(itemStack, Names.NBT.CHARGE_LEVEL) < this.getMaxChargeLevel())
        {
            NBTHelper.setShort(itemStack, Names.NBT.CHARGE_LEVEL, (short) (NBTHelper.getShort(itemStack, Names.NBT.CHARGE_LEVEL) + 1));
        }
    }

    @Override
    public void decreaseChargeLevel(ItemStack itemStack)
    {
        if (NBTHelper.getShort(itemStack, Names.NBT.CHARGE_LEVEL) > 0)
        {
            NBTHelper.setShort(itemStack, Names.NBT.CHARGE_LEVEL, (short) (NBTHelper.getShort(itemStack, Names.NBT.CHARGE_LEVEL) - 1));
        }
    }

    @Override
    public void doKeyBindingAction(EntityPlayer entityPlayer, ItemStack itemStack, Key key)
    {
        if (key == Key.CHARGE)
        {
            if (!entityPlayer.isSneaking())
            {
                if (getChargeLevel(itemStack) == this.getMaxChargeLevel())
                {
                    NetworkSoundHelper.playSoundAt(entityPlayer, Sounds.FAIL, 1.5f, 1.5f);
                }
                else
                {
                    increaseChargeLevel(itemStack);
                    NetworkSoundHelper.playSoundAt(entityPlayer, Sounds.CHARGE_UP, 0.5F, 0.5F + 0.5F * (getChargeLevel(itemStack) * 1.0F / this.getMaxChargeLevel()));
                }
            }
            else
            {
                if (getChargeLevel(itemStack) == 0)
                {
                    NetworkSoundHelper.playSoundAt(entityPlayer, Sounds.FAIL, 1.5f, 1.5f);
                }
                else
                {
                    decreaseChargeLevel(itemStack);
                    NetworkSoundHelper.playSoundAt(entityPlayer, Sounds.CHARGE_DOWN, 0.5F, 1.0F - (0.5F - 0.5F * (getChargeLevel(itemStack) * 1.0F / this.getMaxChargeLevel())));
                }
            }
        }
        else if (key == Key.EXTRA)
        {
            NetworkSoundHelper.playSoundAt(entityPlayer, Sounds.TOCK, 0.5f, 1.5F);
            changeToolMode(itemStack);
        }
    }

    @Override
    public List<ToolMode> getAvailableToolModes()
    {
        // TODO
        return Arrays.asList(ToolMode.STANDARD);
    }
}