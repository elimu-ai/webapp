<c:if test="${fn:contains(header['User-Agent'], 'Firefox') && (applicationScope.configProperties['env'] != 'PROD')}">
    <%-- If a JavaScript error occurs, add the error message as an attribute to the <body> element. --%>
    <script>
        window.onerror = function(msg) {
            $("body").attr("data-js-error", msg);
        }
    </script>
</c:if>
