package org.andromda.cartridges.jsf.metafacades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.andromda.cartridges.jsf.JSFGlobals;
import org.andromda.cartridges.jsf.JSFProfile;
import org.andromda.cartridges.jsf.JSFUtils;
import org.andromda.metafacades.uml.AttributeFacade;
import org.andromda.metafacades.uml.ClassifierFacade;
import org.andromda.metafacades.uml.EventFacade;
import org.andromda.metafacades.uml.FrontEndAction;
import org.andromda.metafacades.uml.FrontEndActivityGraph;
import org.andromda.metafacades.uml.FrontEndForward;
import org.andromda.metafacades.uml.FrontEndParameter;
import org.andromda.metafacades.uml.FrontEndView;
import org.andromda.metafacades.uml.ModelElementFacade;
import org.andromda.metafacades.uml.TransitionFacade;
import org.andromda.metafacades.uml.UseCaseFacade;
import org.andromda.utils.StringUtilsHelper;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

/**
 * MetafacadeLogic implementation for
 * org.andromda.cartridges.jsf.metafacades.JSFParameter.
 * 
 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter
 */
public class JSFParameterLogicImpl extends JSFParameterLogic {
	private static final long serialVersionUID = 34L;

	/**
	 * Stores the initial value of each type.
	 */
	private final Map<String, String> initialValues = new HashMap<String, String>();

	// to be used in the range validator: "range - 1000" or "range 20 -".
	/** - */
	static final String UNDEFINED_BOUND = "-";

	/** javax.validation.constraints.NotNull */
	static final String AN_REQUIRED = "@javax.validation.constraints.NotNull";

	/** org.hibernate.validator.constraints.URL */
	static final String AN_URL = "@org.hibernate.validator.constraints.URL";

	/** org.apache.myfaces.extensions.validator.baseval.annotation.LongRange */
	static final String AN_LONG_RANGE = "@org.apache.myfaces.extensions.validator.baseval.annotation.LongRange";

	/** org.apache.myfaces.extensions.validator.baseval.annotation.DoubleRange */
	static final String AN_DOUBLE_RANGE = "@org.apache.myfaces.extensions.validator.baseval.annotation.DoubleRange";

	/** org.hibernate.validator.constraints.Email */
	static final String AN_EMAIL = "@org.hibernate.validator.constraints.Email";

	/** org.hibernate.validator.constraints.CreditCardNumber */
	static final String AN_CREDIT_CARD = "@org.hibernate.validator.constraints.CreditCardNumber";

	/** javax.validation.constraints.Size */
	static final String AN_LENGTH = "@javax.validation.constraints.Size";

	/** org.apache.myfaces.extensions.validator.baseval.annotation.Pattern */
	static final String AN_PATTERN = "@org.apache.myfaces.extensions.validator.baseval.annotation.Pattern";

	/** org.apache.myfaces.extensions.validator.crossval.annotation.Equals */
	static final String AN_EQUALS = "@org.apache.myfaces.extensions.validator.crossval.annotation.Equals";

	/**
	 * @param metaObject
	 * @param context
	 */
	public JSFParameterLogicImpl(final Object metaObject, final String context) {
		super(metaObject, context);
	}

	/**
	 * Constructs a string representing an array initialization in Java.
	 * 
	 * @return A String representing Java code for the initialization of an
	 *         array.
	 */
	private String constructDummyArray() {
		return JSFUtils.constructDummyArrayDeclaration(getName(),
				JSFGlobals.DUMMY_ARRAY_COUNT);
	}

	@SuppressWarnings("rawtypes")
	private Collection getAccordionColumns() {
		Collection tableColumns;
		final Map<String, ModelElementFacade> tableColumnsMap = new LinkedHashMap<String, ModelElementFacade>();
		for (final Object attribute : getAttributes()) {
			final ModelElementFacade mef = (ModelElementFacade) attribute;
			tableColumnsMap.put(mef.getName(), mef);
		}
		for (final Object assocEndObj : getNavigableAssociationEnds()) {
			final ModelElementFacade associationEnd = (ModelElementFacade) assocEndObj;
			tableColumnsMap.put(associationEnd.getName(), associationEnd);
		}

		tableColumns = new ArrayList();
		final Set<String> addedArrayParameters = new HashSet<String>();
		for (String columnObject : getTableColumnNames()) {
			// in case of accordions take only the bean name
			if (columnObject.contains(".")) {
				columnObject = columnObject.split("\\.")[0];
			}
			// avoid duplicate insertion
			if (addedArrayParameters.contains(columnObject)) {
				continue;
			} else {
				addedArrayParameters.add(columnObject);
			}
			tableColumns.add(tableColumnsMap.get(columnObject));
		}
		return tableColumns;
	}

	/**
	 * @return the default date format pattern as defined using the configured
	 *         property
	 */
	private String getDefaultDateFormat() {
		return (String) getConfiguredProperty(JSFGlobals.PROPERTY_DEFAULT_DATEFORMAT);
	}

	/**
	 * @return the default time format pattern as defined using the configured
	 *         property
	 */
	private String getDefaultTimeFormat() {
		return (String) getConfiguredProperty(JSFGlobals.PROPERTY_DEFAULT_TIMEFORMAT);
	}

	/**
	 * Gets the current value of the specified input type (or an empty string if
	 * one isn't specified).
	 * 
	 * @return the input type name.
	 */
	private String getInputType() {
		return ObjectUtils.toString(
				findTaggedValue(JSFProfile.TAGGEDVALUE_INPUT_TYPE)).trim();
	}

