<content:title>
    Students (${fn:length(students)})
</content:title>

<content:banner>
    <style>
        #index-banner {
            background-position: 0 75%;
        }
    </style>
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <div class="row center">
        <h1 class="header center white-text">Students<span style="color: #A59ADD;">(${fn:length(students)})</span></h1>
    </div>
</content:banner>

<content:section cssId="studentsListPage">
    <div class="section row">
        <table class="bordered highlight">
            <thead>
                <th>id</th>
                <th>android_id</th>
            </thead>
            <tbody>
                <c:forEach var="student" items="${students}">
                    <tr class="student">
                        <td>
                            <a href="<spring:url value='/analytics/students/${student.id}' />">
                                🎓 Student ${student.id}
                            </a>
                        </td>
                        <td>
                            <code>${student.androidId}</code>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</content:section>

<content:aside>
    <div class="card-panel deep-purple lighten-5">
        <h5 class="center">Sponsors 🫶🏽</h5>
        <p>
            To become a sponsor of one of these students, join our  
            <a href="https://sponsors.elimu.ai" target="_blank">sponsorship program</a>.
        </p>
    </div>
</content:aside>
