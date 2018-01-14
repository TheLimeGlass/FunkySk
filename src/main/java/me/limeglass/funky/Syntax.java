package me.limeglass.funky;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.util.SimpleEvent;
import me.limeglass.funky.utils.Utils;
import me.limeglass.funky.utils.annotations.AllChangers;
import me.limeglass.funky.utils.annotations.Changers;
import me.limeglass.funky.utils.annotations.Disabled;

public class Syntax {

	private static HashMap<String, String[]> modified = new HashMap<String, String[]>();
	private static HashMap<String, String[]> completeSyntax = new HashMap<String, String[]>();

	public static String[] register(Class<?> syntaxClass, String... syntax) {
		if (syntaxClass.isAnnotationPresent(Disabled.class)) return null;
		String type = "Expressions";
		if (Condition.class.isAssignableFrom(syntaxClass)) type = "Conditions";
		else if (Effect.class.isAssignableFrom(syntaxClass)) type = "Effects";
		else if (SimpleEvent.class.isAssignableFrom(syntaxClass)) type = "Events";
		else if (PropertyExpression.class.isAssignableFrom(syntaxClass)) type = "PropertyExpressions";
		String node = "Syntax." + type + "." + syntaxClass.getSimpleName() + ".";
		if (!Funky.getSyntaxData().isSet(node + "enabled")) {
			Funky.getSyntaxData().set(node + "enabled", true);
			save();
		}
		if (syntaxClass.isAnnotationPresent(Changers.class) || syntaxClass.isAnnotationPresent(AllChangers.class)) {
			if (syntaxClass.isAnnotationPresent(AllChangers.class)) Funky.getSyntaxData().set(node + "changers", "All changers");
			else {
				ChangeMode[] changers = syntaxClass.getAnnotation(Changers.class).value();
				Funky.getSyntaxData().set(node + "changers", Arrays.toString(changers));
			}
			save();
		}
		if (syntaxClass.isAnnotationPresent(Description.class)) {
			String[] descriptions = syntaxClass.getAnnotation(Description.class).value();
			Funky.getSyntaxData().set(node + "description", descriptions[0]);
			save();
		}
		if (!Funky.getSyntaxData().getBoolean(node + "enabled")) {
			if (Funky.getInstance().getConfig().getBoolean("NotRegisteredSyntax", false)) Funky.consoleMessage(node.toString() + " didn't register!");
			return null;
		}
		if (!Funky.getSyntaxData().isSet(node + "syntax")) {
			Funky.getSyntaxData().set(node + "syntax", syntax);
			save();
			return add(syntaxClass.getSimpleName(), syntax);
		}
		List<String> data = Funky.getSyntaxData().getStringList(node + "syntax");
		if (!Utils.compareArrays(data.toArray(new String[data.size()]), syntax)) modified.put(syntaxClass.getSimpleName(), syntax);
		if (Funky.getSyntaxData().isList(node + "syntax")) {
			List<String> syntaxes = Funky.getSyntaxData().getStringList(node + "syntax");
			return add(syntaxClass.getSimpleName(), syntaxes.toArray(new String[syntaxes.size()]));
		}
		return add(syntaxClass.getSimpleName(), new String[]{Funky.getSyntaxData().getString(node + "syntax")});
	}
	
	public static Boolean isModified(@SuppressWarnings("rawtypes") Class syntaxClass) {
		return modified.containsKey(syntaxClass.getSimpleName());
	}
	
	public static String[] get(String syntaxClass) {
		return completeSyntax.get(syntaxClass);
	}
	
	private static String[] add(String syntaxClass, String... syntax) {
		if (!completeSyntax.containsValue(syntax)) {
			completeSyntax.put(syntaxClass, syntax);
		}
		return syntax;
	}
	
	public static void save() {
		try {
			Funky.getSyntaxData().save(Funky.syntaxFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}