	/**
	 * If this is a table this method returns all those actions that are
	 * declared to work on this table.
	 * 
	 * @param hyperlink
	 *            denotes on which type of actions to filter
	 */
	private List<JSFAction> getTableActions(final boolean hyperlink) {
		final Set<JSFAction> actions = new LinkedHashSet<JSFAction>();
		final String name = StringUtils.trimToNull(getName());
		if (name != null && isTable()) {
			final JSFView view = (JSFView) getView();

			final Collection<UseCaseFacade> allUseCases = getModel()
					.getAllUseCases();
			for (final UseCaseFacade useCase : allUseCases) {
				if (useCase instanceof JSFUseCase) {
					final FrontEndActivityGraph graph = ((JSFUseCase) useCase)
							.getActivityGraph();
					if (graph != null) {
						final Collection<TransitionFacade> transitions = graph
								.getTransitions();
						for (final TransitionFacade transition : transitions) {
							if (transition.getSource().equals(view)
									&& transition instanceof JSFAction) {
								final JSFAction action = (JSFAction) transition;
								if (action.isTableLink()
										&& name.equals(action
												.getTableLinkName())) {
									if (hyperlink == action.isHyperlink()) {
										actions.add(action);
									}
								}
							}
						}
					}
				}
			}
		}
		return new ArrayList<JSFAction>(actions);
	}

	/**
	 * @see org.andromda.metafacades.uml.FrontEndParameter#getTableColumns()
	 */
	@Override
	// TODO tableColumns can be either String or JSFParameter. Should use a
	// single return type in Collection.
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Collection getTableColumns() {
		Collection tableColumns;

		if (isAccordion()) {
			tableColumns = getAccordionColumns();
		} else {
			tableColumns = super.getTableColumns();
			if (tableColumns.isEmpty()) {
				// try to preserve the order of the elements encountered
				// final Map<String, JSFParameter> tableColumnsMap = new
				// LinkedHashMap<String, JSFParameter>();
				final Map tableColumnsMap = new LinkedHashMap();

				// order is important
				final List<JSFAction> actions = new ArrayList<JSFAction>();

				// all table actions need the exact same parameters, just not
				// always
				// all of them
				actions.addAll(getTableFormActions());

				// if there are any actions that are hyperlinks then their
				// parameters get priority
				// the user should not have modeled it that way (constraints
				// will
				// warn him/her)
				actions.addAll(getTableHyperlinkActions());

				for (final JSFAction action : actions) {
					for (final FrontEndParameter actionParameter : action
							.getParameters()) {
						if (actionParameter instanceof JSFParameter) {
							final JSFParameter parameter = (JSFParameter) actionParameter;
							final String parameterName = parameter.getName();
							if (parameterName != null) {
								// never overwrite column specific table links
								// the hyperlink table links working on a real
								// column get priority
								final Object existingObject = tableColumnsMap
										.get(parameterName);
								if (existingObject instanceof JSFParameter) {
									if (action.isHyperlink()
											&& parameterName.equals(action
													.getTableLinkColumnName())) {
										tableColumnsMap.put(parameterName,
												parameter);
									}
								}
							}
						}
					}
				}

				// for any missing parameters we just add the name of the column
				for (final String columnName : getTableColumnNames()) {
					if (!tableColumnsMap.containsKey(columnName)) {
						tableColumnsMap.put(columnName, columnName);
					}
				}

				// return everything in the same order as it has been modeled
				// (using
				// the table tagged value
				for (final String columnObject : getTableColumnNames()) {
					tableColumns.add(tableColumnsMap.get(columnObject));
				}

			}
		}
		return tableColumns;
	}

	// TODO remove after 3.4 release
	/**
	 * Hack to keep the compatibility with Andromda 3.4-SNAPSHOT
	 */
	/**
	 * @see org.andromda.metafacades.uml.FrontEndParameter#getView()
	 */
	@Override
	public FrontEndView getView() {
		Object view = null;
		final EventFacade event = getEvent();
		if (event != null) {
			final TransitionFacade transition = event.getTransition();
			if (transition instanceof JSFActionLogicImpl) {
				final JSFActionLogicImpl action = (JSFActionLogicImpl) transition;
				view = action.getInput();
			} else if (transition instanceof FrontEndForward) {
				final FrontEndForward forward = (FrontEndForward) transition;
				if (forward.isEnteringView()) {
					view = forward.getTarget();
				}
			}
		}
		return (FrontEndView) view;
	}

	@Override
	protected Collection<String> handleGetAccordionHeaderNames() {
		final String accordionHeadersTaggedValue = (String) findTaggedValue(JSFProfile.TAGGEDVALUE_ACCORDION_HEADER_NAMES);
		Collection<String> result;
		if (StringUtils.isBlank(accordionHeadersTaggedValue)) {
			// only non array attributes
			result = super.getTableAttributeNames();
		} else {
			result = Arrays.asList(accordionHeadersTaggedValue.split(","));
		}
		return result;
	}

