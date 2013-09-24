package org.andromda.cartridges.jsf.metafacades;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.andromda.cartridges.jsf.JSFGlobals;
import org.andromda.cartridges.jsf.JSFProfile;
import org.andromda.cartridges.jsf.JSFUtils;
import org.andromda.metafacades.uml.AttributeFacade;
import org.andromda.metafacades.uml.FrontEndAction;
import org.andromda.metafacades.uml.FrontEndForward;
import org.andromda.metafacades.uml.FrontEndParameter;
import org.andromda.metafacades.uml.ModelElementFacade;
import org.andromda.metafacades.uml.UseCaseFacade;
import org.andromda.utils.StringUtilsHelper;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

/**
 * MetafacadeLogic implementation for
 * org.andromda.cartridges.jsf.metafacades.JSFView.
 * 
 * @see org.andromda.cartridges.jsf.metafacades.JSFView
 */
public class JSFViewLogicImpl extends JSFViewLogic {
	private static final long serialVersionUID = 34L;

	/**
	 * @param metaObject
	 * @param context
	 */
	public JSFViewLogicImpl(final Object metaObject, final String context) {
		super(metaObject, context);
	}

	@Override
	protected List handleFilterTablesByZone(final String zone) {
		final List<FrontEndParameter> tables = getTables();
		final List<FrontEndParameter> filtered_table = new ArrayList<FrontEndParameter>();
		for (final FrontEndParameter frontEndParameter : tables) {
			if (frontEndParameter instanceof JSFParameter) {
				final JSFParameter jsfParameter = (JSFParameter) frontEndParameter;
				if (StringUtils.isBlank(zone)) {
					if (jsfParameter.getZone().equals("default")) {
						filtered_table.add(jsfParameter);
					}
				} else if (jsfParameter.getZone().trim()
						.equalsIgnoreCase(zone.trim())) {
					filtered_table.add(jsfParameter);
				}

			}
		}
		return filtered_table;
	}

	/**
	 * @return actionForwards
	 * @see org.andromda.cartridges.jsf.metafacades.JSFViewLogic#getActionForwards()
	 */
	@Override
	protected List<JSFForward> handleGetActionForwards() {
		final List<JSFForward> actionForwards = new ArrayList<JSFForward>(
				getForwards());
		for (final Iterator<JSFForward> iterator = actionForwards.iterator(); iterator
				.hasNext();) {
			if (!(iterator.next() instanceof JSFAction)) {
				iterator.remove();
			}
		}
		return actionForwards;
	}

	/**
	 * @return backingValueVariables
	 * @see org.andromda.cartridges.jsf.metafacades.JSFView#getBackingValueVariables()
	 */
	@Override
	protected List<JSFParameter> handleGetBackingValueVariables() {
		final Map<String, JSFParameter> variables = new LinkedHashMap<String, JSFParameter>();
		for (final FrontEndParameter frontEndParameter : getAllActionParameters()) {
			if (frontEndParameter instanceof JSFParameter) {
				final JSFParameter parameter = (JSFParameter) frontEndParameter;
				final String parameterName = parameter.getName();
				final Collection<AttributeFacade> attributes = parameter
						.getAttributes();
				if (parameter.isBackingValueRequired()
						|| parameter.isSelectable()) {
					if (parameter.isBackingValueRequired()
							|| parameter.isSelectable()) {
						variables.put(parameterName, parameter);
					}
				} else {
					boolean hasBackingValue = false;
					for (final AttributeFacade attribute : attributes) {
						final JSFAttribute jsfAttribute = (JSFAttribute) attribute;
						if (jsfAttribute.isSelectable(parameter)
								|| jsfAttribute
										.isBackingValueRequired(parameter)) {
							hasBackingValue = true;
							break;
						}
					}
					if (hasBackingValue) {
						variables.put(parameterName, parameter);
					}
				}
			}
		}
		return new ArrayList<JSFParameter>(variables.values());
	}

	/**
	 * @return getMessageKey() + '.' +
	 *         JSFGlobals.DOCUMENTATION_MESSAGE_KEY_SUFFIX
	 * @see org.andromda.cartridges.jsf.metafacades.JSFView#getDocumentationKey()
	 */
	@Override
	protected String handleGetDocumentationKey() {
		return getMessageKey() + '.'
				+ JSFGlobals.DOCUMENTATION_MESSAGE_KEY_SUFFIX;
	}

	/**
	 * @return documentationValue
	 * @see org.andromda.cartridges.jsf.metafacades.JSFView#getDocumentationValue()
	 */
	@Override
	protected String handleGetDocumentationValue() {
		final String value = StringUtilsHelper
				.toResourceMessage(getDocumentation(""));
		return value == null ? "" : value;
	}

