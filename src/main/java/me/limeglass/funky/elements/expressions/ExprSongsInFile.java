package me.limeglass.funky.elements.expressions;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import org.bukkit.event.Event;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.funky.lang.FunkyExpression;
import me.limeglass.funky.utils.annotations.Patterns;

@Name("File songs")
@Description("Returns all the .nbs songs in a file location.")
@Patterns("[(all [[of] the]|the)] [nbt] song[s] [file[s]] in [the] (directory|file[s]) %string%")
public class ExprSongsInFile extends FunkyExpression<String> {
	
	@Override
	protected String[] get(Event event) {
		if (areNull(event)) return null;
		ArrayList<String> songs = new ArrayList<String>();
		File directory = new File(expressions.getSingle(event, String.class));
		if (!directory.isDirectory()) return null;
		FilenameFilter filter = new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				if (name.lastIndexOf('.') > 0) {
					String extension = name.substring(name.lastIndexOf('.'));
					if (extension.equals(".nbs")) return true;
				}
				return false;
			}
		};
		for (File file : directory.listFiles(filter)) {
			songs.add(file.getName());
		}
		if (songs == null || songs.isEmpty()) return null;
		return songs.toArray(new String[songs.size()]);
	}
}