	/**
	 * @return the annotations
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getMaxLength()
	 */
	@Override
	protected Collection<String> handleGetAnnotations() {
		final Collection<String> result = new HashSet<String>();
		boolean requiredAdded = false;
		for (final String vt : (Collection<String>) getValidatorTypes()) {
			if (vt.startsWith("@")) // add the annotation
			{
				result.add(vt);
			}
			if (JSFUtils.VT_REQUIRED.equals(vt)) {
				requiredAdded = true;
				result.add(AN_REQUIRED);
			} else if (JSFUtils.VT_URL.equals(vt)) {
				result.add(AN_URL);
			} else if (JSFUtils.VT_INT_RANGE.equals(vt)) {
				final StringBuilder sb = new StringBuilder(AN_LONG_RANGE + "(");
				final String format = JSFUtils
						.getInputFormat((ModelElementFacade) THIS());
				final String rangeStart = JSFUtils.getRangeStart(format);
				boolean addComma = false;
				if (StringUtils.isNotBlank(rangeStart)
						&& !rangeStart.equals(UNDEFINED_BOUND)) {
					sb.append("minimum=" + rangeStart);
					addComma = true;
				}
				final String rangeEnd = JSFUtils.getRangeEnd(format);
				if (StringUtils.isNotBlank(rangeEnd)
						&& !rangeEnd.equals(UNDEFINED_BOUND)) {
					if (addComma) {
						sb.append(",");
					}
					sb.append("maximum=" + rangeEnd);
				}
				sb.append(")");
				result.add(sb.toString());
			} else if (JSFUtils.VT_FLOAT_RANGE.equals(vt)
					|| JSFUtils.VT_DOUBLE_RANGE.equals(vt)) {
				final StringBuilder sb = new StringBuilder(AN_DOUBLE_RANGE
						+ "(");
				final String format = JSFUtils
						.getInputFormat(((ModelElementFacade) THIS()));
				final String rangeStart = JSFUtils.getRangeStart(format);
				boolean addComma = false;
				if (StringUtils.isNotBlank(rangeStart)
						&& !rangeStart.equals(UNDEFINED_BOUND)) {
					sb.append("minimum=" + rangeStart);
					addComma = true;
				}
				final String rangeEnd = JSFUtils.getRangeEnd(format);
				if (StringUtils.isNotBlank(rangeEnd)
						&& !rangeEnd.equals(UNDEFINED_BOUND)) {
					if (addComma) {
						sb.append(",");
					}
					sb.append("maximum=" + rangeEnd);
				}
				sb.append(")");
				result.add(sb.toString());
			} else if (JSFUtils.VT_EMAIL.equals(vt)) {
				result.add(AN_EMAIL);
			} else if (JSFUtils.VT_CREDIT_CARD.equals(vt)) {
				result.add(AN_CREDIT_CARD);
			} else if (JSFUtils.VT_MIN_LENGTH.equals(vt)
					|| JSFUtils.VT_MAX_LENGTH.equals(vt)) {
				final StringBuilder sb = new StringBuilder(AN_LENGTH + "(");
				final Collection formats = findTaggedValues(JSFProfile.TAGGEDVALUE_INPUT_FORMAT);
				boolean addComma = false;
				for (final Iterator formatIterator = formats.iterator(); formatIterator
						.hasNext();) {
					final String additionalFormat = String
							.valueOf(formatIterator.next());
					if (JSFUtils.isMinLengthFormat(additionalFormat)) {
						if (addComma) {
							sb.append(",");
						}
						sb.append("min=");
						sb.append(JSFUtils.getMinLengthValue(additionalFormat));
						addComma = true;
					} else if (JSFUtils.isMaxLengthFormat(additionalFormat)) {
						if (addComma) {
							sb.append(",");
						}
						sb.append("max=");
						sb.append(JSFUtils.getMinLengthValue(additionalFormat));
						addComma = true;
					}
				}
				sb.append(")");
				result.add(sb.toString());
			} else if (JSFUtils.VT_MASK.equals(vt)) {
				final Collection formats = findTaggedValues(JSFProfile.TAGGEDVALUE_INPUT_FORMAT);
				for (final Iterator formatIterator = formats.iterator(); formatIterator
						.hasNext();) {
					final String additionalFormat = String
							.valueOf(formatIterator.next());
					if (JSFUtils.isPatternFormat(additionalFormat)) {
						result.add(AN_PATTERN + "(\""
								+ JSFUtils.getPatternValue(additionalFormat)
								+ "\")");
					}
				}
			} else if (JSFUtils.VT_VALID_WHEN.equals(vt)) {
				result.add("");
			} else if (JSFUtils.VT_EQUAL.equals(vt)) {
				result.add(AN_EQUALS + "(\""
						+ JSFUtils.getEqual((ModelElementFacade) THIS())
						+ "\")");
			}
		}
		if (!requiredAdded && getLower() > 0) {
			result.add(AN_REQUIRED);
		}
		return result;
	}

	/**
	 * @return attributes
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getAttributes()
	 */
	@Override
	protected Collection<AttributeFacade> handleGetAttributes() {
		Collection<AttributeFacade> attributes = null;
		ClassifierFacade type = getType();
		if (type != null) {
			if (type.isArrayType()) {
				type = type.getNonArray();
			}
			if (type != null) {
				attributes = type.getAttributes(true);
			}
		}
		return attributes == null ? new ArrayList<AttributeFacade>()
				: attributes;
	}

	/**
	 * @return backingListName
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getBackingListName()
	 */
	@Override
	protected String handleGetBackingListName() {
		return ObjectUtils.toString(
				getConfiguredProperty(JSFGlobals.BACKING_LIST_PATTERN))
				.replaceAll("\\{0\\}", getName());
	}

	/**
	 * @return backingValueName
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getBackingValueName()
	 */
	@Override
	protected String handleGetBackingValueName() {
		return ObjectUtils.toString(
				getConfiguredProperty(JSFGlobals.BACKING_VALUE_PATTERN))
				.replaceAll("\\{0\\}", getName());
	}

	/**
	 * @return dateFormatter
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getDateFormatter()
	 */
	@Override
	protected String handleGetDateFormatter() {
		final ClassifierFacade type = getType();
		return type != null && type.isDateType() ? getName() + "DateFormatter"
				: null;
	}

