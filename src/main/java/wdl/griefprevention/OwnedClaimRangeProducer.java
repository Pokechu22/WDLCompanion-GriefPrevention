package wdl.griefprevention;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.PlayerData;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import wdl.range.IRangeGroup;
import wdl.range.IRangeProducer;
import wdl.range.ProtectionRange;

public class OwnedClaimRangeProducer implements IRangeProducer {
	private final IRangeGroup rangeGroup;
	
	public OwnedClaimRangeProducer(IRangeGroup group) {
		this.rangeGroup = group;
	}

	@Override
	public List<ProtectionRange> getInitialRanges(Player player) {
		Collection<Claim> claims = getPlayerClaims(player); 
		
		List<ProtectionRange> returned = new ArrayList<>();
		for (Claim claim : claims) {
			Location min = claim.getLesserBoundaryCorner();
			Location max = claim.getGreaterBoundaryCorner();
			
			int minX = min.getChunk().getX();
			int maxX = max.getChunk().getX();
			int minZ = min.getChunk().getZ();
			int maxZ = max.getChunk().getZ();
			
			Long id = claim.getID();
			String tag;
			if (id != null) {
				tag = id.toString();
			} else {
				tag = "";
			}
			returned.add(new ProtectionRange(tag, minX, minZ, maxX, maxZ));
		}
		return returned;
	}

	@Override
	public IRangeGroup getRangeGroup() {
		return rangeGroup;
	}

	@Override
	public void dispose() {
		
	}

	/**
	 * Retrieves the {@link Claim}s for for a player.
	 */
	private Collection<Claim> getPlayerClaims(Player player) {
		PlayerData data = GriefPrevention.instance.dataStore
				.getPlayerData(player.getUniqueId());
		return data.getClaims();
	}
}
