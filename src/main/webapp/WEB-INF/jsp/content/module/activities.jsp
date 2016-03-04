<content:title>
    <fmt:message key="activities" />
</content:title>

<content:section cssId="moduleActivitiesPage">
    <h4><content:gettitle /></h4>
    
    <div class="card-panel">
        <table class="bordered highlight">
            <thead>
              <tr>
                  <th data-field="taskType">Task type</th>
                  <th data-field="literacySkill">Literacy skill</th>
                  <th data-field="numeracySkill">Numeracy skill</th>
                  <th data-field="wellBeingIndicator">Well-being indicator</th>
              </tr>
            </thead>

            <tbody>
              <tr>
                <td><a href="<spring:url value='/content/module/activities/1' />">Image matching</a> (no text)</td>
                <td>-</td>
                <td>-</td>
                <td>Concentration, memory</td>
              </tr>
              <tr>
                <td><a href="<spring:url value='/content/module/activities/1' />">Image matching</a> (no text)</td>
                <td>-</td>
                <td>-</td>
                <td>Concentration, memory</td>
              </tr>
              <tr>
                <td><a href="<spring:url value='/content/module/activities/1' />">Image matching</a> (with text)</td>
                <td>Vocabulary, sound identification</td>
                <td>-</td>
                <td>Concentration, memory</td>
              </tr>
              <tr>
                <td><a href="<spring:url value='/content/module/activities/1' />">Image matching</a> (with text)</td>
                <td>Vocabulary, sound identification</td>
                <td>-</td>
                <td>Concentration, memory</td>
              </tr>
              <tr>
                <td><a href="<spring:url value='/content/module/activities/1' />">Number matching</a></td>
                <td>-</td>
                <td>Number identification</td>
                <td>Concentration, memory</td>
              </tr>
            </tbody>
        </table>
    </div>
    
    <div class="fixed-action-btn" style="bottom: 2em; right: 2em;">
        <a href="<spring:url value='/content/activity/create' />" class="btn-floating btn-large red tooltipped" data-position="left" data-delay="50" data-tooltip="<fmt:message key="add.activity" />"><i class="material-icons">add</i></a>
    </div>
</content:section>
