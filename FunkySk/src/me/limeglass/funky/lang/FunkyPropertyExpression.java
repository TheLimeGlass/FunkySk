package me.limeglass.funky.lang;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.limeglass.funky.Funky;
import me.limeglass.funky.Syntax;
import me.limeglass.funky.utils.Utils;
import me.limeglass.funky.utils.annotations.AllChangers;
import me.limeglass.funky.utils.annotations.Changers;
import me.limeglass.funky.utils.annotations.Multiple;
import me.limeglass.funky.utils.annotations.Properties;
import me.limeglass.funky.utils.annotations.Settable;

public abstract class FunkyPropertyExpression<F, T> extends PropertyExpression<F, T> {

	protected ExpressionData expressions;
	protected Class<T> expressionClass;
	private List<Object> values = new ArrayList<Object>();
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends T> getReturnType() {
		if (expressionClass == null) expressionClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		return expressionClass;
	}
	
	public String[] getSyntax() {
		return Syntax.get(getClass().getSimpleName());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		setExpr((Expression<? extends F>) expressions[0]);
		if (expressions != null && getSyntax() != null) this.expressions = new ExpressionData(expressions, getSyntax()[0]);
		return true;
	}

	protected String getPropertyName() {
		return (getClass().isAnnotationPresent(Properties.class)) ? ((Properties) getClass().getAnnotation(Properties.class)).value()[1] : null;
	}
	
	@Override
	public String toString(Event event, boolean debug) {
		String modSyntax = Syntax.isModified(getClass()) ? "Modified syntax: " + Arrays.toString(getSyntax()) : Arrays.toString(getSyntax());
		if (event == null) {
			Funky.debugMessage(getClass().getSimpleName() + " - " + modSyntax + " Data: " + Arrays.toString(values.toArray()));
		} else {
			Funky.debugMessage(getClass().getSimpleName() + " - " + modSyntax + " (" + event.getEventName() + ")" + " Data: " + Arrays.toString(values.toArray()));
		}
		return "[Stocks] " + getClass().getSimpleName() + ": the " + getPropertyName() + " of " + getExpr().toString(event, debug);
	}
	
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		Class<?>[] returnable = (getClass().isAnnotationPresent(Multiple.class)) ? CollectionUtils.array(Utils.getArrayClass(expressionClass)) : CollectionUtils.array(expressionClass);
		if (getClass().isAnnotationPresent(Settable.class)) returnable = getClass().getAnnotation(Settable.class).value();
		if (getClass().isAnnotationPresent(AllChangers.class)) return returnable;
		if (!getClass().isAnnotationPresent(Changers.class)) return null;
		return (Arrays.asList(getClass().getAnnotation(Changers.class).value()).contains(mode)) ? returnable : null;
	}
	
	protected Boolean isNull(Event event) {
		if (getExpr() == null) return true;
		if (getExpr().isSingle() && getExpr().getSingle(event) == null) {
			Funky.debugMessage("The property expression was null: " + getExpr().toString(event, true));
			return true;
		} else if (getExpr().getAll(event).length == 0 || getExpr().getAll(event) == null) {
			Funky.debugMessage("The property expression was null: " + getExpr().toString(event, true));
			return true;
		}
		return false;
	}
}