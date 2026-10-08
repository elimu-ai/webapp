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
    <div class="row">
        <div class="col s12 left-align">
            <a id="createButton" href="https://github.com/elimu-ai/model/blob/main/src/main/java/ai/elimu/model/v2/enums/analytics/research/ResearchExperiment.java" target="_blank" class="btn waves-effect waves-light"><i class="material-icons left">add</i>Add experiment</a>
        </div>
    </div>
</content:section>
