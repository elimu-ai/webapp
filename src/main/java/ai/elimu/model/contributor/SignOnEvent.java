package ai.elimu.model.contributor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import ai.elimu.model.enums.Provider;

@Entity
public class SignOnEvent extends ContributorEvent {
    
    private String serverName;
    
    @Enumerated(EnumType.STRING)
    private Provider provider;
    
    private String remoteAddress;
    
    @Column(length = 1000)
    private String userAgent;
    
    @Column(length=1000)
    private String referrer;

    // Campaign parameter 'utm_source'
    private String utmSource;

    // Campaign parameter 'utm_medium'
    private String utmMedium;

    // Campaign parameter 'utm_campaign'
    private String utmCampaign;

    // Campaign parameter 'utm_term'
    private String utmTerm;

    // Id of referring Contributor
    private Long referralId;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
    
    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getUtmSource() {
        return utmSource;
    }

    public void setUtmSource(String utmSource) {
        this.utmSource = utmSource;
    }

    public String getUtmMedium() {
        return utmMedium;
    }

    public void setUtmMedium(String utmMedium) {
        this.utmMedium = utmMedium;
    }

    public String getUtmCampaign() {
        return utmCampaign;
    }

    public void setUtmCampaign(String utmCampaign) {
        this.utmCampaign = utmCampaign;
    }

    public String getUtmTerm() {
        return utmTerm;
    }

    public void setUtmTerm(String utmTerm) {
        this.utmTerm = utmTerm;
    }

    public Long getReferralId() {
        return referralId;
    }

    public void setReferralId(Long referralId) {
        this.referralId = referralId;
    }
}