	/**
	 * @return getMessageKey() + '.' +
	 *         JSFGlobals.DOCUMENTATION_MESSAGE_KEY_SUFFIX
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getDocumentationKey()
	 */
	@Override
	protected String handleGetDocumentationKey() {
		return getMessageKey() + '.'
				+ JSFGlobals.DOCUMENTATION_MESSAGE_KEY_SUFFIX;
	}

	/**
	 * @return documentationValue
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getDocumentationValue()
	 */
	@Override
	protected String handleGetDocumentationValue() {
		final String value = StringUtilsHelper.toResourceMessage(this
				.getDocumentation("", 64, false));
		return value == null ? "" : value;
	}

	/**
	 * @return dummyValue
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getDummyValue()
	 */
	@Override
	protected String handleGetDummyValue() {
		final ClassifierFacade type = getType();
		final String typeName = type != null ? type.getFullyQualifiedName()
				: "";
		String initialValue = null;
		if (type != null) {
			if (type.isSetType()) {
				initialValue = "new java.util.LinkedHashSet(java.util.Arrays.asList("
						+ constructDummyArray() + "))";
			} else if (type.isCollectionType()) {
				initialValue = "java.util.Arrays.asList("
						+ constructDummyArray() + ")";
			} else if (type.isArrayType()) {
				initialValue = constructDummyArray();
			}
			final String name = getName() != null ? getName() : "";
			if (initialValues.isEmpty()) {
				initialValues.put(boolean.class.getName(), "false");
				initialValues.put(int.class.getName(),
						"(int)" + name.hashCode());
				initialValues.put(long.class.getName(),
						"(long)" + name.hashCode());
				initialValues.put(short.class.getName(),
						"(short)" + name.hashCode());
				initialValues.put(byte.class.getName(),
						"(byte)" + name.hashCode());
				initialValues.put(float.class.getName(),
						"(float)" + name.hashCode());
				initialValues.put(double.class.getName(),
						"(double)" + name.hashCode());
				initialValues.put(char.class.getName(),
						"(char)" + name.hashCode());

				initialValues.put(String.class.getName(), "\"" + name + "-test"
						+ "\"");
				initialValues.put(java.util.Date.class.getName(),
						"new java.util.Date()");
				initialValues.put(java.sql.Date.class.getName(),
						"new java.util.Date()");
				initialValues.put(java.sql.Timestamp.class.getName(),
						"new java.util.Date()");

				initialValues.put(Integer.class.getName(), "new Integer((int)"
						+ name.hashCode() + ")");
				initialValues.put(Boolean.class.getName(), "Boolean.FALSE");
				initialValues.put(Long.class.getName(), "new Long((long)"
						+ name.hashCode() + ")");
				initialValues.put(Character.class.getName(),
						"new Character(char)" + name.hashCode() + ")");
				initialValues.put(Float.class.getName(), "new Float((float)"
						+ name.hashCode() / hashCode() + ")");
				initialValues.put(Double.class.getName(), "new Double((double)"
						+ name.hashCode() / hashCode() + ")");
				initialValues.put(Short.class.getName(), "new Short((short)"
						+ name.hashCode() + ")");
				initialValues.put(Byte.class.getName(), "new Byte((byte)"
						+ name.hashCode() + ")");
			}
			if (initialValue == null) {
				initialValue = initialValues.get(typeName);
			}
		}
		if (initialValue == null) {
			initialValue = "null";
		}
		return initialValue;
	}

	/**
	 * @return format
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getFormat()
	 */
	@Override
	protected String handleGetFormat() {
		return JSFUtils.getFormat((ModelElementFacade) THIS(), getType(),
				getDefaultDateFormat(), getDefaultTimeFormat());
	}

	/**
	 * @return getName() + "Set"
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getFormAttributeSetProperty()
	 */
	@Override
	protected String handleGetFormAttributeSetProperty() {
		return getName() + "Set";
	}

	/**
	 * @return 
	 *         findTaggedValue(JSFProfile.TAGGEDVALUE_INPUT_TABLE_IDENTIFIER_COLUMNS
	 *         )
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getInputTableIdentifierColumns()
	 */
	@Override
	protected String handleGetInputTableIdentifierColumns() {
		return ObjectUtils
				.toString(
						findTaggedValue(JSFProfile.TAGGEDVALUE_INPUT_TABLE_IDENTIFIER_COLUMNS))
				.trim();
	}

	/**
	 * @return labelListName
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getLabelListName()
	 */
	@Override
	protected String handleGetLabelListName() {
		return ObjectUtils.toString(
				getConfiguredProperty(JSFGlobals.LABEL_LIST_PATTERN))
				.replaceAll("\\{0\\}", getName());
	}

	/**
	 * @return maxLength
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getMaxLength()
	 */
	@Override
	protected String handleGetMaxLength() {
		final Collection<Collection> vars = getValidatorVars();
		if (vars == null) {
			return null;
		}
		for (final Collection collection : vars) {
			final Object[] values = (collection).toArray();
			if ("maxlength".equals(values[0])) {
				return values[1].toString();
			}
		}
		return null;
	}

	/**
	 * @return messageKey
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getMessageKey()
	 */
	@Override
	protected String handleGetMessageKey() {
		final StringBuilder messageKey = new StringBuilder();

		if (!isNormalizeMessages()) {
			if (isActionParameter()) {
				final JSFAction action = (JSFAction) getAction();
				if (action != null) {
					messageKey.append(action.getMessageKey());
					messageKey.append('.');
				}
			} else {
				final JSFView view = (JSFView) getView();
				if (view != null) {
					messageKey.append(view.getMessageKey());
					messageKey.append('.');
				}
			}
			messageKey.append("param.");
		}

		messageKey.append(StringUtilsHelper.toResourceMessageKey(super
				.getName()));
		return messageKey.toString();
	}