	/**
	 * @see org.andromda.cartridges.jsf.metafacades.JSFViewLogic#handleGetFormActions()
	 */
	@Override
	protected List<FrontEndAction> handleGetFormActions() {
		final List<FrontEndAction> actions = new ArrayList<FrontEndAction>(
				getActions());
		for (final Iterator<FrontEndAction> iterator = actions.iterator(); iterator
				.hasNext();) {
			final FrontEndAction action = iterator.next();
			if (action.getFormFields().isEmpty()) {
				iterator.remove();
			}
		}
		return actions;
	}

	/**
	 * @return formKey
	 * @see org.andromda.cartridges.jsf.metafacades.JSFView#getFormKey()
	 */
	@Override
	protected String handleGetFormKey() {
		final Object formKeyValue = findTaggedValue(JSFProfile.TAGGEDVALUE_ACTION_FORM_KEY);
		return formKeyValue == null ? ObjectUtils
				.toString(getConfiguredProperty(JSFGlobals.ACTION_FORM_KEY))
				: String.valueOf(formKeyValue);
	}

	/**
	 * @return forwards
	 * @see org.andromda.cartridges.jsf.metafacades.JSFView#getForwards()
	 */
	@Override
	protected List<ModelElementFacade> handleGetForwards() {
		final Map<String, ModelElementFacade> forwards = new LinkedHashMap<String, ModelElementFacade>();
		for (final FrontEndAction action : getActions()) {
			if (action != null && !action.isUseCaseStart()) {
				for (final FrontEndForward forward : action.getActionForwards()) {
					if (forward instanceof JSFForward) {
						forwards.put(((JSFForward) forward).getName(), forward);
					} else if (forward instanceof JSFAction) {
						forwards.put(((JSFAction) forward).getName(), forward);
					}
				}
			}
		}
		return new ArrayList<ModelElementFacade>(forwards.values());
	}

	/**
	 * @return toWebResourceName(this.getUseCase().getName() + "-" +
	 *         this.getName())
	 * @see org.andromda.cartridges.jsf.metafacades.JSFView#getFromOutcome()
	 */
	@Override
	protected String handleGetFromOutcome() {
		String prefix = "";
		if (isPopup()) {
			final String ajaxLibrary = (String) getConfiguredProperty("ajaxLibrary");
			if ("trinidad".equalsIgnoreCase(ajaxLibrary)) {
				prefix = "dialog:";
			}
		}
		final StringBuilder formOutcome = new StringBuilder();
		formOutcome.append(getUseCase().getName());
		formOutcome.append("-");
		formOutcome.append(getName());
		return prefix + JSFUtils.toWebResourceName(formOutcome.toString());
	}

	/**
	 * @return populatorName
	 * @see org.andromda.cartridges.jsf.metafacades.JSFViewLogic#getFullyQualifiedPopulator()
	 */
	@Override
	protected String handleGetFullyQualifiedPopulator() {
		final StringBuilder name = new StringBuilder();
		final String packageName = this.getPackageName();
		if (StringUtils.isNotBlank(packageName)) {
			name.append(packageName);
			name.append('.');
		}
		name.append(getPopulator());
		return name.toString();
	}

	/**
	 * @return messageKey
	 * @see org.andromda.cartridges.jsf.metafacades.JSFView#getMessageKey()
	 */
	@Override
	protected String handleGetMessageKey() {
		final StringBuilder messageKey = new StringBuilder();

		if (!isNormalizeMessages()) {
			final UseCaseFacade useCase = getUseCase();
			if (useCase != null) {
				messageKey.append(StringUtilsHelper
						.toResourceMessageKey(useCase.getName()));
				messageKey.append('.');
			}
		}

		messageKey.append(StringUtilsHelper.toResourceMessageKey(getName()));
		return messageKey.toString();
	}

	/**
	 * @see org.andromda.cartridges.jsf.metafacades.JSFViewLogic#handleGetMessageValue()
	 */
	@Override
	protected String handleGetMessageValue() {
		return StringUtilsHelper.toPhrase(getName());
	}

	/**
	 * @return path
	 * @see org.andromda.cartridges.jsf.metafacades.JSFView#getPath()
	 */
	@Override
	protected String handleGetPath() {
		final StringBuilder path = new StringBuilder();
		final String packageName = this.getPackageName();
		if (StringUtils.isNotBlank(packageName)) {
			path.append(packageName + '.');
		}
		path.append(JSFUtils.toWebResourceName(
				StringUtils.trimToEmpty(getName())).replace('.', '/'));
		return '/' + path.toString().replace('.', '/');
	}

