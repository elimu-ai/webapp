<a href="<spring:url value='/contributor/${chipContributor.id}' />">
    <div class="chip">
        <c:choose>
            <c:when test="${not empty chipContributor.imageUrl}">
                <img src="${chipContributor.imageUrl}" />
            </c:when>
            <c:when test="${not empty chipContributor.providerIdWeb3}">
                <img src="https://effigy.im/a/<c:out value="${chipContributor.providerIdWeb3}" />.svg" />
            </c:when>
            <c:otherwise>
                <img src="<spring:url value='/static/img/placeholder.png' />" />
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${not empty chipContributor.firstName}">
                <c:out value="${chipContributor.firstName}" />&nbsp;<c:out value="${chipContributor.lastName}" />
            </c:when>
            <c:when test="${not empty chipContributor.providerIdWeb3}">
                ${fn:substring(chipContributor.providerIdWeb3, 0, 6)}...${fn:substring(chipContributor.providerIdWeb3, 38, 42)}
            </c:when>
        </c:choose>
    </div>
</a>