	/**
	 * @return messageValue
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getMessageValue()
	 */
	@Override
	protected String handleGetMessageValue() {
		return StringUtilsHelper.toPhrase(super.getName()); // the actual name
															// is used for
															// displaying
	}

	/**
	 * @return navigableAssociationEnds
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getNavigableAssociationEnds()
	 */
	@Override
	protected Collection<ClassifierFacade> handleGetNavigableAssociationEnds() {
		Collection<ClassifierFacade> associationEnds = null;
		ClassifierFacade type = getType();
		if (type != null) {
			if (type.isArrayType()) {
				type = type.getNonArray();
			}
			if (type != null) {
				associationEnds = type.getNavigableConnectingEnds();
			}
		}
		return associationEnds == null ? new ArrayList<ClassifierFacade>()
				: associationEnds;
	}

	/**
	 * @param columnName
	 * @return tableColumnActions
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getTableColumnActions(String)
	 */
	@Override
	protected List<JSFAction> handleGetTableColumnActions(
			final String columnName) {
		final List<JSFAction> columnActions = new ArrayList<JSFAction>();

		if (columnName != null) {
			final Set<JSFAction> actions = new LinkedHashSet<JSFAction>(
					getTableHyperlinkActions());
			actions.addAll(getTableFormActions());
			for (final JSFAction action : actions) {
				if (columnName.equals(action.getTableLinkColumnName())) {
					columnActions.add(action);
				}
			}
		}

		return columnActions;
	}

	/**
	 * @param columnName
	 * @return tableColumnMessageKey
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getTableColumnMessageKey(String)
	 */
	@Override
	protected String handleGetTableColumnMessageKey(final String columnName) {
		final StringBuilder messageKey = new StringBuilder();
		if (!isNormalizeMessages()) {
			final JSFView view = (JSFView) getView();
			if (view != null) {
				messageKey.append(getMessageKey());
				messageKey.append('.');
			}
		}
		messageKey.append(StringUtilsHelper.toResourceMessageKey(columnName));
		return messageKey.toString();
	}

	/**
	 * @param columnName
	 * @return StringUtilsHelper.toPhrase(columnName)
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getTableColumnMessageValue(String)
	 */
	@Override
	protected String handleGetTableColumnMessageValue(final String columnName) {
		return StringUtilsHelper.toPhrase(columnName);
	}

	/**
	 * @return getTableActions(false)
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getTableFormActions()
	 */
	@Override
	protected List<JSFAction> handleGetTableFormActions() {
		return getTableActions(false);
	}

	/**
	 * @return getTableActions(true)
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getTableHyperlinkActions()
	 */
	@Override
	protected List<JSFAction> handleGetTableHyperlinkActions() {
		return getTableActions(true);
	}

	/**
	 * @return getName() + "SortAscending"
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getTableSortAscendingProperty()
	 */
	@Override
	protected String handleGetTableSortAscendingProperty() {
		return getName() + "SortAscending";
	}

	/**
	 * @return getName() + "SortColumn"
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getTableSortColumnProperty()
	 */
	@Override
	protected String handleGetTableSortColumnProperty() {
		return getName() + "SortColumn";
	}

	/**
	 * @return timeFormatter
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getTimeFormatter()
	 */
	@Override
	protected String handleGetTimeFormatter() {
		final ClassifierFacade type = getType();
		return type != null && type.isTimeType() ? getName() + "TimeFormatter"
				: null;
	}

	/**
	 * @param validatorType
	 * @return validatorArgs
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getValidatorArgs(String)
	 */
	@Override
	protected Collection handleGetValidatorArgs(final String validatorType) {
		return JSFUtils.getValidatorArgs((ModelElementFacade) THIS(),
				validatorType);
	}

	/**
	 * @return validatorTypes
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getValidatorTypes()
	 */
	@Override
	protected Collection handleGetValidatorTypes() {
		return JSFUtils.getValidatorTypes((ModelElementFacade) THIS(),
				getType());
	}

	/**
	 * @return validatorVars
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getValidatorVars()
	 */
	@Override
	protected Collection handleGetValidatorVars() {
		return JSFUtils.getValidatorVars((ModelElementFacade) THIS(),
				getType(), null);
	}

	/**
	 * @return JSFUtils.getValidWhen(this)
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getValidWhen()
	 */
	@Override
	protected String handleGetValidWhen() {
		return JSFUtils.getValidWhen(this);
	}

	/**
	 * @return constructDummyArray()
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getValueListDummyValue()
	 */
	@Override
	protected String handleGetValueListDummyValue() {
		return constructDummyArray();
	}

	/**
	 * @return valueListName
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#getValueListName()
	 */
	@Override
	protected String handleGetValueListName() {
		return ObjectUtils.toString(
				getConfiguredProperty(JSFGlobals.VALUE_LIST_PATTERN))
				.replaceAll("\\{0\\}", getName());
	}

	@Override
	protected String handleGetZone() {
		final String zone = (String) findTaggedValue(JSFProfile.TAGGEDVALUE_ZONE);
		if (StringUtils.isNotBlank(zone)) {
			return zone.trim();
		}
		return "default";
	}