	/**
	 * @return populator
	 * @see org.andromda.cartridges.jsf.metafacades.JSFViewLogic#getPopulator()
	 */
	@Override
	protected String handleGetPopulator() {
		return ObjectUtils.toString(
				getConfiguredProperty(JSFGlobals.VIEW_POPULATOR_PATTERN))
				.replaceAll("\\{0\\}",
						StringUtilsHelper.upperCamelCaseName(getName()));
	}

	/**
	 * @return getFullyQualifiedPopulator().replace('.', '/')
	 * @see org.andromda.cartridges.jsf.metafacades.JSFView#getPopulatorPath()
	 */
	@Override
	protected String handleGetPopulatorPath() {
		return getFullyQualifiedPopulator().replace('.', '/');
	}

	@Override
	protected Map handleGetTabLabelsById() {
		final Pattern columnTabNamePattern = Pattern
				.compile("tab[0-9][0-9]?\\-column[0-9]");
		final Object tabLabelsObj = findTaggedValue(JSFProfile.TAGGEDVALUE_TAB_LABELS);
		final SortedSet<String> tabNames = new TreeSet<String>();
		final Collection<String> zones = getZones();
		for (final String zoneName : zones) {
			final String normZoneName = zoneName.trim().toLowerCase();
			if (normZoneName.startsWith("tab")) {
				if (columnTabNamePattern.matcher(normZoneName).matches()) {
					String tabName = normZoneName.split("-")[0];
					tabNames.add(tabName);
				} else {
					tabNames.add(normZoneName);
				}
			}
		}

		final Collection<String> tabLabelColl = new ArrayList<String>();
		if (tabLabelsObj instanceof String) {
			final String tabLabels = (String) tabLabelsObj;
			if (StringUtils.isNotBlank(tabLabels)) {

				final String[] tabLabelArray = tabLabels.split(",");
				for (final String tabLabel : tabLabelArray) {
					tabLabelColl.add(tabLabel.trim());
				}
			}
		}
		final Map<String, String> tabLabelsById = new LinkedHashMap<String, String>();
		final Iterator<String> labelIterator = tabLabelColl.iterator();

		for (final String tabName : tabNames) {
			String tabLabel;
			if (labelIterator.hasNext()) {
				tabLabel = labelIterator.next();
			} else {
				tabLabel = tabName;
			}
			tabLabelsById.put(tabName, tabLabel);
		}

		return tabLabelsById;
	}

	/**
	 * @return tables
	 * @see org.andromda.cartridges.jsf.metafacades.JSFAction#isTableLink()
	 */
	protected List<JSFParameter> handleGetTables() {
		final List<JSFParameter> tables = new ArrayList<JSFParameter>();
		final List<FrontEndParameter> variables = getVariables();
		for (final FrontEndParameter parameter : variables) {
			if (parameter instanceof JSFParameter) {
				final JSFParameter variable = (JSFParameter) parameter;
				if (variable.isTable()) {
					tables.add(variable);
				}
			}
		}
		return tables;
	}

	/**
	 * @return getMessageKey() + '.' + JSFGlobals.TITLE_MESSAGE_KEY_SUFFIX
	 * @see org.andromda.cartridges.jsf.metafacades.JSFView#getTitleKey()
	 */
	@Override
	protected String handleGetTitleKey() {
		return getMessageKey() + '.' + JSFGlobals.TITLE_MESSAGE_KEY_SUFFIX;
	}

	/**
	 * @return toPhrase(getName())
	 * @see org.andromda.cartridges.jsf.metafacades.JSFView#getTitleValue()
	 */
	@Override
	protected String handleGetTitleValue() {
		return StringUtilsHelper.toPhrase(getName());
	}

	@Override
	protected Collection<String> handleGetZones() {
		final Set<String> zones = new TreeSet<String>();
		for (final FrontEndParameter fp : getTables()) {
			if (fp instanceof JSFParameter) {
				final JSFParameter jsffp = (JSFParameter) fp;
				zones.add(jsffp.getZone());
			}
		}
		for (final FrontEndAction fp : getActions()) {
			for (final FrontEndParameter p : fp.getParameters()) {
				if (fp instanceof JSFParameter) {
					final JSFParameter jsffp = (JSFParameter) p;
					zones.add(jsffp.getZone());
				}
			}
			if (fp instanceof JSFAction) {
				final JSFAction jsfa = (JSFAction) fp;
				zones.add(jsfa.getZone());
			}
		}
		for (final FrontEndParameter fp : getVariables()) {
			if (fp instanceof JSFParameter) {
				final JSFParameter jsffp = (JSFParameter) fp;
				zones.add(jsffp.getZone());
			}
		}
		return zones;
	}

