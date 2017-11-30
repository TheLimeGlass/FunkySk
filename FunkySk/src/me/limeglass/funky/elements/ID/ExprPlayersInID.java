package me.limeglass.funky.elements.ID;

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
import me.limeglass.funky.lang.FunkyExpression;
import me.limeglass.funky.utils.MusicManager;
import me.limeglass.funky.utils.annotations.AllChangers;
import me.limeglass.funky.utils.annotations.DetermineSingle;
import me.limeglass.funky.utils.annotations.Patterns;
import me.limeglass.funky.utils.annotations.Settable;

@Name("ID - Song players")
@Description("Returns the players currently listening to a song with an ID. Returns as a string because that's what NoteBlockAPI does.")
@Patterns("[(all [[of] the]|the)] player[s] (in|listening to) song with id[s] %strings%")
@DetermineSingle
@AllChangers
@Settable({Player[].class, String[].class})
public class ExprPlayersInID extends FunkyExpression<String> {
	
	@Override
	protected String[] get(Event event) {
		if (areNull(event)) return null;
		ArrayList<String> players = new ArrayList<String>();
		for (String song : expressions.getAll(event, String.class)) {
			players.addAll(MusicManager.getSongPlayer(song).getPlayerList());
		}
		if (players == null || players.isEmpty()) return null;
		return players.toArray(new String[players.size()]);
	}
	
	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		if (areNull(event) || delta == null) return;
		ArrayList<SongPlayer> songPlayers = new ArrayList<SongPlayer>();
		for (String song : expressions.getAll(event, String.class)) {
			songPlayers.add(MusicManager.getSongPlayer(song));
		}
		switch (mode) {
			case ADD:
				for (SongPlayer songPlayer : songPlayers) {
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
				for (SongPlayer songPlayer : songPlayers) {
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
				for (SongPlayer songPlayer : songPlayers) {
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