	@Override
	protected boolean handleIsAccordion() {
		final String accordionTaggedValue = (String) findTaggedValue(JSFProfile.TAGGEDVALUE_IS_ACCORDION);
		boolean result;
		if (StringUtils.isNotBlank(accordionTaggedValue)) {
			try {
				result = Boolean.parseBoolean(accordionTaggedValue.trim());
			} catch (final Exception e) {
				result = false;
				System.err.print("Value " + accordionTaggedValue
						+ " not permitted for tagged value "
						+ JSFProfile.TAGGEDVALUE_IS_ACCORDION
						+ " valid values are true,false.");
				e.printStackTrace();
			}
		} else {
			result = StringUtils
					.isNotBlank((String) findTaggedValue(JSFProfile.TAGGEDVALUE_ACCORDION_HEADER_NAMES));
		}
		return result;
	}

	/**
	 * @return isBackingValueRequired
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#isEqualValidator()
	 */
	@Override
	protected boolean handleIsBackingValueRequired() {
		boolean required = false;
		if (isActionParameter()) {
			required = isInputTable();
			final ClassifierFacade type = getType();

			if (!required && type != null) {
				final String name = getName();
				final String typeName = type.getFullyQualifiedName();

				// - if the backing value is not required for this parameter but
				// on
				// a targeting page it IS selectable we must allow the user to
				// set the backing value as well
				final Collection<FrontEndView> views = getAction()
						.getTargetViews();
				for (final Iterator<FrontEndView> iterator = views.iterator(); iterator
						.hasNext() && !required;) {
					final Collection<FrontEndParameter> parameters = iterator
							.next().getAllActionParameters();
					for (final Iterator<FrontEndParameter> parameterIterator = parameters
							.iterator(); parameterIterator.hasNext()
							&& !required;) {
						final FrontEndParameter object = parameterIterator
								.next();
						if (object instanceof JSFParameter) {
							final JSFParameter parameter = (JSFParameter) object;
							final String parameterName = parameter.getName();
							final ClassifierFacade parameterType = parameter
									.getType();
							if (parameterType != null) {
								final String parameterTypeName = parameterType
										.getFullyQualifiedName();
								if (name.equals(parameterName)
										&& typeName.equals(parameterTypeName)) {
									required = parameter.isInputTable();
								}
							}
						}
					}
				}
			}
		} else if (isControllerOperationArgument()) {
			final String name = getName();
			final Collection<FrontEndAction> actions = getControllerOperation()
					.getDeferringActions();
			for (final FrontEndAction frontEndAction : actions) {
				final JSFAction action = (JSFAction) frontEndAction;
				final Collection<FrontEndParameter> formFields = action
						.getFormFields();
				for (final Iterator<FrontEndParameter> fieldIterator = formFields
						.iterator(); fieldIterator.hasNext() && !required;) {
					final Object object = fieldIterator.next();
					if (object instanceof JSFParameter) {
						final JSFParameter parameter = (JSFParameter) object;
						if (!parameter.equals(this)) {
							if (name.equals(parameter.getName())) {
								required = parameter.isBackingValueRequired();
							}
						}
					}
				}
			}
		}
		return required;
	}

	/**
	 * @return complex
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#isComplex()
	 */
	@Override
	protected boolean handleIsComplex() {
		boolean complex = false;
		final ClassifierFacade type = getType();
		if (type != null) {
			complex = !type.getAttributes().isEmpty();
			if (!complex) {
				complex = !type.getAssociationEnds().isEmpty();
			}
		}
		return complex;
	}

	/**
	 * @return isEqualValidator
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#isEqualValidator()
	 */
	@Override
	protected boolean handleIsEqualValidator() {
		final String equal = JSFUtils.getEqual((ModelElementFacade) THIS());
		return equal != null && equal.trim().length() > 0;
	}

	/**
	 * @return isInputCheckbox
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#isInputCheckbox()
	 */
	@Override
	protected boolean handleIsInputCheckbox() {
		boolean checkbox = isInputType(JSFGlobals.INPUT_CHECKBOX);
		if (!checkbox && getInputType().length() == 0) {
			final ClassifierFacade type = getType();
			checkbox = type != null ? type.isBooleanType() : false;
		}
		return checkbox;
	}

	/**
	 * @return isInputFile
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#isInputFile()
	 */
	@Override
	protected boolean handleIsInputFile() {
		boolean file = false;
		final ClassifierFacade type = getType();
		if (type != null) {
			file = type.isFileType();
		}
		return file;
	}

	/**
	 * @return isInputType(JSFGlobals.INPUT_HIDDEN)
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#isInputHidden()
	 */
	@Override
	protected boolean handleIsInputHidden() {
		return isInputType(JSFGlobals.INPUT_HIDDEN);
	}

	/**
	 * @return isInputType(JSFGlobals.INPUT_MULTIBOX)
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#isInputMultibox()
	 */
	@Override
	protected boolean handleIsInputMultibox() {
		return isInputType(JSFGlobals.INPUT_MULTIBOX);
	}

	/**
	 * @return isInputType(JSFGlobals.INPUT_RADIO)
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#isInputRadio()
	 */
	@Override
	protected boolean handleIsInputRadio() {
		return isInputType(JSFGlobals.INPUT_RADIO);
	}

	/**
	 * @return isInputType(JSFGlobals.INPUT_PASSWORD)
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#isInputSecret()
	 */
	@Override
	protected boolean handleIsInputSecret() {
		return isInputType(JSFGlobals.INPUT_PASSWORD);
	}

	/**
	 * @return isInputType(JSFGlobals.INPUT_SELECT)
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#isInputSelect()
	 */
	@Override
	protected boolean handleIsInputSelect() {
		return isInputType(JSFGlobals.INPUT_SELECT);
	}

