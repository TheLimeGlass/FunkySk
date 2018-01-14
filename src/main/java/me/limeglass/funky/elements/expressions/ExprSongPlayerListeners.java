package me.limeglass.funky.elements.expressions;

import java.util.ArrayList;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.xxmicloxx.NoteBlockAPI.SongPlayer;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.funky.lang.FunkyPropertyExpression;
import me.limeglass.funky.utils.annotations.AllChangers;
import me.limeglass.funky.utils.annotations.Properties;
import me.limeglass.funky.utils.annotations.PropertiesAddition;
import me.limeglass.funky.utils.annotations.Settable;

@Name("Song player users")
@Description("Returns the listeners of the song player(s).")
@Properties({"songplayers", "(player|listener)[s]", "{1}[(all [[of] the]|the)]"})
@PropertiesAddition("song[ ]player[s]")
@AllChangers
@Settable({Player[].class, String[].class})
public class ExprSongPlayerListeners extends FunkyPropertyExpression<SongPlayer, Player> {

	@Override
	protected Player[] get(Event event, SongPlayer[] songPlayers) {
		if (isNull(event)) return null;
		ArrayList<Player> players = new ArrayList<Player>();
		for (SongPlayer songPlayer : songPlayers) {
			for (String player : songPlayer.getPlayerList()) {
				players.add(Bukkit.getPlayer(player));
			}
		}
		if (players == null || players.isEmpty()) return null;
		return players.toArray(new Player[players.size()]);
	}
	
	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		if (isNull(event) || delta == null) return;
		switch (mode) {
			case ADD:
				for (SongPlayer songPlayer : expressions.getAll(event, SongPlayer.class)) {
					for (Object object : delta) {
						if (object instanceof String && !songPlayer.getPlayerList().contains((String)object)) {
							songPlayer.addPlayer(Bukkit.getPlayer((String)object));
						} else if (object instanceof Player && !songPlayer.getPlayerList().contains(((Player)object).getName())) {
							songPlayer.addPlayer((Player)object);
						} else {
							Skript.error("That was an unsupported value for removeing of Players within an ID: " + Objects.toString(object));
						}
					}
				}
				break;
			case REMOVE:
				for (SongPlayer songPlayer : expressions.getAll(event, SongPlayer.class)) {
					for (Object object : delta) {
						if (object instanceof String && songPlayer.getPlayerList().contains((String)object)) {
							songPlayer.removePlayer(Bukkit.getPlayer((String)object));
						} else if (object instanceof Player && songPlayer.getPlayerList().contains(((Player)object).getName())) {
							songPlayer.removePlayer((Player)object);
						} else {
							Skript.error("That was an unsupported value for removal of Players within an ID: " + Objects.toString(object));
						}
					}
				}
				break;
			case SET:
				super.change(event, delta, ChangeMode.DELETE);
				super.change(event, delta, ChangeMode.ADD);
				break;
			case REMOVE_ALL:
			case RESET:
			case DELETE:
				for (SongPlayer songPlayer : expressions.getAll(event, SongPlayer.class)) {
					for (String player : songPlayer.getPlayerList()) {
						songPlayer.removePlayer(Bukkit.getPlayer(player));
					}
				}
				break;
			default:
				break;
		}
	}
}