<content:title>
    <fmt:message key="issue.management" />
</content:title>

<content:section cssId="issueManagementPage">
    <p>Each team has its own Trello board:</p>
    <ul>
        <c:if test="${fn:contains(contributor.teams, 'ANALYTICS')}">
            <li>
                <a href="https://trello.com/b/MdPK2Q4i/team-analytics" target="_blank">Team: Analytics</a>
            </li>
        </c:if>
        <c:if test="${fn:contains(contributor.teams, 'CONTENT_CREATION')}">
            <li>
                <a href="https://trello.com/b/7K9nAaMB/team-content-creation" target="_blank">Team: Content Creation</a>
            </li>
        </c:if>
        <c:if test="${fn:contains(contributor.teams, 'DEVELOPMENT')}">
            <li>
                <a href="https://trello.com/b/les4HgKG/team-development" target="_blank">Team: Development</a>
            </li>
        </c:if>
        <c:if test="${fn:contains(contributor.teams, 'MARKETING')}">
            <li>
                <a href="https://trello.com/b/ss1ZzLWD/team-marketing" target="_blank">Team: Marketing</a>
            </li>
        </c:if>
        <c:if test="${fn:contains(contributor.teams, 'TESTING')}">
            <li>
                <a href="https://trello.com/b/kD1Kuh5M/team-testing" target="_blank">Team: Testing</a>
            </li>
        </c:if>
        <c:if test="${fn:contains(contributor.teams, 'TRANSLATION')}">
            <li>
                <a href="https://trello.com/b/zp4irrga/team-translation" target="_blank">Team: Translation</a>
            </li>
        </c:if>
    </ul>
</content:section>
