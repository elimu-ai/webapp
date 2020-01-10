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

    public Long getReferralId() {
        return referralId;
    }

    public void setReferralId(Long referralId) {
        this.referralId = referralId;
    }
}
