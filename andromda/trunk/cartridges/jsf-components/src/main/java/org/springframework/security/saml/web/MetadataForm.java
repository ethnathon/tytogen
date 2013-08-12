/* Copyright 2011 Vladimir Schafer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.security.saml.web;

/**
 * Form able to store UI data related to metadata.
 */
public class MetadataForm {

	private boolean store;
	private String entityId;
	private String securityProfile;
	private String sslSecurityProfile;
	private String baseURL;
	private String alias;
	private boolean signMetadata = true;
	private String serializedMetadata;
	private String configuration;
	private String[] nameID;

	private String[] ssoBindings = new String[] {
			MetadataController.AllowedSSOBindings.SSO_ARTIFACT.toString(),
			MetadataController.AllowedSSOBindings.SSO_POST.toString(), };
	private String ssoDefaultBinding = MetadataController.AllowedSSOBindings.SSO_ARTIFACT
			.toString();

	private String signingKey;
	private String encryptionKey;
	private String tlsKey;

	private boolean local;

	private boolean includeSSO = true;
	private boolean includeHokSSO = false;

	private boolean includeDiscovery = true;
	private boolean includeDiscoveryExtension = false;

	private String customDiscoveryURL;
	private String customDiscoveryResponseURL;

	private boolean requestSigned = true;
	private boolean wantAssertionSigned;
	private boolean requireLogoutRequestSigned;
	private boolean requireLogoutResponseSigned;
	private boolean requireArtifactResolveSigned;

	public MetadataForm() {
	}

	public String getAlias() {
		return alias;
	}

	public String getBaseURL() {
		return baseURL;
	}

	public String getConfiguration() {
		return configuration;
	}

	public String getCustomDiscoveryResponseURL() {
		return customDiscoveryResponseURL;
	}

	public String getCustomDiscoveryURL() {
		return customDiscoveryURL;
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}

	public String getEntityId() {
		return entityId;
	}

	public String[] getNameID() {
		return nameID;
	}

	public String getSecurityProfile() {
		return securityProfile;
	}

	public String getSerializedMetadata() {
		return serializedMetadata;
	}

	public String getSigningKey() {
		return signingKey;
	}

	public String getSslSecurityProfile() {
		return sslSecurityProfile;
	}

	public String[] getSsoBindings() {
		return ssoBindings;
	}

	public String getSsoDefaultBinding() {
		return ssoDefaultBinding;
	}

	public String getTlsKey() {
		return tlsKey;
	}

	public boolean isIncludeDiscovery() {
		return includeDiscovery;
	}

	public boolean isIncludeDiscoveryExtension() {
		return includeDiscoveryExtension;
	}

	public boolean isIncludeHokSSO() {
		return includeHokSSO;
	}

	public boolean isIncludeSSO() {
		return includeSSO;
	}

	public boolean isLocal() {
		return local;
	}

	public boolean isRequestSigned() {
		return requestSigned;
	}

	public boolean isRequireArtifactResolveSigned() {
		return requireArtifactResolveSigned;
	}

	public boolean isRequireLogoutRequestSigned() {
		return requireLogoutRequestSigned;
	}

	public boolean isRequireLogoutResponseSigned() {
		return requireLogoutResponseSigned;
	}

	public boolean isSignMetadata() {
		return signMetadata;
	}

	public boolean isStore() {
		return store;
	}

	public boolean isWantAssertionSigned() {
		return wantAssertionSigned;
	}

	public void setAlias(final String alias) {
		this.alias = alias;
	}

	public void setBaseURL(final String baseURL) {
		this.baseURL = baseURL;
	}

	public void setConfiguration(final String configuration) {
		this.configuration = configuration;
	}

	public void setCustomDiscoveryResponseURL(
			final String customDiscoveryResponseURL) {
		this.customDiscoveryResponseURL = customDiscoveryResponseURL;
	}

	public void setCustomDiscoveryURL(final String customDiscoveryURL) {
		this.customDiscoveryURL = customDiscoveryURL;
	}

	public void setEncryptionKey(final String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}

	public void setEntityId(final String entityId) {
		this.entityId = entityId;
	}

	public void setIncludeDiscovery(final boolean includeDiscovery) {
		this.includeDiscovery = includeDiscovery;
	}

	public void setIncludeDiscoveryExtension(
			final boolean includeDiscoveryExtension) {
		this.includeDiscoveryExtension = includeDiscoveryExtension;
	}

	public void setIncludeHokSSO(final boolean includeHokSSO) {
		this.includeHokSSO = includeHokSSO;
	}

	public void setIncludeSSO(final boolean includeSSO) {
		this.includeSSO = includeSSO;
	}

	public void setLocal(final boolean local) {
		this.local = local;
	}

	public void setNameID(final String[] nameID) {
		this.nameID = nameID;
	}

	public void setRequestSigned(final boolean requestSigned) {
		this.requestSigned = requestSigned;
	}

	public void setRequireArtifactResolveSigned(
			final boolean requireArtifactResolveSigned) {
		this.requireArtifactResolveSigned = requireArtifactResolveSigned;
	}

	public void setRequireLogoutRequestSigned(
			final boolean requireLogoutRequestSigned) {
		this.requireLogoutRequestSigned = requireLogoutRequestSigned;
	}

	public void setRequireLogoutResponseSigned(
			final boolean requireLogoutResponseSigned) {
		this.requireLogoutResponseSigned = requireLogoutResponseSigned;
	}

	public void setSecurityProfile(final String securityProfile) {
		this.securityProfile = securityProfile;
	}

	public void setSerializedMetadata(final String serializedMetadata) {
		this.serializedMetadata = serializedMetadata;
	}

	public void setSigningKey(final String signingKey) {
		this.signingKey = signingKey;
	}

	public void setSignMetadata(final boolean signMetadata) {
		this.signMetadata = signMetadata;
	}

	public void setSslSecurityProfile(final String sslSecurityProfile) {
		this.sslSecurityProfile = sslSecurityProfile;
	}

	public void setSsoBindings(final String[] ssoBindings) {
		this.ssoBindings = ssoBindings;
	}

	public void setSsoDefaultBinding(final String ssoDefaultBinding) {
		this.ssoDefaultBinding = ssoDefaultBinding;
	}

	public void setStore(final boolean store) {
		this.store = store;
	}

	public void setTlsKey(final String tlsKey) {
		this.tlsKey = tlsKey;
	}

	public void setWantAssertionSigned(final boolean wantAssertionSigned) {
		this.wantAssertionSigned = wantAssertionSigned;
	}

}