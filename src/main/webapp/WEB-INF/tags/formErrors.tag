<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<%@attribute name="modelAttribute" %>
<spring:hasBindErrors name="${modelAttribute}">
    <div id="errorPanel" class="card-panel red lighten-3">
        <c:forEach var="error" items="${errors.allErrors}">
            <li>
            <c:set var="errorMessageFoundInPropertiesFile" value="false" />
            <c:forEach var="code" items="${error.codes}">
                <c:if test="${!errorMessageFoundInPropertiesFile}">
                    <c:set var="errorMessageFoundInPropertiesFile" value="true" />
                    <c:choose>
                        <c:when test="${fn:length(error.arguments) == 3}">
                            <c:choose>
                                <c:when test="${code == 'Size'}">${error.field} should contain between ${error.arguments[2]} and ${error.arguments[1]} characters</c:when>
                                <c:otherwise>
                                    <c:set var="errorMessageFoundInPropertiesFile" value="false" />
                                </c:otherwise>                           
                            </c:choose>
                        </c:when>
                        <c:when test="${fn:length(error.arguments) == 2}">
                            <c:choose>
                                <c:when test="${code == 'hoge'}">hogehoge</c:when>
                                <c:otherwise>
                                    <c:set var="errorMessageFoundInPropertiesFile" value="false" />
                                </c:otherwise>                           
                            </c:choose>
                        </c:when>
                        <c:when test="${fn:length(error.arguments) == 1}">
                            <c:choose>
                                <c:when test="${code == 'Length'}">${error.field} is too long</c:when>
                                <c:when test="${code == 'NonUnique'}">${error.field} already exists</c:when>
                                <c:when test="${code == 'NotEmpty'}">${error.field} cannot be empty</c:when>
                                <c:when test="${code == 'NotNull'}">${error.field} must be provided</c:when>                                    
                                <c:when test="${code == 'typeMismatch.java.lang.Integer'}">${error.field} must be a number</c:when>                                    
                                <c:otherwise>
                                    <c:set var="errorMessageFoundInPropertiesFile" value="false" />
                                </c:otherwise>                           
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <%-- Without parameter --%>
                            <c:choose>
                                <c:when test="${code == 'Email'}">The e-mail address is invalid</c:when>
                                <c:when test="${code == 'Length.valueIpa'}">The IPA value cannot be longer than 3 characters</c:when>
                                <c:when test="${code == 'Length.valueSampa'}">The X-SAMPA value cannot be longer than 5 characters</c:when>
                                <c:when test="${code == 'NonUnique.number.value'}">The number already exists</c:when>                                    
                                <c:when test="${code == 'NonUnique.packageName'}">The package name already exists</c:when>                                    
                                <c:when test="${code == 'NonUnique.valueIpa'}">The IPA value already exists</c:when>                                    
                                <c:when test="${code == 'NonUnique.valueSampa'}">The X-SAMPA value already exists</c:when>                                    
                                <c:when test="${code == 'NotNull.bytes'}">File must be selected</c:when>                                    
                                <c:when test="${code == 'NotNull.motivation'}">Personal motivation must be provided</c:when>                                    
                                <c:when test="${code == 'NotNull.packageName'}">Package name must be provided</c:when>                                    
                                <c:when test="${code == 'NotNull.valueIpa'}">IPA value must be provided</c:when>                                    
                                <c:when test="${code == 'NotNull.valueSampa'}">X-SAMPA value must be provided</c:when>                                    
                                <c:when test="${code == 'TooLow.versionCode'}">The versionCode must be larger than the previous one</c:when>                                    
                                <c:when test="${code == 'TooLow.sdkVersion'}">The SdkVersion must be 26 or more</c:when>                                    
                                <c:when test="${code == 'packageName.mismatch'}">The packageName does not match.</c:when>                                    
                                <c:when test="${code == 'typeMismatch.applicationVersion.bytes'}">The file must be an APK file</c:when>                                    
                                <c:when test="${code == 'typeMismatch.image.bytes'}">Valid image formats: GIF/JPG/PNG</c:when>                                    
                                <c:when test="${code == 'typeMismatch.video.bytes'}">Valid video formats: M4V/MP4</c:when>                                    
                                <c:when test="${code == 'typeMismatch.thumbnail'}">Valid thumbnail format: PNG</c:when>                                    
                                <c:when test="${code == 'formatHint.Integer'}">Number (e.g. "5000")</c:when>                                    
                                <c:when test="${code == 'image.too.small'}">The image width must be at least 640px</c:when>                                    
                                <c:when test="${code == 'emoji.unicode.version'}">Only emojis up to Unicode version 9</c:when>                                    
                                <c:when test="${code == 'WordSpace'}">Spaces are not allowed</c:when>         
                                <c:otherwise>
                                    <c:set var="errorMessageFoundInPropertiesFile" value="false" />
                                </c:otherwise>                           
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </c:forEach>
            <c:if test="${!errorMessageFoundInPropertiesFile}">
                ${error}
            </c:if>
            </li>
        </c:forEach>
    </div>
 </spring:hasBindErrors>
