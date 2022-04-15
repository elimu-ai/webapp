<content:title>
    <fmt:message key='quality.basic.education.for.every.child.title' />
</content:title>

<content:banner>
    <br />
    <br />
    <br />
    <br />
    <div class="row center">
        <h1 class="header center white-text">elimu<span style="color: #A59ADD;">.ai</span></h1>
    </div>
    <div class="row center">
        <div class="col s2 m3 l4">&nbsp;</div>
        <h5 class="header col s8 m6 l4 light white-text" style="border-radius: 8px;"><fmt:message key="quality.basic.education.for.every.child" /></h5>
        <div class="col s2 m3 l4">&nbsp;</div>
    </div>
    <div class="row center">
        <a href="#contributeInfoContainer" id="join-button-top" class="btn-large waves-effect waves-light deep-purple lighten-2"><fmt:message key="contribute.now" /></a>
        <script>
            $(function() {
              $('#join-button-top').click(function(event) {
                  event.preventDefault();
                  $('html, body').animate({
                      scrollTop: $('[name="contributeInfoContainer"]').offset().top
                  }, 1000);
              });
            });
        </script>
    </div>
    <br />
</content:banner>

<content:section cssId="welcomePage">
    <div class="section">
        <!--   Icon Section   -->
        <div class="row">
          <div class="col s12 m4">
            <div class="icon-block">
              <h2 class="center"><i class="material-icons medium blue-grey-text">public</i></h2>
              <h4 class="center"><fmt:message key="purpose" /></h4>

              <p class="light center">
                  Worldwide, more than <a href="http://uis.unesco.org/sites/default/files/documents/fs46-more-than-half-children-not-learning-en-2017.pdf" target="_blank">387 million children</a> 
                  of primary school age are not achieving minimum proficiency levels in reading &amp; math.
              </p>
              <p class="light center"><fmt:message key="the.purpose.of.elimu.ai.is.to" /></p>
            </div>
          </div>
          
          <div class="col s12 m4">
            <div class="icon-block">
              <h2 class="center deep-purple-text"><i class="material-icons medium">school</i></h2>
              <h4 class="center"><fmt:message key="why?" /></h4>

              <p class="light center"><fmt:message key="the.word.elimu.is.swahili.for" />&nbsp;<fmt:message key="we.believe.that.a.free.quality.education.is" /></p>
            </div>
          </div>

          <div class="col s12 m4">
            <div class="icon-block">
              <h2 class="center"><i class="material-icons medium">stay_current_landscape</i></h2>
              <h4 class="center"><fmt:message key="solution" /></h4>

              <p class="light center">We develop Free and Open Source Software (FOSS) for teaching children the basics of reading, writing and arithmetic.</p>
              <p class="light center">An AI tutor guides each child through the necessary steps.</p>
            </div>
          </div>
        </div>
    </div>
    
    <div class="divider" style="margin-bottom: 3em;"></div>
            
    <div class="section">
        <div class="row">
            <div class="col s12 offset-m2 m4">
                <h5 class="header">Educational Apps & Games 🎲🕹</h5>
                <p>
                    A collection of educational Android apps teaches children basic 
                    literacy and numeracy in a fun and engaging way.    
                </p>
                <p>
                    Each child can learn <i>fully autonomously</i>, without depending on the availability of qualified teachers.  
                </p>
            </div>
            <div class="col s12 m4">
                <img src="https://github.blog/wp-content/uploads/2019/05/xprize-no-text.png?w=1200" style="border-radius: 8px;" />
            </div>
        </div>
        
        <div class="row">
            <div class="col s12 offset-m2 m4">
                <h5 class="header">Personalized Learning 🚀</h5>
                <p>
                    Each child is guided through the curriculum by our AI tutor <i>Nya</i> and her robot companion.
                </p>
                <p>
                    Nya automatically detects the current knowledge level of the child, and adapts the apps and content 
                    to best fit the child's current skill level.
                </p>
            </div>
            <div class="col s12 m4">
                <img src="https://user-images.githubusercontent.com/15718174/26873794-641bfbfc-4b7b-11e7-95ee-82b19b661ad5.png" style="border-radius: 8px;" />
            </div>
        </div>
        
        <div class="row">
            <div class="col s12 offset-m2 m4">
                <h5 class="header">Learning Platform 🧩</h5>
                <p>
                    The elimu.ai software is a <i>platform</i> of educational content and Android apps/games.
                </p>
                <p>
                    Each child is provided with a clear instructional path for learning literacy and numeracy as efficiently as possible.
                </p>
                <p>
                    As the child demonstrates mastery of prerequisite skills, the AI tutor gradually unlocks more advanced content.
                </p>
            </div>
            <div class="col s12 m4">
                <img src="https://user-images.githubusercontent.com/15718174/27299402-95bea44c-552c-11e7-84ab-217cdca758e4.gif" style="border-radius: 8px;" />
            </div>
        </div>
        
        <div class="row">
            <div class="col s12 offset-m2 m4">
                <h5 class="header">Free and Open Source Software (FOSS) 👩🏽‍💻</h5>
                <p>
                    Our goal is to quickly distribute the solution to as many children as possible, and we believe that using <i>open source</i> software will enable this.
                </p>
                <p>
                    The <a href="https://github.com/elimu-ai/appstore" target="_blank">elimu.ai Appstore</a> makes it easy to download and use the software.
                </p>
                <p>
                    Also, anyone is welcome to take the existing code and <a href="https://github.com/elimu-ai/wiki/blob/main/LOCALIZATION.md" target="_blank">adapt</a> it to their local needs.
                </p>
            </div>
            <div class="col s12 m4">
                <img src="https://user-images.githubusercontent.com/15718174/27515871-41872c60-59ae-11e7-9b2d-3ca886d0d7f2.png" style="border-radius: 8px;" />
            </div>
        </div>
    </div>
            
    <div class="divider" style="margin-bottom: 3em;"></div>
    
    <div class="section">
        <a name="contributeInfoContainer"></a>
        <div class="row center">
            <h4 class="header"><fmt:message key="contribute.now" /></h4>
            <p>There are several ways you can contribute:</p>
        </div>
        
        <div class="row">
            <div class="col s12 m3">
                <div class="icon-block center">
                    <img alt="ETH" src="https://raw.githubusercontent.com/trustwallet/assets/master/blockchains/ethereum/info/logo.png" 
                         style="height: 3.56rem; margin: 1.78rem 0 1.424rem 0;" />
                    <h5>Donate cryptocurrency</h5>
                    
                    <p>
                        Support our work by transferring cryptocurrency to our 
                        <a href="<spring:url value='/contributions/aragon-dao' />" target="_blank">Decentralized Autonomous Organization</a> (DAO).
                    </p>
                    <a href="<spring:url value='/contributions/donate' />" class="btn waves-effect waves-light deep-purple lighten-2">Donate now</a>
                </div>
                <p>&nbsp;</p>
            </div>
                  
            <div class="col s12 offset-m1 m4 z-depth-2 white center" style="border-radius: 8px;">
                <div class="icon-block" style="padding: 1em;">
                  <h2><i class="material-icons large deep-purple-text">group</i></h2>
                  <h4><fmt:message key="help.improve.the.software" /></h4>
                  
                  <p>
                      You are welcome to join us in building the code, or adding more educational content to the 
                      platform.
                  </p>
                  <a href="<spring:url value='/sign-on' />" id="join-button-top" class="btn-large waves-effect waves-light deep-purple"><fmt:message key="join.now" /></a>
                </div>
                <p>&nbsp;</p>
            </div>
                  
            <div class="col s12 offset-m1 m3 center">
                <div class="icon-block">
                  <h2><i class="material-icons medium">local_shipping</i></h2>
                  <h5><fmt:message key="distribute.the.software" /></h5>
                  
                  <p>Do you live near a child who does not have access to quality basic education? Help us <a href="https://github.com/elimu-ai/wiki/blob/main/SOFTWARE_ARCHITECTURE.md#distribution">distribute</a> the learning software.</p>
                </div>
            </div>
        </div>
    </div>
                  
    <div class="divider" style="margin-bottom: 3em;"></div>    
        <div class="section">
            <div class="row">
                <div class="col s12 offset-m3 m6 center">
                    <h4>Sign up for updates ✉️</h4>
                    
                    <form action="https://elimu.us12.list-manage.com/subscribe/post?u=1a69583fdeec7d1888db043c0&amp;id=97b79a9d90" method="post" target="_blank">
                        <div class="input-field col s12">
                            <label for="email" class=""><fmt:message key='email' /></label>
                            <input id="email" name="EMAIL" type="email" value="">
                        </div>
                        <button id="emailButton" class="btn deep-purple waves-effect waves-light disabled" type="submit">
                            <fmt:message key="subscribe" /> <i class="material-icons right">send</i>
                        </button>
                        <script>
                            $(function() {
                                $('#email').on('keyup', function() {
                                    console.log('#email keyup');
                                    var email = $(this).val();
                                    console.log('email: "' + email + '"');
                                    if (email.length > 0) {
                                        $('#emailButton').removeClass("disabled");
                                    }
                                });
                            });
                        </script>
                    </form>
                </div>
            </div>
        </div>
    </div>
</content:section>
