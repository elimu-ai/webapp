<content:title>
    <fmt:message key="activity" />
</content:title>

<content:section cssId="moduleActivityPage">
    <h4>Image matching</h4>
    
    <div class="col l6 offset-l3">
        <div class="previewContainer valignwrapper" style="position: relative;">
            <img src="<spring:url value='/static/img/device-nexus-5.png' />" alt="<fmt:message key="preview" />" />
            <div id="previewContentContainer" style="position: absolute; top: 15.15%; height: 66.75%; width: 100%; text-align: center;">
                <div id="previewContent" class="valign-wrapper" 
                     style="
                     position: relative;
                     width: 64%; 
                     height: 100%; 
                     display: inline-block; 
                     background-color: rgba(0,0,0, .05);
                     ">
                    
                    <div style="height: 33%;" class="valign-wrapper">
                        <div class="placeHolder"
                            data-imageurl="<spring:url value='/image/${image1.id}.${fn:toLowerCase(image1.imageType)}' />" 
                            data-dominantColor="${image1.dominantColor[0]},${image1.dominantColor[1]},${image1.dominantColor[2]}"
                            data-imageid="${image1.id}"
                            style="
                             width: 50%;
                             height: 100%;
                             background-repeat: no-repeat;
                            background-size: cover;
                            background-position: 0 50%;
                             ">
                            <div class="card">
                                <i class="material-icons large grey-text">grade</i>
                            </div>
                        </div>
                        <div  class="placeHolder"
                            data-imageurl="<spring:url value='/image/${image3.id}.${fn:toLowerCase(image3.imageType)}' />" 
                            data-dominantColor="${image3.dominantColor[0]},${image3.dominantColor[1]},${image3.dominantColor[2]}"
                            data-imageid="${image3.id}"
                            style="
                             width: 50%;
                             height: 100%;
                             background-repeat: no-repeat;
                            background-size: cover;
                            background-position: 0 50%;
                             ">
                            <div class="card">
                                <i class="material-icons large grey-text">grade</i>
                            </div>
                        </div>
                    </div>
                    <div style="height: 33%;" class="valign-wrapper">
                        <div  class="placeHolder"
                            data-imageurl="<spring:url value='/image/${image1.id}.${fn:toLowerCase(image1.imageType)}' />" 
                            data-dominantColor="${image1.dominantColor[0]},${image1.dominantColor[1]},${image1.dominantColor[2]}"
                            data-imageid="${image1.id}"
                            style="
                             width: 50%;
                             height: 100%;
                             background-repeat: no-repeat;
                            background-size: cover;
                            background-position: 0 50%;
                             ">
                            <div class="card">
                                <i class="material-icons large grey-text">grade</i>
                            </div>
                        </div>
                        <div  class="placeHolder"
                            data-imageurl="<spring:url value='/image/${image3.id}.${fn:toLowerCase(image3.imageType)}' />" 
                            data-dominantColor="${image3.dominantColor[0]},${image3.dominantColor[1]},${image3.dominantColor[2]}"
                            data-imageid="${image3.id}"
                            style="
                             width: 50%;
                             height: 100%;
                             background-repeat: no-repeat;
                            background-size: cover;
                            background-position: 0 50%;
                             ">
                            <div class="card">
                                <i class="material-icons large grey-text">grade</i>
                            </div>
                        </div>
                    </div>
                    <div style="height: 33%;" class="valign-wrapper">
                        <div  class="placeHolder"
                            data-imageurl="<spring:url value='/image/${image2.id}.${fn:toLowerCase(image2.imageType)}' />" 
                            data-dominantColor="${image2.dominantColor[0]},${image2.dominantColor[1]},${image2.dominantColor[2]}"
                            data-imageid="${image2.id}"
                            style="
                             width: 50%;
                             height: 100%;
                             background-repeat: no-repeat;
                            background-size: cover;
                            background-position: 0 50%;
                             ">
                            <div class="card">
                                <i class="material-icons large grey-text">grade</i>
                            </div>
                        </div>
                        <div  class="placeHolder"
                            data-imageurl="<spring:url value='/image/${image2.id}.${fn:toLowerCase(image2.imageType)}' />" 
                            data-dominantColor="${image2.dominantColor[0]},${image2.dominantColor[1]},${image2.dominantColor[2]}"
                            data-imageid="${image2.id}"
                            style="
                             width: 50%;
                             height: 100%;
                             background-repeat: no-repeat;
                            background-size: cover;
                            background-position: 0 50%;
                             ">
                            <div class="card">
                                <i class="material-icons large grey-text">grade</i>
                            </div>
                        </div>
                    </div>
                    
                </div>
            </div>
        </div>
    </div>
            
    <script>
        $(function() {
            var selectionId1 = 0;
            var selectionId2 = 0;
            $('.placeHolder').on("click", function() {
                console.debug('.placeHolder on click');
                $(this).find('.card').fadeOut();
                $(this).css("background-image", "url(" + $(this).attr("data-imageurl") + ")");
                $('nav').removeClass("black");
                $('nav').css("background-color", "rgb(" + $(this).attr("data-dominantcolor") + ")");
                
                if (selectionId1 == 0) {
                    selectionId1 = $(this).attr("data-imageid");
                } else if (selectionId2 == 0) {
                    selectionId2 = $(this).attr("data-imageid");
                    
                    setTimeout(function() {
                        console.debug("1000 ms");
                        
                        if (selectionId1 == selectionId2) {
                            // Correct pair
                            $('[data-imageid="' + selectionId1 + '"]').css("background-image", "none");
                        } else {
                            $('[data-imageid="' + selectionId1 + '"]').find('.card').fadeIn();
                            $('[data-imageid="' + selectionId2 + '"]').find('.card').fadeIn();
                            $('[data-imageid="' + selectionId1 + '"]').css("background-image", "none");
                            $('[data-imageid="' + selectionId2 + '"]').css("background-image", "none");
                        }

                        selectionId1 = 0;
                        selectionId2 = 0;
                    }, 1000);
                }
                
            });
        });
    </script>
</content:section>
