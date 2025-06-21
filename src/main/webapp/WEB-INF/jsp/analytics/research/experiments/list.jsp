<content:title>
    Research Experiments (${fn:length(researchExperiments)})
</content:title>

<content:section cssId="experimentListPage">
    <div class="row">
        <h5><content:gettitle /></h5>
    </div>
    <div class="row">
        <table class="bordered highlight">
            <thead>
                <th>Experiment ID</th>
                <th>Theory</th>
            </thead>
            <tbody>
                <c:forEach var="researchExperiment" items="${researchExperiments}">
                    <tr class="experiment">
                        <td>
                            <a class="experimentLink" href="<spring:url value='/analytics/research/experiments/${researchExperiment}' />">
                                <code>${researchExperiment}</code>
                            </a>
                        </td>
                        <td>
                            <blockquote>${researchExperiment.theory}</blockquote>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</content:section>
