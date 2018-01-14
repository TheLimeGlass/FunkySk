package me.limeglass.funky.elements.ID;

import org.bukkit.event.Event;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.ExpressionType;
import me.limeglass.funky.lang.FunkyExpression;
import me.limeglass.funky.utils.MusicManager;
import me.limeglass.funky.utils.annotations.ExpressionProperty;
import me.limeglass.funky.utils.annotations.Patterns;

@Name("ID - Grab ID songs")
@Description("Returns the ID's of all songs currently playing.")
@Patterns("[(all [[of] the]|the)] id songs playing")
@ExpressionProperty(ExpressionType.SIMPLE)
public class ExprIDSongs extends FunkyExpression<String> {
	
	@Override
	protected String[] get(Event event) {
		return MusicManager.getSongs().toArray(new String[MusicManager.getSongs().size()]);
	}
}