	/**
	 * @return hasNameOfUseCase
	 * @see org.andromda.cartridges.jsf.metafacades.JSFView#isHasNameOfUseCase()
	 */
	@Override
	protected boolean handleIsHasNameOfUseCase() {
		boolean sameName = false;
		final ModelElementFacade useCase = getUseCase();
		final String useCaseName = useCase != null ? useCase.getName() : null;
		if (useCaseName != null && useCaseName.equalsIgnoreCase(getName())) {
			sameName = true;
		}
		return sameName;
	}

	/**
	 * @return needsFileUpload
	 * @see org.andromda.cartridges.jsf.metafacades.JSFView#isNeedsFileUpload()
	 */
	@Override
	protected boolean handleIsNeedsFileUpload() {
		if (getAllActionParameters().size() == 0) {
			return false;
		}

		for (final FrontEndParameter feParameter : getAllActionParameters()) {
			if (feParameter instanceof JSFParameter) {
				final JSFParameter parameter = (JSFParameter) feParameter;
				if (parameter.isInputFile()) {
					return true;
				}
				if (parameter.isComplex()) {
					for (final Iterator attributes = parameter.getAttributes()
							.iterator(); attributes.hasNext();) {
						if (((JSFAttribute) attributes.next()).isInputFile()) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * @return nonTableVariablesPresent
	 * @see org.andromda.cartridges.jsf.metafacades.JSFView#isNonTableVariablesPresent()
	 */
	@Override
	protected boolean handleIsNonTableVariablesPresent() {
		boolean present = false;
		for (final FrontEndParameter variable : getVariables()) {
			if (!variable.isTable()) {
				present = true;
				break;
			}
		}
		return present;
	}

	/**
	 * @return !getFormActions().isEmpty() || !getVariables().isEmpty()
	 * @see org.andromda.cartridges.jsf.metafacades.JSFView#isPopulatorRequired()
	 */
	@Override
	protected boolean handleIsPopulatorRequired() {
		return !getFormActions().isEmpty() || !getVariables().isEmpty();
	}

	/**
	 * @return isPopup
	 * @see org.andromda.cartridges.jsf.metafacades.JSFView#isPopup()
	 */
	@Override
	protected boolean handleIsPopup() {
		return ObjectUtils.toString(
				findTaggedValue(JSFProfile.TAGGEDVALUE_VIEW_TYPE))
				.equalsIgnoreCase(JSFGlobals.VIEW_TYPE_POPUP);
	}

	/**
	 * @return validationRequired
	 * @see org.andromda.cartridges.jsf.metafacades.JSFView#isPopulatorRequired()
	 */
	@Override
	protected boolean handleIsValidationRequired() {
		boolean required = false;
		for (final FrontEndAction action : getActions()) {
			if (((JSFAction) action).isValidationRequired()) {
				required = true;
				break;
			}
		}
		return required;
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

	@Override
	protected Integer handleGetPopupHeight() {
		String popupDimStr = ObjectUtils
				.toString(findTaggedValue(JSFProfile.TAGGEDVALUE_VIEW_POPUP_GEOMETRY));
		Integer popupHeight = parsePopupDim(popupDimStr, true);
		if (popupHeight == null) {
			final String defaultPopupGeom = (String) getConfiguredProperty(JSFGlobals.POPUP_GEOMETRY);
			popupHeight = parsePopupDim(defaultPopupGeom, true);
		}
		return popupHeight;
	}

	private Integer parsePopupDim(String popupDimStr, boolean height) {
		Integer popupDimension = null;
		if (StringUtils.isNotBlank(popupDimStr)) {
			String trimmedString = popupDimStr.trim().toLowerCase();
			if ("large".equals(trimmedString)) {
				popupDimension = height ? 600 : 600;
			} else if ("medium".equals(trimmedString)) {
				popupDimension = height ? 300 : 300;
			} else if ("small".equals(trimmedString)) {
				popupDimension = height ? 150 : 200;
			} else if (popupDimStr.toLowerCase().contains("x")) {
				int widthOrHeightCompenent = (height ? 1 : 0);
				String heightStr = popupDimStr.toLowerCase().split("x")[widthOrHeightCompenent];
				popupDimension = Integer.parseInt(heightStr);
			}
		}
		return popupDimension;
	}

	@Override
	protected Integer handleGetPopupWidth() {
		String popupDimStr = ObjectUtils
				.toString(findTaggedValue(JSFProfile.TAGGEDVALUE_VIEW_POPUP_GEOMETRY));
		Integer popupHeight = parsePopupDim(popupDimStr, false);
		if (popupHeight == null) {
			final String defaultPopupGeom = (String) getConfiguredProperty(JSFGlobals.POPUP_GEOMETRY);
			popupHeight = parsePopupDim(defaultPopupGeom, false);
		}
		return popupHeight;
	}

}