	/**
	 * @return isInputTable
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#isInputTable()
	 */
	@Override
	protected boolean handleIsInputTable() {
		return getInputTableIdentifierColumns().length() > 0
				|| isInputType(JSFGlobals.INPUT_TABLE);
	}

	/**
	 * @return isInputType(JSFGlobals.INPUT_TEXT)
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#isInputText()
	 */
	@Override
	protected boolean handleIsInputText() {
		return isInputType(JSFGlobals.INPUT_TEXT);
	}

	/**
	 * @return isInputType(JSFGlobals.INPUT_TEXTAREA)
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#isInputTextarea()
	 */
	@Override
	protected boolean handleIsInputTextarea() {
		return isInputType(JSFGlobals.INPUT_TEXTAREA);
	}

	/**
	 * @return isPageableTable
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#isPageableTable()
	 */
	@Override
	protected boolean handleIsPageableTable() {
		final Object value = findTaggedValue(JSFProfile.TAGGEDVALUE_TABLE_PAGEABLE);
		return Boolean.valueOf(ObjectUtils.toString(value)).booleanValue();
	}

	/**
	 * @return isInputType(JSFGlobals.PLAIN_TEXT)
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#isPlaintext()
	 */
	@Override
	protected boolean handleIsPlaintext() {
		return isInputType(JSFGlobals.PLAIN_TEXT);
	}

	/**
	 * @return JSFUtils.isReadOnly(this)
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#isReadOnly()
	 */
	@Override
	protected boolean handleIsReadOnly() {
		return JSFUtils.isReadOnly(this);
	}

	/**
	 * @return reset
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#isReset()
	 */
	@Override
	protected boolean handleIsReset() {
		boolean reset = Boolean
				.valueOf(
						ObjectUtils
								.toString(findTaggedValue(JSFProfile.TAGGEDVALUE_INPUT_RESET)))
				.booleanValue();
		if (!reset) {
			final JSFAction action = (JSFAction) getAction();
			reset = action != null && action.isFormReset();
		}
		return reset;
	}

	/**
	 * @return isSelectable
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#isSelectable()
	 */
	@Override
	protected boolean handleIsSelectable() {
		boolean selectable = isInputMultibox() || isInputSelect()
				|| isInputRadio()
				|| (getType() != null && getType().isEnumeration());
		if (!selectable && isActionParameter()) {
			final ClassifierFacade type = getType();

			if (type != null) {
				final String name = getName();
				final String typeName = type.getFullyQualifiedName();

				// - if the parameter is not selectable but on a targetting page
				// it IS selectable we must
				// allow the user to set the backing list too
				final Collection<FrontEndView> views = getAction().getUseCase()
						.getViews();
				for (final Iterator<FrontEndView> iterator = views.iterator(); iterator
						.hasNext() && !selectable;) {
					final FrontEndView view = iterator.next();
					final Collection<FrontEndParameter> parameters = view
							.getAllFormFields();

					for (final Iterator<FrontEndParameter> parameterIterator = parameters
							.iterator(); parameterIterator.hasNext()
							&& !selectable;) {
						final FrontEndParameter object = parameterIterator
								.next();

						if (object instanceof JSFParameter) {
							final JSFParameter parameter = (JSFParameter) object;
							final String parameterName = parameter.getName();
							final ClassifierFacade parameterType = parameter
									.getType();
							if (parameterType != null) {
								final String parameterTypeName = parameterType
										.getFullyQualifiedName();
								if (name.equals(parameterName)
										&& typeName.equals(parameterTypeName)) {
									selectable |= parameter.isInputMultibox()
											|| parameter.isInputSelect()
											|| parameter.isInputRadio();
								}
							}
							final Collection<JSFAttribute> attributes = parameter
									.getAttributes();
							if (attributes != null) {
								for (final JSFAttribute attribute : attributes) {
									if (name.equals(attribute.getName())) {
										selectable |= attribute
												.isInputMultibox()
												|| attribute.isInputSelect()
												|| attribute.isInputRadio();
									}
								}
							}
						}
					}
				}
			}
		}

		if (!selectable && isControllerOperationArgument()) {
			final String name = getName();
			final Collection actions = getControllerOperation()
					.getDeferringActions();
			for (final Iterator actionIterator = actions.iterator(); actionIterator
					.hasNext();) {
				final JSFAction action = (JSFAction) actionIterator.next();
				final Collection<FrontEndParameter> formFields = action
						.getFormFields();
				for (final Iterator<FrontEndParameter> fieldIterator = formFields
						.iterator(); fieldIterator.hasNext() && !selectable;) {
					final FrontEndParameter object = fieldIterator.next();
					if (object instanceof JSFParameter) {
						final JSFParameter parameter = (JSFParameter) object;
						if (!parameter.equals(this)) {
							if (name.equals(parameter.getName())) {
								selectable |= parameter.isSelectable();
							}
						}
						final Collection<JSFAttribute> attributes = parameter
								.getAttributes();
						if (attributes != null) {
							for (final JSFAttribute attribute : attributes) {
								if (name.equals(attribute.getName())) {
									selectable |= attribute.isInputMultibox()
											|| attribute.isInputSelect()
											|| attribute.isInputRadio();
								}
							}
						}
					}
				}
			}
		}
		return selectable;
	}

	/**
	 * @return JSFUtils.isStrictDateFormat((ModelElementFacade)this.THIS())
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#isStrictDateFormat()
	 */
	@Override
	protected boolean handleIsStrictDateFormat() {
		return JSFUtils.isStrictDateFormat((ModelElementFacade) THIS());
	}

