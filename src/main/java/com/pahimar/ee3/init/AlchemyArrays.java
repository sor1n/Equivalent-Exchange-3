package com.pahimar.ee3.init;

import com.pahimar.ee3.api.AlchemyArray;
import com.pahimar.ee3.api.AlchemyArrayRegistryProxy;
import com.pahimar.ee3.array.TransmutationAlchemyArray;

public class AlchemyArrays
{
    public static final AlchemyArray transmutationAlchemyArray = new TransmutationAlchemyArray();

    public static void registerAlchemyArrays()
    {
        AlchemyArrayRegistryProxy.registerAlchemyArray(transmutationAlchemyArray);
    }
}
