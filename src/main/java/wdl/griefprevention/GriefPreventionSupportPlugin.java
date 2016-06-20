package wdl.griefprevention;

import me.ryanhamshire.GriefPrevention.GriefPrevention;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;
import org.mcstats.Metrics.Graph;
import org.mcstats.Metrics.Plotter;

import wdl.RangeGroupTypeRegistrationEvent;
import wdl.range.IRangeProducer;

/**
 * WDLCompanion support plugin for 
 * <a href="https://www.spigotmc.org/resources/griefprevention.1884/">GriefPrevention</a>
 */
public class GriefPreventionSupportPlugin extends JavaPlugin implements Listener {
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		
		try {
			class StringPlotter extends Plotter {
				public StringPlotter(String str) {
					super(str);
				}
				
				@Override
				public int getValue() {
					return 1;
				}
			}
			
			Metrics metrics = new Metrics(this);
			
			Graph griefPreventionVersionGraph = metrics.createGraph("griefPreventionVersion");
			String griefPreventionVersion = getProvidingPlugin(GriefPrevention.class)
					.getDescription().getFullName();
			griefPreventionVersionGraph.addPlotter(new StringPlotter(griefPreventionVersion));
			
			Graph wdlcVersionGraph = metrics.createGraph("wdlcompanionVersion");
			String wdlcVersion = getProvidingPlugin(IRangeProducer.class)
					.getDescription().getFullName();
			wdlcVersionGraph.addPlotter(new StringPlotter(wdlcVersion));
			
			metrics.start();
		} catch (Exception e) {
			getLogger().warning("Failed to start PluginMetrics :(");
		}
	}
	
	@EventHandler
	public void registerRangeGroupTypes(RangeGroupTypeRegistrationEvent e) {
		e.addRegistration("Owned GriefPrevention claims", new OwnedClaimRangeGroupType());
	}
}
