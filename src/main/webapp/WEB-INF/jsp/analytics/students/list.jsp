<content:title>
    Students (${fn:length(students)})
</content:title>

<content:banner>
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
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
