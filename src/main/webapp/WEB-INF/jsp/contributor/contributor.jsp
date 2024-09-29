<content:title>
    <c:choose>
        <c:when test="${not empty contributor2.firstName}">
            <c:out value="${contributor2.firstName}" />&nbsp;<c:out value="${contributor2.lastName}" />
        </c:when>
        <c:when test="${not empty contributor2.providerIdWeb3}">
            ${fn:substring(contributor2.providerIdWeb3, 0, 6)}...${fn:substring(contributor2.providerIdWeb3, 38, 42)}
        </c:when>
    </c:choose>
</content:title>

<content:section cssId="contributorStoryBooksPage">
    <%@ include file="/WEB-INF/jsp/contributor/contributor-summarized.jsp" %>
    
    <ul class="tabs tabs-transparent deep-purple lighten-1 z-depth-1" style="border-radius: 8px;">
        <li class="tab"><a class="active" href="#storybooks">Storybooks (${storyBookContributionsCount} + ${storyBookPeerReviewsCount})</a></li>
        <li class="tab"><a href="#audios">Audios (${audioContributionsCount} + ${audioPeerReviewsCount})</a></li>
        <li class="tab"><a href="#words">Words (${wordContributionsCount} + ${wordPeerReviewsCount})</a></li>
        <li class="tab"><a href="#numbers">Numbers (${numberContributionsCount} + ${numberPeerReviewsCount})</a></li>
    </ul>
    <div id="storybooks">
        <%@ include file="/WEB-INF/jsp/contributor/contributor-storybooks.jsp" %>
    </div>
    <div id="words">
        <%@ include file="/WEB-INF/jsp/contributor/contributor-words.jsp" %>
    </div>
    <div id="numbers">
        <%@ include file="/WEB-INF/jsp/contributor/contributor-numbers.jsp" %>
    </div>
</content:section>