	/**
	 * @return validationRequired
	 * @see org.andromda.cartridges.jsf.metafacades.JSFParameter#isValidationRequired()
	 */
	@Override
	protected boolean handleIsValidationRequired() {
		boolean required = !getValidatorTypes().isEmpty();
		if (!required) {
			// - look for any attributes
			for (final Iterator<JSFAttribute> iterator = getAttributes()
					.iterator(); iterator.hasNext();) {
				required = !iterator.next().getValidatorTypes().isEmpty();
				if (required) {
					break;
				}
			}

			// - look for any table columns
			if (!required) {
				for (final Iterator iterator = getTableColumns().iterator(); iterator
						.hasNext();) {
					final Object object = iterator.next();
					if (object instanceof JSFAttribute) {
						final JSFAttribute attribute = (JSFAttribute) object;
						required = !attribute.getValidatorTypes().isEmpty();
						if (required) {
							break;
						}
					}
				}
			}
		}
		return required;
	}

	/**
	 * Indicates whether or not this parameter is of the given input type.
	 * 
	 * @param inputType
	 *            the name of the input type to check for.
	 * @return true/false
	 */
	private boolean isInputType(final String inputType) {
		return inputType.equalsIgnoreCase(getInputType());
	}

	/**
	 * Indicates whether or not we should normalize messages.
	 * 
	 * @return true/false
	 */
	private boolean isNormalizeMessages() {
		final String normalizeMessages = (String) getConfiguredProperty(JSFGlobals.NORMALIZE_MESSAGES);
		return Boolean.valueOf(normalizeMessages).booleanValue();
	}

	/**
	 * Overridden to have the same behavior as bpm4struts.
	 * 
	 * @see org.andromda.metafacades.uml.ParameterFacade#isRequired()
	 */
	@Override
	public boolean isRequired() {
		if ("org.omg.uml.foundation.core".equals(metaObject.getClass()
				.getPackage().getName())) {
			// if uml 1.4, keep the old behavior (like bpm4struts)
			final Object value = findTaggedValue(JSFProfile.TAGGEDVALUE_INPUT_REQUIRED);
			return Boolean.valueOf(ObjectUtils.toString(value)).booleanValue();
		} else {
			// if >= uml 2, default behavior
			return super.isRequired();
		}
	}

	/**
	 * Overridden to make sure it's not an inputTable.
	 * 
	 * @see org.andromda.metafacades.uml.FrontEndParameter#isTable()
	 */
	@Override
	public boolean isTable() {
		return (super.isTable() || isPageableTable()) && !isSelectable()
				&& !isInputTable() && !isInputHidden();
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("JSFParameterLogicImpl [");
		if (getName() != null) {
			builder.append("NAME=");
			builder.append(getName());
			builder.append(", ");
		}
		if (getTableColumns() != null) {
			builder.append("getTableColumns()=");
			builder.append(toString(getTableColumns(), maxLen));
			builder.append(", ");
		}
		if (getView() != null) {
			builder.append("getView()=");
			builder.append(getView());
			builder.append(", ");
		}
		builder.append("isJSFParameterMetaType()=");
		builder.append(isJSFParameterMetaType());
		builder.append(", isInputTable()=");
		builder.append(isInputTable());
		builder.append(", ");
		if (getAccordionHeaderNames() != null) {
			builder.append("getAccordionHeaderNames()=");
			builder.append(toString(getAccordionHeaderNames(), maxLen));
			builder.append(", ");
		}
		if (getTableHyperlinkActions() != null) {
			builder.append("getTableHyperlinkActions()=");
			builder.append(toString(getTableHyperlinkActions(), maxLen));
			builder.append(", ");
		}
		if (getTableFormActions() != null) {
			builder.append("getTableFormActions()=");
			builder.append(toString(getTableFormActions(), maxLen));
			builder.append(", ");
		}
		if (getAction() != null) {
			builder.append("getAction()=");
			builder.append(getAction());
			builder.append(", ");
		}
		if (getControllerOperation() != null) {
			builder.append("getControllerOperation()=");
			builder.append(getControllerOperation());
			builder.append(", ");
		}
		if (getTableAttributeNames() != null) {
			builder.append("getTableAttributeNames()=");
			builder.append(toString(getTableAttributeNames(), maxLen));
			builder.append(", ");
		}
		if (getNavigableAssociationEnds() != null) {
			builder.append("getNavigableAssociationEnds");
			builder.append(toString(getNavigableAssociationEnds(), maxLen));
			builder.append(", ");
		}
		builder.append("isActionParameter()=");
		builder.append(isActionParameter());
		builder.append(", ");
		if (getLabel() != null) {
			builder.append("getLabel()=");
			builder.append(getLabel());
			builder.append(", ");
		}
		if (getTaggedValues() != null) {
			builder.append("getTaggedValues()=");
			builder.append(toString(getTaggedValues(), maxLen));
			builder.append(", ");
		}
		if (getGetterName() != null) {
			builder.append("getGetterName()=");
			builder.append(getGetterName());
			builder.append(", ");
		}
		final ClassifierFacade type = getType();
		if (type != null) {
			builder.append("type=[");
			builder.append("name=");
			builder.append(type.getName());
			builder.append(", array=");
			builder.append(type.isArrayType());
			builder.append(", assocEnds=");
			builder.append(toString(type.getNavigableConnectingEnds(), maxLen));
			builder.append("]");

		}
		builder.append("]");
		return builder.toString();
	}

	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext()
				&& i < maxLen; i++) {
			if (i > 0) {
				builder.append(", ");
			}
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}

	@Override
	protected boolean handleIsInputAutocomplete() {
		return isInputType(JSFGlobals.INPUT_AUTOCOMPLETE);
	}

}