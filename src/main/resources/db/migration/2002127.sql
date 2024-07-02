# 2.2.127

# Convert existing Ethereum addresses to lower-case
UPDATE Contributor SET providerIdWeb3 = LOWER(providerIdWeb3);
