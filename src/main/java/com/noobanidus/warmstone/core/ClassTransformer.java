package com.noobanidus.warmstone.core;

import com.noobanidus.warmstone.core.transformers.GroundTransformer;
import net.minecraft.launchwrapper.IClassTransformer;

import java.util.ArrayList;
import java.util.List;

public class ClassTransformer implements IClassTransformer {
    public List<IWarmTransformer> transformers = new ArrayList<>();

    public ClassTransformer () {
        transformers.add(new GroundTransformer());
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        for (IWarmTransformer t : transformers) {
            if (t.accepts(name, transformedName)) return t.transform(name, transformedName, basicClass);
        }
        return basicClass;
    }
}
