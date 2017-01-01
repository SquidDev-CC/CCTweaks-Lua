package org.squiddev.cctweaks.lua.asm;

import org.squiddev.cctweaks.lua.asm.binary.BinaryUtils;
import org.squiddev.patcher.transformer.ClassMerger;
import org.squiddev.patcher.transformer.ClassReplacer;
import org.squiddev.patcher.transformer.IPatcher;
import org.squiddev.patcher.transformer.ISource;

/**
 * Setup everything
 */
public class Tweaks {
	public static void setup(CustomChain chain) {
		chain.addPatchFile("org.luaj.vm2.lib.DebugLib");
		chain.addPatchFile("org.luaj.vm2.lib.StringLib");

		chain.add(new AddAdditionalData());
		chain.add(new AddMethodDescriptor());
		chain.add(new CustomAPIs());
		chain.add(new CustomBios());
		chain.add(new CustomMachine());
		addMulti(chain, new CustomThreading());
		chain.add(new CustomTimeout());
		chain.add(new LimitLabel());
		chain.add(new WhitelistDebug());

		chain.add(new ClassMerger(
			"dan200.computercraft.core.computer.Computer",
			"org.squiddev.cctweaks.lua.patch.Computer_Patch"
		));

		chain.add(new ClassMerger(
			"dan200.computercraft.core.filesystem.FileSystem",
			"org.squiddev.cctweaks.lua.patch.FileSystem_Patch"
		));

		BinaryUtils.inject(chain);
	}

	private static void addMulti(CustomChain chain, ClassReplacer replacer) {
		chain.add((IPatcher) replacer);
		chain.add((ISource) replacer);
	}
}
