package wdl.griefprevention;

import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import wdl.range.IRangeGroup;
import wdl.range.IRangeGroupType;

public class OwnedClaimRangeGroupType implements IRangeGroupType<OwnedClaimRangeProducer> {
	@Override
	public boolean isValidConfig(ConfigurationSection config,
			List<String> warnings, List<String> errors) {
		return true;
	}

	@Override
	public OwnedClaimRangeProducer createRangeProducer(IRangeGroup group,
			ConfigurationSection config) {
		return new OwnedClaimRangeProducer(group);
	}

	@Override
	public void dispose() {
		
	}

}
