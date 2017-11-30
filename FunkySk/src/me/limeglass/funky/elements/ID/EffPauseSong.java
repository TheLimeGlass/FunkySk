package me.limeglass.funky.elements.ID;

import org.bukkit.event.Event;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.funky.lang.FunkyEffect;
import me.limeglass.funky.utils.MusicManager;
import me.limeglass.funky.utils.annotations.Patterns;

@Name("ID - Pause song")
@Description("Pause a song with an ID.")
@Patterns("(stop|pause) [note[[ ]block]] song[s] with id[s] %strings%")
public class EffPauseSong extends FunkyEffect {

	@Override
	protected void execute(Event event) {
		if (isNull(event, String.class)) return;
		for (String song : expressions.getAll(event, String.class)) {
			MusicManager.getSongPlayer(song).setPlaying(false);
		}
	}
}