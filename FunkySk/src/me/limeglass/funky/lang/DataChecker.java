package me.limeglass.funky.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import me.limeglass.funky.Funky;

public interface DataChecker {

	public default <T> Boolean areNull(Event event, ExpressionData expressions) {
		if (expressions.getExpressions() == null) return true;
		for (Expression<?> expression : expressions.getExpressions()) {
			if (expression == null) return true;
			if (expression.isSingle()) {
				if (expression.getSingle(event) == null) {
					Funky.debugMessage("An expression was null: " + expression.toString(event, true));
					return true;
				}
			} else if (expression.getAll(event).length == 0 || expression.getSingle(event) == null) {
				ArrayList<String> nulledExpressions = new ArrayList<String>();
				Arrays.stream(expressions.getExpressions()).filter(expr -> expr != null && expr.getAll(event).length == 0 || expr.getAll(event) == null).forEach(expr -> nulledExpressions.add(expr.toString(event, true)));
				Funky.debugMessage("Expressions were null: " + nulledExpressions.toString());
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public default <T> Boolean isNull(Event event, ExpressionData expressions, Class<T>... types) {
		Map<Expression<?>, T[]> map = expressions.getAllMapOf(event, types);
		if (map == null || map.isEmpty()) return true;
		for (Entry<Expression<?>, T[]> entry : map.entrySet()) {
			if (entry.getKey() != null && entry.getKey().isSingle()) {
				if (entry.getKey().getSingle(event) == null) {
					Funky.debugMessage("An expression was null: " + entry.getKey().toString(event, true));
					return true;
				}
			} else if (entry.getKey() != null && entry.getKey().getAll(event).length == 0 || entry.getKey().getAll(event) == null) {
				ArrayList<String> nulledExpressions = new ArrayList<String>();
				Arrays.stream(expressions.getExpressions()).filter(expr -> expr != null && expr.getAll(event).length == 0 || expr.getAll(event) == null).forEach(expr -> nulledExpressions.add(expr.toString(event, true)));
				Funky.debugMessage("Expressions were null: " + nulledExpressions.toString());
				return true;
			}
		}
		return false;
	}
}